package br.edu.ufabc.napster.comm;

import br.edu.ufabc.napster.peer.Peer;

import java.io.IOException;
import java.net.Socket;

public class TCPClient {

    Peer peer;
    public TCPClient(Peer peer){
        this.peer = peer;
    }

    public void download(String fileName) throws IOException {
        Socket socket = new Socket(this.peer.getIp(), this.peer.getPort());
        ThreadReceiveFile thr = new ThreadReceiveFile(socket, this.peer, fileName);
        thr.start();
    }
}
