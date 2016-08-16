package com.example.zyn.safeguard.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zyn.safeguard.R;
import com.example.zyn.safeguard.Utils.SharedPreferencesUtils;
import com.example.zyn.safeguard.Utils.UserPermissionRequestUtils;
import com.example.zyn.safeguard.View.SettingItemView;

/**
 * 第2个设置向导页
 * 
 * @author Zhangyunan
 * 
 */
public class Setup2Activity extends Activity {
	private static final int MY_PERMISSIONS_READ_PHONE_STATE = 2 ;
	SettingItemView simView;

	CheckBox cb;
	TextView tv;
//	private static final int REQUEST_CODE = 0; // 请求码
//	String PERMISSION = Manifest.permission.READ_PHONE_STATE;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup2);
		cb = (CheckBox)findViewById(R.id.cb_status);
		tv = (TextView)findViewById(R.id.tv_disc);
		if (SharedPreferencesUtils.haveParam(Setup2Activity.this,"sim")){

			cb.setChecked(true);
			tv.setText(R.string.item_simbundle_descOn);

		}else {
			cb.setChecked(false);
			tv.setText(R.string.item_simbundle_descOff);
		}

		simView = (SettingItemView)findViewById(R.id.siv_sim);
		simView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(cb.isChecked()){
					cb.setChecked(false);
					tv.setText(R.string.item_simbundle_descOff);
					SharedPreferencesUtils.delParam(Setup2Activity.this,"sim");
					SharedPreferencesUtils.delParam(Setup2Activity.this,"protect");
				}else{
					if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
						int permissionCheck = ContextCompat.checkSelfPermission(Setup2Activity.this,Manifest.permission.READ_PHONE_STATE);
						if (PackageManager.PERMISSION_GRANTED == permissionCheck){
							bundleSIM();
						}else{
							ActivityCompat.requestPermissions(Setup2Activity.this,
									new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.RECEIVE_BOOT_COMPLETED},
									MY_PERMISSIONS_READ_PHONE_STATE);
						}
					}
				}
			}
		});
	}
	/**
	 * 绑定simcode
	 */
	private void bundleSIM() {
		cb.setChecked(true);
		tv.setText(R.string.item_simbundle_descOn);
		TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		String simSerialNumber = tm.getSimSerialNumber();// 获取sim卡序列号
		SharedPreferencesUtils.setParam(Setup2Activity.this,"sim",simSerialNumber);
		SharedPreferencesUtils.setParam(Setup2Activity.this,"protect",true);
	}
//	权限请求回调函数
	@Override
	public void onRequestPermissionsResult(int requestCode,
										   String permissions[], int[] grantResults) {

		switch (requestCode) {
			case MY_PERMISSIONS_READ_PHONE_STATE: {
				// If request is cancelled, the result arrays are empty.
				if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					bundleSIM();
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
	// 下一页
	public void next(View view) {
		startActivity(new Intent(this, Setup3Activity.class));
		finish();

		// 两个界面切换的动画
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);// 进入动画和退出动画
	}

	// 上一页
	public void previous(View view) {
		startActivity(new Intent(this, Setup1Activity.class));
		finish();

		// 两个界面切换的动画
		overridePendingTransition(R.anim.tran_previous_in,
				R.anim.tran_previous_out);// 进入动画和退出动画
	}
}
