package br.edu.ufabc.napster.comm;

import br.edu.ufabc.napster.peer.Peer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer extends Thread {

    Peer peer;

    public TCPServer(Peer peer){
        this.peer = peer;
    }
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(this.peer.getPort());

            while (true) {
                Socket serverSocketPeer = serverSocket.accept();
                ThreadSendFile thr = new ThreadSendFile(serverSocketPeer, this.peer,"nome_do_arquivo");
                thr.start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

