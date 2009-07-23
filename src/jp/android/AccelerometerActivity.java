package jp.android;

import java.util.List;

import android.app.Activity;
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
public class AccelerometerActivity extends Activity implements SensorEventListener {
	private static final String TAG = "AccelermeterActivity";
	private SensorManager sensorManager;
	private AccelerometerView accelerometerView;

	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");
		sensorManager = (SensorManager)this.getSystemService(SENSOR_SERVICE);
		accelerometerView = new AccelerometerView(this);
		setContentView(accelerometerView);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume");

		// リスナの登録
		List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
		for (Sensor s : sensors) {
			sensorManager.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);
		}
		
	}

	public void onAccuracyChanged(Sensor arg0, int arg1) {
	}

	public void onSensorChanged(SensorEvent event) {
    	Log.v(TAG, "onSensor");
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
        	Log.v(TAG, "acceleometer");
        	accelerometerView.setAccelerometerValues(event.values);
        	accelerometerView.setBitmapMove(event.values);
        	accelerometerView.invalidate();		// 再度、onDraw を行う
        }
	}
}