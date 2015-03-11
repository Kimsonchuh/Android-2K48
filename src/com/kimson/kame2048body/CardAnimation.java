package com.kimson.kame2048body;

import com.kimson.kame2048.MainActivity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

public class CardAnimation extends FrameLayout {

	
	public CardAnimation(Context context) {
		super(context);
	}

	public CardAnimation(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CardAnimation(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
	}

	public void initParams(Kame2048Card card,int id,int width,int Padding,int Margin,int Column){
		LayoutParams params=new LayoutParams(width, width);
		if(id%Column!=0){
			params.leftMargin=Padding+(id%Column-1)*(Margin+width);
			params.topMargin=Padding+(id/Column)*(Margin+width);
		}else {
			params.leftMargin=Padding+(Column-1)*(Margin+width);
			params.topMargin=Padding+(id/Column-1)*(Margin+width);
		}
		card.setLayoutParams(params);
	}
	
	
	
	/**
	 * 添加卡片时的放大动画
	 */
	public void CardScaleAnimation(Kame2048Card from){
		ScaleAnimation sa = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		sa.setDuration(250);
		sa.setStartOffset(180);
		from.startAnimation(sa);
	}

	
	/**
	 * 移动并交换数字卡片
	 * @param from
	 * @param to
	 * @param width
	 * @param Padding
	 * @param Margin
	 * @param Column
	 */
	
	public void CardMoveAnimation(final Kame2048Card from,final Kame2048Card to,int width,int Padding,int Margin,int Column){
		
		final Kame2048Card fromCover=new Kame2048Card(getContext());
		initParams(fromCover, from.getId(), width, Padding, Margin, Column);
		fromCover.setNumber(from.getNumber());
		from.setDisplay(false);
		to.setDisplay(false);
		from.setNumber(to.getNumber());
		to.setNumber(fromCover.getNumber());
		addView(fromCover);
		TranslateAnimation translateAnimation=new TranslateAnimation(0, (to.getLeft()-from.getLeft()-1), 0,(to.getTop()-from.getTop()));
		translateAnimation.setDuration(160);
		translateAnimation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {}
			
			@Override
			public void onAnimationRepeat(Animation animation) {}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				from.setDisplay(true);
				to.setDisplay(true);
				removeView(fromCover);
			}
		});
		fromCover.setAnimation(translateAnimation);
	}
	
	/**
	 * 合并动画
	 * @param from
	 * @param to
	 * @param width
	 * @param Padding
	 * @param Margin
	 * @param Column
	 */
	public void CardMergeAnimation(final Kame2048Card from,final Kame2048Card to,int width,int Padding,int Margin,int Column){
		
		final Kame2048Card fromCover=new Kame2048Card(getContext());
		initParams(fromCover, from.getId(), width, Padding, Margin, Column);
		fromCover.setNumber(from.getNumber());
		from.setNumber(0);
		addView(fromCover);
		TranslateAnimation translateAnimation=new TranslateAnimation(0, (to.getLeft()-from.getLeft()-1), 0,(to.getTop()-from.getTop()));
		translateAnimation.setDuration(150);
		translateAnimation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {}
			
			@Override
			public void onAnimationRepeat(Animation animation) {}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				ScaleAnimation scale = new ScaleAnimation(1, 1.05f, 1, 1.05f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
				scale.setDuration(50);
				scale.setAnimationListener(new AnimationListener() {
					
					@Override
					public void onAnimationStart(Animation animation) {
						
					}
					
					@Override
					public void onAnimationRepeat(Animation animation) {
						
					}
					
					@Override
					public void onAnimationEnd(Animation animation) {
					}
				});
				
				fromCover.setVisibility(View.INVISIBLE);
				to.setNumber(fromCover.getNumber()*2);
				to.setAnimation(scale);
				to.startAnimation(scale);
			}
		});
		fromCover.setAnimation(translateAnimation);
	}
	
	
	
}
