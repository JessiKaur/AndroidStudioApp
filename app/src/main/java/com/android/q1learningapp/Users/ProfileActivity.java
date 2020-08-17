package com.android.q1learningapp.Users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.q1learningapp.Common.FrontScreen;
import com.android.q1learningapp.Common.LoginSignup.SignUpActivity;
import com.android.q1learningapp.Common.LoginSignup.VerifyOTP;
import com.android.q1learningapp.Databases.SessionManager;
import com.android.q1learningapp.R;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.Calendar;
import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {
    ImageView backBtn;
    DatePicker datePicker;
    RadioGroup radioGroup;
    RadioButton radioButton;
    CountryCodePicker countryCodePicker;
    TextInputLayout fullName, email_ID , phone_number, passWord;
    Button updateBtn;
    String _FULL_NAME, _PASSWORD, _USERID, _EMAIL, _GENDER, _DOB;


    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;
    // Get a reference to our posts
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile);



        //Hooks
        radioGroup = findViewById(R.id.profile_radio_group);
        datePicker = findViewById(R.id.profile_age_picker);
        fullName = findViewById(R.id.profile_fullname);
        email_ID = findViewById(R.id.profile_emailId);
        passWord = findViewById(R.id.profile_password);
        countryCodePicker = findViewById(R.id.country_code_picker);
        phone_number = findViewById(R.id.profile_phone_number);
        updateBtn = findViewById(R.id.profile_update_button);




        backBtn = findViewById(R.id.backButtonPress);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, UserDashboard.class);
                startActivity(intent);
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update(view);
            }
        });

        setValues();

    }

    private void setValues() {
        //Start Session
        SessionManager sessionManager = new SessionManager(ProfileActivity.this);
        HashMap<String, String> userDetails = sessionManager.getUserDetailsFromSession();

        //Get all the data from Session
        final String _userId = userDetails.get(SessionManager.KEY_PHONE_NUMBER);
        final String _password = userDetails.get(SessionManager.KEY_PASSWORD);

        Query reference = database.getReference("Users").orderByChild("phoneNo").equalTo(_userId);

        // Attach a listener to read the data at our posts reference
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    _FULL_NAME = dataSnapshot.child(_userId).child("fullName").getValue(String.class);
                    _USERID = dataSnapshot.child(_userId).child("phoneNo").getValue(String.class);
                    _EMAIL = dataSnapshot.child(_userId).child("email").getValue(String.class);
                    _PASSWORD = dataSnapshot.child(_userId).child("password").getValue(String.class);
                    _GENDER = dataSnapshot.child(_userId).child("gender").getValue(String.class);
                    _DOB = dataSnapshot.child(_userId).child("date").getValue(String.class);
                    fullName.getEditText().setText(_FULL_NAME);
                    phone_number.getEditText().setText(_USERID);
                    email_ID.getEditText().setText(_EMAIL);
                    passWord.getEditText().setText(_PASSWORD);
                    //Set Gender
                    setGender(_GENDER);
                    //Set date
                    setDate(_DOB);
                }else{

                    Toast.makeText(ProfileActivity.this, "No Result Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }

    private void setDate(String dob){

        String[] split = dob.split("/");
        String date = split[0];
        String month = split[1];
        String year = split[2];
        datePicker.updateDate(Integer.parseInt(year), Integer.parseInt(month)-1, Integer.parseInt(date));
    }

    private void setGender(String gender) {
       if(gender.equals("Male")){
           RadioButton radioButton = findViewById(R.id.male);
           radioButton.setChecked(true);
       }
       else if(gender.equals("Female")){
           RadioButton radioButton = findViewById(R.id.female);
           radioButton.setChecked(true);
       }
       else{
           if(gender.equals("Other")){
               RadioButton radioButton = findViewById(R.id.others);
               radioButton.setChecked(true);
           }
       }
    }

    private void update(View view) {
        if(isNameChanged() || isPasswordChanged() || isGenderChanged() || isDobChanged()) {
            Toast.makeText(this, R.string.updated_data, Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, R.string.some_error_found, Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isPasswordChanged(){
        if(!_PASSWORD.equals(passWord.getEditText().getText().toString()))
        {
            if(isValid()){
                myRef.child(_USERID).child("password").setValue(passWord.getEditText().getText().toString());
                return true;
            }
            return false;
        }else{
            return false;
        }
    }
    private boolean isNameChanged(){
        if(!_FULL_NAME.equals(fullName.getEditText().getText().toString())){
            if(isValid()){
                myRef.child(_USERID).child("fullName").setValue(fullName.getEditText().getText().toString());
                return true;
            }
            return false;
        }else{
            return false;
        }
    }
    private boolean isDobChanged(){
        String get_dob = getDateOfBirth();
        if(!_DOB.equals(get_dob)){
            if(validateAge()){
                myRef.child(_USERID).child("date").setValue(get_dob);
                return true;
            }
            return false;
        }else{
            return false;
        }
    }
    private boolean isGenderChanged(){
        String user_gender = getUserGender();
        if(!_GENDER.equals(user_gender)){
            if(validateGender()){
                myRef.child(_USERID).child("gender").setValue(user_gender);
                return true;
            }
           return false;
        }else{
            return false;
        }
    }

    /**Validation **/
    private String getUserGender() {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        radioButton = (RadioButton) findViewById(selectedId);
        return (String) radioButton.getText();
    }


    private String getDateOfBirth() {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();
        return day + "/" + month + "/" + year;
    }


    /*** Validations ***/

    private boolean validateGender() {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean isValid(){
        //Initializing awesomeValidation object
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        //Adding validation to editTexts
        awesomeValidation.addValidation(this, R.id.profile_fullname, "[a-zA-Z\\s]+", R.string.firstNameError);
        awesomeValidation.addValidation(this, R.id.profile_password, "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}", R.string.PasswordError);

        if (awesomeValidation.validate()) {
            return true;
        }
        else{
            return false;
        }
    }

    private boolean validateAge() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = datePicker.getYear();
        int isAgeValid = currentYear - userAge;

        if (isAgeValid < 16) {
            Toast.makeText(this, R.string.age_validation_error, Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }
    /*** Validations ***/


}