package ir.maherkala.maherkala.Volley;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import ir.maherkala.maherkala.Activity.InternetActivity;
import ir.maherkala.maherkala.Activity.Login2Activity;
import ir.maherkala.maherkala.Activity.MainActivity;
import ir.maherkala.maherkala.Engine.SnakBar;
import ir.maherkala.maherkala.R;


public class setRegisterUser {


    public void register(final Context context, final String name, final String mobile, final String address, final String postal, final ProgressBar ProgressBar, final Button send, final Class className) {

        InternetActivity internetCheck = new InternetActivity();
        internetCheck.CheckNet(context);


        String urlJsonArray = context.getString(R.string.site) + "/api/User/Register";


        ProgressBar.setVisibility(View.VISIBLE);
        send.setText("");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlJsonArray,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        ProgressBar.setVisibility(View.GONE);
                        send.setText(context.getString(R.string.signupButton));
                        SnakBar snakBar = new SnakBar();
                        Log.i("moh3n", "onResponse: " + response);
                        switch (response) {
                            case "{\"Message\":1}":

                                // Intent intent = new Intent(context, className);
//                                getToken getToken = new getToken();
//                                getToken.connect(context);
                                Toast.makeText(context, context.getString(R.string.signupOk), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context, Login2Activity.class);
                                intent.putExtra("Mobile", mobile);
                                context.startActivity(intent);


                                break;
//                            case "{\"Message\":1}":

//                                snakBar.snakShow(context, context.getString(R.string.error_email_repetetive));

//                                break;
                            case "{\"Message\":2}":

                                snakBar.snakShow(context, context.getString(R.string.error_mobile_repetetive));

                                break;
                        }
                        if (response.contains("\"Message\":0")) {
                            Toast.makeText(context, context.getString(R.string.signupOk), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, Login2Activity.class);
                            intent.putExtra("Mobile", mobile);
                            context.startActivity(intent);
                        } else if (response.contains("\"Message\":1")) {
                            snakBar.snakShow(context, context.getString(R.string.error_mobile_repetetive));
                        }
                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ProgressBar.setVisibility(View.GONE);
                send.setText(context.getString(R.string.signupButton));
                Log.i("mohsenjamali", "onErrorResponse: " + error.getMessage());
            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<>();
                MyData.put("Fullname", name);
                MyData.put("Address", address);
//                MyData.put("Password", password);
//                MyData.put("PhoneNumber", tell);
                MyData.put("Mobile", mobile);
                MyData.put("PostalCode", postal);
//                MyData.put("Email", email);

                return MyData;
            }
        };
        // Log.i("mohsenjamali", "setLike: " + Comment_id + " " + Like + sp.getString("token", "nothing"));
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                300000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }


}

