package ir.maherkala.maherkala.Volley;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import ir.maherkala.maherkala.Activity.ProfileActivity;
import ir.maherkala.maherkala.R;


public class setEditProfile {


    public void edit(final Context context, final Activity activity, final String name, final String password, final String mobile, final String address, final String postal, final String tell, final String email, final ProgressBar ProgressBar, final TextView send, final Class className) {


        InternetActivity internetCheck = new InternetActivity();
        internetCheck.CheckNet(context);

        String urlJsonArray = context.getString(R.string.site) + "/api/User/UpdateProfile";


        ProgressBar.setVisibility(View.VISIBLE);
        send.setText("");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlJsonArray,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        ProgressBar.setVisibility(View.GONE);
                        send.setText(context.getString(R.string.editProfileButton));


                        switch (response) {
                            case "{\"Message\":0}":

                                // Intent intent = new Intent(context, className);
                                SharedPreferences sp = context.getSharedPreferences("Profile", 0);
                                SharedPreferences.Editor edit = sp.edit();
                                edit.putString("Address", address);
                                edit.putString("Fullname", name);
                                edit.putString("Email", email);
                                edit.putString("Mobile", mobile);
                                edit.putString("PhoneNumber", tell);
                                edit.putString("PostalCode", postal);
                                edit.apply();

                                activity.finish();

                                Toast.makeText(context, context.getString(R.string.editProfileOk), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context, ProfileActivity.class);
                                context.startActivity(intent);


                                break;

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
                SharedPreferences sp = context.getSharedPreferences("Token", 0);

                MyData.put("Fullname", name);
                MyData.put("Address", address);
                MyData.put("PhoneNumber", tell);
                MyData.put("Mobile", mobile);
                MyData.put("PostalCode", postal);
                MyData.put("Email", email);
                MyData.put("api_token", sp.getString("token", "nothing"));

                if (password.length() > 0) {
                    //logi.i
                    MyData.put("Password", password);
                }

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
