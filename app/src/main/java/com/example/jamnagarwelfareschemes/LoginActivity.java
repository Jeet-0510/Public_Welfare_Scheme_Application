package com.example.jamnagarwelfareschemes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    TextView txtSignUp, txtForgotPassword;
    TextInputEditText txtusername, txtpassword;
    Button login;
    String username, password, usernameV;
    DatabaseReference databaseReference;
    ValueEventListener eventListener, eventListener1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.e("LA: ","Yes");

        txtSignUp = findViewById(R.id.txtSignUp);
        txtForgotPassword = findViewById(R.id.txtForgotPassword);
        txtusername = findViewById(R.id.txtUserNameLogin);
        txtpassword = findViewById(R.id.txtPasswordLogin);
        login = findViewById(R.id.btnLogin);

        databaseReference = FirebaseDatabase.getInstance().getReference("USERS");

        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ForgotPassword.class);
                startActivity(intent);
            }
        });

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
//                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = txtusername.getText().toString();
                password = txtpassword.getText().toString();

                if (!username.isEmpty() && !password.isEmpty()) {

                    List<String> userNames = new ArrayList<>();
                    eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                                    usernameV = itemSnapshot.getKey();
                                    userNames.add(usernameV);
                                }
                                if (userNames.contains(username)) {

                                    eventListener1 = databaseReference.child(username).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {
                                                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                                                    if (itemSnapshot.getKey().equals("Profile")) {
                                                        UserData userData = itemSnapshot.getValue(UserData.class);
                                                        if (password.equals(userData.getPassword())) {
                                                            Toast.makeText(LoginActivity.this, "Login Successfully!!!", Toast.LENGTH_SHORT).show();

                                                            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                                            editor.putString("userName", username);
                                                            editor.putString("check", "true");
                                                            editor.apply();

                                                            Intent intent = new Intent(getApplicationContext(), Home.class);
                                                            startActivity(intent);
                                                            finish();
                                                        } else {
                                                            Toast.makeText(LoginActivity.this, "Invalid Password!!!", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                                    // Remove the event listener after obtaining the data
                                    FirebaseDatabase.getInstance().getReference("USERS").removeEventListener(eventListener1);
                                } else {
                                    Toast.makeText(LoginActivity.this, "Invalid Username!!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    // Remove the event listener after obtaining the data
//                    FirebaseDatabase.getInstance().getReference("USERS").removeEventListener(eventListener);
                } else {
                    Toast.makeText(LoginActivity.this, "Please Enter All Details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}