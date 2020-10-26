package ir.maherkala.maherkala.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.maherkala.maherkala.R;
import ir.maherkala.maherkala.Volley.setRegisterSenfUser;

public class SignupSenfActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_senf);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        spinner_melk(SignupSenfActivity.this);
    }

    public void spinner_melk(Context context) {

        final Spinner type = ((Activity) context).findViewById(R.id.Type);
        // Spinner click listener
        type.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<>();

        categories.add("تولید کننده");
        categories.add("وارد کننده");
        categories.add("خریدار عمده");
        categories.add("فروشنده");


        final List<String> IdItems = new ArrayList<>();
        IdItems.add("1");
        IdItems.add("2");
        IdItems.add("3");
        IdItems.add("4");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(((Activity) context), R.layout.spinner_item, categories);

        dataAdapter.setDropDownViewResource(R.layout.spinner_item);

        type.setAdapter(dataAdapter);

        TextView send = ((Activity) context).findViewById(R.id.signinBtn);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("mohsenjamali", "spinner_melkOK: " + IdItems.get(type.getSelectedItemPosition()));
                SignUp(IdItems.get(type.getSelectedItemPosition()));
            }
        });
    }

    private void SignUp(final String type) {
        final EditText name = findViewById(R.id.nameEdt);
        final EditText mobile = findViewById(R.id.mobileEdt);
        final EditText address = findViewById(R.id.addressEdt);
        final EditText description = findViewById(R.id.descriptionEdt);

        final EditText tell = findViewById(R.id.tellEdt);
        final EditText postal = findViewById(R.id.postalEdt);
        final EditText email = findViewById(R.id.emailEdt);
        final Button signup = findViewById(R.id.signinBtn);

        final ProgressBar progressBar = findViewById(R.id.progressBarPLus);
        progressBar.setVisibility(View.GONE);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    email.setError(getString(R.string.error_email));
                } else if (name.getText().toString().length() < 3) {
                    name.setError(getString(R.string.error_name_short));
                } else if (name.getText().toString().isEmpty()) {
                    name.setError(getString(R.string.error_name));
                } else if (mobile.getText().toString().length() < 11) {
                    mobile.setError(getString(R.string.error_mobile));
                } else if (address.getText().toString().length() < 10) {
                    address.setError(getString(R.string.error_address_short));
                } else if (address.getText().toString().isEmpty()) {
                    address.setError(getString(R.string.error_address));
                } else if (tell.getText().toString().length() < 8) {
                    tell.setError(getString(R.string.error_tell));
                } else if (postal.getText().toString().length() < 8) {
                    postal.setError(getString(R.string.error_postal));
                } else {

                    setRegisterSenfUser registerUserSenf = new setRegisterSenfUser();
                    registerUserSenf.register(SignupSenfActivity.this, name.getText().toString(), type, mobile.getText().toString(), address.getText().toString(), postal.getText().toString(), tell.getText().toString(), email.getText().toString(), progressBar, signup, description.getText().toString(), MainActivity.class);

                }

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();


        // Showing selected spinner item
        // Toast.makeText(parent.getContext(), item, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}
