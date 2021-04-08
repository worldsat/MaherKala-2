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
import ir.maherkala.maherkala.Adapter.getProductsAdapter;
import ir.maherkala.maherkala.OnLoadMoreListener;
import ir.maherkala.maherkala.R;


public class getProducts {

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
    private static List<String> OtherItems = new ArrayList<>();

    private getProductsAdapter ad;
    private TextView tryagain_txt;
    private Button tryagain_btn;
    private int pageTotal = 0;
    private int page = 1;

    public void get_banners(final Context context, final ProgressBar ProgressBar, final RecyclerView recyclerViewlist, final String Category_id, final String Direction, final String Category_name, final String Activity) {

        InternetActivity internetCheck = new InternetActivity();
        internetCheck.CheckNet(context);

        final String urlJsonArray = context.getString(R.string.site) + "/api/product/getProducts";
        recyclerViewlist.setVisibility(View.INVISIBLE);
        ProgressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlJsonArray,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.i("moh3n", "onResponseGetProducts: "+response);
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
                            OtherItems.clear();

                            // JSONArray array = jsonRootObject.optJSONArray("");
                            // JSONArray array = new JSONObject(response).getJSONArray("");

                            JSONObject jsonRootObject = new JSONObject(response);
                            JSONArray array = jsonRootObject.optJSONArray("Data");

                            pageTotal = jsonRootObject.getInt("TotalItemCount") / 5;

                            for (int i = 0; i < array.length(); i++) {

                                JSONObject person = array.getJSONObject(i);

                                ImageItems.add(person.getString("Main_Image"));
                                DiscountItems.add(person.getString("Discount"));
                                IdItems.add(person.getString("Id"));
                                NameItems.add(person.getString("Name"));
                                DescriptionItems.add(person.getString("Desc"));
                                PriceItems.add(person.getString("Price"));
                                TextItems.add(person.getString("Name"));
                                QuantityItems.add(person.getString("Qty"));
                                LikeItems.add(person.getString("Like"));
                                TotalVotesItems.add(person.getString("TotalVotes"));
                                TotalCommentItems.add(person.getString("TotalComment"));
                                ThumbnailItems.add(person.getString("Thumbnail"));
                                OtherItems.add(person.getString("Images"));
                                // Log.i("mohsenjamali", "onResponse: " + person.getString("Id"));
                                Log.i("mohsenjamali", "onResponseProduct: "+person.getString("Name"));
                            }
                            try {
                                if (Direction.equals("Horizontal")) {
                                    recyclerViewlist.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                                } else {
                                    recyclerViewlist.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                                }
                                ad = new getProductsAdapter(context, ImageItems, DiscountItems, IdItems, DescriptionItems, NameItems, PriceItems, TextItems, QuantityItems, LikeItems, TotalVotesItems, TotalCommentItems, ThumbnailItems, OtherItems, Direction, Category_id, Category_name, Activity, recyclerViewlist);
                                recyclerViewlist.setAdapter(ad);

                            } catch (Exception e) {
                                Toast.makeText(context, "خطایی پیش آمده است لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
                                Log.i("mohsenjamali", "onResponseErrorProduct: " + e);
                            }

                            recyclerViewlist.setVisibility(View.VISIBLE);
                            ProgressBar.setVisibility(View.GONE);

                            ad.setOnLoadMoreListener(new OnLoadMoreListener() {
                                @Override
                                public void onLoadMore() {
                                    IdItems.add(null);
                                    ad.notifyItemInserted(IdItems.size() - 1);

                                    //-----------------------------------------------------------------------------------------------
                                    RequestQueue MyRequestQueue = Volley.newRequestQueue(context);
                                    StringRequest MyStringRequest = new StringRequest(Request.Method.POST, urlJsonArray, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            IdItems.remove(IdItems.size() - 1);
                                            ad.notifyItemRemoved(IdItems.size());

                                            try {
                                                JSONObject jsonRootObject = new JSONObject(response);
                                                JSONArray jsonArray = jsonRootObject.optJSONArray("Data");

                                                for (int i = 0; i < jsonArray.length(); i++) {

                                                    JSONObject person = jsonArray.getJSONObject(i);

                                                    ImageItems.add(person.getString("Main_Image"));
                                                    DiscountItems.add(person.getString("Discount"));
                                                    IdItems.add(person.getString("Id"));
                                                    NameItems.add(person.getString("Name"));
                                                    DescriptionItems.add(person.getString("Desc"));
                                                    PriceItems.add(person.getString("Price"));
                                                    TextItems.add(person.getString("Name"));
                                                    QuantityItems.add(person.getString("Qty"));
                                                    LikeItems.add(person.getString("Like"));
                                                    TotalVotesItems.add(person.getString("TotalVotes"));
                                                    TotalCommentItems.add(person.getString("TotalComment"));
                                                    ThumbnailItems.add(person.getString("Thumbnail"));
                                                    OtherItems.add(person.getString("Images"));

                                                    Log.i("mohsenjamali", "onResponseProduct: "+person.getString("Name"));
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
                                            MyData.put("category_id", Category_id);
                                            return MyData;
                                        }
                                    };

                                    if (page <= pageTotal) {
                                        MyRequestQueue.add(MyStringRequest);
                                    }
                                    ad.notifyDataSetChanged();
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();

                            ProgressBar.setVisibility(View.GONE);

                            //    tryagain_txt.setVisibility(View.VISIBLE);
                            //    tryagain_btn.setVisibility(View.VISIBLE);
                            recyclerViewlist.setVisibility(View.GONE);
                            //    tryagain_txt.setText("ارتباط با سرور برقرار نشد !");
                            Log.i("mohsenjamali", "onResponseErrorProduct: " + e.getMessage());
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
                Log.i("mohsenjamali", "onResponseErrorProduct: " + error.getMessage());

            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<>();
                MyData.put("PageNumber", String.valueOf(page));
                MyData.put("category_id", Category_id);
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
