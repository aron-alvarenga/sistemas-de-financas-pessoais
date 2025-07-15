# Sistema de Finanças Pessoais

Um sistema desktop para controle de finanças pessoais desenvolvido em Java com interface gráfica Swing.

## Funcionalidades

- **Gestão de Transações**: Adicionar receitas e despesas com descrição, valor, categoria e data
- **Categorização**: Sistema de categorias para organizar transações
- **Resumo Financeiro**: Visualização do saldo, receitas e despesas totais
- **Gráficos**: Gráfico de pizza mostrando a distribuição entre receitas e despesas
- **Interface Moderna**: Interface gráfica com tema FlatLaf

## Pré-requisitos

- Java 17 ou superior
- Maven 3.6 ou superior
- MySQL 8.0 ou superior

## Instalação e Configuração

### 1. Configurar o Banco de Dados

1. Instale e configure o MySQL
2. Execute o script SQL para criar o banco e as tabelas:

```sql
-- Execute o arquivo src/main/resources/schema.sql no seu MySQL
```

### 2. Configurar Conexão com Banco

Edite o arquivo `src/main/java/com/financaspessoais/database/DatabaseConnection.java` e ajuste as configurações:

```java
private static final String URL = "jdbc:mysql://localhost:3306/financas_pessoais";
private static final String USER = "seu_usuario";
private static final String PASSWORD = "sua_senha";
```

### 3. Compilar e Executar

```bash
# Compilar o projeto
mvn clean compile

# Criar JAR executável
mvn package

# Executar o aplicativo
java -jar target/finance-app-1.0-SNAPSHOT.jar
```

Ou executar diretamente via Maven:

```bash
mvn exec:java -Dexec.mainClass="com.financaspessoais.App"
```

## Como Usar

### Adicionando Transações

1. Abra a aba "Transações"
2. Preencha os campos:
   - **Tipo**: RECEITA ou DESPESA
   - **Descrição**: Descrição da transação
   - **Valor**: Valor (use vírgula ou ponto como separador decimal)
   - **Categoria**: Selecione uma categoria (baseada no tipo)
   - **Data**: Data da transação (formato dd/MM/yyyy)
3. Clique em "Adicionar Transação"

### Visualizando o Resumo

1. Abra a aba "Resumo"
2. Visualize:
   - Saldo atual (verde se positivo, vermelho se negativo)
   - Total de receitas
   - Total de despesas
   - Gráfico de distribuição

## Estrutura do Projeto

```
src/main/java/com/financaspessoais/
├── App.java                    # Classe principal
├── controller/
│   └── FinanceController.java  # Controlador principal
├── dao/
│   ├── CategoriaDAO.java      # Acesso a dados das categorias
│   └── TransacaoDAO.java      # Acesso a dados das transações
├── database/
│   └── DatabaseConnection.java # Configuração de conexão
├── model/
│   ├── Categoria.java         # Modelo de categoria
│   └── Transacao.java         # Modelo de transação
├── util/
│   └── DateUtil.java          # Utilitários para datas
└── view/
    ├── MainFrame.java         # Janela principal
    ├── ResumoPanel.java       # Painel de resumo
    └── TransacaoPanel.java    # Painel de transações
```

## Tecnologias Utilizadas

- **Java 17**: Linguagem de programação
- **Swing**: Interface gráfica
- **FlatLaf**: Tema moderno para Swing
- **JFreeChart**: Biblioteca para gráficos
- **MySQL**: Banco de dados
- **Maven**: Gerenciamento de dependências

## Categorias Padrão

O sistema vem com as seguintes categorias pré-configuradas:

**Receitas:**
- Salário
- Freelance
- Investimentos

**Despesas:**
- Alimentação
- Moradia
- Transporte
- Lazer
- Saúde
- Educação

## Solução de Problemas

### Erro de Conexão com Banco
- Verifique se o MySQL está rodando
- Confirme as credenciais no `DatabaseConnection.java`
- Certifique-se de que o banco `financas_pessoais` foi criado

### Erro de Compilação
- Verifique se o Java 17 está instalado
- Execute `mvn clean` antes de compilar novamente

### Interface não Carrega
- Verifique se todas as dependências foram baixadas
- Execute `mvn dependency:resolve`

## Contribuição

Para contribuir com o projeto:

1. Faça um fork do repositório
2. Crie uma branch para sua feature
3. Commit suas mudanças
4. Abra um Pull Request

## Licença

Este projeto está sob a licença MIT.
