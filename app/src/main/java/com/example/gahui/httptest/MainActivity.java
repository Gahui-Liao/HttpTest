package com.example.gahui.httptest;

import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final EditText logname = (EditText) findViewById(R.id.logname);
        final EditText password = (EditText) findViewById(R.id.password);
        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = logname.getText().toString().trim();
                final String psw = password.getText().toString().trim();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String path="http://192.168.0.101:8080/AndroidTest/mustLogin?logname="+name+"&password="+psw;
                        try {
                            try{
                                URL url = new URL(path); //新建url并实例化
                                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                                connection.setRequestMethod("GET");//获取服务器数据
                                connection.setReadTimeout(8000);//设置读取超时的毫秒数
                                connection.setConnectTimeout(8000);//设置连接超时的毫秒数
                                InputStream in = connection.getInputStream();
                                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                                String result = reader.readLine();//读取服务器进行逻辑处理后页面显示的数据
                                Log.d("MainActivity","run: "+result);
                                if (result.equals("login successfully!")){
                                    Log.d("MainActivity","run2: "+result);
                                    Looper.prepare();
                                    Log.d("MainActivity","run3: "+result);
                                    Toast.makeText(MainActivity.this,"You logined successfully!",Toast.LENGTH_SHORT).show();
                                    Log.d("MainActivity","run4: "+result);
                                    Looper.loop();
                                }
                            }catch (MalformedURLException e){}
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }
}
