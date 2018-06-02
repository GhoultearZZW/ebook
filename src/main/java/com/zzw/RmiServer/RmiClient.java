package com.zzw.RmiServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RmiClient {
	public static String getResult() throws RemoteException, NotBoundException, MalformedURLException{
		GetRequest result = (GetRequest)Naming.lookup("rmi://localhost:8889/GetRequest");
		return result.GetResult();
	}
}
