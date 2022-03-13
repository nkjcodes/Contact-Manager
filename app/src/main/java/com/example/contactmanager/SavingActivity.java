package com.example.contactmanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SavingActivity extends AppCompatActivity {

    private EditText mname,mphone,memail;
    private Button save,view;

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving);

        mname = findViewById(R.id.editText_name);
        mphone = findViewById(R.id.editText_phone);
        memail = findViewById(R.id.editText_email);
        save = findViewById(R.id.button_save);
        view = findViewById(R.id.button_view);

        ProgressDialog progressDialog = new ProgressDialog(this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Please wait while Saving!!");
                progressDialog.setTitle("Saving");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                String name = mname.getText().toString();
                String phone = mphone.getText().toString();
                String email = memail.getText().toString();

                if((TextUtils.isEmpty(name)) || (TextUtils.isEmpty(phone)) || (TextUtils.isEmpty(email))){
                    progressDialog.dismiss();
                    Toast.makeText(SavingActivity.this,"No empty field is allowed.!!",Toast.LENGTH_SHORT).show();
                    return;
                }

                else if(phone.length()!=10){
                    progressDialog.dismiss();
                    Toast.makeText(SavingActivity.this,"Phone Number is not valid",Toast.LENGTH_SHORT).show();
                    return;
                }

                HashMap<String,String> userMap = new HashMap<>();

                userMap.put("name",name);
                userMap.put("phone",phone);
                userMap.put("email",email);

                root.push().setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        Toast.makeText(SavingActivity.this,"Contact Saved Successfully",Toast.LENGTH_SHORT).show();

                        mname.setText("");
                        mphone.setText("");
                        memail.setText("");
                    }
                });

            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SavingActivity.this,ViewActivity.class));
            }
        });
    }
}