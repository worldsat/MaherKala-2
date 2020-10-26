package ir.maherkala.maherkala.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ir.maherkala.maherkala.R;

public class ProfileActivity extends AppCompatActivity {

    private TextView mobile, address, tell, email, postal, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        findViewById();
        setText();


        ImageView back_arrow = findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        TextView editProfileBtn = findViewById(R.id.editProfileBtn);
        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private void findViewById() {
        mobile = findViewById(R.id.mobile);
        address = findViewById(R.id.address);
        tell = findViewById(R.id.tell);
        email = findViewById(R.id.email);
        postal = findViewById(R.id.postal);
        name = findViewById(R.id.name);
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

    @Override
    public void onBackPressed() {
        finish();
    }
}
