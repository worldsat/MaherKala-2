package ir.maherkala.maherkala.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import ir.maherkala.maherkala.Engine.BackMethodMiddleCategory;
import ir.maherkala.maherkala.OnLoadMoreListener;
import ir.maherkala.maherkala.R;
import ir.maherkala.maherkala.Volley.getCategoryMiddleArrayLengh;


public class getCategoryMiddleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private List<String> ImageItems, IdItems, NameItems;
    private OnLoadMoreListener mOnLoadMoreListener;
    //----------------
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private Context context;

    private RecyclerView mRecyclerViewlist;
    private DecimalFormat formatter = new DecimalFormat("###,###,###,###");
    private SharedPreferences list_food_number;


    public getCategoryMiddleAdapter(final Context context, List<String> ImageItems, List<String> IdItems, List<String> NameItems, RecyclerView recyclerViewlist) {
        this.context = context;
        this.ImageItems = ImageItems;
        this.IdItems = IdItems;
        this.NameItems = NameItems;
        this.mRecyclerViewlist = recyclerViewlist;


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

        return ImageItems.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;

    }

    //--------------------------------------------------------MyViewHolder----------------------------------------------
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.itemview_layout_category, parent, false);
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

            // Log.i("mohsenjamali", "onBindViewHolder: " + TitleItems.get(position));
            myViewHolder.name.setText(changeNumber(NameItems.get(position)));


            // glide
            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions
                    //  .transforms(new CenterCrop(), new RoundedCorners(8))
                    .error(R.mipmap.error)
                    //.override(300, 300)
                    .placeholder(R.mipmap.error);

            Glide.with(context)
                    .load(context.getString(R.string.site) + ImageItems.get(position))
                    .apply(requestOptions)
                    .into(((MyViewHolder) holder).pic);
            //end glide


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    /**change for sadeghi method.clean slash for 3 category & delete getCategoryMiddleArrayLenght && BackMethodMiddleCategory**/


                    //  Intent intent = new Intent(context, ProductActivity.class);
//                    Intent intent = new Intent(context, CategoryMiddleActivity.class);
//                    intent.putExtra("parent", IdItems.get(position));
//                    intent.putExtra("name", NameItems.get(position));
//                    intent.putExtra("Activity","CategoryMiddle");
//                    Log.i("mohsenjamali", "adapterMiddle: " + IdItems.get(position));
//                    context.startActivity(intent);


                    BackMethodMiddleCategory backMethodMiddleCategory = new BackMethodMiddleCategory(context);
                    backMethodMiddleCategory.setPlusMiddleLevel(IdItems.get(position), NameItems.get(position));


                    getCategoryMiddleArrayLengh getCategoryMiddleArrayLengh = new getCategoryMiddleArrayLengh();
                    getCategoryMiddleArrayLengh.get_Length(context, IdItems.get(position), NameItems.get(position));

                }
            });


        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {

        return ImageItems == null ? 0 : ImageItems.size();
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


        TextView name, fee, discount;
        ImageView pic;

        // TextView card_txt = findViewById(R.id.card_basket_txt);
        private MyViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.Date_show);
            pic = itemView.findViewById(R.id.pic_show);

        }

    }


}
