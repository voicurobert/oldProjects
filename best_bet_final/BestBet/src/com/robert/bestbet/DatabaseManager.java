package com.robert.bestbet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;







import android.app.Activity;
import android.util.Log;
import android.widget.TableLayout;

public class DatabaseManager {

	private Map<Integer, Ticket> tickets;
	private Ticket todaysTicket;
	public static final DatabaseManager instance = new DatabaseManager();
	public DatabaseManager() {

	}

	public Map<Integer, Ticket> getTickets() {
		return tickets;
	}

	public Ticket getTodaysTicket() {
		return todaysTicket;
	}

	public void setTodaysTicket(Ticket ticket) {
		this.todaysTicket = ticket;
	}

	public void setTickets(Map<Integer, Ticket> tickets) {
		this.tickets = tickets;
	}

	public String getTodaysTickets() {
		try {
			HttpPost httppost;
			HttpClient httpclient;
			httpclient = new DefaultHttpClient();
			httppost = new HttpPost(Globals.TODAYS_TICKET_URL);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			final String response = httpclient.execute(httppost,
					responseHandler);

			return response.trim();
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	public String getDataFromUrl( String url ){
		try {
			HttpPost httppost;
			HttpClient httpclient;
			httpclient = new DefaultHttpClient();
			httppost = new HttpPost( url );
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			final String response = httpclient.execute(httppost,
					responseHandler);

			return response.trim();
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	public ArrayList<Ticket> parseTicketsDataFromDatabase(String result, ArrayList< Game > allGames ) {
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		// get the games associated with this ticket
		ArrayList< Game > games = allGames;
	
		
		try {
			JSONObject jsonResponse = new JSONObject(result);
			
			JSONArray jsonTicketsMainNode = jsonResponse.optJSONArray(Globals.TICKETS_TABLE_NAME);
			
			for (int i = 0; i < jsonTicketsMainNode.length(); i++) {
				JSONObject jsonTicketChildNode = jsonTicketsMainNode.getJSONObject(i);
				Ticket ticket = new Ticket();
				ArrayList< Game > specificGames = new ArrayList<Game>();
				ticket.setTicketId( (int) Integer.valueOf( jsonTicketChildNode.optInt( "TICKET_ID" ) ) );
				specificGames = getGameForTicketId( games, ticket.getTicketId() );
				ticket.setTicketType(jsonTicketChildNode.optString("TICKET_TYPE"));
				//Log.i(Globals.TAG, ticket.getTicketType());
				ticket.setTicketStake(Float.valueOf(jsonTicketChildNode.optString("TICKET_STAKE")));
				ticket.setTicketOdd(Float.valueOf(jsonTicketChildNode.optString("TICKET_ODD")));
				ticket.setTicketPossibleWin(Float.valueOf(jsonTicketChildNode.optString("TICKET_EARNINGS")));
				ticket.setNrOfGames(Integer.valueOf(jsonTicketChildNode.optInt("NR_OF_GAMES")));
				//SimpleDateFormat ticketDate = new SimpleDateFormat("yyyy-MM-dd");
				//ticketDate.parse(jsonTicketChildNode.opt("TICKET_DATE").toString());
				//Date d = new Date(ticketDate.getCalendar().YEAR, ticketDate.getCalendar().MONTH, ticketDate.getCalendar().DAY_OF_MONTH);
			
			
				ticket.setTicketDate((jsonTicketChildNode.opt("TICKET_DATE").toString()));
				// add the games for this ticket
				ticket.setGames(specificGames);
				tickets.add(ticket);
			}
		} catch (JSONException j) {
			j.printStackTrace();
	//	} catch (ParseException p) {
	//		p.printStackTrace();
		}
		return tickets;
	}

	public ArrayList<Game> parseGamesDataFromDatabase( String result ) {
		ArrayList<Game> games = new ArrayList<Game>();
		try {
			JSONObject jsonResponse = new JSONObject(result);
			
			JSONArray jsonGamesMainNode = jsonResponse.optJSONArray(Globals.GAMES_TABLE_NAME);
		
			for (int j = 0; j < jsonGamesMainNode.length(); j++) {
				JSONObject jsonGameChildNode = jsonGamesMainNode.getJSONObject(j);
				Game game = new Game();
				game.setTicketIDFK( (int) Integer.valueOf( jsonGameChildNode.optInt( "TICKET_ID" ) ) );
				game.setTeamA(jsonGameChildNode.optString("TEAM_A"));
				game.setTeamB(jsonGameChildNode.optString("TEAM_B"));
				game.setGameOdd( (float) Float.valueOf(jsonGameChildNode.optString("GAME_ODD")));
				game.setExpectedResult(jsonGameChildNode.optString("GAME_EXPECTED_RESULT"));
				games.add(game);
			}

		} catch (JSONException j) {
			j.printStackTrace();
		}
		return games;
	}
	
	public ArrayList< Game > getGameForTicketId( ArrayList< Game > games, int ticketId ){
		ArrayList< Game > specificGames = new ArrayList<Game>();
		for( Iterator<Game> i = games.iterator(); i.hasNext(); ){
			Game game = ( Game ) i.next();
			if( game.getTicketIDFK() == ticketId ){
				
				specificGames.add(game);
			}		
		}
		
		return specificGames;
	}

	public Ticket getTigerTicketFromTickets( ArrayList< Ticket > allTickets ){
		if( !allTickets.isEmpty() ){
			
			for( Iterator<Ticket> i = allTickets.iterator(); i.hasNext(); ){
				Ticket ticket = ( Ticket ) i.next();
				if( ticket.getTicketType().contains("Tiger") ){ 
				
					return ticket;
				}		
			}
		}
		return null;
		
	}
	
	public Ticket getLionTicketFromTickets( ArrayList< Ticket > allTickets ){
		if( !allTickets.isEmpty() ){
			
			for( Iterator<Ticket> i = allTickets.iterator(); i.hasNext(); ){
				Ticket ticket = ( Ticket ) i.next();
				if( ticket.getTicketType().contains("Lion") ){ 
					return ticket;
				}		
			}
		}
		return null;
		
	}

	
}
