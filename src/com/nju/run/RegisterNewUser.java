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
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nju.run.model.User;
import com.nju.run.utils.VertifiedCodeUtils;

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
	// 确认密码框
	protected boolean isConPassEmpty = true;
	// 验证码输入框
	protected boolean isVertifiedEmpty = true;
	// 正确的验证码
	protected String vertifiedCode;
	// 电话号码
	protected String phoneNumber;
	//密码
	private String passWord;
	//确认密码
	private String conPassWord;
	//输入的验证码
	private String inputVertifiedCode;

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
		// 设置验证码
		btnVetified.setBackground(new BitmapDrawable(VertifiedCodeUtils
				.getInstance().createBitmap()));
		btnCommit = (Button) findViewById(R.id.bt_register_commit);
		// 失效注册按钮
		unEnableButton();
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
		// 确认密码框
		etConPass.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if (s.toString().length() == 0) {
					unEnableButton();
				} else {
					isConPassEmpty = false;
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
		// 验证码输入框
		etVetified.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if (s.toString().length() == 0) {
					unEnableButton();
				} else {
					isVertifiedEmpty = false;
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
				btnVetified.setBackground(new BitmapDrawable(VertifiedCodeUtils
						.getInstance().createBitmap()));
				System.out.println(VertifiedCodeUtils.getInstance().getCode());
				vertifiedCode = VertifiedCodeUtils.getInstance().getCode();
			}
		});
		btnCommit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//判断电话号码格式是否正确
				if(isPhone()){
					if(isCorrectPass()){
						if(isConPassCorrect()){
							if(isCorrectVertifiedCode()){
								Toast.makeText(getApplicationContext(), "恭喜注册成功",
										Toast.LENGTH_SHORT).show();
								creatUser();
								enterHome();
							}
						}
					}
				}
			}
		});
	}

	/**
	 * 
	 */
	protected void creatUser() {
		// TODO Auto-generated method stub
		User user = User.makeInstance();
		user.setName(phoneNumber);
		
	}

	//进入主界面
	protected void enterHome() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
	}

	//判断验证码是否正确
	protected boolean isCorrectVertifiedCode() {
		// TODO Auto-generated method stub
		inputVertifiedCode = etVetified.getText().toString().trim();
		if(inputVertifiedCode.equalsIgnoreCase(vertifiedCode)){
			return true;
		}else{
			Toast.makeText(getApplicationContext(), "验证码输入错误，请重新输入",
					Toast.LENGTH_SHORT).show();
			return false;
		}
	}

	//判断验证密码是否和密码一致
	protected boolean isConPassCorrect() {
		conPassWord = etConPass.getText().toString().trim();
		if(passWord.equals(conPassWord)){
			return true;
		}else{
			Toast.makeText(getApplicationContext(), "确认密码输入错误，请重新输入",
					Toast.LENGTH_SHORT).show();
			return false;
		}
	}

	//判断密码格式是否正确
	protected boolean isCorrectPass() {
		passWord = etPass.getText().toString().trim();
		if(passWord.length()>=6 && passWord.length()<=18){
			if(checkFormatPass()){
				return true;
			}else{
				Toast.makeText(getApplicationContext(), "密码格式不正确，请重新输入",
						Toast.LENGTH_SHORT).show();
				return false;
			}
		}else{
			Toast.makeText(getApplicationContext(), "密码长度不正确，请重新输入",
					Toast.LENGTH_SHORT).show();
			return false;
		}
	}

	/**
	 * @return
	 */
	private boolean checkFormatPass() {
		// TODO Auto-generated method stub
		for (int i = 0; i < passWord.length(); i++) {
			if(!(Character.isLetterOrDigit(passWord.charAt(i))||
					(passWord.charAt(i) == '_')||(passWord.charAt(i) == '-')))
			{
				return false;
			}
		}
		return true;
	}

	// 判断是否为电话号码
	protected boolean isPhone() {
		// TODO Auto-generated method stub
		phoneNumber = etPhone.getText().toString().trim();
		if ((phoneNumber.matches("^1[34568]\\d{9}$"))
				&& (phoneNumber.length() == 11)) {
			return true;
		} else {
			Toast.makeText(getApplicationContext(), "手机格式错误，请重新输入",
					Toast.LENGTH_SHORT).show();
			return false;
		}
	}

	private void checkEditTextIsEmpty(CharSequence s) {
		if (s.toString().length() == 0) {
			unEnableButton();
		}
	}

	// enable button state
	private void checkIsEnableButton() {
		if ((!isPhoneEmpty) && (!isPassEmpty) && (!isConPassEmpty)
				&& (!isVertifiedEmpty)) {
			btnCommit.setEnabled(true);
			btnCommit.setTextColor(Color.WHITE);
		}
	}

	// unenable button state
	private void unEnableButton() {
		if (btnCommit.isEnabled()) {
			btnCommit.setTextColor(Color.GRAY);
			btnCommit.setEnabled(false);
		}
	}
}
