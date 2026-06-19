package jokenpo.logica;

import java.util.List;

public class Jokenpo{
    private String jogador1Jogada;
    private String jogador2Jogada;
    private List<String> jogadas = List.of("Pedra", "Papel", "Tesoura");

    public Jokenpo() {
        iniciar();
    }
    
    // inicia com as jogadas zeradas
    public void iniciar(){
        jogador1Jogada = null;
        jogador2Jogada = null;
    }
     
    public synchronized void realizarJogada(int jogador, String jogada) throws InterruptedException {
        // verifica se o jogador é válido
        if(jogador != 1 && jogador != 2){
            throw new GameException("Jogador inválido! Escolha entre 1 ou 2.");
        }
        // verifica se o jogador escolheu uma jogada valida
        if(!jogadas.contains(jogada)){
            throw new GameException("Movimento inválido! Escolha entre Pedra, Papel ou Tesoura.");
        }
        // marca jogadas
        if(jogador == 1){
            jogador1Jogada = jogada;
        }else{
            jogador2Jogada = jogada;
        }        
        
        while( jogador1Jogada == null || jogador2Jogada == null){ // se um estiver jogando
            System.out.println("Jogador " + jogador + " escolheu. Aguardado escolha do outro jogador.");
            wait(); // faz a thread parar e esperar
        }
        
        notifyAll(); // aviso
    }
    
    // verifica se os dois jogaram
    public boolean jogaram(){
        return jogador1Jogada != null && jogador2Jogada != null;
    }
    
    public String ganhador(){
        if(!jogaram()){
            return null;
        }
        if(jogador1Jogada.equals(jogador2Jogada)){
            return "Empate!";
        }
        if((jogador1Jogada.equals("Pedra") && jogador2Jogada.equals("Tesoura")) ||
           (jogador1Jogada.equals("Papel") && jogador2Jogada.equals("Pedra")) ||
           (jogador1Jogada.equals("Tesoura") && jogador2Jogada.equals("Papel")) ){
            return "Jogador 1 ganhou!";
        }else{
            return "Jogador 2 ganhou!";
        }
    }
    
}
