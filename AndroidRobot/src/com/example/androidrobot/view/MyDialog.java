package com.example.androidrobot.view;

import com.example.androidrobot.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

public class MyDialog extends Dialog{
	View v;

	public MyDialog(Context context, int theme,View v) {
	    super(context, theme);
	    this.v = v;
	}

	public MyDialog(Context context,View v) {
	    super(context);
	    this.v = v;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(v);
	}

}
