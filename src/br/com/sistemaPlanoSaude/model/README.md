# 🏗️ Model Package

## 🎯 Objetivo

Esta pasta contém as **classes de domínio** do sistema - representam as entidades principais do negócio e suas regras. São os "substantivos" da aplicação: Paciente, Médico, Consulta, Plano de Saúde, etc.

## 📐 Arquitetura

Os models seguem o padrão **Domain Model**, encapsulando dados e comportamentos relacionados. São POJOs (Plain Old Java Objects) com:
- Atributos privados
- Getters/Setters
- Métodos de negócio
- Override de `toString()` para debug

```
View Layer
     ↓
Service Layer
     ↓
Model Layer (VOCÊ ESTÁ AQUI) → Define estrutura dos dados
     ↓
Database Layer
```

## 📁 Estrutura de Subpastas

### 📂 `/consulta`
Classes relacionadas ao agendamento de consultas médicas.

### 📂 `/enums`
Enumerações (valores fixos) usadas em todo o sistema.

### 📂 `/funcionarios`
Classes de funcionários (Médico, Administrador).

### 📂 `/pessoas`
Classes base de pessoas (Paciente, Pessoa).

### 📂 `/planos`
Classes de planos de saúde e suas variações.

---

## 📦 Subpasta: `/consulta`

### 🩺 **Consulta.java**
**Responsabilidade:** Representa uma consulta médica agendada.

**Atributos:**
- `id` (int) - Identificador único
- `paciente` (Paciente) - Quem será atendido
- `medico` (Medico) - Quem realizará atendimento
- `horario` (Horario) - Data/hora agendada
- `descricao` (String) - Motivo da consulta
- `status` (ConsultaStatus) - Estado atual (AGENDADA, CONFIRMADA, REALIZADA, CANCELADA)

**Métodos Importantes:**
- `confirmar()` - Altera status para CONFIRMADA
- `cancelar()` - Altera status para CANCELADA
- `finalizar()` - Altera status para REALIZADA

**Exemplo de uso:**
```java
Consulta consulta = new Consulta(paciente, medico, horario, "Dor de cabeça");
consulta.confirmar(); // Paciente confirmou presença
consulta.finalizar(); // Médico realizou atendimento
```

---

### ⏰ **Horario.java**
**Responsabilidade:** Representa um slot de tempo na agenda do médico.

**Atributos:**
- `id` (int) - Identificador único
- `medico` (Medico) - Dono da agenda
- `dataHora` (Date) - Data e hora do horário
- `disponivel` (boolean) - Se está livre ou ocupado

**Métodos Importantes:**
- `ocupar()` - Marca horário como indisponível
- `liberar()` - Marca horário como disponível novamente

**Fluxo:**
```
1. Médico cria horário → disponivel = true
2. Paciente agenda → ocupar() → disponivel = false
3. Consulta cancelada → liberar() → disponivel = true
```

---

## 📦 Subpasta: `/enums`

### 🌍 **Abrangencia.java**
Define cobertura geográfica do plano.
- `MUNICIPAL` - Apenas cidade do titular
- `ESTADUAL` - Todo o estado
- `NACIONAL` - Todo o Brasil
- `INTERNACIONAL` - Cobertura global

---

### 🏥 **Cobertura.java**
Tipos de procedimentos cobertos.
- `AMBULATORIAL` - Consultas e exames
- `HOSPITALAR` - Internações
- `OBSTETRICA` - Partos e pré-natal
- `ODONTOLOGICA` - Tratamentos dentários

---

### 📊 **ConsultaStatus.java**
Estados possíveis de uma consulta.
- `AGENDADA` - Consulta marcada
- `CONFIRMADA` - Paciente confirmou presença
- `REALIZADA` - Atendimento concluído
- `CANCELADA` - Consulta não realizada

**Transições válidas:**
```
AGENDADA → CONFIRMADA → REALIZADA
AGENDADA → CANCELADA
CONFIRMADA → CANCELADA
```

---

### 🩺 **Especialidades.java**
Especialidades médicas disponíveis.
- `CARDIOLOGIA` - Coração
- `DERMATOLOGIA` - Pele
- `ORTOPEDIA` - Ossos/articulações
- `PEDIATRIA` - Crianças
- `GINECOLOGIA` - Saúde feminina
- `PSIQUIATRIA` - Saúde mental
- `CLINICO_GERAL` - Atendimento geral

---

### 🔐 **NivelAcesso.java**
Níveis de permissão no sistema.
- `ADMINISTRADOR` - Acesso total
- `MEDICO` - Gerenciar consultas e agenda
- `PACIENTE` - Agendar consultas, ver histórico

---

### 💳 **PlanosDeSaude.java**
Operadoras de plano aceitas.
- `UNIMED`
- `AMIL`
- `BRADESCO_SAUDE`
- `SULAMERICA`
- `NOTREDAME_INTERMEDICA`

---

### 👤 **Sexo.java**
Sexo biológico.
- `MASCULINO`
- `FEMININO`
- `OUTRO`

---

### 🚦 **StatusPaciente.java**
Estado do cadastro do paciente.
- `ATIVO` - Pode agendar consultas normalmente
- `INATIVO` - Cadastro desativado (temporário)
- `BLOQUEADO` - Inadimplente ou violação de regras
- `FALECIDO` - Registro histórico

---

### 🛏️ **TipoAcomodacao.java**
Tipo de quarto em internações.
- `ENFERMARIA` - Compartilhado
- `APARTAMENTO` - Individual
- `SUITE` - Luxo

---

### 🩸 **TipoSanguineo.java**
Tipos sanguíneos.
- `A_POSITIVO`, `A_NEGATIVO`
- `B_POSITIVO`, `B_NEGATIVO`
- `AB_POSITIVO`, `AB_NEGATIVO`
- `O_POSITIVO`, `O_NEGATIVO`

---

## 📦 Subpasta: `/funcionarios`

### 👨‍💼 **Administrador.java**
**Herda de:** `Pessoa`

**Responsabilidade:** Usuário com poderes administrativos.

**Atributos Adicionais:**
- `id` (String) - ID único do administrador
- `senha` (String) - Senha de acesso
- `nivelAcesso` (NivelAcesso) - Sempre ADMINISTRADOR

**Permissões:**
- ✅ Cadastrar/remover médicos e pacientes
- ✅ Gerenciar planos de saúde
- ✅ Alterar status de pacientes
- ✅ Visualizar relatórios e logs
- ✅ Configurações do sistema

---

### 👨‍⚕️ **Medico.java**
**Herda de:** `Pessoa`

**Responsabilidade:** Profissional que realiza atendimentos.

**Atributos Adicionais:**
- `crm` (String) - Registro profissional (único)
- `especialidade` (Especialidades) - Área de atuação
- `senha` (String) - Senha de acesso
- `agendaHorarios` (List<Horario>) - Horários disponíveis

**Métodos Importantes:**
- `adicionarHorario(Horario)` - Cria novo slot na agenda
- `removerHorario(int id)` - Remove horário
- `listarHorariosDisponiveis()` - Filtra horários livres

**Exemplo:**
```java
Medico medico = new Medico("Dr. João", "123456-SP", Especialidades.CARDIOLOGIA);
medico.adicionarHorario(new Horario(medico, dataHora, true));
```

---

## 📦 Subpasta: `/pessoas`

### 👤 **Pessoa.java** (Classe Abstrata)
**Responsabilidade:** Classe base para todas as pessoas do sistema.

**Atributos:**
- `nome` (String)
- `cpf` (String)
- `dataNascimento` (Date)
- `sexo` (Sexo)
- `telefone` (String)
- `email` (String)
- `endereco` (String)

**Métodos:**
- `calcularIdade()` - Retorna idade baseada na data de nascimento
- `validarCpf()` - Verifica se CPF é válido

**Hierarquia:**
```
Pessoa (abstrata)
├── Paciente
├── Medico
└── Administrador
```

---

### 🏥 **Paciente.java**
**Herda de:** `Pessoa`

**Responsabilidade:** Usuário que utiliza serviços médicos.

**Atributos Adicionais:**
- `numeroCarteirinha` (String) - ID no plano de saúde
- `planoSaude` (PlanoSaude) - Plano contratado
- `tipoSanguineo` (TipoSanguineo)
- `status` (StatusPaciente) - Estado do cadastro
- `historicoConsultas` (List<Consulta>) - Consultas realizadas

**Métodos Importantes:**
- `ativar()` - Define status como ATIVO
- `bloquear()` - Define status como BLOQUEADO
- `adicionarConsulta(Consulta)` - Adiciona ao histórico

**Regra de Negócio:**
- Paciente com status BLOQUEADO ou FALECIDO **não pode** agendar consultas
- Paciente INATIVO pode reativar cadastro

---

## 📦 Subpasta: `/planos`

### 💳 **PlanoSaude.java** (Classe Abstrata)
**Responsabilidade:** Classe base para todos os planos.

**Atributos:**
- `operadora` (PlanosDeSaude) - Empresa do plano
- `numeroPlano` (String) - Código único
- `titular` (Paciente) - Dono do plano
- `abrangencia` (Abrangencia) - Cobertura geográfica
- `coberturas` (List<Cobertura>) - Serviços inclusos
- `valorMensalidade` (double) - Preço mensal

**Métodos Abstratos:**
- `calcularCopagamento()` - Valor a pagar por consulta
- `verificarCobertura(String procedimento)` - Checa se está coberto

---

### 🥉 **PlanoBasico.java**
**Herda de:** `PlanoSaude`

**Características:**
- 💰 Mensalidade mais barata
- 🏥 Cobertura: AMBULATORIAL + HOSPITALAR
- 🌍 Abrangência: MUNICIPAL ou ESTADUAL
- 🛏️ Acomodação: ENFERMARIA
- 💵 Copagamento: 30% do valor da consulta

**Limitações:**
- ❌ Sem cobertura obstétrica
- ❌ Sem cobertura odontológica
- ❌ Sem atendimento internacional

---

### 🥇 **PlanoPremium.java**
**Herda de:** `PlanoSaude`

**Características:**
- 💰 Mensalidade mais cara
- 🏥 Cobertura: TODAS (ambulatorial, hospitalar, obstétrica, odontológica)
- 🌍 Abrangência: NACIONAL ou INTERNACIONAL
- 🛏️ Acomodação: APARTAMENTO ou SUITE
- 💵 Copagamento: 0% (sem custo adicional)

**Vantagens:**
- ✅ Atendimento prioritário
- ✅ Reembolso de consultas particulares
- ✅ Checkup anual gratuito
- ✅ Telemedicina 24h

---

## 🔗 Relacionamentos Entre Classes

```
Paciente → PlanoSaude (1:1)
Paciente → Consulta (1:N)
Medico → Consulta (1:N)
Medico → Horario (1:N)
Consulta → Horario (N:1)
```

## 📊 Diagrama de Classes Simplificado

```
        Pessoa (abstract)
        /      |      \
   Paciente  Medico  Administrador
      |        |
      |        |
   PlanoSaude  Horario
   /      \       \
PlanoBasico  PlanoPremium  Consulta
```

## 🎨 Princípios Aplicados

✅ **Encapsulamento:** Atributos privados com getters/setters  
✅ **Herança:** Pessoa → Paciente/Medico/Administrador  
✅ **Polimorfismo:** PlanoSaude com implementações diferentes  
✅ **Single Responsibility:** Cada classe tem um propósito claro  
✅ **Enums:** Valores fixos para evitar strings mágicas  

## 🧪 Exemplo de Uso Completo

```java
// Criar paciente
Paciente paciente = new Paciente("Maria", "123.456.789-00");
paciente.setSexo(Sexo.FEMININO);
paciente.setTipoSanguineo(TipoSanguineo.O_POSITIVO);

// Atribuir plano
PlanoSaude plano = new PlanoPremium(PlanosDeSaude.UNIMED, "987654", paciente);
paciente.setPlanoSaude(plano);

// Criar médico
Medico medico = new Medico("Dr. João", "654321-SP", Especialidades.CARDIOLOGIA);

// Criar horário
Horario horario = new Horario(medico, new Date(), true);
medico.adicionarHorario(horario);

// Agendar consulta
Consulta consulta = new Consulta(paciente, medico, horario, "Dor no peito");
consulta.confirmar();
```

---

**Última atualização:** 25/11/2025
