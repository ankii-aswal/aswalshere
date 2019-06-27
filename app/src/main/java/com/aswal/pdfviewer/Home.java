package com.aswal.pdfviewer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {
    EditText txtEmail,txtPassword;
    Button btn_login;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Login Form");

        txtEmail=(EditText)findViewById(R.id.txt_emaillogin);
        txtPassword=(EditText)findViewById(R.id.txt_passwordlogin);
        btn_login=(Button) findViewById(R.id.buttonLogin);

        firebaseAuth=FirebaseAuth.getInstance();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=txtEmail.getText().toString().trim();
                String password=txtPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)) //TextUtils checks if the coreesponding field is empty
                    Toast.makeText(Home.this,"Enter email",Toast.LENGTH_SHORT).show();

                if(TextUtils.isEmpty(password)) //TextUtils checks if the coreesponding field is empty
                    Toast.makeText(Home.this,"Enter password",Toast.LENGTH_SHORT).show();

                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Home.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                } else {
                                    Toast.makeText(Home.this,"Login failed",Toast.LENGTH_SHORT).show();
                                }


                            }
                        });
            }
        });
    }

    // public void btn_signupForm(View view) {
    // Intent intent=new Intent(getApplicationContext(),Signup.class);
    // startActivity(intent);
    // }
}
