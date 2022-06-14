package com.example.myfarm.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.myfarm.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class startActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button continueButton;
    private EditText phoneNumber;
    private ImageView gmailButton;
    private View line;
    private TextView signUpButton;

    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        continueButton = findViewById(R.id.continu);
        gmailButton = findViewById(R.id.gmail);
        signUpButton = findViewById(R.id.signup);
        phoneNumber = findViewById(R.id.phoneno);
        line = findViewById(R.id.view1);
  mAuth=FirebaseAuth.getInstance();

        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (phoneNumber.getText().toString().length() < 10 || phoneNumber.getText().toString().length() > 10) {
                    line.setBackgroundColor(Color.RED);
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (phoneNumber.getText().toString().length() < 10 || phoneNumber.getText().toString().length() > 10) {
                    line.setBackgroundColor(Color.RED);
                }

                if (phoneNumber.getText().toString().length() == 10) {
                    line.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.purple_500));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (phoneNumber.getText().toString().length() < 10 || phoneNumber.getText().toString().length() > 10) {
                    line.setBackgroundColor(Color.RED);
                }

                if (phoneNumber.getText().toString().length() == 10) {
                    line.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.purple_500));
                }
            }
        });









        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (phoneNumber.getText().toString().length() < 10) {
                    Toast.makeText(startActivity.this, "Phone no can't be less than 10", Toast.LENGTH_SHORT).show();
                } else if (phoneNumber.getText().toString().length() > 10) {
                    Toast.makeText(startActivity.this, "Phone no can't be greater than 10", Toast.LENGTH_SHORT).show();
                } else {

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String str = "+91" + phoneNumber.getText().toString();

                    if (str.equals(user.getPhoneNumber())) {

                        Intent i = new Intent(startActivity.this, LoginActivity.class);
                        i.putExtra("mess", phoneNumber.getText().toString());
                        startActivity(i);

                    } else {

                        Toast.makeText(startActivity.this, "There is no account with this Phone Number", Toast.LENGTH_SHORT).show();
                    }

                }


            } });




        gmailButton.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){
            Intent intent = new Intent(startActivity.this, MainActivity.class);
            startActivity(intent);

//            Toast.makeText(startActivity.this, "gmail done", Toast.LENGTH_SHORT).show();
    }
    });


        signUpButton.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){
     Intent i=new Intent(startActivity.this, SignUpActivity.class);
     startActivity(i);
    }
    });


}
/*
    @Override
    protected void onStart() {

        if(mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(startActivity.this, MainActivity.class);
            startActivity(intent);
        }


        super.onStart();
    }
*/
}
