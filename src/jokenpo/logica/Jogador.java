package jokenpo.logica;

public class Jogador implements Runnable{

    private Jokenpo jogo; // area de memoria compartilhada
    private int id;
    private String jogada;

    public Jogador(Jokenpo jogo, int id, String jogada) { 
        this.jogo = jogo;
        this.id = id;
        
    }
    
    @Override
    public void run(){
        
    }
}
