package ir.maherkala.maherkala.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;

import java.text.DecimalFormat;

import ir.maherkala.maherkala.AlertDialog.LoginSignupAlert;
import ir.maherkala.maherkala.Engine.ManagementBasket;
import ir.maherkala.maherkala.Engine.SetterGetterNumberOrder;
import ir.maherkala.maherkala.Engine.SnakBar;
import ir.maherkala.maherkala.R;
import ir.maherkala.maherkala.Volley.PlusItemBasket;
import ir.maherkala.maherkala.Volley.getToken;
import ir.maherkala.maherkala.Volley.setCommentVotes;

public class ItemActivity extends AppCompatActivity implements ObservableScrollViewCallbacks {
    private TextView Title, Discount, Fee, Description,price_title;
    private Bundle address;
    private Button plus;
    private Toolbar my_toolbar;

    private DecimalFormat formatter = new DecimalFormat("###,###,###,###");
    private int mParallaxImageHeight;
    private ObservableScrollView scroll;
    private MaterialDialog Rating_dialog;
    private ConstraintLayout specialLayout;
    private SliderLayout sliderShow;
    private String[] ArrayImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        address = getIntent().getExtras();


        Title = findViewById(R.id.title_item);
        Description = findViewById(R.id.description_item);
        Fee = findViewById(R.id.fee_item);
        Discount = findViewById(R.id.discount_item);
        plus = findViewById(R.id.plus);
        specialLayout = findViewById(R.id.specialLayout);
        price_title = findViewById(R.id.price_title);

        Toolbar();
        Basket();
        ShareButton();
        CompareButton();
        Comment();
        RatingBar();
        Image();
        alert_rating();

        Log.i("mohsenjamali", "id: " + address.getString("Id_item"));
        specialLayout.setVisibility(View.GONE);
        Title.setText(address.getString("Title_item"));
        Description.setText(address.getString("Description_item"));
        String fee = changeNumberToFA(formatter.format(Long.valueOf(address.getString("Discount_item")))) + getString(R.string.currency);

        if (address.getString("Quantity", "").equals("0")) {
            Fee.setVisibility(View.GONE);
            price_title.setText("موجود نیست");
            price_title.setTextColor(ContextCompat.getColor(this, R.color.red));
            plus.setVisibility(View.GONE);
            Discount.setVisibility(View.GONE);
        } else {
            Fee.setText(fee);
            Discount.setText(changeNumberToFA(formatter.format(Long.valueOf(address.getString("Fee_item")))));
            Discount.setPaintFlags(Discount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            plus.setVisibility(View.VISIBLE);
        }
        if (address.getString("Discount_item", "").equals(address.getString("Fee_item"))) {
            Discount.setText(fee);
            Discount.setTextColor(ContextCompat.getColor(this, R.color.green_dark));
            Discount.setPaintFlags(Discount.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            Fee.setVisibility(View.INVISIBLE);
        }
        //Show Special Item Icon
        if (!address.getString("Percent_item", "nothing").equals("nothing")) {
            SpecialItem(address.getString("Day_item"), address.getString("Percent_item"));
        }
        //end

    }

    private void alert_rating() {
        // material dialog help
        Rating_dialog = new MaterialDialog.Builder(ItemActivity.this)
                .customView(R.layout.alert_comment_write, false)
                .autoDismiss(false)

                .backgroundColor(Color.parseColor("#01000000"))
                .build();
        final RatingBar rating = (RatingBar) Rating_dialog.findViewById(R.id.ratingBar);
        final EditText commentEdt = (EditText) Rating_dialog.findViewById(R.id.CommentEdt);
        Button send = (Button) Rating_dialog.findViewById(R.id.save_btn);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setCommentVotes sendCommentVote = new setCommentVotes();
                SnakBar snackItem = new SnakBar();

                if (!commentEdt.getText().toString().isEmpty()) {
                    sendCommentVote.setComment(ItemActivity.this, "", commentEdt.getText().toString(), address.getString("Id_item", "0"));

                }
                int rate = (int) rating.getRating();
                sendCommentVote.setVotes(ItemActivity.this, address.getString("Id_item", "0"), String.valueOf(rate));

                // Toast.makeText(ItemActivity.this, "نظر شما ثبت شد", Toast.LENGTH_SHORT).show();
                //  snackItem.snakShow(ItemActivity.this, ItemActivity.this.getString(R.string.commmentOk));
                Rating_dialog.dismiss();
            }
        });
        //end material dialog
    }

    private void Toolbar() {
        my_toolbar = findViewById(R.id.toolbar);
        my_toolbar.setBackgroundColor(
                ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.toolbar)));
        mParallaxImageHeight = getResources().getDimensionPixelSize(R.dimen.parallax_image_height);
        scroll = findViewById(R.id.list22);
        scroll.setScrollViewCallbacks(this);

        setSupportActionBar(my_toolbar);
        // RelativeLayout BasketLayout=findViewById(R.id.BasketLayout);
        //  BasketLayout.setVisibility(View.GONE);
        TextView titleActionBar = findViewById(R.id.TitleActionBar);
        titleActionBar.setText(address.getString("Title_item", ""));
        LinearLayout backIcon = findViewById(R.id.backButton);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackMethod();

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
                    Intent intent = new Intent(ItemActivity.this, ProfileActivity.class);
                    startActivity(intent);
                } else {
                    loginAlert.alertShow(ItemActivity.this, ItemActivity.class);
                }
            }
        });
    }

    private void Image() {
        //Main image
        sliderShow = findViewById(R.id.slider2);
        String MainImage = address.getString("image");
        sliderShow = findViewById(R.id.slider2);
        DefaultSliderView DefaultSliderView = new DefaultSliderView(ItemActivity.this);
        DefaultSliderView
                .image(ItemActivity.this.getString(R.string.site) + MainImage)
                .error(R.mipmap.error)
                .empty(R.mipmap.error)
                .setScaleType(BaseSliderView.ScaleType.CenterInside);
        sliderShow.addSlider(DefaultSliderView);


        //other image
        ArrayImage = address.getStringArray("OtherImage");
        if (ArrayImage != null && !ArrayImage[0].equals("null")) {
            for (String w : ArrayImage) {
                DefaultSliderView DefaultSliderView2 = new DefaultSliderView(ItemActivity.this);
                DefaultSliderView2
                        .image(ItemActivity.this.getString(R.string.site) + w)
                        .error(R.mipmap.error)
                        .empty(R.mipmap.error)
                        .setScaleType(BaseSliderView.ScaleType.CenterInside);
                sliderShow.addSlider(DefaultSliderView2);
            }
        }
        sliderShow.stopAutoCycle();

        sliderShow.getPagerIndicator().setDefaultIndicatorColor(getResources().getColor(R.color.red), getResources().getColor(R.color.gray));
        if (ArrayImage[0].equals("null")) {
            sliderShow.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
        } else {
            sliderShow.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
        }
    }

    private void Basket() {
        //set badgetNumber
        final TextView BadgeCounter = findViewById(R.id.badgeCounter);
        SetterGetterNumberOrder setterGetter = new SetterGetterNumberOrder(ItemActivity.this);
        BadgeCounter.setText(setterGetter.getNumberOrder());

        int badget_number = Integer.valueOf(setterGetter.getNumberOrder());

        if (badget_number != 0) {
            BadgeCounter.setVisibility(View.VISIBLE);
            BadgeCounter.setText(changeNumberToFA(String.valueOf(badget_number)));
        } else {
            BadgeCounter.setVisibility(View.GONE);
        }
//        getToken token = new getToken();
//        if (!token.Ok(ItemActivity.this)) {
//            BadgeCounter.setVisibility(View.GONE);
//        }
        ImageView basket_icon_bar = findViewById(R.id.basket_icon);

        basket_icon_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ItemActivity.this, MainActivity.class);
                intent.putExtra("NameActivity", "BasketFragment");
                ItemActivity.this.startActivity(intent);
                // overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });

        final ProgressBar progressBarPlus = findViewById(R.id.progressBarPLus);
        progressBarPlus.setVisibility(View.GONE);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ID = address.getString("Id_item");

//                getToken token = new getToken();
//                LoginSignupAlert loginAlert = new LoginSignupAlert();
//
//                if (token.Ok(ItemActivity.this)) {
//                    PlusItemBasket plusItemBasket = new PlusItemBasket();
//                    plusItemBasket.setItem(ItemActivity.this, progressBarPlus, ID, plus, BadgeCounter);
//                } else {
//                    loginAlert.alertShow(ItemActivity.this, ItemActivity.class);
//                }
                ManagementBasket managementBasket = new ManagementBasket(ItemActivity.this);
                managementBasket.insertProduct(ID,1);

                SetterGetterNumberOrder setterGetter = new SetterGetterNumberOrder(ItemActivity.this);
                setterGetter.setNumberOrder("1","+");
                BadgeCounter.setText(setterGetter.getNumberOrder());
                BadgeCounter.setVisibility(View.VISIBLE);

            }

        });


    }

    @Override
    public void onBackPressed() {
        BackMethod();
    }

    private void RatingBar() {
        double like = Integer.valueOf(address.getString("LikeItems"));
        double totalVotes = Integer.valueOf(address.getString("TotalVotesItems"));
        double votes;

        RatingBar ratingBar = findViewById(R.id.ratingBar);
        TextView rateText = findViewById(R.id.rateTxt);

        if (totalVotes == 0) {
            votes = 5;
        } else {
            votes = (like / totalVotes) * 5;

            //   Log.i("mohsenjamali", "RatingBar: " + totalVotes + " " + like + "  " + votes);
        }


        ratingBar.setRating(Float.valueOf(String.valueOf(votes)));

        ratingBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //check Login
                getToken token = new getToken();
                LoginSignupAlert loginAlert = new LoginSignupAlert();
                if (token.Ok(ItemActivity.this)) {
                    Rating_dialog.show();
                } else {
                    loginAlert.alertShow(ItemActivity.this, ItemActivity.class);
                }

                return true;
            }
        });
        int rate = (int) Math.ceil(votes);
        String rateTxt = "امتیاز این محصول " + changeNumberToFA(String.valueOf(rate)) + " از ۵ ";
        rateText.setText(rateTxt);
    }

    private void ShareButton() {
        //set Share Button
        ImageView shareBtn = findViewById(R.id.share_item);
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, address.getString("Title_item"));
                // shareIntent.putExtra(Intent.EXTRA_SUBJECT, "iran");
                startActivity(Intent.createChooser(shareIntent, "اشتراک ..."));
            }
        });
        //End set Share Button
    }

    private void CompareButton() {
        ImageView compare = findViewById(R.id.compare);
        compare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences compare_shared = getApplicationContext().getSharedPreferences("compare1", 0);
                compare_shared.edit()
                        .putString("image", address.getString("image"))
                        .putString("Id_item", address.getString("Id_item"))
                        .putString("Description_item", address.getString("Description_item"))
                        .putString("Discount_item", address.getString("Discount_item"))
                        .putString("Title_item", address.getString("Title_item"))
                        .putString("Fee_item", address.getString("Fee_item"))
                        .putString("Intro_item", address.getString("Intro_item"))
                        .putString("Quantity", address.getString("Quantity"))
                        .putString("Activity", address.getString("Activity"))
                        .putString("parent", address.getString("parent"))
                        .putString("Category_Name", address.getString("Category_Name"))
                        .putString("LikeItems", address.getString("LikeItems"))
                        .putString("TotalVotesItems", address.getString("TotalVotesItems"))
                        .putString("TotalCommentItems", address.getString("TotalCommentItems"))
                        .apply();
                Intent intent = new Intent(ItemActivity.this, ComparisonSearchActivity.class);
                startActivity(intent);
            }
        });

    }

    private void Comment() {
        TextView totalComment = findViewById(R.id.totalComment);
        String total = "نظرات کاربران(" + address.getString("TotalCommentItems", "0") + ")";
        totalComment.setText(total);
        TextView comment = findViewById(R.id.commentItem);
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemActivity.this, CommentActivity.class);
                intent.putExtra("Id_item", address.getString("Id_item", "0"));
                intent.putExtra("Title_item", address.getString("Title_item", "0"));

                ItemActivity.this.startActivity(intent);
            }
        });
    }

    private void BackMethod() {
        if (address.getString("Activity", "").equals("MainActivity")) {
            finish();
            Intent intent = new Intent(ItemActivity.this, MainActivity.class);
            ItemActivity.this.startActivity(intent);
        } else if (address.getString("Activity", "").equals("ProductActivity")) {
            finish();
            Intent intent = new Intent(ItemActivity.this, ProductActivity.class);
            intent.putExtra("parent", address.getString("parent"));
            intent.putExtra("name", address.getString("Category_Name"));
            ItemActivity.this.startActivity(intent);
        } else if (address.getString("Activity", "").equals("SearchActivity")) {
            finish();

        } else {

            finish();
        }
    }

    //marbot be scroll
    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        int baseColor = getResources().getColor(R.color.toolbar);
        float alpha = Math.min(5, (float) scrollY / mParallaxImageHeight);
        my_toolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));
        ViewHelper.setTranslationY(sliderShow, scrollY / 2);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onScrollChanged(scroll.getCurrentScrollY(), false, false);
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }
    //end scroll


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

    private void SpecialItem(String day, String off) {


        TextView percent = findViewById(R.id.percent);
        TextView days = findViewById(R.id.day);

        String str1 = "%" + off + " تخفیف";
        percent.setText(str1);

        String str2 = "فقط تا " + day + " روز دیگر ";
        days.setText(str2);

        specialLayout.setVisibility(View.VISIBLE);

    }


}
