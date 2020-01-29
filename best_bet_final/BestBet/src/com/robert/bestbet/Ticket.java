package com.robert.bestbet;

import java.util.ArrayList;
import java.util.Date;

public class Ticket {

	private String ticketType;
	private float ticketStake;
	private String ticketDate;
	private int nrOfGames;
	private float ticketOdd;
	private float ticketPossibleWin;
	private ArrayList< Game > games;
	private int ticketId;
	private String ticketResult;
	
	public Ticket(){
		
	}
	
	// SETTERS
	public void setTicketId( int id ){
		this.ticketId = id;
	}
	
	
	public void setTicketType( String ticketType ){
		this.ticketType = ticketType;
	}
	
	public void setTicketStake( float ticketStake ){
		this.ticketStake = ticketStake;
	}
	
	public void setTicketDate( String ticketDate ){
		this.ticketDate = ticketDate;
	}
	
	public void setNrOfGames( int nrOfGames ){
		this.nrOfGames = nrOfGames;
	}
	
	public void setTicketOdd( float ticketOdd ){
		this.ticketOdd = ticketOdd;
	}
	
	public void setTicketPossibleWin( float possibleWin ){
		this.ticketPossibleWin = possibleWin;
	}
	
	public void setGames( ArrayList< Game > games ){
		this.games = games;
	}
	
	public void setTicketResult( String ticketResult ){
		this.ticketResult = ticketResult;
	}
	
	// GETTERS
	public int getTicketId(){
		return ticketId;
	}
	
	
	public String getTicketType(){
		return ticketType;
	}
	
	public float getTicketStake(){
		return ticketStake;
	}
	
	public String getTicketDate(){
		return ticketDate;
	}
	
	public int getNrOfGames(){
		return nrOfGames;
	}
	
	public float getTicketOdd(){
		return ticketOdd;
	}
	
	public float getTicketPossibleWin(){
		return ticketPossibleWin;
	}
	
	public ArrayList< Game > getGames(){
		return games;
	}
	
	public String getTicketResult(){
		return ticketResult;
	}
}
