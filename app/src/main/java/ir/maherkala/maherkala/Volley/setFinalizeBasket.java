package ir.maherkala.maherkala.Volley;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
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
import ir.maherkala.maherkala.Engine.SetterGetterNumberOrder;
import ir.maherkala.maherkala.R;

public class setFinalizeBasket {


    public void setFinalize(final Context context) {

        InternetActivity internetCheck = new InternetActivity();
        internetCheck.CheckNet(context);

        String jsonLink = context.getString(R.string.site) + "/api/Factor/Finalize";
        final SharedPreferences sp = context.getSharedPreferences("Token", 0);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, jsonLink, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();

                SetterGetterNumberOrder setterGetterNumberOrder = new SetterGetterNumberOrder(context);
                setterGetterNumberOrder.emptyNumberOrder();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "خطا از سمت سرور", Toast.LENGTH_SHORT).show();
                Log.i("mohsenjamali", "onErrorResponseSetFinalize: " + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> Data = new HashMap<>();
                Data.put("Api_Token", sp.getString("token", "nothing"));
                return Data;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                300000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue Request = Volley.newRequestQueue(context);
        Request.add(stringRequest);
    }
}
