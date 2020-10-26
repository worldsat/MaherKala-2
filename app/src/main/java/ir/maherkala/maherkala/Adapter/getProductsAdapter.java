package ir.maherkala.maherkala.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.DecimalFormat;
import java.util.List;

import ir.maherkala.maherkala.Activity.ItemActivity;
import ir.maherkala.maherkala.OnLoadMoreListener;
import ir.maherkala.maherkala.R;


public class getProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private List<String> ImageItems, IdItems, DescriptionItems, TitleItems, FeeItems, IntroItems, QuantityItems;
    private List<String> FeeOffItems, LikeItems, TotalVotesItems, TotalCommentItems, ThumbnailItems, OtherItems;
    private OnLoadMoreListener mOnLoadMoreListener;
    //----------------
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private Context context;
    private String Direction, Parent, CategoryName, Activity;

    private RecyclerView mRecyclerViewlist;
    private DecimalFormat formatter = new DecimalFormat("###,###,###,###");


    public getProductsAdapter(final Context context, List<String> ImageItems, List<String> FeeOffItems, List<String> IdItems, List<String> DescriptionItems, List<String> TitleItems, List<String> FeeItems, List<String> IntroItems, List<String> QuantityItems, List<String> LikeItems, List<String> TotalVotesItems, List<String> TotalCommentItems, List<String> ThumbnailItems, List<String> OtherItems, String Direction, String Parent, String Category_name, String Activity, RecyclerView recyclerViewlist) {
        this.context = context;

        this.ImageItems = ImageItems;
        this.FeeOffItems = FeeOffItems;
        this.IdItems = IdItems;
        this.DescriptionItems = DescriptionItems;
        this.TitleItems = TitleItems;
        this.FeeItems = FeeItems;
        this.IntroItems = IntroItems;
        this.mRecyclerViewlist = recyclerViewlist;
        this.QuantityItems = QuantityItems;
        this.Direction = Direction;
        this.Parent = Parent;
        this.CategoryName = Category_name;
        this.LikeItems = LikeItems;
        this.TotalVotesItems = TotalVotesItems;
        this.TotalCommentItems = TotalCommentItems;
        this.ThumbnailItems = ThumbnailItems;
        this.Activity = Activity;
        this.OtherItems = OtherItems;

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

        return IdItems.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;

    }

    //--------------------------------------------------------MyViewHolder----------------------------------------------
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View view;
            if (Direction.equals("Horizontal")) {
                view = LayoutInflater.from(context).inflate(R.layout.itemview_layout, parent, false);
            } else {
                view = LayoutInflater.from(context).inflate(R.layout.itemview_layout_vertical, parent, false);
            }
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


            String discount = changeNumber(formatter.format(Long.valueOf(FeeItems.get(position)))) + context.getString(R.string.currency);
            String fee = changeNumber(formatter.format(Long.valueOf(FeeItems.get(position)) - Long.valueOf(FeeOffItems.get(position))) + context.getString(R.string.currency));
            final String discount_intent = String.valueOf(Long.valueOf(FeeItems.get(position)) - Long.valueOf(FeeOffItems.get(position)));

            if (QuantityItems.get(position).equals("0")) {
                myViewHolder.discount.setVisibility(View.INVISIBLE);
                myViewHolder.fee.setText("موجود نیست");
                myViewHolder.fee.setTextColor(ContextCompat.getColor(context, R.color.red));

            } else {
                myViewHolder.fee.setText(fee);
                myViewHolder.fee.setVisibility(View.VISIBLE);
                myViewHolder.fee.setTextColor(ContextCompat.getColor(context, R.color.green_dark));
                myViewHolder.discount.setVisibility(View.VISIBLE);
                myViewHolder.discount.setText(discount);
                myViewHolder.discount.setPaintFlags(myViewHolder.discount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
            if (FeeOffItems.get(position).equals("0") && !QuantityItems.get(position).equals("0")) {
                myViewHolder.discount.setVisibility(View.INVISIBLE);
            }else{
                myViewHolder.discount.setVisibility(View.VISIBLE);
            }
            myViewHolder.Title.setText(changeNumber(TitleItems.get(position)));

            // glide
            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions
                    //.transforms(new CenterCrop(), new RoundedCorners(8))
                    .error(R.mipmap.error)
                    //.override(250, 250)
                    .placeholder(R.mipmap.error);

            Glide.with(context)
                    .load(context.getString(R.string.site) + ThumbnailItems.get(position))
                    .apply(requestOptions)
                    .into(((MyViewHolder) holder).pic);
            //end glide


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {


                        Intent intent = new Intent(context, ItemActivity.class);
                        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        intent.putExtra("image", ImageItems.get(position));
                        intent.putExtra("Id_item", IdItems.get(position));
                        intent.putExtra("Description_item", changeNumber(DescriptionItems.get(position)));
                        intent.putExtra("Discount_item", discount_intent);
                        intent.putExtra("Title_item", changeNumber(TitleItems.get(position)));
                        intent.putExtra("Fee_item", FeeItems.get(position));
                        intent.putExtra("Intro_item", IntroItems.get(position));
                        intent.putExtra("Quantity", QuantityItems.get(position));
                        intent.putExtra("Activity", Activity);
                        intent.putExtra("parent", Parent);
                        intent.putExtra("Category_Name", CategoryName);
                        intent.putExtra("LikeItems", LikeItems.get(position));
                        intent.putExtra("TotalVotesItems", TotalVotesItems.get(position));
                        intent.putExtra("TotalCommentItems", TotalCommentItems.get(position));
                        intent.putExtra("OtherImage", OtherItems.get(position).split(";"));
                        context.startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(context, "خطایی پیش آمده است لطفا دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
                        Log.i("mohsenjamali", "onClickProduct: " + e.toString());
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

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;

        private LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar1);
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Title, intro, fee, discount;
        ImageView pic;

        private MyViewHolder(View itemView) {
            super(itemView);

            Title = itemView.findViewById(R.id.Date_show);
            discount = itemView.findViewById(R.id.discount_show);
            pic = itemView.findViewById(R.id.pic_show);
            fee = itemView.findViewById(R.id.fee_show);

            if (Direction.equals("Vertical")) {
                intro = itemView.findViewById(R.id.intro_show);
            }
        }

    }


}
