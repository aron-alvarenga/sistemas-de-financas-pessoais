package com.financaspessoais;

import com.financaspessoais.view.MainFrame;

public class App {
    public static void main(String[] args) {
        // Configurar look and feel moderno
        try {
            javax.swing.UIManager.setLookAndFeel(
                    javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Iniciar a aplicação
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
}