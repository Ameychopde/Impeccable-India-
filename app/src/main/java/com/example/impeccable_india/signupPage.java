package com.example.impeccable_india;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.impeccable_india.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signupPage extends AppCompatActivity {
    EditText email, password , confirmpassword,name;
    Button signup ,signin;
     FirebaseAuth firebaseAuth;
     DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);
        setupUIViews();

        firebaseAuth = FirebaseAuth.getInstance();
        reference =FirebaseDatabase.getInstance().getReference().child("users");



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   String user_email = email.getText().toString().trim();
                   String user_password = confirmpassword.getText().toString().trim();
                   String Name = name.getText().toString().trim();


                   firebaseAuth.createUserWithEmailAndPassword(user_email,user_password)
                                 .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()) {


                               FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                               User u =new User();
                               u.setName(Name);
                               u.setEmail(user_email);

                               reference.child(firebaseUser.getUid()).setValue(u)
                                       .addOnCompleteListener((new OnCompleteListener<Void>() {
                                           @Override
                                           public void onComplete(@NonNull Task<Void> task) {

                                               if (task.isSuccessful())
                                               {

                                                   Toast.makeText(signupPage.this, "wellcome to the world of Impeccable India", Toast.LENGTH_LONG).show();
                                                   Intent intent =new Intent(signupPage.this,froupage.class);
                                                   startActivity(intent);

                                               }

                                           }
                                       }));







                           }
                           else{
                               Toast.makeText(signupPage.this, "error", Toast.LENGTH_LONG).show();
                           }
                       }
                   });

               }



        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signupPage.this, Pagelogin.class);
                startActivity(intent);


            }
        });


    }
    private  void setupUIViews(){
        email =(EditText)findViewById(R.id.EmailidRG);
        password =(EditText)findViewById(R.id.Password2);
        confirmpassword =(EditText)findViewById(R.id.Password3);
        name = (EditText)findViewById(R.id.name);

        signup =(Button)findViewById(R.id.button2);
        signin =(Button)findViewById(R.id.button3);
        reference = FirebaseDatabase.getInstance().getReference().child("User");
        firebaseAuth = FirebaseAuth.getInstance();

    }
    private Boolean validate(){
        Boolean result = false;

        String emailid = email.getText().toString();
        String password1 = password.getText().toString();
        String password2 = confirmpassword.getText().toString();
        String Name = name.getText().toString();

        if(emailid.isEmpty() || password1.isEmpty() || password2.isEmpty() || Name.isEmpty()) {
            Toast.makeText(this, "enter all detail", Toast.LENGTH_LONG).show();
        }else {
            result = true ;
        }
        return result;
    }
}