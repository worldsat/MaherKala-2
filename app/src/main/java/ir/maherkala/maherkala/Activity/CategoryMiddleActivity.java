package ir.maherkala.maherkala.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import ir.maherkala.maherkala.AlertDialog.LoginSignupAlert;
import ir.maherkala.maherkala.Engine.BackMethodMiddleCategory;
import ir.maherkala.maherkala.Engine.SetterGetterNumberOrder;
import ir.maherkala.maherkala.Fragment.NavigationDrawerFragment;
import ir.maherkala.maherkala.R;
import ir.maherkala.maherkala.Volley.getCategoryMiddle;
import ir.maherkala.maherkala.Volley.getToken;

public class CategoryMiddleActivity extends AppCompatActivity {
    private Toolbar my_toolbar;
    private Bundle address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middle_category);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        address = getIntent().getExtras();

        ProgressBar progressBar = findViewById(R.id.progressBar);
        RecyclerView recyclerView = findViewById(R.id.RecyclerView);


        SharedPreferences categoryMiddleId = getApplicationContext().getSharedPreferences("CategoryMiddle", 0);
        String parent, name;
        // if (categoryMiddleId.getString("CategoryMiddleId", "").equals("")) {
        parent = address.getString("parent", "0");
        name = address.getString("name", "0");
        //  } else {
        //      parent = categoryMiddleId.getString("CategoryMiddleId", "");
        //       name = categoryMiddleId.getString("CategoryMiddleName", "");
        //   }
        Toolbar(name);
        NavigationDrawer();
        Basket();


        try {
            getCategoryMiddle get_category = new getCategoryMiddle();
            //Log.i("mohsenjamali", "parent: " + address.getString("parent", "0"));
            get_category.get_banners(CategoryMiddleActivity.this, progressBar, recyclerView, parent, name);
        } catch (Error e) {
            Toast.makeText(CategoryMiddleActivity.this, "خطایی پیش آمده است لطفا بعدا امتحان کنید", Toast.LENGTH_SHORT).show();
        }

    }

    private void Toolbar(String name) {

        setSupportActionBar(my_toolbar);
        my_toolbar = findViewById(R.id.toolbar);
        // RelativeLayout BasketLayout=findViewById(R.id.BasketLayout);
        //  BasketLayout.setVisibility(View.GONE);
        TextView titleActionBar = findViewById(R.id.TitleActionBar);
        titleActionBar.setText(name);
        LinearLayout backIcon = findViewById(R.id.backButton);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
                    Intent intent = new Intent(CategoryMiddleActivity.this, ProfileActivity.class);
                    startActivity(intent);
                } else {
                    loginAlert.alertShow(CategoryMiddleActivity.this, CategoryMiddleActivity.class);
                }
            }
        });
    }

    private void Basket() {
        //set badgetNumber
        TextView BadgeCounter = findViewById(R.id.badgeCounter);

        SetterGetterNumberOrder setterGetter = new SetterGetterNumberOrder(CategoryMiddleActivity.this);
        BadgeCounter.setText(setterGetter.getNumberOrder());

        int badget_number = Integer.valueOf(setterGetter.getNumberOrder());

        if (badget_number != 0) {
            BadgeCounter.setVisibility(View.VISIBLE);
            BadgeCounter.setText(String.valueOf(badget_number));
        } else {
            BadgeCounter.setVisibility(View.GONE);
        }
//        getToken token = new getToken();
//        if (!token.Ok(CategoryMiddleActivity.this)) {
//            BadgeCounter.setVisibility(View.GONE);
//        }
        // set Basket icon button
        ImageView basket_icon_bar = findViewById(R.id.basket_icon);
        // basket_icon_bar.setVisibility(View.GONE);
        // BadgeCounter.setVisibility(View.GONE);
        basket_icon_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CategoryMiddleActivity.this, MainActivity.class);
                intent.putExtra("NameActivity", "BasketFragment");
                CategoryMiddleActivity.this.startActivity(intent);
                // overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });


    }

    public void NavigationDrawer() {
        final LayoutInflater factory = getLayoutInflater();
        final View textEntryView = factory.inflate(R.layout.fragment_one, null);

        NavigationDrawerFragment my_nav = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        my_nav.setUp(R.id.fragment_navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), my_toolbar);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //  finish();
        BackMethod();
     /*   Intent intent = new Intent(Product.this, MiddleCategory.class);
        intent.putExtra("category_id",address.getString("category_id"));
        Product.this.startActivity(intent);
*/
    }

    private void BackMethod() {
        /**change for sadeghi category.for 3 middle category, delete BackMethodMiddleCategory and put finish() only**/
        BackMethodMiddleCategory backMethodMiddleCategory = new BackMethodMiddleCategory(CategoryMiddleActivity.this);
        backMethodMiddleCategory.setMinusMiddleLevel();

        if (backMethodMiddleCategory.totalMiddlePage()) {
            Intent intent = new Intent(CategoryMiddleActivity.this, MainActivity.class);
            intent.putExtra("NameActivity", "TwoFragment");
            startActivity(intent);
        } else {
            Intent intent = new Intent(CategoryMiddleActivity.this, CategoryMiddleActivity.class);
            intent.putExtra("parent", backMethodMiddleCategory.getLastMiddleLevel());
            intent.putExtra("name", backMethodMiddleCategory.getLastMiddleName());
            intent.putExtra("Activity", "CategoryMiddle");
            finish();
            startActivity(intent);

        }

        // Intent intent = new Intent(CategoryMiddleActivity.this, MainActivity.class);
        // CategoryMiddleActivity.this.startActivity(intent);

    }

}
