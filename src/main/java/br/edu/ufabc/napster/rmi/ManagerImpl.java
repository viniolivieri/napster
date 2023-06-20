package br.edu.ufabc.napster.rmi;

import br.edu.ufabc.napster.rmi.models.Peer;
import com.sun.jmx.remote.internal.ArrayQueue;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ManagerImpl extends UnicastRemoteObject implements Manager {

    private ArrayQueue<Peer> peersList;

    // Default capacity of 1000 peers
    public ManagerImpl() throws RemoteException {
        super();
        this.peersList = new ArrayQueue(1000);
    }

    public ManagerImpl(int capacity) throws RemoteException {
        super();
        this.peersList = new ArrayQueue(capacity);
    }

    // Adding new peer to the list of connected peers.
    public Response join(int peerId, String[] peerFiles) throws RemoteException, Exception {
        Peer newPeer = new Peer(peerId, peerFiles);
        if (!this.idExists(peerId)) {
            this.peersList.add(newPeer);
            return new Response("JOIN_OK", "SERVER", Integer.toString(peerId));
        }
        else {
            throw new Exception("peerId already exists, please choose another one.");
        }
    }

    // Return list of peers that has the searched file.
    public Peer[] search(String file) throws RemoteException{

        return null;
    }


    public ArrayQueue search() throws RemoteException {
        return null;
    }

    public Response update() throws RemoteException {

        return null;
    }

    private boolean idExists(int id) {
        for (Peer peer : this.peersList) {
            if(peer.id == id) {
                return true;
            }
        }
        return false;
    }
}
