package ir.maherkala.maherkala.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import ir.maherkala.maherkala.Engine.BackMethodMiddleCategory;
import ir.maherkala.maherkala.R;
import ir.maherkala.maherkala.Volley.getCategory;

/**
 * A simple {@link Fragment} subclass.
 */
public class TwoFragment extends Fragment {


    public TwoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_two, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerViewlist = getView().findViewById(R.id.View);
        ProgressBar progressBarRow = getView().findViewById(R.id.progressBarRow);

        BackMethodMiddleCategory backMethodMiddleCategory = new BackMethodMiddleCategory(getActivity());
        backMethodMiddleCategory.clearMiddleId();

        getCategory get_category = new getCategory();
        get_category.get_banners(getActivity(), progressBarRow, recyclerViewlist, "0");
    }
}
