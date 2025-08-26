# 🚀 EnergizaWeb - Sistema de Gestão de Energia Elétrica

## 📋 Descrição do Projeto

O **EnergizaWeb** é um sistema web completo para gestão de faturas de energia elétrica, desenvolvido em Java com arquitetura MVC e interface moderna. O sistema permite o upload de PDFs de faturas, extração automática de dados, entrada manual de informações e gerenciamento completo do ciclo de vida das faturas.

## ✨ Funcionalidades Principais

### 🔐 Sistema de Autenticação
- Login e logout de usuários
- Gerenciamento de sessões
- Controle de acesso baseado em usuário

### 📊 Gestão de Faturas
- **Upload de PDFs**: Extração automática de dados de faturas
- **Formulário Manual**: Entrada manual de dados de faturas
- **Validação**: Verificação automática de campos obrigatórios
- **Persistência**: Salvamento no banco de dados com relacionamentos

### 🏠 Unidades de Consumo
- Cadastro de unidades de consumo
- Associação com usuários proprietários
- Validação de propriedade antes de salvar faturas

### 📈 Relatórios e Visualização
- Listagem de faturas com filtros
- Tabelas organizadas por data
- Formatação de dados para exibição

## 🏗️ Arquitetura do Sistema

### Frontend
- **HTML5**: Estrutura semântica e responsiva
- **CSS3**: Estilos modernos com Bootstrap 4.6.0
- **JavaScript**: Lógica de interface e comunicação com backend
- **FontAwesome**: Ícones profissionais

### Backend
- **Java Servlets**: Controle de requisições HTTP
- **JSP**: Geração dinâmica de conteúdo
- **DAO Pattern**: Acesso organizado ao banco de dados
- **Session Management**: Controle de sessões de usuário

### Banco de Dados
- **SQL Server**: Banco de dados principal
- **Tabelas Relacionadas**: Fatura, FT_ITENS_FATURA, FT_LEITURA
- **Integridade Referencial**: Chaves estrangeiras e constraints

## 📁 Estrutura do Projeto

```
EnergizaWeb/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/energiza/EnergizaWeb/
│   │   │       ├── modelos/           # Classes de modelo (POJOs)
│   │   │       │   ├── acesso/        # Autenticação e sessão
│   │   │       │   ├── minhaFatura/   # Modelos de faturas
│   │   │       │   ├── pessoa/        # Dados de pessoas
│   │   │       │   ├── unidade_consumo/ # Unidades de consumo
│   │   │       │   └── usina/         # Dados de usinas
│   │   │       ├── servlets/          # Controladores HTTP
│   │   │       │   ├── login/         # Autenticação
│   │   │       │   ├── minhaConta/    # Perfil do usuário
│   │   │       │   ├── minhaFatura/   # Gestão de faturas
│   │   │       │   └── unidadeConsumo/ # Unidades de consumo
│   │   │       └── utils/             # Utilitários e helpers
│   │   ├── resources/                 # Arquivos de configuração
│   │   └── webapp/                    # Interface web
│   │       ├── styles/                # Arquivos CSS
│   │       ├── scripts/               # JavaScript
│   │       ├── imagens/               # Imagens e ícones
│   │       └── *.html                 # Páginas principais
│   └── test/                          # Testes unitários
├── pom.xml                            # Configuração Maven
└── README.md                          # Este arquivo
```

## 🗄️ Modelo de Dados

### Tabela: Fatura
- `ID_FATURA` (int): Chave primária
- `REFERENCIA` (varchar): Referência da fatura (mês/ano)
- `VENCIMENTO` (date): Data de vencimento
- `ID_UNIDADE_CONSUMO` (int): FK para unidade de consumo
- `VALOR_TOTAL` (decimal): Valor total da fatura

### Tabela: FT_ITENS_FATURA
- `ID_FT_ITENS_FATURA` (int): Chave primária
- `ITEM` (varchar): Descrição do item
- `UNIDADE` (varchar): Unidade de medida
- `QUANTIDADE` (decimal): Quantidade consumida
- `VALOR` (decimal): Valor unitário
- `ID_FATURA` (int): FK para fatura

### Tabela: FT_LEITURA
- `ID_FT_LEITURA` (int): Chave primária
- `DATA_LEITURA_ANTERIOR` (date): Data da leitura anterior
- `DATA_LEITURA_ATUAL` (date): Data da leitura atual
- `DATA_LEITURA_PROXIMA` (date): Data da próxima leitura
- `DIAS_LIDOS` (int): Período entre leituras
- `MEDIDOR` (varchar): Identificação do medidor
- `LEITURA_ANTERIOR` (decimal): Valor da leitura anterior
- `LEITURA_ACTUAL` (decimal): Valor da leitura atual
- `TOTAL_APURADO` (decimal): Consumo total apurado
- `ID_FATURA` (int): FK para fatura
- `SERVICO` (varchar): Tipo de serviço

## 🚀 Como Executar

### Pré-requisitos
- Java JDK 8 ou superior
- Apache Tomcat 8.5 ou superior
- SQL Server 2012 ou superior
- Maven 3.6 ou superior

### Configuração
1. **Clone o repositório**
   ```bash
   git clone [URL_DO_REPOSITORIO]
   cd EnergizaWeb
   ```

2. **Configure o banco de dados**
   - Crie o banco `projetoenergiza`
   - Execute os scripts SQL para criação das tabelas
   - Configure a conexão em `src/main/resources/`

3. **Compile o projeto**
   ```bash
   mvn clean compile
   ```

4. **Execute no Tomcat**
   ```bash
   mvn tomcat7:run
   ```

5. **Acesse a aplicação**
   - URL: `http://localhost:8080/EnergizaWeb`
   - Usuário padrão: [configurar]

## 🔧 Configurações

### Banco de Dados
O arquivo de conexão está em `src/main/resources/` e deve ser configurado com:
- Servidor SQL Server
- Nome do banco
- Credenciais de acesso

### Servlets
Os endpoints principais são:
- `/servlet/login` - Autenticação
- `/servlet/minhaConta` - Dados do usuário
- `/servlet/salvarFatura` - Salvar faturas
- `/servlet/listarFaturas` - Listar faturas
- `/servlet/unidadeConsumo` - Gestão de unidades

## 📝 Padrões de Código

### Java
- **Nomenclatura**: camelCase para métodos e variáveis, PascalCase para classes
- **Documentação**: JavaDoc completo para todas as classes públicas
- **Tratamento de Erros**: Try-catch com logging apropriado
- **Conexões**: Uso de try-with-resources para recursos de banco

### JavaScript
- **Funções**: Nomes descritivos e documentação JSDoc
- **Tratamento de Erros**: Promise.catch() para requisições HTTP
- **DOM**: Manipulação eficiente com seletores específicos

### HTML/CSS
- **Semântica**: Uso apropriado de tags HTML5
- **Responsividade**: Design mobile-first com Bootstrap
- **Acessibilidade**: Labels apropriados e navegação por teclado

## 🧪 Testes

### Testes Unitários
- Localização: `src/test/java/`
- Framework: JUnit 4 ou 5
- Cobertura: Mínimo 80% para classes críticas

### Testes de Integração
- Testes de servlets com MockMvc
- Testes de DAOs com banco de teste
- Validação de fluxos completos

## 📊 Métricas de Qualidade

- **Complexidade Ciclomática**: Máximo 10 por método
- **Linhas por Método**: Máximo 50 linhas
- **Cobertura de Testes**: Mínimo 80%
- **Duplicação de Código**: Máximo 5%

## 🤝 Contribuição

### Processo de Desenvolvimento
1. **Fork** do repositório
2. **Branch** para nova funcionalidade
3. **Commit** com mensagens descritivas
4. **Pull Request** com descrição detalhada

### Padrões de Commit
```
feat: nova funcionalidade
fix: correção de bug
docs: documentação
style: formatação de código
refactor: refatoração
test: testes
chore: tarefas de manutenção
```

## 📄 Licença

Este projeto está sob a licença [INSERIR_LICENÇA]. Veja o arquivo `LICENSE` para mais detalhes.

## 👥 Equipe

- **Desenvolvedor Principal**: [Nome]
- **Arquitetura**: [Nome]
- **Design**: [Nome]
- **Testes**: [Nome]

## 📞 Suporte

- **Email**: suporte@energiza.com
- **Documentação**: [URL_DOCUMENTAÇÃO]
- **Issues**: [URL_ISSUES]

## 🔄 Histórico de Versões

### v1.0.0 (2024-01-XX)
- ✅ Sistema básico de autenticação
- ✅ Gestão de faturas com upload de PDF
- ✅ CRUD completo de unidades de consumo
- ✅ Interface responsiva com Bootstrap
- ✅ Validação de dados e tratamento de erros

### Próximas Versões
- 🔄 Sistema de relatórios avançados
- 🔄 Integração com APIs de distribuidoras
- 🔄 Dashboard com métricas em tempo real
- 🔄 Notificações automáticas de vencimento

---

**EnergizaWeb** - Transformando a gestão de energia elétrica! ⚡
