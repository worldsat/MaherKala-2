package ir.maherkala.maherkala.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import ir.maherkala.maherkala.AlertDialog.LoginSignupAlert;
import ir.maherkala.maherkala.Engine.SnakBar;
import ir.maherkala.maherkala.R;
import ir.maherkala.maherkala.Volley.getComment;
import ir.maherkala.maherkala.Volley.getToken;
import ir.maherkala.maherkala.Volley.setCommentVotes;

public class CommentActivity extends AppCompatActivity {
    private Bundle address;
    private MaterialDialog Rating_dialog;
    private Toolbar my_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        ProgressBar progressBar = findViewById(R.id.progressBar);
        RecyclerView recyclerView = findViewById(R.id.RecyclerView);
        FloatingActionButton FloatBtn = findViewById(R.id.floatingActionButton);
        TextView no_comment = findViewById(R.id.no_comment);

        address = getIntent().getExtras();
        Toolbar();
        getComment comment = new getComment();
        comment.get_banners(CommentActivity.this, progressBar, recyclerView, address.getString("Id_item", "0"), no_comment);

        // material dialog help
        Rating_dialog = new MaterialDialog.Builder(CommentActivity.this)
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
                    sendCommentVote.setComment(CommentActivity.this, "test", commentEdt.getText().toString(), address.getString("Id_item", "0"));
                    Intent intent = new Intent(CommentActivity.this, CommentActivity.class);
                    intent.putExtra("Title_item", address.getString("Title_item", "0"));
                    intent.putExtra("Id_item", address.getString("Id_item", "0"));
                    startActivity(intent);
                }

                int rate = (int) rating.getRating();
                sendCommentVote.setVotes(CommentActivity.this, address.getString("Id_item", "0"), String.valueOf(rate));

                // Toast.makeText(CommentActivity.this, "نظر شما ثبت شد", Toast.LENGTH_SHORT).show();
                // snackItem.snakShow(CommentActivity.this, CommentActivity.this.getString(R.string.commmentOk));
                // Intent intent = new Intent(CommentActivity.this, MainActivity.class);
                //   startActivity(intent);
                Rating_dialog.dismiss();


            }
        });
        //end material
        FloatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getToken token = new getToken();
                LoginSignupAlert loginAlert = new LoginSignupAlert();
                if (token.Ok(CommentActivity.this)) {
                    Rating_dialog.show();
                } else {
                    loginAlert.alertShow(CommentActivity.this, ItemActivity.class);
                }
            }
        });

    }

    private void Toolbar() {
        my_toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(my_toolbar);
        RelativeLayout BasketLayout = findViewById(R.id.BasketLayout);
        BasketLayout.setVisibility(View.GONE);
        TextView titleActionBar = findViewById(R.id.TitleActionBar);
        titleActionBar.setText(address.getString("Title_item", "0"));
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

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();

    }
}
