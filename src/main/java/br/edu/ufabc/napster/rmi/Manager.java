package br.edu.ufabc.napster.rmi;

import br.edu.ufabc.napster.rmi.models.Peer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Manager extends Remote {
    public Response join(int peerId, String[] peerFiles) throws RemoteException, Exception;
    public Peer[] search(String file) throws RemoteException;
    public Response update() throws RemoteException;
}
