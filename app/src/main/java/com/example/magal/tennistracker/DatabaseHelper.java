package com.example.magal.tennistracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TAG = "DatabasHelper";


    //Name of the databse
    public static final String DATABASE_NAME = "TennisTracker.db";


    //Name of tables
    public static final String TABLE_MATCHES = "table_matches";
    public static final String TABLE_JOUEURS = "table_joueurs";
    public static final String TABLE_SETS = "table_sets";
    public static final String TABLE_ACES = "table_aces";
    public static final String TABLE_FAUTES = "table_fautes";
    public static final String TABLE_PHOTOS = "table_photos";

    public static final String COL_ID = "id_";

    //Name of columns of TABLE_MATCHES
    public static final String COL_ID_MATCHES = "id_match";
    public static final String COL_ID_J1 = "id_J1";
    public static final String COL_ID_J2 = "id_J2";
    public static final String COL_ID_WINNER = "id_winner";
    public static final String COL_NAME_WINNER = "name_winner";
    public static final String COL_LAT = "latitude";
    public static final String COL_LNG = "longitude";
    public static final String COL_ADD = "addresse";

    //Name of columns of JOUEURS
    //public static final String COL_ID_JOUEUR = "id_joueur";
    public static final String COL_NAME_JOUEUR = "name_joueur";

    //Name of columns of SETS
    public static final String COL_ID_SETS = "id_set";
    //public static final String COL_ID_MATCHES = "id_match";
    //public static final String COL_ID_J1 = "id_J1";
    //public static final String COL_ID_J2 = "id_J2";
    public static final String COL_SET_WINNER = "id_set_winner";
    public static final String COL_SCORE_J1 = "id_score_J1";
    public static final String COL_SCORE_J2 = "id_score_J2";
    public static final String COL_NB_SETS = "nb_sets";

    //Name of columns of PHOTOS
    public static final String COL_ID_PHOTOS = "id_photo";
    //public static final String COL_ID_MATCHES = "id_match";
    public static final String COL_CHEMIN = "chemin_photo";

    //Name of columns of ACES
    public static final String COL_ID_JOUEUR = "id_joueur";
    public static final String COL_NB_ACES = "nb_aces";

    //Name of columns of FAUTES
    public static final String COL_NB_FAUTES = "nb_aces";

    //Creation of Table
    //private static final String TABLE_CREATE =
            //"CREATE TABLE " + TABLE_JOUEURS + " (" + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "name TEXT NOT NULL);";


    public DatabaseHelper( Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_JOUEURS + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NAME_JOUEUR + " TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_MATCHES + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_ID_J1 + " INTEGER, "
                + COL_ID_J2 + " INTEGER, "
                + COL_ID_WINNER + " INTEGER)");


        db.execSQL("CREATE TABLE " + TABLE_SETS + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_ID_JOUEUR + " INTEGER, "
                + COL_NB_SETS + " INTEGER, "
                + COL_ID_MATCHES + " INTEGER)");

        db.execSQL("CREATE TABLE " + TABLE_ACES + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_ID_JOUEUR + " INTEGER, "
                + COL_NB_ACES + " INTEGER, "
                + COL_ID_MATCHES + " INTEGER)");

        db.execSQL("CREATE TABLE " + TABLE_FAUTES + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_ID_JOUEUR + " INTEGER, "
                + COL_NB_FAUTES + " INTEGER, "
                + COL_ID_MATCHES + " INTEGER)");

        db.execSQL("CREATE TABLE " + TABLE_PHOTOS + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_ID_MATCHES + " INTEGER, "
                + COL_CHEMIN + " TEXT) ");

}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_JOUEURS);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_MATCHES);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_ACES);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_FAUTES);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_SETS);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_PHOTOS);


        onCreate(db);

    }

    /*public boolean insertData(int id_J1, int id_J2, int id_match_winner, double latitude, double longitude, String address){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_ID_J1, id_J1);
        contentValues.put(COL_ID_J2, id_J2);
        contentValues.put(COL_ID_MATCH_WINNER, id_match_winner);

        contentValues.put(COL_LAT, latitude);
        contentValues.put(COL_LNG, longitude);
        contentValues.put(COL_ADD, address);

        long result = db.insert(TABLE_MATCHES, null, contentValues);

        if(result == -1) return false;

        else return true;
    }*/

    //ADD DATA

    public boolean addJoueursData(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_NAME_JOUEUR, name);

        Log.d(TAG, "addData: Adding " + name + " to " + TABLE_JOUEURS);


        long result = db.insert(TABLE_JOUEURS, null, contentValues);

        if(result == -1) return false;

        else return true;

    }

    public long createJoueur(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_NAME_JOUEUR, name);

        Log.d(TAG, "addData: Adding " + name + " to " + TABLE_JOUEURS);


        long result = db.insert(TABLE_JOUEURS, null, contentValues);

        //if(result == -1) return false;

        //else return true;
        return result;

    }

    public long createMatch(Matches match){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_ID_J1, match.getPlayer1());
        contentValues.put(COL_ID_J2, match.getPlayer2());
        contentValues.put(COL_ID_WINNER, match.getWinner_id());

        //contentValues.put(COL_NAME_WINNER, name);

        long result = db.insert(TABLE_MATCHES, null, contentValues);


        //if(result == -1) return false;
        //else return true;
        return result;

    }

    public long createSet(long match, long player, int ballnumber){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_ID_JOUEUR, player);
        values.put(COL_ID_MATCHES, match);
        values.put(COL_NB_SETS, ballnumber);
        //values.put(COL_ID_J1, set.getPlayer1());
        //values.put(COL_ID_J2, set.getPlayer2());
        //values.put(COL_SCORE_J1, set.getScore1());
        //values.put(COL_SCORE_J2, set.getScore2());

        // insert row
        long set_id = db.insert(TABLE_SETS, null, values);

        return set_id;
    }

    public long createAces(long match, long player, int ballnumber){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_ID_JOUEUR, player);
        values.put(COL_ID_MATCHES, match);
        values.put(COL_NB_ACES, ballnumber);
        // insert row
        long ace_id = db.insert(TABLE_ACES, null, values);


        return ace_id;
    }

    public void updateAces(long match, long player, int ballnumber){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_ID_JOUEUR, player);
        values.put(COL_ID_MATCHES, match);
        values.put(COL_NB_ACES, ballnumber);

        // updating row
        db.update(TABLE_ACES, values, COL_ID_MATCHES + "=? AND "+ COL_ID_JOUEUR + "=?",
                new String[]{ String.valueOf(match), String.valueOf(player)});

    }

    public long createFautes(long match, long player, int ballnumber){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_ID_JOUEUR, player);
        values.put(COL_ID_MATCHES, match);
        values.put(COL_NB_FAUTES, ballnumber);
        // insert row
        long faute_id = db.insert(TABLE_FAUTES, null, values);


        return faute_id;
    }

    public long createPicture(long match, String path){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_ID_MATCHES, match);
        values.put(COL_CHEMIN, path);
        // insert row
        long pic_id = db.insert(TABLE_PHOTOS, null, values);


        return pic_id;
    }

    public List<String> getPics(){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COL_CHEMIN};

        //Cursor result = db.query(TABLE_PHOTOS, columns, COL_ID_MATCHES+"=?",
                //new String[]{String.valueOf(match_id)}, null, null, null);
        //Cursor result = db.rawQuery("SELECT  * FROM " + TABLE_PICTURES + " WHERE "
        // + COLUMN_MATCH + " =?", new String[]{match_id});

        Cursor result = db.rawQuery("SELECT * FROM "+ TABLE_PHOTOS,null);

        List<String> pics = new ArrayList<String>();

        result.moveToFirst();
        // while there are results
        while (!result.isAfterLast()) {
            // add image path to list
            pics.add(result.getString(0));
            result.moveToNext();
        }
        result.close();


        return pics;
    }



    //UPDATE DATA
    /*public void updateSet(Sets set){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_SCORE_J1, set.getScore1());
        values.put(COL_SCORE_J2, set.getScore2());
        //values.put(COL_SET_WINNER, set.getWinner());

        // updating row
        db.update(TABLE_SETS, values, COL_ID + " = ?",
                new String[]{ String.valueOf(set.getId())});
    }*/

    public void updateMatch(Matches match){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_ID_WINNER, match.getWinner_id());

        // updating row
        db.update(TABLE_MATCHES, values, COL_ID + " = ?",
                new String[]{ String.valueOf(match.getId())});

    }


    //GET DATA

    public Cursor getJoueursData(){

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_JOUEURS;
        Cursor data = db.rawQuery(query,null);
        return data;
    }


    public String[] getPlayerNamesID(long match_id){
        SQLiteDatabase db = this.getReadableDatabase();
        String playerNamesID[] = new String[4];

        long id1, id2;

        // get players ID
        String[] columns = {COL_ID_J1, COL_ID_J2};

        Cursor result = db.query(TABLE_MATCHES, columns, COL_ID+"=?",
                new String[]{String.valueOf(match_id)}, null, null, null);

        result.moveToFirst();
        // while there are results
        if(result!=null && result.getCount()>0){
            id1 = result.getLong(0);
            id2 = result.getLong(1);
            playerNamesID[1]=String.valueOf(id1);
            playerNamesID[3]=String.valueOf(id2);

            // get player names

            String[] columnName = {COL_NAME_JOUEUR};
            Cursor name1 = db.query(TABLE_JOUEURS, columnName, COL_ID+"=?",
                    new String[]{String.valueOf(id1)}, null, null, null);
            Cursor name2 = db.query(TABLE_JOUEURS, columnName, COL_ID+"=?",
                    new String[]{String.valueOf(id2)}, null, null, null);

            name1.moveToFirst();
            name2.moveToFirst();

            if(name1!=null && name1.getCount()>0){
                playerNamesID[0]=name1.getString(0);
            }
            if(name2!=null && name2.getCount()>0){
                playerNamesID[2]=name2.getString(0);
            }
            name1.close();
            name2.close();
        }
        result.close();

        return playerNamesID;
    }

    public int getStat(long match_id, long player_id, String statTable){
        int stat;
        SQLiteDatabase db = this.getReadableDatabase();

        // get stat instances
        Cursor result = db.query(statTable, null, COL_ID_MATCHES+"=? AND "+COL_ID_JOUEUR+"=?",
                new String[]{String.valueOf(match_id), String.valueOf(player_id)}, null, null, null);

        result.moveToFirst();
        if(result!=null && result.getCount()>0){
            stat = result.getCount();
        }else{
            stat = 0;
        }
        return stat;
    }

    public int[] getPlayerStats(long match_id, long player_id){
        SQLiteDatabase db = this.getReadableDatabase();
        int playerStats[] = new int[3];

        String[] Stats = {TABLE_SETS, TABLE_ACES, TABLE_FAUTES};

        //playerStats[0]=getTotalScore(match_id, player_id);
        //playerStats[1]=getSetsWon(match_id, player_id);
        int i = 0;
        for (String statTable : Stats) {
            playerStats[i]=getStat(match_id, player_id, statTable);
            i++;
        }

        return playerStats;
    }

    public int getWinner(long winner_id){
        int stat;
        SQLiteDatabase db = this.getReadableDatabase();

        // get stat instances
        Cursor result = db.query(TABLE_MATCHES, null, COL_ID_WINNER+"=?",
                new String[]{String.valueOf(winner_id)}, null, null, null);

        result.moveToFirst();
        if(result!=null && result.getCount()>0){
            stat = result.getCount();
        }else{
            stat = 0;
        }
        return stat;
    }

    public String getPlayerName(long joueur_id){
        SQLiteDatabase db = this.getReadableDatabase();
        String playerName = new String();

        // get players ID
        Cursor result = db.query(TABLE_JOUEURS, null, COL_ID+"=?",
                new String[]{String.valueOf(joueur_id)}, null, null, null);

        result.moveToFirst();
        // while there are results
        if(result!=null && result.getCount()>0){
            playerName=String.valueOf(result);
        }
        result.close();

        return playerName;
    }

    /*public int getTotalScore(long match_id, long player_id){
        int totalScore=0;
        SQLiteDatabase db = this.getReadableDatabase();

        // player as player1

        String[] col1 = {COL_SCORE_J1};
        Cursor result1 = db.query(TABLE_SETS,
                null,
                COL_ID_MATCHES +"=? AND "+COL_ID_J1+"=?",
                new String[]{String.valueOf(match_id), String.valueOf(player_id)}, null, null, null);

        if(result1.moveToFirst()){
            // while there are results
            while (!result1.isAfterLast()) {
                totalScore= totalScore + (result1.getInt(3));
                result1.moveToNext();
            }
            result1.close();
        } else{

        }


        //player as player2

        String[] col2 = {COL_SCORE_J2};
        Cursor result2 = db.query(TABLE_SETS,
                null,
                COL_ID_MATCHES+"=? AND "+COL_ID_J2+"=?",
                new String[]{String.valueOf(match_id), String.valueOf(player_id)}, null, null, null);
        result2.moveToFirst();
        // while there are results
        while (!result2.isAfterLast()) {
            totalScore = totalScore+(result2.getInt(4));
            result2.moveToNext();
        }
        result2.close();

        return totalScore;
    }*/

    /*public void removeLastSet(Sets set){
        String[] whereArgs = {String.valueOf(set.getId())};
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SETS, COL_ID+"=?", whereArgs);

    }*/

    /*public String getNbSets(long match_id){
        SQLiteDatabase db = this.getReadableDatabase();
        String nbSets;
        Cursor result = db.query(TABLE_SETS, null, COL_ID_MATCHES+"=?",
                new String[]{String.valueOf(match_id)}, null, null, null);
        nbSets = String.valueOf(result.getCount());

        return nbSets;
    }*/

    /*public List<String[]> getMatchListInfo(){

        List<String[]> matchInfo = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        long pid1, pid2;

        String[] columns = {COL_ID_J1, COL_ID_J2, COL_ID};

        String[] col = {COL_NAME_JOUEUR};
        Cursor result = db.query(TABLE_MATCHES, columns, null, null, null, null, null);

        result.moveToFirst();
        // while there are results
        while (!result.isAfterLast()) {
            String[] instance = new String[3];
            //instance[0]=result.getString(0);
            long match_id = result.getLong(2);

            Log.d("MATCH ID", String.valueOf(match_id));

            instance[2]=String.valueOf(match_id);

            pid1 = result.getLong(0);
            pid2 = result.getLong(1);

            Cursor res = db.query(TABLE_JOUEURS, col, COL_ID + "=?",
                    new String[]{String.valueOf(pid1)}, null, null, null);

            res.moveToFirst();
            if(res!=null && res.getCount()>0){
                instance[0] = res.getString(0);
            }else{
                instance[0] = "??";
            }
            res.close();

            Cursor res2 = db.query(TABLE_JOUEURS, col, COL_ID + "=?",
                    new String[]{String.valueOf(pid2)}, null, null, null);

            res2.moveToFirst();
            if(res2!=null && res2.getCount()>0){
                instance[1] = res2.getString(0);
            }else{
                instance[1] = "??";
            }
            res2.close();

            matchInfo.add(instance);

            result.moveToNext();
        }

        result.close();


        return matchInfo;
    }*/









}
