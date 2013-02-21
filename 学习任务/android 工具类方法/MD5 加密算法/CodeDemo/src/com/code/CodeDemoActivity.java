package com.code;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CodeDemoActivity extends Activity {
	private EditText username;
	private EditText password;
	private Button save;
	private Button login;
	private String strUsername;
	private String strPassword;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        initMD5Views();
        
    }

	private void initMD5Views() {
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		save = (Button) findViewById(R.id.save);
		login = (Button) findViewById(R.id.login);
		save.setOnClickListener(listener);
		login.setOnClickListener(listener);
	}
	private OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			strUsername = username.getText().toString();
			strPassword = CodeUtils.encrypt(password.getText().toString());
			switch (v.getId()) {
			case R.id.save:
				// 将数据保存到 SharedPreferences，loginvalue是保存文件的名称
				SharedPreferences pre = getSharedPreferences("loginvalue", 
						MODE_WORLD_WRITEABLE);
System.out.println("username:" + strUsername + " -- password:" + strPassword);
				if(!"".equals(strUsername) && !"".equals(strPassword)) {
					pre.edit().putString("username", strUsername).putString("password", 
							strPassword).commit(); 
					Toast.makeText(getApplicationContext(), "保存成功!", 
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(), "用户名或者密码不能为空!", 
							Toast.LENGTH_SHORT).show();
				}
				break;
			case R.id.login:
				// 从 SharedPreferences 取出数据
				SharedPreferences sp = getSharedPreferences("loginvalue", 
						MODE_WORLD_READABLE);
				String loginuser = sp.getString("username", null);
				String loginpass = sp.getString("password", null);
				if(!"".equals(strUsername) && !"".equals(strPassword)) {
					if(loginuser.equals(strUsername) && loginpass.equals(strPassword)) {
						Intent intent = new Intent(CodeDemoActivity.this, LoginActivity.class);
						startActivity(intent);
					} else {
						Toast.makeText(getApplicationContext(), "用户名或者密码不正确!", 
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(getApplicationContext(), "用户名或者密码不能为空!", 
							Toast.LENGTH_SHORT).show();
				}
				break;
			}
		}
	};
    
}
