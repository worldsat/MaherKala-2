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
import ir.maherkala.maherkala.Engine.ManagementBasket;
import ir.maherkala.maherkala.Engine.SnakBar;
import ir.maherkala.maherkala.R;


public class setBasket2 {


    public void register(final Context context) {

        InternetActivity internetCheck = new InternetActivity();
        internetCheck.CheckNet(context);

//        dialog.show();
        String urlJsonArray = context.getString(R.string.site) + "/api/Factor/AdvancedStore";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlJsonArray,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.i("mohsenjamali", "onResponse: " + response);
//                        dialog.dismiss();

                        // ProgressBar.setVisibility(View.GONE);
                        SnakBar snakbar = new SnakBar();

//                        switch (response) {
//                            case  "{\"Message\":0}":
//                                // Toast.makeText(context, "رای شما ثبت شد", Toast.LENGTH_SHORT).show();
//                                snakbar.snakShow(context, context.getString(R.string.rateOk));
//                                break;
//                            case "{\"Message\":1}":
//                                //    Toast.makeText(context, "شما قبلا به این نظر رای داده اید", Toast.LENGTH_SHORT).show();
//                                snakbar.snakShow(context, context.getString(R.string.rateNo));
//                                break;
//                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                dialog.dismiss();
                Log.i("mohsenjamali", "onErrorResponse: " + error.getMessage());
            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                final SharedPreferences sp = context.getSharedPreferences("Token", 0);

                ManagementBasket managementBasket1 = new ManagementBasket(context);
                Map<String, String> MyData = new HashMap<>(managementBasket1.getListBasketItems());

                Log.i("moh3", "onClick: " + managementBasket1.getListBasketItems());

                MyData.put("Api_token", sp.getString("token", "nothing"));
//                MyData.put("Api_token", "808d256b0db260437f0858f05960f82c863b");
                Log.i("moh3n", "getParams: " + MyData);
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
