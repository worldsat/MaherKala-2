package ir.maherkala.maherkala.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ir.maherkala.maherkala.Engine.SetterGetterNumberOrder;
import ir.maherkala.maherkala.R;
import ir.maherkala.maherkala.Volley.getProductsAdvancedSearch;

public class ResultAdvancedSearchActivity extends AppCompatActivity {
    private Toolbar my_toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_advanced_search);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        Bundle address = getIntent().getExtras();

        ProgressBar progressBar = findViewById(R.id.progressBar2);
        RecyclerView recyclerView = findViewById(R.id.View1);
        TextView nothingTxt = findViewById(R.id.nothing_search2);
        nothingTxt.setVisibility(View.GONE);

        if (address != null) {
            String categoryId = address.getString("CategoryID");
            String Name = address.getString("Name");
            String MinPrice = address.getString("MinPrice");
            String MaxPrice = address.getString("MaxPrice");
            String Switch = address.getString("Switch");

            Log.i("mohsenjamali", "onCreateResultSearch: " + categoryId);

            getProductsAdvancedSearch getProductsAdvancedSearch = new getProductsAdvancedSearch();
            getProductsAdvancedSearch.get_banners(ResultAdvancedSearchActivity.this, progressBar, recyclerView, categoryId, Name, MinPrice, MaxPrice, Switch, nothingTxt);
        }

        Toolbar();
        Basket();

    }

    private void Toolbar() {
        my_toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(my_toolbar);
        RelativeLayout BasketLayout = findViewById(R.id.BasketLayout);
        BasketLayout.setVisibility(View.VISIBLE);
        TextView titleActionBar = findViewById(R.id.TitleActionBar);
        titleActionBar.setText("نتایج جستجو");
        LinearLayout backIcon = findViewById(R.id.backButton);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BackMethod();

            }
        });
        ImageView profile = findViewById(R.id.profile);
        profile.setVisibility(View.GONE);
    }


    private void Basket() {
        //set badgetNumber
        TextView BadgeCounter = findViewById(R.id.badgeCounter);
        SetterGetterNumberOrder setterGetter = new SetterGetterNumberOrder(ResultAdvancedSearchActivity.this);
        BadgeCounter.setText(setterGetter.getNumberOrder());
        int badget_number = Integer.valueOf(setterGetter.getNumberOrder());
        if (badget_number != 0) {
            BadgeCounter.setVisibility(View.VISIBLE);
            BadgeCounter.setText(changeNumberToFA(String.valueOf(badget_number)));
        } else {
            BadgeCounter.setVisibility(View.GONE);
        }
//        getToken token = new getToken();
//        if (!token.Ok(ResultAdvancedSearchActivity.this)) {
        BadgeCounter.setVisibility(View.GONE);
//        }
        LinearLayout backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackMethod();
            }
        });
        //  backButton.setVisibility(View.GONE);
        // set CalculateBasket icon button
        ImageView basket_icon_bar = findViewById(R.id.basket_icon);
        basket_icon_bar.setVisibility(View.GONE);
//        basket_icon_bar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(ResultAdvancedSearchActivity.this, MainActivity.class);
//                intent.putExtra("NameActivity", "BasketFragment");
//                ResultAdvancedSearchActivity.this.startActivity(intent);
//
//                // overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //finish();
        BackMethod();
    }

    private void BackMethod() {

//        if (address.getString("Activity", "").equals("MainActivity")) {
//            finish();
//            Intent intent = new Intent(ResultAdvancedSearchActivity.this, MainActivity.class);
//            ResultAdvancedSearchActivity.this.startActivity(intent);
//        } else if (address.getString("Activity", "").equals("CategoryMiddle")) {
//            finish();
//            Intent intent = new Intent(ResultAdvancedSearchActivity.this, CategoryMiddleActivity.class);
//            intent.putExtra("parent", address.getString("parent"));
//            ResultAdvancedSearchActivity.this.startActivity(intent);
//        } else {
        finish();
        // }

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
