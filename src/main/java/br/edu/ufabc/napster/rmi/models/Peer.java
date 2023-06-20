package br.edu.ufabc.napster.rmi.models;

public class Peer {
    public int id;
    public String[] files;

    public Peer(int id, String[] files){
        this.id = id;
        this.files = files;
    }
}
