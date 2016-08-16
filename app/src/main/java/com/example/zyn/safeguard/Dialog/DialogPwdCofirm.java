package com.example.zyn.safeguard.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zyn.safeguard.R;
import com.example.zyn.safeguard.Utils.MD5Utils;

public class DialogPwdCofirm extends Dialog {
    Boolean blEquel;
    String  mpwd1,mpwd2;
    SharedPreferences msp ;
    public DialogPwdCofirm(Context context) {
        super(context);

    }

    public DialogPwdCofirm(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DialogPwdCofirm(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pwd_confirm);
        Button btnOK = (Button)findViewById(R.id.btn_confirm);
        Button btnCancel = (Button)findViewById(R.id.btn_cancel);
        final EditText et1stpwd = (EditText)findViewById(R.id.et_firstpwd);
        final EditText et2ndpwd = (EditText)findViewById(R.id.et_secondpwd);
        final TextView tvWrongip= (TextView)findViewById(R.id.tv_wrongPwd);

        msp= getContext().getSharedPreferences("config",Context.MODE_PRIVATE);

        mpwd2 = et2ndpwd.getText().toString();

        et1stpwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(!hasFocus) {
                    mpwd1 = et1stpwd.getText().toString();
                }
            }
        });

        et2ndpwd.addTextChangedListener(new TextWatcher() {
            int l=0;////////记录字符串被删除字符之前，字符串的长度
            int location=0;//记录光标的位置
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                l=s.length();
                location=et2ndpwd.getSelectionStart();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (unCompletepwd2(s.toString())==false){
                        tvWrongip.setVisibility(View.VISIBLE);
                    }
                else{
                        tvWrongip.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

            }
        });
        btnOK.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mpwd1!=null&&tvWrongip.getVisibility()==View.INVISIBLE){
                    msp.edit().putString("pwd", MD5Utils.md5Password(mpwd1)).commit();
                    msp.edit().putBoolean("havaPwd",true).commit();
                    dismiss();
                }else{

                }
            }
        });
    }
    //比较未完成的pwd2 与pwd1的前几位
    protected boolean unCompletepwd2(String str){
        if(str.length() > 0){
            if(str.length()<=mpwd1.length()){
                return str.equals(mpwd1.substring(0,str.length()))==true?true:false;
            }else{
                return false;
            }

        }else{
            return true;
        }
    }

}
