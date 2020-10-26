package ir.maherkala.maherkala.Volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ir.maherkala.maherkala.Activity.InternetActivity;
import ir.maherkala.maherkala.R;


public class getSlider {

    public void get_banners(final Context context, final SliderLayout sliderShow) {


        InternetActivity internetCheck = new InternetActivity();
        internetCheck.CheckNet(context);

        String urlJsonArray = context.getString(R.string.site) + "/api/slider/getSliders";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlJsonArray,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonRootObject = new JSONObject(response);
                            JSONArray array = jsonRootObject.optJSONArray("Data");

                            for (int i = 0; i < array.length(); i++) {

                                JSONObject person = array.getJSONObject(i);

                                // Log.i("mohsenjamali", "onResponseJson: "+ context.getString(R.string.site) + person.getString("Image"));
                                DefaultSliderView DefaultSliderView = new DefaultSliderView(context);
                                DefaultSliderView
                                        .image(context.getString(R.string.site) + person.getString("Image"))
                                        .setScaleType(BaseSliderView.ScaleType.Fit);
                                sliderShow.addSlider(DefaultSliderView);

                                if (array.length() == 1) {
                                    sliderShow.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
                                } else {
                                    sliderShow.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i("mohsenjamali", "onErrorResponseSlider: " + e.getMessage());
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("mohsenjamali", "onErrorResponseSlider: " + error.getMessage());
            }

        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                300000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

}
