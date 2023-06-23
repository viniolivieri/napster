package br.edu.ufabc.napster.peer;

import br.edu.ufabc.napster.comm.TCPClient;
import br.edu.ufabc.napster.comm.TCPServer;
import br.edu.ufabc.napster.rmi.Manager;
import br.edu.ufabc.napster.rmi.serializables.Response;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.stream.Collectors;

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
        this.address = this.getIp() + ":" + Integer.toString(port);
    }

    // Some getProperties functions
    public int getPort(){ return this.port;}
    public String getIp(){ return this.ip.getHostAddress();}
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
        InetAddress ip = InetAddress.getByName(scanner.nextLine());
        System.out.println("Enter server port: ");
        int port =  Integer.parseInt(scanner.nextLine());
        System.out.println("Enter peer sharing folder: ");
        String folder = scanner.nextLine();

        Peer peer = new Peer(ip, port, folder);
        return peer;
    }
    public String toString() {
        return "IP: " + this.getIp() + "\n"+
                "Port: " + this.getPort() + "\n"+
                "Address: "+ this.address + "\n"+
                "Shared Folder: " + this.getSharedFolder() + "\n"+
                "Files: " + this.files.toString() + "\n"+
                "Get Files: " + this.getSharedFiles().toString() + "\n";
    }

    // Function to download the file to the current Peer.
    public void download(String fileName, Peer peer) throws IOException {
        Peer peerWithFile = peer;
        Peer me = this;
        TCPClient client = new TCPClient(me, peerWithFile);

        // Now we have a client with connection with that peer.
        // And we can download the file.
        client.download(fileName);

    }

    public static int printMenu(Scanner scanner){
        String menu = "Choose your command:\n" +
                "1) JOIN\n" +
                "2) SEARCH\n" +
                "3) DOWNLOAD\n";
                System.out.println(menu);

        return Integer.parseInt(scanner.nextLine());
    }
    public static void main(String[] args) throws Exception{

        // Captures starting information from keyboard
        Peer peer = getStartingInformation();
        TCPServer fileServer = new TCPServer(peer);
        fileServer.start();

        Registry reg = LocateRegistry.getRegistry();
        Manager manager = (Manager) reg.lookup("rmi://" + peer.getIp() + "/manager");

        // Menu
        int selector;
        ArrayList<Peer> peers = new ArrayList<>();
        String fileName = null;
        Scanner scanner = new Scanner(System.in);
        Hashtable<String, Peer> hashTablePeers = new Hashtable<>();
        selector = printMenu(scanner);
        while (selector != 0) {
            switch (selector) {
                case 1:
                    Response response = manager.join(peer);
                    System.out.println();
                    if (response.message.equals("JOIN_OK")){
                        System.out.printf("Sou peer %s com arquivos %s\n", peer.address, peer.getSharedFiles().toString());
                    }
                    selector = printMenu(scanner);
                    break;
                case 2:
                    System.out.println("Please type the name of the file that you want to search in the network:");
                    fileName = scanner.nextLine();
                    peers = manager.search(peer, fileName);
                    hashTablePeers = new Hashtable<>();
                    System.out.printf("Peers with requested file: \n");
                    for (Peer p: peers){
                        System.out.print("- " + p.address + "\n");
                        hashTablePeers.put(p.address, p);
                    }
                    selector = printMenu(scanner);
                    break;
                case 3:
                    if (fileName == null) {
                        System.out.println("Please, first you need to search for a file.");
                        selector = printMenu(scanner);
                        break;
                    }
                    else if (peers.isEmpty()) {
                        System.out.println("File is not available.");
                        selector = printMenu(scanner);
                        break;
                    }
                    System.out.printf("Please type from which peer you want to download %s:", fileName);
                    String address = scanner.nextLine();
                    Peer peerWithFile = hashTablePeers.get(address);
                    peer.download(fileName, peerWithFile);
                    manager.update(peer, fileName);
                    selector = printMenu(scanner);
                    break;
                case 0:
                    break;
                default:
                    System.out.print("Opção inválida! Tente de novo.\n");
                    selector = printMenu(scanner);
            }

        }

    }

}
