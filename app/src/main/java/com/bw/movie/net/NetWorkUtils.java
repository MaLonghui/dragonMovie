package com.bw.movie.net;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bw.movie.R;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class NetWorkUtils {

    /*
    * 判断是否有网络连接
    * */
    public boolean isNetworkConnected(Context context) {
             if (context != null) {
                     ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                             .getSystemService(CONNECTIVITY_SERVICE);
                     NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
                     if (mNetworkInfo != null) {
                             return mNetworkInfo.isAvailable();
                        }
                 }
            return false;
         }
    /*
    * 判断wifi是否有网
    * */
    public boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }
    /*
    * 判断移动数据是否有网
    * */
    public boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }
    /*
    * 获取当前网络连接类的信息
    * */
    public static int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }

    /*
    * 安卓监控网络状态
    * */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            Log.i("NetWorkState", "Unavailabel");
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        Log.i("NetWorkState", "Availabel");
                        return true;
                    }
                }
            }
        }
        return false;
    }

        public  static BroadcastReceiver connectionReceiver = new BroadcastReceiver() {

            private Handler handler=new Handler();

            @Override
            public void onReceive(final Context context, Intent intent) {
                ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo mobNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
                    View view = LayoutInflater.from(context).inflate(R.layout.network, null);
                    ImageView imageWangqiu = view.findViewById(R.id.image_wangqiu);
                    final Dialog dialog = new AlertDialog.Builder(context).create();
                    dialog.setCancelable(false);
                    dialog.show();
                    dialog.getWindow().setContentView(view);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.cancel();
                            //Log.i(TAG, "unconnect");
                            //Toast.makeText(context, "您的网络连接已中断", Toast.LENGTH_LONG).show();
                            //弹出对话框并跳转到网络加载页面
                            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("网络提示").setMessage("网络未连接,是否进行网络设置?").setPositiveButton("设置", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = null;
                                    //判断手机系统的版本  即API大于10 就是3.0或以上版本
                                    if (android.os.Build.VERSION.SDK_INT > 10) {
                                        intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                                    } else {
                                        intent = new Intent();
                                        ComponentName component = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
                                        intent.setComponent(component);
                                        intent.setAction("android.intent.action.VIEW");
                                    }
                                    context.startActivity(intent);
                                }
                            }).setNegativeButton("重试", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    NetWorkUtils noStudoInterent = new NetWorkUtils();
                                    if (noStudoInterent.isNetworkConnected(context)){
                                        dialog.dismiss();
                                        Toast.makeText(context,"有网络",Toast.LENGTH_LONG).show();
                                    }else{
                                        builder.show();
                                        Toast.makeText(context,"无网络",Toast.LENGTH_LONG).show();
                                    }

                                }
                            }).show();
                        }
                    },5000);


                }else {
                    //Toast.makeText(context, "亲，您的网络连接已恢复", Toast.LENGTH_LONG).show();

                }
            }
        };
}
