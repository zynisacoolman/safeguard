package com.example.zyn.safeguard.Activity;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zyn.safeguard.R;
import com.example.zyn.safeguard.Utils.AutoInstallUtils;
import com.example.zyn.safeguard.Utils.StreamUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashActivity extends BaseActivity {
    private static final int UPDATE_MESSAGE = 1;
    private static final int URL_ERR_MESSAGE = 0;
    private static final int CONN_ERR_MESSAGE = 2;
    private static final int JSON_ERR_MESSAGE = 3;
    private static final int GOMAIN_MESSAGE = 4;


    SharedPreferences sharedPreferences;
    private static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 1 ;
    //服务器返回信息
    private TextView TVversion;
    private String mVersionName;
    private double mVersionCode;
    private String mDiscription;
    private String mDownloadUrl;
    private Boolean mCanPass;
    private ProgressBar PBapkDownload;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg){
            switch (msg.what) {
                case 1:
                    showUpdateDialog();
                    break;
                case 2:
                    Toast.makeText(SplashActivity.this, "网络连接发生错误", Toast.LENGTH_SHORT).show();
                    goMainActivity();
                    break;
                case 3:
                    Toast.makeText(SplashActivity.this, "内部错误：json串格式出现错误", Toast.LENGTH_SHORT).show();
                    goMainActivity();
                    break;
                case 0:
                    Toast.makeText(SplashActivity.this, "内部错误：网址发生错误", Toast.LENGTH_SHORT).show();
                    goMainActivity();
                    break;
                case 4:
                    goMainActivity();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //1.找到需要绑定动画事件的控件
        View view = findViewById(R.id.rlSplash);
        //2.绑定透明度变化的动画
        AlphaAnimation anim = new AlphaAnimation(0.3f, 1.0f);
        anim.setDuration(3000);
        view.startAnimation(anim);
        //3.设置动画完成后响应事件
//        anim.setAnimationListener(am);

        sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);


        TVversion = (TextView) findViewById(R.id.versionName);
        TVversion.setText("版本号:" + getVersionName());

        PBapkDownload = (ProgressBar)findViewById(R.id.dlprogressBar);
        PBapkDownload.setVisibility(View.GONE);

        boolean auto_update = sharedPreferences.getBoolean("auto_update", true);
        if(auto_update == true){
            checkVersion();
        }else{
               mHandler.sendEmptyMessageDelayed(GOMAIN_MESSAGE,2000);
        }
    }
//    private Animation.AnimationListener am = new Animation.AnimationListener() {
//        @Override
//        public void onAnimationEnd(Animation animation) {
//            //动画执行结束的时候去主页 动画完成后
//
//        }
//        @Override
//        public void onAnimationStart(Animation animation) {
//        }
//
//        @Override
//        public void onAnimationRepeat(Animation animation) {
//        }
//    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Toast.makeText(SplashActivity.this,"取消安装",Toast.LENGTH_LONG);
        goMainActivity();
        super.onActivityResult(requestCode,requestCode,data);
    }
    //权限请求回调函数
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    apkDownload();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    //获取versionname
    private String getVersionName() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String versionName = packageInfo.versionName;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    //
    private int getVersionCode() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            int versionCode = packageInfo.versionCode;
            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }


    private void checkVersion() {

        new Thread() {
            @Override
            public void run() {
                //获取消息
                Message msg = Message.obtain();
                HttpURLConnection conn = null;
                try {
                    //本机地址用localhost，模拟器访问地址时用10.0.2.2地址来替换,如果模拟器访问localhost将访问本虚拟机上运行的tomcat
                    URL url = new URL("http://10.0.2.2:8080/update.json");
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    conn.setReadTimeout(5000);
                    conn.connect();

                    int respNum = conn.getResponseCode();
                    if (respNum == 200) {
                        InputStream ips = conn.getInputStream();
                        String str = StreamUtils.readFromStream(ips);


                        JSONObject jso = new JSONObject(str);
                        mVersionName = (String) jso.get("versionName");
                        mVersionCode = (double) jso.get("versionCode");
                        mDiscription = (String) jso.get("discription");
                        mDownloadUrl = (String) jso.get("downloadUrl");
                        mCanPass = (Boolean) jso.get("canPass");
                        System.out.print("版本号"+mVersionCode);
                        System.out.println("版本描述：" + mDiscription);
                        if (mVersionCode > getVersionCode()) {
                            msg.what = UPDATE_MESSAGE;
                        }else{
                            msg.what = GOMAIN_MESSAGE;
                        }
                    }
                } catch (MalformedURLException e) {
                    //url错误异常
                    msg.what = URL_ERR_MESSAGE;
                    e.printStackTrace();
                } catch (IOException e) {
                    //网络错误异常
                    msg.what = CONN_ERR_MESSAGE;
                    e.printStackTrace();
                } catch (JSONException e) {
                    msg.what = JSON_ERR_MESSAGE;
                    e.printStackTrace();
                } finally {
                    mHandler.sendMessage(msg);
                    conn.disconnect();
                }
            }
        }.start();
    }

    protected void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("最近版本: " + mVersionName).setMessage(mDiscription).setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //判断是否有外部存储卡
                if (android.os.Environment.getExternalStorageState().equals(
                        android.os.Environment.MEDIA_MOUNTED)) {
                    //注意下面代码是关于Android build_sdk版本判断的，为什么加判断呢，是基于6.0Android所加的运行时权限判定所修改的代码
                    /**
                     * 如果编译sdk版本超过6.0，也是就是Android M的时候，检查是否获取权限
                     * */
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        int permissionCheck = ContextCompat.checkSelfPermission(SplashActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        if (PackageManager.PERMISSION_GRANTED == permissionCheck){
                            apkDownload();
                        }else{
                            ActivityCompat.requestPermissions(SplashActivity.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
                        }
                    }

                }else{
                    System.out.println("no SDCard");
                }
            }
        }).setNegativeButton("以后再说",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                goMainActivity();
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                goMainActivity();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setEnabled((mCanPass == true) ? true : false);
        //这个方法专门监听dialog显示后的动作
//        dialog.setOnShowListener(new DialogInterface.OnShowListener(){
//                                     @Override
//                                     public void onShow(DialogInterface dialog) {
//                                         AlertDialog ad = (AlertDialog)dialog;
//                                         ad.getButton(AlertDialog.BUTTON_NEGATIVE).setEnabled((mCanPass == true) ? true : false);
//                                     }
//                                 });
    }
    private void goMainActivity(){
        Intent  intent = new Intent(SplashActivity.this,MainActivity.class);
        startActivity(intent);
        SplashActivity.this.finish();
    }
    private void apkDownload(){
        RequestParams params = new RequestParams(mDownloadUrl);
        params.setAutoRename(true);
        params.setSaveFilePath(Environment.getExternalStorageDirectory()+"/safe"+"/test.apk");
        x.http().get(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onWaiting() {

            }
            @Override
            public void onStarted() {

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                System.out.println("下载进度："+current+"/"+total);
                PBapkDownload.setProgress((int)current);
            }
            @Override
            public void onSuccess(File result) {
                Toast.makeText(SplashActivity.this,"正在开始安装..",Toast.LENGTH_LONG).show();
                PBapkDownload.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFinished() {
                PBapkDownload.setVisibility(View.GONE);
                AutoInstallUtils.setUrl(Environment.getExternalStorageDirectory()
                        + "/safe/test.apk");
                AutoInstallUtils.install(SplashActivity.this);
            }
        });
    }
}
