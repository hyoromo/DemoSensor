package jp.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;

/**
 * センサーのデモアプリなため、ソース内に解説を書いております。
 * @author hyoromo
 *
 */
public class DemoSensor extends Activity implements OnClickListener {	
	private static final String TAG = "DemoSensor";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate");
        setContentView(R.layout.main);
        
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        // 全てのボタンにクリックリスナーを設定
        // 加速度デモへのボタン
        View accelerometerButton = findViewById(R.id.accelerometer_button_id);
        accelerometerButton.setOnClickListener(this);
        /*
        // 角速度デモへのボタン（HT03-A だと動作しません）
        View gyroscopeButton = findViewById(R.id.gyroscope_button_id);
        gyroscopeButton.setOnClickListener(this);
        */
        // 輝度デモへのボタン
        View lightButton = findViewById(R.id.light_button_id);
        lightButton.setOnClickListener(this);
        // 磁界デモへのボタン
        View magneticButton = findViewById(R.id.magnetic_button_id);
        magneticButton.setOnClickListener(this);
        // 傾きデモへのボタン
        View orientationButton = findViewById(R.id.orientation_button_id);
        orientationButton.setOnClickListener(this);
        // 圧力デモへのボタン
        View proximityBotton = findViewById(R.id.proximity_button_id);
        proximityBotton.setOnClickListener(this);
        // 温度デモへのボタン
        View temperatureBotton = findViewById(R.id.temperature_button_id);
        temperatureBotton.setOnClickListener(this);
    }

	public void onClick(View v) {
        Log.v(TAG, "onClick");
		switch (v.getId()) {
		case R.id.accelerometer_button_id:
			Intent accelerometerIntent = new Intent(DemoSensor.this, SensorActivity.class);
			accelerometerIntent.putExtra("MODE",R.integer.accelerometer_mode);
			startActivity(accelerometerIntent);
			break;
		/*
		case R.id.gyroscope_button_id:
			Intent gyroscopeIntent = new Intent(DemoSensor.this, SensorActivity.class);
			gyroscopeIntent.putExtra("MODE",R.integer.gyroscope_mode);
			startActivity(gyroscopeIntent);
			break;
		*/
		case R.id.orientation_button_id:
			Intent orientationIntent = new Intent(DemoSensor.this, SensorActivity.class);
			orientationIntent.putExtra("MODE",R.integer.orientation_mode);
			startActivity(orientationIntent);
			break;			
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		Log.v(TAG, "onPause");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
        Log.v(TAG, "onDestroy");
	}
}