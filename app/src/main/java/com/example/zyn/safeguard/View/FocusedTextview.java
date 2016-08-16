package com.example.zyn.safeguard.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by zyn on 2016/7/27.
 */
public class FocusedTextview extends TextView {
    public FocusedTextview(Context context) {
        super(context);
    }
    public FocusedTextview(Context context, AttributeSet attrs){
        super(context,attrs);
    }
    public FocusedTextview(Context context,AttributeSet attrs,int defStyleAttr){
        super(context,attrs,defStyleAttr);
    }
//    public FocusedTextview(Context context,)
    //强制返回true获取焦点
    @Override
    public boolean isFocused(){
        return true;
    }
}

