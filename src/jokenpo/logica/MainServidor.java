package jokenpo.logica;

public class MainServidor {
    public static void main(String[] args) throws Exception {
        
        ServidorJogo servidor = new ServidorJogo();
        
        servidor.iniciar();
        servidor.conectar(); // esperar a conexão de 2 jogadores
        System.out.println("Partida iniciada! Melhor de 3 rodadas.");
        
        for( int i = 1; i <=3; i++){
            System.out.println("Iniciando rodado número: " + i);
            servidor.jogar();
        }
        
        System.out.println("Calculando vencedor da MD3...");
        servidor.ganhadorMD3();
        
        System.out.println("Fim do campeonato. Servidor finalizado.");
        
    }
}