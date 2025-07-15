package com.financaspessoais.dao;

import com.financaspessoais.database.DatabaseConnection;
import com.financaspessoais.model.Categoria;
import com.financaspessoais.model.Transacao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {
    public List<Categoria> listarPorTipo(Transacao.TipoTransacao tipo) throws SQLException {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categorias WHERE tipo = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tipo.toString());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("id"));
                categoria.setNome(rs.getString("nome"));
                categoria.setTipo(Transacao.TipoTransacao.valueOf(rs.getString("tipo")));

                categorias.add(categoria);
            }
        }

        return categorias;
    }
}