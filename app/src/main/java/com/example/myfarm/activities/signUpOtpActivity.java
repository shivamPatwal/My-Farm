package com.example.myfarm.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.myfarm.modals.Profile;
import com.example.myfarm.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class signUpOtpActivity extends AppCompatActivity {
    private TextView myphone;
    private FirebaseAuth mAuth;
    private EditText otp1;
    private EditText otp2;
    private EditText otp3;
    private EditText otp4;
    private EditText otp5;
    private EditText otp6;
    private Button mbutton;
    String otpid;
    String messa;
    String userName;
    String state;
    String emailid;
    String download;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Profile profileInfo;
    ProgressDialog pd;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        myphone = findViewById(R.id.textView7);
        otp1=findViewById(R.id.firstblock);
        otp2=findViewById(R.id.secondblock);
        otp3=findViewById(R.id.thirdblock);
        otp4=findViewById(R.id.fourthblock);
        otp5=findViewById(R.id.fifthblock);
        otp6=findViewById(R.id.sixthblock);
        mbutton=findViewById(R.id.login);
      pd=new ProgressDialog(this);
      textView=findViewById(R.id.welcome);

        Bundle bundle = getIntent().getExtras();
         messa = bundle.getString("phoneNumber");
        userName = bundle.getString("profileName");
        state = bundle.getString("stateName");
         emailid = bundle.getString("emailId");
        download = bundle.getString("down");



        String show = "";
        if (!messa.isEmpty()) {
            show = "+91 " + messa;

        }
        myphone.setText(show);
        textView.setText("Welcome "+userName);
        textView.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.purple_500));
        initiateOtp(show);
        profileInfo=new Profile();
        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(otp1.getText().toString().isEmpty() || otp2.getText().toString().isEmpty() || otp3.getText().toString().isEmpty()
                        || otp4.getText().toString().isEmpty() || otp5.getText().toString().isEmpty() || otp6.getText().toString().isEmpty()){
                    Toast.makeText(signUpOtpActivity.this,"Please Enter OTP",Toast.LENGTH_SHORT).show();
                }else {
                    profileInfo.setContactNumber(messa);
                    profileInfo.setName(userName);
                    profileInfo.setEmailAddress(emailid);
                    profileInfo.setState(state);
                    profileInfo.setDownload(download);


                    String otp=otp1.getText().toString()+otp2.getText().toString()+otp3.getText().toString()+otp4.getText().toString()+otp5.getText().toString()+otp6.getText().toString();

                    verifyCode(otpid,otp);
                 pd.setMessage("Please Wait");
                 pd.show();

                }

            }
        });




    }

    private void initiateOtp(String show) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(show)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                otpid=s;
                            }

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                final String code = phoneAuthCredential.getSmsCode();



                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(signUpOtpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


    private void signInWithCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // if the code is correct and the task is successful
                            // we are sending our user to new activity.

                            String uid=mAuth.getCurrentUser().getUid();
                            profileInfo.setUid(uid);
                            databaseReference= firebaseDatabase.getReference("users").child(uid);

                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                               databaseReference.setValue(profileInfo);

                                    Intent i = new Intent(signUpOtpActivity.this, MainActivity.class);
                                    startActivity(i);
                                    finish();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    pd.dismiss();
                                    Toast.makeText(signUpOtpActivity.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();

                                }
                            });


                        } else {
                            // if the code is not correct then we are
                            // displaying an error message to the user.

                            Toast.makeText(signUpOtpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });


    }

    private void verifyCode(String otpid,String code) {
        // below line is used for getting getting
        // credentials from our verification id and code.
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otpid, code);

        // after getting credential we are
        // calling sign in method.
        signInWithCredential(credential);
    }

}
