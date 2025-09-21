# 💳 Sistema de Finanças Pessoais

<div align="center">

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![Maven](https://img.shields.io/badge/Maven-3.6+-blue?style=for-the-badge&logo=apache-maven)
![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue?style=for-the-badge&logo=mysql)
![Swing](https://img.shields.io/badge/Swing-Desktop-green?style=for-the-badge&logo=java)
![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)

**Um sistema desktop moderno e intuitivo para controle completo de suas finanças pessoais**

[🚀 Instalação](#-instalação-e-configuração) • [📖 Documentação](#-como-usar) • [🛠️ Tecnologias](#-tecnologias-utilizadas) • [🤝 Contribuição](#-contribuição)

</div>

---

## 📋 Sobre o Projeto

O **Sistema de Finanças Pessoais** é uma aplicação desktop desenvolvida em Java que oferece uma solução completa para o gerenciamento de suas finanças pessoais. Com interface moderna e intuitiva, o sistema permite controlar receitas, despesas e visualizar relatórios detalhados de sua situação financeira.

### ✨ Principais Características

- 🎨 **Interface Moderna**: Design limpo e profissional com tema FlatLaf
- 📊 **Visualizações Interativas**: Gráficos responsivos com zoom e tooltips
- 💰 **Gestão Completa**: Controle total de receitas e despesas
- 🏷️ **Categorização Inteligente**: Sistema de categorias organizadas por tipo
- 📈 **Relatórios em Tempo Real**: Resumos financeiros atualizados instantaneamente
- 🔒 **Dados Seguros**: Armazenamento em banco de dados MySQL
- ⚡ **Performance Otimizada**: Interface responsiva e fluida

---

## 🚀 Funcionalidades

### 💸 Gestão de Transações
- ✅ Adicionar receitas e despesas com detalhes completos
- ✅ Categorização automática baseada no tipo de transação
- ✅ Validação de dados em tempo real
- ✅ Histórico completo de transações
- ✅ Formatação monetária brasileira (R$)

### 📊 Visualizações e Relatórios
- ✅ **Dashboard Financeiro**: Saldo, receitas e despesas totais
- ✅ **Gráfico Interativo**: Distribuição visual com zoom e detalhes
- ✅ **Tooltips Informativos**: Valores e percentuais em tempo real
- ✅ **Cores Temáticas**: Verde para receitas, vermelho para despesas
- ✅ **Atualizações Automáticas**: Dados sempre sincronizados

### 🎨 Interface do Usuário
- ✅ **Design Responsivo**: Adapta-se a diferentes tamanhos de tela
- ✅ **Tema Moderno**: FlatLaf com cores profissionais
- ✅ **Navegação Intuitiva**: Tabs organizadas e tooltips
- ✅ **Feedback Visual**: Animações suaves e transições
- ✅ **Acessibilidade**: Interface clara e fácil de usar

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Versão | Descrição |
|------------|--------|-----------|
| **Java** | 17+ | Linguagem de programação principal |
| **Swing** | - | Framework para interface gráfica |
| **FlatLaf** | 3.2+ | Tema moderno para Swing |
| **JFreeChart** | 1.5+ | Biblioteca para criação de gráficos |
| **MySQL** | 8.0+ | Banco de dados relacional |
| **Maven** | 3.6+ | Gerenciamento de dependências |
| **JDBC** | - | Conectividade com banco de dados |

---

## 📋 Pré-requisitos

Antes de começar, certifique-se de ter instalado:

- ☕ **Java 17 ou superior** ([Download](https://adoptium.net/))
- 🗄️ **MySQL 8.0 ou superior** ([Download](https://dev.mysql.com/downloads/))
- 📦 **Maven 3.6 ou superior** ([Download](https://maven.apache.org/download.cgi))
- 💻 **Sistema Operacional**: Windows, macOS ou Linux

---

## 🚀 Instalação e Configuração

### 1️⃣ Clone o Repositório

```bash
git clone https://github.com/seu-usuario/sistemas-de-financas-pessoais.git
cd sistemas-de-financas-pessoais
```

### 2️⃣ Configurar o Banco de Dados

#### Instalar e Configurar MySQL
1. Instale o MySQL Server
2. Inicie o serviço MySQL
3. Acesse o MySQL como root:
```bash
mysql -u root -p
```

#### Criar o Banco de Dados
```sql
-- Criar banco de dados
CREATE DATABASE financas_pessoais;
USE financas_pessoais;

-- Executar o script de criação das tabelas
SOURCE src/main/resources/schema.sql;
```

### 3️⃣ Configurar Conexão

Edite o arquivo `src/main/java/com/financaspessoais/database/DatabaseConnection.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/financas_pessoais";
private static final String USER = "seu_usuario_mysql";
private static final String PASSWORD = "sua_senha_mysql";
```

### 4️⃣ Compilar e Executar

#### Opção 1: Via Maven (Recomendado)
```bash
# Compilar o projeto
mvn clean compile

# Executar a aplicação
mvn exec:java -Dexec.mainClass=com.financaspessoais.App
```

#### Opção 2: Via Scripts
```bash
# Windows
.\run.bat

# Linux/macOS
./run.sh
```

#### Opção 3: JAR Executável
```bash
# Criar JAR
mvn clean package

# Executar JAR
java -jar target/finance-app-1.0-SNAPSHOT.jar
```

---

## 📖 Como Usar

### 🏠 Tela Principal

A aplicação possui uma interface moderna com duas abas principais:

- **💰 Transações**: Para gerenciar receitas e despesas
- **📊 Resumo**: Para visualizar relatórios e gráficos

### ➕ Adicionando Transações

1. **Acesse a aba "Transações"**
2. **Preencha o formulário:**
   - **Tipo**: Selecione RECEITA ou DESPESA
   - **Descrição**: Descreva a transação (ex: "Salário", "Supermercado")
   - **Valor**: Digite o valor (aceita vírgula ou ponto decimal)
   - **Categoria**: Escolha uma categoria baseada no tipo
   - **Data**: Data da transação (formato: dd/MM/yyyy)
3. **Clique em "Adicionar Transação"**

### 📊 Visualizando Relatórios

1. **Acesse a aba "Resumo"**
2. **Visualize as informações:**
   - **Saldo Total**: Valor atual (verde = positivo, vermelho = negativo)
   - **Receitas**: Total de todas as receitas
   - **Despesas**: Total de todas as despesas
   - **Gráfico**: Distribuição visual interativa

### 🎯 Interagindo com o Gráfico

- **🖱️ Clique**: Clique em uma seção para ver detalhes
- **🔍 Zoom**: Use a roda do mouse para zoom in/out
- **💡 Tooltip**: Passe o mouse para ver informações detalhadas
- **📱 Responsivo**: Redimensione a janela para adaptar o gráfico

---

## 📁 Estrutura do Projeto

```
📦 sistemas-de-financas-pessoais/
├── 📄 pom.xml                           # Configuração Maven
├── 📄 README.md                         # Documentação
├── 📄 run.bat / run.sh                  # Scripts de execução
├── 📁 src/
│   ├── 📁 main/
│   │   ├── 📁 java/com/financaspessoais/
│   │   │   ├── 📄 App.java              # Classe principal
│   │   │   ├── 📁 controller/
│   │   │   │   └── 📄 FinanceController.java
│   │   │   ├── 📁 dao/
│   │   │   │   ├── 📄 CategoriaDAO.java
│   │   │   │   └── 📄 TransacaoDAO.java
│   │   │   ├── 📁 database/
│   │   │   │   └── 📄 DatabaseConnection.java
│   │   │   ├── 📁 model/
│   │   │   │   ├── 📄 Categoria.java
│   │   │   │   └── 📄 Transacao.java
│   │   │   ├── 📁 util/
│   │   │   │   ├── 📄 DateUtil.java
│   │   │   │   └── 📄 ThemeConfig.java
│   │   │   └── 📁 view/
│   │   │       ├── 📄 MainFrame.java
│   │   │       ├── 📄 ResumoPanel.java
│   │   │       └── 📄 TransacaoPanel.java
│   │   └── 📁 resources/
│   │       └── 📄 schema.sql            # Script do banco
│   └── 📁 test/                         # Testes (futuro)
└── 📁 target/                           # Arquivos compilados
```

---

## 🏷️ Categorias Padrão

O sistema vem com categorias pré-configuradas para facilitar o uso:

### 💰 Receitas
- 💼 **Salário**: Renda do trabalho formal
- 🎯 **Freelance**: Trabalhos autônomos
- 📈 **Investimentos**: Dividendos, juros, etc.
- 🎁 **Presentes**: Dinheiro recebido como presente
- 💸 **Outros**: Outras fontes de receita

### 💸 Despesas
- 🍽️ **Alimentação**: Supermercado, restaurantes
- 🏠 **Moradia**: Aluguel, condomínio, IPTU
- 🚗 **Transporte**: Combustível, transporte público
- 🎮 **Lazer**: Entretenimento, hobbies
- 🏥 **Saúde**: Médicos, medicamentos, plano de saúde
- 📚 **Educação**: Cursos, livros, mensalidades
- 👕 **Vestuário**: Roupas, calçados, acessórios
- 💡 **Utilidades**: Luz, água, internet, telefone

---

## 🔧 Solução de Problemas

### ❌ Erro de Conexão com Banco

**Problema**: `java.sql.SQLException: Access denied`

**Soluções**:
1. ✅ Verifique se o MySQL está rodando
2. ✅ Confirme as credenciais no `DatabaseConnection.java`
3. ✅ Certifique-se de que o banco `financas_pessoais` foi criado
4. ✅ Teste a conexão manualmente:
```sql
mysql -u seu_usuario -p financas_pessoais
```

### ❌ Erro de Compilação

**Problema**: `Maven compilation failed`

**Soluções**:
1. ✅ Verifique se o Java 17+ está instalado: `java -version`
2. ✅ Execute `mvn clean` antes de compilar
3. ✅ Verifique se o Maven está configurado: `mvn -version`
4. ✅ Baixe as dependências: `mvn dependency:resolve`

### ❌ Interface não Carrega

**Problema**: Janela não abre ou aparece em branco

**Soluções**:
1. ✅ Verifique se todas as dependências foram baixadas
2. ✅ Execute `mvn clean compile`
3. ✅ Verifique os logs de erro no console
4. ✅ Teste com `mvn exec:java -Dexec.mainClass=com.financaspessoais.App`

### ❌ Gráfico não Aparece

**Problema**: Gráfico em branco ou com erro

**Soluções**:
1. ✅ Adicione algumas transações primeiro
2. ✅ Verifique se há dados no banco
3. ✅ Reinicie a aplicação
4. ✅ Verifique se o JFreeChart está funcionando

---

## 🚀 Funcionalidades Futuras

- [ ] 📅 **Relatórios por Período**: Filtros por mês, ano, período customizado
- [ ] 📊 **Mais Tipos de Gráfico**: Barras, linhas, área
- [ ] 💾 **Backup e Restore**: Exportar/importar dados
- [ ] 🎯 **Metas Financeiras**: Definir e acompanhar objetivos
- [ ] 📱 **Versão Web**: Interface web responsiva
- [ ] 🔄 **Sincronização**: Backup automático na nuvem
- [ ] 📈 **Análise de Tendências**: Previsões e análises avançadas
- [ ] 🏦 **Integração Bancária**: Importar extratos automaticamente
- [ ] 📊 **Relatórios PDF**: Gerar relatórios em PDF
- [ ] 🌍 **Multi-idioma**: Suporte a diferentes idiomas

---

## 🤝 Contribuição

Contribuições são sempre bem-vindas! Para contribuir:

### 🔀 Como Contribuir

1. **🍴 Fork** o projeto
2. **🌿 Crie** uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. **💾 Commit** suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. **📤 Push** para a branch (`git push origin feature/AmazingFeature`)
5. **🔀 Abra** um Pull Request

### 📝 Padrões de Código

- ✅ Use nomes descritivos para variáveis e métodos
- ✅ Comente código complexo
- ✅ Siga as convenções do Java
- ✅ Teste suas mudanças antes de submeter
- ✅ Mantenha a consistência com o código existente

### 🐛 Reportando Bugs

Ao reportar bugs, inclua:
- 📱 Sistema operacional e versão
- ☕ Versão do Java
- 🗄️ Versão do MySQL
- 📋 Passos para reproduzir o erro
- 📸 Screenshots (se aplicável)
- 📄 Logs de erro completos

---

## 📄 Licença

Este projeto está licenciado sob a **Licença MIT** - veja o arquivo [LICENSE](LICENSE) para detalhes.

```
MIT License

Copyright (c) 2024 Sistema de Finanças Pessoais

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

---

## 👨‍💻 Autor

**Desenvolvedor** - [Aron Alvarenga](https://github.com/aron-alvarenga)

- 💼 LinkedIn: [Aron Alvarenga](https://linkedin.com/in/aron-alvarenga)

---

## 🙏 Agradecimentos

- 🎨 **FlatLaf** - Tema moderno para Swing
- 📊 **JFreeChart** - Biblioteca de gráficos
- ☕ **Java Community** - Suporte e recursos
- 🤝 **Contribuidores** - Todos que ajudaram no projeto

---

<div align="center">

**⭐ Se este projeto foi útil para você, considere dar uma estrela! ⭐**

[⬆️ Voltar ao topo](#-sistema-de-finanças-pessoais)

</div>