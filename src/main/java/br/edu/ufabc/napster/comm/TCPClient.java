package br.edu.ufabc.napster.comm;

import java.net.Socket;

public class TCPClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("127.0.0.1", 9000);

        ThreadReceiveFile thr = new ThreadReceiveFile(socket);
        thr.start();
    }
}
