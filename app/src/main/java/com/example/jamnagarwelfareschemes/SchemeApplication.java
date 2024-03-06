package com.example.jamnagarwelfareschemes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class SchemeApplication extends AppCompatActivity {

    private static final int FILE_PICKER_REQUEST_CODE = 1;
    public static final String SHARED_PREFS = "sharedPrefs";
    StorageReference storageReference;
    TextView SchemeName;
    TextInputEditText txtDateOfBirth, txtName, txtAddress, txtOccupation, txtincome, txtPincode, txtAadhar, txtPan;
    RadioGroup radioGender, radioDisabled;
    Spinner spinner;
    TextInputEditText txtFees, txtSSCBoard, txtSSCPY, txtSSCSchool, txtSSCPercentage, txtHSCBoard, txtHSCPY, txtHSCSchool, txtHSCPercentage, txtBachUniversity, txtBachDegree, txtbackGY, txtBachCgpa;
    TextInputEditText txtBankName, txtBranch, txtIFSC, txtAccountNo, txtRTAccountNo;
    Button btnCAadhar, btnPhoto, btnPan, btnCaste, btnIncome, btnSsc, btnHsc, btnGraduation, btnPwd, btnBank, btnFeesReceipt;
    ImageView imgVAdhar;
    Button btnSave, btnSubmit;
    private static final int PICK_PDF_REQUEST = 1;
    String schemeName, eligibilitycriteria, deadline, schemeDetail, documents, catagory, applicationID, status = "Submission Pending";
    String caste = "General", gender, disabled, name, dob, address, occupation, income, pincode, aadhar, pan;
    String fees, SSCBoard, SSCPY, SSCSchool, SSCPercentage, HSCBoard, HSCPY, HSCSchool, HSCPercentage, BachUniversity, BachDegree, backGY, BachCgpa;
    String BankName, Branch, IFSC, AccountNo, RTAccountNo;
    String aadharURL, photoURL, panURL, casteURL, incomeURL, sscURL, hscURL, graduationURL, pwdURL, bankURL, feesReceiptURL;
    Uri uriAadhar;
    PDFView pdfView;
    String userName;
    int flag=0;
    int currentYear;
    String query, transaction;
    List<String> documentsList;
    String[] casteOptions = {"General", "OBC", "SC", "ST", "EWS"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheme_application);
        Log.e("SA: ","Yes");

        Intent intent1 = getIntent();
        String key = intent1.getStringExtra("key");
        schemeName = intent1.getStringExtra("schemeName");
        catagory = intent1.getStringExtra("schemeCatagory");
        applicationID = intent1.getStringExtra("applicationId");

        if (key.equals("CategorySchemeDetail")) {
            eligibilitycriteria = intent1.getStringExtra("schemeEligibility");
            deadline = intent1.getStringExtra("schemeDeadline");
            schemeDetail = intent1.getStringExtra("schemeDetail");
            documents = intent1.getStringExtra("schemeDocument");
        } else if (key.equals("SchemeStatusAdapter")) {

        }

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        userName = sharedPreferences.getString("userName", "");

        documentsList = new ArrayList<>();

        SchemeName = findViewById(R.id.txtSchemeNameA);
        txtName = findViewById(R.id.txtFullNameA);
        txtDateOfBirth = findViewById(R.id.txtDateOfBirthA);
        txtAadhar = findViewById(R.id.txtAadharNoA);
        txtAddress = findViewById(R.id.txtAddressA);
        txtincome = findViewById(R.id.txtFamilyIncomeA);
        txtOccupation = findViewById(R.id.txtOccupationA);
        txtPincode = findViewById(R.id.txtPincodeA);
        txtPan = findViewById(R.id.txtPanNoA);
        spinner = findViewById(R.id.spinnerA);
        radioGender = findViewById(R.id.radioGroupGenderA);
        radioDisabled = findViewById(R.id.radioGroupDisabledA);

        txtFees = findViewById(R.id.txtFeesAmountA);
        txtSSCBoard = findViewById(R.id.txtSSCBoardNameA);
        txtSSCPY = findViewById(R.id.txtSSCPassingYearA);
        txtSSCPercentage = findViewById(R.id.txtSSCObtainedPercentageA);
        txtSSCSchool = findViewById(R.id.txtSSCSchoolNameA);
        txtHSCPercentage = findViewById(R.id.txtHSCDObtainedPercentageA);
        txtHSCPY = findViewById(R.id.txtHSCDPassingYearA);
        txtHSCBoard = findViewById(R.id.txtHSCDBoardNameA);
        txtHSCSchool = findViewById(R.id.txtHSCDSchoolNameA);
        txtBachCgpa = findViewById(R.id.txtBachCGPA);
        txtBachDegree = findViewById(R.id.txtBachDegreeNameA);
        txtBachUniversity = findViewById(R.id.txtBachUniversityNameA);
        txtbackGY = findViewById(R.id.txtBachGraduationYearA);

        txtBankName = findViewById(R.id.txtBankNameA);
        txtBranch = findViewById(R.id.txtBankBranchA);
        txtIFSC = findViewById(R.id.txtIFSCCodeA);
        txtAccountNo = findViewById(R.id.txtAccountNumberA);
        txtRTAccountNo = findViewById(R.id.txtReTypeAccountNumberA);

        btnCAadhar = findViewById(R.id.btnAadharA);
        btnPhoto = findViewById(R.id.btnPhotoA);
        btnCaste = findViewById(R.id.btnCasteCertificateA);
        btnIncome = findViewById(R.id.btnIncomeCertificateA);
        btnSsc = findViewById(R.id.btnSSCMarksheetA);
        btnHsc = findViewById(R.id.btnHSCMarksheetA);
        btnGraduation = findViewById(R.id.btnGraduationMarksheetA);
        btnBank = findViewById(R.id.btnBankPassBookA);
        btnPan = findViewById(R.id.btnPANCardA);
        btnPwd = findViewById(R.id.btnPWDCertificateA);
        btnFeesReceipt = findViewById(R.id.btnFeesReceiptA);

        imgVAdhar = findViewById(R.id.imgViewAadharA);
        pdfView = findViewById(R.id.pdfView);

        btnSave = findViewById(R.id.btnSaveA);
        btnSubmit = findViewById(R.id.btnSubmitA);

        SchemeName.setText(schemeName);
        Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);

        storageReference = FirebaseStorage.getInstance().getReference().child("Uploads").child(applicationID);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_list, casteOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                caste = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // This method is called when nothing is selected
            }
        });

        fetchData();

        setDocuments();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flagSubmit = 0;
                name = txtName.getText().toString();
                occupation = txtOccupation.getText().toString();
                income = txtincome.getText().toString();
                address = txtAddress.getText().toString();
                pincode = txtPincode.getText().toString();
                aadhar = txtAadhar.getText().toString();
                pan = txtPan.getText().toString();

                int selectedGenderId = radioGender.getCheckedRadioButtonId();
                if (selectedGenderId != -1) {
                    RadioButton selectedRadioButton = findViewById(selectedGenderId);
                    gender = selectedRadioButton.getText().toString();
                }

                int selectedDisabledId = radioDisabled.getCheckedRadioButtonId();
                if (selectedDisabledId != -1) {
                    RadioButton selectedRadioButton = findViewById(selectedDisabledId);
                    disabled = selectedRadioButton.getText().toString();
                }

                fees = txtFees.getText().toString();
                SSCBoard = txtSSCBoard.getText().toString();
                SSCPY = txtSSCPY.getText().toString();
                SSCSchool = txtSSCSchool.getText().toString();
                SSCPercentage = txtSSCPercentage.getText().toString();
                HSCBoard = txtHSCBoard.getText().toString();
                HSCPY = txtHSCPY.getText().toString();
                HSCSchool = txtHSCSchool.getText().toString();
                HSCPercentage = txtHSCPercentage.getText().toString();
                BachUniversity = txtBachUniversity.getText().toString();
                BachDegree = txtBachDegree.getText().toString();
                backGY = txtbackGY.getText().toString();
                BachCgpa = txtBachCgpa.getText().toString();

                BankName = txtBankName.getText().toString();
                Branch = txtBranch.getText().toString();
                IFSC = txtIFSC.getText().toString();
                AccountNo = txtAccountNo.getText().toString();
                RTAccountNo = txtRTAccountNo.getText().toString();

                if (applicationID != null) {

                    if (!name.equals("") && !address.equals("") && !pincode.equals("") && !aadhar.equals("") && !pan.equals("") && !gender.equals("") && !caste.equals("") && !disabled.equals("") && !dob.equals("") && !occupation.equals("") && !income.equals("")) {

                        flagSubmit++;
                        PersonalInformationData personalInformationData = new PersonalInformationData(name, caste, gender, disabled, dob, address, occupation, income, pincode, aadhar, pan);

                        //Inside Applications
                        FirebaseDatabase.getInstance().getReference().child("Applications").child(String.valueOf(currentYear)).child(catagory).child(schemeName).child(applicationID).child("Personal Information").setValue(personalInformationData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(SchemeApplication.this, "Personal Details Updated!!!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SchemeApplication.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        //Inside Users
                        FirebaseDatabase.getInstance().getReference().child("USERS").child(userName).child("Schemes").child(String.valueOf(currentYear)).child(applicationID).child("Personal Information").setValue(personalInformationData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
//                                Toast.makeText(SchemeApplicationDetails.this, "Personal Details Updated!!!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SchemeApplication.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
//                        Toast.makeText(SchemeApplication.this, "All the Fields of Personal Details are Mandatory!!!", Toast.LENGTH_SHORT).show();
                    }

                    if (!fees.equals("") && !SSCBoard.equals("") && !SSCPercentage.equals("") && !SSCSchool.equals("") && !SSCPY.equals("") && !HSCBoard.equals("") && !HSCPercentage.equals("") && !HSCSchool.equals("") && !HSCPY.equals("") && !BachCgpa.equals("") && !BachDegree.equals("") && !BachUniversity.equals("") && !backGY.equals("")) {

                        flagSubmit++;
                        EducationData educationData = new EducationData(fees, SSCBoard, SSCPY, SSCSchool, SSCPercentage, HSCBoard, HSCPY, HSCSchool, HSCPercentage, BachUniversity, BachDegree, backGY, BachCgpa);

                        //Inside Applications
                        FirebaseDatabase.getInstance().getReference().child("Applications").child(String.valueOf(currentYear)).child(catagory).child(schemeName).child(applicationID).child("Education Information").setValue(educationData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(SchemeApplication.this, "Education Details Updated!!!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SchemeApplication.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        //Inside Users
                        FirebaseDatabase.getInstance().getReference().child("USERS").child(userName).child("Schemes").child(String.valueOf(currentYear)).child(applicationID).child("Education Information").setValue(educationData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
//                                Toast.makeText(SchemeApplicationDetails.this, "Education Details Updated!!!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SchemeApplication.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
//                        Toast.makeText(SchemeApplicationDetails.this, "All the Fields of Education Details are Mandatory!!!", Toast.LENGTH_SHORT).show();
                    }

                    if (!BankName.equals("") && !Branch.equals("") && !IFSC.equals("") && !AccountNo.equals("") && !RTAccountNo.equals("")) {

                        flagSubmit++;

                        //Inside Applications
                        BankData bankData = new BankData(BankName, Branch, IFSC, AccountNo, RTAccountNo);
                        FirebaseDatabase.getInstance().getReference().child("Applications").child(String.valueOf(currentYear)).child(catagory).child(schemeName).child(applicationID).child("Bank Information").setValue(bankData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(SchemeApplication.this, "Bank Details Updated!!!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SchemeApplication.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        //Inside Users
                        FirebaseDatabase.getInstance().getReference().child("USERS").child(userName).child("Schemes").child(String.valueOf(currentYear)).child(applicationID).child("Bank Information").setValue(bankData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
//                                Toast.makeText(SchemeApplicationDetails.this, "Bank Details Updated!!!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SchemeApplication.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
//                        Toast.makeText(SchemeApplicationDetails.this, "All the Fields of Bank Details are Mandatory!!!", Toast.LENGTH_SHORT).show();
                    }

                    saveSubmitStatus(flagSubmit);

                    /*if(aadharURL != null && photoURL != null && incomeURL != null && casteURL != null && bankURL != null && sscURL != null && hscURL != null && graduationURL != null && panURL != null && feesReceiptURL != null){

                        flagSubmit++;
                        DocumentsData documentsData = new DocumentsData(aadharURL, photoURL, panURL, casteURL , incomeURL, sscURL,  hscURL, graduationURL, pwdURL, bankURL,feesReceiptURL);

                        //Inside Applications
                        FirebaseDatabase.getInstance().getReference().child("Applications").child(String.valueOf(currentYear)).child(catagory).child(schemeName).child(applicationID).child("Document Information").setValue(documentsData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(SchemeApplication.this, "Documents Updated!!!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SchemeApplication.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        //Inside Users
                        FirebaseDatabase.getInstance().getReference().child("USERS").child(userName).child("Schemes").child(String.valueOf(currentYear)).child(applicationID).child("Document Information").setValue(documentsData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
//                                Toast.makeText(SchemeApplicationDetails.this, "Bank Details Updated!!!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SchemeApplication.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }*/

                } else {
                    Toast.makeText(SchemeApplication.this, "Error Fetching Application Id!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flagSave=0;

                name = txtName.getText().toString();
                occupation = txtOccupation.getText().toString();
                income = txtincome.getText().toString();
                address = txtAddress.getText().toString();
                pincode = txtPincode.getText().toString();
                aadhar = txtAadhar.getText().toString();
                pan = txtPan.getText().toString();

                int selectedGenderId = radioGender.getCheckedRadioButtonId();
                if (selectedGenderId != -1) {
                    RadioButton selectedRadioButton = findViewById(selectedGenderId);
                    gender = selectedRadioButton.getText().toString();
                }

                int selectedDisabledId = radioDisabled.getCheckedRadioButtonId();
                if (selectedDisabledId != -1) {
                    RadioButton selectedRadioButton = findViewById(selectedDisabledId);
                    disabled = selectedRadioButton.getText().toString();
                }

                fees = txtFees.getText().toString();
                SSCBoard = txtSSCBoard.getText().toString();
                SSCPY = txtSSCPY.getText().toString();
                SSCSchool = txtSSCSchool.getText().toString();
                SSCPercentage = txtSSCPercentage.getText().toString();
                HSCBoard = txtHSCBoard.getText().toString();
                HSCPY = txtHSCPY.getText().toString();
                HSCSchool = txtHSCSchool.getText().toString();
                HSCPercentage = txtHSCPercentage.getText().toString();
                BachUniversity = txtBachUniversity.getText().toString();
                BachDegree = txtBachDegree.getText().toString();
                backGY = txtbackGY.getText().toString();
                BachCgpa = txtBachCgpa.getText().toString();

                BankName = txtBankName.getText().toString();
                Branch = txtBranch.getText().toString();
                IFSC = txtIFSC.getText().toString();
                AccountNo = txtAccountNo.getText().toString();
                RTAccountNo = txtRTAccountNo.getText().toString();

                if (applicationID != null) {

                    if (!Objects.equals(name, "") && !Objects.equals(address, "") && !Objects.equals(pincode, "") && !Objects.equals(aadhar, "") && !Objects.equals(pan, "") && !Objects.equals(gender, "") && !Objects.equals(caste, "") && !Objects.equals(disabled, "") && !Objects.equals(dob, "") && !Objects.equals(occupation, "") && !Objects.equals(income, "")) {

                        Log.d("Inside If","Yes");
                        flagSave++;
                        PersonalInformationData personalInformationData = new PersonalInformationData(name, caste, gender, disabled, dob, address, occupation, income, pincode, aadhar, pan);

                        //Inside Applications
                        FirebaseDatabase.getInstance().getReference().child("Applications").child(String.valueOf(currentYear)).child(catagory).child(schemeName).child(applicationID).child("Personal Information").setValue(personalInformationData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d("Inside App","Yes");
                                Toast.makeText(SchemeApplication.this, "Personal Details Updated!!!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SchemeApplication.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        //Inside Users
                        FirebaseDatabase.getInstance().getReference().child("USERS").child(userName).child("Schemes").child(String.valueOf(currentYear)).child(applicationID).child("Personal Information").setValue(personalInformationData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d("Inside User","Yes");
//                                Toast.makeText(SchemeApplicationDetails.this, "Personal Details Updated!!!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SchemeApplication.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else {
                        Log.d("Inside else","Yes");
//                        Toast.makeText(SchemeApplication.this, name + " " + gender, Toast.LENGTH_SHORT).show();
//                        Log.d("name",name);
//                        Log.d("gender",gender);
//                        Log.d("dob",dob);
//                        Log.d("caste",caste);
//                        Log.d("address",address);
//                        Log.d("pincode",pincode);
//                        Log.d("aadhar",aadhar);
//                        Log.d("pan",pan);
//                        Log.d("occupation",occupation);
//                        Log.d("income",income);
//                        Log.d("disabled",disabled);

                    }

                    if (!Objects.equals(fees, "") && !Objects.equals(SSCBoard, "") && !Objects.equals(SSCPercentage, "") && !Objects.equals(SSCSchool, "") && !Objects.equals(SSCPY, "") && !Objects.equals(HSCBoard, "") && !Objects.equals(HSCPercentage, "") && !Objects.equals(HSCSchool, "") && !Objects.equals(HSCPY, "") && !Objects.equals(BachCgpa, "") && !Objects.equals(BachDegree, "") && !Objects.equals(BachUniversity, "") && !Objects.equals(backGY, "")) {
                        flagSave++;
                        EducationData educationData = new EducationData(fees, SSCBoard, SSCPY, SSCSchool, SSCPercentage, HSCBoard, HSCPY, HSCSchool, HSCPercentage, BachUniversity, BachDegree, backGY, BachCgpa);

                        //Inside Applications
                        FirebaseDatabase.getInstance().getReference().child("Applications").child(String.valueOf(currentYear)).child(catagory).child(schemeName).child(applicationID).child("Education Information").setValue(educationData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(SchemeApplication.this, "Education Details Updated!!!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SchemeApplication.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        //Inside Users
                        FirebaseDatabase.getInstance().getReference().child("USERS").child(userName).child("Schemes").child(String.valueOf(currentYear)).child(applicationID).child("Education Information").setValue(educationData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
//                                Toast.makeText(SchemeApplicationDetails.this, "Education Details Updated!!!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SchemeApplication.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    if (!Objects.equals(BankName, "") && !Objects.equals(Branch, "") && !Objects.equals(IFSC, "") && AccountNo!=null && !Objects.equals(RTAccountNo, "")) {

                        flagSave++;
                        BankData bankData = new BankData(BankName, Branch, IFSC, AccountNo, RTAccountNo);

                        //Inside Applications
                        FirebaseDatabase.getInstance().getReference().child("Applications").child(String.valueOf(currentYear)).child(catagory).child(schemeName).child(applicationID).child("Bank Information").setValue(bankData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(SchemeApplication.this, "Bank Details Updated!!!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SchemeApplication.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        //Inside Users
                        FirebaseDatabase.getInstance().getReference().child("USERS").child(userName).child("Schemes").child(String.valueOf(currentYear)).child(applicationID).child("Bank Information").setValue(bankData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
//                                Toast.makeText(SchemeApplicationDetails.this, "Bank Details Updated!!!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SchemeApplication.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    saveStatusDocument(flagSave);

                }else {
                    Toast.makeText(SchemeApplication.this, "Error Fetching Application Id!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCAadhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 1;
                selectfiles();
            }
        });

        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 2;
                selectfiles();
            }
        });

        btnIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 3;
                selectfiles();
            }
        });

        btnCaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 4;
                selectfiles();
            }
        });

        btnPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 5;
                selectfiles();
            }
        });

        btnBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 6;
                selectfiles();
            }
        });

        btnSsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 7;
                selectfiles();
            }
        });

        btnHsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 8;
                selectfiles();
            }
        });

        btnGraduation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 9;
                selectfiles();
            }
        });

        btnPan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 10;
                selectfiles();
            }
        });

        btnFeesReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 11;
                selectfiles();
            }
        });
//
//        btnVAadhar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(uriAadhar!=null) {
//
//                    Log.d("UriAAdahar",uriAadhar.toString());
//                    Toast.makeText(SchemeApplicationDetails.this, uriAadhar.toString(), Toast.LENGTH_SHORT).show();
//
//                    pdfView.fromUri(uriAadhar)
//                            .defaultPage(0) // Page to display by default (0 for the first page)
//                            .onLoad(new OnLoadCompleteListener() {
//                                @Override
//                                public void loadComplete(int nbPages) {
//                                    // PDF has been loaded
//                                    // You can add any additional logic here
//                                }
//                            })
//                            .load();
//
////                    Intent intent = new Intent(Intent.ACTION_VIEW);
////                    intent.setType("application/pdf");
////                    intent.setData(uriAadhar);
////                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////
////                    startActivity(intent);
//
//                }else {
//                    Toast.makeText(SchemeApplicationDetails.this, "Null Uri!!!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

    private void saveStatusDocument(int flag) {
        ValueEventListener valueEventListener = FirebaseDatabase.getInstance().getReference("Schemes").child(catagory).child(schemeName).child("document").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    int flagSave = flag;
                    List<String> document = new ArrayList<>();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        document.add(snapshot1.getValue(String.class));
                    }
                    int count=0;
                    if((document.contains("AadharCard") && aadharURL != null)){
                        count++;
                    }
                    if((document.contains("Photo") && photoURL != null)){
                        count++;
                    }
                    if((document.contains("Income Certificate") && incomeURL != null)){
                        count++;
                    }
                    if((document.contains("Caste Certificate") && casteURL != null)){
                        count++;
                    }
                    if((document.contains("PWD Certificate") && pwdURL != null)){
                        count++;
                    }
                    if((document.contains("Bank Passbook") && bankURL != null)){
                        count++;
                    }
                    if((document.contains("SSC") && sscURL != null)){
                        count++;
                    }
                    if((document.contains("HSC") && hscURL != null)){
                        count++;
                    }
                    if((document.contains("Graduation") && graduationURL != null)){
                        count++;
                    }
                    if((document.contains("PAN Card") && panURL != null)){
                        count++;
                    }
                    if((document.contains("Fees Receipt") && feesReceiptURL != null)){
                        count++;
                    }

                    if (document.size()==count) {

                        flagSave++;

                        saveApplication(flagSave);
                        DocumentsData documentsData = new DocumentsData(aadharURL, photoURL, panURL, casteURL, incomeURL, sscURL, hscURL, graduationURL, pwdURL, bankURL, feesReceiptURL);

                        //Inside Applications
                        FirebaseDatabase.getInstance().getReference().child("Applications").child(String.valueOf(currentYear)).child(catagory).child(schemeName).child(applicationID).child("Document Information").setValue(documentsData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(SchemeApplication.this, "Documents Updated!!!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SchemeApplication.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        //Inside Users
                        FirebaseDatabase.getInstance().getReference().child("USERS").child(userName).child("Schemes").child(String.valueOf(currentYear)).child(applicationID).child("Document Information").setValue(documentsData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SchemeApplication.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        if (flagSave>0) {

                            saveApplication(flag);

//                            flagSave=0;
//                            status = "Application Started";
//                            query = "Submission Pending!!!";
//                            transaction = "Pending";
//                            String date = "Not Applied";
//                            SchemesStatusData schemesStatusData = new SchemesStatusData(applicationID,schemeName,catagory, status, query,transaction, date);
//
//                            //Inside Applications
//                            FirebaseDatabase.getInstance().getReference().child("Applications").child(String.valueOf(currentYear)).child(catagory).child(schemeName).child(applicationID).child("Status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Toast.makeText(SchemeApplication.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                            FirebaseDatabase.getInstance().getReference().child("Applications").child(String.valueOf(currentYear)).child(catagory).child(schemeName).child(applicationID).child("SchemeStatusData").setValue(schemesStatusData).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Toast.makeText(SchemeApplication.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                            });
//
//                            //Inside Users
//                            FirebaseDatabase.getInstance().getReference().child("USERS").child(userName).child("Schemes").child(String.valueOf(currentYear)).child(applicationID).child("SchemeStatusData").setValue(schemesStatusData).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
////                                Toast.makeText(SchemeApplicationDetails.this, "Status Updated!!!", Toast.LENGTH_SHORT).show();
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Toast.makeText(SchemeApplication.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                            });
//
//                            //Username
//                            //Inside Applications
//                            FirebaseDatabase.getInstance().getReference().child("Applications").child(String.valueOf(currentYear)).child(catagory).child(schemeName).child(applicationID).child("UserName").setValue(userName).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Toast.makeText(SchemeApplication.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                            });

                        }else {
                            Toast.makeText(SchemeApplication.this, "All the Fields of Particular Section is Mandatory!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void saveSubmitStatus(int flag) {
        ValueEventListener valueEventListener = FirebaseDatabase.getInstance().getReference("Schemes").child(catagory).child(schemeName).child("document").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    int flagSubmit = flag;
                    List<String> document = new ArrayList<>();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        document.add(snapshot1.getValue(String.class));
                    }
                    int count=0;
                    if((document.contains("AadharCard") && aadharURL != null)){
                        count++;
                    }
                    if((document.contains("Photo") && photoURL != null)){
                        count++;
                    }
                    if((document.contains("Income Certificate") && incomeURL != null)){
                        count++;
                    }
                    if((document.contains("Caste Certificate") && casteURL != null)){
                        count++;
                    }
                    if((document.contains("PWD Certificate") && pwdURL != null)){
                        count++;
                    }
                    if((document.contains("Bank Passbook") && bankURL != null)){
                        count++;
                    }
                    if((document.contains("SSC") && sscURL != null)){
                        count++;
                    }
                    if((document.contains("HSC") && hscURL != null)){
                        count++;
                    }
                    if((document.contains("Graduation") && graduationURL != null)){
                        count++;
                    }
                    if((document.contains("PAN Card") && panURL != null)){
                        count++;
                    }
                    if((document.contains("Fees Receipt") && feesReceiptURL != null)){
                        count++;
                    }

                    if (document.size()==count) {

                        flagSubmit++;

                        submitApplication(flagSubmit);
                        DocumentsData documentsData = new DocumentsData(aadharURL, photoURL, panURL, casteURL , incomeURL, sscURL,  hscURL, graduationURL, pwdURL, bankURL,feesReceiptURL);

                        //Inside Applications
                        FirebaseDatabase.getInstance().getReference().child("Applications").child(String.valueOf(currentYear)).child(catagory).child(schemeName).child(applicationID).child("Document Information").setValue(documentsData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(SchemeApplication.this, "Documents Updated!!!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SchemeApplication.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        //Inside Users
                        FirebaseDatabase.getInstance().getReference().child("USERS").child(userName).child("Schemes").child(String.valueOf(currentYear)).child(applicationID).child("Document Information").setValue(documentsData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
//                                Toast.makeText(SchemeApplicationDetails.this, "Bank Details Updated!!!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SchemeApplication.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        Toast.makeText(SchemeApplication.this, "All the Fields are Mandatory!!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void saveApplication(int flagSave) {
        if (flagSave>0) {
            flagSave=0;
            status = "Application Started";
            query = "Submission Pending!!!";
            transaction = "Pending";
            String date = "Not Applied";
            SchemesStatusData schemesStatusData = new SchemesStatusData(applicationID,schemeName,catagory, status, query,transaction, date);

            //Inside Applications
            FirebaseDatabase.getInstance().getReference().child("Applications").child(String.valueOf(currentYear)).child(catagory).child(schemeName).child(applicationID).child("Status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SchemeApplication.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            FirebaseDatabase.getInstance().getReference().child("Applications").child(String.valueOf(currentYear)).child(catagory).child(schemeName).child(applicationID).child("SchemeStatusData").setValue(schemesStatusData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SchemeApplication.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            //Inside Users
            FirebaseDatabase.getInstance().getReference().child("USERS").child(userName).child("Schemes").child(String.valueOf(currentYear)).child(applicationID).child("SchemeStatusData").setValue(schemesStatusData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
//                                Toast.makeText(SchemeApplicationDetails.this, "Status Updated!!!", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SchemeApplication.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            //Username
            //Inside Applications
            FirebaseDatabase.getInstance().getReference().child("Applications").child(String.valueOf(currentYear)).child(catagory).child(schemeName).child(applicationID).child("UserName").setValue(userName).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SchemeApplication.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            Intent intent = new Intent(SchemeApplication.this, Home.class);
            intent.putExtra("keySubmit","Save");
            startActivity(intent);
            finish();

        }else {
            Toast.makeText(SchemeApplication.this, "All the Fields of Particular Section is Mandatory!!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void submitApplication(int flagSubmit) {
        if(flagSubmit==4) {
            status = "Submitted";
            query = "Application Under Process...";
            transaction = "Pending";
            String date =  DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

            flagSubmit=0;
            SchemesStatusData schemesStatusData = new SchemesStatusData(applicationID,schemeName,catagory, status, query,transaction, date);
            //Inside Applications
            FirebaseDatabase.getInstance().getReference().child("Applications").child(String.valueOf(currentYear)).child(catagory).child(schemeName).child(applicationID).child("Status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SchemeApplication.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            FirebaseDatabase.getInstance().getReference().child("Applications").child(String.valueOf(currentYear)).child(catagory).child(schemeName).child(applicationID).child("SchemeStatusData").setValue(schemesStatusData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SchemeApplication.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            //Inside Users
            FirebaseDatabase.getInstance().getReference().child("USERS").child(userName).child("Schemes").child(String.valueOf(currentYear)).child(applicationID).child("SchemeStatusData").setValue(schemesStatusData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
//                                Toast.makeText(SchemeApplicationDetails.this, "Status Updated!!!", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SchemeApplication.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            //Username
            //Inside Applications
            FirebaseDatabase.getInstance().getReference().child("Applications").child(String.valueOf(currentYear)).child(catagory).child(schemeName).child(applicationID).child("UserName").setValue(userName).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SchemeApplication.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            Intent intent = new Intent(SchemeApplication.this, Home.class);
            intent.putExtra("keySubmit","Submit");
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(SchemeApplication.this, "All the Fields are Mandatory!!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void setDocuments() {
        ValueEventListener valueEventListener;
        valueEventListener = FirebaseDatabase.getInstance().getReference("Schemes").child(catagory).child(schemeName).child("document").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    List<String> document = new ArrayList<>();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                        document.add(snapshot1.getValue(String.class));
                    }
                    setDocumentData(document);
                    if(!document.contains("AadharCard")){
                        LinearLayout layout = findViewById(R.id.LLAadharCard);
                        layout.setVisibility(View.GONE);
                    }else{
                        LinearLayout layout = findViewById(R.id.LLAadharCard);
                        layout.setVisibility(View.VISIBLE);
                    }
                    if(!document.contains("Photo")){
                        LinearLayout layout = findViewById(R.id.LLPhoto);
                        layout.setVisibility(View.GONE);
                    } else{
                        LinearLayout layout = findViewById(R.id.LLPhoto);
                        layout.setVisibility(View.VISIBLE);
                    }
                    if(!document.contains("Income Certificate")){
                        LinearLayout layout = findViewById(R.id.LLIncomeCertificate);
                        layout.setVisibility(View.GONE);
                    }else{
                        LinearLayout layout = findViewById(R.id.LLIncomeCertificate);
                        layout.setVisibility(View.VISIBLE);
                    }
                    if(!document.contains("Caste Certificate")){
                        LinearLayout layout = findViewById(R.id.LLCasteCertificate);
                        layout.setVisibility(View.GONE);
                    }else{
                        LinearLayout layout = findViewById(R.id.LLCasteCertificate);
                        layout.setVisibility(View.VISIBLE);
                    }
                    if(!document.contains("Bank Passbook")){
                        LinearLayout layout = findViewById(R.id.LLBankPassbook);
                        layout.setVisibility(View.GONE);
                    }else{
                        LinearLayout layout = findViewById(R.id.LLBankPassbook);
                        layout.setVisibility(View.VISIBLE);
                    }
                    if(!document.contains("SSC")){
                        LinearLayout layout = findViewById(R.id.LLSSC);
                        layout.setVisibility(View.GONE);
                    }else{
                        LinearLayout layout = findViewById(R.id.LLSSC);
                        layout.setVisibility(View.VISIBLE);
                    }
                    if(!document.contains("PAN Card")){
                        LinearLayout layout = findViewById(R.id.LLPAN);
                        layout.setVisibility(View.GONE);
                    }else{
                        LinearLayout layout = findViewById(R.id.LLPAN);
                        layout.setVisibility(View.VISIBLE);
                    }
                    if(!document.contains("Fees Receipt")){
                        LinearLayout layout = findViewById(R.id.LLFeesReceipt);
                        layout.setVisibility(View.GONE);
                    }else{
                        LinearLayout layout = findViewById(R.id.LLFeesReceipt);
                        layout.setVisibility(View.VISIBLE);
                    }
                    if(!document.contains("HSC")){
                        LinearLayout layout = findViewById(R.id.LLHSC);
                        layout.setVisibility(View.GONE);
                    }else{
                        LinearLayout layout = findViewById(R.id.LLHSC);
                        layout.setVisibility(View.VISIBLE);
                    }
                    if(!document.contains("PWD Certificate")){
                        LinearLayout layout = findViewById(R.id.LLPWDCertificate);
                        layout.setVisibility(View.GONE);
                    }else{
                        LinearLayout layout = findViewById(R.id.LLPWDCertificate);
                        layout.setVisibility(View.VISIBLE);
                    }
                    if(!document.contains("Graduation")){
                        LinearLayout layout = findViewById(R.id.LLGraduation);
                        layout.setVisibility(View.GONE);
                    }else{
                        LinearLayout layout = findViewById(R.id.LLGraduation);
                        layout.setVisibility(View.VISIBLE);
                    }

                } else{
                    Toast.makeText(SchemeApplication.this, "No Schemes Available!!!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setDocumentData(List<String> data) {
        documentsList = data;
    }

    private void fetchData() {

        ValueEventListener valueEventListener = FirebaseDatabase.getInstance().getReference("Applications").child(String.valueOf(currentYear)).child(catagory).child(schemeName).child(applicationID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (dataSnapshot.getKey().equals("Personal Information")) {
                            PersonalInformationData personalInformationData = dataSnapshot.getValue(PersonalInformationData.class);

//                            Log.d("Personal Info", personalInformationData.getAadhar());
                            txtName.setText(personalInformationData.getName());
                            txtAddress.setText(personalInformationData.getAddress());
                            txtAadhar.setText(personalInformationData.getAadhar());
                            txtDateOfBirth.setText(personalInformationData.getDob());
                            txtPincode.setText(personalInformationData.getPincode());
                            txtPan.setText(personalInformationData.getPan());
                            txtOccupation.setText(personalInformationData.getOccupation());
                            txtincome.setText(personalInformationData.getIncome());

                            for (int i = 0; i < radioGender.getChildCount(); i++) {
                                View view = radioGender.getChildAt(i);
                                if (view instanceof RadioButton) {
                                    RadioButton radioButton = (RadioButton) view;
                                    if (radioButton.getText().toString().equals(personalInformationData.getGender())) {
                                        radioButton.setChecked(true);
                                        break; // Stop looping once the desired radio button is found and checked
                                    }
                                }
                            }

                            for (int i = 0; i < radioDisabled.getChildCount(); i++) {
                                View view = radioDisabled.getChildAt(i);
                                if (view instanceof RadioButton) {
                                    RadioButton radioButton = (RadioButton) view;
                                    if (radioButton.getText().toString().equals(personalInformationData.getDisabled())) {
                                        radioButton.setChecked(true);
                                        break; // Stop looping once the desired radio button is found and checked
                                    }
                                }
                            }

                            for (int i = 0; i < casteOptions.length; i++) {
                                if (casteOptions[i].equals(personalInformationData.getCaste())) {
                                    spinner.setSelection(i);
                                    break; // Stop looping once "OBC" is found and selected
                                }
                            }

                            dob = personalInformationData.getDob();
                            caste = personalInformationData.getCaste();

                        } else if (dataSnapshot.getKey().equals("Bank Information")) {

                            BankData bankData = dataSnapshot.getValue(BankData.class);

                            txtBankName.setText(bankData.getBankName());
                            txtBranch.setText(bankData.getBranch());
                            txtIFSC.setText(bankData.getIFSC());
                            txtAccountNo.setText(bankData.getAccountNo());
                            txtRTAccountNo.setText(bankData.getRTAccountNo());

                        } else if (dataSnapshot.getKey().equals("Education Information")) {

                            EducationData educationData = dataSnapshot.getValue(EducationData.class);

                            txtFees.setText(educationData.getFees());
                            txtSSCBoard.setText(educationData.getSSCBoard());
                            txtSSCPY.setText(educationData.getSSCPY());
                            txtSSCSchool.setText(educationData.getSSCSchool());
                            txtSSCPercentage.setText(educationData.getSSCPercentage());
                            txtHSCBoard.setText(educationData.getHSCBoard());
                            txtHSCPY.setText(educationData.getHSCPY());
                            txtHSCSchool.setText(educationData.getHSCSchool());
                            txtHSCPercentage.setText(educationData.getHSCPercentage());
                            txtBachUniversity.setText(educationData.getBachUniversity());
                            txtBachDegree.setText(educationData.getBachDegree());
                            txtbackGY.setText(educationData.getBackGY());
                            txtBachCgpa.setText(educationData.getBachCgpa());

                        } else if (dataSnapshot.getKey().equals("Document Information")) {

                            DocumentsData documentsData = dataSnapshot.getValue(DocumentsData.class);

                            Log.e("Aadhar Before: ",documentsData.getAadhar());
                            if(documentsData!=null) {
                                setDocumentsData(documentsData);
                            }
                        }else{
                            Log.e("Error: ","No Data");
                        }
                    }
                }else{
                    Toast.makeText(SchemeApplication.this, "No Reference Exist", Toast.LENGTH_SHORT).show();
//                    Log.d("Fetchdata","No Reference Exist");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference("USERS").removeEventListener(valueEventListener);
    }

    private void setDocumentsData(DocumentsData documentsData) {

        if(documentsList.contains("AadharCard")){
            aadharURL= documentsData.getAadhar();
            Log.e("Aadhar", aadharURL);
        }
        if(documentsList.contains("Photo")){
            photoURL = documentsData.getPhoto();
        }
        if(documentsList.contains("Income Certificate")){
            incomeURL = documentsData.getIncome();
        }
        if(documentsList.contains("Caste Certificate")){
            casteURL= documentsData.getCaste();
        }
        if(documentsList.contains("Bank Passbook")){
            bankURL = documentsData.getBank();
        }
        if(documentsList.contains("SSC")){
            sscURL = documentsData.getSsc();
        }
        if(documentsList.contains("PAN Card")){
            panURL = documentsData.getPan();
        }
        if(documentsList.contains("Fees Receipt")){
            feesReceiptURL = documentsData.getFeesReceipt();
        }
        if(documentsList.contains("HSC")){
            hscURL = documentsData.getHsc();
        }
        if(documentsList.contains("PWD Certificate")){
            pwdURL = documentsData.getPwd();
        }
        if(documentsList.contains("Graduation")){
            graduationURL = documentsData.getGraduation();
        }
    }

    public void selectfiles() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent, FILE_PICKER_REQUEST_CODE);
        //intent.setAction(Intent.ACTION_GET_CONTENT);
        //startActivityForResult(Intent.createChooser(intent,"Select PDF Files..."),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            UploadFiles(data.getData());
        }
    }

    private void UploadFiles(Uri data) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        StorageReference reference = storageReference.child( applicationID + "_"+ System.currentTimeMillis() + ".pdf");
        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                Uri url = null;
                while (!uriTask.isComplete()) ;
                url = uriTask.getResult();

                if (flag == 1) {
                    aadharURL = String.valueOf(url);
                    btnCAadhar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.light_blue));
                    flag = 0;
                } else if (flag == 2) {
                    photoURL = String.valueOf(url);
                    btnPhoto.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.light_blue));
                    flag = 0;
                } else if (flag == 3) {
                    incomeURL = String.valueOf(url);
                    btnIncome.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.light_blue));
                    flag = 0;
                } else if (flag == 4) {
                    casteURL = String.valueOf(url);
                    btnCaste.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.light_blue));
                    flag = 0;
                } else if (flag == 5) {
                    pwdURL = String.valueOf(url);
                    btnPwd.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.light_blue));
                    flag = 0;
                } else if (flag == 6) {
                    bankURL = String.valueOf(url);
                    btnBank.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.light_blue));
                    flag = 0;
                } else if (flag == 7) {
                    sscURL = String.valueOf(url);
                    btnSsc.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.light_blue));
                    flag = 0;
                } else if (flag == 8) {
                    hscURL = String.valueOf(url);
                    btnHsc.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.light_blue));
                    flag = 0;
                } else if (flag == 9) {
                    graduationURL = String.valueOf(url);
                    btnGraduation.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.light_blue));
                    flag = 0;
                } else if (flag == 10) {
                    panURL = String.valueOf(url);
                    btnPan.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.light_blue));
                    flag = 0;
                } else if (flag == 11) {
                    feesReceiptURL = String.valueOf(url);
                    btnFeesReceipt.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.light_blue));
                    flag = 0;
                }
                progressDialog.dismiss();

                Toast.makeText(SchemeApplication.this, "Successfully Uploaded!!", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = (100.0 * snapshot.getBytesTransferred()) / (snapshot.getTotalByteCount());
                progressDialog.setMessage("Uploaded: " + (int) progress + "%");
            }
        });
    }

    public void showDatePickerDialog(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Format the selected date and set it in the text field
                String formattedDate = (month + 1) + "/" + dayOfMonth + "/" + year;
                txtDateOfBirth.setText(formattedDate);
                dob = formattedDate;
            }
        }, 2000, 0, 1); // Set initial date (you can adjust this as needed)

        datePickerDialog.show();
    }
}