# 📦 Database Package

## 🎯 Objetivo

Esta pasta contém as classes responsáveis pela **camada de persistência de dados** do sistema. Cada classe Database atua como um repositório em memória, gerenciando operações CRUD (Create, Read, Update, Delete) para suas respectivas entidades.

## 🏗️ Arquitetura

A camada de Database implementa o padrão **Repository**, isolando a lógica de acesso a dados do resto da aplicação. Atualmente utiliza **armazenamento em memória** (listas Java), mas pode ser facilmente migrada para banco de dados SQL/NoSQL no futuro.

```
Service Layer
     ↓
Database Layer (VOCÊ ESTÁ AQUI)
     ↓
Data Storage (ArrayList in-memory)
```

## 📂 Classes

### 1️⃣ **AgendaDataBase.java**
**Responsabilidade:** Gerenciar horários disponíveis dos médicos.

**Operações:**
- ✅ `adicionarHorario(Horario)` - Adiciona novo horário disponível
- 🔍 `buscarHorariosPorMedico(String crm)` - Lista horários de um médico específico
- 🔍 `buscarHorarioPorId(int id)` - Busca horário por ID
- ❌ `removerHorario(int id)` - Remove horário da agenda
- 📋 `listarTodos()` - Retorna todos os horários cadastrados

**Exemplo de uso:**
```java
Horario horario = new Horario(medico, dataHora, true);
agendaDB.adicionarHorario(horario);
```

---

### 2️⃣ **ConsultaDatabase.java**
**Responsabilidade:** Gerenciar consultas médicas agendadas.

**Operações:**
- ✅ `cadastrar(Consulta)` - Registra nova consulta
- 🔍 `buscarPorPaciente(String cpf)` - Lista consultas de um paciente
- 🔍 `buscarPorMedico(String crm)` - Lista consultas de um médico
- 🔄 `atualizarStatus(int id, ConsultaStatus)` - Altera status da consulta
- ❌ `cancelar(int id)` - Cancela consulta
- 📋 `listarTodas()` - Retorna todas as consultas

**Estados de Consulta:**
- `AGENDADA` → `CONFIRMADA` → `REALIZADA`
- `AGENDADA` → `CANCELADA`

---

### 3️⃣ **FuncionariosDataBase.java**
**Responsabilidade:** Gerenciar cadastro de médicos e administradores.

**Operações:**
- ✅ `cadastrarMedico(Medico)` - Adiciona novo médico
- ✅ `cadastrarAdministrador(Administrador)` - Adiciona novo administrador
- 🔍 `buscarMedicoPorCrm(String crm)` - Localiza médico pelo CRM
- 🔍 `buscarAdministradorPorId(String id)` - Localiza administrador
- 🔐 `autenticarMedico(String crm, String senha)` - Valida login médico
- 🔐 `autenticarAdministrador(String id, String senha)` - Valida login admin
- 📋 `listarMedicos()` / `listarAdministradores()` - Lista funcionários
- ❌ `removerMedico(String crm)` - Remove médico do sistema

**Autenticação:**
```java
Medico medico = funcionariosDB.autenticarMedico("123456-SP", "senha123");
if (medico != null) {
    // Login válido
}
```

---

### 4️⃣ **LogDatabase.java**
**Responsabilidade:** Registrar histórico de ações no sistema (auditoria).

**Operações:**
- ✅ `registrar(String acao, String usuario, String detalhes)` - Grava log
- 🔍 `buscarPorUsuario(String usuario)` - Filtra logs por usuário
- 🔍 `buscarPorPeriodo(Date inicio, Date fim)` - Filtra logs por data
- 📋 `listarTodos()` - Exibe todos os logs

**Exemplo:**
```java
logDB.registrar("CADASTRO_PACIENTE", "admin001", "CPF: 123.456.789-00");
logDB.registrar("CANCELAMENTO_CONSULTA", "medico@crm", "ID Consulta: 42");
```

**Utilidade:** Rastreabilidade de ações sensíveis (LGPD compliance).

---

### 5️⃣ **PacienteDataBase.java**
**Responsabilidade:** Gerenciar cadastro de pacientes e status.

**Operações:**
- ✅ `cadastrar(Paciente)` - Adiciona novo paciente
- 🔍 `buscarPorCpf(String cpf)` - Localiza paciente pelo CPF
- 🔍 `buscarPorCarteirinha(String carteirinha)` - Busca por número da carteirinha
- 🔄 `atualizarStatus(String cpf, StatusPaciente)` - Altera status (ATIVO/INATIVO/BLOQUEADO/FALECIDO)
- 🔄 `desbloquearPaciente(String cpf)` - Define status como ATIVO
- 🔄 `bloquearPaciente(String cpf)` - Define status como BLOQUEADO
- 🔄 `desativarPaciente(String cpf)` - Define status como INATIVO
- 🔄 `marcarComoFalecido(String cpf)` - Define status como FALECIDO
- ❌ `remover(String cpf)` - Remove paciente do sistema
- 📋 `listarTodos()` / `listarAtivos()` - Lista pacientes

**Status de Paciente:**
- `ATIVO` - Pode agendar consultas
- `INATIVO` - Cadastro desativado temporariamente
- `BLOQUEADO` - Inadimplência ou violação de regras
- `FALECIDO` - Registro histórico, não permite agendamentos

---

## 🔐 Segurança

⚠️ **Nota Importante:** Atualmente as senhas são armazenadas em **texto plano**. Em produção, implementar:
- Hash com BCrypt/Argon2
- Salt único por usuário
- Política de senhas fortes

## 🔄 Migração Futura

Objetivo de migrar para banco de dados real no futuro:

1. **SQL (MySQL/PostgreSQL):**
   ```java
   // Substituir ArrayList por JDBC/JPA
   Connection conn = DriverManager.getConnection(url);
   PreparedStatement stmt = conn.prepareStatement("INSERT INTO pacientes...");
   ```

2. **NoSQL (MongoDB):**
   ```java
   // Substituir ArrayList por MongoDB Driver
   MongoCollection<Document> collection = database.getCollection("pacientes");
   collection.insertOne(pacienteDocument);
   ```

3. **ORM (Hibernate):**
   ```java
   @Entity
   @Table(name = "pacientes")
   public class Paciente { ... }
   ```

## 📊 Estrutura de Dados

Todas as classes utilizam `ArrayList` para armazenamento:

```java
private List<Paciente> pacientes = new ArrayList<>();
private List<Medico> medicos = new ArrayList<>();
private List<Consulta> consultas = new ArrayList<>();
```

**Vantagens:**
- ✅ Simples de implementar
- ✅ Sem dependências externas
- ✅ Bom para testes/protótipos

**Desvantagens:**
- ❌ Dados perdidos ao fechar aplicação
- ❌ Não escalável para produção


## 📝 Convenções

- **Métodos de busca** retornam `null` se não encontrado
- **Métodos de listagem** retornam lista vazia (nunca `null`)
- **CPF/CRM** devem ser únicos (validados no Service)
- **IDs** são gerados automaticamente (incrementais)

---

**Última atualização:** 25/11/2025
