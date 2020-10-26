package ir.maherkala.maherkala.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import ir.maherkala.maherkala.AlertDialog.LoginSignupAlert;
import ir.maherkala.maherkala.Engine.SetterGetterNumberOrder;
import ir.maherkala.maherkala.R;
import ir.maherkala.maherkala.Volley.getToken;

public class NewsFullActivity extends AppCompatActivity {
    private Toolbar my_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_full);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);


        Toolbar();
        Basket();
        // getNews();

        Bundle address = getIntent().getExtras();
        TextView title = findViewById(R.id.titleNews);
        final ProgressBar progress = findViewById(R.id.progressBar6);
        final CardView explain = findViewById(R.id.explain);
        if (address != null) {
            explain.setVisibility(View.GONE);
            progress.setVisibility(View.VISIBLE);
            title.setText(address.getString("Title_item"));


            //  String url = "http://5.9.213.180/api/News/Detail?id=" + address.getString("Id_item");
            String url = "http://5.9.213.180/api/Setting/AboutUs";
            ViewGroup book2;
            book2 = findViewById(R.id.m);
            final WebView webView = new WebView(this);
            book2.addView(webView);
            webView.loadUrl(url);

            webView.setWebViewClient(new WebViewClient() {

                public void onPageFinished(WebView view, String url) {
                    explain.setVisibility(View.VISIBLE);
                    progress.setVisibility(View.GONE);
                }
            });
        }
    }

//    private void getNews() {
//        TextView title = findViewById(R.id.Date_show);
//        TextView detail = findViewById(R.id.detailNews);
//        ProgressBar progress = findViewById(R.id.progressBar6);
//        CardView explain = findViewById(R.id.explain);
//
//        Bundle address = getIntent().getExtras();
//        if (address != null) {
//            title.setText(address.getString("Title_item"));
//
//            getNewsFull getNewsFull = new getNewsFull();
//            getNewsFull.get_news(NewsFullActivity.this, progress, address.getString("Id_item"), detail, explain);
//        }
//    }

    private void Toolbar() {

        setSupportActionBar(my_toolbar);
        my_toolbar = findViewById(R.id.toolbar);
        // RelativeLayout BasketLayout=findViewById(R.id.BasketLayout);
        //  BasketLayout.setVisibility(View.GONE);
        TextView titleActionBar = findViewById(R.id.TitleActionBar);
        titleActionBar.setText("مشروح خبر");
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
                    Intent intent = new Intent(NewsFullActivity.this, ProfileActivity.class);
                    startActivity(intent);
                } else {
                    loginAlert.alertShow(NewsFullActivity.this, NewsFullActivity.class);
                }
            }
        });
    }

    private void Basket() {
        //set badgetNumber
        TextView BadgeCounter = findViewById(R.id.badgeCounter);

        SetterGetterNumberOrder setterGetter = new SetterGetterNumberOrder(NewsFullActivity.this);
        BadgeCounter.setText(setterGetter.getNumberOrder());

        int badget_number = Integer.valueOf(setterGetter.getNumberOrder());

        if (badget_number != 0) {
            BadgeCounter.setVisibility(View.VISIBLE);
            BadgeCounter.setText(String.valueOf(badget_number));
        } else {
            BadgeCounter.setVisibility(View.GONE);
        }
        getToken token = new getToken();
        if (!token.Ok(NewsFullActivity.this)) {
            BadgeCounter.setVisibility(View.GONE);
        }
        // set Basket icon button
        ImageView basket_icon_bar = findViewById(R.id.basket_icon);

        basket_icon_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(NewsFullActivity.this, MainActivity.class);
                intent.putExtra("NameActivity", "BasketFragment");
                NewsFullActivity.this.startActivity(intent);
                // overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
