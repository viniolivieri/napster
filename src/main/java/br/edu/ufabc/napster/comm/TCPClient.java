package br.edu.ufabc.napster.comm;

import br.edu.ufabc.napster.peer.Peer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TCPClient {

    Peer peerWithFile;
    Peer receiverPeer;
    public TCPClient(Peer receiverPeer, Peer peerWithFile){
        this.peerWithFile = peerWithFile;
        this.receiverPeer = receiverPeer;
    }

    public void download(String fileName) throws IOException {
        // Cria um socket de comunicação com o serverSocket iniciado do lado do peer
        // que contem o arquivo.
        Socket socket = new Socket(this.peerWithFile.getIp(), this.peerWithFile.getPort());

        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        this.sendFileName(dataOutputStream, fileName);
        ThreadReceiveFile thr = new ThreadReceiveFile(socket, this.receiverPeer, fileName);
        thr.start();
    }

    private void sendFileName(DataOutputStream dataOutputStream, String fileName) throws IOException {
        // Send the fileName length and its content.
        long fileLength = fileName.length();
        dataOutputStream.writeLong(fileLength);
        dataOutputStream.writeBytes(fileName);

    }
}
