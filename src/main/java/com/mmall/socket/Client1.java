package com.mmall.socket;

import com.mmall.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client1 {
    private Logger LOGGER= LoggerFactory.getLogger(Client1.class);
//    public Client(String host,int port) throws Exception{
//            if(this.client==null) {
//                this.client = new Socket(host, port);
//                this.client.setSoTimeout(20 * 1000);
//                System.out.print("client[port:" + client.getLocalPort() + "]与服务器端建立连接");
//            }
//    }

        public Client1(){  }
    /***
     * 发送消息
     * @param cmd
     */

    public String send(String host,int port,String cmd)throws Exception{
        try {
            OutputStream out = client.getOutputStream();
            byte[] hexStrToByteArrs = hexStrToByteArrs(cmd);
            if (hexStrToByteArrs == null) {
                return "error";
            }
            out.write(hexStrToByteArrs);

            InputStream in = client.getInputStream();

            byte[] buf = new byte[1024];

            in.read(buf);

            stop();

            String msg=bytesToHexString(buf);

          return bytesToHexString(buf);
        } catch (IOException e) {
            LOGGER.error("sendCmd error", e);
            stop();
            return "error";
        }
    }

    private  Socket client=null;


    public void stop(){
        try{
            if(client!=null){

                if(!client.isInputShutdown())
                {client.shutdownInput();}
                if(!client.isOutputShutdown())
                {client.shutdownOutput();}
                client.close();
                client=null;
            }
        }catch (Exception e)
        {
            LOGGER.error("connection error",e);
        }
    }

    /**
     * 将十六进制的字符串转换成字节数组
     *
     * @param hexString
     * @return
     */
    public static byte[] hexStrToByteArrs(String hexString) {
        if (StringUtils.isEmpty(hexString)) {
            return null;
        }

        hexString = hexString.replaceAll(" ", "");
        int len = hexString.length();
        int index = 0;

        byte[] bytes = new byte[len / 2];

        while (index < len) {
            String sub = hexString.substring(index, index + 2);
            bytes[index / 2] = (byte) Integer.parseInt(sub, 16);
            index += 2;
        }

        return bytes;
    }

    /**
     * 数组转换成十六进制字符串
     *
     * @return HexString
     */
    public static final String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
            // 在这里故意追加一个逗号便于最后的区分
            sb.append(" ");
        }
        return sb.toString();
    }
    public synchronized String sendCmd(String ip,int port ,String cmd) {

        try {
            boolean isLock = SocketLock.isLock(ip);
            while (isLock) {
                //等待其正在通讯的线程结束
                try {
                    this.wait(2);
                    isLock = SocketLock.isLock(ip);
                } catch (InterruptedException e) {
                    LOGGER.error("等待其他socket通讯通讯线程结束失败", e);
                }
            }
            String result= SendSocket(ip, port,cmd);
            return result;
        } catch (Exception e) {
            SocketLock.loseLock(ip);
            LOGGER.error("失败");
        }finally {
            //释放socket通讯锁
            SocketLock.loseLock(ip);


        }
        return "";
    }

    private String SendSocket(String ip, int port,String cmd) {
        Socket tempSocket = null;
        InputStream in =null;
        OutputStream out = null;
        try {
            tempSocket = new Socket(ip, port);
            tempSocket.setSoTimeout(Constants.SOCKET_OUT_TIME * 1000);
            in = tempSocket.getInputStream();
            out = tempSocket.getOutputStream();

            byte[] hexStrToByteArrs = hexStrToByteArrs(cmd);
            if (hexStrToByteArrs == null) {
                return "error";
            }
            out.write(hexStrToByteArrs);
            out.flush();
            // in.read得到应答
            byte[] buf = new byte[1024];
            //int c = in.read(buf);
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            tempSocket.close();
            SocketLock.loseLock(ip);
            return bytesToHexString(buf);
            /*
             *  对应答c进行处理
             */

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
                tempSocket.close();
                SocketLock.loseLock(ip);
            }catch (Exception e){

            }
        }
        return "";

    }
}
