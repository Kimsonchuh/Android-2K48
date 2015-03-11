package com.kimson.kame2048;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.kimson.SplashStart.SplashActivity;
import com.kimson.kame2048body.CardAnimation;
import com.kimson.kame2048body.Kame2048Layout;

public class MainActivity extends Activity {

	private CardAnimation cardAnimation;
	private Kame2048Layout kame2048Layout;
	private TextView scroeVeiw, bestView;
	private Button restartButton;
	public int Sum = 0, bestValue = 0;// 需要显示最高分的值
	private static MainActivity mainActivity;
	private SharedPreferences preferences;

	public MainActivity() {
		mainActivity = this;
	}

	public static MainActivity getMainActivity() {
		return mainActivity;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		preferences = getSharedPreferences("bestCode", MODE_PRIVATE);

		cardAnimation = (CardAnimation) findViewById(R.id.kamecardanimation);
		scroeVeiw = (TextView) findViewById(R.id.score);
		bestView = (TextView) findViewById(R.id.best);
		bestValue = preferences.getInt("bestValue", 0);
		bestView.setText("" + bestValue);
		restartButton = (Button) findViewById(R.id.restart_button);
		restartButton.setOnClickListener(listener);
		kame2048Layout = (Kame2048Layout) findViewById(R.id.kamelayout);

	}

	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (kame2048Layout.isGameOver()) {
				MainActivity.this.RestartGame();
			} else {
				showDialogFragment();
			}
		}
	};

	public CardAnimation getCardAnimation() {
		return cardAnimation;
	}

	public void showScore(int score) {
		Sum += score;
		scroeVeiw.setText("" + Sum);
	}

	/**
	 * 比较当前得分与最高分，如果当前得分大于最高分则把当前得分保存为最高分 (方法最后也可以弹出祝贺对话框)
	 * 
	 * @param score
	 * @param best
	 */
	public void UpdateBest() {
		if (Sum > bestValue) {
			bestValue = Sum;
			bestView.setText(String.valueOf(bestValue));
			SharedPreferences.Editor editor = preferences.edit();
			editor.putInt("bestValue", bestValue);
			editor.commit();
		}

	}

	private void showDialogFragment() {
		RestartDialogFragment dialogFragment = new RestartDialogFragment();
		dialogFragment.show(getFragmentManager(), "RestartDialog");
	}

	public void showOverDialogFragment() {
		OverDialogFragment overDialogFragment = new OverDialogFragment();
		overDialogFragment.show(getFragmentManager(), "OverDialog");
	}

	public void RestartGame() {
		kame2048Layout.Restart();
		scroeVeiw.setText(0 + "");
	}

}
