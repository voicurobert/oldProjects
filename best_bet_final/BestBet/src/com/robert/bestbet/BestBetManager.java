package com.robert.bestbet;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;


public class BestBetManager {
	
	private GoogleCloudMessaging gcm = null;
	private String registrationId = "";
	private Context context = null;
	private AtomicInteger messageId = new AtomicInteger();
	
	
	public BestBetManager(Context context){
		this.context = context;
	}
	
	public void setGCM( GoogleCloudMessaging gcm){
		this.gcm = gcm;
	}
	
	public GoogleCloudMessaging getGCM(){
		return this.gcm;
	}
	
	public Context getContext(){
		return context;
	}
	
	
	protected boolean checkForGooglePlayServices(Activity activity){
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
		if ( resultCode != ConnectionResult.SUCCESS ){
		    if ( GooglePlayServicesUtil.isUserRecoverableError( resultCode ) ){
		    	GooglePlayServicesUtil.getErrorDialog(resultCode, activity, Globals.PLAY_SERVICES_RESOLUTION_REQUEST).show();
		    } else {
		    	Log.i(Globals.TAG, "This device is not supported.");
		    	activity.finish();
		    }
		    return false;
		}
		return true;
	}
	
	protected boolean checkForInternetConnection( Context context ){
		ConnectivityManager cm = ( ConnectivityManager ) context.getSystemService( Context.CONNECTIVITY_SERVICE );
		NetworkInfo ni = cm.getActiveNetworkInfo();
		Log.i(Globals.TAG, String.valueOf(ni.getType()));
		if( ni != null ){
			if( ni.getType() == Globals.TYPE_WIFI ){
				return true;
			}else if( ni.getType() == Globals.TYPE_MOBILE ){
				return true;
			}else if( ni.getType() == Globals.TYPE_NOT_CONNECTED ){
				return false;
			}
		}
		return false;
	}
	
	public String getRegistrationId(){
		return registrationId;
	}
	
	protected String computeRegistrationId( Activity activity ){
		
		final SharedPreferences prefs = getGCMPreferences( context );
		registrationId = prefs.getString( Globals.PREFS_PROPERTY_REG_ID, "" );
		if ( registrationId == null || registrationId.equals( "" ) ){
			
			Log.i(Globals.TAG, "Registration not found.");
			return "";
			
		}
		
		int registeredVersion = prefs.getInt( Globals.PROPERTY_APP_VERSION, Integer.MIN_VALUE );
		int currentVersion = getApplicationVersion( context );
		if ( registeredVersion != currentVersion ){
			
			Log.i(Globals.TAG, "App version changed.");
			return "";
		}
		
		return registrationId;
	}
	
	
	private SharedPreferences getGCMPreferences(Context context){
		return context.getSharedPreferences(Globals.PREFS_NAME, Context.MODE_PRIVATE);
	}
	
	private int getApplicationVersion( Context context ){
		try{
			
		    PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
		    return packageInfo.versionCode;
		}
		catch (NameNotFoundException e){
		    // should never happen
		    throw new RuntimeException("Could not get package name: " + e);
		}
	}
	
	public void registerInBackground( final Activity activity ){
		new AsyncTask< Void, Void, String >(){
			
			@Override
			protected String doInBackground( Void... params ){
				String msg = "";
				try{
					if ( gcm == null){
						gcm = GoogleCloudMessaging.getInstance( activity );
					}
					Log.i( Globals.TAG, " Trying to register on GCM..." );
					registrationId = gcm.register( Globals.GCM_SENDER_ID );
					msg = "Device registered...";
					Log.i( Globals.TAG, msg );
					
					// send registration id to DB server
					sendRegistrationIdToServer();
					Log.i( Globals.TAG, " Sended the registration id to server, check it!" );
					
					// store the regId to SharedPrefs
					storeRegistrationIdToSharedPrefs();
					Log.i( Globals.TAG, " Stored registration id on Shared Prefs" );

				} catch( IOException e){
					msg = "Error: " + e.getMessage();
					Log.i(Globals.TAG, msg);
				}
				
				return msg;
			}
			
			protected void onPostExecute( String msg ){
				Log.i(Globals.TAG, "Registered the regId and sended to db ");
			}
		}.execute( null, null, null );
	}
	
	
	public void sendRegistrationIdToServer(){
		new AsyncTask<Void, Void, String>() {
		    @Override
		    protected String doInBackground(Void... params){
		    	String serverUrl = Globals.SERVER_URL;
				Map<String, String> param = new HashMap<String, String>();
				param.put("regId", registrationId);
				//for (int i = 1; i < 5; i++){
					try{
						sendPostRequestToServer( serverUrl, param );
						
					} catch(IOException e){
						e.printStackTrace();
					}
				//}
				return "true";
		    }

		   
			@Override
		    protected void onPostExecute(String msg){
				Log.i(Globals.TAG, "Sended the reg id to server ");
		    }
		}.execute(null, null, null);
	}
	
	
	private void sendPostRequestToServer(String serverUrl, Map<String, String> params) throws IOException{
		URL url;
		try{
			url = new URL(serverUrl);
			
		}catch( MalformedURLException e){
			throw new IllegalArgumentException("invalid url: " + serverUrl);
		}
		
		StringBuilder sb = new StringBuilder();
		Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
		 Entry<String, String> param = iterator.next();
		sb.append(param.getKey()).append("=").append(param.getValue());
		
		String body = sb.toString();
		//Toast.makeText(context, "Params to send: " + body, Toast.LENGTH_SHORT).show();
		byte[] bytes = body.getBytes();
		HttpURLConnection conn = null;
		
		try{
			 	conn = (HttpURLConnection) url.openConnection();
	            conn.setDoOutput(true);
	            conn.setUseCaches(false);
	            conn.setFixedLengthStreamingMode(bytes.length);
	            conn.setRequestMethod("POST");
	            conn.setRequestProperty("Content-Type",
	                    "application/x-www-form-urlencoded;charset=UTF-8");
	            // post the request
	            OutputStream out = conn.getOutputStream();
	            out.write(bytes);
	            out.close();
	             
	            // handle the response
	            int status = conn.getResponseCode();
	             
	            // If response is not success
	            if (status != 200) {
	                 
	              throw new IOException("Post failed with error code " + status);
	            }
		} finally {
			if (conn != null) {
                conn.disconnect();
            }
		}
	}
	
	private void storeRegistrationIdToSharedPrefs(){
		final SharedPreferences prefs = getGCMPreferences(context);
		int appVersion = getApplicationVersion(context);
		Log.i(Globals.TAG, "Saving regId on app version " + appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		
		editor.putString(Globals.PREFS_PROPERTY_REG_ID, registrationId);
		editor.putInt(Globals.PROPERTY_APP_VERSION, appVersion);
		editor.commit();
	}
	
	// unregister device, use it just for testing
	
	public void unregister(){
		Log.d(Globals.TAG, "UNREGISTER USERID: " + registrationId);
		new AsyncTask<Void, Void, String>(){
		    @Override
		    protected String doInBackground(Void... params){
		    	String msg = "";
		    	try{
		    		Bundle data = new Bundle();
		    		data.putString("action", "com.robert.bestbet.UNREGISTER");
		    		String id = Integer.toString(messageId.incrementAndGet());
		    		gcm.send(Globals.GCM_SENDER_ID + "@gcm.googleapis.com", id, Globals.GCM_TIME_TO_LIVE, data);
		    		msg = "Sent unregistration";
		    		gcm.unregister();
		    	}catch (IOException ex){
		    		msg = "Error :" + ex.getMessage();
		    	}
		    	return msg;
		    	}

		    @Override
		    protected void onPostExecute(String msg){
		    	removeRegistrationId();
		    	Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
			
		    }
		}.execute();
		
	}
	
	private void removeRegistrationId(){
		final SharedPreferences prefs = getGCMPreferences(context);
		int appVersion = getApplicationVersion(context);
		Log.i(Globals.TAG, "Removig regId on app version " + appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.remove(Globals.PREFS_PROPERTY_REG_ID);
		editor.commit();
		registrationId = null;
	}

}
