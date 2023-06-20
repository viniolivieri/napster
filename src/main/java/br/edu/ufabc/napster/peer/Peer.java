package br.edu.ufabc.napster.peer;

import br.edu.ufabc.napster.rmi.Manager;
import br.edu.ufabc.napster.rmi.Response;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Scanner;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Peer {

    private InetAddress ip;
    private int port;
    private String sharedFolder;

    public Peer(InetAddress ip, int port, String sharedFolder){
        this.ip = ip;
        this.port = port;
        this.sharedFolder = sharedFolder;
    }

    public int getPort(){ return this.port;}
    public String getIp(){ return this.ip.toString();}
    public String getSharedFolder(){ return this.sharedFolder;}

    public String[] getSharedFiles(){
        File sharedFolderDir = new File(this.getSharedFolder());
        String files[] = sharedFolderDir.list();
        return files;
    }
    public static Peer getStartingInformation() throws UnknownHostException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter peer IP: ");
        InetAddress ip = InetAddress.getByName(scanner.nextLine());
        System.out.println("Enter peer port: ");
        int port =  Integer.parseInt(scanner.nextLine());
        System.out.println("Enter peer sharing folder: ");
        String folder = scanner.nextLine();

        Peer peer = new Peer(ip, port, folder);
        return peer;
    }
    public static void main(String[] args) throws Exception{

        // Captures starting information from keyboard
        Peer peer = getStartingInformation();

        Registry reg = LocateRegistry.getRegistry();
        System.out.println(Arrays.toString(peer.getSharedFiles()));
        Manager manager = (Manager) reg.lookup("rmi://127.0.0.1/manager");
        Response response = manager.join(6, peer.getSharedFiles());
        System.out.println(response);
    }

}
