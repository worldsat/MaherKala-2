package ir.maherkala.maherkala.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import ir.maherkala.maherkala.R;
import ir.maherkala.maherkala.Volley.setEditProfile;

public class EditProfileActivity extends AppCompatActivity {
    private EditText mobile, address, tell, email, postal, name, password;
    private TextView saveEditProfile;
    private Toolbar my_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        name = findViewById(R.id.nameEdt);
        mobile = findViewById(R.id.mobileEdt);
        address = findViewById(R.id.addressEdt);
        password = findViewById(R.id.passwordEdt);
        tell = findViewById(R.id.tellEdt);
        postal = findViewById(R.id.postalEdt);
        email = findViewById(R.id.emailEdt);
        saveEditProfile = findViewById(R.id.searchBtn);


        Toolbar();
        Basket();
        setText();
        SignUp();

    }


    private void setText() {

        SharedPreferences sp = getApplicationContext().getSharedPreferences("Profile", 0);
        mobile.setText(sp.getString("Mobile", ""));
        address.setText(sp.getString("Address", ""));
        tell.setText(sp.getString("PhoneNumber", ""));
        email.setText(sp.getString("Email", ""));
        postal.setText(sp.getString("PostalCode", ""));
        name.setText(sp.getString("Fullname", ""));
    }

    private void SignUp() {


        final ProgressBar progressBar = findViewById(R.id.progressBar5);
        progressBar.setVisibility(View.GONE);


        saveEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    email.setError(getString(R.string.error_email));
                } else if (!password.getText().toString().isEmpty()) {

                    if (password.getText().toString().length() < 6) {
                        password.setError(getString(R.string.error_password_short));
                    }

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

                    setEditProfile editProfile = new setEditProfile();
                    editProfile.edit(EditProfileActivity.this, EditProfileActivity.this, name.getText().toString(), password.getText().toString(), mobile.getText().toString(), address.getText().toString(), postal.getText().toString(), tell.getText().toString(), email.getText().toString(), progressBar, saveEditProfile, MainActivity.class);

                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    private void Toolbar() {

        setSupportActionBar(my_toolbar);
        my_toolbar = findViewById(R.id.toolbar);
        // RelativeLayout BasketLayout=findViewById(R.id.BasketLayout);
        //  BasketLayout.setVisibility(View.GONE);
        TextView titleActionBar = findViewById(R.id.TitleActionBar);
        titleActionBar.setText("ویرایش حساب کاربری");
        LinearLayout backIcon = findViewById(R.id.backButton);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

                Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                startActivity(intent);
                //  BackMethod();

            }
        });
        ImageView profile = findViewById(R.id.profile);
        profile.setVisibility(View.GONE);
    }

    private void Basket() {
        //set badgetNumber
        TextView BadgeCounter = findViewById(R.id.badgeCounter);
        BadgeCounter.setVisibility(View.GONE);
//        SetterGetterNumberOrder setterGetter = new SetterGetterNumberOrder(EditProfileActivity.this);
//        BadgeCounter.setText(setterGetter.getNumberOrder());
//
//        int badget_number = Integer.valueOf(setterGetter.getNumberOrder());
//
//        if (badget_number != 0) {
//            BadgeCounter.setVisibility(View.VISIBLE);
//            BadgeCounter.setText(String.valueOf(badget_number));
//        } else {
//            BadgeCounter.setVisibility(View.GONE);
//        }
        // set Basket icon button
        ImageView basket_icon_bar = findViewById(R.id.basket_icon);
        basket_icon_bar.setVisibility(View.GONE);
//        basket_icon_bar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
//                intent.putExtra("NameActivity", "BasketFragment");
//                EditProfileActivity.this.startActivity(intent);
//                // overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
//            }
//        });


    }
}
