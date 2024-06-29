package com.example.helpingpeople.ui.account;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.helpingpeople.MainPage;
import com.example.helpingpeople.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class Account extends Fragment {

    private ImageView updateImage;
    private EditText updateName, updateUserName, updateEmail, updatePhoneNo, updatePassword;
    private Button updateBtn, deleteBtn;

    private String name, username, email, image, phoneNo, password, removeOld, category, uniquekey;
    private final int REQ = 1;
    private Bitmap bitmap = null;

    private StorageReference storageReference, deleteReference;
    private DatabaseReference reference;
    private String downloadUrl;

    private ProgressDialog pd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.account_fragment, container, false);

        updateImage = view.findViewById(R.id.updateImage);
        updateName = view.findViewById(R.id.updateName);
        updateUserName = view.findViewById(R.id.updateUserName);
        updateEmail = view.findViewById(R.id.updateEmail);
        updatePhoneNo = view.findViewById(R.id.updatePhoneNo);
        updatePassword = view.findViewById(R.id.updatePassword);
        updateBtn = view.findViewById(R.id.updateBtn);
        deleteBtn = view.findViewById(R.id.deleteBtn);

        reference = FirebaseDatabase.getInstance().getReference().child("users");
        storageReference = FirebaseStorage.getInstance().getReference();

        MainPage mainPage = (MainPage) getActivity();

        pd = new ProgressDialog(getContext());

        image = mainPage.getLink();
        name = mainPage.getName();
        username = mainPage.getUsername();
        email = mainPage.getEmail();
        phoneNo = mainPage.getPhoneNo();
        password = mainPage.getPassword();
        removeOld = image;

        deleteReference = FirebaseStorage.getInstance().getReferenceFromUrl(removeOld);

        updateName.setText(name);
        updateUserName.setText(username);
        updateEmail.setText(email);
        updatePhoneNo.setText(phoneNo);
        updatePassword.setText(password);
        Picasso.get().load(image).into(updateImage);

        updateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pd.setMessage("Updating....");
                pd.show();

                name = updateName.getText().toString();
                username = updateUserName.getText().toString();
                email = updateEmail.getText().toString();
                phoneNo = updatePhoneNo.getText().toString();
                password = updatePassword.getText().toString();

                checkValidation();
            }
        });

        return view;
    }

    private void checkValidation() {
        if (bitmap == null) {
            UpdateData(image);
        } else if (name.isEmpty()) {
            updateName.setError("Empty");
            updateName.requestFocus();
        } else if (username.isEmpty()) {
            updateUserName.setError("Empty");
            updateUserName.requestFocus();
        } else if (email.isEmpty()) {
            updateEmail.setError("Empty");
            updateEmail.requestFocus();
        } else if (phoneNo.isEmpty()) {
            updatePhoneNo.setError("Empty");
            updatePhoneNo.requestFocus();
        } else if (password.isEmpty()) {
            updatePassword.setError("Empty");
            updatePassword.requestFocus();
        } else {
            uploadImage();
        }
    }

    private void uploadImage() {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] finalimg = baos.toByteArray();
        final StorageReference filePath;
        filePath = storageReference.child("users").child(finalimg + "png");
        final UploadTask uploadTask = filePath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(getActivity(), new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl = String.valueOf(uri);
                                    deleteOldImage();
                                    UpdateData(downloadUrl);
                                }
                            });
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void UpdateData(String downloadUrl) {

        HashMap hp = new HashMap();

        hp.put("name", name);
        hp.put("username", username);
        hp.put("email", email);
        hp.put("phoneNo", phoneNo);
        hp.put("password", password);
        hp.put("image", downloadUrl);

        reference.child(username).updateChildren(hp).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
//                Intent intent = new Intent(getActivity(), UploadFaculty.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
                pd.dismiss();
                Toast.makeText(getActivity(), "Updated Successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteOldImage(){
        deleteReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openGallery() {
        Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage, REQ);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            updateImage.setImageBitmap(bitmap);
        }
    }

}