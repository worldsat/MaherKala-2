package ir.maherkala.maherkala.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import ir.maherkala.maherkala.R;
import ir.maherkala.maherkala.Volley.getToken;
import ir.maherkala.maherkala.Volley.setRecoveryPasswordUser;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);


        Login();
        SignUp();
        ForgetPass();

    }

    private void Login() {
        final EditText user = findViewById(R.id.addressEdt);
//        final EditText password = findViewById(R.id.addressEdt);
        Button login = findViewById(R.id.signinBtn);
        final ProgressBar progressBar=findViewById(R.id.progressBarPLus3);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (user.getText().toString().isEmpty()) {
//                    user.setError(getString(R.string.error_email));
//                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(user.getText().toString()).matches()) {
//                    user.setError(getString(R.string.error_email));
//                } else if (password.getText().toString().isEmpty()) {
//                    password.setError(getString(R.string.error_password));
//                } else {
                    getToken getToken = new getToken();
                getToken.connect(LoginActivity.this, user.getText().toString(), "",progressBar);
//                }
            }
        });
    }

    private void SignUp() {
        TextView signup = findViewById(R.id.signupBtn);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void ForgetPass() {
        TextView forget = findViewById(R.id.forgetPassword);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MaterialDialog recoveryPassword = new MaterialDialog.Builder(LoginActivity.this)
                        .customView(R.layout.alert_email, false)
                        .autoDismiss(false)
                        .backgroundColor(Color.parseColor("#01000000"))
                        .show();
                TextView email_alert_no = (TextView) recoveryPassword.findViewById(R.id.cancel);
                TextView email_alert_yes = (TextView) recoveryPassword.findViewById(R.id.save);

                final EditText emailEdt = (EditText) recoveryPassword.findViewById(R.id.editText);
                email_alert_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        recoveryPassword.dismiss();

                    }
                });
                email_alert_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        setRecoveryPasswordUser setRecoveryPassword = new setRecoveryPasswordUser();
                        setRecoveryPassword.register(LoginActivity.this, emailEdt.getText().toString());

                        recoveryPassword.dismiss();
                    }
                });
            }
        });
    }

}
