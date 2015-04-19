package Jogos;

import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.swing.JOptionPane;
import java.net.Socket;
import java.net.ServerSocket;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
import java.io.*;
import java.lang.Thread;
/**
 *
 * @author Lucas
 */

public class JogoDaVelha extends javax.swing.JFrame{
    boolean Jogador1Ativado = true;         //Responsável por marcar se o jogador 1 está com a vez de jogar
    boolean Jogador2Ativado = false;        //Responsável por marcar se o jogador 2 está com a vez de jogar
    //Socket socket = new Socket();           //Socket de Comunicação para o serverSocket 
    public Socket cliente = new Socket();                         //Socket para a conexão com o outro jogo, como cliente
    public ServerSocket serverSocket;              //serverSocket para aguardar conexão do outro parceiro
    public Scanner leia = new Scanner(System.in);  //Objeto responsável por ler as mensagens recebidas
    public BufferedReader entrada;                 //Objeto responbsável por receber e armazenar em buffer, os bytes recebidos das mensagens recebidas
    public PrintStream saida;                      //Objeto responsável pelo envio das informações através do socket
    
    
    
    int NumeroDeVitoriaDoJogador1 = 0;
    int NumeroDeVitoriaDoJogador2 = 0;
    int NumeroDeEmpates = 0;
    
            
    public JogoDaVelha() {
        initComponents();
    }
    //Métodos relacionados Às conexões com o outro jogador.
    //O método abaixo deixa o 
    public void abreConexao(int porta){
        new Thread(){
            @Override
            public void run(){
                try {
                    serverSocket = new ServerSocket(porta);
                    cliente = serverSocket.accept();
                    entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                    jLabelStatus.setText("Status da Conexão: Conectado!");
                    
                } catch (IOException ex) {
                    if(!RdLocal.isSelected()){
                        JOptionPane.showMessageDialog(null, "Não foi possível abrir a Conexão\nerro:" + ex.toString());
                    }
                }
            }
        }.start();
        
    }
    public void fechaConexao(){
        try {
            cliente.close();
            serverSocket.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar fechar Conexão!!!");
        }
    }
    public void conecta(String endereco, int porta){
        try {
            cliente = new Socket(endereco, porta);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Não foi Possível Conectar com o parceiro: " + endereco + "\nErro: " + ex.getMessage());
        }
    }
    
    public void enviaMensagem(String mensagem){
        try{
            saida = new PrintStream(cliente.getOutputStream());
            saida.println(mensagem);
        }catch(IOException ex){
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao enviar a mensagem!");
        }
    }
    
    public void recebeMensagem(){
        Thread teste;
        teste = new Thread(){
            public void run(){
                try {
                    boolean saida = true;
                    String mensagemRecebida = "";
                    //O ciclo de repetição abaixo fica rodando até que o cliente seja conectado, e só cria o buffer quando isso acontece
                    do{
                        //A verificação abaixo testa se o Socket está conectado e se o RdLocal não está selecionado, e só cria o buffer de entrada, caso essas duas condições sejam verdadeiras
                        if(cliente.isConnected() && !RdLocal.isSelected()){
                            entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                            saida = false;
                            //A verificação abaixo força a saida do loop caso seja marcado o balão tipo de jogo local
                        }else if(RdLocal.isSelected()){
                            saida = false;
                        }
                    }while(saida);
                    //As instruções abaixo recebem e tratam as mensagens recebidas através do socket
                    while(cliente.isConnected()){
                        mensagemRecebida = entrada.readLine();                        
                        
                        if(mensagemRecebida.contains(":")){
                            awtAreaDeTextoMensagem.setText(awtAreaDeTextoMensagem.getText() + mensagemRecebida + "\n");
                            
                        }else{
                            if(mensagemRecebida.contains("B1")){
                                B1.setText(mensagemRecebida.substring(3));
                                JogadorAtivo();
                                
                           }else if(mensagemRecebida.contains("B2")){
                               B2.setText(mensagemRecebida.substring(3));
                                JogadorAtivo();
                                
                           }else if(mensagemRecebida.contains("B3")){
                               B3.setText(mensagemRecebida.substring(3));
                                JogadorAtivo();

                           }else if(mensagemRecebida.contains("B4")){
                               B4.setText(mensagemRecebida.substring(3));
                               JogadorAtivo();

                           }else if(mensagemRecebida.contains("B5")){
                               B5.setText(mensagemRecebida.substring(3));
                               JogadorAtivo();

                           }else if(mensagemRecebida.contains("B6")){
                               B6.setText(mensagemRecebida.substring(3));
                               JogadorAtivo();

                           }else if(mensagemRecebida.contains("B7")){
                               B7.setText(mensagemRecebida.substring(3));
                               JogadorAtivo();
                               
                           }else if(mensagemRecebida.contains("B8")){
                               B8.setText(mensagemRecebida.substring(3));
                               JogadorAtivo();

                           }else if(mensagemRecebida.contains("B9")){
                               B9.setText(mensagemRecebida.substring(3));
                               JogadorAtivo();
                           }
                        }
                    }
                } catch (IOException ex) {
                    System.out.printf(ex.getMessage());
                }
            }};
        teste.start();
    }
        
    
    public void JogadorAtivo(){
        if (Jogador1Ativado == true){
            Jogador1Ativado = false;
            Jogador2Ativado = true;
        }else{
            Jogador1Ativado = true;
            Jogador2Ativado = false;
        }
        JogadorVencedor("X");
        JogadorVencedor("O");
    }
    
    public void JogadorVencedor(String Jogador){
        if (B1.getText().equals(Jogador) && B2.getText().equals(Jogador) && B3.getText().equals(Jogador)){
            if (B1.getText().equals("X")) 
                Vencedor("Jogador 1");
            else
                Vencedor("Jogador 2");
        }
        if (B4.getText().equals(Jogador) && B5.getText().equals(Jogador) && B6.getText().equals(Jogador)){
            if (B4.getText().equals("X")) 
                Vencedor("Jogador 1");
            else
                Vencedor("Jogador 2");
        }
        if (B7.getText().equals(Jogador) && B8.getText().equals(Jogador) && B9.getText().equals(Jogador)){
            if (B7.getText().equals("X")) 
                Vencedor("Jogador 1");
            else
                Vencedor("Jogador 2");
        }
        if (B1.getText().equals(Jogador) && B5.getText().equals(Jogador) && B9.getText().equals(Jogador)){
            if (B1.getText().equals("X")) 
                Vencedor("Jogador 1");
            else
                Vencedor("Jogador 2");
        }
        if (B3.getText().equals(Jogador) && B5.getText().equals(Jogador) && B7.getText().equals(Jogador)){
            if (B3.getText().equals("X")) 
                Vencedor("Jogador 1");
            else
                Vencedor("Jogador 2");
        }
        if (B1.getText().equals(Jogador) && B4.getText().equals(Jogador) && B7.getText().equals(Jogador)){
            if (B1.getText().equals("X")) 
                Vencedor("Jogador 1");
            else
                Vencedor("Jogador 2");
        }
        if (B2.getText().equals(Jogador) && B5.getText().equals(Jogador) && B8.getText().equals(Jogador)){
            if (B2.getText().equals("X")) 
                Vencedor("Jogador 1");
            else
                Vencedor("Jogador 2");
        }
        if (B3.getText().equals(Jogador) && B6.getText().equals(Jogador) && B9.getText().equals(Jogador)){
            if (B3.getText().equals("X")) 
                Vencedor("Jogador 1");
            else
                Vencedor("Jogador 2");
        }
        
        if(!B1.getText().equals("") &&
           !B2.getText().equals("") &&
           !B3.getText().equals("") &&
           !B4.getText().equals("") &&
           !B5.getText().equals("") &&
           !B6.getText().equals("") &&
           !B7.getText().equals("") &&
           !B8.getText().equals("") &&
           !B9.getText().equals("")){
            
            Vencedor("Empate");
        }
    }
    
    public void Vencedor(String JogadorVencedor){
        if (JogadorVencedor.equals("Jogador 1")){
            JOptionPane.showMessageDialog(JogoDaVelha.this, "Parabéns jogador 1. Você venceu o Jogo!");
            NumeroDeVitoriaDoJogador1++;
            NumeroDeVitoriasDoJogador1.setText("Número de Vitórias: " + NumeroDeVitoriaDoJogador1);
            LimparCampos();
        }
        if (JogadorVencedor.equals("Jogador 2")){
            JOptionPane.showMessageDialog(JogoDaVelha.this, "Parabéns jogador 2. Você venceu o Jogo!");
            NumeroDeVitoriaDoJogador2++;
            NumeroDeVitoriasDoJogador2.setText("Número de Vitórias: " + NumeroDeVitoriaDoJogador2);
            LimparCampos();
        }
        if (JogadorVencedor.equals("Empate")){
            JOptionPane.showMessageDialog(JogoDaVelha.this, "O jogo terminou Empatado! Joguem Novamente");
            NumeroDeEmpates++;
            NumeroEmpates.setText("Número de Empates: " + NumeroDeEmpates);
            LimparCampos();
        }
    }
    
    public void LimparCampos(){
        B1.setText("");
        B2.setText("");
        B3.setText("");
        B4.setText("");
        B5.setText("");
        B6.setText("");
        B7.setText("");
        B8.setText("");
        B9.setText("");
        
        Jogador1Ativado = true;
        Jogador2Ativado = false;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        B1 = new javax.swing.JButton();
        B2 = new javax.swing.JButton();
        B3 = new javax.swing.JButton();
        B4 = new javax.swing.JButton();
        B5 = new javax.swing.JButton();
        B6 = new javax.swing.JButton();
        B7 = new javax.swing.JButton();
        B8 = new javax.swing.JButton();
        B9 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabelJogador1 = new javax.swing.JLabel();
        NumeroDeVitoriasDoJogador1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabelJogador2 = new javax.swing.JLabel();
        NumeroDeVitoriasDoJogador2 = new javax.swing.JLabel();
        NumeroEmpates = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        BtNovoJogo = new javax.swing.JButton();
        BtSobreJogo = new javax.swing.JButton();
        BtSairDoJogo = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        RdLocal = new javax.swing.JRadioButton();
        RdRemoto = new javax.swing.JRadioButton();
        jLabelEnderecoParceiro = new javax.swing.JLabel();
        CampoEndereçoParceiro = new javax.swing.JTextField();
        jBConectar = new javax.swing.JButton();
        jLabelNomeJogador = new javax.swing.JLabel();
        CampoNomeJogador = new javax.swing.JTextField();
        jLabelStatus = new javax.swing.JLabel();
        CampoDeTextoMensagem = new javax.swing.JTextField();
        BtEnviarMensagem = new javax.swing.JButton();
        awtAreaDeTextoMensagem = new java.awt.TextArea();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Jogo da Velha");
        setBackground(new java.awt.Color(255, 0, 0));
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Campo de Jogo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        B1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        B1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B1ActionPerformed(evt);
            }
        });

        B2.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        B2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B2ActionPerformed(evt);
            }
        });

        B3.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        B3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B3ActionPerformed(evt);
            }
        });

        B4.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        B4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B4ActionPerformed(evt);
            }
        });

        B5.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        B5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B5ActionPerformed(evt);
            }
        });

        B6.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        B6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B6ActionPerformed(evt);
            }
        });

        B7.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        B7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B7ActionPerformed(evt);
            }
        });

        B8.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        B8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B8ActionPerformed(evt);
            }
        });

        B9.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        B9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(B1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(B4, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(B7, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(B5, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(B2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(B8, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(B6, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(B3, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(B9, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(B1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(B3, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(B2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(B4, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(B6, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(B5, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(B7, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(B9, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(B8, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informações do Jogo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jLabelJogador1.setText("Jogador 1      Símbolo: X");

        NumeroDeVitoriasDoJogador1.setText("Número de Vitórias: 0");

        jLabelJogador2.setText("Jogador 2      Símbolo: O");

        NumeroDeVitoriasDoJogador2.setText("Número de Vitórias: 0");

        NumeroEmpates.setText("Número de Empates: 0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelJogador1)
                            .addComponent(NumeroDeVitoriasDoJogador1)
                            .addComponent(jLabelJogador2)
                            .addComponent(NumeroDeVitoriasDoJogador2))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(NumeroEmpates)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabelJogador1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NumeroDeVitoriasDoJogador1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelJogador2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NumeroDeVitoriasDoJogador2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NumeroEmpates)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Controle do Jogo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        jPanel3.setToolTipText("");

        BtNovoJogo.setText("Novo Jogo");
        BtNovoJogo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtNovoJogoActionPerformed(evt);
            }
        });

        BtSobreJogo.setText("Sobre...");
        BtSobreJogo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSobreJogoActionPerformed(evt);
            }
        });

        BtSairDoJogo.setText("Sair do Jogo");
        BtSairDoJogo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSairDoJogoActionPerformed(evt);
            }
        });

        jLabel2.setText("Categoria do Jogo:");

        RdLocal.setSelected(true);
        RdLocal.setText("Local");
        RdLocal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RdLocalActionPerformed(evt);
            }
        });

        RdRemoto.setText("Remoto");
        RdRemoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RdRemotoActionPerformed(evt);
            }
        });

        jLabelEnderecoParceiro.setText("Endereço do Parceiro:");

        CampoEndereçoParceiro.setEnabled(false);
        CampoEndereçoParceiro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CampoEndereçoParceiroActionPerformed(evt);
            }
        });
        CampoEndereçoParceiro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CampoEndereçoParceiroKeyPressed(evt);
            }
        });

        jBConectar.setText("Conectar");
        jBConectar.setEnabled(false);
        jBConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBConectarActionPerformed(evt);
            }
        });

        jLabelNomeJogador.setText("Digite o Seu Nome: ");

        CampoNomeJogador.setEnabled(false);
        CampoNomeJogador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CampoNomeJogadorActionPerformed(evt);
            }
        });
        CampoNomeJogador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CampoNomeJogadorKeyPressed(evt);
            }
        });

        jLabelStatus.setText("Status da Conexão: ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(BtNovoJogo, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(BtSairDoJogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(16, 16, 16)
                                .addComponent(RdLocal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(RdRemoto)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabelEnderecoParceiro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CampoEndereçoParceiro))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabelStatus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBConectar))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabelNomeJogador)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CampoNomeJogador))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(BtSobreJogo))))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {BtNovoJogo, BtSairDoJogo, BtSobreJogo});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtNovoJogo)
                    .addComponent(BtSairDoJogo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(RdLocal)
                    .addComponent(RdRemoto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelEnderecoParceiro)
                    .addComponent(CampoEndereçoParceiro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelNomeJogador)
                    .addComponent(CampoNomeJogador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBConectar)
                    .addComponent(jLabelStatus))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                .addComponent(BtSobreJogo)
                .addContainerGap())
        );

        CampoDeTextoMensagem.setEnabled(false);
        CampoDeTextoMensagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CampoDeTextoMensagemActionPerformed(evt);
            }
        });
        CampoDeTextoMensagem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CampoDeTextoMensagemKeyPressed(evt);
            }
        });

        BtEnviarMensagem.setText("Enviar");
        BtEnviarMensagem.setEnabled(false);
        BtEnviarMensagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtEnviarMensagemActionPerformed(evt);
            }
        });

        awtAreaDeTextoMensagem.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        awtAreaDeTextoMensagem.setEditable(false);
        awtAreaDeTextoMensagem.setEnabled(false);
        awtAreaDeTextoMensagem.setName(""); // NOI18N

        jLabel1.setText("Troca de Mensagens:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(awtAreaDeTextoMensagem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(CampoDeTextoMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BtEnviarMensagem))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(awtAreaDeTextoMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(CampoDeTextoMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(BtEnviarMensagem))
                                .addContainerGap())))))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {BtEnviarMensagem, CampoDeTextoMensagem});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void B2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B2ActionPerformed
        if (Jogador1Ativado == true){
            if (B2.getText().equals("")){
                B2.setText("X");
                JogadorAtivo();
                if(RdRemoto.isSelected())enviaMensagem("B2 X");
            }
        }else{
            if (B2.getText().equals("")){
                B2.setText("O");
                JogadorAtivo();
                if(RdRemoto.isSelected())enviaMensagem("B2 O");
            }
        }
    }//GEN-LAST:event_B2ActionPerformed

    private void B3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B3ActionPerformed
        if (Jogador1Ativado == true){
            if (B3.getText().equals("")){
                B3.setText("X");
                JogadorAtivo();
                if(RdRemoto.isSelected())enviaMensagem("B3 X");
            }
        }else{
            if (B3.getText().equals("")){
                B3.setText("O");
                JogadorAtivo();
                if(RdRemoto.isSelected())enviaMensagem("B3 O");
            }
        }
    }//GEN-LAST:event_B3ActionPerformed

    private void B5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B5ActionPerformed
        if (Jogador1Ativado == true){
            if (B5.getText().equals("")){
                B5.setText("X");
                JogadorAtivo();
                if(RdRemoto.isSelected())enviaMensagem("B5 X");
            }
        }else{
            if (B5.getText().equals("")){
                B5.setText("O");
                JogadorAtivo();
                if(RdRemoto.isSelected())enviaMensagem("B5 O");
            }
        }
    }//GEN-LAST:event_B5ActionPerformed

    private void B6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B6ActionPerformed
        if (Jogador1Ativado == true){
            if (B6.getText().equals("")){
                B6.setText("X");
                JogadorAtivo();
                if(RdRemoto.isSelected())enviaMensagem("B6 X");
            }
        }else{
            if (B6.getText().equals("")){
                B6.setText("O");
                JogadorAtivo();
                if(RdRemoto.isSelected())enviaMensagem("B6 O");
            }
        }
    }//GEN-LAST:event_B6ActionPerformed

    private void B8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B8ActionPerformed
        if (Jogador1Ativado == true){
            if (B8.getText().equals("")){
                B8.setText("X");
                JogadorAtivo();
                if(RdRemoto.isSelected())enviaMensagem("B8 X");
            }
        }else{
            if (B8.getText().equals("")){
                B8.setText("O");
                JogadorAtivo();
                if(RdRemoto.isSelected())enviaMensagem("B8 O");
            }
        }
    }//GEN-LAST:event_B8ActionPerformed

    private void B9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B9ActionPerformed
        if (Jogador1Ativado == true){
            if (B9.getText().equals("")){
                B9.setText("X");
                JogadorAtivo();
                if(RdRemoto.isSelected())enviaMensagem("B9 X");
            }
        }else{
            if (B9.getText().equals("")){
                B9.setText("O");
                JogadorAtivo();
                if(RdRemoto.isSelected())enviaMensagem("B9 O");
            }
        }
    }//GEN-LAST:event_B9ActionPerformed

    private void B4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B4ActionPerformed
        if (Jogador1Ativado == true){
            if (B4.getText().equals("")){
                B4.setText("X");
                JogadorAtivo();
                if(RdRemoto.isSelected())enviaMensagem("B4 X");
            }
        }else{
            if (B4.getText().equals("")){
                B4.setText("O");
                JogadorAtivo();
                if(RdRemoto.isSelected())enviaMensagem("B4 O");
            }
        }
    }//GEN-LAST:event_B4ActionPerformed

    private void B1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B1ActionPerformed
        if (Jogador1Ativado == true){
            if (B1.getText().equals("")){
                B1.setText("X");
                JogadorAtivo();
                //A verificação abaixo é para que a posição da jogada seja enviada para o outro jogo, caso RdRemoto esteja selecionada
                //A mensagem é usada pelo método recebeMensagem para marcar a jogada ou mostrar a mensagem na janela de mensagens
                if(RdRemoto.isSelected())enviaMensagem("B1 X");
            }
        }else{
            if (B1.getText().equals("")){
                B1.setText("O");
                JogadorAtivo();
                if(RdRemoto.isSelected())enviaMensagem("B1 O");
            }
        }
    }//GEN-LAST:event_B1ActionPerformed

    private void B7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B7ActionPerformed
        if (Jogador1Ativado == true){
            if (B7.getText().equals("")){
                B7.setText("X");
                JogadorAtivo();
                if(RdRemoto.isSelected())enviaMensagem("B7 X");
            }
        }else{
            if (B7.getText().equals("")){
                B7.setText("O");
                JogadorAtivo();
                if(RdRemoto.isSelected())enviaMensagem("B7 O");
            }
        }
    }//GEN-LAST:event_B7ActionPerformed

    private void CampoDeTextoMensagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CampoDeTextoMensagemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CampoDeTextoMensagemActionPerformed

    private void BtEnviarMensagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtEnviarMensagemActionPerformed
        //Enviar a mensagem da caixa de texto caso o Botão Enviar seja pressionado
        enviaMensagem(awtAreaDeTextoMensagem.getText() + CampoNomeJogador.getText() + ": " + CampoDeTextoMensagem.getText() + "\n");
        awtAreaDeTextoMensagem.setText(awtAreaDeTextoMensagem.getText() + CampoNomeJogador.getText() + ": " + CampoDeTextoMensagem.getText() + "\n");
        CampoDeTextoMensagem.setText("");
    }//GEN-LAST:event_BtEnviarMensagemActionPerformed

    private void CampoDeTextoMensagemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CampoDeTextoMensagemKeyPressed
        //Enviar mensagem do campo de texto, caso o Botão enter seja pressionado
        int key = evt.getKeyCode();
        if (key == KeyEvent.VK_ENTER){
            enviaMensagem(CampoNomeJogador.getText() + ": " + CampoDeTextoMensagem.getText() + "\n");
            awtAreaDeTextoMensagem.setText(awtAreaDeTextoMensagem.getText() + CampoNomeJogador.getText() + ": " + CampoDeTextoMensagem.getText() + "\n");
            CampoDeTextoMensagem.setText("");
        }
    }//GEN-LAST:event_CampoDeTextoMensagemKeyPressed

    private void BtSairDoJogoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSairDoJogoActionPerformed
        System.exit(0);
    }//GEN-LAST:event_BtSairDoJogoActionPerformed

    private void BtSobreJogoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSobreJogoActionPerformed
        JOptionPane.showMessageDialog(JogoDaVelha.this, "Jogo Desenvolvido pelo Prof. Esp. José Lucas Brandão Montes\n"
            + "Contato: zehlucas@live.com  +55 65 9214-0449");
    }//GEN-LAST:event_BtSobreJogoActionPerformed

    private void BtNovoJogoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtNovoJogoActionPerformed
        LimparCampos();
    }//GEN-LAST:event_BtNovoJogoActionPerformed

    private void RdLocalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RdLocalActionPerformed
        //Opção que quando marcada desativa as opções do jogo Remoto, o RdRemoto e o Chat
        RdRemoto.setSelected(false);
        CampoEndereçoParceiro.setEnabled(false);
        CampoNomeJogador.setEnabled(false);
        awtAreaDeTextoMensagem.setEnabled(false);
        CampoDeTextoMensagem.setEnabled(false);
        BtEnviarMensagem.setEnabled(false);        
        jBConectar.setEnabled(false);
        fechaConexao();        
    }//GEN-LAST:event_RdLocalActionPerformed

    private void RdRemotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RdRemotoActionPerformed
        //opção que quando marcada desativa o RdLocal, ativa as opções de chat e do jogo remoto
        RdLocal.setSelected(false);
        CampoEndereçoParceiro.setEnabled(true);
        CampoNomeJogador.setEnabled(true);
        awtAreaDeTextoMensagem.setEnabled(true);
        CampoDeTextoMensagem.setEnabled(true);
        BtEnviarMensagem.setEnabled(true);
        //jBConectar.setEnabled(true);
        abreConexao(500);
        System.out.println("Teste1");
        recebeMensagem();
        System.out.println("Teste2");
    }//GEN-LAST:event_RdRemotoActionPerformed

    private void jBConectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBConectarActionPerformed
        //Quando o Botão Conectar é pressionado, os campos para inserção do nome do jogador e do endereço do parceiro são desativados
        CampoNomeJogador.setEnabled(false);
        CampoEndereçoParceiro.setEnabled(false);
        conecta(CampoEndereçoParceiro.getText(), 500);
    }//GEN-LAST:event_jBConectarActionPerformed

    private void CampoEndereçoParceiroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CampoEndereçoParceiroActionPerformed
    
    }//GEN-LAST:event_CampoEndereçoParceiroActionPerformed

    private void CampoNomeJogadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CampoNomeJogadorActionPerformed
        
    }//GEN-LAST:event_CampoNomeJogadorActionPerformed

    private void CampoEndereçoParceiroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CampoEndereçoParceiroKeyPressed
        if(CampoNomeJogador.getText() != ""){
            jBConectar.setEnabled(true);
        }else{
            jBConectar.setEnabled(false);
        }
    }//GEN-LAST:event_CampoEndereçoParceiroKeyPressed

    private void CampoNomeJogadorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CampoNomeJogadorKeyPressed
        if(CampoEndereçoParceiro.getText() != ""){
            jBConectar.setEnabled(true);
        }else{
            jBConectar.setEnabled(false);
        }
    }//GEN-LAST:event_CampoNomeJogadorKeyPressed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JogoDaVelha().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton B1;
    public javax.swing.JButton B2;
    public javax.swing.JButton B3;
    public javax.swing.JButton B4;
    public javax.swing.JButton B5;
    public javax.swing.JButton B6;
    public javax.swing.JButton B7;
    public javax.swing.JButton B8;
    public javax.swing.JButton B9;
    private javax.swing.JButton BtEnviarMensagem;
    private javax.swing.JButton BtNovoJogo;
    private javax.swing.JButton BtSairDoJogo;
    private javax.swing.JButton BtSobreJogo;
    private javax.swing.JTextField CampoDeTextoMensagem;
    private javax.swing.JTextField CampoEndereçoParceiro;
    private javax.swing.JTextField CampoNomeJogador;
    private javax.swing.JLabel NumeroDeVitoriasDoJogador1;
    private javax.swing.JLabel NumeroDeVitoriasDoJogador2;
    private javax.swing.JLabel NumeroEmpates;
    private javax.swing.JRadioButton RdLocal;
    private javax.swing.JRadioButton RdRemoto;
    private java.awt.TextArea awtAreaDeTextoMensagem;
    private javax.swing.JButton jBConectar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelEnderecoParceiro;
    private javax.swing.JLabel jLabelJogador1;
    private javax.swing.JLabel jLabelJogador2;
    private javax.swing.JLabel jLabelNomeJogador;
    private javax.swing.JLabel jLabelStatus;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    // End of variables declaration//GEN-END:variables
}
