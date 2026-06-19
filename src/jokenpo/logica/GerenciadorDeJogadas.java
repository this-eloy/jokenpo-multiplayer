package jokenpo.logica;

public class GerenciadorDeJogadas implements Runnable {
    private String jogada;
    private Jokenpo jogo;
    private int id;

    public GerenciadorDeJogadas(String jogada, Jokenpo jogo, int id) {
        this.jogada = jogada;
        this.jogo = jogo;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            // tempo
            if (id == 2) Thread.sleep(1000); 
            
            // chama Jokenpo
            jogo.realizarJogada(id, jogada);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}