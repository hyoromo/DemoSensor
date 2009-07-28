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
 * 傾きセンサーのデモ描画処理。
 * @author hyoromo
 *
 */
public class OrientationView extends View {
	private static final String TAG = "OrientationView";
	private static final int FONT_SIZE = 20;
	private static final String REAL_NAME = "実傾き";
	private static final String SET_NAME = "方角度";
	private static final String[] PAINT_STRING = {"X：", "Y：", "Z："};
	private Bitmap bitmap;
	private static float x;
	private static String[] orientationString = new String[3];

	public OrientationView(Context context) {
		super(context);
		Log.d(TAG, "OrientationView");
		Resources resources = getResources();
		bitmap = BitmapFactory.decodeResource(resources, R.drawable.compass);
		for (int i = 0; i < orientationString.length; i++) {
			orientationString[i] = PAINT_STRING[i] + 0;
		}
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		Log.d(TAG, "onDraw");
		// 背景を描画する
		Paint background = new Paint();
		background.setColor(getResources().getColor(R.color.background));
		canvas.drawRect(0, 0, getWidth(), getHeight(), background);
		// 加速度を表示
		Paint paint = new Paint();
		paint.setAntiAlias(true);		// アンチエイリアスを有効にする事で、フォントを綺麗に見せる
		paint.setColor(getResources().getColor(R.color.accelerometer));
		paint.setTextSize(FONT_SIZE);
		paint.setStyle(Style.FILL);
		canvas.drawText(REAL_NAME, 0, FONT_SIZE, paint);
		for (int i = 0; i < orientationString.length; i++) {
			canvas.drawText(orientationString[i], 0, FONT_SIZE * (i + 2), paint);
		}
		canvas.drawText(SET_NAME, 0, FONT_SIZE * 6, paint);
		String str = "" + x;
		canvas.drawText(str, 0, FONT_SIZE * 7, paint);
		
		// デモ画像を描画する
		int pointX = (this.getWidth() / 2) - (bitmap.getWidth() / 2);
		int pointY = (this.getHeight() / 2) - (bitmap.getHeight() / 2);
		canvas.rotate(x, pointX + bitmap.getWidth() * 0.5f, pointY + bitmap.getHeight() * 0.5f);
		canvas.drawBitmap(bitmap, pointX, pointY, null);
	}
	
	public void setAccelerometerValues(float[] values) {
		Log.d(TAG, "setAccelerometerValues");
		for (int i = 0; i < orientationString.length; i++) {
			orientationString[i] = PAINT_STRING[i] + values[i];
		}
	}
	
	public void setBitmapRotate(float[] values) {
		Log.d(TAG, "setBitmapMove");
		x = (filter(values[0]) - 360) * -1;
	}
	
	private int filter(float f) {
		return (int)f;
	}
}
