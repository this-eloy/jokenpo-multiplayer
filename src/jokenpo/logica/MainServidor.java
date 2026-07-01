package jokenpo.logica;

public class MainServidor {
    public static void main(String[] args) throws Exception {
        
        ServidorJogo servidor = new ServidorJogo();
        
        servidor.iniciar();
        servidor.conectar(); // esperar a conexão de 2 jogadores
        
        while(true) {
            
            System.out.println("Partida iniciada! Melhor de 3 rodadas.");
        
            // 3 primeiras rodadas
            for( int i = 1; i <=3; i++){
                System.out.println("Iniciando rodada número: " + i);
                servidor.jogar(i);
            }
            
            boolean reiniciar = servidor.processarDecisaoJogadores();
            
            if(!reiniciar){
                System.out.println("Um jogador escolheu sair do jogo.");
                break;
            }
        }
        System.out.println("Fim do campeonato.");
    }
}