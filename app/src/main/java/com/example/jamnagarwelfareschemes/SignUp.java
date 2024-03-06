package com.example.jamnagarwelfareschemes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    private static final String EMAIL_REGEX =
            "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PASSWORD_PATTERN =
            "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    TextInputEditText txtName, txtNumber, txtEmail, txtAddress, txtUsername, txtPassword, txtConfirmPassword, txtCity;
    Button btnRegister;
    String name, email, address, number, city, username, password, confirmPassword;
    UserData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txtName = findViewById(R.id.txtNameUser);
        txtAddress = findViewById(R.id.txtAddressUser);
        txtNumber = findViewById(R.id.txtNumberUser);
        txtCity = findViewById(R.id.txtCityUser);
        txtEmail = findViewById(R.id.txtEmailUser);
        txtUsername = findViewById(R.id.txtUserNameUser);
        txtPassword = findViewById(R.id.txtPasswordUser);
        txtConfirmPassword = findViewById(R.id.txtConfirmPasswordUser);
        btnRegister = findViewById(R.id.btnRegisterUser);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = txtName.getText().toString();
                email = txtEmail.getText().toString();
                address = txtAddress.getText().toString();
                number = txtNumber.getText().toString();
                city = txtCity.getText().toString();
                username = txtUsername.getText().toString();
                password = txtPassword.getText().toString();
                confirmPassword = txtConfirmPassword.getText().toString();

                if (!name.isEmpty() && !email.isEmpty() && !address.isEmpty() && !number.isEmpty() && !city.isEmpty() && !username.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()) {

                    if (!isValidEmail(email)) {
                        Toast.makeText(SignUp.this, "Email address not valid!!!", Toast.LENGTH_SHORT).show();
                    }else if (!validate(password)){
                        Toast.makeText(SignUp.this, "Password not valid!!!", Toast.LENGTH_SHORT).show();
                    }else if (!(number.length() ==10)){
                        Toast.makeText(SignUp.this, "Mobile Number should be 10 digits!!!", Toast.LENGTH_SHORT).show();
                    }else {
                        List<String> userNames = new ArrayList<>();

                        ValueEventListener eventListener = FirebaseDatabase.getInstance().getReference("USERS").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                                        String usernameV = itemSnapshot.getKey();
                                        userNames.add(usernameV);
                                    }
                                    if (!userNames.contains(username)) {
                                        if (!password.equals(confirmPassword)) {
                                            txtPassword.setText("");
                                            txtConfirmPassword.setText("");
                                            Toast.makeText(SignUp.this, "Password didn't Matched!!!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            data = new UserData(name, email, number, address, city, username, password, confirmPassword,"no image");
                                            FirebaseDatabase.getInstance().getReference().child("USERS").child(username).child("Profile").setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                    Toast.makeText(SignUp.this, "Registration Successful!!!", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    } else {
                                        Toast.makeText(SignUp.this, "UserName Already Exists!!", Toast.LENGTH_SHORT).show();
                                    }

                                    // Remove the event listener after obtaining the data
                                    FirebaseDatabase.getInstance().getReference("USERS").removeEventListener(this);
                                } else {
                                    if (!password.equals(confirmPassword)) {
                                        txtPassword.setText("");
                                        txtConfirmPassword.setText("");
                                        Toast.makeText(SignUp.this, "Password didn't Matched!!!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        data = new UserData(name, email, number, address, city, username, password, confirmPassword, "no image");
                                        FirebaseDatabase.getInstance().getReference().child("USERS").child(username).child("Profile").setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                                startActivity(intent);
                                                finish();
                                                Toast.makeText(SignUp.this, "Registration Successful!!!", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                        // Remove the event listener after obtaining the data
                                        FirebaseDatabase.getInstance().getReference("USERS").removeEventListener(this);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                } else {
                    Toast.makeText(SignUp.this, "All the Fields are Mandatory!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validate(final String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}