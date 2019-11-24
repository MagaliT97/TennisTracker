package com.example.magal.tennistracker;

import android.content.Intent;
import android.database.Cursor;
import android.service.autofill.FieldClassification;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity {

    DatabaseHelper mydb;
    TextView viewStats, match1_J1, match1_J2, match2_J1, match2_J2, match3_J1, match3_J2, match4_J1, match4_J2, match5_J1, match5_J2;
    long match_id;
    private String namep1, namep2;
    TextView test;
    TextView scoreJ1, scoreJ2, acesJ1, acesJ2, fautesJ1, fautesj2;
    //String nameWinner;

    private int nbAcesp1, nbAcesp2, nbFautesp1, nbFautesp2, score1, score2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        mydb = new DatabaseHelper(this);
        //viewStats = findViewById(R.id.viewStats);
        /*match1_J1 = findViewById(R.id.match1_J1);
        match1_J2 = findViewById(R.id.match1_J2);
        match2_J1 = findViewById(R.id.match2_J1);
        match2_J2 = findViewById(R.id.match2_J2);
        match3_J1 = findViewById(R.id.match3_J1);
        match3_J2 = findViewById(R.id.match3_J2);
        match4_J1 = findViewById(R.id.match4_J1);
        match4_J2 = findViewById(R.id.match4_J2);*/
        match5_J1 = findViewById(R.id.match5_J1);
        match5_J2 = findViewById(R.id.match5_J2);

        //test = findViewById(R.id.id_match);
        scoreJ1 = findViewById(R.id.scoreJ1);
        scoreJ2 = findViewById(R.id.scoreJ2);
        acesJ1 = findViewById(R.id.acesJ1);
        acesJ2 = findViewById(R.id.acesJ2);
        fautesJ1 = findViewById(R.id.fauteJ1);
        fautesj2 = findViewById(R.id.fauteJ2);
       // winnerName = findViewById(R.id.winnerName);



        Intent intent = getIntent();
        match_id = intent.getLongExtra("match",0);

        nbAcesp1=0;
        nbAcesp2=0;
        nbFautesp1=0;
        nbFautesp2=0;
        score1=0;
        score2=0;
        //nameWinner = "personne";

        //afficherData();
        displayPlayerStats();
    }

    /*private void afficherData() {

        Cursor res = mydb.getJoueursData();
        ArrayList<String> listdata = new ArrayList<>();

        if (res.getCount() == 0) {
            // show message
            showMessage("Error", "Nothing found");
            return;
        }

        while (res.moveToNext()) {
            //viewStats.setText(res.getString(1));
            listdata.add(res.getString(1));

        }

        match1_J1.setText(listdata.get(listdata.size()-10));
        match1_J2.setText(listdata.get(listdata.size()-9));

        match2_J1.setText(listdata.get(listdata.size()-8));
        match2_J2.setText(listdata.get(listdata.size()-7));

        match3_J1.setText(listdata.get(listdata.size()-6));
        match3_J2.setText(listdata.get(listdata.size()-5));

        match4_J1.setText(listdata.get(listdata.size()-4));
        match4_J2.setText(listdata.get(listdata.size()-3));

        match5_J1.setText(listdata.get(listdata.size()-2));
        match5_J2.setText(listdata.get(listdata.size()-1));

    }*/

    public void displayPlayerStats(){
        long player1_id;
        long player2_id;
        String[] playerNames = mydb.getPlayerNamesID(match_id);

        // get IDs
        player1_id = Long.parseLong(playerNames[1]);
        player2_id = Long.parseLong(playerNames[3]);

        // display names
        match5_J1.setText(playerNames[0]);
        namep1 = playerNames[0];
        match5_J2.setText(playerNames[2]);
        namep2 = playerNames[2];

        int[] player1Stats = mydb.getPlayerStats(match_id, player1_id);
        int[] player2Stats = mydb.getPlayerStats(match_id, player2_id);

        //String nameWinner = mydb.getPlayerName(mydb.getWinner(match_id));

        //int sco1 = mydb.getTotalScore(match_id, player1_id);
        //int sco2 = mydb.getTotalScore(match_id, player2_id);

        //scoreJ1.setText(String.valueOf(sco1));
        //scoreJ2.setText(String.valueOf(sco2));

        scoreJ1.setText(String.valueOf(player1Stats[0]));
        score1=player1Stats[0];
        scoreJ2.setText(String.valueOf(player2Stats[0]));
        score2=player2Stats[0];

        acesJ1.setText(String.valueOf(player1Stats[1]));
        nbAcesp1 = player1Stats[1];
        acesJ2.setText(String.valueOf(player2Stats[1]));
        nbAcesp2 = player2Stats[1];

        fautesJ1.setText(String.valueOf(player1Stats[2]));
        nbFautesp1=player1Stats[2];
        fautesj2.setText(String.valueOf(player2Stats[2]));
        nbFautesp2=player2Stats[2];

        //winnerName.setText(nameWinner);

    }


        public void showMessage(String title,String Message){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle(title);
            builder.setMessage(Message);
            builder.show();
        }

    private void toastMessage(String message){
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }




}
