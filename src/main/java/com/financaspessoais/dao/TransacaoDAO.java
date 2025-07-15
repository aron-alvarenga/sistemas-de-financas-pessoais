package com.financaspessoais.dao;

import com.financaspessoais.database.DatabaseConnection;
import com.financaspessoais.model.Transacao;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransacaoDAO {
    public void adicionar(Transacao transacao) throws SQLException {
        String sql = "INSERT INTO transacoes (descricao, valor, tipo, data, categoria) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, transacao.getDescricao());
            stmt.setBigDecimal(2, transacao.getValor());
            stmt.setString(3, transacao.getTipo().toString());
            stmt.setDate(4, Date.valueOf(transacao.getData()));
            stmt.setString(5, transacao.getCategoria());

            stmt.executeUpdate();
        }
    }

    public List<Transacao> listarTodos() throws SQLException {
        List<Transacao> transacoes = new ArrayList<>();
        String sql = "SELECT * FROM transacoes ORDER BY data DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Transacao transacao = new Transacao();
                transacao.setId(rs.getInt("id"));
                transacao.setDescricao(rs.getString("descricao"));
                transacao.setValor(rs.getBigDecimal("valor"));
                transacao.setTipo(Transacao.TipoTransacao.valueOf(rs.getString("tipo")));
                transacao.setData(rs.getDate("data").toLocalDate());
                transacao.setCategoria(rs.getString("categoria"));

                transacoes.add(transacao);
            }
        }

        return transacoes;
    }

    public BigDecimal calcularSaldo() throws SQLException {
        BigDecimal saldo = BigDecimal.ZERO;
        
        // Calcular receitas
        String sqlReceitas = "SELECT COALESCE(SUM(valor), 0) AS total FROM transacoes WHERE tipo = 'RECEITA'";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlReceitas)) {

            if (rs.next()) {
                BigDecimal receitas = rs.getBigDecimal("total");
                saldo = saldo.add(receitas);
            }
        }

        // Calcular despesas
        String sqlDespesas = "SELECT COALESCE(SUM(valor), 0) AS total FROM transacoes WHERE tipo = 'DESPESA'";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlDespesas)) {

            if (rs.next()) {
                BigDecimal despesas = rs.getBigDecimal("total");
                saldo = saldo.subtract(despesas);
            }
        }

        return saldo;
    }

    public BigDecimal calcularTotalReceitas() throws SQLException {
        BigDecimal total = BigDecimal.ZERO;
        String sql = "SELECT COALESCE(SUM(valor), 0) AS total FROM transacoes WHERE tipo = 'RECEITA'";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                total = rs.getBigDecimal("total");
            }
        }

        return total;
    }

    public BigDecimal calcularTotalDespesas() throws SQLException {
        BigDecimal total = BigDecimal.ZERO;
        String sql = "SELECT COALESCE(SUM(valor), 0) AS total FROM transacoes WHERE tipo = 'DESPESA'";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                total = rs.getBigDecimal("total");
            }
        }

        return total;
    }
}