package br.edu.ufabc.napster.comm;
import java.net.Socket;

public abstract class ThreadFile extends Thread {
    public Socket peerSocket = null;
    public String fileName = null;
    public ThreadFile(Socket peer, String fileName){
        this.peerSocket = peer;
        this.fileName = String.valueOf(fileName);
    }
    public abstract void run();

}

