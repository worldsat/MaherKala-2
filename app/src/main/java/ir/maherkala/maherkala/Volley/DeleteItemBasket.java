package ir.maherkala.maherkala.Volley;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import java.util.List;
import java.util.Map;

import ir.maherkala.maherkala.Activity.InternetActivity;
import ir.maherkala.maherkala.Activity.MainActivity;
import ir.maherkala.maherkala.Adapter.getBasketAdapter;
import ir.maherkala.maherkala.Engine.SetterGetterBill;
import ir.maherkala.maherkala.Engine.SetterGetterNumberOrder;
import ir.maherkala.maherkala.Engine.SnakBar;
import ir.maherkala.maherkala.Fragment.BasketFragment;
import ir.maherkala.maherkala.R;

public class DeleteItemBasket extends getBasketAdapter {

    public DeleteItemBasket(final Context context, List<String> IdItems, List<String> TitleItems, List<String> FeeItems, List<String> QuantityItems, List<String> ImageItems, TextView emptyText, RecyclerView recyclerViewlist) {
        super(context, IdItems, TitleItems, FeeItems, QuantityItems, ImageItems, emptyText, recyclerViewlist);
    }


    public void DeleteItem(final Context context, final String Price, final ProgressBar ProgressBar, final String ItemId, final ImageView DeleteBtn, final String BadgeNumber, final int AdapterPosition, final TextView emptyText) {

        InternetActivity internetCheck = new InternetActivity();
        internetCheck.CheckNet(context);

        final SharedPreferences sp = context.getSharedPreferences("Token", 0);
        String JsonUrl = context.getString(R.string.site) + "/api/Factor/DeleteProduct";
        ProgressBar.setVisibility(View.VISIBLE);
        DeleteBtn.setVisibility(View.GONE);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, JsonUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ProgressBar.setVisibility(View.GONE);
                DeleteBtn.setVisibility(View.VISIBLE);

                SetterGetterNumberOrder setterGetter = new SetterGetterNumberOrder(context);
                SetterGetterBill SetterGetterBill = new SetterGetterBill();
                SnakBar snakbar = new SnakBar();

                switch (response) {
                    case "{\"Message\":0}":
                        // Toast.makeText(context, "محصول شما از سبد خرید حذف شد", Toast.LENGTH_SHORT).show();
                        snakbar.snakShow(context, context.getString(R.string.basket_delete));


                        setterGetter.setNumberOrder(BadgeNumber, "-");
                        SetterGetterBill.setPriceItem(context, Price, BadgeNumber, "minus");

                        //update BadgeNumber
                        MainActivity m = new MainActivity();
                        m.setBadgeCounter(context);


                        //update Price Bill Impure & Pure
                        BasketFragment Basket = new BasketFragment();
                        Basket.setPriceBill(context);


                        //show empty text
                        if (setterGetter.getNumberOrder().equals("0")) {
                            emptyText.setVisibility(View.VISIBLE);
                            Basket.setHiddenLayout(context);
                        } else {
                            emptyText.setVisibility(View.GONE);
                            Basket.setShowLayout(context);
                        }


                        break;
                    case "{\"Message\":1}":
                        // Toast.makeText(context, "محصول شما قبلا از سبد خرید حذف شده است", Toast.LENGTH_SHORT).show();
                        snakbar.snakShow(context, context.getString(R.string.basket_deleted));
                        break;

                }
                Log.i("mohsenjamali", "onResponseSetter: " + setterGetter.getNumberOrder());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("mohsenjamali", "onErrorResponsePlusItem: " + error);
                ProgressBar.setVisibility(View.GONE);
                DeleteBtn.setVisibility(View.VISIBLE);
                Toast.makeText(context, "خطای سرور", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> Data = new HashMap<>();
                Data.put("Api_Token", sp.getString("token", "nothing"));

                Data.put("Item_Id", ItemId);
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
