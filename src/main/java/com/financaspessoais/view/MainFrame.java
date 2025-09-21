package com.financaspessoais.view;

import com.formdev.flatlaf.FlatLightLaf;
import com.financaspessoais.util.ThemeConfig;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;
    private TransacaoPanel transacaoPanel;
    private ResumoPanel resumoPanel;
    private JPanel headerPanel;
    private JLabel titleLabel;
    private JLabel subtitleLabel;

    public MainFrame() {
        configureTheme();
        initComponents();
        setupFrame();
    }

    private void configureTheme() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            ThemeConfig.applyModernTheme();
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Criar header moderno
        createHeader();
        
        // Configurar painéis
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        transacaoPanel = new TransacaoPanel();
        resumoPanel = new ResumoPanel();

        // Configurar callback para atualizar o resumo quando transações forem adicionadas
        transacaoPanel.setResumoPanel(resumoPanel);

        // Adicionar tabs sem emojis
        tabbedPane.addTab("Transações", transacaoPanel);
        tabbedPane.addTab("Resumo", resumoPanel);
        
        // Adicionar tooltips
        tabbedPane.setToolTipTextAt(0, "Gerenciar suas transações financeiras");
        tabbedPane.setToolTipTextAt(1, "Visualizar resumo financeiro");

        // Configurar estilo dos tabs
        tabbedPane.setFont(ThemeConfig.FONT_PRIMARY);
        tabbedPane.setTabPlacement(JTabbedPane.TOP);
        
        add(headerPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }
    
    private void createHeader() {
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(ThemeConfig.PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        
        titleLabel = new JLabel("Finanças Pessoais");
        titleLabel.setFont(ThemeConfig.FONT_HEADER);
        titleLabel.setForeground(Color.WHITE);
        
        subtitleLabel = new JLabel("Controle suas receitas e despesas de forma inteligente");
        subtitleLabel.setFont(ThemeConfig.FONT_PRIMARY);
        subtitleLabel.setForeground(new Color(0xE0E0E0));
        
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(subtitleLabel, BorderLayout.SOUTH);
        
        headerPanel.add(titlePanel, BorderLayout.WEST);
        
        // Adicionar informações do sistema no canto direito
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        infoPanel.setOpaque(false);
        
        JLabel versionLabel = new JLabel("v1.0");
        versionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        versionLabel.setForeground(new Color(0xE0E0E0));
        infoPanel.add(versionLabel);
        
        headerPanel.add(infoPanel, BorderLayout.EAST);
    }
    

    private void setupFrame() {
        setTitle("Finanças Pessoais - Sistema de Controle Financeiro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        
        // Configurar ícone da janela (se disponível)
        try {
            // Usar ícone padrão do sistema ou sem ícone
            setIconImage(null);
        } catch (Exception e) {
            // Ignorar se não conseguir carregar ícone
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}