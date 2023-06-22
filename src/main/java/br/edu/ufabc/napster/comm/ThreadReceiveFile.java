package br.edu.ufabc.napster.comm;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class ThreadReceiveFile extends ThreadFile{

    private static DataInputStream dataInputStream;
    public ThreadReceiveFile(Socket peer) {
        super(peer);
    }

    @Override
    public void run() {
        try {
            dataInputStream = new DataInputStream(this.peer.getInputStream());

            String path = "C:\\Users\\vmoli\\projeto1\\napster\\test_files\\peer1\\test2.txt";
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
