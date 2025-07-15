CREATE DATABASE financas_pessoais;

USE financas_pessoais;

CREATE TABLE transacoes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(100) NOT NULL,
    valor DECIMAL(10, 2) NOT NULL,
    tipo ENUM('RECEITA', 'DESPESA') NOT NULL,
    data DATE NOT NULL,
    categoria VARCHAR(50) NOT NULL
);

CREATE TABLE categorias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    tipo ENUM('RECEITA', 'DESPESA') NOT NULL
);

-- Inserir algumas categorias padrão
INSERT INTO categorias (nome, tipo) VALUES
('Salário', 'RECEITA'),
('Freelance', 'RECEITA'),
('Investimentos', 'RECEITA'),
('Alimentação', 'DESPESA'),
('Moradia', 'DESPESA'),
('Transporte', 'DESPESA'),
('Lazer', 'DESPESA'),
('Saúde', 'DESPESA'),
('Educação', 'DESPESA');