package jokenpo.logica;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Config {
    
    private static String ip;
    private static int porta = 12345;
    
    // impede a criação de objetos da classe ConfigTXT
    private Config() {
    }
    
    private static void readConfig() throws UnknownHostException{
        
        ip = InetAddress.getLocalHost().getHostAddress();
        
    }

    public static String getIp() throws UnknownHostException {
        
        if(ip == null)readConfig();
        
        return ip;
    }
    
    public static int getPorta() throws UnknownHostException {
        
        if(porta == 12345)readConfig();
        
        return porta;
    }
        
}