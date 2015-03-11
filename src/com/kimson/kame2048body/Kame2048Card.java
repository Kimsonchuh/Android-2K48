package com.kimson.kame2048body;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class Kame2048Card extends View {

	private int mNumber=0;
	private String mNumberVal;
	private Paint mPaint;
	private Rect mRect;
	private boolean isDisplay=true;

	public Kame2048Card(Context context) {
		this(context, null);
	}

	public Kame2048Card(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public Kame2048Card(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mPaint = new Paint();
	}
	
	

	public void setDisplay(boolean isDisplay) {
		this.isDisplay = isDisplay;
		invalidate();
	}

	public int getNumber() {
		return mNumber;
	}

	public void setNumber(int mNumber) {
		this.mNumber = mNumber;
		mNumberVal=mNumber+"";
		if(mNumber>1000){
			mPaint.setTextSize(60);
		}else if(mNumber>100){
			mPaint.setTextSize(70);
		}else {
			mPaint.setTextSize(80);
		}
		mRect=new Rect();
		mPaint.getTextBounds(mNumberVal, 0, mNumberVal.length(), mRect);
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		String mBgColor = "";
		switch (mNumber) {
		case 0:
			mBgColor = "#ccc0b2";
			break;
		case 2:
			mBgColor = "#eee4da";
			break;
		case 4:
			mBgColor = "#f0e0c9";
			break;
		case 8:
			mBgColor = "#f2b179";
			break;
		case 16:
			mBgColor = "#f69463";
			break;
		case 32:
			mBgColor = "#f57c5f";
			break;
		case 64:
			mBgColor = "#f85e3c";
			break;
		case 128:
			mBgColor = "#edce71";
			break;
		case 256:
			mBgColor = "#eed04a";
			break;
		case 512:
			mBgColor = "#f4ca33";
			break;
		case 1024:
			mBgColor = "#f5c51a";
			break;
		case 2048:
			mBgColor = "#ecc400";
			break;
		case 4096:
			mBgColor = "#ecc400";
			break;
		case 8192:
			mBgColor = "#ecc400";
			break;
		}
		if(isDisplay){
			mPaint.setColor(Color.parseColor(mBgColor));
			mPaint.setStyle(Style.FILL);
			canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
			if (mNumber != 0)
				drawText(canvas);
		}else {
			mBgColor = "#CCC0B3";
			mPaint.setColor(Color.parseColor(mBgColor));
			mPaint.setStyle(Style.FILL);
			canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
		}
		
	}

	private void drawText(Canvas canvas) {
		if(mNumber>4){
			mPaint.setColor(Color.parseColor("#ffffff"));
		}else {
			mPaint.setColor(Color.parseColor("#797069"));
		}
		float x = (getWidth() - mRect.width()) / 2;
		float y = getHeight() / 2 + mRect.height() / 2;
		canvas.drawText(mNumberVal, x, y, mPaint);
	}

}
