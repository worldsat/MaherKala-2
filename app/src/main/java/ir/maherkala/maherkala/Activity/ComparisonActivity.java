package ir.maherkala.maherkala.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.DecimalFormat;

import ir.maherkala.maherkala.Engine.SetterGetterNumberOrder;
import ir.maherkala.maherkala.R;
import ir.maherkala.maherkala.Volley.getToken;

public class ComparisonActivity extends AppCompatActivity {
    private DecimalFormat formatter = new DecimalFormat("###,###,###,###");
    private Toolbar my_toolbar;
    private Bundle address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparison);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        address = getIntent().getExtras();

        Toolbar();
        Basket();
        SharedPreferences compare_shared = getApplicationContext().getSharedPreferences("compare1", 0);


        TextView title1 = findViewById(R.id.title__compare1);
        TextView title2 = findViewById(R.id.title_compare2);
        TextView desc1 = findViewById(R.id.desc_compare1);
        TextView desc2 = findViewById(R.id.desc_compare2);
        TextView price1 = findViewById(R.id.price_compare1);
        TextView price2 = findViewById(R.id.price_compare2);
        ImageView pic1 = findViewById(R.id.pic_compare1);
        ImageView pic2 = findViewById(R.id.pic_compare2);


        //compare1
        title1.setText(compare_shared.getString("Title_item", " "));
        desc1.setText(compare_shared.getString("Description_item", " "));
        String str1 = changeNumber(formatter.format(Long.valueOf(compare_shared.getString("Discount_item", "0")))) + getString(R.string.currency);
        if (compare_shared.getString("Quantity", "").equals("0")) {
            price1.setText("موجود نیست");
        } else {
            price1.setText(str1);
        }
        // glide
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions
                // .transforms(new CenterCrop(), new RoundedCorners(8))
                .error(R.mipmap.error)
                //  .override(300, 300)
                .placeholder(R.mipmap.error);

        Glide.with(ComparisonActivity.this)
                .load(getString(R.string.site) + compare_shared.getString("image", " "))
                .apply(requestOptions)
                .into(pic1);
        //end glide


        //compare2
        title2.setText(address.getString("Title_item", " "));
        desc2.setText(address.getString("Description_item", " "));
        String str2 = changeNumber(formatter.format(Long.valueOf(address.getString("Discount_item", "0")))) + getString(R.string.currency);
        if (address.getString("Quantity", "").equals("0")) {
            price2.setText("موجود نیست");
        } else {
            price2.setText(str2);
        }
        // glide
        RequestOptions requestOptions2 = new RequestOptions();
        requestOptions2 = requestOptions2
                // .transforms(new CenterCrop(), new RoundedCorners(8))
                .error(R.mipmap.error)
                //  .override(300, 300)
                .placeholder(R.mipmap.error);

        Glide.with(ComparisonActivity.this)
                .load(getString(R.string.site) + address.getString("image", " "))
                .apply(requestOptions2)
                .into(pic2);
        //end glide
        Log.i("mohsenjamali", "onCreateComparision: " + address.getString("image", " ") + " " + compare_shared.getString("image", " "));

    }

    private String changeNumber(String num) {
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

    private void Toolbar() {
        my_toolbar = findViewById(R.id.toolbar);
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

        SetterGetterNumberOrder setterGetter = new SetterGetterNumberOrder(getApplicationContext());
        BadgeCounter.setText(setterGetter.getNumberOrder());

        int badget_number = Integer.valueOf(setterGetter.getNumberOrder());

        if (badget_number != 0) {
            BadgeCounter.setVisibility(View.VISIBLE);
            BadgeCounter.setText(String.valueOf(badget_number));
        } else {
            BadgeCounter.setVisibility(View.GONE);
        }
//        getToken token = new getToken();
//        if (!token.Ok(ComparisonActivity.this)) {
//            BadgeCounter.setVisibility(View.GONE);
//        }
        // set Basket icon button
        ImageView basket_icon_bar = findViewById(R.id.basket_icon);

        basket_icon_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ComparisonActivity.this, MainActivity.class);
                intent.putExtra("NameActivity", "BasketFragment");
                ComparisonActivity.this.startActivity(intent);
                // overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });


    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
