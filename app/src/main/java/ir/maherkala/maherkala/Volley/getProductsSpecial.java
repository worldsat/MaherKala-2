package ir.maherkala.maherkala.Volley;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.maherkala.maherkala.Activity.InternetActivity;
import ir.maherkala.maherkala.Adapter.getProductsSpecialAdapter;
import ir.maherkala.maherkala.Fragment.OneFragment;
import ir.maherkala.maherkala.R;


public class getProductsSpecial {

    private static List<String> ImageItems = new ArrayList<>();
    private static List<String> DiscountItems = new ArrayList<>();
    private static List<String> IdItems = new ArrayList<>();
    private static List<String> DescriptionItems = new ArrayList<>();
    private static List<String> NameItems = new ArrayList<>();
    private static List<String> PriceItems = new ArrayList<>();
    private static List<String> TextItems = new ArrayList<>();
    private static List<String> QuantityItems = new ArrayList<>();
    private static List<String> LikeItems = new ArrayList<>();
    private static List<String> TotalVotesItems = new ArrayList<>();
    private static List<String> TotalCommentItems = new ArrayList<>();
    private static List<String> ThumbnailItems = new ArrayList<>();
    private static List<String> ExpireDateItems = new ArrayList<>();
    private static List<String> OtherItems = new ArrayList<>();

    private getProductsSpecialAdapter ad;
    private TextView tryagain_txt;
    private Button tryagain_btn;
    private int pageTotal = 0;
    private int page = 1;

    public void get_banners(final Context context, final ProgressBar ProgressBar, final RecyclerView recyclerViewlist) {

        InternetActivity internetCheck = new InternetActivity();
        internetCheck.CheckNet(context);

        final String urlJsonArray = context.getString(R.string.site) + "/api/product/Special/getProducts";
        recyclerViewlist.setVisibility(View.INVISIBLE);
        ProgressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlJsonArray,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            recyclerViewlist.setVisibility(View.VISIBLE);
                            // tryagain_txt.setVisibility(View.GONE);
                            // tryagain_btn.setVisibility(View.GONE);

                            ImageItems.clear();
                            DiscountItems.clear();
                            IdItems.clear();
                            DescriptionItems.clear();
                            NameItems.clear();
                            PriceItems.clear();
                            TextItems.clear();
                            QuantityItems.clear();
                            LikeItems.clear();
                            TotalVotesItems.clear();
                            TotalCommentItems.clear();
                            ThumbnailItems.clear();
                            ExpireDateItems.clear();
                            OtherItems.clear();


                            JSONObject jsonRootObject = new JSONObject(response);
                            JSONArray array = jsonRootObject.optJSONArray("Data");

                            OneFragment oneFragment = new OneFragment();

                            if (array.length() != 0) {
                                oneFragment.Row1(context, true);
                            } else {
                                oneFragment.Row1(context, false);
                            }

                            // pageTotal = jsonRootObject.getInt("TotalItemCount") / 5;
                            String CurrentDate = jsonRootObject.getString("CurrentDate");
                            for (int i = 0; i < array.length(); i++) {

                                JSONObject person = array.getJSONObject(i);
                                JSONObject jsonRootObject2 = person.getJSONObject("Product");

                                DiscountItems.add(jsonRootObject2.getString("Discount"));
                                ImageItems.add(jsonRootObject2.getString("Main_Image"));
                                IdItems.add(jsonRootObject2.getString("Id"));
                                NameItems.add(jsonRootObject2.getString("Name"));
                                DescriptionItems.add(jsonRootObject2.getString("Desc"));
                                PriceItems.add(jsonRootObject2.getString("Price"));
                                TextItems.add(jsonRootObject2.getString("Name"));
                                QuantityItems.add(jsonRootObject2.getString("Qty"));
                                LikeItems.add(String.valueOf(jsonRootObject2.getInt("Like")));
                                TotalVotesItems.add(jsonRootObject2.getString("TotalVotes"));
                                TotalCommentItems.add(jsonRootObject2.getString("TotalComment"));
                                ThumbnailItems.add(jsonRootObject2.getString("Thumbnail"));
                                ExpireDateItems.add(person.getString("ExpireDate"));
                                OtherItems.add(jsonRootObject2.getString("Images"));
                            }
                            try {
                                recyclerViewlist.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                                ad = new getProductsSpecialAdapter(context, ImageItems, DiscountItems, IdItems, DescriptionItems, NameItems, PriceItems, TextItems, QuantityItems, LikeItems, TotalVotesItems, TotalCommentItems, ThumbnailItems, ExpireDateItems, OtherItems, CurrentDate, recyclerViewlist);
                                recyclerViewlist.setAdapter(ad);

                            } catch (Exception e) {
                                Toast.makeText(context, "خطایی پیش آمده است لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
                                Log.i("mohsenjamali", "onResponseError: " + e);
                            }


                            ProgressBar.setVisibility(View.GONE);

                          /*  ad.setOnLoadMoreListener(new OnLoadMoreListener() {
                                @Override
                                public void onLoadMore() {
                                    NameItems.add(null);
                                    ad.notifyItemInserted(NameItems.size() - 1);

                                    //-----------------------------------------------------------------------------------------------
                                    RequestQueue MyRequestQueue = Volley.newRequestQueue(context);
                                    StringRequest MyStringRequest = new StringRequest(Request.Method.POST, urlJsonArray, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            NameItems.remove(NameItems.size() - 1);
                                            ad.notifyItemRemoved(NameItems.size());

                                            try {
                                                JSONObject jsonRootObject = new JSONObject(response);
                                                JSONArray jsonArray = jsonRootObject.optJSONArray("Data");

                                                for (int i = 0; i < jsonArray.length(); i++) {

                                                    JSONObject person = jsonArray.getJSONObject(i);

                                                    ImageItems.add(person.getString("Image"));
                                                    DiscountItems.add(person.getString("Discount"));
                                                    IdItems.add(person.getString("Id"));
                                                    NameItems.add(person.getString("Name"));
                                                    DescriptionItems.add(person.getString("Desc"));
                                                    PriceItems.add(person.getString("Price"));
                                                    TextItems.add(person.getString("Text"));
                                                    QuantityItems.add(person.getString("Qty"));

                                                }
                                                ad.setLoaded();

                                            } catch (JSONException e) {
                                                ProgressBar.setVisibility(View.GONE);
                                                //         tryagain_txt.setVisibility(View.VISIBLE);
                                                //         tryagain_btn.setVisibility(View.VISIBLE);
                                                recyclerViewlist.setVisibility(View.GONE);
                                                //         tryagain_txt.setText("خطایی داخلی رخ داده است !");
                                                Log.i("mohsenjamali", "onErrorResponse:loadmore1 " + e.getMessage());
                                            }

                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            ProgressBar.setVisibility(View.GONE);
                                            //    tryagain_txt.setVisibility(View.VISIBLE);
                                            //    tryagain_btn.setVisibility(View.VISIBLE);
                                            recyclerViewlist.setVisibility(View.GONE);
                                            //    tryagain_txt.setText("خطای داخلی رخ داده است !");
                                            Log.i("mohsenjamali", "onErrorResponse1::loadmore2 " + error.getMessage());
                                        }
                                    }) {
                                        protected Map<String, String> getParams() {
                                            Map<String, String> MyData = new HashMap<>();
                                            page = page + 1;
                                            MyData.put("PageNumber", String.valueOf(page));
                                            return MyData;
                                        }
                                    };

                                    if (page <= pageTotal) {
                                        MyRequestQueue.add(MyStringRequest);
                                    }
                                    ad.notifyDataSetChanged();
                                }
                            });*/

                        } catch (JSONException e) {
                            e.printStackTrace();

                            ProgressBar.setVisibility(View.GONE);

                            //    tryagain_txt.setVisibility(View.VISIBLE);
                            //    tryagain_btn.setVisibility(View.VISIBLE);
                            recyclerViewlist.setVisibility(View.GONE);
                            //    tryagain_txt.setText("ارتباط با سرور برقرار نشد !");
                            Log.i("mohsenjamali", "onErrorResponseSpecial: " + e.getMessage());
                        }

                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                ProgressBar.setVisibility(View.GONE);
                //     tryagain_txt.setVisibility(View.VISIBLE);
                //     tryagain_btn.setVisibility(View.VISIBLE);
                recyclerViewlist.setVisibility(View.GONE);
                //     tryagain_txt.setText("مشکل در دریافت اطلاعات !");
                Log.i("mohsenjamali", "onErrorResponseSpecial: " + error.getMessage());

            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<>();
                MyData.put("PageNumber", String.valueOf(page));
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
