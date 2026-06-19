package jokenpo.logica;

import javax.swing.SwingUtilities;

public class MainCliente {
    public static void main(String[] args) throws InterruptedException {
        SwingUtilities.invokeLater(() ->{
            try{
                JokenpoJanela janela = new JokenpoJanela();
            }catch (Exception e){
                System.out.println("Erro ao iniciar a janela do jogador " + e.getMessage());
                e.printStackTrace();
            }
        });
        
    }
}