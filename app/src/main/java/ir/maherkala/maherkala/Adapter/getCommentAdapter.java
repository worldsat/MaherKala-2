package ir.maherkala.maherkala.Adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import ir.maherkala.maherkala.Activity.ItemActivity;
import ir.maherkala.maherkala.AlertDialog.LoginSignupAlert;
import ir.maherkala.maherkala.Engine.MiladiToShamsi;
import ir.maherkala.maherkala.OnLoadMoreListener;
import ir.maherkala.maherkala.R;
import ir.maherkala.maherkala.Volley.getToken;
import ir.maherkala.maherkala.Volley.setLikeDislike;


public class getCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private List<String> FullnameItems, IdItems, TextItems, TitleItems, DateItems, LikeItems, DislikeItems;
    private OnLoadMoreListener mOnLoadMoreListener;
    //----------------
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private Context context;

    private RecyclerView mRecyclerViewlist;


    public getCommentAdapter(final Context context, List<String> FullnameItems, List<String> TitleItems, List<String> IdItems, List<String> LikeItems, List<String> DislikeItems, List<String> TextItems, List<String> DateItems, RecyclerView recyclerViewlist) {

        this.context = context;
        this.FullnameItems = FullnameItems;
        this.IdItems = IdItems;
        this.TitleItems = TitleItems;
        this.LikeItems = LikeItems;
        this.DateItems = DateItems;
        this.DislikeItems = DislikeItems;
        this.mRecyclerViewlist = recyclerViewlist;
        this.TextItems = TextItems;


//---------------------------
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerViewlist.getLayoutManager();
        mRecyclerViewlist.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;

                }
            }
        });


    }

    @Override
    public int getItemViewType(int position) {
        return FullnameItems.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    //--------------------------------------------------------MyViewHolder----------------------------------------------
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View view;

            view = LayoutInflater.from(context).inflate(R.layout.itemview_layout_comment, parent, false);

            MyViewHolder vh = new MyViewHolder(view);
            return vh;
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(context).inflate(R.layout.loading_layout, parent, false);
            LoadingViewHolder vh2 = new LoadingViewHolder(view);
            return vh2;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof MyViewHolder) {
            final MyViewHolder myViewHolder = (MyViewHolder) holder;

            MiladiToShamsi miladiToShamsi = new MiladiToShamsi();

            myViewHolder.Name.setText(changeNumber(FullnameItems.get(position)));
            myViewHolder.Text.setText(changeNumber(TextItems.get(position)));
            myViewHolder.like.setText(changeNumber(LikeItems.get(position)));
            myViewHolder.dislike.setText(changeNumber(DislikeItems.get(position)));
            Log.i("mohsenjamali", "onBindViewHolder: " + DateItems.get(position));
            myViewHolder.date.setText(miladiToShamsi.convert(DateItems.get(position)));


            myViewHolder.dislikeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int number = Integer.valueOf(DislikeItems.get(position));
                    getToken token = new getToken();
                    LoginSignupAlert loginAlert = new LoginSignupAlert();

                    if (token.Ok(context)) {
                        setLikeDislike setlike = new setLikeDislike();
                        setlike.setLike(context, false, IdItems.get(position), number, myViewHolder.dislike);
                    } else {
                        loginAlert.alertShow(context, ItemActivity.class);
                    }

                }
            });


            myViewHolder.LikeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int number = Integer.valueOf(LikeItems.get(position));

                    getToken token = new getToken();
                    LoginSignupAlert loginAlert = new LoginSignupAlert();

                    if (token.Ok(context)) {
                        setLikeDislike setlike = new setLikeDislike();
                        setlike.setLike(context, true, IdItems.get(position), number, myViewHolder.like);
                    } else {
                        loginAlert.alertShow(context, ItemActivity.class);
                    }

                }
            });


        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {

        return FullnameItems == null ? 0 : FullnameItems.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    public void setLoaded() {
        isLoading = false;
    }

    private String changeNumber(String num) {
        num = num.replaceAll("0", "۰");
        num = num.replaceAll("1", "۱");
        num = num.replaceAll("2", "۲");
        num = num.replaceAll("3", "۳");
        num = num.replaceAll("4", "۴");
        num = num.replaceAll("5", "۵");
        num = num.replaceAll("6", "۶");
        num = num.replaceAll("7", "۷");
        num = num.replaceAll("8", "۸");
        num = num.replaceAll("9", "۹");
        return num;
    }

    private String changeNumberToEN(String num) {
        num = num.replaceAll("۰", "0");
        num = num.replaceAll("۱", "1");
        num = num.replaceAll("۲", "2");
        num = num.replaceAll("۳", "3");
        num = num.replaceAll("۴", "4");
        num = num.replaceAll("۵", "5");
        num = num.replaceAll("۶", "6");
        num = num.replaceAll("۷", "7");
        num = num.replaceAll("۸", "8");
        num = num.replaceAll("۹", "9");
        return num;
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;

        private LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar1);
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {


        TextView Name, Text, like, dislike, date;
        ImageView dislikeBtn, LikeBtn;

        // TextView card_txt = findViewById(R.id.card_basket_txt);
        private MyViewHolder(View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.NameComment);
            Text = itemView.findViewById(R.id.TextComment);
            like = itemView.findViewById(R.id.likeTxt);
            dislike = itemView.findViewById(R.id.DislikeTxt);
            dislikeBtn = itemView.findViewById(R.id.DislikeCommentBtn);
            LikeBtn = itemView.findViewById(R.id.LikeCommentBtn);
            date = itemView.findViewById(R.id.DateComment);

        }

    }


}
