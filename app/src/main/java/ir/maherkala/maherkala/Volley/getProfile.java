package ir.maherkala.maherkala.Volley;

import android.content.Context;
import android.content.Intent;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ir.maherkala.maherkala.Activity.InternetActivity;
import ir.maherkala.maherkala.Activity.MainActivity;
import ir.maherkala.maherkala.R;


public class getProfile {

    public void show_profile(final Context context) {


        InternetActivity internetCheck = new InternetActivity();
        internetCheck.CheckNet(context);

        String urlJsonArray = context.getString(R.string.site) + "/api/User/ShowProfile";
        final SharedPreferences sp = context.getSharedPreferences("Token", 0);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlJsonArray,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonRootObject = new JSONObject(response);

                            SharedPreferences sp = context.getSharedPreferences("Profile", 0);
                            SharedPreferences.Editor edit = sp.edit();
                            edit.putString("Address", jsonRootObject.getString("Address"));
                            edit.putString("Fullname", jsonRootObject.getString("Fullname"));
                            edit.putString("Email", jsonRootObject.getString("Email"));
                            edit.putString("Mobile", jsonRootObject.getString("Mobile"));
                            edit.putString("PhoneNumber", jsonRootObject.getString("PhoneNumber"));
                            edit.putString("PostalCode", jsonRootObject.getString("PostalCode"));
                            edit.apply();

                            Toast.makeText(context, "به حساب کاربری خود خوش آمدید", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i("mohsenjamali", "onErrorResponseProfile: " + e.getMessage());
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("mohsenjamali", "onErrorResponseSlider: " + error.getMessage());
            }

        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("Api_Token", sp.getString("token", "nothing"));
                return params;
            }
        };


        stringRequest.setRetryPolicy(new

                DefaultRetryPolicy(
                300000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

}
