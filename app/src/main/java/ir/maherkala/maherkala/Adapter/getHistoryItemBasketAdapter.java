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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.DecimalFormat;
import java.util.List;

import ir.maherkala.maherkala.OnLoadMoreListener;
import ir.maherkala.maherkala.R;


public class getHistoryItemBasketAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private List<String> IdItems, QuantityItems, TitleItems, FeeItems, ImageItems;
    private OnLoadMoreListener mOnLoadMoreListener;
    //----------------
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private Context context;

    private RecyclerView mRecyclerViewlist;
    private DecimalFormat formatter = new DecimalFormat("###,###,###,###");


    public getHistoryItemBasketAdapter(final Context context, List<String> IdItems, List<String> TitleItems, List<String> FeeItems, List<String> QuantityItems, List<String> ImageItems, RecyclerView recyclerViewlist) {
        this.context = context;
        this.IdItems = IdItems;
        this.QuantityItems = QuantityItems;
        this.TitleItems = TitleItems;
        this.FeeItems = FeeItems;
        this.mRecyclerViewlist = recyclerViewlist;
        this.ImageItems = ImageItems;


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

    private static boolean isEven(int number) {
        return (number % 2 == 0);
    }

    @Override
    public int getItemViewType(int position) {
        return IdItems.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    //--------------------------------------------------------MyViewHolder----------------------------------------------
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View view;

            view = LayoutInflater.from(context).inflate(R.layout.itemview_layout_basket_history_item, parent, false);

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
            // glide
            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions
                    // .transforms(new CenterCrop(), new RoundedCorners(8))
                    .error(R.mipmap.error_basket)
                    .override(100, 100)
                    .placeholder(R.mipmap.error_basket);

            Glide.with(context)
                    .load(context.getString(R.string.site) + ImageItems.get(position))
                    .apply(requestOptions)
                    .into(((MyViewHolder) holder).pic);
            //end glide
            myViewHolder.Title.setText(changeNumber(TitleItems.get(position)));
            String str1 = changeNumber(formatter.format(Long.valueOf(FeeItems.get(position)) * Long.valueOf(QuantityItems.get(position))) + context.getString(R.string.currency));
            myViewHolder.fee.setText(str1);

            String str2 = QuantityItems.get(position) + " x " + changeNumber(formatter.format(Long.valueOf(FeeItems.get(position))));
            myViewHolder.number_sefaresh.setText(str2);


            //Hide line for last record
            if ((myViewHolder.getAdapterPosition() + 1) == IdItems.size()) {
                myViewHolder.Line.setVisibility(View.GONE);

            }

        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {
        return IdItems == null ? 0 : IdItems.size();
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

    public void remove(int position) {

        TitleItems.remove(position);
        IdItems.remove(position);
        QuantityItems.remove(position);
        FeeItems.remove(position);
        ImageItems.remove(position);

        notifyItemRemoved(position);
        notifyItemRangeChanged(position, TitleItems.size());
        Log.i("mohsenjamali", "remove: " + position);
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;

        private LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar1);
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {


        TextView Title, fee, discount, number_sefaresh;
        ImageView pic;

        View Line;


        private MyViewHolder(View itemView) {
            super(itemView);


            Title = itemView.findViewById(R.id.Date_show);
            number_sefaresh = itemView.findViewById(R.id.Price_show);
            pic = itemView.findViewById(R.id.pic_show);
            fee = itemView.findViewById(R.id.fee_show);
            Line = itemView.findViewById(R.id.Line);

        }

    }


}
