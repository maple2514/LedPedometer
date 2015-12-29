/**
 *---------------------------------------------------
 * 项目名称： LedPedometer
 * Copyright: © 2015 Sun Quan,Computer Science,NJU University 
 * @author Sun Quan
 * @version 1.0
 * @date 2015年12月29日 下午8:40:57
 * @since JDK 1.7.0_67
 * LoginActivity.java 
 *----------------------------------------------------
 */
package com.nju.run;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}
	//
	public void loginNewUser(View view){
		Intent i = new Intent(this,RegisterNewUser.class);
		startActivity(i);
	}
}
