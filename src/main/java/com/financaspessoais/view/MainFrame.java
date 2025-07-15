package com.financaspessoais.view;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;

public class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;
    private TransacaoPanel transacaoPanel;
    private ResumoPanel resumoPanel;

    public MainFrame() {
        configureTheme();
        initComponents();
        setupFrame();
    }

    private void configureTheme() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
    }

    private void initComponents() {
        tabbedPane = new JTabbedPane();
        transacaoPanel = new TransacaoPanel();
        resumoPanel = new ResumoPanel();

        // Configurar callback para atualizar o resumo quando transações forem adicionadas
        transacaoPanel.setResumoPanel(resumoPanel);

        tabbedPane.addTab("Transações", transacaoPanel);
        tabbedPane.addTab("Resumo", resumoPanel);

        add(tabbedPane);
    }

    private void setupFrame() {
        setTitle("Finanças Pessoais");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}