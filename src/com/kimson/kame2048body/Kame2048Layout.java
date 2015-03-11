package com.kimson.kame2048body;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kimson.kame2048.MainActivity;

public class Kame2048Layout extends RelativeLayout {

	// 游戏主题的列数
	private int mColumn = 4;

	// card的边距
	private int mMargin = 10;

	// card的内边距
	private int mPadding;

	// 装载卡片的数组
	public  Kame2048Card[] kame2048Cards;

	// 是否第一次测量界面
	private boolean once = true;
	
	private boolean isMerging=false;
	
	private static int childwidth;
	
	private List<Integer> emptyCardNums = new ArrayList<Integer>();
	
	private List<Integer> lineCardNums = new ArrayList<Integer>();
	
	private MainActivity mainActivity=MainActivity.getMainActivity();


	public Kame2048Layout(Context context) {
		this(context, null);
	}

	public Kame2048Layout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public Kame2048Layout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				mMargin, getResources().getDisplayMetrics());
		mPadding = min(getPaddingLeft(), getPaddingTop(), getPaddingRight(),
				getPaddingBottom());
		setOnTouchListener(ontouchlistener);
	}
	
	
	private OnTouchListener ontouchlistener = new OnTouchListener() {

		private float startX, startY, offsetX, offsetY;

		@Override
		public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX = event.getX();
					startY = event.getY();
					break;
				case MotionEvent.ACTION_UP:
					offsetX = event.getX() - startX;
					offsetY = event.getY() - startY;
					if (Math.abs(offsetX) > Math.abs(offsetY)) {
						if (offsetX < -5) {
							action(ACTION.LEFT);
						} else if (offsetX > 5) {
							action(ACTION.RIGHT);
						}
					} else {
						if (offsetY < -5) {
							action(ACTION.UP);
						} else if (offsetY > 5) {
							action(ACTION.DOWN);
						}
					}
					break;
				}
				return true;
		}
	};
	


	private enum ACTION
	{
		LEFT, RIGHT, UP, DOWN
	}


	private void action(ACTION action) {
		for (int i = 0; i < mColumn; i++) {
			lineCardNums.clear();
			for (int j = 0; j < mColumn; j++) {
				int index = getIndexByAction(action, i, j);
				lineCardNums.add(index);
			}
			//数字变更的实现算法
			for (int x = 0; x < mColumn; x++) {
				for (int y = x + 1; y < mColumn; y++) {
					if (kame2048Cards[lineCardNums.get(y)].getNumber() > 0) {
						if (kame2048Cards[lineCardNums.get(x)].getNumber() <= 0) {
							mainActivity.getCardAnimation().CardMoveAnimation(kame2048Cards[lineCardNums.get(y)], kame2048Cards[lineCardNums.get(x)], childwidth, mPadding, mMargin, mColumn);
							//kame2048Cards[lineCardNums.get(x)].setNumber(kame2048Cards[lineCardNums.get(y)].getNumber());
                            //kame2048Cards[lineCardNums.get(y)].setNumber(0);
							
							x--;
							isMerging=true;
						} else if (
							kame2048Cards[lineCardNums.get(y)].getNumber() == kame2048Cards[lineCardNums.get(x)].getNumber()) {
							//kame2048Cards[lineCardNums.get(x)].setNumber(kame2048Cards[lineCardNums.get(x)].getNumber() * 2);
                            //kame2048Cards[lineCardNums.get(y)].setNumber(0);
							mainActivity.getCardAnimation().CardMergeAnimation(kame2048Cards[lineCardNums.get(y)], kame2048Cards[lineCardNums.get(x)], childwidth, mPadding, mMargin, mColumn);
							mainActivity.showScore(kame2048Cards[lineCardNums.get(x)].getNumber());
							isMerging=true;
						}
						break;
					}
				}
			}
		}
		createNewCard();
	}
	


	private int getIndexByAction(ACTION action, int i, int j)
	{
		int index = -1;
		switch (action)
		{
		case LEFT:
			index = i * mColumn + j;
			break;
		case RIGHT:
			index = i * mColumn + mColumn - j - 1;
			break;
		case UP:
			index = i + j * mColumn;
			break;
		case DOWN:
			index = i + (mColumn - 1 - j) * mColumn;
			break;
		}
		return index;
	}
	


	private int min(int... params) {
		int min = params[0];
		for (int param : params) {
			if (min > param) {
				min = param;
			}
		}
		return min;
	}



	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int length = Math.min(getMeasuredHeight(), getMeasuredWidth());
		childwidth = (length - 2 * mPadding - (mColumn - 1) * mMargin)
				/ mColumn;

		if (once) {
			if (kame2048Cards == null) {
				kame2048Cards = new Kame2048Card[mColumn * mColumn];
			}
			for (int i = 0; i < kame2048Cards.length; i++) {
				Kame2048Card card = new Kame2048Card(getContext());
				kame2048Cards[i] = card;
				card.setId(i + 1);
				RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
						childwidth, childwidth);
				if ((i + 1) % mColumn != 0) {
					layoutParams.rightMargin = mMargin;
				}
				if (i % mColumn != 0) {
					// equals android:layout_RightOf"@+/id/kame2048Cards[i-1].getId()"
					layoutParams.addRule(RelativeLayout.RIGHT_OF,
							kame2048Cards[i - 1].getId());
				}
				if ((i + 1) > mColumn) {
					layoutParams.topMargin = mMargin;
					layoutParams.addRule(RelativeLayout.BELOW, kame2048Cards[i
							- mColumn].getId());
				}
				addView(card, layoutParams);
			}
			initCardCreate();
			
		}
		once = false;
		setMeasuredDimension(length, length);
	}
	
	
	@SuppressLint("DrawAllocation") @Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint paint=new Paint();
		paint.setColor(Color.parseColor("#ccc0b2"));
		int k=0,c=0,Left=0,Top=0;
		
		for (int i = 0; i < kame2048Cards.length; i++) {
			k=i%4;
			c=i/4;
			Left=(k+1)*mMargin+k*childwidth;
			Top=(c+1)*mMargin+c*childwidth;
			canvas.drawRect(Left, Top, childwidth+Left, childwidth+Top, paint);
		}
		
		
	}
	


	private boolean isFull() {
		for (int i = 0; i < kame2048Cards.length; i++) {
			if (kame2048Cards[i].getNumber() == 0) {
				return false;
			}
		}
		return true;
	}



	public boolean isGameOver() {
		// 检测是否所有位置都有数字
		if (!isFull()) {
			return false;
		}
		for (int i = 0; i < kame2048Cards.length - 1; i++) {
			if ((i + 1) % mColumn != 0 && kame2048Cards[i].getNumber() == kame2048Cards[i + 1].getNumber()) {
				return false;
			}
			if ((i < kame2048Cards.length - mColumn)&& kame2048Cards[i].getNumber() == kame2048Cards[i + mColumn].getNumber()) {
				return false;
			}
		}
		return true;
	}



	private void createNewCard() {
		if (isGameOver()) {
			mainActivity.UpdateBest();
			mainActivity.showOverDialogFragment();
		}
		if (!isFull()&&isMerging) {
			initCardCreate();
			isMerging=false;
		}
	}
	
	
	private void initCardCreate(){
		emptyCardNums.clear();
		for (int i = 0; i < kame2048Cards.length; i++) {
			if (kame2048Cards[i].getNumber() <= 0) {
				emptyCardNums.add(i);
			}
		}
		if (emptyCardNums.size() > 0) {
			Kame2048Card card=kame2048Cards[emptyCardNums.get((int) (Math.random() * emptyCardNums.size()))];
			int randomNum=Math.random() > 0.1 ? 2 : 4;
			card.setNumber(randomNum);
			mainActivity.getCardAnimation().CardScaleAnimation(card);
		}
	}
	
	public void Restart(){
		for (int i = 0; i < kame2048Cards.length; i++) {
			kame2048Cards[i].setNumber(0);
		}
		initCardCreate();
	}
	
	
}
