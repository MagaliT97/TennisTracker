package com.example.magal.tennistracker;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class StatsViewActivity extends AppCompatActivity {

    DatabaseHelper mydb;
    TextView match_J1, match_J2;
    long match_id;
    private String namep1, namep2;
    TextView scoreJ1, scoreJ2, acesJ1, acesJ2, fautesJ1, fautesj2, numero;

    private int nbAcesp1, nbAcesp2, nbFautesp1, nbFautesp2, score1, score2;

    ArrayList<String> infosMatch;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_view);
        mydb = new DatabaseHelper(this);
        infosMatch = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        match_J1 = findViewById(R.id.match5_J1);
        match_J2 = findViewById(R.id.match5_J2);

        scoreJ1 = findViewById(R.id.scoreJ1);
        scoreJ2 = findViewById(R.id.scoreJ2);
        acesJ1 = findViewById(R.id.acesJ1);
        acesJ2 = findViewById(R.id.acesJ2);
        fautesJ1 = findViewById(R.id.fauteJ1);
        fautesj2 = findViewById(R.id.fauteJ2);
        numero = findViewById(R.id.numero);

        Intent intent = getIntent();
        match_id = intent.getLongExtra("match",0);

        nbAcesp1=0;
        nbAcesp2=0;
        nbFautesp1=0;
        nbFautesp2=0;
        score1=0;
        score2=0;

        //afficherData();
        displayPlayerStats();
    }

    public void displayPlayerStats(){
        long player1_id;
        long player2_id;
        String[] playerNames = mydb.getPlayerNamesID(match_id);

        // get IDs
        player1_id = Long.parseLong(playerNames[1]);
        player2_id = Long.parseLong(playerNames[3]);

        //get id match
        //infosMatch.add(match_id+"");
        // display names
        match_J1.setText(playerNames[0]);
        namep1 = playerNames[0];
        //infosMatch.add(namep1);
        match_J2.setText(playerNames[2]);
        namep2 = playerNames[2];
        //infosMatch.add(namep2);

        int[] player1Stats = mydb.getPlayerStats(match_id, player1_id);
        int[] player2Stats = mydb.getPlayerStats(match_id, player2_id);

        scoreJ1.setText(String.valueOf(player1Stats[0]));
        score1=player1Stats[0];
        //infosMatch.add(score1+"");
        scoreJ2.setText(String.valueOf(player2Stats[0]));
        score2=player2Stats[0];
        //infosMatch.add(score2+"");

        acesJ1.setText(String.valueOf(player1Stats[1]));
        nbAcesp1 = player1Stats[1];
        //infosMatch.add(nbAcesp1+"");
        acesJ2.setText(String.valueOf(player2Stats[1]));
        nbAcesp2 = player2Stats[1];
        //infosMatch.add(nbAcesp2+"");

        fautesJ1.setText(String.valueOf(player1Stats[2]));
        nbFautesp1=player1Stats[2];
        //infosMatch.add(nbFautesp1+"");
        fautesj2.setText(String.valueOf(player2Stats[2]));
        nbFautesp2=player2Stats[2];
        //infosMatch.add(nbFautesp2+"");

    }

}
