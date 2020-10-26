package ir.maherkala.maherkala.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import ir.maherkala.maherkala.Engine.SnakBar;
import ir.maherkala.maherkala.R;
import ir.maherkala.maherkala.Volley.getSearch;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThreeFragment extends Fragment {

    private RecyclerView recyclerViewlist;
    private getSearch saerch;
    private EditText search_edt;
    private ImageView search_btn, searchFullIcon;
    private ProgressBar progressBar;
    private TextView nothing_search;


    public ThreeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_three, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        saerch = new getSearch();
        final SnakBar snackItem = new SnakBar();

        recyclerViewlist = getView().findViewById(R.id.View1);

        searchFullIcon = getView().findViewById(R.id.search_full_icon);


        nothing_search = getView().findViewById(R.id.nothing_search);
        nothing_search.setVisibility(View.GONE);

        progressBar = getView().findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.GONE);


        search_btn = getView().findViewById(R.id.search_btn);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (search_edt.getText().toString().isEmpty()) {

                    snackItem.snakShow(getActivity(), getString(R.string.fullText));

                } else if (search_edt.getText().toString().length() < 3) {

                    snackItem.snakShow(getActivity(), getString(R.string.shortText));
                } else {
                    saerch.get_banners(getActivity(), progressBar, recyclerViewlist, search_edt.getText().toString(), nothing_search, "search");
                    searchFullIcon.setVisibility(View.GONE);
                }
            }
        });


        search_edt = getView().findViewById(R.id.search_edt);
        search_edt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (search_edt.getText().toString().isEmpty()) {

                        snackItem.snakShow(getActivity(), getString(R.string.fullText));
                    } else if (search_edt.getText().toString().length() < 3) {

                        snackItem.snakShow(getActivity(), getString(R.string.shortText));
                    } else {
                        saerch.get_banners(getActivity(), progressBar, recyclerViewlist, search_edt.getText().toString(), nothing_search, "search");
                        searchFullIcon.setVisibility(View.GONE);
                    }
                }
                return false;
            }
        });

    }
}
