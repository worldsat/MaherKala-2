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

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.maherkala.maherkala.Activity.InternetActivity;
import ir.maherkala.maherkala.Activity.MainActivity;
import ir.maherkala.maherkala.Adapter.getBasketAdapter;
import ir.maherkala.maherkala.Engine.SetterGetterNumberOrder;
import ir.maherkala.maherkala.Engine.SnakBar;
import ir.maherkala.maherkala.Fragment.BasketFragment;
import ir.maherkala.maherkala.R;

public class ChangeQuantityItemBasket extends getBasketAdapter {

    public ChangeQuantityItemBasket(final Context context, List<String> IdItems, List<String> TitleItems, List<String> FeeItems, List<String> QuantityItems, List<String> ImageItems, TextView emptyText, RecyclerView recyclerViewlist) {
        super(context, IdItems, TitleItems, FeeItems, QuantityItems, ImageItems, emptyText, recyclerViewlist);
    }


    public void ChangeItem(final Context context, final ProgressBar ProgressBar, final ImageView plusBtn, final ImageView minusBtn, final String Product_Id, final TextView BadgeNumberItem, final TextView NumberOrderItem, final TextView PriceItem, final String PriceEachItem, final String Qty, final boolean plus, final int AdapterPosition, final TextView emptyText) {

        InternetActivity internetCheck = new InternetActivity();
        internetCheck.CheckNet(context);

        plusBtn.setEnabled(false);
        minusBtn.setEnabled(false);
        final SharedPreferences sp = context.getSharedPreferences("Token", 0);
        String JsonUrl = context.getString(R.string.site) + "/api/Factor/ChangeQty";
        ProgressBar.setVisibility(View.VISIBLE);
        BadgeNumberItem.setVisibility(View.INVISIBLE);
        final DecimalFormat formatter = new DecimalFormat("###,###,###,###");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, JsonUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ProgressBar.setVisibility(View.GONE);
                BadgeNumberItem.setVisibility(View.VISIBLE);

                SetterGetterNumberOrder setterGetter = new SetterGetterNumberOrder(context);
                SnakBar snackItem = new SnakBar();

                switch (response) {
                    case "{\"Message\":0}":


                        //tashkhis plus ya minus baraye BadgeNumber
                        if (plus) {
                            setterGetter.setNumberOrder("1", "+");

                        } else {
                            setterGetter.setNumberOrder("1", "-");
                        }
                        BadgeNumberItem.setText(Qty);

                        //update BadgeNumber
                        MainActivity m = new MainActivity();
                        m.setBadgeCounter(context);

                        //update Price Bill Impure & Pure
                        BasketFragment Basket = new BasketFragment();
                        Basket.setPriceBill(context);


                        //show empty text
                        if (setterGetter.getNumberOrder().equals("0")) {
                            emptyText.setVisibility(View.VISIBLE);
                        } else {
                            emptyText.setVisibility(View.GONE);
                        }


                        //update numberOrderItem && PriceItem
                        String str1 = formatter.format(Long.valueOf(PriceEachItem) * Long.valueOf(Qty)) + context.getString(R.string.currency);
                        PriceItem.setText(str1);

                        String str2 = Qty + " x " + (formatter.format(Long.valueOf(PriceEachItem)));
                        NumberOrderItem.setText(str2);


                        break;
                    case "{\"Message\":1}":
                        // Toast.makeText(context, "این کالا وجود ندارد", Toast.LENGTH_SHORT).show();
                        snackItem.snakShow(context, context.getString(R.string.noProduct));
                        break;
                    case "{\"Message\":2}":
                        //Toast.makeText(context, "موجودی این کالا در انبار کافی نیست", Toast.LENGTH_SHORT).show();
                        snackItem.snakShow(context, context.getString(R.string.noQuantity));
                        break;

                }
                // Log.i("mohsenjamali", "onResponseSetter: " + setterGetter.getNumberOrder());
                plusBtn.setEnabled(true);
                minusBtn.setEnabled(true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("mohsenjamali", "onErrorResponsePlusItem: " + error);
                ProgressBar.setVisibility(View.GONE);
                BadgeNumberItem.setVisibility(View.VISIBLE);
                plusBtn.setEnabled(true);
                minusBtn.setEnabled(true);
                Toast.makeText(context, "خطای سرور", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> Data = new HashMap<>();
                Data.put("Api_Token", sp.getString("token", "nothing"));
                Data.put("Id", Product_Id);
                Data.put("Qty", Qty);
                //  Log.i("mohsenjamali", "getParamsChangeQty: " + Qty);
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
