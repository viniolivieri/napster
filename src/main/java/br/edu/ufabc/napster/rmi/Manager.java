package br.edu.ufabc.napster.rmi;

import com.sun.jmx.remote.internal.ArrayQueue;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Manager extends Remote {
    public String join() throws RemoteException;
    public ArrayQueue search() throws RemoteException;
    public String update() throws RemoteException;
}
