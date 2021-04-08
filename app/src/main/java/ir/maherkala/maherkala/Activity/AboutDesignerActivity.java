package ir.maherkala.maherkala.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.maherkala.maherkala.Engine.SetterGetterNumberOrder;
import ir.maherkala.maherkala.R;

public class AboutDesignerActivity extends AppCompatActivity {
    private ImageView site;
    private ImageView call;
    private TextView title_toolbar;
    private Toolbar my_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_designer);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        //toolbar
//        Toolbar my_toolbar =  findViewById(R.id.toolbar);
//        setSupportActionBar(my_toolbar);
//        LinearLayout back_icon =  findViewById(R.id.backButton);
//        back_icon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(AboutDesignerActivity.this, MainActivity.class);
//                AboutDesignerActivity.this.startActivity(intent);
//            }
//        });
//        title_toolbar =  findViewById(R.id.TitleActionBar);
//        title_toolbar.setText("درباره طراح برنامه");

        //end toolbar
        Toolbar();
        Basket();
        testWebView();
        site = findViewById(R.id.siteb);
        call = findViewById(R.id.call);
        site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "telegram.me/atrincom";

                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "http://" + url;
                }
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent telling = new Intent(Intent.ACTION_DIAL);
                telling.setData(Uri.parse("tel:09136569769"));
                AboutDesignerActivity.this.startActivity(telling);
            }

        });


    }

    private void testWebView() {
        ViewGroup book2;
        book2 = findViewById(R.id.textView22);
        final WebView webView = new WebView(this);
        book2.addView(webView);
        webView.loadUrl("file:///android_asset/about.html");
        webView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {

            }
        });
    }

    //    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(AboutDesignerActivity.this, MainActivity.class);
        AboutDesignerActivity.this.startActivity(intent);

    }

    private void Toolbar() {

        setSupportActionBar(my_toolbar);
        my_toolbar = findViewById(R.id.toolbar);
        // RelativeLayout BasketLayout=findViewById(R.id.BasketLayout);
        //  BasketLayout.setVisibility(View.GONE);
        TextView titleActionBar = findViewById(R.id.TitleActionBar);
        titleActionBar.setText("درباره طراح برنامه");
        LinearLayout backIcon = findViewById(R.id.backButton);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                //  BackMethod();

            }
        });
        ImageView profile = findViewById(R.id.profile);
        profile.setVisibility(View.GONE);
    }

    private void Basket() {
        //set badgetNumber
        TextView BadgeCounter = findViewById(R.id.badgeCounter);

        SetterGetterNumberOrder setterGetter = new SetterGetterNumberOrder(AboutDesignerActivity.this);
        BadgeCounter.setText(setterGetter.getNumberOrder());

        int badget_number = Integer.valueOf(setterGetter.getNumberOrder());

        if (badget_number != 0) {
            BadgeCounter.setVisibility(View.VISIBLE);
            BadgeCounter.setText(String.valueOf(badget_number));
        } else {
            BadgeCounter.setVisibility(View.GONE);
        }
//        getToken token = new getToken();
//        if (!token.Ok(AboutDesignerActivity.this)) {
        BadgeCounter.setVisibility(View.GONE);
        // }
        // set Basket icon button
        ImageView basket_icon_bar = findViewById(R.id.basket_icon);
        basket_icon_bar.setVisibility(View.INVISIBLE);
//        basket_icon_bar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(AboutDesignerActivity.this, MainActivity.class);
//                intent.putExtra("NameActivity", "BasketFragment");
//                AboutDesignerActivity.this.startActivity(intent);
//                // overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
//            }
//        });


    }
}
