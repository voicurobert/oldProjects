package com.robert.bestbet;

public class Globals {

	protected static final String TAG = "GCM DEMO";
    protected static final String GCM_SENDER_ID = "297668632966";
    protected static final String SERVER_URL = "http://10.0.2.2/best_bet_v1_06.18.2014/register_device_id.php";
   // protected static final String SERVER_URL = "http://127.0.0.1:80//best_bet_v1_06.18.2014/register_device_id.php";
    protected static final String PREFS_NAME = "BEST_BET_PREFS";
    protected static final String PREFS_PROPERTY_REG_ID = "registration_id";
    protected static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    protected static final String PROPERTY_APP_VERSION = "appVersion";
    protected static final String EXTRA_MESSAGE = "message";
    protected static final long GCM_TIME_TO_LIVE = 60L * 60L * 24L * 7L * 4L; // 4 Weeks
    protected static final String TICKETS_TABLE_NAME = "tickets";
    protected static final String GAMES_TABLE_NAME = "games";
   // protected static final String TODAYS_TICKET_URL = "http://10.0.2.2/best_bet_v1_06.18.2014/todays_ticket.php"; 
    protected static final String TODAYS_TICKET_URL = "http://192.168.1.132/best_bet_v1_06.18.2014/todays_ticket.php";
    //protected static final String TODAYS_GAMES_URL = "http://10.0.2.2/best_bet_v1_06.18.2014/todays_games.php"; 
    protected static final String TODAYS_GAMES_URL = "http://192.168.1.132/best_bet_v1_06.18.2014/todays_games.php";
    protected static final String ALL_DATA_URL = "http://192.168.1.132/best_bet_v1_06.18.2014/all_db_data.php";
    protected static final String ALL_TICKETS = "http://192.168.1.132/best_bet_v1_06.18.2014/all_tickets.php";
    protected static final String ALL_GAMES = "http://192.168.1.132/best_bet_v1_06.18.2014/all_games.php";
    protected static final int TYPE_WIFI = 1;
    protected static final int TYPE_MOBILE = 2;
    protected static final int TYPE_NOT_CONNECTED = 0;
}
