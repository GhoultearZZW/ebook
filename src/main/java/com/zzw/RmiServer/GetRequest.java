package com.zzw.RmiServer;

import java.rmi.Remote;

public interface GetRequest extends Remote{
	public String GetResult();
}
