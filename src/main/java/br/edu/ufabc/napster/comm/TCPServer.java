package br.edu.ufabc.napster.comm;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9000);

        while (true) {
            Socket peer = serverSocket.accept();
            ThreadSendFile thr = new ThreadSendFile(peer);
            thr.start();
        }

    }
}

