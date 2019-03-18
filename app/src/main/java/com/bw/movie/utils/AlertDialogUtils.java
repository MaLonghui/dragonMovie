package com.bw.movie.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.MainActivity;
import com.bw.movie.activity.ShowActivity;
import com.bw.movie.activity.login.LoginActivity;
import com.bw.movie.app.MyApp;
import com.bw.movie.fragment.mine.MineFragment;

public class AlertDialogUtils {

    private static AlertDialog.Builder builder;

    public static void AlertDialogLogin(final Context context) {
        builder = new AlertDialog.Builder(context)
                .setIcon(R.mipmap.bitmap_title)
                .setTitle("请选择")
                .setMessage("您尚未登录,是否现在去登陆?")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.startActivity(new Intent(context, LoginActivity.class));
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context,"您以游客身份进入",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context, ShowActivity.class);
                        context.startActivity(intent);
                    }
                });
        builder.create();
        builder.show();
    }
}
