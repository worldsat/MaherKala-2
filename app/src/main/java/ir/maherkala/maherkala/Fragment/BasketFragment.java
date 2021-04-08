package ir.maherkala.maherkala.Fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.text.DecimalFormat;
import java.util.ArrayList;

import ir.maherkala.maherkala.Activity.BasketMiddleActivity;
import ir.maherkala.maherkala.Activity.ItemActivity;
import ir.maherkala.maherkala.Activity.LoginActivity;
import ir.maherkala.maherkala.Activity.MainActivity;
import ir.maherkala.maherkala.AlertDialog.LoginSignupAlert;
import ir.maherkala.maherkala.Engine.ManagementBasket;
import ir.maherkala.maherkala.Engine.SetterGetterBill;
import ir.maherkala.maherkala.R;
import ir.maherkala.maherkala.Volley.getBasket;
import ir.maherkala.maherkala.Volley.getBasket2;
import ir.maherkala.maherkala.Volley.getToken;
import ir.maherkala.maherkala.Volley.setBasket2;
import ir.maherkala.maherkala.Volley.setFinalizeBasket;

/**
 * A simple {@link Fragment} subclass.
 */
public class BasketFragment extends Fragment {

    private TextView totalPriceImpure;
    private TextView totalPricePure;
    private MaterialDialog Rating_dialog;
    private String str;
    private Button nextBasket;
    private DecimalFormat formatter = new DecimalFormat("###,###,###,###");
    public BasketFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_basket, container, false);

        return inflater.inflate(R.layout.fragment_basket, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ProgressBar progressBar = getView().findViewById(R.id.progressBar);
        RecyclerView recyclerViewlist = getView().findViewById(R.id.RecyclerView);
        TextView emptyText = getView().findViewById(R.id.emptyText);
        Button loginBasketBtn = getView().findViewById(R.id.loginBasketBtn);

        nextBasket = getView().findViewById(R.id.nextBasket);
        totalPriceImpure = getView().findViewById(R.id.totalImpure);
        totalPricePure = getView().findViewById(R.id.totalPure);

        ConstraintLayout BasketLayout = getView().findViewById(R.id.Basket_layout);
        ConstraintLayout BillLayout = getView().findViewById(R.id.Bill_Layout);

        emptyText.setVisibility(View.GONE);
        loginBasketBtn.setVisibility(View.GONE);
        loginBasketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        //end Check Login
//        getToken token = new getToken();
//
//        if (token.Ok(getActivity())) {
//            getBasket getBasket = new getBasket();
//            getBasket.get_Items(getActivity(), progressBar, recyclerViewlist, emptyText, BasketLayout, BillLayout, totalPriceImpure, totalPricePure);
//
//        } else {
//            setHiddenLayout(getActivity());
//            emptyText.setVisibility(View.VISIBLE);
//            loginBasketBtn.setVisibility(View.VISIBLE);
//
//        }
        ManagementBasket managementBasket = new ManagementBasket(getActivity());
        //end check Login
        ArrayList<String> idProducts = managementBasket.getProducts();
        if (idProducts.size() == 0) {
            setHiddenLayout(getActivity());
            emptyText.setVisibility(View.VISIBLE);

        } else {

            getBasket2 getBasket2 = new getBasket2();
            getBasket2.get_Items(getActivity(), managementBasket.getProducts(), progressBar, recyclerViewlist, emptyText, BasketLayout, BillLayout, totalPriceImpure, totalPricePure);

        }

        SetterGetterBill setterGetterBill = new SetterGetterBill();
        str = formatter.format(Long.valueOf(setterGetterBill.getPriceItem(getActivity()))) + getActivity().getString(R.string.currency);

        Button nextBasket = getView().findViewById(R.id.nextBasket);
        nextBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                setFinalizeBasket setFinalizeBasket = new setFinalizeBasket();
//                setFinalizeBasket.setFinalize(getActivity());
//
//
////                SetterGetterNumberOrder setterGetterNumberOrder = new SetterGetterNumberOrder(getActivity());
////                setterGetterNumberOrder.emptyNumberOrder();
//
//
//                Intent intent = new Intent(getActivity(), MainActivity.class);
//                intent.putExtra("NameActivity", "BasketFragment");
//                getActivity().startActivity(intent);
                getToken token = new getToken();
                LoginSignupAlert loginAlert = new LoginSignupAlert();

                if (token.Ok(getActivity())) {
                    Intent intent = new Intent(getActivity(), BasketMiddleActivity.class);
                    intent.putExtra("NameActivity", "BasketFragment");
                    getActivity().startActivity(intent);

                    setBasket2 setBasket2 = new setBasket2();
                    setBasket2.register(getActivity());
                } else {
                    loginAlert.alertShow(getActivity(), ItemActivity.class);
                }

                // LoginSignupAlert loginSignupAlert = new LoginSignupAlert();
                //  loginSignupAlert.alertShow(getActivity(), MainActivity.class);
            }
        });

    }

    public void setPriceBill(Context context) {
        totalPriceImpure = ((Activity) context).findViewById(R.id.totalImpure);
        totalPricePure = ((Activity) context).findViewById(R.id.totalPure);
        SetterGetterBill setterGetterBill = new SetterGetterBill();
        str = formatter.format(Long.valueOf(setterGetterBill.getPriceItem(((Activity) context)))) + ((Activity) context).getString(R.string.currency);
        try {
            totalPriceImpure.setText(str);
            totalPricePure.setText(str);
        } catch (Exception e) {

        }
    }

    public void setHiddenLayout(Context context) {
        ConstraintLayout BasketLayout = ((Activity) context).findViewById(R.id.Basket_layout);
        ConstraintLayout BillLayout = ((Activity) context).findViewById(R.id.Bill_Layout);
        Button nextBasket = ((Activity) context).findViewById(R.id.nextBasket);
        try {
            BasketLayout.setVisibility(View.GONE);
            BillLayout.setVisibility(View.GONE);
            nextBasket.setVisibility(View.GONE);
        } catch (Exception e) {
            Log.i("mohsenjamali", "setHiddenLayoutError: " + e);
        }
    }

    public void setShowLayout(Context context) {
        ConstraintLayout BasketLayout = ((Activity) context).findViewById(R.id.Basket_layout);
        ConstraintLayout BillLayout = ((Activity) context).findViewById(R.id.Bill_Layout);
        Button nextBasket = ((Activity) context).findViewById(R.id.nextBasket);

        try {
            BasketLayout.setVisibility(View.VISIBLE);
            BillLayout.setVisibility(View.VISIBLE);
            nextBasket.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Log.i("mohsenjamali", "setShowLayoutError: " + e);
        }
    }


}
