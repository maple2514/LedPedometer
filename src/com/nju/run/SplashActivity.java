/**
 *---------------------------------------------------
 * 项目名称： LedPedometer
 * Copyright: © 2015 Sun Quan,Computer Science,NJU University 
 * @author Sun Quan
 * @version 1.0
 * @date 2015年12月29日 下午8:40:57
 * @since JDK 1.7.0_67
 * SplashActivity.java 
 *----------------------------------------------------
 */
package com.nju.run;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.nju.run.utils.StreamTools;


public class SplashActivity extends Activity {

	protected static final String TAG = "SplashActivity";
	protected static final int ENTER_HOME = 0;
	protected static final int SHOW_UPDATE_DIALOG = 1;
	protected static final int URL_ERROR = 2;
	protected static final int NETWORK_ERROR = 3;
	protected static final int JOSN_ERROR = 4;
	//配置文件
    private SharedPreferences sp;
    //版本textView
	private TextView tv_splash_version;
	//������Ϣ
	private TextView tv_update_info;
	//版本号
	protected String Version;
	//描述信息
	protected String Description;
	protected String Apkurl;
	protected Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case SHOW_UPDATE_DIALOG:		
				Log.i(TAG, "显示升级的对话框");
				showUpdateDialog();
				break;
			case ENTER_HOME:	
				enterHome();
				
				break;
			case URL_ERROR:		
				enterHome();
				Toast.makeText(getApplicationContext(), "URL错误", Toast.LENGTH_SHORT).show();
				break;
			case NETWORK_ERROR:		
				enterHome();
				Toast.makeText(getApplicationContext(), "网络异常", Toast.LENGTH_SHORT).show();
				break;
			case JOSN_ERROR:		
				enterHome();
				Toast.makeText(getApplicationContext(), "JSON解析出错", Toast.LENGTH_SHORT).show();
				break;
			

			default:
				break;
			}
		}
	};

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        sp = getSharedPreferences("config", MODE_PRIVATE);
        tv_splash_version= (TextView) findViewById(R.id.tv_splash_version);
		tv_splash_version.setText("版本号"+getVersionName());
		tv_update_info = (TextView) findViewById(R.id.tv_update_info);
		boolean update = sp.getBoolean("update", false);
		
		if(update){
			//�����
			checkUpdate();
		}else{
			//�Զ����Ѿ��ر�
			handler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					enterHome();
				}
			}, 2000);
		}
		AlphaAnimation aa = new AlphaAnimation(0.2f, 1.0f);
		aa.setDuration(2000);
		findViewById(R.id.rl_root_splash).startAnimation(aa);
        
    }

	protected void enterHome() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
		finish();
	}

	protected void showUpdateDialog() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder=new Builder(this);
		builder.setTitle("提示升级"); 
		builder.setMessage(Description);
//		builder.setCancelable(false);//ǿ����
		builder.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub
				enterHome();
				dialog.dismiss();
			}
		});
		builder.setPositiveButton("立刻升级", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				//����APK���Ұ�װ
				if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
					//����SDK
					//affinal
					FinalHttp finalHttp = new FinalHttp();
					finalHttp.download(Apkurl, Environment.getExternalStorageDirectory().getAbsolutePath()+"/mobilesafe2.0.apk", new AjaxCallBack<File>() {

						@Override
						public void onFailure(Throwable t, int errorNo,
								String strMsg) {
							// TODO Auto-generated method stub
							t.printStackTrace();
							Toast.makeText(getApplicationContext(), "下载失败", 0);
							super.onFailure(t, errorNo, strMsg);
							
						}

						@Override
						public void onLoading(long count, long current) {
							// TODO Auto-generated method stub
							super.onLoading(count, current);
							tv_update_info.setVisibility(View.VISIBLE);
							int progress = (int) (current*100/count);
							tv_update_info.setText("下载进度："+progress+"%");
						}

						@Override
						public void onSuccess(File t) {
							// TODO Auto-generated method stub
							super.onSuccess(t);
							installAPK(t);
						}

						private void installAPK(File t) {
							// TODO Auto-generated method stub
							Intent intent =new Intent();
							intent.setAction("android.intent.action.VIEW");
							intent.addCategory("android.intent.category.DEFAULT");
							intent.setDataAndType(Uri.fromFile(t), "application/vnd.android.package-archive");
							startActivity(intent);
						}
						
					});
				}else{
					Toast.makeText(getApplicationContext(), "没有sdcard，请安装上在试",Toast.LENGTH_SHORT).show();
				}
			}
		});
		builder.setNegativeButton("下次再说", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				enterHome();
			}
		});
		builder.show();
	}

	/**
	 * ����Ƿ��и���
	 */
	private void checkUpdate() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		new Thread(){
			
			public void run() {
				Message mes = Message.obtain();
				long startTime = System.currentTimeMillis();
				try {
					//����������վ
					URL url = new URL(getString(R.string.server_url));
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(4000);
					int code=conn.getResponseCode();
					if(code==200){
						InputStream is = conn.getInputStream();
						//����ת��Ϊstring
						String result = StreamTools.readFromStream(is);
						Log.i(TAG, result);
						//json����
						JSONObject obj=new JSONObject(result);
						Version = (String) obj.get("version");
						Description = (String) obj.get("description");
						Apkurl = (String) obj.get("apkurl");
						if(getVersionName().equals(Version)){
							//�汾һ�£�������ҳ��
							mes.what = ENTER_HOME;
						}else{
							//���°汾��������Ի���
							mes.what = SHOW_UPDATE_DIALOG;
						}
					}
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					mes.what = URL_ERROR;
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					mes.what = NETWORK_ERROR;
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					mes.what = JOSN_ERROR;
					e.printStackTrace();
				}finally{
					long endTime = System.currentTimeMillis();
					long dTime = endTime-startTime;
					if(dTime<2000){
						try {
							Thread.sleep(2000-dTime);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					handler.sendMessage(mes);
				}
			};
		}.start();
	}

	/**
	 * ��ȡ�汾��
	 */
	private String getVersionName() {
		// TODO Auto-generated method stub
		try {
			PackageManager pm = getPackageManager();
			PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
