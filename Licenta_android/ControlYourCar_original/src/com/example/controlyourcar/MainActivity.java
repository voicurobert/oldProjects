package com.example.controlyourcar;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	
	Button buttonAccel, buttonButoane,buttonSettings, buttonAbout;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //instantiere butoane si adaugare event
        
        buttonAccel = (Button)findViewById(R.id.button_Accelerometer);
        buttonAccel.setOnClickListener(this);	
        
        buttonButoane = (Button)findViewById(R.id.button_Butoane);
        buttonButoane.setOnClickListener(this);
        
        buttonAbout = (Button)findViewById(R.id.buttonAbout);
        buttonAbout.setOnClickListener(this);
        
        buttonSettings = (Button)findViewById(R.id.buttonSettings);
        buttonSettings.setOnClickListener(this);
        
    }
    
    public void onClick(View v){
    	switch(v.getId()){
    	
    	//creare intent pentru fiecare buton, pentru a porni activitatea aferenta fiecarui buton
    	
    	case R.id.button_Accelerometer:
    		Intent intentAccel = new Intent(this, AccelActivity.class);
    		startActivity(intentAccel);
    		break;
    		
    	case R.id.button_Butoane:
    		Intent intentButoane = new Intent(this,ButoaneActivity.class);
    		startActivity(intentButoane);
    		break;
    		
    	case R.id.buttonSettings:
    		Intent intentSettings = new Intent(this,SettingsActivity.class);
    		startActivity(intentSettings);
    		break;
    		
    	case R.id.buttonAbout:
    		Intent intentAbout = new Intent(this, AboutActivity.class);
    		startActivity(intentAbout);
    		break;
    		
    	default:
    		break;
    	
    	}
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}

