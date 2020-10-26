package ir.maherkala.maherkala.Engine;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

public class SnakBar {
    public void snakShow(Context context, String str) {


        final Snackbar snackbar = Snackbar.make(((Activity) context).findViewById(android.R.id.content), str, Snackbar.LENGTH_SHORT);
//        snackbar.setAction("باشه", new View.OnClickListener() {
//            @Override
//            public void onClick(View vv) {
//                snackbar.dismiss();
//            }
//        });


        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/iransans_m.ttf");
        textView.setTypeface(font);
        snackbar.show();

    }
}
