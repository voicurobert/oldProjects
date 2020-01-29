package com.example.controlyourcar;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends Activity {
	private MyBluetooth b = null;
	private Button buttonRead, buttonWrite;
	private static CheckBox checkBox_ConnectionLost;
	private static EditText editText_ConnectionLost;
	private static String succes;
	private static String errGetData;
	private String macAddress;
	private static StringBuilder sb = new StringBuilder();
	
	private final MyHandler myHandler = new MyHandler(this);
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		macAddress = (String) getResources().getText(R.string.defaultMacAddress);
	//	buttonRead = (Button) findViewById(R.id.button_ReadData);
	//	buttonWrite = (Button) findViewById(R.id.button_WriteData);
		checkBox_ConnectionLost = (CheckBox) findViewById(R.id.checkBox_ConnLost);
		editText_ConnectionLost = (EditText) findViewById(R.id.editText1);
		succes = (String) getResources().getText(R.string.succes);
		errGetData = (String) getResources().getText(R.string.errGetData);
		
		
		b = new MyBluetooth(this, myHandler);
		b.checkBluetoothState();
		
		checkBox_ConnectionLost.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					editText_ConnectionLost.setEnabled(true);
				}else if(!isChecked){
					editText_ConnectionLost.setEnabled(false);
				}
				
			}
		});
		/*
		buttonRead.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				b.sendData(String.valueOf("Fr\t"));
				
			}
		});
		
		buttonWrite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				float number = 0;
				String stringToSend = "Fw";
				
				if(checkBox_ConnectionLost.isChecked()){
					stringToSend += "1";
				}else{
					stringToSend += "0";
				}
				
				try{
					number = Float.parseFloat(editText_ConnectionLost.getText().toString());
					
				}catch(NumberFormatException e){
					String errGetData = getString(R.string.errGetData);
					Toast.makeText(getBaseContext(), errGetData, Toast.LENGTH_SHORT).show();
				}
				
				if(number > 0 && number < 100){
					DecimalFormat myDF = new DecimalFormat("00.0");
					String o = myDF.format(number);
					stringToSend += String.valueOf(o.charAt(0) + String.valueOf(o.charAt(1)) + String.valueOf(o.charAt(3)));
					stringToSend += "\t";
					Log.d(MyBluetooth.TAG, "Trimitere:" + stringToSend);
					b.sendData(stringToSend);
				}else{
					String errRange = getString(R.string.err_Range);
					Toast.makeText(getBaseContext(), errRange, Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		*/
		myHandler.postDelayed(myRunnable, 600000);
	}
		
		
		private  class MyHandler extends Handler{
			private final WeakReference<SettingsActivity> myActivity;
			
			public MyHandler(SettingsActivity act){
				myActivity = new WeakReference<SettingsActivity>(act);
			}
			
			public void handleMessage(Message msg){
				SettingsActivity activity = myActivity.get();
				if(activity != null){
					switch(msg.what){
					
					case MyBluetooth.BLUETOOTH_UNAVAILABLE:
						Log.d(MyBluetooth.TAG, "Bluetooth-ul nu este disponibil!");
						Toast.makeText(activity.getBaseContext(),"Bluetooth-ul nu este disponibil" , Toast.LENGTH_SHORT).show();
						activity.finish();
						break;
						
					case MyBluetooth.BLUETOOTH_INCORRECT_ADDRESS:
						Log.d(MyBluetooth.TAG, "Adresa MAC nu este corecta");
						Toast.makeText(activity.getBaseContext(),"Adresa MAC nu este corecta" , Toast.LENGTH_SHORT).show();
						break;
						
					case MyBluetooth.BLUETOOTH_REQUEST_ENABLED:
						Log.d(MyBluetooth.TAG, "Cerere de activare bluetooth");
						BluetoothAdapter.getDefaultAdapter();
						Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
						activity.startActivityForResult(enableBluetoothIntent, 1);
						break;
						
					case MyBluetooth.BLUETOOTH_SOCKET_FAILED:
						Toast.makeText(activity.getBaseContext(),"Conexiunea socket-ului nu s-a realizat" , Toast.LENGTH_SHORT).show();
						activity.finish();
						break;
						
					case MyBluetooth.BLUETOOTH_RECEIVED_MESSAGE:
						byte[] readBuffer = (byte[]) msg.obj;
						String sIn = new String(readBuffer,0,msg.arg1);
						sb.append(sIn);
						
						int forwardData = sb.indexOf("forwardData: ");
						int forwardWriteOK = sb.indexOf("forwardWriteOK");
						int endOf = sb.indexOf("\r\t");
						
						if(forwardData >= 0 && endOf > 0 && endOf > forwardData){
							String sbPrint = sb.substring("ForwardData:".length(),endOf);
							if(sbPrint.substring(0,1).equals("1")){
								checkBox_ConnectionLost.setChecked(true);
							}else{
								checkBox_ConnectionLost.setChecked(false);
							}
							
							float editData = Float.parseFloat(sbPrint.substring(1,4))/10;
							editText_ConnectionLost.setText(String.valueOf(editData));
							sb.delete(0, sb.length());
						}else if(forwardWriteOK >= 0 && endOf > 0 && endOf > forwardWriteOK){
							Toast.makeText(activity.getBaseContext(), succes, Toast.LENGTH_SHORT).show();
							sb.delete(0, sb.length());
						}else if(endOf > 0){
							Toast.makeText(activity.getBaseContext(), errGetData, Toast.LENGTH_SHORT).show();
							sb.delete(0, sb.length());
						}
						break;
					}
				}
				
			}
			
		}
		
		private final static Runnable myRunnable = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}
		}; 
		
	
		
		protected void onResume(){
			super.onResume();
			if(checkBox_ConnectionLost.isChecked()){
				editText_ConnectionLost.setEnabled(true);
			}else{
				editText_ConnectionLost.setEnabled(false);
			}
			b.connectBluetooth(macAddress, true);
		}
		
		
		protected void onPause() {
	    	super.onPause();
	    	b.bluetoothOnPause();
	    }

}
