package com.android.q1learningapp.Common.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.q1learningapp.Common.FrontScreen;
import com.android.q1learningapp.Databases.UserHelperClass;
import com.android.q1learningapp.R;
import com.android.q1learningapp.Users.UserDashboard;
import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class VerifyOTP extends AppCompatActivity {

    String fullName, email, gender, dob,  phoneNo, password, whatToDO;
    TextView otpDescriptionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_o_t_p);


        otpDescriptionText = findViewById(R.id.otp_description_text);
        //Get all the data from Intent
        fullName = getIntent().getStringExtra("fullName");
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        gender = getIntent().getStringExtra("gender");
        dob = getIntent().getStringExtra("dob");
        phoneNo = getIntent().getStringExtra("phoneNo");
        whatToDO = getIntent().getStringExtra("whatToDO");

        otpDescriptionText.setText("Registered Using a Phone Number: "+phoneNo);

        if (whatToDO.equals("createNewUser")) {
            storeNewUsersData();
            //Toast.makeText(VerifyOTP.this, "Verification Completed", Toast.LENGTH_SHORT).show();
        }
    }

    private void storeNewUsersData() {

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("Users");

        UserHelperClass addNewUser = new UserHelperClass(fullName, email, phoneNo, password, dob, gender);
        reference.child(phoneNo).setValue(addNewUser);

    }

    public void goToHomeFromOTP(View view) {
        Intent intent = new Intent(getApplicationContext(), FrontScreen.class);
        startActivity(intent);
    }

    public void callNextScreenFromOTP(View view) {
        Intent intent = new Intent(getApplicationContext(), FrontScreen.class);
        startActivity(intent);
    }
}