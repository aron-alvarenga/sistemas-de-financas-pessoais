-- =====================================================
-- Sistema de Finanças Pessoais - Schema do Banco de Dados
-- Versão: 1.0
-- =====================================================

-- Criar banco de dados
CREATE DATABASE IF NOT EXISTS financas_pessoais 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- Usar o banco de dados
USE financas_pessoais;

-- =====================================================
-- TABELA: categorias
-- Armazena as categorias de receitas e despesas
-- =====================================================
CREATE TABLE IF NOT EXISTS categorias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    tipo ENUM('RECEITA', 'DESPESA') NOT NULL,
    descricao VARCHAR(200),
    ativo BOOLEAN DEFAULT TRUE,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    -- Índices para melhor performance
    INDEX idx_categoria_tipo (tipo),
    INDEX idx_categoria_nome (nome)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- TABELA: transacoes
-- Armazena todas as transações financeiras
-- =====================================================
CREATE TABLE IF NOT EXISTS transacoes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(200) NOT NULL,
    valor DECIMAL(10, 2) NOT NULL,
    tipo ENUM('RECEITA', 'DESPESA') NOT NULL,
    data DATE NOT NULL,
    categoria VARCHAR(50) NOT NULL, -- Mantido como string para compatibilidade
    observacoes TEXT,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    -- Índices para melhor performance
    INDEX idx_transacao_data (data),
    INDEX idx_transacao_tipo (tipo),
    INDEX idx_transacao_categoria (categoria),
    INDEX idx_transacao_valor (valor)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- INSERÇÃO DE DADOS INICIAIS
-- =====================================================

-- Inserir categorias padrão de RECEITAS
INSERT INTO categorias (nome, tipo, descricao) VALUES
('Salário', 'RECEITA', 'Renda do trabalho formal'),
('Freelance', 'RECEITA', 'Trabalhos autônomos e projetos'),
('Investimentos', 'RECEITA', 'Dividendos, juros e rendimentos'),
('Presentes', 'RECEITA', 'Dinheiro recebido como presente'),
('Vendas', 'RECEITA', 'Venda de produtos ou serviços'),
('Outros', 'RECEITA', 'Outras fontes de receita');

-- Inserir categorias padrão de DESPESAS
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
('Outros', 'DESPESA', 'Outras despesas não categorizadas');

-- =====================================================
-- VIEWS SIMPLES PARA FACILITAR CONSULTAS
-- =====================================================

-- View para resumo financeiro mensal
CREATE OR REPLACE VIEW vw_resumo_mensal AS
SELECT 
    YEAR(data) as ano,
    MONTH(data) as mes,
    SUM(CASE WHEN tipo = 'RECEITA' THEN valor ELSE 0 END) as total_receitas,
    SUM(CASE WHEN tipo = 'DESPESA' THEN valor ELSE 0 END) as total_despesas,
    SUM(CASE WHEN tipo = 'RECEITA' THEN valor ELSE -valor END) as saldo,
    COUNT(CASE WHEN tipo = 'RECEITA' THEN 1 END) as qtd_receitas,
    COUNT(CASE WHEN tipo = 'DESPESA' THEN 1 END) as qtd_despesas
FROM transacoes 
GROUP BY YEAR(data), MONTH(data)
ORDER BY ano DESC, mes DESC;

-- =====================================================
-- TRIGGER SIMPLES PARA VALIDAÇÃO
-- =====================================================

-- Trigger para validar valores positivos
DELIMITER //
CREATE TRIGGER tr_transacoes_valor_positivo
BEFORE INSERT ON transacoes
FOR EACH ROW
BEGIN
    IF NEW.valor <= 0 THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Valor da transação deve ser maior que zero';
    END IF;
END//

DELIMITER ;

-- 1. Charset UTF8MB4 para suporte a caracteres especiais
-- 2. Índices para melhor performance
-- 3. Campos de auditoria (data_criacao, data_atualizacao)
-- 4. Campo observacoes para notas adicionais
-- 5. View para resumo mensal
-- 6. Trigger para validação de valores
-- 7. Categorias mais completas com descrições

-- Para verificar a estrutura criada, execute:
-- SHOW TABLES;
-- DESCRIBE transacoes;
-- DESCRIBE categorias;