package com.robert.bestbet;


import java.util.ArrayList;
import java.util.Iterator;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Space;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class TodaysTicketActivity extends Activity {
	
	private ActionBar.Tab tigerTab, lionTab;
	private Fragment fragmentTigerTab = new FragmentTigerTab();
	private Fragment fragmentLionTab = new FragmentLionTab();
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.todays_ticket_activity);
	    
	    ActionBar actionBar = getActionBar();
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    
	    tigerTab = actionBar.newTab().setText("Tiger Ticket");
	    lionTab = actionBar.newTab().setText("Lion Ticket");
	    
	    tigerTab.setTabListener( new TigerTabListener( fragmentTigerTab));
	    lionTab.setTabListener( new LionTabListener( fragmentLionTab));
	    actionBar.addTab(tigerTab);
	    actionBar.addTab(lionTab);
	   
	}
	
	private class TigerTabListener implements ActionBar.TabListener{
		private Fragment fragment;
		
		public TigerTabListener( Fragment f){
			this.fragment = f;
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			ft.replace(R.id.FrameLayout1, fragment);
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			ft.remove(fragment);
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class LionTabListener implements ActionBar.TabListener{
		private Fragment fragment;
		
		public LionTabListener( Fragment f){
			this.fragment = f;
		}
		
		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			ft.replace(R.id.FrameLayout1, fragment);
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			ft.remove(fragment);
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class FragmentTigerTab extends Fragment{
		
		private String ticketsResult = "";
		private String gamesResult = "";
		private TableLayout tigerTableLayout;
		
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ){
			View v = inflater.inflate(R.layout.tiger_ticket_activity, container, false);
			Log.i(Globals.TAG, "View");
			final DatabaseManager dbManager = new DatabaseManager();
			
			new Thread( new Runnable() {
				
				@Override
				public void run() {
					
					ticketsResult = dbManager.getTodaysTickets();
					Log.i(Globals.TAG, ticketsResult);
					gamesResult = dbManager.getDataFromUrl(Globals.TODAYS_GAMES_URL);
					
					runOnUiThread( new Runnable(){

						@Override
						public void run() {
							
							// TODO Auto-generated method stub
							ArrayList<Game> allGames = dbManager.parseGamesDataFromDatabase(gamesResult);
							ArrayList<Ticket> tickets = dbManager.parseTicketsDataFromDatabase( ticketsResult, allGames );
							Log.i(Globals.TAG, "Draw");
							createTodaysTigerTicket( dbManager.getTigerTicketFromTickets( tickets ) );
						}
						
					});
				}
			
			}).start();
			return v;
		}
		
		private void createTodaysTigerTicket( Ticket ticket ){
			tigerTableLayout = (TableLayout) findViewById(R.id.tiger_tableLayout);
			tigerTableLayout.setStretchAllColumns(true);
			Log.i(Globals.TAG, "Unset?"+ticket.toString());
			ArrayList< Game > ticketGames = ticket.getGames();
			// create the table headings
			// first row
			TableRow tableHeading = new TableRow(getApplicationContext());
			tableHeading.setOrientation( android.view.Gravity.CENTER_HORIZONTAL);
			
			// first column
			TextView gamesHeadingTextView = new TextView(getApplicationContext());
			gamesHeadingTextView.setGravity( android.view.Gravity.CENTER_HORIZONTAL );
			gamesHeadingTextView.setTextSize(22);
			gamesHeadingTextView.setText("Games");
			gamesHeadingTextView.setTextColor(Color.BLACK);
			gamesHeadingTextView.setTypeface(null, Typeface.NORMAL);
			tableHeading.addView(gamesHeadingTextView);
			// second column
			TextView gameOddHeadingTextView = new TextView(getApplicationContext());
			gameOddHeadingTextView.setGravity( android.view.Gravity.CENTER_HORIZONTAL );
			gameOddHeadingTextView.setTextColor(Color.BLACK);
			gameOddHeadingTextView.setTypeface(null, Typeface.NORMAL);
			gameOddHeadingTextView.setTextSize(22);
			gameOddHeadingTextView.setText("Game Odd");
			tableHeading.addView(gameOddHeadingTextView);
			
			// thirst column
			TextView gameTipHeadingTextView = new TextView(getApplicationContext());
			gameTipHeadingTextView.setGravity( android.view.Gravity.CENTER_HORIZONTAL );
			gameTipHeadingTextView.setTypeface(null , Typeface.NORMAL);
			gameTipHeadingTextView.setTextColor(Color.BLACK);
			gameTipHeadingTextView.setTextSize(22);
			gameTipHeadingTextView.setText("Game Tip");
			tableHeading.addView(gameTipHeadingTextView);
			
			tigerTableLayout.addView(tableHeading);
			
			for( Iterator<Game> i = ticketGames.iterator(); i.hasNext(); ){
				Game game = ( Game ) i.next();
				// create a row, to add data
				TableRow tr = new TableRow(getApplicationContext());
				tr.setOrientation( android.view.Gravity.CENTER_HORIZONTAL );
				// create a text view and add both games to it "Team A vs Team B"
				TextView gamesTextView = new TextView(getApplicationContext());
				gamesTextView.setGravity( android.view.Gravity.CENTER_HORIZONTAL );
				String gamesString = createGameName( game.getTeamA(), game.getTeamB());
				gamesTextView.setText(gamesString);
				gamesTextView.setTextSize(12);
				gamesTextView.setTextColor(Color.DKGRAY);
				gamesTextView.setTypeface(null, Typeface.ITALIC);
				tr.addView(gamesTextView);
				
				TextView gameOddTextView = new TextView(getApplicationContext());
				gameOddTextView.setGravity( android.view.Gravity.CENTER_HORIZONTAL );
				gameOddTextView.setText( String.valueOf(game.getGameOdd() ));
				gameOddTextView.setTextSize(12);
				gameOddTextView.setTextColor(Color.DKGRAY);
				gameOddTextView.setTypeface(null, Typeface.ITALIC);
				tr.addView(gameOddTextView);
				
				TextView gameTipTextView = new TextView(getApplicationContext());
				gameTipTextView.setGravity( android.view.Gravity.CENTER_HORIZONTAL );
				gameTipTextView.setText( String.valueOf(game.getExpectedResult() ));
				gameTipTextView.setTextSize(12);
				gameTipTextView.setTextColor(Color.DKGRAY);
				gameTipTextView.setTypeface(null, Typeface.ITALIC);
				tr.addView(gameTipTextView);
				
				tigerTableLayout.addView(tr);
			}
			
			
			// add some empty rows
			for (int j = 0; j <= 5; j++){
				TableRow row = new TableRow(getApplicationContext());
				TextView emptyTextView = new TextView(getApplicationContext());
				row.addView(emptyTextView);
				tigerTableLayout.addView(row);
				
			}
			
			// add info in table layout for ticket: ticket total odd, ticket stake, ticket possible win
			// create a row for total odd
			TableRow ticketTotalOddRow = new TableRow(getApplicationContext());
			ticketTotalOddRow.setOrientation( android.view.Gravity.CENTER_HORIZONTAL );
			// create a header for Total Odd:
			TextView ticketTotalOddHeaderTextView = new TextView(getApplicationContext());
			ticketTotalOddHeaderTextView.setGravity( android.view.Gravity.CENTER_HORIZONTAL );
			ticketTotalOddHeaderTextView.setText(" Total Odd: ");
			ticketTotalOddHeaderTextView.setTextSize(22);
			ticketTotalOddHeaderTextView.setTextColor(Color.BLUE);
			ticketTotalOddHeaderTextView.setTypeface(null, Typeface.BOLD);
			ticketTotalOddRow.addView(ticketTotalOddHeaderTextView);
			// create a text view to add data
			TextView ticketTotalOddTextView = new TextView(getApplicationContext());
			ticketTotalOddTextView.setGravity( android.view.Gravity.CENTER_HORIZONTAL );
			ticketTotalOddTextView.setText( String.valueOf(ticket.getTicketOdd()));
			ticketTotalOddTextView.setTextSize(22);
			ticketTotalOddTextView.setTextColor(Color.BLUE);
			ticketTotalOddTextView.setTypeface(null, Typeface.BOLD);
			ticketTotalOddRow.addView(ticketTotalOddTextView);
			tigerTableLayout.addView(ticketTotalOddRow);
			
			TableRow ticketStakeRow = new TableRow(getApplicationContext());
			ticketStakeRow.setOrientation( android.view.Gravity.CENTER_HORIZONTAL );
			TextView ticketStakeHeaderTextView = new TextView(getApplicationContext());
			ticketStakeHeaderTextView.setGravity( android.view.Gravity.CENTER_HORIZONTAL );
			ticketStakeHeaderTextView.setText(" Stake: ");
			ticketStakeHeaderTextView.setTextSize(22);
			ticketStakeHeaderTextView.setTextColor(Color.BLUE);
			ticketStakeHeaderTextView.setTypeface(null, Typeface.BOLD);
			ticketStakeRow.addView(ticketStakeHeaderTextView);
			TextView ticketStakeTextView = new TextView(getApplicationContext());
			ticketStakeTextView.setGravity( android.view.Gravity.CENTER_HORIZONTAL );
			ticketStakeTextView.setText( String.valueOf(ticket.getTicketStake()));
			ticketStakeTextView.setTextSize(22);
			ticketStakeTextView.setTextColor(Color.BLUE);
			ticketStakeTextView.setTypeface(null, Typeface.BOLD);
			ticketStakeRow.addView(ticketStakeTextView);
			tigerTableLayout.addView(ticketStakeRow);
			
			
			TableRow ticketPossibleWinRow = new TableRow(getApplicationContext());
			ticketPossibleWinRow.setOrientation( android.view.Gravity.CENTER_HORIZONTAL );
			TextView ticketPossibleWinHeaderTextView = new TextView(getApplicationContext());
			ticketPossibleWinHeaderTextView.setGravity( android.view.Gravity.CENTER_HORIZONTAL );
			ticketPossibleWinHeaderTextView.setText(" Possible Win: ");
			ticketPossibleWinHeaderTextView.setTextSize(22);
			ticketPossibleWinHeaderTextView.setTextColor(Color.BLUE);
			ticketPossibleWinHeaderTextView.setTypeface(null, Typeface.BOLD);
			ticketPossibleWinRow.addView(ticketPossibleWinHeaderTextView);
			
			TextView ticketPossibleWinTextView = new TextView(getApplicationContext());
			ticketPossibleWinTextView.setGravity( android.view.Gravity.CENTER_HORIZONTAL );
			ticketPossibleWinTextView.setText( String.valueOf(ticket.getTicketPossibleWin()));
			ticketPossibleWinTextView.setTextSize(22);
			ticketPossibleWinTextView.setTextColor(Color.BLUE);
			ticketPossibleWinTextView.setTypeface(null, Typeface.BOLD);
			ticketPossibleWinRow.addView(ticketPossibleWinTextView);
			
			tigerTableLayout.addView(ticketPossibleWinRow);
		}
		
		private String createGameName( String teamA, String teamB ){
			String gameName = teamA + " vs " + teamB;
			return gameName;
		}
		
		
	}
	
	private class FragmentLionTab extends Fragment{
		
		private String ticketsResult = "";
		private String gamesResult = "";
		private TableLayout lionTableLayout;
		
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ){
			View v = inflater.inflate(R.layout.lion_ticket_activity, container, false);
			
			final DatabaseManager dbManager = new DatabaseManager();
			new Thread( new Runnable() {
				
				@Override
				public void run() {
					
					ticketsResult = dbManager.getTodaysTickets();
					gamesResult = dbManager.getDataFromUrl(Globals.TODAYS_GAMES_URL);
					//Log.i(Globals.TAG, result);
					runOnUiThread( new Runnable(){

						@Override
						public void run() {
							
							// TODO Auto-generated method stub
							ArrayList<Game> allGames = dbManager.parseGamesDataFromDatabase(gamesResult);
							ArrayList<Ticket> tickets = dbManager.parseTicketsDataFromDatabase( ticketsResult, allGames );
							createTodaysLionTicket( dbManager.getLionTicketFromTickets( tickets ) );
						}
						
					});
				}
			}).start();
			
			return v;
		}
		
		private void createTodaysLionTicket( Ticket ticket){
			lionTableLayout = (TableLayout) findViewById(R.id.lion_tableLayout);
			lionTableLayout.setStretchAllColumns(true);
			ArrayList< Game > ticketGames = ticket.getGames();
			// create the table headings
			// first row
			TableRow tableHeading = new TableRow(getApplicationContext());
			tableHeading.setOrientation( android.view.Gravity.CENTER_HORIZONTAL);
			
			// first column
			TextView gamesHeadingTextView = new TextView(getApplicationContext());
			gamesHeadingTextView.setGravity( android.view.Gravity.CENTER_HORIZONTAL );
			gamesHeadingTextView.setTextSize(24);
			gamesHeadingTextView.setText("Games");
			gamesHeadingTextView.setTypeface(null, Typeface.BOLD);
			tableHeading.addView(gamesHeadingTextView);
			// second column
			TextView gameOddHeadingTextView = new TextView(getApplicationContext());
			gameOddHeadingTextView.setGravity( android.view.Gravity.CENTER_HORIZONTAL );
			gameOddHeadingTextView.setTypeface(null, Typeface.BOLD);
			gameOddHeadingTextView.setTextSize(24);
			gameOddHeadingTextView.setText("Game Odd");
			tableHeading.addView(gameOddHeadingTextView);
			
			// thirst column
			TextView gameTipHeadingTextView = new TextView(getApplicationContext());
			gameTipHeadingTextView.setGravity( android.view.Gravity.CENTER_HORIZONTAL );
			gameTipHeadingTextView.setTypeface(null , Typeface.BOLD);
			gameTipHeadingTextView.setTextSize(24);
			gameTipHeadingTextView.setText("Game Tip");
			tableHeading.addView(gameTipHeadingTextView);
			
			lionTableLayout.addView(tableHeading);
			
			for( Iterator<Game> i = ticketGames.iterator(); i.hasNext(); ){
				Game game = ( Game ) i.next();
				// create a row, to add data
				TableRow tr = new TableRow(getApplicationContext());
				tr.setOrientation( android.view.Gravity.CENTER_HORIZONTAL );
				// create a text view and add both games to it "Team A vs Team B"
				TextView gamesTextView = new TextView(getApplicationContext());
				gamesTextView.setGravity( android.view.Gravity.CENTER_HORIZONTAL );
				String gamesString = createGameName( game.getTeamA(), game.getTeamB());
				gamesTextView.setText(gamesString);
				tr.addView(gamesTextView);
				
				TextView gameOddTextView = new TextView(getApplicationContext());
				gameOddTextView.setGravity( android.view.Gravity.CENTER_HORIZONTAL );
				gameOddTextView.setText( String.valueOf(game.getGameOdd() ));
				tr.addView(gameOddTextView);
				
				TextView gameTipTextView = new TextView(getApplicationContext());
				gameTipTextView.setGravity( android.view.Gravity.CENTER_HORIZONTAL );
				gameTipTextView.setText( String.valueOf(game.getExpectedResult() ));
				tr.addView(gameTipTextView);
				
				lionTableLayout.addView(tr);
			}
			
			// add info in table layout for ticket: ticket total odd, ticket stake, ticket possible win
			// create a row for total odd
			TableRow ticketTotalOddRow = new TableRow(getApplicationContext());
			ticketTotalOddRow.setOrientation( android.view.Gravity.CENTER_HORIZONTAL );
			// create a header for Total Odd:
			TextView ticketTotalOddHeaderTextView = new TextView(getApplicationContext());
			ticketTotalOddHeaderTextView.setGravity( android.view.Gravity.CENTER_HORIZONTAL );
			ticketTotalOddHeaderTextView.setText(" Total Odd: ");
			ticketTotalOddRow.addView(ticketTotalOddHeaderTextView);
			// create a text view to add data
			TextView ticketTotalOddTextView = new TextView(getApplicationContext());
			ticketTotalOddTextView.setGravity( android.view.Gravity.CENTER_HORIZONTAL );
			ticketTotalOddTextView.setText( String.valueOf(ticket.getTicketOdd()));
			ticketTotalOddRow.addView(ticketTotalOddTextView);
			lionTableLayout.addView(ticketTotalOddRow);
			
			TableRow ticketStakeRow = new TableRow(getApplicationContext());
			ticketStakeRow.setOrientation( android.view.Gravity.CENTER_HORIZONTAL );
			TextView ticketStakeHeaderTextView = new TextView(getApplicationContext());
			ticketStakeHeaderTextView.setGravity( android.view.Gravity.CENTER_HORIZONTAL );
			ticketStakeHeaderTextView.setText(" Stake: ");
			ticketStakeRow.addView(ticketStakeHeaderTextView);
			TextView ticketStakeTextView = new TextView(getApplicationContext());
			ticketStakeTextView.setGravity( android.view.Gravity.CENTER_HORIZONTAL );
			ticketStakeTextView.setText( String.valueOf(ticket.getTicketStake()));
			ticketStakeRow.addView(ticketStakeTextView);
			lionTableLayout.addView(ticketStakeRow);
			
			
			TableRow ticketPossibleWinRow = new TableRow(getApplicationContext());
			ticketPossibleWinRow.setOrientation( android.view.Gravity.CENTER_HORIZONTAL );
			TextView ticketPossibleWinHeaderTextView = new TextView(getApplicationContext());
			ticketPossibleWinHeaderTextView.setGravity( android.view.Gravity.CENTER_HORIZONTAL );
			ticketPossibleWinHeaderTextView.setText(" Possible Win: ");
			ticketPossibleWinRow.addView(ticketPossibleWinHeaderTextView);
			
			TextView ticketPossibleWinTextView = new TextView(getApplicationContext());
			ticketPossibleWinTextView.setGravity( android.view.Gravity.CENTER_HORIZONTAL );
			ticketPossibleWinTextView.setText( String.valueOf(ticket.getTicketPossibleWin()));
			ticketPossibleWinRow.addView(ticketPossibleWinTextView);
			
			lionTableLayout.addView(ticketPossibleWinRow);
		}
		
		private String createGameName( String teamA, String teamB ){
			String gameName = teamA + " vs " + teamB;
			return gameName;
		}
	}
	
	
}
