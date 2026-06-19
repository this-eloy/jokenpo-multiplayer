package jokenpo.logica;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        //cria a janela da interface gráfica do jogo
        //JokenpoJanela jokenpo = new JokenpoJanela();
        
        Jokenpo jogo = new Jokenpo();

        // teste da thread
        Thread t1 = new Thread(new GerenciadorDeJogadas("Pedra", jogo, 1));
        Thread t2 = new Thread(new GerenciadorDeJogadas("Tesoura", jogo, 2));

        t1.start();
        t2.start();

        // O segredo está aqui: faça o main esperar um pouco 
        // para dar tempo das threads terminarem antes do programa fechar
        Thread.sleep(5000); 
        System.out.println("--- Jogo finalizado ---");
    }
}