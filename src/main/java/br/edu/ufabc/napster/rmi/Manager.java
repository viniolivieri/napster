package br.edu.ufabc.napster.rmi;

import com.sun.jmx.remote.internal.ArrayQueue;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Manager extends Remote {
    public Response join() throws RemoteException;
    public ArrayQueue search() throws RemoteException;
    public Response update() throws RemoteException;
}
