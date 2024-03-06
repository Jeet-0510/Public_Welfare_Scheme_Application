package com.example.jamnagarwelfareschemes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AlignmentSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.EventListener;
import java.util.Objects;

public class CatagorySchemeDetail extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    TextView SchemeName, Details, Deadline, Documents, Eligibility;
    Button Apply;
    String schemeName, eligibilitycriteria, deadline, schemeDetail, documents, catagory, userName;
    int currentYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catagory_scheme_detail);
        Log.e("CSD: ","Yes");

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        userName = sharedPreferences.getString("userName", "");

        Intent intent1 = getIntent();
        schemeName = intent1.getStringExtra("schemeName");
        eligibilitycriteria = intent1.getStringExtra("schemeEligibility");
        deadline = intent1.getStringExtra("schemeDeadline");
        schemeDetail = intent1.getStringExtra("schemeDetail");
        documents = intent1.getStringExtra("schemeDocument");
        catagory = intent1.getStringExtra("schemeCatagory");

        SchemeName = findViewById(R.id.txtSchemeNameD);
        Details = findViewById(R.id.txtSchemeDetailD);
        Deadline = findViewById(R.id.txtDeadlineD);
        Documents = findViewById(R.id.txtDocumentsD);
        Eligibility = findViewById(R.id.txtEligibilityCriteriaD);
        Apply = findViewById(R.id.btnApplyNowD);

        SpannableString spannableString1 = new SpannableString(schemeName);
        spannableString1.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_NORMAL), 0, schemeName.length(), 0);

        SpannableString spannableString2 = new SpannableString(eligibilitycriteria);
        spannableString2.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_NORMAL), 0, eligibilitycriteria.length(), 0);

        SpannableString spannableString3 = new SpannableString(schemeDetail);
        spannableString3.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_NORMAL), 0, schemeDetail.length(), 0);

        SchemeName.setText(spannableString1);
        Details.setText(spannableString3);
        Eligibility.setText(spannableString2);
        Deadline.setText(deadline);

        Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);

        documents = documents.substring(1, documents.length() - 1);
        String[] items = documents.split(", ");

        StringBuilder displayText = new StringBuilder();
        for (int i = 0; i < items.length; i++) {
            displayText.append((i + 1) + ". " + items[i]).append("\n");
        }
        Documents.setText(displayText);

        ValueEventListener valueEventListener = FirebaseDatabase.getInstance().getReference("USERS").child(userName).child("Schemes").child(String.valueOf(currentYear)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        String scheme = snapshot1.child("SchemeStatusData").child("schemeName").getValue(String.class);
//                        SchemesStatusData schemesStatusData = snapshot1.getValue(SchemesStatusData.class);
                        if (Objects.equals(scheme, schemeName)) {
                            Apply.setEnabled(false);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference("USERS").removeEventListener(valueEventListener);

        Apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("ApplicationIdCounter");

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Long currentId = (Long) snapshot.getValue();
                            if (currentId != null) {
                                Long nextCurrentId = currentId + 1;

                                databaseReference.setValue(nextCurrentId).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    if (snapshot.exists()) {
                                                        Long currentId = (Long) snapshot.getValue();

                                                        if (currentId != null) {

                                                            String status = "Application Started";
                                                            String query = "Submission Pending!!!";
                                                            String transaction = "Pending";
                                                            String date = "Not Applied";
                                                            SchemesStatusData schemesStatusData = new SchemesStatusData(String.valueOf(currentId), schemeName, catagory, status, query, transaction, date);
                                                            //Inside Applications
                                                            FirebaseDatabase.getInstance().getReference().child("Applications").child(String.valueOf(currentYear)).child(catagory).child(schemeName).child(String.valueOf(currentId)).child("SchemeStatusData").setValue(schemesStatusData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Toast.makeText(CatagorySchemeDetail.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                }
                                                            });

                                                            //Inside Users
                                                            FirebaseDatabase.getInstance().getReference().child("USERS").child(userName).child("Schemes").child(String.valueOf(currentYear)).child(String.valueOf(currentId)).child("SchemeStatusData").setValue(schemesStatusData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
//                                Toast.makeText(SchemeApplicationDetails.this, "Status Updated!!!", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Toast.makeText(CatagorySchemeDetail.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                }
                                                            });

                                                            Intent intent = new Intent(getApplicationContext(), SchemeApplication.class);
                                                            intent.putExtra("key", "CategorySchemeDetail");
                                                            intent.putExtra("applicationId", String.valueOf(currentId));
//                                                            Log.d("AppIdCS", String.valueOf(currentId));
                                                            intent.putExtra("schemeName", schemeName);
                                                            intent.putExtra("schemeDetail", schemeDetail);
                                                            intent.putExtra("schemeEligibility", eligibilitycriteria);
                                                            intent.putExtra("schemeDeadline", deadline);
                                                            intent.putExtra("schemeCatagory", catagory);
                                                            intent.putExtra("schemeDocument", documents);
                                                            startActivity(intent);
                                                            Toast.makeText(CatagorySchemeDetail.this, "Application Started!!!", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(CatagorySchemeDetail.this, "Error Fetching the Application Id!!!", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }else{
                                                        Log.e("Error:" , "No snapshot exist CSD.java");
//                                                        Toast.makeText(CatagorySchemeDetail.this, "No Snapshot Exists", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });


                                        } else {
                                            Toast.makeText(CatagorySchemeDetail.this, "Application not Generated!!!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(CatagorySchemeDetail.this, "ApplicationId is null!!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Long startApplicationId = 1000L;
                            databaseReference.setValue(startApplicationId).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(getApplicationContext(), SchemeApplication.class);
                                        intent.putExtra("key", "CategorySchemeDetail");
                                        intent.putExtra("applicationId", startApplicationId);
                                        intent.putExtra("schemeName", schemeName);
                                        intent.putExtra("schemeDetail", schemeDetail);
                                        intent.putExtra("schemeEligibility", eligibilitycriteria);
                                        intent.putExtra("schemeDeadline", deadline);
                                        intent.putExtra("schemeCatagory", catagory);
                                        intent.putExtra("schemeDocument", documents);
                                        startActivity(intent);
                                        Toast.makeText(CatagorySchemeDetail.this, "Application Started!!!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(CatagorySchemeDetail.this, "Application not Generated!!!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                // Remove the event listener after obtaining the data
//                FirebaseDatabase.getInstance().getReference("USERS").removeEventListener();
            }
        });
    }
}