package ir.maherkala.maherkala.Engine;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ManagementBasket {
    private Context context;
    private TinyDB tinyDB;
    private ArrayList<String> idList;
    private ArrayList<String> countList;
    private SnakBar snackItem = new SnakBar();

    public ManagementBasket(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
        idList = tinyDB.getListString("IdList");
        countList = tinyDB.getListString("countList");
    }

    public ArrayList<String> getProducts() {
        idList = tinyDB.getListString("IdList");
        return idList;
    }

    public ArrayList<String> getCount() {
        countList = tinyDB.getListString("countList");
        return countList;
    }

    public void insertProduct(String id, int count) {
        boolean Exist = false;
        int n = 0;
        int countBasket = 0;
        for (int i = 0; i < idList.size(); i++) {
            if (idList.get(i).equals(id)) {
                Exist = true;
                n = i;
                countBasket = Integer.valueOf(countList.get(i));
                break;
            }
        }
        if (Exist) {
            countBasket++;
            countList.set(n, String.valueOf(countBasket));
            tinyDB.putListString("countList", countList);

        } else {
            idList.add(id);
            countList.add(String.valueOf(count));
            tinyDB.putListString("IdList", idList);
            tinyDB.putListString("countList", countList);

        }
        snackItem.snakShow(context, "محصول مورد نظر به سبد خرید اضافه شد");
        idList = tinyDB.getListString("IdList");
        countList = tinyDB.getListString("countList");
        Log.i("moh3n", "insertProduct: " + idList + "    " + countList);
    }

    public void updateProduct(String id, String count) {
        for (int i = 0; i < idList.size(); i++) {
            if (idList.get(i).equals(id)) {
                countList.set(i, count);
                tinyDB.putListString("countList", countList);
                break;
            }
        }
    }

    public void deleteProduct(String id) {
        for (int i = 0; i < idList.size(); i++) {
            if (idList.get(i).equals(id)) {
                idList.remove(i);
                countList.remove(i);
                tinyDB.putListString("IdList", idList);
                tinyDB.putListString("countList", countList);
                snackItem.snakShow(context, "محصول مورد نظر از سبد خرید حذف گردید");
                break;
            }
        }
    }

    public Map<String, String> getListBasketItems() {
        idList = tinyDB.getListString("IdList");
        countList = tinyDB.getListString("countList");
        Map<String, String> params=new HashMap<>();

        for (int i = 0; i < idList.size(); i++) {

            params.put("FactorDetails",idList.get(i)+"."+countList.get(i));
        }
        return params;
    }
}
