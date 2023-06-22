package br.edu.ufabc.napster.comm;

import br.edu.ufabc.napster.peer.Peer;

import java.io.*;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.Arrays;

public class ThreadReceiveFile extends ThreadFile{

    private static DataInputStream dataInputStream;
    public Peer receivingPeer;
    public String fileName;

    public ThreadReceiveFile(Socket peerSocket, Peer peer, String fileName) {
        super(peerSocket, fileName);
        this.receivingPeer = peer;
    }

    @Override
    public void run() {
        try {
            dataInputStream = new DataInputStream(this.peerSocket.getInputStream());

            // Using Paths package to join the path that the file is going to be written.
            String path = Paths.get(this.receivingPeer.getSharedFolder(), this.fileName).toString();
            int writingBytes = 0;
            FileOutputStream fileOutputStream = new FileOutputStream(path);

            // Using protocol to read first the file size. ref:https://shorturl.at/gopzE
            long fileSize = dataInputStream.readLong();

            // Now reading the prepared buffer on the sender side.
            byte[] buffer = new byte[4*1024]; //4kB
            writingBytes = dataInputStream.read(buffer, 0 , (int)Math.min(buffer.length, fileSize));
            while (fileSize > 0 && writingBytes!=-1) {
                fileOutputStream.write(buffer, 0, writingBytes);
                fileSize -= writingBytes;
                writingBytes = dataInputStream.read(buffer, 0 , (int)Math.min(buffer.length, fileSize));
            }
            fileOutputStream.close();
            dataInputStream.close();

        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}
