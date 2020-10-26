package ir.maherkala.maherkala.Fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.slider.library.SliderLayout;

import ir.maherkala.maherkala.Activity.ProductActivity;
import ir.maherkala.maherkala.R;
import ir.maherkala.maherkala.Volley.getNewest;
import ir.maherkala.maherkala.Volley.getProductsSpecial;
import ir.maherkala.maherkala.Volley.getRow1;
import ir.maherkala.maherkala.Volley.getRow2;
import ir.maherkala.maherkala.Volley.getSlider;
import ir.maherkala.maherkala.Volley.setEmailUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class OneFragment extends Fragment {

    private SliderLayout sliderShow;

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_one, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sliderShow = getView().findViewById(R.id.slider2);
        RecyclerView recyclerViewlist1 = getView().findViewById(R.id.View1);
        RecyclerView recyclerViewlist2 = getView().findViewById(R.id.View2);
        RecyclerView recyclerViewlist3 = getView().findViewById(R.id.View3);
        RecyclerView recyclerViewlist4 = getView().findViewById(R.id.View4);
        ProgressBar progressBarRow1 = getView().findViewById(R.id.progressBarRow1);
        ProgressBar progressBarRow2 = getView().findViewById(R.id.progressBarRow2);
        ProgressBar progressBarRow3 = getView().findViewById(R.id.progressBarRow3);
        ProgressBar progressBarRow4 = getView().findViewById(R.id.progressBarRow4);

        slider();
        Banner(getActivity());

        getProductsSpecial get_product_special = new getProductsSpecial();
        get_product_special.get_banners(getActivity(), progressBarRow1, recyclerViewlist1);


        getNewest get_newest = new getNewest();
        get_newest.get_banners(getActivity(), progressBarRow2, recyclerViewlist2);


        getRow1 getRow1 = new getRow1();
        getRow1.get_banners(getActivity(), progressBarRow3, recyclerViewlist3);

        getRow2 getRow2 = new getRow2();
        getRow2.get_banners(getActivity(), progressBarRow4, recyclerViewlist4);
    }


    private void slider() {
        sliderShow.setDuration(3000);

        getSlider getSlider = new getSlider();
        getSlider.get_banners(getActivity(), sliderShow);
    }

    private void Banner(final Context context) {
        ImageView banner = ((Activity) context).findViewById(R.id.banner);
        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final MaterialDialog email_alert = new MaterialDialog.Builder((Activity) context)
                        .customView(R.layout.alert_email, false)
                        .autoDismiss(false)
                        .backgroundColor(Color.parseColor("#01000000"))
                        .show();
                TextView email_alert_no = (TextView) email_alert.findViewById(R.id.cancel);
                TextView email_alert_yes = (TextView) email_alert.findViewById(R.id.save);

                final EditText emailEdt = (EditText) email_alert.findViewById(R.id.editText);
                email_alert_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        email_alert.dismiss();

                    }
                });
                email_alert_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        setEmailUser setEmailUser = new setEmailUser();
                        setEmailUser.register(getActivity(), emailEdt.getText().toString());

                        email_alert.dismiss();
                    }
                });
            }
        });
        // glide
        RequestOptions requestOptions = new RequestOptions();
        // requestOptions = requestOptions
        // .transforms(new CenterCrop(), new RoundedCorners(8));
        //  .transforms( new RoundedCorners(8));
        //   .error(R.mipmap.error)
        //.override(300, 300)
        //   .placeholder(R.mipmap.error);

        Glide.with(((Activity) context))
                .load(R.mipmap.banner1)
                //.apply(requestOptions)
                .into(banner);
        //end glide
    }

    public void AllProductRow1(final Context context, final String Category_id, final String name) {
        TextView allProductBtn = ((Activity) context).findViewById(R.id.allProductBtn);
        TextView row1_name = ((Activity) context).findViewById(R.id.row1_name);
        try {


            row1_name.setVisibility(View.VISIBLE);
            row1_name.setText(name);
            allProductBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProductActivity.class);
                    intent.putExtra("parent", Category_id);
                    intent.putExtra("name", name);
                    intent.putExtra("Activity", "MainActivity");

                    context.startActivity(intent);
                }
            });
        } catch (Exception e) {
            Log.i("mohsnejamali", "AllProductRow2Error: " + e);
        }
    }

    public void AllProductRow2(final Context context, final String Category_id, final String name) {
        TextView allProductBtn = ((Activity) context).findViewById(R.id.allProductBtn2);
        TextView row1_name = ((Activity) context).findViewById(R.id.row2_name);
        try {
            row1_name.setVisibility(View.VISIBLE);
            row1_name.setText(name);
            allProductBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProductActivity.class);
                    intent.putExtra("parent", Category_id);
                    intent.putExtra("name", name);
                    intent.putExtra("Activity", "MainActivity");

                    context.startActivity(intent);
                }
            });
        } catch (Exception e) {
            Log.d("mohsenjamali", "AllProductRow2Error: " + e);
        }
    }

    public void Row1(final Context context, final Boolean show) {
        ConstraintLayout row1 = ((Activity) context).findViewById(R.id.row1);
        if (row1 != null) {
            if (show) {
                row1.setVisibility(View.VISIBLE);
            } else {
                row1.setVisibility(View.GONE);
            }
        }
    }
}
