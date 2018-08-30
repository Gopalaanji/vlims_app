package dev.info.basic.viswaLab.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dev.info.basic.viswaLab.models.ScheduleAlertModel;
import dev.info.basic.viswaLab.models.ShipdetailsModel;

/**
 * Created by Giri Thangellapally on 02-03-2018.
 */

public class helper extends SQLiteOpenHelper {

    private Context context;
    private static String DATABASE_NAME = "VLIMS_APP_DB";
    private static int DATABASE_VERSION = 2;

    //shcedule alerts Table
    public static String SCHEDULE_ALERTS_TABLE = "SCHEDULE_ALERTS_TABLE";
    private static String COL_ALERT_ID = "ID";
    private static String COL_SHIP_NAME = "SHIP_NAME";
    private static String COL_EQUIPMENT_NAME = "EQUIPMENT_NAME";
    private static String COL_SCHEDULE_DATE = "SCHEDULE_DATE";

    private static String CREATE_SCHEDULE_ALERTS_TABLE = "CREATE TABLE " + SCHEDULE_ALERTS_TABLE + "(" + COL_ALERT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_SHIP_NAME + " VARCHAR(255)," + COL_EQUIPMENT_NAME + " VARCHAR(255)," + COL_SCHEDULE_DATE + " VARCHAR(255));";
    private static String UPGRADE_SCHEDULE_ALERTS_TABLE = "DROP TABLE IF EXISTS" + CREATE_SCHEDULE_ALERTS_TABLE;


    //Ships Table
    public static String SHIPS_TABLE = "SHIPS_TABLE";
    private static String COL_SHIP_ID = "ID";
    private static String COL_SHIP_ID_ID = "SHIP_ID";
    private static String COL_SHIP_NAME_NAME = "SHIP_NAME_NAME";
    private static String CREATE_SHIPS_TABLE = "CREATE TABLE " + SHIPS_TABLE + "(" + COL_SHIP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_SHIP_ID_ID + " VARCHAR(255)," + COL_SHIP_NAME_NAME + " VARCHAR(255));";
    private static String UPGRADE_SHIPS_TABLE = "DROP TABLE IF EXISTS" + CREATE_SHIPS_TABLE;


    private static helper sInstance;

    public helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        // TODO Auto-generated constructor stub
    }

    public static helper getInstance(Context context) {

        if (sInstance == null) {
            sInstance = new helper(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(CREATE_SCHEDULE_ALERTS_TABLE);
        db.execSQL(CREATE_SHIPS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        try {
            db.execSQL(UPGRADE_SCHEDULE_ALERTS_TABLE);
            db.execSQL(UPGRADE_SHIPS_TABLE);
            onCreate(db);
        } catch (SQLException e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public long AddProjectsData(ScheduleAlertModel scheduleAlertModel) {
        // TODO Auto-generated method stub
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_SHIP_NAME, scheduleAlertModel.getShipName().toString());
        values.put(COL_EQUIPMENT_NAME, scheduleAlertModel.getEquipment().toString());
        values.put(COL_SCHEDULE_DATE, ConvertJsonDate(scheduleAlertModel.getSheduleDate().toString()));
        long id = db.insert(SCHEDULE_ALERTS_TABLE, null, values);
        db.close();
        return id;
    }

    public static String ConvertJsonDate(String jsondate) {

        try {
            jsondate = jsondate.replace("/Date(", "").replace(")/", "");
            long time = Long.parseLong(jsondate);
            Date d = new Date(time);
            return new SimpleDateFormat("dd/MM/yyyy").format(d).toString();
        } catch (Exception e) {

        }
        return jsondate;
    }


    public List<ScheduleAlertModel> getAllScheduleAlerts() {
        List<ScheduleAlertModel> projectDtoList = new ArrayList<ScheduleAlertModel>();
        String Selectall = "SELECT  * FROM " + SCHEDULE_ALERTS_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(Selectall, null);
        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                do {
                    ScheduleAlertModel alert = new ScheduleAlertModel();
                    alert.setShipName(cursor.getString(cursor.getColumnIndex(COL_SHIP_NAME)));
                    alert.setEquipment(cursor.getString(cursor.getColumnIndex(COL_EQUIPMENT_NAME)));
                    alert.setSheduleDate(cursor.getString(cursor.getColumnIndex(COL_SCHEDULE_DATE)));
                    projectDtoList.add(alert);
                } while (cursor.moveToNext());
            }
        }
        return projectDtoList;
    }

    public void deleteAllAlerts() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        try {
            sqLiteDatabase.execSQL("DELETE FROM " + helper.SCHEDULE_ALERTS_TABLE);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    //SHIPS DETAILS

    public long AddShipsData(ShipdetailsModel shipdetailsModel) {
        // TODO Auto-generated method stub
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_SHIP_ID_ID, shipdetailsModel.getShipId());
        values.put(COL_SHIP_NAME_NAME, shipdetailsModel.getShipName().toString());
        long id = db.insert(SHIPS_TABLE, null, values);
        db.close();
        return id;
    }

    public List<ShipdetailsModel> getAllShipDetails() {
        List<ShipdetailsModel> shipdetailsModelList = new ArrayList<ShipdetailsModel>();
        String selectShips = "SELECT  * FROM " + SHIPS_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectShips, null);
        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                do {
                    ShipdetailsModel ship = new ShipdetailsModel();
                    ship.setShipId(cursor.getInt(cursor.getColumnIndex(COL_SHIP_ID_ID)));
                    ship.setShipName(cursor.getString(cursor.getColumnIndex(COL_SHIP_NAME_NAME)));
                    shipdetailsModelList.add(ship);
                } while (cursor.moveToNext());
            }
        }
        return shipdetailsModelList;
    }
    public void deleteAllShips() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        try {
            sqLiteDatabase.execSQL("DELETE FROM " + helper.SHIPS_TABLE);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

}
