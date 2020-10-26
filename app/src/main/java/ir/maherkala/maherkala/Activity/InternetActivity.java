package ir.maherkala.maherkala.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import ir.maherkala.maherkala.R;

public class InternetActivity extends AppCompatActivity {

    Bundle address;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        address = getIntent().getExtras();

        final ProgressBar ProgressBar = findViewById(R.id.progressBar7);
        ProgressBar.setVisibility(View.GONE);


        final Button tryAgain = findViewById(R.id.try_again);
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckNetFromActivity(ProgressBar, tryAgain);
            }
        });

    }

    private void CheckNetFromActivity(final ProgressBar ProgressBar, final Button tryAgain) {
        ProgressBar.setVisibility(View.VISIBLE);
        tryAgain.setVisibility(View.GONE);
        boolean connect;

        ConnectivityManager connectivityManager = (ConnectivityManager) InternetActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            connect = true;
        } else {
            connect = false;
        }

        if (connect) {
            try {

                //  String classname = address.getString("class");
                // Class<?> clazz = Class.forName(classname);

                //  Intent i = new Intent(InternetActivity.this, clazz);
                Intent i = new Intent(InternetActivity.this, MainActivity.class);
                startActivity(i);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {


            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    ProgressBar.setVisibility(View.GONE);
                    tryAgain.setVisibility(View.VISIBLE);


                }
            }, 1000);
        }

    }

    public void CheckNet(Context context) {

        boolean connect;

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            connect = true;
        } else {
            connect = false;
        }

        if (connect) {
            try {

                //return "ok";
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            Intent intent = new Intent(context, InternetActivity.class);
            intent.putExtra("class", "ir.maherkala.maherkala.Activity." + context.getClass().getSimpleName());


            // Log.i("mohsenjamali", "CheckNet: "+context.getClass().getSimpleName());
            context.startActivity(intent);

        }
        // return "no";
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        final MaterialDialog exit_alert = new MaterialDialog.Builder(InternetActivity.this)
                .customView(R.layout.alert_exit, false)
                .autoDismiss(false)
                .show();
        TextView delete_alert_no = (TextView) exit_alert.findViewById(R.id.alert_exit_no);
        TextView delete_alert_yes = (TextView) exit_alert.findViewById(R.id.alert_exit_yes);
        delete_alert_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exit_alert.dismiss();

            }
        });
        delete_alert_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   if (android.os.Build.VERSION.SDK_INT<21) {
                finishAffinity();
                // }else if (android.os.Build.VERSION.SDK_INT>=21) {
                //     finishAndRemoveTask();
                //  }
            }
        });
    }
}
