/**
 *---------------------------------------------------
 * 项目名称： LedPedometer
 * Copyright: © 2015 Sun Quan,Computer Science,NJU University 
 * @author Sun Quan
 * @version 1.0
 * @date 2015年12月29日 下午8:40:57
 * @since JDK 1.7.0_67
 * RegisterNewUser.java 
 *----------------------------------------------------
 */

package com.nju.run;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterNewUser extends Activity {

	// 电话输入框
	private EditText etPhone;
	// 密码
	private EditText etPass;
	// 密码确认
	private EditText etConPass;
	// 验证码
	private EditText etVetified;
	// 发送验证码
	private Button btnVetified;
	// 注册按钮
	private Button btnCommit;
	// 判断phone输入框是否有内容
	private boolean isPhoneEmpty = true;
	// 判断pass输入框是否有内容
	private boolean isPassEmpty = true;
	// 验证码
	protected String vertifiedCode;
	// 电话号码
	protected String phoneNumber;
	private int count = 60;
	//验证码按钮是否有效
	protected boolean isBtnVetifiedUnEnalbe =false;
	protected Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// 循环处理 等待验证码失效时间美妙减1
			if(count>0){
				btnVetified.setText("重新发送("+count+"s)");
				count--;
				handler.sendEmptyMessageDelayed(0, 1000);
			}else{
				btnVetified.setEnabled(true);
				btnVetified.setText("发送验证码");
			}
		}
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		etPhone = (EditText) findViewById(R.id.et_register_phone);
		etPass = (EditText) findViewById(R.id.et_register_pass);
		etConPass = (EditText) findViewById(R.id.et_register_confirm_pass);
		etVetified = (EditText) findViewById(R.id.et_register_vetified_code);
		btnVetified = (Button) findViewById(R.id.bt_register_vetified_code);
		btnCommit = (Button) findViewById(R.id.bt_register_commit);
		btnCommit.setEnabled(false);
		// 监听输入号码框
		etPhone.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if (s.toString().length() == 0) {
					unEnableButton();
				} else {
					isPhoneEmpty = false;
				}
				checkIsEnableButton();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				checkEditTextIsEmpty(s);
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				checkEditTextIsEmpty(s);
			}
		});
		// 密码框内容监听
		etPass.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if (s.toString().length() == 0) {
					unEnableButton();
				} else {
					isPassEmpty = false;
				}
				checkIsEnableButton();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				checkEditTextIsEmpty(s);
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				checkEditTextIsEmpty(s);
			}
		});
		btnVetified.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 获取验证码
				vertifiedCode = getVertifiedCode();
				// 判断是否为手机号码
				phoneNumber = etPhone.getText().toString().trim();
				if (isPhone()) {
					// 发送短信
					sentVertifiedCodeToPhoneNumber();
					//使按钮无效
					if(btnVetified.isEnabled()){
						btnVetified.setEnabled(false);
						count = 60;
						//isBtnVetifiedUnEnalbe =true;
					}
					//开启线程更改状态
					handler.sendEmptyMessageDelayed(0, 0);
				}
			}
		});
	}

//	//显示重新发送
//	protected void updateVertifiedButtonState() {
//		// TODO Auto-generated method stub
//		
//		new Thread(new Runnable() {
//			int count = 60;
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				while(count>0){
//					btnVetified.setText("重新发送("+count+"s)");
//					SystemClock.sleep(1000);
//					count--;
//				}
//			}
//		}).start();
//	}

	// 发送验证码
	protected void sentVertifiedCodeToPhoneNumber() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			@Override
			public void run() {
				SmsManager smsManager = SmsManager.getDefault();
				String content = getResources().getString(R.string.vertifiedCodeContent);
				content = String.format(content, vertifiedCode);
				smsManager.sendTextMessage(phoneNumber, // 收件人的号码
						null, // 短信中心号码
						content, null, // 如果发送成功, 回调此广播, 通知我们.
						null); // 当对方接收成功, 回调此广播.
			}
		}).start();
	}

	// 判断是否为电话号码
	protected boolean isPhone() {
		// TODO Auto-generated method stub
		if (TextUtils.isEmpty(phoneNumber)) {
			Toast.makeText(getApplicationContext(), "手机号码为空，请重新输入",
					Toast.LENGTH_SHORT).show();
			return false;
		} else if ((phoneNumber.matches("^1[34568]\\d{9}$"))
				&& (phoneNumber.length() == 11)) {
			return true;
		} else {
			Toast.makeText(getApplicationContext(), "手机格式错误，请重新输入",
					Toast.LENGTH_SHORT).show();
			return false;
		}
	}

	// 随机生成验证码
	protected String getVertifiedCode() {
		// TODO Auto-generated method stub
		String result = "";
		for (int i = 0; i < 6; i++) {
			result += (int) (Math.random() * 10);
		}
		return null;
	}

	private void checkEditTextIsEmpty(CharSequence s) {
		if (s.toString().length() == 0) {
			unEnableButton();
		}
	}

	// enable button state
	private void checkIsEnableButton() {
		if ((!isPhoneEmpty) && (!isPassEmpty)) {
			btnCommit.setEnabled(true);
		}
	}

	// unenable button state
	private void unEnableButton() {
		if (btnCommit.isEnabled()) {
			btnCommit.setEnabled(false);
		}
	}
}
