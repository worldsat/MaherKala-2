package ir.maherkala.maherkala.Volley;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
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
import ir.maherkala.maherkala.R;


public class setBasket {


    public void register(final Context context, final ProgressDialog dialog, final String postalCode, final String address, final String PhoneNumber, final String mobile) {

        InternetActivity internetCheck = new InternetActivity();
        internetCheck.CheckNet(context);

        dialog.show();
        String urlJsonArray = context.getString(R.string.site) + "/api/Payment/Token";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlJsonArray,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.i("mohsenjamali", "onResponse: " + response);
                        dialog.dismiss();
                        try {
                            JSONObject jsonRootObject = new JSONObject(response);
                            Log.i("mohsenjamali", "JSON: " + jsonRootObject.getString("StatusCode"));

                            if (jsonRootObject.getString("StatusCode").equals("0")) {

                                String uri = Uri.parse(jsonRootObject.getString("Url"))
                                        .buildUpon()
                                        .appendQueryParameter("RedirectURL", jsonRootObject.getString("RedirectURL"))
                                        .appendQueryParameter("Token", jsonRootObject.getString("Token"))
                                        .build().toString();

                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                context.startActivity(browserIntent);

//                                Intent intent = new Intent(context, MainActivity.class);
//                                intent.putExtra("NameActivity", "BasketFragment");
//                                context.startActivity(intent);


                            } else {
                                Toast.makeText(context, jsonRootObject.getString("Message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.i("mohsenjamali", "error" + e);
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Log.i("mohsenjamali", "onErrorResponse: " + error.getMessage());
            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<>();
                final SharedPreferences sp = context.getSharedPreferences("Token", 0);
                SharedPreferences Bill = context.getSharedPreferences("Bill", 0);

                MyData.put("PostalCode", postalCode);
                MyData.put("PhoneNumber", PhoneNumber);
                MyData.put("Mobile", mobile);
                MyData.put("Address", address);
                MyData.put("City_id", Bill.getString("shahr", "1"));

                MyData.put("Discount_Amount", Bill.getString("takhfif", "0"));
                MyData.put("TransportationFee", Bill.getString("postalFee", "0"));
                MyData.put("Total", Bill.getString("PriceItem", "0"));
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
