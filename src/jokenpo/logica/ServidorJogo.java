package jokenpo.logica;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
    
    private final String relatorio = "relatorio.txt";
    
    
    // iniciar servidor
    public void iniciar() throws Exception {
        //toda vez que se inicia um jogo, limpa o relatório
        limparRelatorio();
        
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
    
    public void jogar(int numeroRodada) throws Exception{
        jogador1Jogada = (String) entradaJogador1.readObject();
        jogador2Jogada = (String) entradaJogador2.readObject();
        
        String resultadoRodada = ganhadorRodada();
        
        gravaResultadoRodada(numeroRodada, resultadoRodada, jogador1Jogada, jogador2Jogada);
        
        String statusPlacar = "PLACAR: Jogador 1 Pontos = " + jogador1Pontos + " | " + "Jogador 2 Pontos = " + jogador2Pontos;
        
        String mensagemFinal = "Rodada " + numeroRodada + ": " + resultadoRodada + "\n" + statusPlacar;
        
       
        if(numeroRodada == 3){
            String resultadoMD3;
            if(jogador1Pontos > jogador2Pontos){      
            resultadoMD3 = "FIM DE JOGO: O Jogador 1 é o Vencedor da MD3";
            }else if(jogador2Pontos > jogador1Pontos){
                resultadoMD3 = "FIM DE JOGO: O Jogador 2 é o Vencedor da MD3";
            }else{
                resultadoMD3 = "FIM DE JOGO: O Jogo terminou em empate!";
            }
            
            mensagemFinal += "\n" + resultadoMD3;
            mensagemFinal += lerRelatorio();
        }
   
        saidaJogador1.writeObject(mensagemFinal);
        saidaJogador1.flush();
        
        saidaJogador2.writeObject(mensagemFinal);
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
    
    
    private void limparRelatorio() {
        
        //sobrescreve o arquivo
        try (FileWriter fw = new FileWriter(relatorio, false)) { //tenta criar um novo ou sobrescrever
            fw.write(""); 
        } catch (IOException e) {
            System.err.println("Erro ao limpar o arquivo de relatório: " + e.getMessage());
        }
        
    }
    
    private void gravaResultadoRodada(int rodada, String resultado, String jogada1, String jogada2){
        
        try (FileWriter fw = new FileWriter(relatorio, true); BufferedWriter bw = new BufferedWriter(fw)) {
            
            bw.write("Rodada " + rodada + ":");
            bw.newLine();
            
            bw.write("Jogada do jogador 1: " + jogada1);
            bw.newLine();
            
            bw.write("Jogada do jogador 2: " + jogada2);
            bw.newLine();
            
            bw.write("Resultado: " + resultado);
            bw.newLine();
            
            bw.write("=====================");
            bw.newLine();
            
        } catch (IOException e) {
            System.err.println("Erro ao gravar o arquivo de relatório: " + e.getMessage());
        }
        
    }
    
    private String lerRelatorio(){
        
        StringBuilder textoRelatorio = new StringBuilder(); //string builder para conectar as strings umas às outras
        textoRelatorio.append("\n == RELATÓRIO DA MD3 == \n");
        
        
        try (BufferedReader br = new BufferedReader(new FileReader("relatorio.txt"))) {
            
            String linha;
            while((linha = br.readLine()) != null){ //percorre e lê todas as linhas do arquivo
                textoRelatorio.append(linha).append("\n");
            }
            
        } catch (IOException e) {
            textoRelatorio.append("Erro ao carregar o arquivo de relatório: ").append(e.getMessage());
        }
        
        textoRelatorio.append("================");
        return textoRelatorio.toString();
    }
    
}