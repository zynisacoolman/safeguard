package com.example.zyn.safeguard.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zyn.safeguard.R;

/**
 * Created by zyn on 2016/7/27.
 */
public class SettingItemView extends RelativeLayout {

    private int resourceIDbool;
    private int resourceIDdes;
    private TextView mtextView;
    private TextView mtextViewDes ;
    private CheckBox mviewById;
    private String mStrtile;
    private String mStrdes;
    private Boolean mBolch;
    private int resourceID = 0;
    public SettingItemView(Context context) {
        super(context);
        init();
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.SettingItemView);
        resourceID = array.getResourceId(R.styleable.SettingItemView_text, 0);
        resourceIDdes = array.getResourceId(R.styleable.SettingItemView_textDisc,0);
        resourceIDbool = array.getResourceId(R.styleable.SettingItemView_textStatus,0);

        mStrtile = getResources().getString(resourceID);
        mStrdes = getResources().getString(resourceIDdes);
        mBolch= Boolean.valueOf(getResources().getString(resourceIDbool));
        init();
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        View.inflate(getContext(), R.layout.activity_setting_item, this);
        mtextView= (TextView) findViewById(R.id.tv_title);
        mtextViewDes = (TextView) findViewById(R.id.tv_disc);
        mviewById = (CheckBox) findViewById(R.id.cb_status);
        settextView(mStrtile);
        settextViewDes(mStrdes);
        setviewById(mBolch);
    }

    public void settextView(String id){
        mtextView.setText(id);
    }
    public void settextViewDes(String id){

//        mtextViewDes.setText((mviewById.isChecked()? R.string.item_setting_update_descOff:R.string.item_setting_update_descOn));
        mtextViewDes.setText((id));
    }
    public void  setviewById(Boolean val){
        mviewById.setChecked(val);
    }
    public boolean getviewById(){
        return mviewById.isChecked();
    }
}
