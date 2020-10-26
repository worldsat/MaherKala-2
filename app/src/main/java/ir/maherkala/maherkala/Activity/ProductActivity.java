package ir.maherkala.maherkala.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ir.maherkala.maherkala.AlertDialog.LoginSignupAlert;
import ir.maherkala.maherkala.Engine.BackMethodMiddleCategory;
import ir.maherkala.maherkala.Engine.SetterGetterNumberOrder;
import ir.maherkala.maherkala.Fragment.NavigationDrawerFragment;
import ir.maherkala.maherkala.R;
import ir.maherkala.maherkala.Volley.getProducts;
import ir.maherkala.maherkala.Volley.getToken;

public class ProductActivity extends AppCompatActivity {
    private Toolbar my_toolbar;
    private Bundle address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        address = getIntent().getExtras();


        ProgressBar progressBar = findViewById(R.id.progressBar2);
        RecyclerView recyclerView = findViewById(R.id.View1);

        String previus;
        if (address.getString("Activity", "ProductActivity").equals("MainActivity")) {
            previus = "MainActivity";
        } else {
            previus = address.getString("Activity", "ProductActivity");
        }


        getProducts getProducts = new getProducts();
        getProducts.get_banners(ProductActivity.this, progressBar, recyclerView, address.getString("parent", "1"), "Vertical", address.getString("name", ""), previus);

        Toolbar();
        Basket();
        NavigationDrawer();
    }

    private void Toolbar() {
        my_toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(my_toolbar);
        RelativeLayout BasketLayout = findViewById(R.id.BasketLayout);
        BasketLayout.setVisibility(View.VISIBLE);
        TextView titleActionBar = findViewById(R.id.TitleActionBar);
        titleActionBar.setText(address.getString("name", "0"));
        LinearLayout backIcon = findViewById(R.id.backButton);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BackMethod();

            }
        });

        ImageView profile = findViewById(R.id.profile);
        profile.setVisibility(View.GONE);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check Login
                getToken token = new getToken();
                LoginSignupAlert loginAlert = new LoginSignupAlert();
                if (token.Ok(getApplicationContext())) {
                    Intent intent = new Intent(ProductActivity.this, ProfileActivity.class);
                    startActivity(intent);
                } else {
                    loginAlert.alertShow(ProductActivity.this, ProductActivity.class);
                }
            }
        });
    }

    private void NavigationDrawer() {

        //navigation drawer
        NavigationDrawerFragment my_nav = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        my_nav.setUp(R.id.fragment_navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), my_toolbar);
        //end navigation drawer
    }

    private void Basket() {
        //set badgetNumber
        TextView BadgeCounter = findViewById(R.id.badgeCounter);
        SetterGetterNumberOrder setterGetter = new SetterGetterNumberOrder(ProductActivity.this);
        BadgeCounter.setText(setterGetter.getNumberOrder());
        int badget_number = Integer.valueOf(setterGetter.getNumberOrder());
        if (badget_number != 0) {
            BadgeCounter.setVisibility(View.VISIBLE);
            BadgeCounter.setText(changeNumberToFA(String.valueOf(badget_number)));
        } else {
            BadgeCounter.setVisibility(View.GONE);
        }
        getToken token = new getToken();
        if (!token.Ok(ProductActivity.this)) {
            BadgeCounter.setVisibility(View.GONE);
        }
        LinearLayout backButton = findViewById(R.id.backButton);
        //  backButton.setVisibility(View.GONE);
        // set CalculateBasket icon button
        ImageView basket_icon_bar = findViewById(R.id.basket_icon);
        basket_icon_bar.setVisibility(View.GONE);
        BadgeCounter.setVisibility(View.GONE);

        basket_icon_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ProductActivity.this, MainActivity.class);
                intent.putExtra("NameActivity", "BasketFragment");
                ProductActivity.this.startActivity(intent);
                // overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });
    }

    @Override
    public void onBackPressed() {


        BackMethod();
    }

    private void BackMethod() {

        if (address.getString("Activity", "").equals("MainActivity")) {
            finish();
            Intent intent = new Intent(ProductActivity.this, MainActivity.class);
            ProductActivity.this.startActivity(intent);
        } else if (address.getString("Activity", "").equals("CategoryMiddle")) {


            /** delete BackMethodMiddleCategory for static middle category*/
            BackMethodMiddleCategory backMethodMiddleCategory = new BackMethodMiddleCategory(ProductActivity.this);
            backMethodMiddleCategory.setMinusMiddleLevel();


            Intent intent = new Intent(ProductActivity.this, CategoryMiddleActivity.class);
            intent.putExtra("parent", backMethodMiddleCategory.getLastMiddleLevel());
            intent.putExtra("name", backMethodMiddleCategory.getLastMiddleName());
            intent.putExtra("Activity", "CategoryMiddle");
            startActivity(intent);

            finish();
            // Intent intent = new Intent(ProductActivity.this, CategoryMiddleActivity.class);
            // intent.putExtra("parent", address.getString("parent"));
            // ProductActivity.this.startActivity(intent);
        } else {
            finish();
        }

    }

    private String changeNumberToFA(String num) {
        num = num.replaceAll("0", "۰");
        num = num.replaceAll("1", "۱");
        num = num.replaceAll("2", "۲");
        num = num.replaceAll("3", "۳");
        num = num.replaceAll("4", "۴");
        num = num.replaceAll("5", "۵");
        num = num.replaceAll("6", "۶");
        num = num.replaceAll("7", "۷");
        num = num.replaceAll("8", "۸");
        num = num.replaceAll("9", "۹");
        return num;
    }
}
