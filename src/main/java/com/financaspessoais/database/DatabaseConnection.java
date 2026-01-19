package com.financaspessoais.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    // Configuração para H2 em memória (desenvolvimento)
    private static final boolean USE_H2 = true; // Mude para false para usar MySQL
    
    // H2 em memória
    private static final String H2_URL = "jdbc:h2:mem:financas_pessoais;DB_CLOSE_DELAY=-1;MODE=MySQL";
    private static final String H2_USER = "sa";
    private static final String H2_PASSWORD = "";
    
    // MySQL (produção)
    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/financas_pessoais";
    private static final String MYSQL_USER = "root";
    private static final String MYSQL_PASSWORD = "123456";
    
    private static boolean initialized = false;

    public static Connection getConnection() throws SQLException {
        Connection conn;
        
        if (USE_H2) {
            conn = DriverManager.getConnection(H2_URL, H2_USER, H2_PASSWORD);
            // Inicializar schema apenas uma vez para H2
            if (!initialized) {
                initializeH2Database(conn);
                initialized = true;
            }
        } else {
            conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
        }
        
        return conn;
    }
    
    private static void initializeH2Database(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            // Criar tabela categorias (H2 não suporta ENUM, usa VARCHAR com CHECK)
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS categorias (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nome VARCHAR(50) NOT NULL,
                    tipo VARCHAR(10) NOT NULL CHECK (tipo IN ('RECEITA', 'DESPESA')),
                    descricao VARCHAR(200),
                    ativo BOOLEAN DEFAULT TRUE,
                    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """);
            
            // Criar índices
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_categoria_tipo ON categorias(tipo)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_categoria_nome ON categorias(nome)");
            
            // Criar tabela transacoes
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS transacoes (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    descricao VARCHAR(200) NOT NULL,
                    valor DECIMAL(10, 2) NOT NULL,
                    tipo VARCHAR(10) NOT NULL CHECK (tipo IN ('RECEITA', 'DESPESA')),
                    data DATE NOT NULL,
                    categoria VARCHAR(50) NOT NULL,
                    observacoes TEXT,
                    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """);
            
            // Criar índices
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_transacao_data ON transacoes(data)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_transacao_tipo ON transacoes(tipo)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_transacao_categoria ON transacoes(categoria)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_transacao_valor ON transacoes(valor)");
            
            // Inserir categorias padrão de RECEITAS
            stmt.execute("""
                INSERT INTO categorias (nome, tipo, descricao) VALUES
                ('Salário', 'RECEITA', 'Renda do trabalho formal'),
                ('Freelance', 'RECEITA', 'Trabalhos autônomos e projetos'),
                ('Investimentos', 'RECEITA', 'Dividendos, juros e rendimentos'),
                ('Presentes', 'RECEITA', 'Dinheiro recebido como presente'),
                ('Vendas', 'RECEITA', 'Venda de produtos ou serviços'),
                ('Outros', 'RECEITA', 'Outras fontes de receita')
            """);
            
            // Inserir categorias padrão de DESPESAS
            stmt.execute("""
                INSERT INTO categorias (nome, tipo, descricao) VALUES
                ('Alimentação', 'DESPESA', 'Supermercado, restaurantes e comida'),
                ('Moradia', 'DESPESA', 'Aluguel, condomínio, IPTU e manutenção'),
                ('Transporte', 'DESPESA', 'Combustível, transporte público e manutenção'),
                ('Lazer', 'DESPESA', 'Entretenimento, hobbies e diversão'),
                ('Saúde', 'DESPESA', 'Médicos, medicamentos e plano de saúde'),
                ('Educação', 'DESPESA', 'Cursos, livros e mensalidades'),
                ('Vestuário', 'DESPESA', 'Roupas, calçados e acessórios'),
                ('Utilidades', 'DESPESA', 'Luz, água, internet e telefone'),
                ('Seguros', 'DESPESA', 'Seguros diversos'),
                ('Outros', 'DESPESA', 'Outras despesas não categorizadas')
            """);
        }
    }
}