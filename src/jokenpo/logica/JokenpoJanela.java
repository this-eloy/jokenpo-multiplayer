package jokenpo.logica;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class JokenpoJanela extends JFrame implements ActionListener {
    private JPanel painelPrincipal; //painel principal
    private JButton jButtonPedra;
    private JButton jButtonPapel;
    private JButton jButtonTesoura;
    private Jokenpo cliente;
    private JLabel jLabelFundo;
    
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
        
        ImageIcon icone = new ImageIcon("assets/titulo.png");
        Image imgRedimensionada = icone.getImage().getScaledInstance(500, 96, Image.SCALE_SMOOTH);
        ImageIcon iconeFinal = new ImageIcon(imgRedimensionada);
        JLabel jLabelIcone = new JLabel(iconeFinal);
        jLabelIcone.setBounds(130, 100, 700, 135);
        add(jLabelIcone);
        
        ImageIcon iconePedra = new ImageIcon("assets/pedra.png");
        Image imgPedra = iconePedra.getImage().getScaledInstance(240, 51, Image.SCALE_SMOOTH);
        ImageIcon pedraBotao = new ImageIcon(imgPedra);
        
        jButtonPedra = new JButton("Pedra"); //cria o botão
        jButtonPedra.setIcon(pedraBotao); 
        jButtonPedra.setBounds(350, 261, 240, 51);
        jButtonPedra.addActionListener(this); //adiciona evento ao clicar
     
        jButtonPedra.setBorderPainted(false);   
        jButtonPedra.setContentAreaFilled(false); 
        jButtonPedra.setFocusPainted(false);     
        jButtonPedra.setOpaque(false);
        add(jButtonPedra);
        
        
        ImageIcon iconePapel = new ImageIcon("assets/papel.png");
        Image imgPapel = iconePapel.getImage().getScaledInstance(240, 51, Image.SCALE_SMOOTH);
        ImageIcon papelBotao = new ImageIcon(imgPapel);
        
        jButtonPapel = new JButton("Papel"); //cria o botão
        jButtonPapel.setIcon(papelBotao); 
        jButtonPapel.setBounds(350, 321, 240, 51);
        jButtonPapel.addActionListener(this); //adiciona evento ao clicar
        jButtonPapel.setBorderPainted(false);   
        jButtonPapel.setContentAreaFilled(false); 
        jButtonPapel.setFocusPainted(false);     
        add(jButtonPapel);
        
        
        ImageIcon iconeTesoura = new ImageIcon("assets/tesoura.png");
        Image imgTesoura = iconeTesoura.getImage().getScaledInstance(240, 51, Image.SCALE_SMOOTH);
        ImageIcon tesouraBotao = new ImageIcon(imgTesoura);
        
        jButtonTesoura = new JButton("Tesoura"); //cria o botão
        jButtonTesoura.setIcon(tesouraBotao); 
        jButtonTesoura.setBounds(350, 381, 240, 51);
        jButtonTesoura.addActionListener(this); //adiciona evento ao clicar
        jButtonTesoura.setBorderPainted(false);   
        jButtonTesoura.setContentAreaFilled(false); 
        jButtonTesoura.setFocusPainted(false);     
        add(jButtonTesoura);
        
        ImageIcon imagemFundo = new ImageIcon("assets/wallpaper.png");
        Image imagemRedimensionada = imagemFundo.getImage().getScaledInstance(960, 540, Image.SCALE_SMOOTH);
        jLabelFundo = new JLabel(new ImageIcon(imagemRedimensionada));
        jLabelFundo.setBounds(0, 0, 960, 540);
        add(jLabelFundo);
        
        setVisible(true); //deixa visível
        
    }
    
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        //ao clicar em um botão, pega a jogada
        String jogada = ae.getActionCommand();
        
        // impede que o jogador clique 2 vzs na msm jogada
        jButtonPedra.setEnabled(false);
        jButtonPapel.setEnabled(false);
        jButtonTesoura.setEnabled(false);
        
        new Thread(() -> {
            try{
                // se escolher reiniciar
                if( jogada.equals("Jogar Novamente")){
                    cliente.realizarJogada("REINICIAR");
                    
                    // pega o resultado do servidor;;
                    String confirmacao = cliente.obterResultadoServidor();
                    
                    if(confirmacao.trim().contains("JOGO REINICIADO")){
                        
                        SwingUtilities.invokeLater( () -> {
                        // volta os botoes originais
                            Image imgPedra = new ImageIcon("assets/pedra.png").getImage().getScaledInstance(240, 51, Image.SCALE_SMOOTH);
                            Image imgPapel = new ImageIcon("assets/papel.png").getImage().getScaledInstance(240, 51, Image.SCALE_SMOOTH);
                            Image imgTesoura = new ImageIcon("assets/tesoura.png").getImage().getScaledInstance(240, 51, Image.SCALE_SMOOTH);

                            jButtonPedra.setIcon(null);
                            jButtonPapel.setIcon(null);
                            jButtonTesoura.setIcon(null);
                            
                            jButtonPedra.setIcon(new ImageIcon(imgPedra));
                            jButtonPapel.setIcon(new ImageIcon(imgPapel));
                            jButtonTesoura.setIcon(new ImageIcon(imgTesoura));

                            // volta os comandos do jogo
                            jButtonPedra.setActionCommand("Pedra");
                            jButtonPapel.setActionCommand("Papel");
                            jButtonTesoura.setActionCommand("Tesoura");

                            jButtonTesoura.setVisible(true);
                            jButtonTesoura.setEnabled(true);
                            
                            // ativa os botoes para as rodadas
                            jButtonPedra.setEnabled(true);
                            jButtonPapel.setEnabled(true);
                            
                            
                            repaint();
                        });
                    };
                
                // se escolher sair do jogo
                } else if(jogada.equals("Sair do Jogo")){
                    cliente.realizarJogada("SAIR");
                    System.exit(0);
                    
                // jogo normal    
                } else {
                    
                    cliente.realizarJogada(jogada);
                    
                    // resultado final do jogo
                    String resultado = cliente.obterResultadoServidor();
                    
                    // TESTE
                    System.out.println("servidor: " + resultado);
                    
                    // se for fimde jogo
                    SwingUtilities.invokeLater(() -> {
                        if(resultado.contains("FIM DE JOGO")){
                            try{
                                Image imgReiniciar = new ImageIcon("assets/reiniciar.png").getImage().getScaledInstance(240, 51, Image.SCALE_SMOOTH);
                                Image imgSair = new ImageIcon("assets/sair.png").getImage().getScaledInstance(240, 51, Image.SCALE_SMOOTH);
                            
                                // coloca as imagens de reiniciar e sair por ciam
                                jButtonPedra.setIcon(new ImageIcon(imgReiniciar));
                                jButtonPapel.setIcon(new ImageIcon(imgSair));
                                
                                jButtonPedra.setActionCommand("Jogar Novamente");
                                jButtonPapel.setActionCommand("Sair do Jogo");
                                
                                jButtonTesoura.setVisible(false); // tira o botao
                            } catch (Exception e) {
                                System.out.println("Erro ao acrregar as imagens dos novos botões" + e.getMessage());
                            }
                        }
                        
                        jButtonPedra.setEnabled(true);
                        jButtonPapel.setEnabled(true);
                        
                        if(!resultado.contains("FIM DE JOGO")){
                            jButtonTesoura.setEnabled(true);
                        }
                        
                        getContentPane().repaint();
                    });
                }
                
            }catch(Exception ex){
                ex.printStackTrace();
                
                SwingUtilities.invokeLater(()->{
                    jButtonPedra.setEnabled(true);
                    jButtonPapel.setEnabled(true);
                    jButtonTesoura.setEnabled(true);
                });   
            }
        }).start();
        
    }
        
}