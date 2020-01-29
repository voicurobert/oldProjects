package com.example.controlyourcar;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Toast;

public class ButoaneActivity extends Activity{
	private MyBluetooth b = null;
	private Button buttonForward, buttonBackward, buttonLeft, buttonRight;
	private int leftMotor = 0;
	private int rightMotor = 0;
	private String macAddress;
	private int buttonLeftMotorPWM;
	private int buttonRightMotorPWM;
	private String leftCommand;
	private String rightCommand;
	private boolean bluetoothIsConnected;
	
	private final MyHandler myHandler = new MyHandler(this);
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_butoane);
		
		macAddress = (String) getResources().getText(R.string.defaultMacAddress);
		buttonLeftMotorPWM = Integer.parseInt((String) getResources().getText(R.string.defaultButtonLeftMotorPWM));
		buttonRightMotorPWM = Integer.parseInt((String) getResources().getText(R.string.defaultButtonRightMotorPWM));
		leftCommand = (String) getResources().getText(R.string.defaultLeftCommand);
		rightCommand = (String) getResources().getText(R.string.defaultRightCommand);
		
		
		b = new MyBluetooth(this, myHandler);
		b.checkBluetoothState();
		
		buttonBackward = (Button) findViewById(R.id.button_Backward);
		buttonForward = (Button) findViewById(R.id.button_Accelerometer);
		buttonLeft = (Button) findViewById(R.id.button_Butoane);
		buttonRight = (Button) findViewById(R.id.button_Right);
		
		//eveniment pentru miscarea in fata
		buttonForward.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_MOVE){
					leftMotor = buttonLeftMotorPWM;
					rightMotor = buttonRightMotorPWM;
					if(bluetoothIsConnected){
						b.sendData(String.valueOf(leftCommand + leftMotor + "\r" + rightCommand + rightMotor + "\r"));
						
					}
				}else if(event.getAction() == MotionEvent.ACTION_UP){
					leftMotor = 0;
					rightMotor = 0;
					if(bluetoothIsConnected){
						b.sendData(String.valueOf(leftCommand + leftMotor + "\r" + rightCommand + rightMotor + "\r"));
					}
				}
				return false;
			}
		});
		
		//eveniment pentru miscarea la stanga
		buttonLeft.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_MOVE){
					leftMotor = -buttonLeftMotorPWM;
					rightMotor = buttonRightMotorPWM;
					
					if(bluetoothIsConnected){
						b.sendData(String.valueOf(leftCommand + leftMotor + "\r" + rightCommand + rightMotor + "\r"));
					}
				}else if( event.getAction() == MotionEvent.ACTION_UP){
					leftMotor = 0;
					rightMotor = 0;
					if(bluetoothIsConnected){
						b.sendData(String.valueOf(leftCommand + leftMotor + "\r" + rightCommand + rightMotor + "\r"));
					}
				}
				
				return false;
			}
		});
		
		//eveniment pentru miscarea la dreapta
		buttonRight.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_MOVE){
					leftMotor = buttonLeftMotorPWM;
					rightMotor = -buttonRightMotorPWM;
					
					if(bluetoothIsConnected){
						b.sendData(String.valueOf(leftCommand + leftMotor + "\r" + rightCommand + rightMotor + "\r"));
					}
				}else if( event.getAction() == MotionEvent.ACTION_UP){
					leftMotor = 0;
					rightMotor = 0;
					if(bluetoothIsConnected){
						b.sendData(String.valueOf(leftCommand + leftMotor + "\r" + rightCommand + rightMotor + "\r"));
					}
				}
				
				return false;
			}
		});
		
		
		buttonBackward.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_MOVE){
					leftMotor = - buttonLeftMotorPWM;
					rightMotor = - buttonRightMotorPWM;
					
					if(bluetoothIsConnected){
						b.sendData(String.valueOf(leftCommand + leftMotor + "\r" + rightCommand + rightMotor + "\r"));
					}
				}else if( event.getAction() == MotionEvent.ACTION_UP){
					leftMotor = 0;
					rightMotor = 0;
					if(bluetoothIsConnected){
						b.sendData(String.valueOf(leftCommand + leftMotor + "\r" + rightCommand + rightMotor + "\r"));
					}
				}
				
				return false;
			}
		});
		
		myHandler.postDelayed(myRunnable, 600000);
	}
	
	
	private static class MyHandler extends Handler{
		private final WeakReference<ButoaneActivity> myActivity;
		
		public MyHandler(ButoaneActivity act){
			myActivity = new WeakReference<ButoaneActivity>(act);
		}
		
		public void handleMessage(Message msg) {
			ButoaneActivity activity = myActivity.get();
			if(activity != null){
				switch(msg.what){
				
				case MyBluetooth.BLUETOOTH_UNAVAILABLE:
					Toast.makeText(activity.getBaseContext(),"Bluetooth-ul nu este disponibil" , Toast.LENGTH_SHORT).show();
					activity.finish();
					break;
					
				case MyBluetooth.BLUETOOTH_INCORRECT_ADDRESS:
					Toast.makeText(activity.getBaseContext(),"Adresa MAC nu este corecta" , Toast.LENGTH_SHORT).show();
					break;
					
				case MyBluetooth.BLUETOOTH_REQUEST_ENABLED:
					BluetoothAdapter.getDefaultAdapter();
					Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
					activity.startActivityForResult(enableBluetoothIntent, 1);
					break;
					
				case MyBluetooth.BLUETOOTH_SOCKET_FAILED:
					Toast.makeText(activity.getBaseContext(),"Conexiunea socket-ului nu s-a realizat" , Toast.LENGTH_SHORT).show();
					activity.finish();
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
		bluetoothIsConnected = b.connectBluetooth(macAddress, false);
	}
	
	protected void onPause(){
		super.onPause();
		b.bluetoothOnPause();
	}
	
	

}
