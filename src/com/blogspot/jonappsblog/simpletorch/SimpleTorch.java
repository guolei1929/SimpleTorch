package com.blogspot.jonappsblog.simpletorch;


import com.blogspot.jonappsblog.simpletorch.R;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

public class SimpleTorch extends Activity {
	private Camera cam = Camera.open();					// used to open
	private Parameters params = cam.getParameters();	// the camera and flashlight
	private boolean turnon = turnOn();					// as quick as possible

	
	// creating main window:
	protected void onCreate(Bundle savedInstanceState) {
		boolean flash = params.getFlashMode().equals(Parameters.FLASH_MODE_TORCH);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_window);
		ToggleButton tb = (ToggleButton) findViewById(R.id.TBut);
		tb.setChecked(flash);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_window, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:{
			AlertDialog("Coming soon!", "Settings will be in a future version! ;)", "OK");
			return true;
		}
		case R.id.menu_about:{
			AlertDialog(getString(R.string.app_name), getString(R.string.aboutText), "OK");
		}
		default:return super.onOptionsItemSelected(item);
		}
	}
	
	
	// when the toggle button is tapped:
	public void onToggle(View view) {
		ToggleButton TB = (ToggleButton) findViewById(R.id.TBut);
		boolean on = TB.isChecked();
		if (on) {
			turnOn();
		} else {
			turnOff();
		}
	}

	
	// turns the flashlight on:
	private boolean turnOn() {
		cam.startPreview();
		params.setFlashMode(Parameters.FLASH_MODE_TORCH);
		cam.setParameters(params);
		return true;
	}

	
	// turns the flashlight off:
	private void turnOff() {
		params.setFlashMode(Parameters.FLASH_MODE_OFF);
		cam.setParameters(params);
	}
	
	private void AlertDialog(String title, String msg, String btn){
		SpannableString txt = new SpannableString(msg);
		Linkify.addLinks(txt, Linkify.WEB_URLS);
			final AlertDialog ad = new AlertDialog.Builder(this)
		    .setTitle(title)
		    .setMessage(txt)
		    .setIcon(R.drawable.ic_launcher)
		    .setPositiveButton(btn, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		        }
		     })
		     .create();
		     ad.show();
		     ((TextView)ad.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
	}
	
	
	// when the app is closed:
	protected void onDestroy() {
		turnOff();
		cam.stopPreview();
		cam.setPreviewCallback(null);
		cam.release();
		super.onDestroy();
	}

}