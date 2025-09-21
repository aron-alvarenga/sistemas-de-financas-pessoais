package com.financaspessoais.view;

import com.financaspessoais.controller.FinanceController;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.PieSectionEntity;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.math.BigDecimal;
import java.sql.SQLException;

public class ResumoPanel extends JPanel {
    private FinanceController controller;
    private JLabel saldoLabel;
    private JLabel receitasLabel;
    private JLabel despesasLabel;
    private ChartPanel chartPanel;
    private DefaultPieDataset<String> dataset;

    public ResumoPanel() {
        this.controller = new FinanceController();
        initComponents();
        setupLayout();
        atualizarDados();
    }

    private void initComponents() {
        // Criar labels modernos com ícones
        saldoLabel = createModernInfoLabel("$", "Saldo Total", "R$ 0,00", new Color(0x007ACC));
        receitasLabel = createModernInfoLabel("+", "Receitas", "R$ 0,00", new Color(0x4CAF50));
        despesasLabel = createModernInfoLabel("-", "Despesas", "R$ 0,00", new Color(0xF44336));

        // Criar gráfico moderno
        criarGrafico();
    }
    
    private JLabel createModernInfoLabel(String icon, String title, String value, Color color) {
        JLabel label = new JLabel();
        label.setText(String.format("<html><div style='text-align: center; padding: 15px;'>" +
                "<div style='font-size: 24px; margin-bottom: 5px;'>%s</div>" +
                "<div style='font-size: 12px; color: #666; margin-bottom: 8px;'>%s</div>" +
                "<div style='font-size: 18px; font-weight: bold; color: %s;'>%s</div></div></html>",
                icon, title, String.format("#%06X", color.getRGB() & 0xFFFFFF), value));
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0xE0E0E0), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        label.setBackground(Color.WHITE);
        label.setOpaque(true);
        return label;
    }

    private void setupLayout() {
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(new Color(0xF8F9FA));

        // Painel de informações moderno
        JPanel infoPanel = createModernInfoPanel();
        add(infoPanel, BorderLayout.NORTH);
        
        // Painel do gráfico responsivo
        JPanel chartContainer = createResponsiveChartContainer();
        add(chartContainer, BorderLayout.CENTER);
        
        // Adicionar listener para redimensionamento
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateChartSize();
            }
        });
    }
    
    private JPanel createResponsiveChartContainer() {
        JPanel chartContainer = new JPanel(new BorderLayout());
        chartContainer.setBackground(Color.WHITE);
        chartContainer.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0xE0E0E0), 1),
                "Distribuição Financeira",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 16),
                new Color(0x333333)
            ),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        // Painel central para o gráfico com scroll se necessário
        JScrollPane chartScrollPane = new JScrollPane(chartPanel);
        chartScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        chartScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        chartScrollPane.setBorder(null);
        chartScrollPane.getViewport().setBackground(Color.WHITE);
        
        chartContainer.add(chartScrollPane, BorderLayout.CENTER);
        return chartContainer;
    }
    
    private void updateChartSize() {
        if (chartPanel != null) {
            Dimension containerSize = getSize();
            int chartWidth = Math.max(400, containerSize.width - 100);
            int chartHeight = Math.max(300, containerSize.height - 200);
            
            chartPanel.setPreferredSize(new Dimension(chartWidth, chartHeight));
            chartPanel.setMinimumSize(new Dimension(300, 250));
            chartPanel.setMaximumSize(new Dimension(800, 600));
            
            revalidate();
            repaint();
        }
    }
    
    private JPanel createModernInfoPanel() {
        JPanel infoPanel = new JPanel(new GridLayout(1, 3, 15, 15));
        infoPanel.setBackground(new Color(0xF8F9FA));
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0xE0E0E0), 1),
                "Resumo Financeiro",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 16),
                new Color(0x333333)
            ),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        infoPanel.add(saldoLabel);
        infoPanel.add(receitasLabel);
        infoPanel.add(despesasLabel);

        return infoPanel;
    }

    private void criarGrafico() {
        dataset = new DefaultPieDataset<>();
        dataset.setValue("Receitas", 0);
        dataset.setValue("Despesas", 0);

        JFreeChart chart = ChartFactory.createPieChart(
                "Distribuição de Receitas e Despesas",
                dataset,
                true, true, false);

        // Personalizar o gráfico
        chart.setBackgroundPaint(Color.WHITE);
        chart.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 16));
        chart.getTitle().setPaint(new Color(0x333333));
        
        @SuppressWarnings("unchecked")
        PiePlot<String> plot = (PiePlot<String>) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);
        plot.setLabelFont(new Font("Segoe UI", Font.PLAIN, 12));
        plot.setLabelBackgroundPaint(Color.WHITE);
        plot.setLabelOutlinePaint(Color.WHITE);
        plot.setLabelShadowPaint(Color.WHITE);
        
        // Cores modernas
        plot.setSectionPaint("Receitas", new Color(0x4CAF50));
        plot.setSectionPaint("Despesas", new Color(0xF44336));
        
        // Configurar labels personalizados com valores monetários
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: R$ {1}"));
        
        // Configurar tooltips personalizados
        plot.setToolTipGenerator(new org.jfree.chart.labels.StandardPieToolTipGenerator("{0}: R$ {1} ({2})"));
        
        // Configurar interatividade
        plot.setExplodePercent("Receitas", 0.05);
        plot.setExplodePercent("Despesas", 0.05);
        
        // Configurar transparência para efeito moderno
        plot.setSectionPaint("Receitas", new Color(76, 175, 80, 200));
        plot.setSectionPaint("Despesas", new Color(244, 67, 54, 200));

        // Criar ChartPanel com funcionalidades avançadas
        chartPanel = new ChartPanel(chart) {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Adicionar efeito de gradiente sutil
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.dispose();
            }
        };
        
        // Configurar tamanho responsivo
        chartPanel.setPreferredSize(new Dimension(500, 350));
        chartPanel.setMinimumSize(new Dimension(300, 250));
        chartPanel.setMaximumSize(new Dimension(800, 600));
        chartPanel.setBackground(Color.WHITE);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Habilitar funcionalidades interativas
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setDomainZoomable(true);
        chartPanel.setRangeZoomable(true);
        chartPanel.setPopupMenu(null); // Desabilitar menu de contexto padrão
        
        // Adicionar listener para hover effects
        chartPanel.addChartMouseListener(new org.jfree.chart.ChartMouseListener() {
            @Override
            public void chartMouseClicked(org.jfree.chart.ChartMouseEvent event) {
                ChartEntity entity = event.getEntity();
                if (entity instanceof PieSectionEntity) {
                    PieSectionEntity pieEntity = (PieSectionEntity) entity;
                    String section = (String) pieEntity.getSectionKey();
                    showSectionDetails(section);
                }
            }
            
            @Override
            public void chartMouseMoved(org.jfree.chart.ChartMouseEvent event) {
                // Efeito de hover pode ser implementado aqui
            }
        });
    }
    
    
    private void showSectionDetails(String section) {
        try {
            BigDecimal value = section.equals("Receitas") ? 
                controller.calcularTotalReceitas() : 
                controller.calcularTotalDespesas();
            
            String message = String.format("<html><b>%s</b><br>Valor Total: R$ %.2f</html>", 
                section, value);
            
            JOptionPane.showMessageDialog(this, message, "Detalhes da " + section, 
                JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar detalhes: " + ex.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarDados() {
        try {
            BigDecimal saldo = controller.calcularSaldo();
            BigDecimal receitas = controller.calcularTotalReceitas();
            BigDecimal despesas = controller.calcularTotalDespesas();

            // Atualizar labels com formatação moderna
            atualizarInfoLabel(saldoLabel, "$", "Saldo Total", String.format("R$ %.2f", saldo), 
                saldo.compareTo(BigDecimal.ZERO) >= 0 ? new Color(0x4CAF50) : new Color(0xF44336));
            atualizarInfoLabel(receitasLabel, "+", "Receitas", String.format("R$ %.2f", receitas), new Color(0x4CAF50));
            atualizarInfoLabel(despesasLabel, "-", "Despesas", String.format("R$ %.2f", despesas), new Color(0xF44336));

            // Atualizar gráfico
            dataset.clear();
            dataset.setValue("Receitas", receitas.doubleValue());
            dataset.setValue("Despesas", despesas.doubleValue());

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void atualizarInfoLabel(JLabel label, String icon, String title, String value, Color color) {
        label.setText(String.format("<html><div style='text-align: center; padding: 15px;'>" +
                "<div style='font-size: 24px; margin-bottom: 5px;'>%s</div>" +
                "<div style='font-size: 12px; color: #666; margin-bottom: 8px;'>%s</div>" +
                "<div style='font-size: 18px; font-weight: bold; color: %s;'>%s</div></div></html>",
                icon, title, String.format("#%06X", color.getRGB() & 0xFFFFFF), value));
    }

    public void refresh() {
        atualizarDados();
        // Adicionar animação suave ao atualizar
        animateChartUpdate();
    }
    
    private void animateChartUpdate() {
        if (chartPanel != null) {
            // Efeito de fade in/out sutil
            Timer timer = new Timer(50, null);
            timer.addActionListener(e -> {
                chartPanel.repaint();
                if (timer.getDelay() >= 200) {
                    timer.stop();
                } else {
                    timer.setDelay(timer.getDelay() + 10);
                }
            });
            timer.start();
        }
    }
}