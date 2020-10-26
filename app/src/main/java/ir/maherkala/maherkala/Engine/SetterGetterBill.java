package ir.maherkala.maherkala.Engine;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SetterGetterBill {

    private String PriceItem;
    private Context context;
    private SharedPreferences Bill;

    public SetterGetterBill() {


    }


    public String getPriceItem(Context context) {
        Bill = context.getSharedPreferences("Bill", 0);
        PriceItem = Bill.getString("totalItem", "0");

        return PriceItem;
    }

    public void setPriceItem(Context context, String PriceItem, String numberOrderItem, String Kind) {
        Bill = context.getSharedPreferences("Bill", 0);

        int totalPriceItemImpure = StringToInt(numberOrderItem) * StringToInt(PriceItem);

        int n = StringToInt(Bill.getString("totalItem", "0"));

        if (Kind.equals("plus")) {
            int TotalpricePure = n + totalPriceItemImpure;
            Log.i("mohsenjamali", "setPriceItem: " + n + " " + totalPriceItemImpure + " " + TotalpricePure);
            Bill.edit().putString("totalItem", IntToString(TotalpricePure)).apply();
        } else if (Kind.equals("minus")) {
            int TotalpricePure = n - totalPriceItemImpure;
            Bill.edit().putString("totalItem", IntToString(TotalpricePure)).apply();
        }
    }

    public void EmptyTotalPrice(Context context) {
        Bill = context.getSharedPreferences("Bill", 0);
        Bill.edit().clear().apply();
    }

    private int StringToInt(String number) {

        return Integer.valueOf(number);
    }

    private String IntToString(int number) {

        return String.valueOf(number);
    }

}
