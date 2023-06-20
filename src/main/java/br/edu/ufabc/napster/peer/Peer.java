package br.edu.ufabc.napster.peer;

import br.edu.ufabc.napster.rmi.Manager;
import br.edu.ufabc.napster.rmi.serializables.ArrayListSerializable;
import br.edu.ufabc.napster.rmi.serializables.Response;

import java.io.File;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Peer implements Serializable {

    private static final long serialVersionUID = 123123123;
    private InetAddress ip;
    private int port;
    private String sharedFolder;

    public String address;

    //Constructor of Peer
    public Peer(InetAddress ip, int port, String sharedFolder){
        this.ip = ip;
        this.port = port;
        this.sharedFolder = sharedFolder;
        this.address = ip.toString() + ":" + Integer.toString(port);
    }

    // Some getProperties functions
    public int getPort(){ return this.port;}
    public String getIp(){ return this.ip.toString();}
    public String getSharedFolder(){ return this.sharedFolder;}

    // This get function in special is defined to list the files that are available in the defined shared folder.
    public String[] getSharedFiles(){
        File sharedFolderDir = new File(this.getSharedFolder());
        String files[] = sharedFolderDir.list();
        return files;
    }

    // Function to ask the user information needed in the start of the peer.
    public static Peer getStartingInformation() throws UnknownHostException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter peer IP: ");
        //InetAddress ip = InetAddress.getByName(scanner.nextLine());
        InetAddress ip = InetAddress.getByName("127.0.0.1");
        System.out.println("Enter server port: ");
        //int port =  Integer.parseInt(scanner.nextLine());
        int port =  7894;
        System.out.println("Enter peer sharing folder: ");
        //String folder = scanner.nextLine();
        String folder = "C:\\Users\\vmoli\\Documents\\peer4";

        Peer peer = new Peer(ip, port, folder);
        return peer;
    }
    public static void main(String[] args) throws Exception{

        // Captures starting information from keyboard
        Peer peer = getStartingInformation();

        Registry reg = LocateRegistry.getRegistry();
        Manager manager = (Manager) reg.lookup("rmi://127.0.0.1/manager");
        Response response = manager.join(peer);
        System.out.println(response);
        ArrayListSerializable files = manager.search("test1.txt");
        System.out.println(files.toString());

    }

}
