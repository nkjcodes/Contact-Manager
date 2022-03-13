package com.example.contactmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.ktx.Firebase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity2 extends AppCompatActivity {

    EditText txtUsername;
    EditText txtPassword;
    Button btnSignup;
    String regularExpression = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!])[A-Za-z\\d@$!]{8,}$";

    FirebaseAuth mAuth;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.signup);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        TextView textView = findViewById(R.id.textView_SignIn);

        String text= (String) textView.getText();

        SpannableString ss = new SpannableString(text);

        ForegroundColorSpan fcBlue = new ForegroundColorSpan(Color.BLUE);

        ss.setSpan(fcBlue,24,32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        StyleSpan bold = new StyleSpan(Typeface.BOLD);

        ss.setSpan(bold,24,32,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(ss);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

        txtUsername = (EditText) findViewById(R.id.signUp_email);
        txtPassword = (EditText) findViewById(R.id.signUp_pass);
        btnSignup = (Button) findViewById(R.id.button_signUp);
        ProgressDialog progressDialog = new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();


        btnSignup.setOnClickListener(v -> {
            String username = txtUsername.getText().toString();
            String password = txtPassword.getText().toString();
            if (validatePassword(password)) {
                progressDialog.setMessage("Please wait while Signing Up!!");
                progressDialog.setTitle("SignUp");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                mAuth.createUserWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity2.this,"Sign Up Successful",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity2.this, SavingActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity2.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            } else {
                Toast.makeText(getBaseContext(), "Invalid Password", Toast.LENGTH_LONG).show();
            }
        });

    }

    public boolean validatePassword(String password)
    {
        Pattern pattern = Pattern.compile(regularExpression);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}