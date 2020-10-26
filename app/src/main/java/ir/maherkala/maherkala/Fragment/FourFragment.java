package ir.maherkala.maherkala.Fragment;


import android.app.Activity;
import android.content.Context;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import ir.maherkala.maherkala.R;
import ir.maherkala.maherkala.Volley.getHistoryBasket;
import ir.maherkala.maherkala.Volley.getToken;

/**
 * A simple {@link Fragment} subclass.
 */
public class FourFragment extends Fragment {




    public FourFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_four, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ConstraintLayout BasketLayout = getView().findViewById(R.id.Basket_layout);
        ProgressBar progressBar = getView().findViewById(R.id.progressBar);
        RecyclerView recyclerViewlist = getView().findViewById(R.id.RecyclerView);
        TextView emptyText = getView().findViewById(R.id.emptyText);

        emptyText.setVisibility(View.GONE);

        //end Check Login
        getToken token = new getToken();

        if (token.Ok(getActivity())) {
            getHistoryBasket historyBasket = new getHistoryBasket();
            historyBasket.get_Items(getActivity(), progressBar, recyclerViewlist, emptyText, BasketLayout);
        } else {
            emptyText.setVisibility(View.VISIBLE);
            setHiddenLayout(getActivity());
        }
        //end check Login


    }

    public void setHiddenLayout(Context context) {
        ConstraintLayout BasketLayout = ((Activity) context).findViewById(R.id.Basket_layout);
        ProgressBar progressBar = ((Activity) context).findViewById(R.id.progressBar);
        try {
        BasketLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        } catch (Exception e) {
            Log.i("mohsenjamali", "setHiddenLayoutError: " + e);
        }
    }

    public void setShowLayout(Context context) {
        ConstraintLayout BasketLayout = ((Activity) context).findViewById(R.id.Basket_layout);
        try {
            BasketLayout.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Log.i("mohsenjamali", "setShowLayoutError: " + e);
        }

    }
}
