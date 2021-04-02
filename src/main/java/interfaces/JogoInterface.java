package main.java.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface JogoInterface extends Remote {
  public int registra(String ip) throws RemoteException;
  public int joga(int id) throws RemoteException;
  public int desiste(int id) throws RemoteException;
  public int finaliza(int id) throws RemoteException;
}
