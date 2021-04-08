package ir.maherkala.maherkala.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ir.maherkala.maherkala.Engine.SetterGetterNumberOrder;
import ir.maherkala.maherkala.Engine.SnakBar;
import ir.maherkala.maherkala.R;
import ir.maherkala.maherkala.Volley.getSearch;
import ir.maherkala.maherkala.Volley.getToken;

public class ComparisonSearchActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparison_search);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        setTitle();
        Toolbar();
        Basket();
        SearchButton();
    }

    private void SearchButton() {

        final EditText search_edt = findViewById(R.id.search_edt);
        final RecyclerView recyclerViewlist = findViewById(R.id.RecyclerView);

        final TextView nothing_search = findViewById(R.id.nothing_search);
        nothing_search.setVisibility(View.GONE);

        final ProgressBar progressBar = findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.GONE);

        final getSearch saerch = new getSearch();

//        ImageView search_btn = findViewById(R.id.search_btn);
//        search_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (search_edt.getText().toString().isEmpty()) {
//                    Toast.makeText(ComparisonSearchActivity.this, "لطفا متن مورد نظر خود را وارد کنید", Toast.LENGTH_SHORT).show();
//
//                } else if (search_edt.getText().toString().length() < 3) {
//                    Toast.makeText(ComparisonSearchActivity.this, "لطفا طول کلمه مورد نظر  3 حرف یا بیشتر باشد", Toast.LENGTH_SHORT).show();
//                } else {
//                    saerch.get_Items(ComparisonSearchActivity.this, progressBar, recyclerViewlist, search_edt.getText().toString(), nothing_search);
//                }
//            }
//        });


        search_edt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    SnakBar snackItem = new SnakBar();

                    if (search_edt.getText().toString().isEmpty()) {
                        // Toast.makeText(ComparisonSearchActivity.this, "لطفا متن مورد نظر خود را وارد کنید", Toast.LENGTH_SHORT).show();
                        snackItem.snakShow(ComparisonSearchActivity.this, ComparisonSearchActivity.this.getString(R.string.fullText));

                    } else if (search_edt.getText().toString().length() < 3) {

                        // Toast.makeText(ComparisonSearchActivity.this, "لطفا طول کلمه مورد نظر  3 حرف یا بیشتر باشد", Toast.LENGTH_SHORT).show();
                        snackItem.snakShow(ComparisonSearchActivity.this, ComparisonSearchActivity.this.getString(R.string.shortText));

                    } else {
                        saerch.get_banners(ComparisonSearchActivity.this, progressBar, recyclerViewlist, search_edt.getText().toString(), nothing_search, "compare");
                    }
                }
                return false;
            }
        });
    }

    private void setTitle() {
        SharedPreferences compare_shared = getApplicationContext().getSharedPreferences("compare1", 0);
        TextView title = findViewById(R.id.title_compare);
        title.setText(compare_shared.getString("Title_item", ""));
    }

    private void Toolbar() {
        Toolbar my_toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(my_toolbar);
        RelativeLayout BasketLayout = findViewById(R.id.BasketLayout);
        BasketLayout.setVisibility(View.VISIBLE);
        TextView titleActionBar = findViewById(R.id.TitleActionBar);
        titleActionBar.setText("مقایسه محصول");
        LinearLayout backIcon = findViewById(R.id.backButton);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });
        ImageView profile = findViewById(R.id.profile);
        profile.setVisibility(View.GONE);
    }

    private void Basket() {
        //set badgetNumber
        TextView BadgeCounter = findViewById(R.id.badgeCounter);

        SetterGetterNumberOrder setterGetter = new SetterGetterNumberOrder(ComparisonSearchActivity.this);
        BadgeCounter.setText(setterGetter.getNumberOrder());

        int badget_number = Integer.valueOf(setterGetter.getNumberOrder());

        if (badget_number != 0) {
            BadgeCounter.setVisibility(View.VISIBLE);
            BadgeCounter.setText(String.valueOf(badget_number));
        } else {
            BadgeCounter.setVisibility(View.GONE);
        }
//        getToken token = new getToken();
//        if (!token.Ok(ComparisonSearchActivity.this)) {
//            BadgeCounter.setVisibility(View.GONE);
//        }
        // set Basket icon button
        ImageView basket_icon_bar = findViewById(R.id.basket_icon);

        basket_icon_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ComparisonSearchActivity.this, MainActivity.class);
                intent.putExtra("NameActivity", "BasketFragment");
                ComparisonSearchActivity.this.startActivity(intent);
                // overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });


    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
