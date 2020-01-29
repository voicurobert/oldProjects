package rvo.mobilegateway.datastore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import rvo.mobilegateway.R;
import rvo.mobilegateway.PniDataset;
import rvo.mobilegateway.generic.UndergroundRoute;

/**
 * Created by Robert on 8/4/2015.
 */
public class DatastoreHelper extends SQLiteOpenHelper {

    private static final String LOG = DatastoreHelper.class.getName();
    private static final String DATABASE_NAME = "smallworld_datastore";
    private static final int DATABASE_VERSION = 1;
    private static final String UUB_TABLE = "uub";
    private static final String POLE_TABLE = "pole";
    private static final String HUB_TABLE = "mit_hub";
    private static final String UNDERGROUND_ROUTE_TABLE = "underground_route";
    private static final String AERIAL_ROUTE_TABLE = "aerial_route";

    private static final String CREATE_UUB_TABLE = "CREATE TABLE " + UUB_TABLE + "("
            + "id" + " INTEGER PRIMARY KEY, "
            + "latitude" + " TEXT, "
            + "longitude" + " TEXT, "
            + "constructionStatus" + " TEXT, "
            + "type" + " TEXT " + ")";

    private static final String CREATE_POLE_TABLE = "CREATE TABLE " + POLE_TABLE + "("
            + "id" + " INTEGER PRIMARY KEY, "
            + "latitude" + " TEXT, "
            + "longitude" + " TEXT, "
            + "constructionStatus" + " TEXT, "
            + "usage" + " TEXT, "
            + "materialType" + " TEXT ) ";

    private static final String CREATE_UNDERGROUND_ROUTE_TABLE = "CREATE TABLE " + UNDERGROUND_ROUTE_TABLE + "("
            + "id" + " INTEGER PRIMARY KEY, "
            + "constructionStatus" + " TEXT, "
            + "points" + " BLOB " + ")";

    private static final String CREATE_AERIAL_ROUTE_TABLE = "CREATE TABLE " + AERIAL_ROUTE_TABLE + "("
            + "id" + " INTEGER PRIMARY KEY, "
            + "constructionStatus" + " TEXT, "
            + "points" + " BLOB " + ")";

    private static final String CREATE_HUB_TABLE = "CREATE TABLE " + HUB_TABLE + "("
            + "id" + " INTEGER PRIMARY KEY, "
            + "latitude" + " TEXT, "
            + "longitude" + " TEXT, "
            + "constructionStatus" + " TEXT, "
            + "name " + " TEXT,"
            + "type " + "TEXT )";


    public DatastoreHelper( Context context ){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( CREATE_UUB_TABLE );
        db.execSQL( CREATE_UNDERGROUND_ROUTE_TABLE );
        db.execSQL( CREATE_HUB_TABLE );
        db.execSQL(CREATE_AERIAL_ROUTE_TABLE);
        db.execSQL(CREATE_POLE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertUndergroundUtilityBox( int id, String lat, String longitude, String constructionStatus, String type ){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id", id );
        values.put("latitude", lat);
        values.put("longitude", longitude);
        values.put("constructionStatus", constructionStatus);
        values.put("type", type);
        long rowId = db.insert(UUB_TABLE, null, values );
       // Log.d(LOG, "inserted uub id: "+rowId);
    }

    public void insertPole( int id, String lat, String longitude, String constructionStatus, String usage, String materialType ){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id", id );
        values.put("latitude", lat);
        values.put("longitude", longitude);
        values.put("constructionStatus", constructionStatus);
        values.put("usage", usage);
        values.put("materialType", materialType);
        long rowId = db.insert(POLE_TABLE, null, values );
        // Log.d(LOG, "inserted uub id: "+rowId);
    }

    public void insertUndergroundRoute( int id, String constructionStatus, StringBuilder points ){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id", id );
        values.put("points", points.toString());
        values.put( "constructionStatus", constructionStatus );
        long rowId = db.insert(UNDERGROUND_ROUTE_TABLE, null, values );
        //Log.d(LOG, "Inserted ur id: "+rowId);
    }

    public void insertAerialRoute( int id, String constructionStatus, String points ){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id", id );
        values.put("points", points);
        values.put( "constructionStatus", constructionStatus );
        long rowId = db.insert( AERIAL_ROUTE_TABLE, null, values );
    }

    public void insertHub( int id, String latitude, String longitude, String constructionStatus, String type, String name ){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id", id );
        values.put("latitude", latitude);
        values.put("longitude", longitude);
        values.put("constructionStatus", constructionStatus);
        values.put("name", name);
        values.put("type", type);
        long rowId = db.insert( HUB_TABLE, null, values );
        // Log.d(LOG, "inserted uub id: "+rowId);
    }

    public int getSizeForTableName( String collectionName ){
        String query = "SELECT * FROM "+ collectionName;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        int size = c.getCount();
        c.close();
        return size;
    }


    public boolean recordExists( String tableName, int id ){
        String query = "SELECT * FROM "+ tableName + " WHERE id = " + String.valueOf( id );
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if( c.getCount() <= 0 ){
            c.close();
            return false;
        }
        c.close();
        return true;
    }


    public void initUubsForBounds( LatLngBounds screenBounds ){
        String query = "SELECT * FROM " + UUB_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if( cursor.moveToFirst()  ){
            do{
                String latitude = this.getColumnString( cursor, "latitude");
                String longitude = this.getColumnString( cursor, "longitude");
                if( !latitude.isEmpty() || !longitude.isEmpty() ){
                    LatLng location = new LatLng( Float.valueOf( latitude ), Float.valueOf( longitude));
                    if( screenBounds.contains( location )){
                        // create a UUB record and insert it to PniDataset
                        int id = Integer.valueOf(this.getColumnString(cursor, "id"));
                        String constructionStatus = this.getColumnString(cursor, "constructionStatus");
                        String type = this.getColumnString(cursor, "type");
                        MarkerOptions mo = new MarkerOptions().position( location )
                                .title("Underground Utility Box")
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.uub));
                      //  UndergroundUtilityBox uub = new UndergroundUtilityBox( id, location, constructionStatus, type, mo );
                       // PniDataset.instance.getUubs().add( uub );
                    }
                }
            }while( cursor.moveToNext() );
        }
        cursor.close();
    }

    public void initUndergroundRoutesForBounds( LatLngBounds screenBounds ){
        String query = "SELECT * FROM " + UNDERGROUND_ROUTE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if( cursor.moveToFirst()  ){
            do{
                String points = this.getColumnString( cursor, "points" );
                if( !points.isEmpty() ){
                    String[] vec =  points.split( Pattern.quote(";") );
                    List coords = new ArrayList();
                    for( String pair : vec ){
                        Log.d("tag ", "############################################### "+ pair);
                        String latitude = pair.split( Pattern.quote(",") )[0];
                        String longitude = pair.split( Pattern.quote(",") )[1];
                        LatLng location = new LatLng( Float.valueOf( latitude ), Float.valueOf( longitude));
                        if( screenBounds.contains( location )){
                            coords.add( location );
                        }
                    }
                    int id = Integer.valueOf(this.getColumnString(cursor, "id") );
                    String constructionStatus = this.getColumnString(cursor, "constructionStatus");

                    UndergroundRoute ur = new UndergroundRoute();
                    ur.setId( id );
                    ur.setConstructionStatus(constructionStatus);
                    ur.setPoints( coords );
                    PniDataset.instance.getUndergroundRoutes().add( ur );

                }
            }while( cursor.moveToNext() );
        }
        cursor.close();
    }

    private String getColumnString( Cursor cursor, String columnName ){
        return cursor.getString( cursor.getColumnIndex( columnName ) );
    }
}
