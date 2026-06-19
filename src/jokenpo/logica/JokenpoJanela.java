package jokenpo.logica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class JokenpoJanela extends JFrame implements ActionListener {
    private JPanel painelPrincipal; //painel principal
    private JButton jButtonPedra;
    private JButton jButtonPapel;
    private JButton jButtonTesoura;
    private Jokenpo cliente;
    
    public JokenpoJanela() throws Exception{
        cliente = new Jokenpo();
        
        //JANELA PRINCIPAL
        //setando a janela
        setTitle("Jokenpo"); //nome da janela
        setSize(960, 540); //tamanho da janela
        setLayout(null); //tipo de layout (posicionar componentes manualmente ou não)
        setResizable(false); //janela de tamanho fixo
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //quando fechada, encerra o programa           
        setLocationRelativeTo(null); //centraliza
        
        jButtonPedra = new JButton(); //cria o botão
        jButtonPedra.setText("Pedra");
        jButtonPedra.setBounds(100, 400, 240, 30);
        jButtonPedra.addActionListener(this); //adiciona evento ao clicar
        add(jButtonPedra);
        
        jButtonPapel = new JButton(); //cria o botão
        jButtonPapel.setText("Papel");
        jButtonPapel.setBounds(350, 400, 240, 30);
        jButtonPapel.addActionListener(this); //adiciona evento ao clicar
        add(jButtonPapel);
        
        jButtonTesoura = new JButton(); //cria o botão
        jButtonTesoura.setText("Tesoura");
        jButtonTesoura.setBounds(600, 400, 240, 30);
        jButtonTesoura.addActionListener(this); //adiciona evento ao clicar
        add(jButtonTesoura);
        
        setVisible(true); //deixa visível
        
    }
    
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        //ao clicar em um botão, pega a jogada
        String jogada = ae.getActionCommand();
        
        try{
            cliente.realizarJogada(jogada);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
        
}