package br.edu.ufabc.napster.rmi;

import br.edu.ufabc.napster.rmi.models.Peer;
import com.sun.jmx.remote.internal.ArrayQueue;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ManagerImpl extends UnicastRemoteObject implements Manager {

    private ArrayQueue<Peer> peersList;
    public ManagerImpl() throws RemoteException {
        super();
        this.peersList = new ArrayQueue();
    }

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
    public ArrayQueue search(String file) throws RemoteException{

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
