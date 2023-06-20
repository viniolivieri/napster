package br.edu.ufabc.napster.rmi;

import com.sun.jmx.remote.internal.ArrayQueue;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ManagerImpl extends UnicastRemoteObject implements Manager {
    protected ManagerImpl() throws RemoteException {
    }

    public String join() throws RemoteException {

        return null;
    }
    public ArrayQueue search() throws RemoteException{

        return null;
    }
    public String update() throws RemoteException {

        return null;
    }
}
