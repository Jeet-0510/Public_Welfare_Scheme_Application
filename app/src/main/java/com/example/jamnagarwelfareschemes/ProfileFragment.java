package com.example.jamnagarwelfareschemes;

import static android.content.Context.MODE_PRIVATE;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ProfileFragment extends Fragment {

    public static final String SHARED_PREFS = "sharedPrefs";
    FloatingActionButton floatingActionButton;
    ImageView imageView;
    TextInputEditText name, email, number, address, city;
    Button update;
    String userName, Password, ConfirmPassword;
    Uri uriImage = null, uri = null;
    UserData userData;
    ValueEventListener valueEventListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Log.e("PF: ", "YES");
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        userName = sharedPreferences.getString("userName", "");

        floatingActionButton = view.findViewById(R.id.floating_actionPF);
        imageView = view.findViewById(R.id.image_viewPF);
        name = view.findViewById(R.id.namePF);
        email = view.findViewById(R.id.emailPF);
        number = view.findViewById(R.id.phonenoPF);
        address = view.findViewById(R.id.addressPF);
        city = view.findViewById(R.id.cityPF);
        update = view.findViewById(R.id.update);


        setData();
        // Remove the event listener after obtaining the data
//        FirebaseDatabase.getInstance().getReference("USERS").removeEventListener(valueEventListener);

        floatingActionButton.setOnClickListener(v -> ImagePicker.with(ProfileFragment.this).crop().compress(1024).maxResultSize(1080, 1080).start());

        imageView.setOnClickListener(v -> {
            // Create a dialog to show the enlarged image
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            // Create a parent layout to hold the ImageView
            LinearLayout parentLayout = new LinearLayout(getContext());
            parentLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            parentLayout.setGravity(Gravity.CENTER);

            // Create the ImageView and set its image resource or URI
            ImageView enlargedImageView = new ImageView(getContext());
            enlargedImageView.setLayoutParams(new LinearLayout.LayoutParams(
                    1000, // Set width in pixels
                    1000  // Set height in pixels
            ));
            if (uriImage == null) {
                enlargedImageView.setImageResource(R.drawable.person);
                //imageView.setImageResource(R.drawable.person1);
            } else {
                enlargedImageView.setImageURI(uriImage);
            }

            // Add the ImageView to the parent layout
            parentLayout.addView(enlargedImageView);

            // Set the parent layout as the view for the dialog
            builder.setView(parentLayout);

            AlertDialog dialog = builder.create();
            dialog.show();

        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString();
                String Email = email.getText().toString();
                String Phone = number.getText().toString();
                String Address = address.getText().toString();
                String City = city.getText().toString();
                if (uri != null) {
                    String Image = uri.toString();

                    if (Name.isEmpty() || Email.isEmpty() || Phone.isEmpty() || Address.isEmpty() || City.isEmpty() || Image.isEmpty()) {
                        Toast.makeText(getContext(), "Please Fill all the Details!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        save();
                        name.setText(Name);
                        email.setText(Email);
                        number.setText(Phone);
                        address.setText(Address);
                        city.setText(City);

                        Glide.with(getContext())
                                .load(Image)
                                .into(imageView);
                    }
                }
            }
        });


        return view;
    }

    private void setData() {
        valueEventListener = FirebaseDatabase.getInstance().getReference("USERS").child(userName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                        if (Objects.equals(snapshot1.getKey(), "Profile")) {
                            UserData userData = snapshot1.getValue(UserData.class);

                            if (userData != null) {
                                name.setText(userData.getName());
                                email.setText(userData.getEmail());
                                number.setText(userData.getNumber());
                                address.setText(userData.getAddress());
                                city.setText(userData.getCity());
                                Password = userData.getPassword();
                                ConfirmPassword = userData.getConfirmPassword();


//                        Log.d("userName",userName);
//                        Log.d("name", String.valueOf(userData.getName().isEmpty()));

                                if (getActivity() != null) {
                                    if (userData.getPhoto() != null && !userData.getPhoto().equals("no image")) {
                                        Glide.with(getContext())
                                                .load(userData.getPhoto())
                                                .into(imageView);

                                        uriImage = Uri.parse(userData.getPhoto());
                                        uri = uriImage;
                                    } else {
                                        imageView.setImageResource(R.drawable.person);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void save() {
        String Name = name.getText().toString();
        String Email = email.getText().toString();
        String Phone = number.getText().toString();
        String Address = address.getText().toString();
        String City = city.getText().toString();
        String Image = null;

        if (uri != null) {
            Image = uri.toString();

            UserData user = new UserData(Name, Email, Phone, Address, City, userName, Password, ConfirmPassword, Image);
            FirebaseDatabase.getInstance().getReference("USERS").child(userName).child("Profile")
                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getContext(), "Updated Successfully!!!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    });

            // Remove the event listener after obtaining the data
//            FirebaseDatabase.getInstance().getReference("USERS").removeEventListener(valueEventListener);

        }
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        uri = data.getData();
        if (uri != null) {
            imageView.setImageURI(uri);
        } else {
//            uri = uriImage;
            imageView.setImageURI(uri);
        }
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        // Remove the listener
//        if (valueEventListener != null) {
//            // Remove the event listener after obtaining the data
//            FirebaseDatabase.getInstance().getReference("USERS").removeEventListener(valueEventListener);
//        }
//    }
}