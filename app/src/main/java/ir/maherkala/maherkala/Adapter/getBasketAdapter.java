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

import ir.maherkala.maherkala.Activity.MainActivity;
import ir.maherkala.maherkala.Engine.ManagementBasket;
import ir.maherkala.maherkala.Engine.SetterGetterBill;
import ir.maherkala.maherkala.Engine.SetterGetterNumberOrder;
import ir.maherkala.maherkala.Engine.SnakBar;
import ir.maherkala.maherkala.Fragment.BasketFragment;
import ir.maherkala.maherkala.OnLoadMoreListener;
import ir.maherkala.maherkala.R;
import ir.maherkala.maherkala.Volley.ChangeQuantityItemBasket;
import ir.maherkala.maherkala.Volley.DeleteItemBasket;


public class getBasketAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private List<String> IdItems, QuantityItems, TitleItems, FeeItems, ImageItems;
    private TextView emptyText;
    private OnLoadMoreListener mOnLoadMoreListener;
    //----------------
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private Context context;

    private RecyclerView mRecyclerViewlist;
    private DecimalFormat formatter = new DecimalFormat("###,###,###,###");


    public getBasketAdapter(final Context context, List<String> IdItems, List<String> TitleItems, List<String> FeeItems, List<String> QuantityItems, List<String> ImageItems, TextView emptyText, RecyclerView recyclerViewlist) {
        this.context = context;
        this.IdItems = IdItems;
        this.QuantityItems = QuantityItems;
        this.TitleItems = TitleItems;
        this.FeeItems = FeeItems;
        this.mRecyclerViewlist = recyclerViewlist;
        this.emptyText = emptyText;
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

            view = LayoutInflater.from(context).inflate(R.layout.itemview_layout_basket_item, parent, false);

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

            ManagementBasket managementBasket = new ManagementBasket(context);
            QuantityItems = managementBasket.getCount();
            // glide
            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions
                    // .transforms(new CenterCrop(), new RoundedCorners(8))
                    .error(R.mipmap.error)
                    //.override(100, 100)
                    .placeholder(R.mipmap.error);

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
            myViewHolder.number_order.setText(QuantityItems.get(position));

            myViewHolder.plus_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //calculate NumberOrder
                    String ID = IdItems.get(position);
                    String Qty_new = String.valueOf(Integer.valueOf(myViewHolder.number_order.getText().toString()) + 1);

                    if (Integer.valueOf(Qty_new) < 21) {
//                        ChangeQuantityItemBasket changeQty = new ChangeQuantityItemBasket(context, IdItems, TitleItems, FeeItems, QuantityItems, ImageItems, emptyText, mRecyclerViewlist);
//                        changeQty.ChangeItem(context, myViewHolder.ProgressBarQuantity, myViewHolder.plus_btn, myViewHolder.minus_btn, ID, myViewHolder.number_order, myViewHolder.number_sefaresh, myViewHolder.fee, FeeItems.get(position), Qty_new, true, myViewHolder.getAdapterPosition(), emptyText);
//
//
//                        //calculate Price
//                        SetterGetterBill SetterGetterBill = new SetterGetterBill();
//                        SetterGetterBill.setPriceItem(context, FeeItems.get(position), "1", "plus");
                        QuantityItems.add(position, Qty_new);
                        ManagementBasket managementBasket = new ManagementBasket(context);
                        managementBasket.updateProduct(ID, Qty_new);
                        notifyDataSetChanged();
                        ChangeItem(context, myViewHolder.ProgressBarQuantity, myViewHolder.plus_btn, myViewHolder.minus_btn, ID, myViewHolder.number_order, myViewHolder.number_sefaresh, myViewHolder.fee, FeeItems.get(position), Qty_new, true, myViewHolder.getAdapterPosition(), emptyText);

                    }
                }
            });


            myViewHolder.minus_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String ID = IdItems.get(position);

                    String Qty_new = String.valueOf(Integer.valueOf(myViewHolder.number_order.getText().toString()) - 1);
                    if (Integer.valueOf(Qty_new) > 0) {
                        //calculate NumberOrder
//                        ChangeQuantityItemBasket changeQty = new ChangeQuantityItemBasket(context, IdItems, TitleItems, FeeItems, QuantityItems, ImageItems, emptyText, mRecyclerViewlist);
//                        changeQty.ChangeItem(context, myViewHolder.ProgressBarQuantity, myViewHolder.plus_btn, myViewHolder.minus_btn, ID, myViewHolder.number_order, myViewHolder.number_sefaresh, myViewHolder.fee, FeeItems.get(position), Qty_new, true, myViewHolder.getAdapterPosition(), emptyText);
//
//
//                        //calculate Price
//                        SetterGetterBill SetterGetterBill = new SetterGetterBill();
//                        SetterGetterBill.setPriceItem(context, FeeItems.get(position), "1", "minus");
                        QuantityItems.add(position, Qty_new);
                        ManagementBasket managementBasket = new ManagementBasket(context);
                        managementBasket.updateProduct(ID, Qty_new);
                        notifyDataSetChanged();
                        ChangeItem(context, myViewHolder.ProgressBarQuantity, myViewHolder.plus_btn, myViewHolder.minus_btn, ID, myViewHolder.number_order, myViewHolder.number_sefaresh, myViewHolder.fee, FeeItems.get(position), Qty_new, false, myViewHolder.getAdapterPosition(), emptyText);


                    }
                }
            });

            myViewHolder.ProgressBarDelete.setVisibility(View.GONE);
            myViewHolder.ProgressBarQuantity.setVisibility(View.GONE);

            //set delete button
            myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String ID = IdItems.get(position);
                    String Qty = myViewHolder.number_order.getText().toString();

                    //deleteItem with contructor
//                    DeleteItemBasket DeleteItem = new DeleteItemBasket(context, IdItems, TitleItems, FeeItems, QuantityItems, ImageItems, emptyText, mRecyclerViewlist);
//                    DeleteItem.DeleteItem(context, FeeItems.get(position), myViewHolder.ProgressBarDelete, ID, myViewHolder.delete, Qty, myViewHolder.getAdapterPosition(), emptyText);

//                    remove(myViewHolder.getAdapterPosition());
                    QuantityItems.remove(position);

                    ManagementBasket managementBasket = new ManagementBasket(context);
                    managementBasket.deleteProduct(ID);

                    ChangeItem(context, myViewHolder.ProgressBarQuantity, myViewHolder.plus_btn, myViewHolder.minus_btn, ID, myViewHolder.number_order, myViewHolder.number_sefaresh, myViewHolder.fee, FeeItems.get(position), Qty, false, myViewHolder.getAdapterPosition(), emptyText);

//                    notifyDataSetChanged();
                    remove(position);
                    if (IdItems.size() == 0) {
                        BasketFragment basketFragment = new BasketFragment();
                        basketFragment.setHiddenLayout(context);
                        emptyText.setVisibility(View.VISIBLE);
                    }
                }
            });

            //Hide line for last record
            if ((myViewHolder.getAdapterPosition() + 1) == IdItems.size()) {
                myViewHolder.Line.setVisibility(View.GONE);

            }
            Log.i("mohsenjamali", "onBindViewHolder: " + (myViewHolder.getAdapterPosition() + 1) + "  " + IdItems.size());
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
//        QuantityItems.remove(position);
        FeeItems.remove(position);
        ImageItems.remove(position);
        notifyDataSetChanged();
//        notifyItemRemoved(position);
//        notifyItemRangeChanged(position, TitleItems.size());
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


        TextView Title, description, fee, discount, number_order, number_sefaresh;
        ImageView pic, plus_btn, minus_btn, delete;
        ProgressBar ProgressBarDelete, ProgressBarQuantity;
        View Line;


        private MyViewHolder(View itemView) {
            super(itemView);

            delete = itemView.findViewById(R.id.delete);
            ProgressBarDelete = itemView.findViewById(R.id.progressBarDelete);
            ProgressBarQuantity = itemView.findViewById(R.id.progressBarQuantity);
            Title = itemView.findViewById(R.id.Date_show);
            number_sefaresh = itemView.findViewById(R.id.Price_show);
            pic = itemView.findViewById(R.id.pic_show);
            fee = itemView.findViewById(R.id.fee_show);
            plus_btn = itemView.findViewById(R.id.plus_icon);
            minus_btn = itemView.findViewById(R.id.minus_icon);
            number_order = itemView.findViewById(R.id.number_order);
            Line = itemView.findViewById(R.id.Line);

        }

    }
    public void ChangeItem(final Context context, final ProgressBar ProgressBar, final ImageView plusBtn, final ImageView minusBtn, final String Product_Id, final TextView BadgeNumberItem, final TextView NumberOrderItem, final TextView PriceItem, final String PriceEachItem, final String Qty, final boolean plus, final int AdapterPosition, final TextView emptyText) {

        plusBtn.setEnabled(false);
        minusBtn.setEnabled(false);



        final DecimalFormat formatter = new DecimalFormat("###,###,###,###");


        SetterGetterNumberOrder setterGetter = new SetterGetterNumberOrder(context);
        SnakBar snackItem = new SnakBar();


        //tashkhis plus ya minus baraye BadgeNumber
        if (plus) {
            setterGetter.setNumberOrder("1", "+");
            //calculate Price
            SetterGetterBill SetterGetterBill = new SetterGetterBill();
            SetterGetterBill.setPriceItem(context, PriceEachItem, "1", "plus");

        } else {
            setterGetter.setNumberOrder("1", "-");
            //calculate Price
            SetterGetterBill SetterGetterBill = new SetterGetterBill();
            SetterGetterBill.setPriceItem(context, PriceEachItem, "1", "minus");
        }
        BadgeNumberItem.setText(Qty);

        //update BadgeNumber
        MainActivity m = new MainActivity();
        m.setBadgeCounter(context);

        //update Price Bill Impure & Pure
        BasketFragment Basket = new BasketFragment();
        Basket.setPriceBill(context);


        //show empty text
        if (setterGetter.getNumberOrder().equals("0")) {
            emptyText.setVisibility(View.VISIBLE);
        } else {
            emptyText.setVisibility(View.GONE);
        }


        //update numberOrderItem && PriceItem
        String str1 = formatter.format(Long.valueOf(PriceEachItem) * Long.valueOf(Qty)) + context.getString(R.string.currency);
        PriceItem.setText(str1);

        String str2 = Qty + " x " + (formatter.format(Long.valueOf(PriceEachItem)));
        NumberOrderItem.setText(str2);


        // Log.i("mohsenjamali", "onResponseSetter: " + setterGetter.getNumberOrder());
        plusBtn.setEnabled(true);
        minusBtn.setEnabled(true);

    }

}
