package jokenpo.logica;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class Jokenpo{
    private Socket socket;
    private ObjectOutputStream saida;
    private ObjectInputStream entrada;
    private List<String> jogadas = List.of("Pedra", "Papel", "Tesoura");

    public Jokenpo() throws Exception{
        iniciar();
    }
    
    // inicia com as jogadas zeradas
    public void iniciar() throws Exception{
        socket = new Socket(Config.getIp(), Config.getPorta());
        entrada = new ObjectInputStream(socket.getInputStream());
        saida = new ObjectOutputStream(socket.getOutputStream());
    }
     
    public void realizarJogada(String jogada) throws Exception {
        
        // verifica se o jogador escolheu uma jogada valida
        if(!jogadas.contains(jogada)){
            throw new GameException("Movimento inválido! Escolha entre Pedra, Papel ou Tesoura.");
        }
        
        // marca jogadas
        saida.writeObject(jogada);
        saida.flush();     
        
    }
    
}
