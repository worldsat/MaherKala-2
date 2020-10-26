package ir.maherkala.maherkala.Volley;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import ir.maherkala.maherkala.Engine.SetterGetterNumberOrder;
import ir.maherkala.maherkala.Engine.SnakBar;
import ir.maherkala.maherkala.R;

public class PlusItemBasket {


    public void setItem(final Context context, final ProgressBar ProgressBar, final String ProductId, final Button Plus, final TextView BadgeNumber) {


        InternetActivity internetCheck = new InternetActivity();
        internetCheck.CheckNet(context);


        final SharedPreferences sp = context.getSharedPreferences("Token", 0);
        String JsonUrl = context.getString(R.string.site) + "/api/Factor/Store";
        ProgressBar.setVisibility(View.VISIBLE);
        Plus.setText("");
        Plus.setCompoundDrawables(null, null, null, null);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, JsonUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ProgressBar.setVisibility(View.GONE);
                Plus.setText(context.getString(R.string.addCard));
                Plus.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.card), null, null, null);

                SetterGetterNumberOrder setterGetter = new SetterGetterNumberOrder(context);
                SnakBar snackItem = new SnakBar();


                switch (response) {
                    case "{\"Message\":0}":
                        //Toast.makeText(context, "محصول شما به سبد خرید اضافه شد", Toast.LENGTH_SHORT).show();

                        snackItem.snakShow(context, context.getString(R.string.snackPlusItem));
                        setterGetter.setNumberOrder("1", "+");
                        BadgeNumber.setText(setterGetter.getNumberOrder());
                        BadgeNumber.setVisibility(View.VISIBLE);
                        break;

                    case "{\"Message\":1}":
                        //  Toast.makeText(context, "موجودی انبار کافی نمی باشد", Toast.LENGTH_SHORT).show();
                        snackItem.snakShow(context, context.getString(R.string.snackNoItem));
                        break;

                    case "{\"Message\":2}":
                        //Toast.makeText(context, "محصول شما به سبد خرید اضافه شد", Toast.LENGTH_SHORT).show();

                        snackItem.snakShow(context, context.getString(R.string.snackPlusItem));
                        setterGetter.setNumberOrder("1", "+");
                        BadgeNumber.setText(setterGetter.getNumberOrder());
                        BadgeNumber.setVisibility(View.VISIBLE);
                        break;
                }
                Log.i("mohsenjamali", "onResponseSetter: " + setterGetter.getNumberOrder());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("mohsenjamali", "onErrorResponsePlusItem: " + error);
                ProgressBar.setVisibility(View.GONE);
                Toast.makeText(context, "خطای سرور", Toast.LENGTH_SHORT).show();
                Plus.setText(context.getString(R.string.addCard));
                Plus.setCompoundDrawables(context.getResources().getDrawable(R.mipmap.card), null, null, null);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> Data = new HashMap<>();
                Data.put("Api_Token", sp.getString("token", "nothing"));
                Data.put("Id", ProductId);
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
