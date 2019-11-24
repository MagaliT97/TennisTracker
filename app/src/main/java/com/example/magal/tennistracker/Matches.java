package com.example.magal.tennistracker;

public class Matches {

    private long id;
    private long winner_id;
    private long player1;
    private long player2;

    public Matches(){}

    public Matches(long player1, long player2){
        this.player1=player1;
        this.player2=player2;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public long getPlayer1() {
        return player1;
    }

    public long getWinner_id() {
        return winner_id;
    }

    public long getPlayer2() {
        return player2;
    }

    public void setPlayer1(long player1) {
        this.player1 = player1;
    }

    public void setPlayer2(long player2) {
        this.player2 = player2;
    }

    public void setWinner_id(long winner_id) {
        this.winner_id = winner_id;
    }

}
