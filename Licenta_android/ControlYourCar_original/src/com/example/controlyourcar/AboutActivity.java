package com.example.controlyourcar;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class AboutActivity extends Activity {
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		TextView textView = (TextView) findViewById(R.id.textView_Info);
		textView.setText(Html.fromHtml(getString(R.string.textview_about)));
		textView.setMovementMethod(LinkMovementMethod.getInstance());
		
	}
	
	
	protected void onResume(){
		super.onResume();
	}
	
	protected void onPause(){
		super.onPause();
	}
}
