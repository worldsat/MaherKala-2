package ir.maherkala.maherkala.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import ir.maherkala.maherkala.AlertDialog.LoginSignupAlert;
import ir.maherkala.maherkala.Engine.SetterGetterNumberOrder;
import ir.maherkala.maherkala.R;
import ir.maherkala.maherkala.Volley.getHistoryItemBasket;
import ir.maherkala.maherkala.Volley.getToken;


public class HistoryItemBasketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_item_basket);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        Toolbar();
        Basket();

        ProgressBar progressBar = findViewById(R.id.progressBar);
        RecyclerView recyclerView = findViewById(R.id.view1);
        ConstraintLayout basket_layout = findViewById(R.id.Basket_layout);

        Bundle addresss = getIntent().getExtras();
        if (addresss != null) {
            String factor = addresss.getString("factor");
            getHistoryItemBasket getHistoryItemBasket = new getHistoryItemBasket();
            getHistoryItemBasket.get_Items(this, progressBar, recyclerView, basket_layout, factor);
        }
    }

    private void Toolbar() {
        Toolbar my_toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(my_toolbar);

        // RelativeLayout BasketLayout=findViewById(R.id.BasketLayout);
        //  BasketLayout.setVisibility(View.GONE);
        TextView titleActionBar = findViewById(R.id.TitleActionBar);
        titleActionBar.setText("");
        LinearLayout backIcon = findViewById(R.id.backButton);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                //  BackMethod();

            }
        });
        ImageView profile = findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check Login
                getToken token = new getToken();
                LoginSignupAlert loginAlert = new LoginSignupAlert();
                if (token.Ok(getApplicationContext())) {
                    Intent intent = new Intent(HistoryItemBasketActivity.this, ProfileActivity.class);
                    startActivity(intent);
                } else {
                    loginAlert.alertShow(HistoryItemBasketActivity.this, NewsActivity.class);
                }
            }
        });
    }

    private void Basket() {
        //set badgetNumber
        TextView BadgeCounter = findViewById(R.id.badgeCounter);

        SetterGetterNumberOrder setterGetter = new SetterGetterNumberOrder(this);
        BadgeCounter.setText(setterGetter.getNumberOrder());

        int badget_number = Integer.valueOf(setterGetter.getNumberOrder());

        if (badget_number != 0) {
            BadgeCounter.setVisibility(View.VISIBLE);
            BadgeCounter.setText(String.valueOf(badget_number));
        } else {
            BadgeCounter.setVisibility(View.GONE);
        }
//        getToken token = new getToken();
//        if (!token.Ok(this)) {
//            BadgeCounter.setVisibility(View.GONE);
//        }
        // set Basket icon button
        ImageView basket_icon_bar = findViewById(R.id.basket_icon);

        basket_icon_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HistoryItemBasketActivity.this, MainActivity.class);
                intent.putExtra("NameActivity", "BasketFragment");
                startActivity(intent);
                // overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });


    }
}
