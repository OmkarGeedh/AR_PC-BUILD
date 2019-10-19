package com.godkiller.reoract;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import es.dmoral.toasty.Toasty;


public class LoginPage extends AppCompatActivity {

    public EditText Email;
    public EditText Password;
    public Button Login;
    public Button Cheat;
    public ImageButton showhide;
    public TextView Info;
    public TextView Rege;
    public int count = 0;


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //Toast.makeText(this,"Already Logged In",Toast.LENGTH_LONG).show();
    }

    private FirebaseAuth mAuth;

    // TODO

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        Email = findViewById(R.id.lemail);
        Password = findViewById(R.id.rlpass);
        Login = findViewById(R.id.login);
        Cheat = findViewById(R.id.cheat);
        Info = findViewById(R.id.inpass);
        Rege = findViewById(R.id.rege);
        showhide = findViewById(R.id.sh);


        mAuth = FirebaseAuth.getInstance();

        showhide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count == 0)
                {
                        Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        showhide.setImageResource(R.drawable.ic_iconfinder___eye_show_view_watch_see_disable_inactive_unavailable_off_3844443);
                        count = 1;
                }
                else
                {
                    Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showhide.setImageResource(R.drawable.ic_iconfinder_eye_24_103177);
                    count = 0;
                }
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String remail = Email.getText().toString();
                final String rpass = Password.getText().toString();
                if(remail.isEmpty() || rpass.isEmpty())
                {
                    Info.setText("Please fill all Details");
                }
                else
                {
                    OnLogin(view);
                }

            }
        });

        Rege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPage.this,Register.class);
                finish();
                startActivity(intent);
            }
        });

        Cheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this,HomePage.class);
                finish();
                startActivity(intent);
            }
        });


    }


    public void OnLogin(View view)
    {
        mAuth.signInWithEmailAndPassword(Email.getText().toString(),Password.getText().toString())
                .addOnCompleteListener(LoginPage.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Log.i("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toasty.success(LoginPage.this, "Authentication Successful.",
                                    Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginPage.this,HomePage.class);
                            finish();
                            startActivity(intent);
                        }
                        else
                        {
                            Log.i("TAG", "signInWithEmail:failure", task.getException());
                            Info.setText("Incorrect Credentials");
                            Toasty.error(LoginPage.this, "Authentication failed.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
