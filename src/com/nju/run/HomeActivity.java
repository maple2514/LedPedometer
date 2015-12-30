/**
 *---------------------------------------------------
 * 项目名称： LedPedometer
 * <p>Description: </p>
 * Copyright: © 2015 Sun Quan,Computer Science,NJU University 
 * @author Sun Quan
 * @version 1.0
 * @date 2015年12月30日 下午7:08:02
 * @since JDK 1.7.0_67
 * HomeActivity.java 
 *----------------------------------------------------
*/   
package com.nju.run;

import com.nju.run.model.User;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class HomeActivity extends Activity {
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		TextView tv=(TextView) findViewById(R.id.tv_home);
		tv.setText("hello"+User.makeInstance().getName());
		
	}
}
