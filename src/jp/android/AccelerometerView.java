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
	private static final String[] PAINT_STRING = {"X：", "Y：", "Z："};
	private Bitmap bitmap;
	private static int x, y;
	private static String[] accelerometerString = new String[3];

	public AccelerometerView(Context context) {
		super(context);
		Log.d(TAG, "AccelerometerView");
		
		Resources resources = getResources();
		bitmap = BitmapFactory.decodeResource(resources, R.drawable.demo);
		x = 110;
		y = 160;
		for (int i = 0; i < accelerometerString.length; i++) {
			accelerometerString[i] = PAINT_STRING[i] + 0;
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
		canvas.drawBitmap(bitmap, x, y, null);
		// 加速度を表示
		Paint paint = new Paint();
		paint.setAntiAlias(true);		// アンチエイリアスを有効にする事で、フォントを綺麗に見せる
		paint.setColor(getResources().getColor(R.color.accelerometer));
		paint.setTextSize(FONT_SIZE);
		paint.setStyle(Style.FILL);
		for (int i = 0; i < accelerometerString.length; i++) {
			canvas.drawText(accelerometerString[i], 0, FONT_SIZE * (i + 1), paint);
		}
	}
	
	public void setAccelerometerValues(float[] values) {
		Log.d(TAG, "setAccelerometerValues");

		for (int i = 0; i < accelerometerString.length; i++) {
			accelerometerString[i] = PAINT_STRING[i] + values[i];
		}
	}
	
	public void setBitmapMove(float[] values) {
		Log.d(TAG, "setBitmapMove");

		x -= values[0];
		y += values[1];
	}
}
