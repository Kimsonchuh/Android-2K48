package com.kimson.kame2048;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class OverDialogFragment extends DialogFragment {

	private Button okButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// 该语句很重要，只有设置该语句才能完全的自定义dialog的布局
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		View view = inflater.inflate(R.layout.overdialog, container);
		okButton=(Button) view.findViewById(R.id.okbutton);
		okButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				OverDialogFragment.this.dismiss();
			}
		});
		return view;
		
	}
	
}