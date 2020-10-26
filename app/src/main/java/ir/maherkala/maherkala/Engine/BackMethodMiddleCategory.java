package ir.maherkala.maherkala.Engine;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * this is calculator engine for save middle id & name category and used for dynamic middle category
 */

public class BackMethodMiddleCategory {
    private SharedPreferences SharedMiddleId;

    public BackMethodMiddleCategory(Context context) {
        SharedMiddleId = context.getSharedPreferences("Middle Id", 0);
    }

    public void setPlusMiddleLevel(String parent, String name) {

        for (int n = 1; n < 30; n++) {
            if (SharedMiddleId.getString("level" + String.valueOf(n), "0").equals("0")) {

                SharedMiddleId.edit().putString("level" + String.valueOf(n), parent).apply();
                SharedMiddleId.edit().putString("levelName" + String.valueOf(n), name).apply();

                break;
            }
        }


    }

    public void setMinusMiddleLevel() {

        for (int n = 30; n > 0; n--) {
            if (!SharedMiddleId.getString("level" + String.valueOf(n), "0").equals("0")) {

                SharedMiddleId.edit().remove("level" + String.valueOf(n)).apply();
                SharedMiddleId.edit().remove("levelName" + String.valueOf(n)).apply();

                break;
            }
        }

    }


    public String getLastMiddleLevel() {

        String id = "zero";
        for (int n = 30; n > 0; n--) {
            if (!SharedMiddleId.getString("level" + String.valueOf(n), "0").equals("0")) {

                id = SharedMiddleId.getString("level" + String.valueOf(n), "0");

                break;

            }
        }
        return id;
    }

    public String getLastMiddleName() {

        String id = "zero";
        for (int n = 30; n > 0; n--) {
            if (!SharedMiddleId.getString("levelName" + String.valueOf(n), "0").equals("0")) {

                id = SharedMiddleId.getString("levelName" + String.valueOf(n), "0");
                break;

            }
        }
        return id;
    }

    public Boolean totalMiddlePage() {
        if (SharedMiddleId.getString("levelName1", "0").equals("0")) {
            return true;
        } else {
            return false;
        }

    }

    public void clearMiddleId() {
        SharedMiddleId.edit().clear().apply();
    }
}
