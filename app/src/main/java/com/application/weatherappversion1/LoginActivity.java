package com.application.weatherappversion1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText user_name, pass_word;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        user_name=findViewById(R.id.email);
        pass_word=findViewById(R.id.password);
        Button btn_login = findViewById(R.id.btn_login);
        TextView btn_sign = findViewById(R.id.btn_signup);
        FirebaseApp.initializeApp(LoginActivity.this);
        mAuth=FirebaseAuth.getInstance();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email= user_name.getText().toString().trim();
                final String password=pass_word.getText().toString().trim();
                if(email.isEmpty())
                {
                    user_name.setError("Email is empty");
                    user_name.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    user_name.setError("Enter the valid email");
                    user_name.requestFocus();
                    return;
                }
                if(password.isEmpty())
                {
                    pass_word.setError("Password is empty");
                    pass_word.requestFocus();
                    return;
                }
                if(password.length()<6)
                {
                    pass_word.setError("Length of password is more than 6");
                    pass_word.requestFocus();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        saveData(email, password);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this,
                                "Please Check Your login Credentials",
                                Toast.LENGTH_SHORT).show();
                    }

                }  });}
            });


        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUp.class ));
            }
        });
    }
    void saveData(String email, String password) {
        SharedPreferences sharedPref = getSharedPreferences("application", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("email", email);
        editor.putString("password", password);

        editor.apply();
    }
}