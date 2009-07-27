package jp.android;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.View;

/**
 * 加速度センサーのデモ描画処理。
 * @author hyoromo
 *
 */
public class AccelerometerView extends View {
	private static final String TAG = "AccelerometerView";
	private static final int FONT_SIZE = 20;
	private static final String REAL_NAME = "実加速度";
	private static final String SET_NAME = "設定加速度";
	private static final String[] PAINT_STRING = {"X：", "Y：", "Z："};
	private Bitmap bitmap;
	private static float x, y;
	private static String[] realAccelerometerString = new String[3];
	private static String[] setAccelerometerString = new String[3];

	public AccelerometerView(Context context) {
		super(context);
		Log.d(TAG, "AccelerometerView");	
		Resources resources = getResources();
		bitmap = BitmapFactory.decodeResource(resources, R.drawable.demo);
		for (int i = 0; i < realAccelerometerString.length; i++) {
			realAccelerometerString[i] = PAINT_STRING[i] + 0;
		}
		for (int i = 0; i < setAccelerometerString.length; i++) {
			setAccelerometerString[i] = PAINT_STRING[i] + 0;
		}
	}

	@Override
	public void onDraw(Canvas canvas) {
		Log.d(TAG, "onDraw");		
		// 背景を描画する
		Paint background = new Paint();
		background.setColor(getResources().getColor(R.color.background));
		canvas.drawRect(0, 0, getWidth(), getHeight(), background);
		// デモ画像を描画する
		if (x <= -1.0f) x = (this.getWidth() / 2) - (bitmap.getWidth() / 2);
		if (y <= -1.0f) y = (this.getHeight() / 2) - (bitmap.getHeight() / 2);
		// デモ画像を描画する
		canvas.drawBitmap(bitmap, x, y, null);
		// 加速度を表示
		Paint paint = new Paint();
		paint.setAntiAlias(true);		// アンチエイリアスを有効にする事で、フォントを綺麗に見せる
		paint.setColor(getResources().getColor(R.color.accelerometer));
		paint.setTextSize(FONT_SIZE);
		paint.setStyle(Style.FILL);
		canvas.drawText(REAL_NAME, 0, FONT_SIZE, paint);
		for (int i = 0; i < realAccelerometerString.length; i++) {
			canvas.drawText(realAccelerometerString[i], 0, FONT_SIZE * (i + 2), paint);
		}
		canvas.drawText(SET_NAME, 0, FONT_SIZE * 6, paint);
		for (int i = 0; i < setAccelerometerString.length; i++) {
			canvas.drawText(setAccelerometerString[i], 0, FONT_SIZE * (i + 7), paint);
		}
	}
	
	public void setAccelerometerValues(float[] values) {
		Log.d(TAG, "setAccelerometerValues");
		for (int i = 0; i < realAccelerometerString.length; i++) {
			realAccelerometerString[i] = PAINT_STRING[i] + values[i];
		}
		for (int i = 0; i < setAccelerometerString.length; i++) {
			setAccelerometerString[i] = PAINT_STRING[i] + filter(values[i]);
		}
	}
	
	public void setBitmapMove(float[] values) {
		Log.d(TAG, "setBitmapMove");
		x -= filter(values[0]);
		y += filter(values[1]);
	}
	
	private int filter(float f) {
		return (int)f;
	}
	
	public float getAccelerometerX() {
		return x;
	}

	public float getAccelerometerY() {
		return y;
	}
	
	public void setAccelerometerX(float tX) {
		x = filter(tX);
	}

	public void setAccelerometerY(float tY) {
		y = filter(tY);
	}
}
