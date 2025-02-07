package com.example.controlyourcar;

import java.lang.ref.WeakReference;
import java.util.Locale;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


public class AccelActivity extends Activity implements SensorEventListener {
	private SensorManager mySensorManger;
	private Sensor mySensor;
	private MyBluetooth b = null;
	
	private final MyHandler myHandler = new MyHandler(this);
	
	private int axaX = 0;
	private int axaY = 0;
	private int leftMotor = 0;
	private int rightMotor = 0;
	private String macAddress;
	private boolean showDebug;
	private boolean bConnect;
	private int maxX;
	private int maxY;
	private int thresholdY;
	private int maxPWM;
	private int xR;
	private String leftCommand;
	private String rightCommand;
	


	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accelerometru);
		
		macAddress = (String) getResources().getText(R.string.defaultMacAddress);
		maxX = Integer.parseInt((String)getResources().getText(R.string.defaultMaxX));
		maxY = Integer.parseInt((String)getResources().getText(R.string.defaultMaxY));
		xR = Integer.parseInt((String)getResources().getText(R.string.defaultXR));
		thresholdY = Integer.parseInt((String)getResources().getText(R.string.defaultThresholdY));
		maxPWM = Integer.parseInt((String)getResources().getText(R.string.defaultMaxPWM));
		leftCommand = (String) getResources().getText(R.string.defaultLeftCommand);
		rightCommand = (String) getResources().getText(R.string.defaultRightCommand);
		
		
		mySensorManger = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		mySensor = mySensorManger.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
		
		b = new MyBluetooth(this, myHandler);
		b.checkBluetoothState();
		
		myHandler.postDelayed(r, 600000);
		
	}
	
	
	//crearea clasei MyHandler
	private static class MyHandler extends Handler{
		private final WeakReference<AccelActivity>  myActivity;
		
		
		//constructorul clasei MyHandler
		public MyHandler(AccelActivity act){
			myActivity = new WeakReference<AccelActivity>(act);
		}
		
		@Override
		public void handleMessage(Message msg) {
			AccelActivity activity = myActivity.get();
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
	
	private final static Runnable r = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
	};
	

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		String leftDirection = " ";
		String rightDirection = " ";
		String commandSendLeft, commandSendRight;
		
		axaX = Math.round(event.values[0]*maxPWM/xR);
		axaY = Math.round(event.values[1]*maxPWM/maxY);
		
		if(axaX > maxPWM){
			axaX = maxPWM;
		}else if(axaX < -maxPWM){
			axaX = -maxPWM;
		}
		
		if(axaY > maxPWM){
			axaY = maxPWM;
		}else if(axaY < - maxPWM){
			axaY = - maxPWM;
		}else if(axaY >= 0 && axaY < thresholdY){
			axaY = 0;
		}else if(axaY < 0 && axaY > -thresholdY){
			axaY = 0;
		}
		
		if(axaX > 0){
			rightMotor = axaX;
			if(Math.abs(Math.round(event.values[0])) > xR){
				leftMotor = Math.round(event.values[0] - xR) * maxPWM/(maxX - xR);
				leftMotor = Math.round(-leftMotor * axaY/maxPWM);
			}else{
				leftMotor = axaY - axaY*axaX/maxPWM;
			}
		}else if(axaX < 0){
			leftMotor = axaY;
			if(Math.round(event.values[0]) > xR){
				rightMotor = Math.round(Math.abs(event.values[0]) - xR) * maxPWM/(maxX - xR);
				rightMotor = Math.round(-rightMotor * axaY/maxPWM);
			}else{
				rightMotor = axaY - axaY*Math.abs(axaX)/maxPWM;
			}
		}else if(axaX == 0){
			leftMotor = axaY;
			rightMotor = axaX;
		}
		
		if(leftMotor > 0){
			leftDirection = "-";
			
		}
		if(rightMotor > 0){
			rightDirection = "-";
		}
		
		leftMotor = Math.abs(leftMotor);
		rightMotor = Math.abs(rightMotor);
		
		if(leftMotor > maxPWM){
			leftMotor = maxPWM;
		}
		
		if(rightMotor > maxPWM){
			rightMotor = maxPWM;
		}
		
		commandSendLeft = String.valueOf(leftCommand + leftDirection + leftMotor + "\r");
		commandSendRight = String.valueOf(rightCommand + rightDirection + rightMotor + "\r");
		
		if(bConnect){
			Log.d("TAG", commandSendLeft + " "+ commandSendRight);
			b.sendData(commandSendLeft+commandSendRight);
		
		}
		TextView textX = (TextView) findViewById(R.id.textViewX);
		TextView textY = (TextView) findViewById(R.id.textViewY);
		TextView leftMotorTV = (TextView) findViewById(R.id.mLeft);
		TextView rightMotorTV = (TextView) findViewById(R.id.mRight);
		TextView commandSendTV = (TextView) findViewById(R.id.textViewCmdSend);
		
		
		if(showDebug){
			textX.setText(String.valueOf("X:" + String.format("%.1f",event.values[0]) + "; PWM X: " + axaX));
			textY.setText(String.valueOf("Y:" + String.format("%.1f",event.values[0]) + "; PWM Y: " + axaY));
			leftMotorTV.setText(String.valueOf("Motor-ul stang " + leftDirection + "." + leftMotor) );
			rightMotorTV.setText(String.valueOf("Motor-ul drept " + rightDirection + "." + rightMotor) );
			commandSendTV.setText(String.valueOf("Send:" + commandSendLeft.toUpperCase(Locale.getDefault()) + commandSendRight.toUpperCase(Locale.getDefault())));
			
		}else{
			textX.setText(" ");
			textY.setText(" ");
			leftMotorTV.setText("" );
			rightMotorTV.setText(" ");
			commandSendTV.setText(" ");
		}
		
			
	}
	
	protected void onResume(){
		super.onResume();
		bConnect = b.connectBluetooth(macAddress, false);
		mySensorManger.registerListener(this,mySensor, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	protected void onPause(){
		super.onPause();
		b.bluetoothOnPause();
		mySensorManger.unregisterListener(this);
	}
	

	
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	

	
}
