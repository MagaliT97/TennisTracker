package com.example.magal.tennistracker;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MatchActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public static final String ARG_J1_NAME = "PLAYER_1_NAME";
    public static final String ARG_J2_NAME = "PLAYER_2_NAME";
    public static final String ARG_SERVE_FIRST = "SERVE_START";

    public static final String ARG_J1_ID = "J1_ID";
    public static final String ARG_J2_ID = "J2_ID";

    private static final int ADVANTAGE = 41;

    /* Variables to track in-game, game and set scores */
    int scorePlayer1 = 0;
    int scorePlayer2 = 0;

    int gamesPlayer1 = 0;
    int gamesPlayer2 = 0;

    int setsPlayer1 = 0;
    int setsPlayer2 = 0;

    /* Variable */
    int currServe;
    int counterAceJ1 = 0;
    int counterAceJ2 = 0;
    int counterFaultJ1 = 0;
    int counterFaultJ2 = 0;

    /* Variable to keep track of the name of the players */
    String player1Name;
    String player2Name;

    /* Initializing some TextViews as globals to limit use of findViewbyId */
    TextView scoreView1;
    TextView scoreView2;

    TextView gameView1;
    TextView gameView2;

    TextView setView1;
    TextView setView2;

    TextView aceView1;
    TextView aceView2;

    TextView faultView1;
    TextView faultView2;

    TextView serveView;
    LinearLayout LinearLayoutMatch;

    Matches match;
    Joueurs joueur1, joueur2;
    Sets currentSet;
    DatabaseHelper mydb;
    long match_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /* If there are no arguments, go to main menu */
        Bundle args = getIntent().getExtras();
        if (args == null)
            startActivity(new Intent(MatchActivity.this, NewMatchActivity.class));


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Afficher score

        scoreView1 = (TextView) findViewById(R.id.pointsJ1);
        scoreView2 = (TextView) findViewById(R.id.pointsJ2);

        gameView1 = (TextView) findViewById(R.id.jeuxJ1);
        gameView2 = (TextView) findViewById(R.id.jeuxJ2);

        setView1 = (TextView) findViewById(R.id.setsJ1);
        setView2 = (TextView) findViewById(R.id.setsJ2);

        aceView1 = (TextView) findViewById(R.id.TextView_aceJ1);
        aceView2 = (TextView) findViewById(R.id.TextView_aceJ2);

        faultView1 = (TextView) findViewById(R.id.TextView_fauteJ1);
        faultView2 = (TextView) findViewById(R.id.TextView_fauteJ2);

        serveView = (TextView) findViewById(R.id.server_name);

        LinearLayoutMatch = (LinearLayout) findViewById(R.id.LinearLayout_match);

        /* Setup bundle args */
        TextView player1NameView = (TextView) findViewById(R.id.nameJ1);
        player1Name = args.getString(ARG_J1_NAME);
        //player1NameView.setText(player1Name);
        //player1NameView.setText(joueur1.getName());


        TextView player2NameView = (TextView) findViewById(R.id.nameJ2);
        player2Name = args.getString(ARG_J2_NAME);
        //player2NameView.setText(player2Name);
        //player2NameView.setText(joueur2.getName());


        currServe = args.getInt(ARG_SERVE_FIRST);

        /* Display current serving player */
        displayServe();

        //BDD
        mydb = new DatabaseHelper(this);

        // creating players instances
        joueur1 = new Joueurs(player1Name);
        joueur2 = new Joueurs(player2Name);
        // adding players to the database if they don't already exist, fetching their ids
        joueur1.setId(mydb.createJoueur(joueur1.getName()));
        joueur2.setId(mydb.createJoueur(joueur2.getName()));
        //set names
        player1NameView.setText(joueur1.getName());
        player2NameView.setText(joueur2.getName());


        // creating new match instance
        match = new Matches(joueur1.getId(), joueur2.getId());
        // adding match to the database, fetching its id
        match.setId(mydb.createMatch(match));

        // creating new set instance
        //currentSet = new Sets(match.getId(), match.getPlayer1(), match.getPlayer2());
        // adding set to the database, fetching its id
        //currentSet.setId(mydb.createSet(currentSet));

        //create aces and fautes
        mydb.createAces(match.getId(), joueur1.getId(), 0);
        mydb.createAces(match.getId(), joueur2.getId(), 0);


        //mydb.createFautes(match.getId(), joueur1.getId(), 0);
        //mydb.createFautes(match.getId(), joueur2.getId(), 0);


    }


    /**
     * Displays score of player whose tag is given by playerNumber
     */
    protected void displayScore(int playerNumber) {
        if (playerNumber == 1) {
            if (scorePlayer1 == ADVANTAGE)
                scoreView1.setText(getString(R.string.advantage).toUpperCase());
            else
                scoreView1.setText(String.valueOf(scorePlayer1));
        } else {
            if (scorePlayer2 == ADVANTAGE)
                scoreView2.setText(getString(R.string.advantage).toUpperCase());
            else
                scoreView2.setText(String.valueOf(scorePlayer2));
        }
    }

    /**
     * Displays the game score of the player given in the playerNumber parameter.
     *
     * @param playerNumber
     */
    protected void displayGames(int playerNumber) {
        if (playerNumber == 1) {
            gameView1.setText(String.valueOf(gamesPlayer1));
        } else {
            gameView2.setText(String.valueOf(gamesPlayer2));
        }
    }

    /**
     * Displays the set score of the player given in the playerNumber parameter.
     *
     * @param playerNumber
     */
    protected void displaySets(int playerNumber) {
        //currentSet = new Sets(match.getId(), match.getPlayer1(), match.getPlayer2());
        //currentSet.setId(mydb.createSet(currentSet));
        if (playerNumber == 1) {
            setView1.setText(String.valueOf(setsPlayer1));
            //mydb.updateSet(currentSet);

            } else {
            setView2.setText(String.valueOf(setsPlayer2));
            //mydb.updateSet(currentSet);

        }
    }

    /**
     * Displays serving player
     */
    protected void displayServe() {
        switch (currServe) {
            case 1:
                serveView.setText(player1Name);
                break;
            case 2:
                serveView.setText(player2Name);
                break;
        }
    }

    /**
     * Returns the number of the non-serving player
     */
    protected int reverseServe() {
        if (currServe == 1)
            return (2);
        else
            return (1);
    }


    /**
     * Method to display score = 15 to the corresponding player, who is found by the
     * view tag.
     *
     * @param v
     */
    public void fifteenScore(View v) {

        //if (v.getTag() == null)
         //   return;
        //int playerNumber = Integer.parseInt((String) v.getTag());
        int playerNumber;
        if(v.getId() == R.id.quinze_J1)
        {
            playerNumber = 1;
            if (scorePlayer1 == 15)
                return;
            scorePlayer1 = 15;
            displayScore(1);
        }
        else
        {
            playerNumber = 2;
            if (scorePlayer2 == 15)
                return;
            scorePlayer2 = 15;
            displayScore(2);
        }


        /*if (playerNumber == 1) {
            if (scorePlayer1 == 15)
                return;
            scorePlayer1 = 15;
            displayScore(1);

        } else {
            if (scorePlayer2 == 15)
                return;
            scorePlayer2 = 15;
            displayScore(2);
        }*/
        displayServe();

    }

    /**
     * Method to display score = 30 to the corresponding player, who is found by the
     * view tag.
     *
     * @param v
     */
    public void thirtyScore(View v) {
        /*if (v.getTag() == null)
            return;
        int playerNumber = Integer.parseInt((String) v.getTag());*/

        if(v.getId() == R.id.trente_J1)
        {
            if (scorePlayer1 == 30)
                return;
            scorePlayer1 = 30;
            displayScore(1);
        }
        else{
            if (scorePlayer2 == 30)
                return;
            scorePlayer2 = 30;
            displayScore(2);
        }
        /*if (playerNumber == 1) {
            if (scorePlayer1 == 30)
                return;
            scorePlayer1 = 30;
            displayScore(1);

        } else {
            if (scorePlayer2 == 30)
                return;
            scorePlayer2 = 30;
            displayScore(2);
        }*/
        displayServe();
    }

    /**
     * Method to display score = 40 to the corresponding player, who is found by the
     * view tag.
     *
     * @param v
     */
    public void fortyScore(View v) {
        /*if (v.getTag() == null)
            return;
        int playerNumber = Integer.parseInt((String) v.getTag());*/

        if(v.getId() == R.id.quarante_J1)
        {
            if (scorePlayer1 == 40)
                return;
            scorePlayer1 = 40;
            displayScore(1);
        }
        else{
            if (scorePlayer2 == 40)
                return;
            scorePlayer2 = 40;
            displayScore(2);
        }
        /*if (playerNumber == 1) {
            if (scorePlayer1 == 40)
                return;
            scorePlayer1 = 40;
            displayScore(1);

        } else {
            if (scorePlayer2 == 40)
                return;
            scorePlayer2 = 40;
            displayScore(2);
        }*/
        displayServe();
    }

    /**
     * Method to display score = ADV to the corresponding player, who is found by the
     * view tag.
     *
     * @param v
     */
    public void advantageScore(View v) {
        /*if (v.getTag() == null)
            return;
        int playerNumber = Integer.parseInt((String) v.getTag());*/
        int playerNumber;
        if(v.getId() == R.id.adv_J1)
        {
            playerNumber = 1;
            if (scorePlayer1 == ADVANTAGE || scorePlayer2 != 40)
                return;
            scorePlayer1 = ADVANTAGE;
        }
        else{
            playerNumber = 2;
            if (scorePlayer2 == ADVANTAGE || scorePlayer1 != 40)
                return;
            scorePlayer2 = ADVANTAGE;
        }

        /*if (playerNumber == 1) {
            if (scorePlayer1 == ADVANTAGE || scorePlayer2 != 40)
                return;
            scorePlayer1 = ADVANTAGE;
        } else {
            if (scorePlayer2 == ADVANTAGE || scorePlayer1 != 40)
                return;
            scorePlayer2 = ADVANTAGE;
        }*/

        displayScore(playerNumber);
    }

    /**
     * Method to increase game score of the corresponding player (found by view tag) by 1.
     * Checks if game score >= 6 and score difference >= 2 and updates set score if necessary.
     *
     * @param v
     */
    public void gameScore(View v) {
        if (v.getTag() == null)
            return;
        int playerNumber = Integer.parseInt((String) v.getTag());

        /*int playerNumber;

        if(v.getId() == R.id.jeu_J1) playerNumber = 1;
        else playerNumber = 2;*/

        resetScore();
        currServe = reverseServe();
        displayServe();
        if (playerNumber == 1) {
            gamesPlayer1 += 1;
            if (gamesPlayer1 >= 6 && gamesPlayer1 - gamesPlayer2 >= 2) {
                resetGames();
                setScore(v);
            } else
                displayGames(playerNumber);
        } else {
            gamesPlayer2 += 1;
            if (gamesPlayer2 >= 6 && gamesPlayer2 - gamesPlayer1 >= 2) {
                resetGames();
                setScore(v);
            } else
                displayGames(playerNumber);
        }
    }

    /**
     * Method to increase set score of the corresponding player (found by view tag) by 1.
     * It also checks if the game has ended and declares the winner if necessary.
     *
     * @param v
     */
    public void setScore(View v) {
        if (v.getTag() == null)
            return;
        int playerNumber = Integer.parseInt((String) v.getTag());
       /* int playerNumber;

        if(v.getId() == R.id.set_J1) playerNumber = 1;
        else playerNumber = 2;*/

        resetGames();
        if (playerNumber == 1) {
            setsPlayer1 += 1;
            mydb.createSet(match.getId(), joueur1.getId(), setsPlayer1);
            if ((setsPlayer1 > setsPlayer2) && (setsPlayer1 >= 3)) {
                declareWinner(1);
                match.setWinner_id(joueur1.getId());
            } else
                displaySets(playerNumber);
        } else {
            setsPlayer2 += 1;
            mydb.createSet(match.getId(), joueur2.getId(), setsPlayer2);
            if ((setsPlayer2 > setsPlayer1) && (setsPlayer2 >= 3)) {
                declareWinner(2);
                match.setWinner_id(joueur2.getId());

            } else
                displaySets(playerNumber);
        }
        //currentSet = new Sets(match.getId(), match.getPlayer1(), match.getPlayer2());
        // adding set to the database, fetching its id
        //currentSet.setId(mydb.createSet(currentSet));
    }

    /**
     * Method to display number of aces to the corresponding player, who is found by the
     * view id.
     *
     * @param v
     */
    public void aceCounter(View v) {
        if(v.getId() == R.id.btn_aceJ1)
        {
            counterAceJ1++;
            aceView1.setText(Integer.toString(counterAceJ1));
            //mydb.createAces(match.getId(), joueur1.getId(), counterAceJ1);
            mydb.updateAces(match_id, joueur1.getId(), counterAceJ1);
        }
        else
        {
            counterAceJ2++;
            aceView2.setText(Integer.toString(counterAceJ2));
            //mydb.createAces(match.getId(), joueur2.getId(), counterAceJ2);
            mydb.updateAces(match_id, joueur2.getId(), counterAceJ2);
        }
    }

    /**
     * Method to display number of faults to the corresponding player, who is found by the
     * view id.
     *
     * @param v
     */
    public void faultCounter(View v) {
        if(v.getId() == R.id.btn_fauteJ1)
        {
            counterFaultJ1++;
            faultView1.setText(Integer.toString(counterFaultJ1));
            mydb.createFautes(match.getId(), joueur1.getId(), counterFaultJ1);

        }
        else
        {
            counterFaultJ2++;
            faultView2.setText(Integer.toString(counterFaultJ2));
            mydb.createFautes(match.getId(), joueur2.getId(), counterFaultJ2);
        }
    }



    public void reset(View v) {

        resetSets();
        resetStats();
        match = new Matches(joueur1.getId(), joueur2.getId());
        // adding match to the database, fetching its id
        match.setId(mydb.createMatch(match));
    }

    /**
     * Resets in-game score count.
     */
    protected void resetScore() {
        scorePlayer1 = scorePlayer2 = 0;
        displayScore(1);
        displayScore(2);
    }

    /**
     * Resets game count.
     */
    protected void resetGames() {
        gamesPlayer1 = gamesPlayer2 = 0;
        displayGames(1);
        displayGames(2);
        resetScore();
    }

    /**
     * Resets set count.
     */
    protected void resetSets() {
        setsPlayer1 = setsPlayer2 = 0;
        displaySets(1);
        displaySets(2);
        resetGames();
    }

    /**
     * Resets aces and faults count.
     */
    protected void resetStats() {
        counterAceJ1 = counterAceJ2 = 0;
        counterFaultJ1 = counterFaultJ2 = 0;
        aceView1.setText(Integer.toString(counterAceJ1));
        aceView2.setText(Integer.toString(counterAceJ2));
        faultView1.setText(Integer.toString(counterFaultJ1));
        faultView2.setText(Integer.toString(counterFaultJ2));
    }

    /**
     * Method to display the popup message in order to declare winner.
     *
     * @param playerNumber
     */
    protected void declareWinner(int playerNumber) {
        /* Inflate a new popup window */
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View winnerView = inflater.inflate(R.layout.gagnant_popup, null);
        final PopupWindow mPopupWindow = new PopupWindow(
                winnerView,
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        if (Build.VERSION.SDK_INT >= 21) {
            mPopupWindow.setElevation(6.0f);
        }
        /* Cast a transparent black layer on the foreground*/
        if (Build.VERSION.SDK_INT >= 23)
            LinearLayoutMatch.setForeground(new ColorDrawable(ContextCompat.getColor(getApplicationContext(), R.color.transparentBlack)));
        Button resetButton = (Button) winnerView.findViewById(R.id.dismiss);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetSets();
                mPopupWindow.dismiss();
                if (Build.VERSION.SDK_INT >= 23)
                    LinearLayoutMatch.setForeground(null);
            }
        });
        TextView winnerText = (TextView) winnerView.findViewById(R.id.winner_textview);
        if (playerNumber == 1)
        {
            winnerText.setText(player1Name + " " + getString(R.string.player_wins));
            match.setWinner_id(joueur1.getId());
        }


        else
        {
            winnerText.setText(player2Name + " " + getString(R.string.player_wins));
            match.setWinner_id(joueur2.getId());
        }

        mPopupWindow.showAtLocation(LinearLayoutMatch, Gravity.CENTER, 0, 0);
    }

    /**
     * Displays score of player whose tag is given by playerNumber
     */
    public void endMatch(View v) {
        final Intent intentStats = new Intent(this, StatsActivity.class);

        if(setsPlayer1 > setsPlayer2){
            match.setWinner_id(joueur1.getId());

        }else{
            if(setsPlayer1 > setsPlayer2){
                match.setWinner_id(joueur2.getId());
                //mydb.updateSet(currentSet);

            }else{
                this.showMatchNotOverDialog();
                //mydb.updateSet(currentSet);
            }
        }

        mydb.updateMatch(match);

        intentStats.putExtra("match",match.getId());

        //mydb.close();

        startActivity(intentStats);

    }

    public void showMatchNotOverDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.match_not_over).setMessage(R.string.message_not_over);
        builder.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.match, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action

            ///
            Bundle args = new Bundle();
            args.putString(MatchActivity.ARG_J1_ID, joueur1.getId()+"");
            args.putString(MatchActivity.ARG_J2_ID, joueur2.getId()+"");

            Intent i = new Intent(MatchActivity.this, PhotoActivity.class);
            i.putExtras(args);
            startActivity(i);

            ////
            //Intent myIntent = new Intent(MatchActivity.this,PhotoActivity.class);
            //startActivity(myIntent);

        } else if (id == R.id.nav_gallery) {

        } /*else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
