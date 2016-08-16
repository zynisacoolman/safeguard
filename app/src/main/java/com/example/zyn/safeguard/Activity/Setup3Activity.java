package com.example.zyn.safeguard.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.zyn.safeguard.R;

/**
 * 第3个设置向导页
 * 
 * @author Kevin
 * 
 */
public class Setup3Activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup3);
	}

	// 下一页
	public void next(View view) {
		startActivity(new Intent(this, Setup4Activity.class));
		finish();

		// 两个界面切换的动画
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);// 进入动画和退出动画
	}

	// 上一页
	public void previous(View view) {
		startActivity(new Intent(this, Setup2Activity.class));
		finish();

		// 两个界面切换的动画
		overridePendingTransition(R.anim.tran_previous_in,
				R.anim.tran_previous_out);// 进入动画和退出动画
	}
}
