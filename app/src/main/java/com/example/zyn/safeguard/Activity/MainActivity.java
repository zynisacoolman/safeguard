package com.example.zyn.safeguard.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.example.zyn.safeguard.Dialog.DialogPwdCofirm;
import com.example.zyn.safeguard.R;
import com.example.zyn.safeguard.Utils.MD5Utils;
import com.example.zyn.safeguard.Utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.AlertDialog.*;

public class MainActivity extends AppCompatActivity {


    // 图片封装为一个数组
    private int[] icon = { R.drawable.home_apps, R.drawable.home_callmsgsafe,
            R.drawable.home_netmanager, R.drawable.home_safe, R.drawable.home_settings,
            R.drawable.home_sysoptimize, R.drawable.home_taskmanager, R.drawable.home_tools,
            R.drawable.home_trojan};
    private String[] iconName = {"应用","通信安全","网络管理","安全","设置","系统优化","任务管理","工具","木马" };
//    private List<HashMap> labelList;
    private  List<Map<String, Object>> labellist;
    SharedPreferences config;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gv = (GridView)findViewById(R.id.gvMain);
        config = getSharedPreferences("config",MODE_PRIVATE);
        labellist = new ArrayList<>();
        for(int i = 0 ;i<icon.length;i++){
            Map<String,Object> map = new HashMap<>();
            map.put("icon",icon[i]);
            map.put("iconName", iconName[i]);
            labellist.add(map);
        }
        gv.setAdapter(new SimpleAdapter(this,
                labellist,
               R.layout.home_label_item,
                new String[]{"iconName","icon"},
                new int[]{R.id.tv_label_name,R.id.iv_label_pic}
        ));
        //我爱你谁谁谁
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 4:
                        Intent intent = new Intent(MainActivity.this,SettingActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        if(config.getBoolean("havaPwd",false)==false){
                            getPwdsettingDialog();
                        }else{
                            inputPwd();
                        }
                        break;
                }
            }
        });
    }
    //密码登录dialog
    protected Boolean inputPwd(){
        Builder builder = new Builder(this);
        final AlertDialog alertdialog = builder.create();

        final View viewdialog = View.inflate(this,R.layout.dailog_input_password,null);
        alertdialog.setView(viewdialog);

        alertdialog.show();
        Button btnOk=  (Button)viewdialog.findViewById(R.id.btn_ok);
        Button btnCancel = (Button)viewdialog.findViewById(R.id.btn_cancel);
        final EditText etPwd= (EditText)viewdialog.findViewById(R.id.et_password);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MD5Utils.md5Password((etPwd.getText().toString())).equals(config.getString("pwd",""))){
                        if(!config.getBoolean("configed",false)){
                            startActivity(new Intent(MainActivity.this, Setup1Activity.class));
                            alertdialog.dismiss();
                        }else{startActivity(new Intent(MainActivity.this, LostFindActivity.class));

                            System.out.println("转移到安全activity");
                            alertdialog.dismiss();
                        }
                }else
                {
                    ToastUtils.showToast(MainActivity.this,"密码不正确，请重新输入密码");
                    etPwd.selectAll();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertdialog.dismiss();
            }
        });
        viewdialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertdialog.dismiss();
            }
        });

        return false ;
    }
    //获取密码注册界面
    private void getPwdsettingDialog(){
        Builder builder = new Builder(this);
        final AlertDialog alertdialog = builder.create();

//        final View viewdialog = View.inflate(this,R.layout.dialog_pwd_confirm,null);
        DialogPwdCofirm viewdialog = new DialogPwdCofirm(this);
        viewdialog.show();
//        alertdialog.setView(viewdialog,0,0,0,0);

//        Button btnOK = (Button).findViewById(R.id.btn_confirm);
//        Button btnCancel = (Button)viewdialog.findViewById(R.id.btn_cancel);
//
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertdialog.dismiss();
//                Log.i("测试","取消按钮被点击了");
//            }
//        });

        alertdialog.show();
//        alertdialog.getButton(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertdialog.dismiss();
//            }
//        });
//        alertdialog.show();
    }
}
