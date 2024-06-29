package com.example.helpingpeople;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.TimeUnit;

public class verifyphoneno extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String verificationId;
    private Button verify_btn;
    private EditText CodeEnteredByTheUser;
    private ProgressBar progressBar;
    public static int DELAY = 4000;
    private ProgressDialog progress;

    // STORAGE REFERENCE
    private StorageReference storageReference;

    // DATABASE REFERENCE
    private DatabaseReference reference, dbRef;

    // String
    private String name, username, email, phoneNo, password, downloadUrl = "";

    // BITMAP
    private Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_verifyphoneno);

        mAuth = FirebaseAuth.getInstance();

        reference = FirebaseDatabase.getInstance().getReference().child("users");
        storageReference = FirebaseStorage.getInstance().getReference();
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.users);

        progress = new ProgressDialog(this);

        // HOOKS // GET BTN :- idBtnGetOtp

        verify_btn = findViewById(R.id.verify_btn); // idBtnVerify
        CodeEnteredByTheUser = findViewById(R.id.verification_code_entered_by_user); // idEdtOtp

        String phoneNo = getIntent().getStringExtra("phoneNo");

        String phone = "+91" + phoneNo;

        sendVerificationCode(phone);

        // VERIFY BTN

        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String otp = CodeEnteredByTheUser.getText().toString();

                if (TextUtils.isEmpty(otp)) {
                    // if the OTP text field is empty display
                    // a message to user to enter OTP
                    Toast.makeText(verifyphoneno.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
                } else {
                    // if OTP field is not empty calling
                    // method to verify the OTP.
                    verifyCode(otp);
                }
            }
        });
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // CODING

                            uploadImage();

                        } else {
                            Toast.makeText(verifyphoneno.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progress.dismiss();
                        }
                    }
                });
    }

    private void uploadImage() {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] finalimg = baos.toByteArray();
        final StorageReference filePath;
        filePath = storageReference.child("users").child(finalimg + "png");
        final UploadTask uploadTask = filePath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(verifyphoneno.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                                    insertData();
                                }
                            });
                        }
                    });
                } else {
//                    pd.dismiss();
                    Toast.makeText(verifyphoneno.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void insertData() {
        dbRef = reference;
        final String uniqueKey = dbRef.push().getKey();

        String name = getIntent().getStringExtra("name");
        String username = getIntent().getStringExtra("username");
        String email = getIntent().getStringExtra("email");
        String phoneNo = getIntent().getStringExtra("phoneNo");
        String password = getIntent().getStringExtra("password");

        userData UserData = new userData(name, username, email, phoneNo, password, downloadUrl, uniqueKey);

        dbRef.child(username).setValue(UserData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progress.dismiss();
                Intent intent = new Intent(verifyphoneno.this, MainPage.class);

                intent.putExtra("username", username);
                intent.putExtra("email", email);

                Toast.makeText(verifyphoneno.this, "User Added", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progress.dismiss();
                Toast.makeText(verifyphoneno.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
        finish();
    }

    private void sendVerificationCode(String number) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallBack)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            final String code = phoneAuthCredential.getSmsCode();

            if (code != null) {

                CodeEnteredByTheUser.setText(code);

                verifyCode(code);

            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(verifyphoneno.this, "OTP DOES NOT MATCH", Toast.LENGTH_LONG).show();
        }
    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        progress.setMessage("Verifying");
        progress.show();
        signInWithCredential(credential);
    }
}