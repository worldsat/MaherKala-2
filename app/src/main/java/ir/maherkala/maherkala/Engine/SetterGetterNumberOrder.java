package ir.maherkala.maherkala.Engine;

import android.content.Context;
import android.content.SharedPreferences;

public class SetterGetterNumberOrder {

    private String numberOrder;
    private SharedPreferences nOrder;

    public SetterGetterNumberOrder(Context context) {

        nOrder = context.getSharedPreferences("NumberOrder", 0);
    }

    public String getNumberOrder() {

        numberOrder = nOrder.getString("NumberOrder", "0");
        return numberOrder;
    }

    public void setNumberOrder(String numberOrder, String kind) {
        this.numberOrder = numberOrder;

        int n = Integer.valueOf(nOrder.getString("NumberOrder", "0"));
        if (kind.equals("+")) {
            n = n + Integer.valueOf(numberOrder);
        } else if (kind.equals("-")) {
            n = n - Integer.valueOf(numberOrder);

        }
        nOrder.edit().putString("NumberOrder", String.valueOf(n)).apply();
    }

    public void emptyNumberOrder() {
        nOrder.edit().clear().apply();
    }


}
