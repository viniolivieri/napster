package br.edu.ufabc.napster.comm;

import br.edu.ufabc.napster.peer.Peer;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TCPServer extends Thread {
    Peer peer;

    public TCPServer(Peer peer){
        this.peer = peer;
    }
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(this.peer.getPort());
            // Um serversocket é criado que basicamente ficará esperando um socket se conectar
            // na porta definida acima
            while (true) {
                Socket serverSocketPeer = serverSocket.accept();
                // Levanta outro thread para o envio do arquivo.
                // Mas antes espera pelo nome do arquivo que deve enviar pela função
                // receiveFileNameToSend
                ThreadSendFile thr = new ThreadSendFile(serverSocketPeer, this.peer, this.receiveFileNameToSend(serverSocketPeer));
                thr.start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String receiveFileNameToSend(Socket serverSocketPeer) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(serverSocketPeer.getInputStream());

        long fileNameLength = dataInputStream.readLong();
        byte[] fileNameByte = new byte[1024 * 16];
        dataInputStream.read(fileNameByte, 0, (int) fileNameLength);
        return new String(fileNameByte, StandardCharsets.UTF_8);
    }

}
