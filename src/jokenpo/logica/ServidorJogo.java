package jokenpo.logica;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class ServidorJogo {
    
    private ServerSocket servidor;
    
    private Socket jogador1;
    private ObjectOutputStream saidaJogador1;
    private ObjectInputStream entradaJogador1;
            
    private Socket jogador2;
    private ObjectOutputStream saidaJogador2;
    private ObjectInputStream entradaJogador2;
    
    private String jogador1Jogada;
    private String jogador2Jogada;
        
    private int jogador1Pontos = 0;
    private int jogador2Pontos = 0;
    
    // iniciar servidor
    public void iniciar() throws Exception {
        // usa a classe Config para pegar a porta e ip
        servidor = new ServerSocket( Config.getPorta(), 2, InetAddress.getByName( Config.getIp() ) );
        System.out.println("Servidor Jokenpo Inicializado ( " + servidor + " ).\n");
        
    }
    
    // conectar jogadores
    public void conectar() throws Exception {
        
        System.out.println( "Esperando por Conexão (Jogador 1)." );
        jogador1 =  servidor.accept();
        System.out.println( "Conexão Recebida: " + jogador1.toString() + ":" + jogador1.getPort() + "\n" );
        
        saidaJogador1 = new ObjectOutputStream( jogador1.getOutputStream() );
        entradaJogador1 = new ObjectInputStream( jogador1.getInputStream() );
        saidaJogador1.flush();        
        saidaJogador1.writeObject("1;true");
        
        System.out.println( "Esperando por Conexão (Jogador 2)." );
        jogador2 =  servidor.accept();
        System.out.println( "Conexão Recebida: " + jogador2.toString() + ":" + jogador2.getPort() + "\n" );
        
        saidaJogador2 = new ObjectOutputStream( jogador2.getOutputStream() );
        entradaJogador2 = new ObjectInputStream( jogador2.getInputStream() );
        saidaJogador2.flush();        
        saidaJogador2.writeObject("2;true");
        
    }
    
    public void jogar() throws Exception{
        jogador1Jogada = (String) entradaJogador1.readObject();
        jogador2Jogada = (String) entradaJogador2.readObject();
        
        String resultadoRodada = ganhadorRodada();
        
        String statusPlacar = "PLACAR: Jogador 1 = " + jogador1Pontos + "|" + "Jogador 2 = " + jogador2Pontos;
        
        saidaJogador1.writeObject("Resultado da rodada: " + resultadoRodada + statusPlacar );
        saidaJogador1.flush();
        
        saidaJogador2.writeObject("Resultado da rodada: " + resultadoRodada + statusPlacar);
        saidaJogador2.flush();
    }
    
        
    public String ganhadorRodada(){
//        if(!jogaram()){
//            return null;
//        }
        if(jogador1Jogada.equals(jogador2Jogada)){
            return "Empate!";
        }
        if((jogador1Jogada.equals("Pedra") && jogador2Jogada.equals("Tesoura")) ||
           (jogador1Jogada.equals("Papel") && jogador2Jogada.equals("Pedra")) ||
           (jogador1Jogada.equals("Tesoura") && jogador2Jogada.equals("Papel")) ){
            jogador1Pontos++;
            return "Jogador 1 ganhou!";
            
        }else{
            jogador2Pontos++;
            return "Jogador 2 ganhou!";
        }
    }
    
    public void ganhadorMD3() throws Exception{
        String mensagem;
        
        if(jogador1Pontos > jogador2Pontos){
            mensagem = "FIM DE JOGO: O Jogador 1 é o Vencedor da MD3";
        }else if(jogador2Pontos > jogador1Pontos){
            mensagem = "FIM DE JOGO: O Jogador 2 é o Vencedor da MD3";
        }else{
            mensagem = "FIM DE JOGO: O Jogo terminou em empate!";
        }
        
        saidaJogador1.writeObject(mensagem);
        saidaJogador1.flush();
        
        saidaJogador2.writeObject(mensagem);
        saidaJogador2.flush();
    }
    
}