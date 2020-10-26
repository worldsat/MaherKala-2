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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.maherkala.maherkala.Engine.SetterGetterNumberOrder;
import ir.maherkala.maherkala.R;


/**
 * Created by Moh3n on 20/07/2018.
 */

public class getBasketNumber {

    private static List<String> ImageItems = new ArrayList<>();
    private static List<String> IdItems = new ArrayList<>();
    private static List<String> NameItems = new ArrayList<>();
    private static List<String> PriceItems = new ArrayList<>();
    private static List<String> QuantityItems = new ArrayList<>();


    public void get_Items(final Context context) {

        final SharedPreferences sp = context.getSharedPreferences("Token", 0);
        String urlJsonArray = context.getString(R.string.site) + "/getBasket.php";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlJsonArray,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {

                            if (response.equals("{\"Message\":1}")) {

                                SetterGetterNumberOrder setterGetterNumberOrder = new SetterGetterNumberOrder(context);
                                setterGetterNumberOrder.emptyNumberOrder();

                            } else {


                                NameItems.clear();
                                PriceItems.clear();
                                IdItems.clear();
                                QuantityItems.clear();
                                ImageItems.clear();

                                JSONObject jsonRootObject = new JSONObject(response);
                                JSONArray array = jsonRootObject.optJSONArray("Items");


                                SetterGetterNumberOrder SetterGetterNumberOrder = new SetterGetterNumberOrder(context);
                                SetterGetterNumberOrder.emptyNumberOrder();


                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject person = array.getJSONObject(i);
                                    NameItems.add(person.getString("ProductName"));
                                    PriceItems.add(person.getString("UnitPrice"));
                                    IdItems.add(person.getString("Id"));
                                    QuantityItems.add(person.getString("Qty"));
                                    ImageItems.add(person.getString("Thumbnail"));

                                    SetterGetterNumberOrder.setNumberOrder(person.getString("Qty"), "+");

                                }


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                            Log.i("mohsenjamali", "onErrorResponse: " + e.getMessage());
                        }

                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("mohsenjamali", "onErrorResponse: " + error.getMessage());

            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<>();
                MyData.put("Api_token", sp.getString("token", "nothing"));
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
