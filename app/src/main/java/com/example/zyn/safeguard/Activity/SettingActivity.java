package com.example.zyn.safeguard.Activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.zyn.safeguard.R;
import com.example.zyn.safeguard.View.SettingItemView;

public class SettingActivity extends AppCompatActivity{
    SettingItemView mSettingItemView;
    SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mSettingItemView = (SettingItemView)findViewById(R.id.itm_update);
        //从sharedpreference中获取auto_update的值，后面的参数是默认值
        mPref = getSharedPreferences("config",MODE_PRIVATE);
        mPref.edit().putBoolean("auto_update",false);
        boolean autoUpdate = mPref.getBoolean("auto_update", true);
        if (autoUpdate){
            mSettingItemView.setviewById(true);
            mSettingItemView.settextViewDes("已开启自动更新");
        }else{
            mSettingItemView.setviewById(false);
            mSettingItemView.settextViewDes("已关闭自动更新");
        }
        mSettingItemView.settextView("自动更新");
        mSettingItemView.settextViewDes("已开启自动更新");
        mSettingItemView.setviewById(true);
        mSettingItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSettingItemView.getviewById()==true){
                    mSettingItemView.settextViewDes("已关闭自动更新");
                    mSettingItemView.setviewById(false);
                    mPref.edit().putBoolean("auto_update",false).commit();

                }else{
                    mSettingItemView.settextViewDes("已开启自动更新");
                    mSettingItemView.setviewById(true);
                    mPref.edit().putBoolean("auto_update",true).commit();
                }
            }
        });
    }

}
