package com.robert.bestbet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class TicketHistoryActivity extends Activity {
	
	private ArrayList<String> allTickets;
	private ArrayList<String>  allGames;
	private ArrayList<String> allData;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ticket_history_activity);
	    
	    ListView allTickets_listview = (ListView) findViewById(R.id.tickets_listview);
	    allTickets = new ArrayList<String>();
	    allGames = new ArrayList<String>();
	    allData = new ArrayList<String>();
	    computeTicketsAndGames();
	    Log.i(Globals.TAG, allData.toString());
	    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, allData);
                // Set The Adapter
        allTickets_listview.setAdapter(arrayAdapter); 
	  
        // TO DO: item selected
	}
	
	
	public void setAllTickets( ArrayList<String>  tickets ){

		this.allTickets = tickets;
		
	}
	
	public void setAllGanes( ArrayList<String>  games ){
		this.allGames = games;
	}
	
	public ArrayList<String>  getTodaysTickets(){
		return allTickets;
	}
	
	public ArrayList<String>  getTodaysGames(){
		return allGames;
	}
	
	
	private void computeTicketsAndGames(){
		
		new Thread( new Runnable() {
			
			@Override
			public void run() {
				
				final String ticketsResult = DatabaseManager.instance.getDataFromUrl(Globals.ALL_TICKETS);
				final String gamesResult = DatabaseManager.instance.getDataFromUrl(Globals.ALL_GAMES);
				
				runOnUiThread( new Runnable(){

					@Override
					public void run() {
						
						// TODO Auto-generated method stub
						ArrayList<Game> allGames = DatabaseManager.instance.parseGamesDataFromDatabase(gamesResult);
						ArrayList<Ticket> alltickets = DatabaseManager.instance.parseTicketsDataFromDatabase( ticketsResult, allGames );
						
						computeAllTickets( alltickets );
					
					}
					
				});
			}
		}).start();
	}
	
	private void computeAllTickets( ArrayList<Ticket> allTickets ){
		
		for( Iterator<Ticket> t = allTickets.iterator(); t.hasNext(); ){
			Ticket ticket = ( Ticket )t.next();
			allData.add( createStringForTicket(ticket) );
		
		}

	}
	
	private String createStringForTicket( Ticket ticket ){
		Log.i(Globals.TAG, ticket.getTicketDate().toString());
		return "The "+ticket.getTicketType() + " Ticket from " + ticket.getTicketDate() + " was " + ticket.getTicketResult(); 
	}
}
