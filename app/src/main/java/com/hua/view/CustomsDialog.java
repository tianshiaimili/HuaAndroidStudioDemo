package com.hua.view;

import com.example.annotationdemo.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

public class CustomsDialog extends Dialog{

	
	
	public CustomsDialog(Context context) {
		super(context);
		
	}

	public CustomsDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public CustomsDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_position);
	}
	
}
