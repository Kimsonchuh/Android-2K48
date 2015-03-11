package com.kimson.SplashStart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.kimson.kame2048.MainActivity;
import com.kimson.kame2048.R;

public class SplashActivity extends Activity {
	
    private final int SPLASH_DISPLAY_LENGHT = 1800; // 延迟六秒  
    
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.splash);  
  
        new Handler().postDelayed(new Runnable() {  
            public void run() {  
                Intent mainIntent = new Intent(SplashActivity.this,  
                        MainActivity.class);  
                SplashActivity.this.startActivity(mainIntent);  
                SplashActivity.this.finish();  
            }  
  
        }, SPLASH_DISPLAY_LENGHT);  
  
    } 

}
