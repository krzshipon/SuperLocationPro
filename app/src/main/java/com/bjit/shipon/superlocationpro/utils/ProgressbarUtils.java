package com.bjit.shipon.superlocationpro.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.ProgressBar;

import com.bjit.shipon.superlocationpro.R;
import com.bjit.shipon.superlocationpro.activity.MainActivity;

public class ProgressbarUtils {
    private final Context context;
    private Dialog progressDialog = null;
    private ProgressBar progressBar;


    public ProgressbarUtils(Context context) {
        this.context = context;
    }

    public void showPopupProgressSpinner(Boolean isShowing) {
        if (isShowing == true) {
            progressDialog = new Dialog(context);
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            progressDialog.setContentView(R.layout.circular_progressbar);
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawable(
                    new ColorDrawable(Color.TRANSPARENT));

            progressBar = (ProgressBar) progressDialog
                    .findViewById(R.id.progressbar_circular);
            progressBar.getIndeterminateDrawable().setColorFilter(
                    Color.parseColor("#ff6700"),
                    android.graphics.PorterDuff.Mode.MULTIPLY);
            progressDialog.show();
        } else if (isShowing == false) {
            progressDialog.dismiss();
        }
    }
}
