package com.example.magal.tennistracker;

public class Joueurs {

     private long id;
        private String name;

        //constructors
        public Joueurs(){}

        public Joueurs(String name){
            this.name = name;
        }

        //getters
        public long getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        //setters
        public void setId(long id) {
            this.id = id;
        }

        public void setName(String name){
            this.name = name;
        }

}
