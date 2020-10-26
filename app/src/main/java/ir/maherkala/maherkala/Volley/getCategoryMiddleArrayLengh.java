package ir.maherkala.maherkala.Volley;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

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

import ir.maherkala.maherkala.Activity.CategoryMiddleActivity;
import ir.maherkala.maherkala.Activity.InternetActivity;
import ir.maherkala.maherkala.Activity.ProductActivity;
import ir.maherkala.maherkala.Adapter.getCategoryMiddleAdapter;
import ir.maherkala.maherkala.R;


public class getCategoryMiddleArrayLengh {

    private static List<String> ImageItems = new ArrayList<>();
    private static List<String> IdItems = new ArrayList<>();
    private static List<String> NameItems = new ArrayList<>();
    private getCategoryMiddleAdapter ad;
    private TextView tryagain_txt;
    private Button tryagain_btn;
    private int pageTotal = 0;
    private int page = 1;

    public void get_Length(final Context context, final String parent, final String name) {

        InternetActivity internetCheck = new InternetActivity();
        internetCheck.CheckNet(context);

        final String urlJsonArray = context.getString(R.string.site) + "/api/Category/getCategory?parent_id=" + parent;


        // ProgressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlJsonArray,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {

                            // tryagain_txt.setVisibility(View.GONE);
                            // tryagain_btn.setVisibility(View.GONE);


                            JSONObject jsonRootObject = new JSONObject(response);
                            JSONArray array = jsonRootObject.optJSONArray("Data");


                            if (array.length() == 0) {
                                Intent intent = new Intent(context, ProductActivity.class);
                                intent.putExtra("parent", parent);
                                intent.putExtra("name", name);
                                intent.putExtra("Activity", "CategoryMiddle");
                                context.startActivity(intent);
                            } else {
                                Intent intent = new Intent(context, CategoryMiddleActivity.class);
                                intent.putExtra("parent", parent);
                                intent.putExtra("name", name);
                                intent.putExtra("Activity", "CategoryMiddle");
                                context.startActivity(intent);
                            }
                            // Log.i("mohsenjamali", "onResponseArrayLenght: "+parent+" "+name);

//                            //save category middle id
//                            SharedPreferences categoryMiddleId = context.getSharedPreferences("CategoryMiddle", 0);
//                            categoryMiddleId.edit().putString("CategoryMiddleId", parent).apply();
//                            categoryMiddleId.edit().putString("CategoryMiddleName", name).apply();
//                            //end


                            //  ProgressBar.setVisibility(View.GONE);


                        } catch (JSONException e) {
                            e.printStackTrace();

                            // ProgressBar.setVisibility(View.GONE);

                            //    tryagain_txt.setVisibility(View.VISIBLE);
                            //    tryagain_btn.setVisibility(View.VISIBLE);

                            //    tryagain_txt.setText("ارتباط با سرور برقرار نشد !");
                            Log.i("mohsenjamali", "onErrorResponse: " + e.getMessage());
                        }

                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // ProgressBar.setVisibility(View.GONE);
                //     tryagain_txt.setVisibility(View.VISIBLE);
                //     tryagain_btn.setVisibility(View.VISIBLE);
                //     tryagain_txt.setText("مشکل در دریافت اطلاعات !");
                Log.i("mohsenjamali", "onErrorResponse: " + error.getMessage());

            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<>();
                //MyData.put("PageNumber", String.valueOf(page));
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
