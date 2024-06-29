package com.example.helpingpeople.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.helpingpeople.Cloth_Section;
import com.example.helpingpeople.Food_Section;
import com.example.helpingpeople.Money_Section;
import com.example.helpingpeople.Pet_Section;
import com.example.helpingpeople.R;
import com.example.helpingpeople.TestUserData;
import com.example.helpingpeople.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private FragmentHomeBinding binding;
    String name, username, email, phoneNo, password;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CardView food, cloth, money, pet;


        View v = inflater.inflate(R.layout.fragment_home, container, false);

        food = v.findViewById(R.id.food);
        food.setOnClickListener(this);

        cloth = v.findViewById(R.id.cloth);
        cloth.setOnClickListener(this);

        money = v.findViewById(R.id.money);
        money.setOnClickListener(this);

        pet = v.findViewById(R.id.pet);
        pet.setOnClickListener(this);


        ImageSlider imageSlider = v.findViewById(R.id.image_slider);
        List<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel(R.drawable.a, ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.b, ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.c, ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.d, ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.e, ScaleTypes.FIT));
        imageSlider.setImageList(imageList, ScaleTypes.FIT);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.food:
                intent = new Intent(getActivity().getApplication(), Food_Section.class);

                TestUserData testUserData = new TestUserData();

                name = testUserData.getName();
                username = testUserData.getUsername();
                email = testUserData.getEmail();
                phoneNo = testUserData.getPhoneNo();
                password = testUserData.getPassword();

                intent.putExtra("name",name);
                intent.putExtra("username",username);
                intent.putExtra("email",email);
                intent.putExtra("phoneNo",phoneNo);
                intent.putExtra("password",password);

                startActivity(intent);
                break;
            case R.id.cloth:
                intent = new Intent(getActivity().getApplication(), Cloth_Section.class);
                startActivity(intent);
                break;
            case R.id.money:
                intent = new Intent(getActivity().getApplication(), Money_Section.class);
                startActivity(intent);
                break;
            case R.id.pet:
                intent = new Intent(getActivity().getApplication(), Pet_Section.class);
                startActivity(intent);
                break;
        }
    }
}