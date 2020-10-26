package ir.maherkala.maherkala.Activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import ir.maherkala.maherkala.R;
import ir.maherkala.maherkala.Volley.getToken;


public class Login2Activity extends AppCompatActivity {
    private Bundle address;
    private String str;
    private EditText edigit1, edigit2, edigit3, edigit4;
    private CountDownTimer time;
    TextView login_counter;
    private Boolean let_sms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        address = getIntent().getExtras();

        edigit1 = findViewById(R.id.edDigit1);
        edigit2 = findViewById(R.id.edDigit2);
        edigit3 = findViewById(R.id.edDigit3);
        edigit4 = findViewById(R.id.edDigit4);
        login_counter = findViewById(R.id.counter);
//        timer();


        edigit1.requestFocus();

        edigit1.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() != 0)
                    chechnumber();
                edigit2.requestFocus();
            }
        });

        edigit2.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() != 0)
                    chechnumber();
                edigit3.requestFocus();
            }
        });

        edigit3.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() != 0)
                    chechnumber();
                edigit4.requestFocus();
            }
        });

        edigit4.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() != 0)
                    chechnumber();

            }
        });


    }

    private void chechnumber() {
        String str = (edigit1.getText().toString()) + (edigit2.getText().toString()) + (edigit3.getText().toString()) + (edigit4.getText().toString());
        final ProgressBar progressBar=findViewById(R.id.progressBar6);

        if (address != null) {
            if (address.getString("Mobile", "0")!=null && !address.getString("Mobile", "0").equals("0")) {
                Log.i("mohsenjamali", "chechnumber: " + str);
                if(str.length()==4){
                    getToken getToken = new getToken();
                    getToken.connect(Login2Activity.this, address.getString("Mobile", "0"), str,progressBar);
                }
//                getToken getToken = new getToken();
//                getToken.connect(Login2Activity.this, address.getString("Mobile", "0"), str);
//
//                Intent intent = new Intent(Login2Activity.this, MainActivity.class);
//                startActivity(intent);
            }
        }
    }

    private void timer() {
        CountDownTimer time = new CountDownTimer(121000, 1000) {

            public void onTick(long millisUntilFinished) {

                login_counter.setText("" + millisUntilFinished / 1000 + " ثانیه تا درخواست مجدد کد");
                login_counter.setTextColor(Color.parseColor("#FFA2A1A1"));

            }

            public void onFinish() {

                let_sms = true;
                login_counter.setText("درخواست ارسال مجدد کد");
                login_counter.setTextColor(Color.BLACK);
                login_counter.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {


                        SharedPreferences sms_number = getApplicationContext().getSharedPreferences("sms_number", 0);
                        int sms_no = sms_number.getInt("sms_no", 0);

                        if (sms_no < 10) {

                            Date date = new Date(System.currentTimeMillis()); //or simply new Date();
                            sms_no++;
                            sms_number.edit().putInt("sms_no", sms_no).apply();
                            sms_number.edit().putLong("time", date.getTime()).apply();

                            //it's fake
                            final ProgressBar progressBar = findViewById(R.id.progressBar6);
                            final Button login = findViewById(R.id.button);
                            //end


//                            setSms setsms = new setSms();
//                            setsms.send(Login2Activity.this, progressBar, login, address.getString("Mobile", "0"), address.getString("Tell", "0"), address.getString("Name", "0"), address.getString("City", "0"), address.getString("RandomCode", "0"));


                        } else if (sms_no >= 10) {

                            //getting the current time in milliseconds, and creating a Date object from it:
                            Date date = new Date(System.currentTimeMillis()); //or simply new Date();
                            //converting it back to a milliseconds representation:
                            long millis = date.getTime();
                            long startMillis = sms_number.getLong("time", 0L);
                            Calendar startTime = Calendar.getInstance();
                            startTime.setTimeInMillis(millis);
                            long now = System.currentTimeMillis();
                            long difference = now - startMillis;
                            long differenceInSeconds = difference / DateUtils.SECOND_IN_MILLIS;


                          //  if (differenceInSeconds >= 3600) {

                                sms_number.edit().clear().apply();

                                //it's fake
                                final ProgressBar progressBar = findViewById(R.id.progressBar6);
                                final Button login = findViewById(R.id.button);
                                //end
//                                setSms setsms = new setSms();
//                                setsms.send(Login2Activity.this,progressBar, login, address.getString("Mobile", "0"), address.getString("Tell", "0"), address.getString("Name", "0"), address.getString("City", "0"), address.getString("RandomCode", "0"));


                         //   } else {


                             //   Toast.makeText(Login2Activity.this, "شما در یک ساعت گذشته بیش از 10 بار درخواست کد فعال سازی داده اید", Toast.LENGTH_LONG).show();
                             //   Toast.makeText(Login2Activity.this, "لطفا یک ساعت دیگر درخواست کد فعال سازی نمائید", Toast.LENGTH_LONG).show();

                           // }
                        }
                    }
                });
            }

        };
        final SharedPreferences count = getApplicationContext().getSharedPreferences("count", 0);


        Date date = new Date(System.currentTimeMillis()); //or simply new Date();
        if (count.getLong("time", 0) == 0) {
            count.edit().putLong("time", date.getTime()).apply();
        }

        //getting the current time in milliseconds, and creating a Date object from it:
        Date date2 = new Date(System.currentTimeMillis()); //or simply new Date();
        //converting it back to a milliseconds representation:
        long millis = date2.getTime();
        long startMillis = count.getLong("time", 0L);
        Calendar startTime = Calendar.getInstance();
        startTime.setTimeInMillis(millis);
        long now = System.currentTimeMillis();
        long difference = now - startMillis;
        long differenceInSeconds = difference / DateUtils.SECOND_IN_MILLIS;

        if (differenceInSeconds == 0) {


            time.start();
            let_sms = false;

        } else if (differenceInSeconds >= 122) {


            count.edit().clear().apply();
            count.edit().putLong("time", date.getTime()).apply();

            time.start();
            let_sms = false;
        } else {
            // let_sms = false;
            time.start();
        }
    }


}
