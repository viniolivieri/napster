package br.edu.ufabc.napster.comm;

import br.edu.ufabc.napster.peer.Peer;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.Arrays;

public class ThreadSendFile extends ThreadFile{

    private static DataOutputStream dataOutputStream;
    public Peer sendingPeer;
    public ThreadSendFile(Socket peerSocket, Peer sendingPeer, String fileName) {
        super(peerSocket, fileName);
        this.sendingPeer = sendingPeer;
    }

    @Override
    public void run() {
        try {
            dataOutputStream = new DataOutputStream(this.peerSocket.getOutputStream());

            String path = Paths.get(this.sendingPeer.getSharedFolder(), this.fileName.trim()).toString();
            int readingBytes = 0;
            File file = new File(path);
            FileInputStream fileInputStream = new FileInputStream(file);

            // Using protocol to send first the file size. ref:https://shorturl.at/gopzE
            dataOutputStream.writeLong(file.length());

            // Now buffering the file into small pieces to be able to fit in TCP of the Socket implementation.
            byte[] buffer = new byte[4*1024]; //4kB

            while ((readingBytes=fileInputStream.read(buffer))!=-1) {
                dataOutputStream.write(buffer, 0, readingBytes);
                dataOutputStream.flush();
            }
            // Closing file and connection
            fileInputStream.close();
            dataOutputStream.close();

        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}
