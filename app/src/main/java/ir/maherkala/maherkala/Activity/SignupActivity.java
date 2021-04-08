package ir.maherkala.maherkala.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import ir.maherkala.maherkala.R;
import ir.maherkala.maherkala.Volley.setRegisterUser;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        SignUp();

    }

    private void SignUp() {
        final EditText name = findViewById(R.id.nameEdt);
        final EditText mobile = findViewById(R.id.mobileEdt);
        final EditText address = findViewById(R.id.addressEdt);
        final EditText password = findViewById(R.id.passwordEdt);
        final EditText tell = findViewById(R.id.tellEdt);
        final EditText postal = findViewById(R.id.postalEdt);
        final EditText email = findViewById(R.id.emailEdt);
        final Button signup = findViewById(R.id.signinBtn);

        final ProgressBar progressBar = findViewById(R.id.progressBarPLus);
        progressBar.setVisibility(View.GONE);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
//                    email.setError(getString(R.string.error_email));
//                } else if (password.getText().toString().length() < 6) {
//                    password.setError(getString(R.string.error_password_short));
//                } else if (password.getText().toString().isEmpty()) {
//                    password.setError(getString(R.string.error_password));
                  if (name.getText().toString().length() < 3) {
                    name.setError(getString(R.string.error_name_short));
                } else if (name.getText().toString().isEmpty()) {
                    name.setError(getString(R.string.error_name));
                } else if (mobile.getText().toString().length() < 11) {
                    mobile.setError(getString(R.string.error_mobile));
                } else if (address.getText().toString().length() < 10) {
                    address.setError(getString(R.string.error_address_short));
                } else if (address.getText().toString().isEmpty()) {
                    address.setError(getString(R.string.error_address));
//                } else if (tell.getText().toString().length() < 8) {
//                    tell.setError(getString(R.string.error_tell));
                } else if (postal.getText().toString().length() < 8) {
                    postal.setError(getString(R.string.error_postal));
                } else {

                    setRegisterUser registerUser = new setRegisterUser();
                    registerUser.register(SignupActivity.this, name.getText().toString(), mobile.getText().toString(), address.getText().toString(), postal.getText().toString(),progressBar,signup, MainActivity.class);

                }

            }
        });
    }

}
