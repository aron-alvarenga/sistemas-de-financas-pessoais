package com.financaspessoais.view;

import com.financaspessoais.controller.FinanceController;
import com.financaspessoais.dao.CategoriaDAO;
import com.financaspessoais.model.Transacao;
import com.financaspessoais.util.DateUtil;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
        // Componentes para o formulário
        tipoCombo = new JComboBox<>(new String[]{"RECEITA", "DESPESA"});
        descricaoField = new JTextField(20);
        valorField = new JTextField(10);
        categoriaCombo = new JComboBox<>();
        dataField = new JFormattedTextField(DateUtil.getTodayFormatted());
        dataField.setColumns(10);

        // Botão para adicionar transação
        adicionarButton = new JButton("Adicionar Transação");

        // Tabela para exibir transações
        String[] colunas = {"Data", "Descrição", "Valor", "Tipo", "Categoria"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        transacaoTable = new JTable(tableModel);
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));

        // Painel de formulário
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Nova Transação"));

        formPanel.add(new JLabel("Tipo:"));
        formPanel.add(tipoCombo);
        formPanel.add(new JLabel("Descrição:"));
        formPanel.add(descricaoField);
        formPanel.add(new JLabel("Valor:"));
        formPanel.add(valorField);
        formPanel.add(new JLabel("Categoria:"));
        formPanel.add(categoriaCombo);
        formPanel.add(new JLabel("Data:"));
        formPanel.add(dataField);
        formPanel.add(new JLabel());
        formPanel.add(adicionarButton);

        add(formPanel, BorderLayout.NORTH);

        // Tabela de transações
        JScrollPane scrollPane = new JScrollPane(transacaoTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Transações Recentes"));
        add(scrollPane, BorderLayout.CENTER);
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
                Object[] row = {
                    DateUtil.format(transacao.getData()),
                    transacao.getDescricao(),
                    String.format("R$ %.2f", transacao.getValor()),
                    transacao.getTipo().toString(),
                    transacao.getCategoria()
                };
                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar transações: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
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