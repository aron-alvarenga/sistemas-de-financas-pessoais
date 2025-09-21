package com.financaspessoais.util;

import javax.swing.*;
import java.awt.*;

/**
 * Classe para configuração de temas e estilos modernos da aplicação
 */
public class ThemeConfig {
    
    // Cores principais do tema
    public static final Color PRIMARY_COLOR = new Color(0x007ACC);
    public static final Color SUCCESS_COLOR = new Color(0x4CAF50);
    public static final Color ERROR_COLOR = new Color(0xF44336);
    public static final Color WARNING_COLOR = new Color(0xFF9800);
    public static final Color INFO_COLOR = new Color(0x2196F3);
    
    // Cores de fundo
    public static final Color BACKGROUND_LIGHT = new Color(0xF8F9FA);
    public static final Color BACKGROUND_WHITE = Color.WHITE;
    public static final Color BORDER_COLOR = new Color(0xE0E0E0);
    
    // Cores de texto
    public static final Color TEXT_PRIMARY = new Color(0x333333);
    public static final Color TEXT_SECONDARY = new Color(0x666666);
    public static final Color TEXT_LIGHT = new Color(0x999999);
    
    // Fontes
    public static final Font FONT_PRIMARY = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font FONT_LARGE = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font FONT_HEADER = new Font("Segoe UI", Font.BOLD, 28);
    
    /**
     * Aplica configurações modernas ao UIManager
     */
    public static void applyModernTheme() {
        try {
            // Configurações para botões
            UIManager.put("Button.arc", 8);
            UIManager.put("Button.focusWidth", 0);
            UIManager.put("Button.defaultButtonFollowsFocus", true);
            
            // Configurações para campos de texto
            UIManager.put("TextComponent.arc", 8);
            UIManager.put("TextComponent.focusWidth", 2);
            
            // Configurações para combos
            UIManager.put("ComboBox.arc", 8);
            
            // Configurações para tabs
            UIManager.put("TabbedPane.selectedBackground", PRIMARY_COLOR);
            UIManager.put("TabbedPane.selectedForeground", Color.WHITE);
            UIManager.put("TabbedPane.tabInsets", new Insets(12, 20, 12, 20));
            UIManager.put("TabbedPane.tabHeight", 40);
            
            // Configurações para tabelas
            UIManager.put("Table.selectionBackground", new Color(0xE3F2FD));
            UIManager.put("Table.selectionForeground", TEXT_PRIMARY);
            UIManager.put("Table.gridColor", BORDER_COLOR);
            
            // Configurações para bordas
            UIManager.put("Component.arc", 8);
            UIManager.put("Component.focusWidth", 2);
            
        } catch (Exception e) {
            System.err.println("Erro ao aplicar tema moderno: " + e.getMessage());
        }
    }
    
    /**
     * Cria uma borda moderna com título
     */
    public static javax.swing.border.Border createModernTitledBorder(String title) {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                title,
                0, 0,
                FONT_TITLE,
                TEXT_PRIMARY
            ),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );
    }
    
    /**
     * Cria um botão moderno
     */
    public static JButton createModernButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(FONT_BOLD);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(200, 40));
        return button;
    }
    
    /**
     * Cria um campo de texto moderno
     */
    public static JTextField createModernTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(FONT_PRIMARY);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        return field;
    }
    
    /**
     * Cria um label moderno
     */
    public static JLabel createModernLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_PRIMARY);
        label.setForeground(TEXT_PRIMARY);
        return label;
    }
}
