package jp.android;

import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

/**
 * 加速度センサーのデモアプリ。
 * @author hyoromo
 *
 */
public class SensorActivity extends Activity implements SensorEventListener {
	private static final String TAG = "SensorActivity";
	private static final String ACCELEROMETER_PREFS_NAME = "AccelerometerData";
	private SensorManager sensorManager;
	private AccelerometerView accelerometerView;
	private OrientationView orientationView;
	private int sensorMode;
	private boolean registerdeSensor;

	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG, "onCreate");
		registerdeSensor = false;
		sensorManager = (SensorManager)this.getSystemService(SENSOR_SERVICE);		

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			sensorMode = extras.getInt("MODE");
		} else {
			// エラーハンドリング
		}
		
		switch (sensorMode) {
		case R.integer.accelerometer_mode:
			accelerometerView = new AccelerometerView(this);
			setContentView(accelerometerView);
	        SharedPreferences prefs = getSharedPreferences(ACCELEROMETER_PREFS_NAME, 0);
	        accelerometerView.setAccelerometerX(prefs.getFloat("accelerometer_x", -100.0f));
	        accelerometerView.setAccelerometerY(prefs.getFloat("accelerometer_y", -100.0f));
			break;
		case R.integer.orientation_mode:
			orientationView = new OrientationView(this);
			setContentView(orientationView);
			break;
		default:
			// エラーハンドリング
			break;
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.v(TAG, "onResume");
		// リスナの登録
		List<Sensor> sensors = null;
		switch (sensorMode) {
		case R.integer.accelerometer_mode:
			sensors = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
			break;
		/*
		case R.integer.gyroscope_mode:
			// HT03-A だと取得できないようです。
			sensors = sensorManager.getSensorList(Sensor.TYPE_GYROSCOPE);
			break;
		*/
		case R.integer.orientation_mode:
			sensors = sensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
			break;
		default:
			// エラーハンドリング
			break;
		}
		for (Sensor s : sensors) {
			registerdeSensor = sensorManager.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);
		}
	}
	
    @Override
    protected void onPause() {
    	super.onPause();
    	Log.v(TAG, "onPause");
    	if (registerdeSensor) {
    		switch (sensorMode) {
    		case R.integer.accelerometer_mode:
	            SharedPreferences settings = getSharedPreferences(ACCELEROMETER_PREFS_NAME, 0);
	            SharedPreferences.Editor editor = settings.edit();
	            editor.putFloat("accelerometer_x", accelerometerView.getAccelerometerX());
	            editor.putFloat("accelerometer_y", accelerometerView.getAccelerometerY());
	            editor.commit();
    		case R.integer.orientation_mode:
    			break;
    		default:
    			// エラーハンドリング
    			break;
    		}
			sensorManager.unregisterListener(this);
    		registerdeSensor = false;
    	}
    }
    
    @Override
    protected void onStop() {
    	super.onPause();
    	Log.v(TAG, "onStop");    	
    }
    
    @Override
    protected void onDestroy() {
    	super.onPause();
    	Log.v(TAG, "onDestroy");
    	// 加速度のデータをクリア
        SharedPreferences settings = getSharedPreferences(ACCELEROMETER_PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();
    }

	public void onAccuracyChanged(Sensor arg0, int arg1) {
	}

	public void onSensorChanged(SensorEvent event) {
    	Log.v(TAG, "onSensor");
    	switch (event.sensor.getType()) {
    	case Sensor.TYPE_ACCELEROMETER:
	        	Log.v(TAG, "acceleometer");
	        	accelerometerView.setAccelerometerValues(event.values);
	        	accelerometerView.setBitmapMove(event.values);
	        	accelerometerView.invalidate();		// 再度、onDraw を行う
	        	break;
	    /*
    	case Sensor.TYPE_GYROSCOPE:
        	Log.v(TAG, "gyroscope");
    		break;
    	*/
    	case Sensor.TYPE_ORIENTATION:
    		Log.v(TAG, "orientation");
    		orientationView.setAccelerometerValues(event.values);
        	orientationView.setBitmapRotate(event.values);
    		orientationView.invalidate();
    		break;
    	}
	}
}