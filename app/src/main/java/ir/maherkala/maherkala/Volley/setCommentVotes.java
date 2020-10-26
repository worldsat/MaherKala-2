package ir.maherkala.maherkala.Volley;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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


public class setCommentVotes {


    public void setComment(final Context context, final String Title, final String Text, final String Product_id) {


        InternetActivity internetCheck = new InternetActivity();
        internetCheck.CheckNet(context);

        final SharedPreferences sp = context.getSharedPreferences("Token", 0);

        String urlJsonArray = context.getString(R.string.site) + "/api/product/RegisterComment";

        //  ProgressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlJsonArray,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.i("mohsenjamali", "onResponse1: " + response);
                        // ProgressBar.setVisibility(View.GONE);
                        SnakBar snakbar = new SnakBar();

                        switch (response) {
                            case "{\"Message\":0}":
                                // Toast.makeText(context, "رای شما ثبت شد", Toast.LENGTH_SHORT).show();
                                snakbar.snakShow(context, context.getString(R.string.rateOk));
                                break;
                            case "{\"Message\":1}":
                                //    Toast.makeText(context, "شما قبلا به این نظر رای داده اید", Toast.LENGTH_SHORT).show();
                                snakbar.snakShow(context, context.getString(R.string.rateNo));
                                break;
                        }
                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // ProgressBar.setVisibility(View.GONE);

                Log.i("mohsenjamali", "onErrorResponse1: " + error.getMessage());

            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<>();
                MyData.put("Title", Title);
                MyData.put("Text", Text);
                MyData.put("Product_id", Product_id);
                MyData.put("Api_token", sp.getString("token", "nothing"));
                Log.i("mohsenjamali", "getParams: " + Title + " " + Product_id);
                return MyData;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                300000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public void setVotes(final Context context, final String Product_id, final String Vote) {

        InternetActivity internetCheck = new InternetActivity();
        internetCheck.CheckNet(context);

        final SharedPreferences sp = context.getSharedPreferences("Token", 0);

        String urlJsonArray = context.getString(R.string.site) + "/api/product/Like";

        //  ProgressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlJsonArray,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.i("mohsenjamali", "onResponse2: " + response);
                        // ProgressBar.setVisibility(View.GONE);
                        SnakBar snakbar = new SnakBar();

                        switch (response) {
                            case "{\"Message\":0}":
                                // Toast.makeText(context, "رای شما ثبت شد", Toast.LENGTH_SHORT).show();
                                snakbar.snakShow(context, context.getString(R.string.rateOk));
                                break;
                            case "{\"Message\":1}":
                                //  Toast.makeText(context, "شما قبلا به این محصول رای داده اید", Toast.LENGTH_SHORT).show();
                                snakbar.snakShow(context, context.getString(R.string.rateNo2));
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
                MyData.put("Product_id", Product_id);
                MyData.put("Vote", Vote);
                MyData.put("Api_token", sp.getString("token", "nothing"));
                return MyData;
            }
        };
        Log.i("mohsenjamali", "setVotes: " + Vote + " " + Product_id);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                300000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
