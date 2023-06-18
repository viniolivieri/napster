package br.edu.ufabc.napster.server;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.Remote;
import java.util.Scanner;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {

    InetAddress ip;
    int port;

    public Server(InetAddress ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public int getPort(){ return this.port;}
    public String getIp(){ return this.ip.toString();}
    public static Server getStartingInformation() throws UnknownHostException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter server IP: ");
        InetAddress ip = InetAddress.getByName(scanner.nextLine());
        System.out.println("Enter server port: ");
        int port =  Integer.parseInt(scanner.nextLine());

        Server server = new Server(ip,port);
        return server;
    }
    public static void main(String[] args) throws Exception{

        // Captures starting information from keyboard
        Server server = getStartingInformation();
        LocateRegistry.createRegistry(server.getPort());

        Registry reg = LocateRegistry.getRegistry();
        //reg.bind("rmi://127.0.0.1/join", null); //change to JOIN
    }

}
