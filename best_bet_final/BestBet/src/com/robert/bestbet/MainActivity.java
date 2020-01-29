package com.robert.bestbet;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	
	protected BestBetManager manager = null; 
    private String regId;

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = new BestBetManager(getApplicationContext());
       // boolean isInternet = manager.checkForInternetConnection(getApplicationContext());
       // if( isInternet == true ){
        	 TextView tv = (TextView) findViewById(R.id.regId_tv);
             final Button todaysTicketButton = ( Button ) findViewById(R.id.todayTicket_button);
             final Button ticketHistoryButton = ( Button ) findViewById(R.id.ticketHistory_button);
             
             todaysTicketButton.setOnClickListener( new View.OnClickListener() {
     			
     			@Override
     			public void onClick(View v) {
     				// TODO Auto-generated method stub
     				Intent todaysTicketIntent = new Intent( getApplicationContext() , TodaysTicketActivity.class );
     				startActivity( todaysTicketIntent );
     			}
     		});
             
             ticketHistoryButton.setOnClickListener( new View.OnClickListener() {
     			
     			@Override
     			public void onClick(View v) {
     				// TODO Auto-generated method stub
     				Intent ticketHistoryIntent = new Intent( getApplicationContext() , TicketHistoryActivity.class );
     				startActivity( ticketHistoryIntent );
     			}
     		});
             
     	     if ( manager.checkForGooglePlayServices(this) ){
     	    	 manager.setGCM(GoogleCloudMessaging.getInstance(this));
                 // get registration id from shared prefs if exists
                 regId = manager.computeRegistrationId(this);
        	     tv.setText( regId );
     	    	 Toast.makeText(getApplicationContext(), "a:" + regId, Toast.LENGTH_SHORT).show();
     	    	 if ( regId == "" ){ // if regId is null, register to GCM
     	    		 Log.i(Globals.TAG, "trying to generate id ");
     	    		 
     	    		 manager.registerInBackground( this );
     	    		 manager.computeRegistrationId( this );
     	    		 regId = manager.getRegistrationId();
     	    		 Toast.makeText(getApplicationContext(), "b:  " + regId, Toast.LENGTH_SHORT).show();
     	    		 tv.setText(regId);
     	    	 } else {
     	    		 manager.sendRegistrationIdToServer();
     	    		 Toast.makeText(getApplicationContext(), "c:" + regId, Toast.LENGTH_SHORT).show();
     	    		 regId = manager.getRegistrationId();
     	    		 tv.setText(regId);
     	    	 }
     	     }
     	     
       // }else{
      //  	Toast.makeText(getApplicationContext(), "No internet connection! ", Toast.LENGTH_SHORT).show();
     //   }
       
    }
    
    @Override
    public void onResume( ){
    	super.onResume();
    	// manager.checkForInternetConnection(getApplicationContext());
    }
   
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if( id == R.id.action_about){
        	Intent aboutIntent = new Intent( getApplicationContext() , AboutActivity.class );
			startActivity( aboutIntent );
        } else if ( id == R.id.action_how_to ){
        	Intent howToIntent = new Intent( getApplicationContext() , HowToActivity.class );
			startActivity( howToIntent );
        }
        return super.onOptionsItemSelected(item);
    }
}

  