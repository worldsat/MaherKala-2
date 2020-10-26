package ir.maherkala.maherkala.Volley;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TextView;

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
import ir.maherkala.maherkala.Engine.SnakBar;
import ir.maherkala.maherkala.R;


public class setLikeDislike {


    public void setLike(final Context context, final Boolean Like, final String Comment_id, final int number, final TextView likeDislike) {

        InternetActivity internetCheck = new InternetActivity();
        internetCheck.CheckNet(context);

        final SharedPreferences sp = context.getSharedPreferences("Token", 0);

        String urlJsonArray;
        if (Like) {
            urlJsonArray = context.getString(R.string.site) + "/api/Comment/Like";
        } else {
            urlJsonArray = context.getString(R.string.site) + "/api/Comment/Dislike";
        }


        //  ProgressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlJsonArray,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // ProgressBar.setVisibility(View.GONE);
                        SnakBar snakbar = new SnakBar();
                        switch (response) {
                            case "{\"Message\":0}":
                                // Toast.makeText(context, "رای شما ثبت شد", Toast.LENGTH_SHORT).show();
                                snakbar.snakShow(context, context.getString(R.string.rateOk));
                                //plus number like or Dislike
                                int n = number;
                                n = n + 1;
                                likeDislike.setText(String.valueOf(n));

                                break;
                            case "{\"Message\":1}":
                                // Toast.makeText(context, "شما قبلا به این نظر رای داده اید", Toast.LENGTH_SHORT).show();
                                snakbar.snakShow(context, context.getString(R.string.rateNo));
                                break;
                        }
                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // ProgressBar.setVisibility(View.GONE);

                Log.i("mohsenjamali", "onErrorResponse: " + error.getMessage());

            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<>();
                MyData.put("Comment_id", Comment_id);
                MyData.put("Api_token", sp.getString("token", "nothing"));
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
