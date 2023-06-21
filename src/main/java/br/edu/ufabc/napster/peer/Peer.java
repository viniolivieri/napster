package br.edu.ufabc.napster.peer;

import br.edu.ufabc.napster.rmi.Manager;
import br.edu.ufabc.napster.rmi.serializables.Response;

import java.io.File;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Peer implements Serializable {

    private static final long serialVersionUID = 123123123;
    private InetAddress ip;
    private int port;
    private String sharedFolder;
    public List<String> files;

    public String address;

    //Constructor of Peer
    public Peer(InetAddress ip, int port, String sharedFolder){
        this.ip = ip;
        this.port = port;
        this.sharedFolder = sharedFolder;
        this.files = new ArrayList<String>(this.getSharedFiles());
        this.address = ip.toString() + ":" + Integer.toString(port);
    }

    // Some getProperties functions
    public int getPort(){ return this.port;}
    public String getIp(){ return this.ip.toString();}
    public String getSharedFolder(){ return this.sharedFolder;}

    // This get function in special is defined to list the files that are available in the defined shared folder.
    public List<String> getSharedFiles(){
        File sharedFolderDir = new File(this.getSharedFolder());
        List<String> files = new ArrayList<String>(Arrays.asList(sharedFolderDir.list()));
        return files;
    }
    // Update the state of files listed in shared folder.
    public void updateSharedFilesState(){
        this.files = new ArrayList<String>(this.getSharedFiles());
    }

    // Function to ask the user information needed in the start of the peer.
    public static Peer getStartingInformation() throws UnknownHostException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter peer IP: ");
        //InetAddress ip = InetAddress.getByName(scanner.nextLine());
        InetAddress ip = InetAddress.getByName("127.0.0.1");
        System.out.println("Enter server port: ");
        //int port =  Integer.parseInt(scanner.nextLine());
        int port =  7891;
        System.out.println("Enter peer sharing folder: ");
        //String folder = scanner.nextLine();
        String folder = "/Users/vinijampp/Documents/0_UFABC/sistemas_distribuidos/projeto1/napster/test_files/peer1";

        Peer peer = new Peer(ip, port, folder);
        return peer;
    }
    public static void main(String[] args) throws Exception{

        // Captures starting information from keyboard
        Peer peer = getStartingInformation();

        Registry reg = LocateRegistry.getRegistry();
        Manager manager = (Manager) reg.lookup("rmi://127.0.0.1/manager");
        Response response = manager.join(peer);
        //Response response = manager.update(peer, "test1.txt");
        System.out.println(response);

        ArrayList<Peer> peers = manager.search("test1.txt");
        for (Peer p : peers) {
            System.out.println(p.address);
            System.out.println(p.getSharedFolder());
        }


    }

}
