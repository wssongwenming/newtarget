package com.mmall.socket;

public class ClientFactory {
    public static Client createClient(String host,int port,int deviceGroupIndex) throws Exception{
        return new Client(host,port,deviceGroupIndex);
    }
}
