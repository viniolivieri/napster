package br.edu.ufabc.napster.comm;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

public class ThreadSendFile extends ThreadFile{

    private static DataOutputStream dataOutputStream;
    public ThreadSendFile(Socket peer) {
        super(peer);
    }

    @Override
    public void run() {
        try {
            dataOutputStream = new DataOutputStream(this.peer.getOutputStream());

            String path = "C:\\Users\\vmoli\\projeto1\\napster\\test_files\\peer1\\test1.txt";
            int readingBytes = 0;
            File file = new File(path);
            FileInputStream fileInputStream = new FileInputStream(file);

            // Using protocol to send first the file size. ref:https://shorturl.at/gopzE
            dataOutputStream.writeLong(file.length());

            // Now buffering the file into small pieces to be able to fit in TCP of the Socket implementation.
            byte[] buffer = new byte[4*1024]; //4kB
            readingBytes = fileInputStream.read(buffer);
            while (readingBytes!=-1) {
                dataOutputStream.write(buffer, 0, readingBytes);
                dataOutputStream.flush();
                readingBytes = fileInputStream.read(buffer);
            }
            // Closing file and connection
            fileInputStream.close();
            dataOutputStream.close();
            System.out.println("file sent!");

        } catch (Exception e) {
            
        }
    }
}
