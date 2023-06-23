package br.edu.ufabc.napster.rmi;

import br.edu.ufabc.napster.peer.Peer;
import br.edu.ufabc.napster.rmi.serializables.Response;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Manager extends Remote {
    public Response join(Peer peer) throws Exception;
    public ArrayList search(Peer requestingPeer, String file) throws RemoteException;
    public Response update(Peer peer, String file) throws RemoteException;
}
