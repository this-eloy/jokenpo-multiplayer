package jokenpo.logica;

import java.net.UnknownHostException;

public class Config {
    
    private static String ip;
    private static int porta = 1606;
    
    // impede a criação de objetos da classe Config
    private Config() {
    }
    
    private static void readConfig() throws UnknownHostException{   
        ip = "0.0.0.0";
    }

    public static String getIp() throws UnknownHostException {   
        if(ip == null)readConfig();
        
        return ip;
    }
    
    public static int getPorta() throws UnknownHostException {
        return porta;
    }
        
}