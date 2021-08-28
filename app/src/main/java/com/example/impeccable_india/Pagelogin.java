package com.example.impeccable_india;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Pagelogin extends AppCompatActivity {
    private Button button,button2;
    private EditText email,confirmpassword;
    FirebaseAuth fauth ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        email =(EditText)findViewById(R.id.EmailidRG);
        confirmpassword =(EditText)findViewById(R.id.Password3);
        button2 =(Button)findViewById(R.id.button);
        fauth = FirebaseAuth.getInstance();



        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_email = email.getText().toString().trim();
                String user_password =confirmpassword.getText().toString().trim();
              fauth.signInWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Pagelogin.this, "login done", Toast.LENGTH_LONG).show();
                         Intent intent = new Intent(Pagelogin.this,froupage.class);
                         startActivity(intent);

                    }else {
                        Toast.makeText(Pagelogin.this, "user is not register", Toast.LENGTH_LONG).show();

                    }
                  }
              });
            }
        });


        Button button=(Button)findViewById(R.id.signup);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opensignupPage();
            }
        });
    }
    public void opensignupPage(){
        Intent intent1=new Intent(this,signupPage.class);
        startActivity(intent1);
    }
}