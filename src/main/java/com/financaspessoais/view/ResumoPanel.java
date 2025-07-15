package com.financaspessoais.view;

import com.financaspessoais.controller.FinanceController;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;

public class ResumoPanel extends JPanel {
    private FinanceController controller;
    private JLabel saldoLabel;
    private JLabel receitasLabel;
    private JLabel despesasLabel;
    private ChartPanel chartPanel;
    private DefaultPieDataset dataset;

    public ResumoPanel() {
        this.controller = new FinanceController();
        initComponents();
        setupLayout();
        atualizarDados();
    }

    private void initComponents() {
        saldoLabel = new JLabel("Saldo: R$ 0,00");
        receitasLabel = new JLabel("Receitas: R$ 0,00");
        despesasLabel = new JLabel("Despesas: R$ 0,00");

        // Configurar estilo dos labels
        Font boldFont = new Font(saldoLabel.getFont().getName(), Font.BOLD, 16);
        saldoLabel.setFont(boldFont);

        // Criar gráfico
        criarGrafico();
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));

        // Painel de informações
        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Resumo Financeiro"));
        infoPanel.add(saldoLabel);
        infoPanel.add(receitasLabel);
        infoPanel.add(despesasLabel);

        add(infoPanel, BorderLayout.NORTH);
        add(chartPanel, BorderLayout.CENTER);
    }

    @SuppressWarnings("unchecked")
    private void criarGrafico() {
        dataset = new DefaultPieDataset();
        dataset.setValue("Receitas", 0);
        dataset.setValue("Despesas", 0);

        JFreeChart chart = ChartFactory.createPieChart(
                "Distribuição de Receitas e Despesas",
                dataset,
                true, true, false);

        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(400, 300));
    }

    private void atualizarDados() {
        try {
            BigDecimal saldo = controller.calcularSaldo();
            BigDecimal receitas = controller.calcularTotalReceitas();
            BigDecimal despesas = controller.calcularTotalDespesas();

            // Atualizar labels
            saldoLabel.setText(String.format("Saldo: R$ %.2f", saldo));
            receitasLabel.setText(String.format("Receitas: R$ %.2f", receitas));
            despesasLabel.setText(String.format("Despesas: R$ %.2f", despesas));

            // Atualizar gráfico
            dataset.clear();
            dataset.setValue("Receitas", receitas.doubleValue());
            dataset.setValue("Despesas", despesas.doubleValue());

            // Atualizar cor do saldo baseado no valor
            if (saldo.compareTo(BigDecimal.ZERO) >= 0) {
                saldoLabel.setForeground(new Color(0, 128, 0)); // Verde
            } else {
                saldoLabel.setForeground(Color.RED);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void refresh() {
        atualizarDados();
    }
}