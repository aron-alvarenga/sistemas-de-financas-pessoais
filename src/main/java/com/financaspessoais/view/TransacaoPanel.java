package com.financaspessoais.view;

import com.financaspessoais.controller.FinanceController;
import com.financaspessoais.dao.CategoriaDAO;
import com.financaspessoais.model.Transacao;
import com.financaspessoais.util.DateUtil;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class TransacaoPanel extends JPanel {
    private FinanceController controller;
    private CategoriaDAO categoriaDAO;
    private ResumoPanel resumoPanel;
    private JTable transacaoTable;
    private JButton adicionarButton;
    private JComboBox<String> tipoCombo;
    private JTextField descricaoField;
    private JTextField valorField;
    private JComboBox<String> categoriaCombo;
    private JFormattedTextField dataField;
    private DefaultTableModel tableModel;

    public TransacaoPanel() {
        this.controller = new FinanceController();
        this.categoriaDAO = new CategoriaDAO();
        initComponents();
        setupLayout();
        setupListeners();
        atualizarCategorias();
        atualizarTabela();
    }

    private void initComponents() {
        // Componentes para o formulário com estilo moderno
        tipoCombo = new JComboBox<>(new String[]{"RECEITA", "DESPESA"});
        tipoCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        descricaoField = new JTextField(20);
        descricaoField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descricaoField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0xCCCCCC), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        
        valorField = new JTextField(10);
        valorField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        valorField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0xCCCCCC), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        
        categoriaCombo = new JComboBox<>();
        categoriaCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        dataField = new JFormattedTextField(DateUtil.getTodayFormatted());
        dataField.setColumns(10);
        dataField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dataField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0xCCCCCC), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        // Botão moderno para adicionar transação
        adicionarButton = new JButton("Adicionar Transação");
        adicionarButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        adicionarButton.setBackground(new Color(0x007ACC));
        adicionarButton.setForeground(Color.WHITE);
        adicionarButton.setFocusPainted(false);
        adicionarButton.setBorderPainted(false);
        adicionarButton.setPreferredSize(new Dimension(200, 40));

        // Tabela moderna para exibir transações
        String[] colunas = {"Data", "Descrição", "Valor", "Tipo", "Categoria"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        transacaoTable = new JTable(tableModel);
        transacaoTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        transacaoTable.setRowHeight(35);
        transacaoTable.setSelectionBackground(new Color(0xE3F2FD));
        transacaoTable.setSelectionForeground(Color.BLACK);
        transacaoTable.setGridColor(new Color(0xE0E0E0));
        transacaoTable.setShowGrid(true);
        transacaoTable.setIntercellSpacing(new Dimension(1, 1));
        
        // Configurar header da tabela
        JTableHeader header = transacaoTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(0xF5F5F5));
        header.setForeground(new Color(0x333333));
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
    }

    private void setupLayout() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);

        // Painel de formulário moderno
        JPanel formPanel = createModernFormPanel();
        add(formPanel, BorderLayout.NORTH);

        // Tabela de transações com scroll moderno
        JScrollPane scrollPane = new JScrollPane(transacaoTable);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0xE0E0E0), 1),
                "Transações Recentes",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 16),
                new Color(0x333333)
            ),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private JPanel createModernFormPanel() {
        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0xE0E0E0), 1),
                "Nova Transação",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 16),
                new Color(0x333333)
            ),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Painel principal do formulário
        JPanel mainFormPanel = new JPanel(new GridBagLayout());
        mainFormPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        // Primeira linha: Tipo e Descrição
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel tipoLabel = createModernLabel("Tipo:");
        mainFormPanel.add(tipoLabel, gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        mainFormPanel.add(tipoCombo, gbc);
        
        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel descricaoLabel = createModernLabel("Descrição:");
        mainFormPanel.add(descricaoLabel, gbc);
        
        gbc.gridx = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 2.0;
        mainFormPanel.add(descricaoField, gbc);

        // Segunda linha: Valor e Categoria
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel valorLabel = createModernLabel("Valor:");
        mainFormPanel.add(valorLabel, gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        mainFormPanel.add(valorField, gbc);
        
        gbc.gridx = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel categoriaLabel = createModernLabel("Categoria:");
        mainFormPanel.add(categoriaLabel, gbc);
        
        gbc.gridx = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 2.0;
        mainFormPanel.add(categoriaCombo, gbc);

        // Terceira linha: Data e Botão
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        JLabel dataLabel = createModernLabel("Data:");
        mainFormPanel.add(dataLabel, gbc);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        mainFormPanel.add(dataField, gbc);
        
        gbc.gridx = 2; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainFormPanel.add(adicionarButton, gbc);

        formPanel.add(mainFormPanel, BorderLayout.CENTER);
        return formPanel;
    }
    
    private JLabel createModernLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(new Color(0x333333));
        return label;
    }

    private void setupListeners() {
        tipoCombo.addActionListener(e -> atualizarCategorias());
        adicionarButton.addActionListener(e -> adicionarTransacao());
    }

    private void atualizarCategorias() {
        categoriaCombo.removeAllItems();
        try {
            String tipoSelecionado = (String) tipoCombo.getSelectedItem();
            if (tipoSelecionado != null) {
                Transacao.TipoTransacao tipo = Transacao.TipoTransacao.valueOf(tipoSelecionado);
                List<com.financaspessoais.model.Categoria> categorias = categoriaDAO.listarPorTipo(tipo);
                for (com.financaspessoais.model.Categoria categoria : categorias) {
                    categoriaCombo.addItem(categoria.getNome());
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar categorias: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void adicionarTransacao() {
        try {
            // Validar campos
            if (descricaoField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Descrição é obrigatória", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (valorField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Valor é obrigatório", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Transacao transacao = new Transacao();
            transacao.setDescricao(descricaoField.getText().trim());
            transacao.setValor(new BigDecimal(valorField.getText().replace(",", ".")));
            transacao.setTipo(Transacao.TipoTransacao.valueOf(tipoCombo.getSelectedItem().toString()));
            
            LocalDate data = DateUtil.parse(dataField.getText());
            if (data == null) {
                JOptionPane.showMessageDialog(this, "Data inválida. Use o formato dd/MM/yyyy", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            transacao.setData(data);
            
            if (categoriaCombo.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Selecione uma categoria", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            transacao.setCategoria(categoriaCombo.getSelectedItem().toString());

            controller.adicionarTransacao(transacao);
            atualizarTabela();
            limparCampos();
            
            // Atualizar o resumo se estiver configurado
            if (resumoPanel != null) {
                resumoPanel.refresh();
            }
            
            JOptionPane.showMessageDialog(this, "Transação adicionada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Valor inválido. Use números separados por vírgula ou ponto.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar transação: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        try {
            List<Transacao> transacoes = controller.listarTransacoes();
            for (Transacao transacao : transacoes) {
                String valorFormatado = String.format("R$ %.2f", transacao.getValor());
                String tipoFormatado = transacao.getTipo() == Transacao.TipoTransacao.RECEITA ? 
                    "[+] " + transacao.getTipo().toString() : 
                    "[-] " + transacao.getTipo().toString();
                
                Object[] row = {
                    DateUtil.format(transacao.getData()),
                    transacao.getDescricao(),
                    valorFormatado,
                    tipoFormatado,
                    transacao.getCategoria()
                };
                tableModel.addRow(row);
            }
            
            // Aplicar renderização customizada para cores
            transacaoTable.setDefaultRenderer(Object.class, new ModernTableCellRenderer());
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar transações: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Renderer customizado para células da tabela
    private class ModernTableCellRenderer extends javax.swing.table.DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            if (!isSelected) {
                // Cor baseada no tipo de transação (coluna 3)
                String tipo = (String) table.getValueAt(row, 3);
                if (tipo != null) {
                    if (tipo.contains("RECEITA")) {
                        c.setBackground(new Color(0xF1F8E9)); // Verde claro
                    } else if (tipo.contains("DESPESA")) {
                        c.setBackground(new Color(0xFFEBEE)); // Vermelho claro
                    } else {
                        c.setBackground(Color.WHITE);
                    }
                } else {
                    c.setBackground(Color.WHITE);
                }
            }
            
            // Estilo para coluna de valor
            if (column == 2) {
                setFont(new Font("Segoe UI", Font.BOLD, 13));
                setHorizontalAlignment(SwingConstants.RIGHT);
            } else {
                setFont(new Font("Segoe UI", Font.PLAIN, 13));
                setHorizontalAlignment(SwingConstants.LEFT);
            }
            
            return c;
        }
    }

    private void limparCampos() {
        descricaoField.setText("");
        valorField.setText("");
        dataField.setValue(DateUtil.getTodayFormatted());
        categoriaCombo.setSelectedIndex(-1);
    }

    public void setResumoPanel(ResumoPanel resumoPanel) {
        this.resumoPanel = resumoPanel;
    }
}