package br.edu.ufabc.napster.rmi;

import br.edu.ufabc.napster.peer.Peer;
import br.edu.ufabc.napster.rmi.serializables.ArrayListSerializable;
import br.edu.ufabc.napster.rmi.serializables.Response;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Manager extends Remote {
    public Response join(Peer peer) throws Exception;
    public ArrayListSerializable search(String file) throws RemoteException;
    public Response update() throws RemoteException;
}
