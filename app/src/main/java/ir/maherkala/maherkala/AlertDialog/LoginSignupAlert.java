package ir.maherkala.maherkala.AlertDialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import ir.maherkala.maherkala.Activity.LoginActivity;
import ir.maherkala.maherkala.R;

public class LoginSignupAlert {

    private MaterialDialog Alert_dialog;

    private void alert(final Context context, final Class className) {

        Alert_dialog = new MaterialDialog.Builder(((Activity) context))
                .customView(R.layout.alert_login_signup, false)
                .autoDismiss(false)
                .backgroundColor(Color.parseColor("#01000000"))
                .build();

        TextView login = (TextView) Alert_dialog.findViewById(R.id.save);
        TextView cancel = (TextView) Alert_dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Alert_dialog.dismiss();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, LoginActivity.class);
                intent.putExtra("className", className);
                Alert_dialog.dismiss();
                context.startActivity(intent);

            }
        });

    }

    public void alertShow(Context context, Class className) {
        alert(context, className);
        Alert_dialog.show();

    }
}
