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

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import ir.maherkala.maherkala.R;


/**
 * Created by Moh3n on 20/07/2018.
 */

public class getShahrPrice {

    private DecimalFormat formatter = new DecimalFormat("###,###,###,###");
    public void get_price(final Context context, final String code, final TextView sharPrice, final TextView feeNahaye,final TextView pricefirst) {

        final SharedPreferences sp = context.getSharedPreferences("Token", 0);
        String urlJsonArray = context.getString(R.string.site) + "/api/Transportation/Fee";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlJsonArray,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {


//
                        try {
                            JSONObject jsonRootObject = new JSONObject(response).getJSONObject("Data");
                            String esfahanPost = jsonRootObject.getString("TransportationEsfahan");
                            String najafPost = jsonRootObject.getString("TransportationNajafabad");
                            String otherPost = jsonRootObject.getString("TransportationOther");

                            SharedPreferences Bill = context.getSharedPreferences("Bill", 0);

                            switch (code) {
                                case "1": {

                                    sharPrice.setText(formatter.format(Long.valueOf(esfahanPost)) + " تومان ");
                                    Bill.edit().putString("postalFee", esfahanPost).apply();
                                    int feeKol = Integer.valueOf(Bill.getString("PriceItem", "0")) + Integer.valueOf(esfahanPost);
                                    feeNahaye.setText(formatter.format(Long.valueOf(String.valueOf(feeKol))) + " تومان ");
                                    break;
                                }
                                case "2": {
                                    sharPrice.setText(formatter.format(Long.valueOf(najafPost ))+ " تومان ");
                                    Bill.edit().putString("postalFee", najafPost).apply();
                                    int feeKol = Integer.valueOf(Bill.getString("PriceItem", "0")) + Integer.valueOf(najafPost);
                                    feeNahaye.setText(formatter.format(Long.valueOf(String.valueOf(feeKol))) + " تومان ");
                                    break;
                                }
                                case "3": {
                                    sharPrice.setText(formatter.format(Long.valueOf(otherPost ))+ " تومان ");
                                    Bill.edit().putString("postalFee", otherPost).apply();
                                    int feeKol = Integer.valueOf(Bill.getString("PriceItem", "0")) + Integer.valueOf(otherPost);
                                    feeNahaye.setText(formatter.format(Long.valueOf(String.valueOf(feeKol)))+ " تومان ");
                                    break;
                                }

                            }
                            pricefirst.setText(formatter.format(Long.valueOf(Bill.getString("PriceItem", "0")))+ " تومان ");


                        } catch (Exception e) {
                            Log.i("moh3n", "onResponseError: " + e);
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("mohsenjamali", "onErrorResponseBasketNumber: " + error.getMessage());

            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<>();
                MyData.put("Api_token", sp.getString("token", "nothing"));
                MyData.put("City_id", code);
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

}
