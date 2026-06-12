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
    
    // verifica se o jogador escolheu uma jogada valida
    public void realizarJogada(String jogada){
        if(!jogadas.contains(jogada)){
            throw new GameException("Movimento inválido!");
        }
    }
}
