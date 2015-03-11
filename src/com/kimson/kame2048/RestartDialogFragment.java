package com.kimson.kame2048;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

public class RestartDialogFragment extends DialogFragment {

	private Button YesButton,noButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// 该语句很重要，只有设置该语句才能完全的自定义dialog的布局
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		View view = inflater.inflate(R.layout.dialogfragment, container);
		YesButton=(Button) view.findViewById(R.id.ybutton);
		noButton=(Button) view.findViewById(R.id.nbutton);
		YesButton.setOnClickListener(listener);
		noButton.setOnClickListener(listener);
		return view;
		
	}
	
	private OnClickListener listener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.nbutton:
				RestartDialogFragment.this.dismiss();
				break;

			case R.id.ybutton:
				MainActivity.getMainActivity().RestartGame();
				RestartDialogFragment.this.dismiss();
				break;
			}
		}
	};
}