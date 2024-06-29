package com.example.helpingpeople;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helpingpeople.ui.aboutUs.AboutUs;
import com.example.helpingpeople.ui.account.Account;
import com.example.helpingpeople.ui.contacts.Contacts;
import com.example.helpingpeople.ui.home.HomeFragment;
import com.example.helpingpeople.ui.share.Share;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.helpingpeople.databinding.ActivityMainPageBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MainPage extends AppCompatActivity implements View.OnClickListener{

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainPageBinding binding;
    public Switch aSwitch;
    public SaveState saveState;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, getImg;

    ImageView headerImage;
    TextView headerName, headerEmail;

    String name, username, email, phoneNo, password, link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saveState = new SaveState(this);
        binding = ActivityMainPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMainPage.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_Account, R.id.nav_AboutUS, R.id.nav_Contacts,R.id.nav_Share )
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_page);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        // HEADER CODING

        name = getIntent().getStringExtra("name");
        username = getIntent().getStringExtra("username");
        email = getIntent().getStringExtra("email");
        phoneNo = getIntent().getStringExtra("phoneNo");
        password = getIntent().getStringExtra("password");

        setUserData(name, username, email, phoneNo, password, link);
        new TestUserData(name, username, email, phoneNo, password, link);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        getImg = databaseReference.child("users").child(username).child("image");

        View headerView = navigationView.getHeaderView(0);
        headerName = headerView.findViewById(R.id.username);
        headerEmail = headerView.findViewById(R.id.email);
        headerImage = headerView.findViewById(R.id.profileImage);

        headerName.setText(username);
        headerEmail.setText(email);

    }

    @Override
    protected void onStart() {
        super.onStart();

        getImg.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                link = dataSnapshot.getValue(String.class);
                Picasso.get().load(link).into(headerImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainPage.this, "Found Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (saveState.getState() == true) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        aSwitch = findViewById(R.id.switch_btn);
        if (saveState.getState() == true) {
            aSwitch.setChecked(true);
        }
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    saveState.setState(true);
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    saveState.setState(false);
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });
        getMenuInflater().inflate(R.menu.main_page, menu);
        boolean isNightModeOn = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES;
        aSwitch.setChecked(isNightModeOn);
        if (isNightModeOn) {
            aSwitch.setText("Night Mode");
        } else {
            aSwitch.setText("Light Mode");
        }

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_page);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.nav_home){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment()).commit();
        }
        else if(view.getId()==R.id.nav_Account){
         getSupportFragmentManager().beginTransaction().replace(R.id.container,new Account()).commit();
        }
        else if(view.getId()==R.id.nav_AboutUS){
         getSupportFragmentManager().beginTransaction().replace(R.id.container,new AboutUs()).commit();
        }
        else if(view.getId()==R.id.nav_Contacts){

            getSupportFragmentManager().beginTransaction().replace(R.id.container,new Contacts()).commit();
        }
       else if(view.getId()==R.id.nav_Share){
           getSupportFragmentManager().beginTransaction().replace(R.id.container,new Share()).commit();
       }
    }

    // DATA SETTING

    public void setUserData(String name, String username, String email, String phoneNo, String password, String link) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.phoneNo = phoneNo;
        this.password = password;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


}