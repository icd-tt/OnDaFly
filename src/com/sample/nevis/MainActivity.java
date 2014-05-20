package com.sample.nevis;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.game_main);
		
//		setContentView(R.layout.activity_main);
//
//		
//		Button.OnClickListener l = new Button.OnClickListener(){
//
//			@Override
//			public void onClick(View arg0) {
//				
//				EditText et = (EditText)findViewById(R.id.editText1);		
//				if(null == et.getText()){
//					return;
//				}
//				
//				if("".equals(et.getText().toString().trim())){
//					return;
//				}
//				
//				String detail = et.getText().toString().trim();				
//				new CreateTask().execute(new String[]{detail});				
//			}	
//		};
//		
//		
//		Button btn  = (Button)findViewById(R.id.button1);
//		btn.setOnClickListener(l);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
