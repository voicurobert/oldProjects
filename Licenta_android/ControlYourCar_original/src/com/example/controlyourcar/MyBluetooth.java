package com.example.controlyourcar;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

@SuppressLint("NewApi")
public class MyBluetooth {
public final static String TAG = "ControlCar";
	
	private static BluetoothAdapter myBluetoothAdapter = null;
	private BluetoothSocket myBluetoothSocket = null;
	private OutputStream os = null;
	private ConnectedThread myConnectedThread;
	
	private static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); 
	private final Handler myHandler;
	public final static int BLUETOOTH_UNAVAILABLE = 1;
	public final static int BLUETOOTH_INCORRECT_ADDRESS = 2;
	public final static int BLUETOOTH_REQUEST_ENABLED = 3;
	public final static int BLUETOOTH_SOCKET_FAILED = 4;
	public final static int BLUETOOTH_RECEIVED_MESSAGE = 5;
	
	
	//Creearea constructorului MyBluetooth
	MyBluetooth(Context c, Handler h){
		myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		myHandler = h;
		if(myBluetoothAdapter == null){
			myHandler.sendEmptyMessage(BLUETOOTH_UNAVAILABLE);
			return;
		}
	}
	
	//Creearea metodei "checkBluetoothState" pentru a verifica starea bluetooth-ului
	public void checkBluetoothState(){
		if(myBluetoothAdapter == null){
			myHandler.sendEmptyMessage(BLUETOOTH_UNAVAILABLE);
		}else{
			if(myBluetoothAdapter.isEnabled()){
				Log.d(TAG, "Bluetooth pornit");
			}else{
				myHandler.sendEmptyMessage(BLUETOOTH_REQUEST_ENABLED);
			}
		}
	}
	
	//creare metoda pentru a realiza un socket bluetooth

	private BluetoothSocket createBluetoothSocket(BluetoothDevice d) throws IOException{
		if(Build.VERSION.SDK_INT >= 10){
			try{
				
				final Method m = d.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[]{UUID.class }  );
				return (BluetoothSocket)m.invoke(d, myUUID);
				
			}catch(Exception e){
				Log.d(TAG, "Nu s-a putut creea RFCOMM Connection",e);
			}
		}
		return d.createInsecureRfcommSocketToServiceRecord(myUUID);
	}
	
	public boolean connectBluetooth(String address, boolean listenInputStream){
		boolean conn = false;
		if(!BluetoothAdapter.checkBluetoothAddress(address)){
			myHandler.sendEmptyMessage(BLUETOOTH_INCORRECT_ADDRESS);
			return false;
		}else{
			BluetoothDevice d = myBluetoothAdapter.getRemoteDevice(address);
			d.createBond();
			try{
				myBluetoothAdapter.getRemoteDevice(address);
				myBluetoothSocket = createBluetoothSocket(d);
				
			}catch(IOException e){
				Log.e(TAG, "Nu s-a putut crea socket-ul"+e.toString());
				myHandler.sendEmptyMessage(BLUETOOTH_SOCKET_FAILED);
				return false;
			}
			
			myBluetoothAdapter.cancelDiscovery();
			try{
				myBluetoothAdapter.cancelDiscovery();
				myBluetoothSocket.connect();
				
				if(!myBluetoothSocket.isConnected()){
					myBluetoothSocket.connect();
				}
			}catch(IOException e){
			            
				try {
					myBluetoothSocket.connect();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				try{
					myBluetoothSocket.close();
				}catch(IOException e1){
					myHandler.sendEmptyMessage(BLUETOOTH_SOCKET_FAILED);
					return false;
				}
				
			}
			
			try{
				os = myBluetoothSocket.getOutputStream();
				conn = true;
			}catch(IOException e){
				Log.e(TAG, "Nu s-a putut crea streamul de iesire" + e.getMessage());
				myHandler.sendEmptyMessage(BLUETOOTH_SOCKET_FAILED);
				return false;
			}
			
			if(listenInputStream){
				myConnectedThread = new ConnectedThread();
				myConnectedThread.start();
			}
		}
		
		return conn;
	}
	
	//creeare metoda in cazul in care bluetooth-ul este ON PAUSE
	public void bluetoothOnPause(){
		if(os != null){
			try{
				os.flush();
			}catch(IOException e){
				myHandler.sendEmptyMessage(BLUETOOTH_SOCKET_FAILED);
				return;
			}
		}
		
		if(myBluetoothSocket != null){
			try{
				myBluetoothSocket.close();
			}catch(IOException e1){
				myHandler.sendEmptyMessage(BLUETOOTH_SOCKET_FAILED);
				return;
			}
		}
	}
	
	//creeare metoda pentru trimitere date
	public void sendData(String s){
		byte[] buffer = s.getBytes();
		Log.i(TAG, "Date de trimis: "+ s);
		if(os != null){
			try{
				if( myBluetoothSocket.isConnected() == false ){
					
					myBluetoothSocket.connect();
				}
				os.write(buffer);
			}catch(IOException e){
				Log.e( TAG, "Nu s-au putut trimite datele " + e.getMessage()  );
				myHandler.sendEmptyMessage(BLUETOOTH_SOCKET_FAILED);
				return;
			}
		}else{
			Log.e(TAG, "streamul de iesire este null");
		}
			
	}
	
	
	
	//creare clasa thread
	private class ConnectedThread extends Thread{
		private final InputStream is;
		
		public ConnectedThread(){
			InputStream tempis = null;
			
			try{
				tempis = myBluetoothSocket.getInputStream();
				
			}catch(IOException e){
				Log.e(TAG, "In ConnectedThread nu s-a putut primii streamul de intrare" + e.getMessage());
			}
			
			is = tempis;
		}
		
		public void run(){
			byte[] buffer = new byte[256];
			int b;
			
			while(true){
				try{
					b = is.read(buffer);
					myHandler.obtainMessage(BLUETOOTH_RECEIVED_MESSAGE,b,-1,buffer).sendToTarget();
				}catch(IOException e){
					break;
					
				}
			}
		}
	}
}
