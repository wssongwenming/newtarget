package com.mmall.socket;

import java.util.Hashtable;
import java.util.Map;

public class SocketLock {

    private static SocketLock socketLock ;

    /**
     * map key 为当前socket通讯的静态ip地址；
     * value通讯状态，true为正在通讯、false为可以进行下一次通讯
     *
     */
    private static Map<String, Boolean> socketLocks = new Hashtable<String, Boolean>();

    static{
        instance();
    }

    private SocketLock(){

    }

    private static synchronized SocketLock instance(){
        if(socketLock==null){
            socketLock = new SocketLock();
        }
        return socketLock;
    }

    /**
     * 添加socket通讯锁
     * @param host socket通讯静态ip
     */
    public static synchronized void addlock(String host){
        socketLocks.put(host, true);
    }

    /**
     * 查看当前socket通讯是否完成，是否可进入下一次通讯
     * @param host socket通讯静态ip
     * @return false 可进入下一次通讯，true 正在通讯
     */
    public static synchronized boolean isLock(String host){
        boolean isLock = false;
        if(socketLocks.containsKey(host)){
            isLock = socketLocks.get(host);
        }
        if(!isLock){
            //给未添加或已完成的socket通讯 重新加锁
            addlock(host);
        }
        return isLock;
    }

    /**
     * 在socket通讯池同解除当前socket通讯锁
     * @param host socket通讯的静态ip
     */
    public static synchronized void loseLock(String host){
        if(socketLocks.containsKey(host)){
            socketLocks.put(host, false);
        }
    }

}