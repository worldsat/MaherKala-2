package ir.maherkala.maherkala.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ir.maherkala.maherkala.R;

public class BasketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);


        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        //toolbar
        Toolbar my_toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(my_toolbar);
        RelativeLayout BasketLayout = findViewById(R.id.BasketLayout);
        BasketLayout.setVisibility(View.GONE);
        TextView titleActionBar = findViewById(R.id.TitleActionBar);
        titleActionBar.setText("سبد خرید");
        LinearLayout backIcon = findViewById(R.id.backButton);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent previous_intent = getIntent();
                String name_class = previous_intent.getStringExtra("nameActivity");
                if (name_class.equals("MainActivity")) {
                    Intent intent = new Intent(BasketActivity.this, MainActivity.class);
                    BasketActivity.this.startActivity(intent);

                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent previous_intent = getIntent();
        String name_class = previous_intent.getStringExtra("nameActivity");
        if (name_class.equals("MainActivity")) {
            Intent intent = new Intent(BasketActivity.this, MainActivity.class);
            BasketActivity.this.startActivity(intent);

        }
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);

    }

}
