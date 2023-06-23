package br.edu.ufabc.napster.rmi;

import br.edu.ufabc.napster.peer.Peer;
import br.edu.ufabc.napster.rmi.serializables.Response;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

public class ManagerImpl extends UnicastRemoteObject implements Manager {

    private ArrayBlockingQueue<Peer> peersList;

    // Default capacity of 1000 peers
    public ManagerImpl() throws RemoteException {
        super();
        this.peersList = new ArrayBlockingQueue(1000);
    }

    public ManagerImpl(int capacity) throws RemoteException {
        super();
        this.peersList = new ArrayBlockingQueue(capacity);
    }

    // Adding new peer to the list of connected peers.
    public Response join(Peer newPeer) throws Exception {
        String peerAddress = newPeer.address;
        if (!this.addressExists(peerAddress)) {
            this.peersList.add(newPeer);
            System.out.printf("Peer %s adicionado com arquivos %s\n", newPeer.address, newPeer.getSharedFiles().toString());
            return new Response("JOIN_OK", "SERVER", newPeer.address);
        }
        else {
            throw new Exception("peer with this address already exists, please choose another one.");
        }
    }

    // Return list of peers that has the searched file.
    public ArrayList search(Peer requestingPeer, String file) throws RemoteException{
        System.out.printf("Peer %s solicitou arquivo %s.\n", requestingPeer.address, file);
        ArrayList<Peer> peersWithThisFile = new ArrayList<Peer>();
        Iterator<Peer> peerListIterator = this.peersList.iterator();

        while (peerListIterator.hasNext()) {
            Peer peer = peerListIterator.next();
            List peerFiles = peer.files;
            if(peerFiles.contains(file)) {
                peersWithThisFile.add(peer);
            }
        }
        return peersWithThisFile;
    }


    public Response update(Peer peer, String file) throws RemoteException {
        peer.files.add(file);
        Iterator<Peer> iteratorPeersList = this.peersList.iterator();
        while (iteratorPeersList.hasNext()){
            Peer oldPeer = iteratorPeersList.next();
            if (oldPeer.address.equals(peer.address)) {
                iteratorPeersList.remove();
                this.peersList.add(peer);
            }
        }
        return new Response("UPDATE_OK", "SERVER", peer.address);

    }

    private boolean addressExists(String address) {

        Iterator<Peer> peerListIterator = this.peersList.iterator();
        while (peerListIterator.hasNext()) {
            Peer peer = peerListIterator.next();
            if(peer.address.equals(address)) {
                return true;
            }
        }
        return false;
    }
}
