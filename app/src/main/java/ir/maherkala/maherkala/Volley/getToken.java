package ir.maherkala.maherkala.Volley;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ir.maherkala.maherkala.Activity.InternetActivity;
import ir.maherkala.maherkala.Activity.Login2Activity;
import ir.maherkala.maherkala.Engine.SnakBar;
import ir.maherkala.maherkala.R;

public class getToken {

    public void connect(final Context context, final String username, final String password, final ProgressBar progressBar) {


        InternetActivity internetCheck = new InternetActivity();
        internetCheck.CheckNet(context);
        progressBar.setVisibility(View.VISIBLE);
        String url = context.getString(R.string.site) + "/api/User/Login";
        Log.i("moh3n", "connect: " + url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.i("moh3n", "onResponse: " + response);
                            if (response.contains("\"Message\":0")) {
                                JSONObject jsonRootObject = new JSONObject(response);

                                // Log.i("mohsenjamali", "arrayLenght: " + jsonRootObject.getString("Api_Token"));
                                if (!password.equals("")) {
                                    SharedPreferences sp = context.getSharedPreferences("Token", 0);
                                    SharedPreferences.Editor edit = sp.edit();
                                    edit.putString("token", jsonRootObject.getString("Api_Token"));
                                    edit.apply();

                                    getProfile getProfile = new getProfile();
                                    getProfile.show_profile(context);
                                } else {
//                                getProfile getProfile=new getProfile();
//                                getProfile.show_profile(context);
                                    Intent intent = new Intent(context, Login2Activity.class);
                                    intent.putExtra("Mobile", username);
                                    context.startActivity(intent);
                                }
                            } else if (response.contains("\"Message\":1")) {
                                SnakBar snakBar = new SnakBar();
                                snakBar.snakShow(context, "شماره همراه شما یافت نشد");
                            } else if (response.contains("\"Message\":2")) {
                                SnakBar snakBar = new SnakBar();
                                snakBar.snakShow(context, "رمز عبور و یا نام کاربری اشتباه است");
//                            } else if (response.contains("\"Message\":3")) {
//                                SnakBar snakBar = new SnakBar();
//                                snakBar.snakShow(context, "رمز عبور و یا نام کاربری اشتباه است");

                            } else if (response.contains("\"Message\":4")) {
                                SnakBar snakBar = new SnakBar();
                                snakBar.snakShow(context, "حساب کاربری شما فعال نشده است");
                            } else if (response.contains("\"Message\":-4")) {
                                SnakBar snakBar = new SnakBar();
                                snakBar.snakShow(context, "کد ورود اشتباه است");
                            }

//                            ProgressBar.setVisibility(View.GONE);
                        } catch (Exception e) {
                            Log.i("mohsenjamali", "token error: " + e.toString());
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // wait.dismiss();
                        // Toast.makeText(context, "نام کاربری یا رمز عبور معتبر نمی باشد", Toast.LENGTH_LONG).show();
                        Log.i("mohsenjamali", "onErrorResponse: " + error);
                        progressBar.setVisibility(View.GONE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();

                params.put("Mobile", username);
//                params.put("email", username);
                if (!password.equals("")) {
                    params.put("Password", password);
                }
                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                300000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }


    public void ClearToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences("Token", 0);
        sp.edit().clear().apply();
        Toast.makeText(context, "خروج از حساب کاربری", Toast.LENGTH_SHORT).show();
    }


    public Boolean Ok(Context context) {
        SharedPreferences sp = context.getSharedPreferences("Token", 0);
        return !sp.getString("token", "nothing").equals("nothing");
    }
}