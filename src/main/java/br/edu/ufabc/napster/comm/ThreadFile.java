package br.edu.ufabc.napster.comm;
import java.net.Socket;

public abstract class ThreadFile extends Thread {
    public Socket peer = null;
    public ThreadFile(Socket peer){
        this.peer = peer;
    }
    public abstract void run();

}

