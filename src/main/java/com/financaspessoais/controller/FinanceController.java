package com.financaspessoais.controller;

import com.financaspessoais.dao.TransacaoDAO;
import com.financaspessoais.model.Transacao;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class FinanceController {
    private TransacaoDAO transacaoDAO;

    public FinanceController() {
        this.transacaoDAO = new TransacaoDAO();
    }

    public void adicionarTransacao(Transacao transacao) throws SQLException {
        transacaoDAO.adicionar(transacao);
    }

    public List<Transacao> listarTransacoes() throws SQLException {
        return transacaoDAO.listarTodos();
    }

    public BigDecimal calcularSaldo() throws SQLException {
        return transacaoDAO.calcularSaldo();
    }

    public BigDecimal calcularTotalReceitas() throws SQLException {
        return transacaoDAO.calcularTotalReceitas();
    }

    public BigDecimal calcularTotalDespesas() throws SQLException {
        return transacaoDAO.calcularTotalDespesas();
    }
}