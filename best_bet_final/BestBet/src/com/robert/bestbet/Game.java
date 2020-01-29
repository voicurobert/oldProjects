package com.robert.bestbet;

public class Game {

	private String teamA;
	private String teamB;
	private float gameOdd;
	private String expectedResult;
	private int ticketIDFK;
	public Game(){
		
	}
	
	// SETTERS
	public void setTicketIDFK( int ticketId){
		this.ticketIDFK = ticketId;
	}
	
	
	public void setTeamA( String teamA ){
		this.teamA = teamA;
	}
	
	public void setTeamB( String teamB ){
		this.teamB = teamB;
	}
	
	public void setGameOdd( float gameOdd){
		this.gameOdd = gameOdd;
	}
	
	public void setExpectedResult( String expectedResult ){
		this.expectedResult = expectedResult;
	}
	
	
	// GETTERS
	public int getTicketIDFK(){
		return ticketIDFK;
	}
	
	
	public String getTeamA(){
		return teamA;
	}
	
	public String getTeamB(){
		return teamB;
	}
	
	public float getGameOdd(){
		return gameOdd;
	}
	
	public String getExpectedResult(){
		return expectedResult;
	}
}
