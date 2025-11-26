# 📊 Diagrama UML - Sistema de Plano de Saúde

## 🏗️ Diagrama de Classes Completo

```mermaid
---
config:
  look: neo
  theme: neo
---
classDiagram
direction TB
    class Pessoa {
	    #String nome
	    #String cpf
	    #Sexo sexo
	    #LocalDate dataDeNascimento
	    #int idade
	    #String endereco
	    #String telefone
	    #String email
	    #NivelAcesso nivelAcesso
	    +getNome() String
	    +getCpf() String
	    +getIdade() int
	    +setNome(String)
	    +setCpf(String)
    }

    class PlanoSaude {
	    #PlanosDeSaude nomePlano
	    #String codigo
	    #double valorBase
	    #Cobertura cobertura
	    #int limiteConsultas
	    #boolean ativo
	    #TipoAcomodacao tipoAcomodacao
	    #Abrangencia abrangencia
	    #LocalDate dataCriacao
	    #LocalDate ultimaAtualizacao
	    +calcularMensalidade() double*
	    +getNomePlano() PlanosDeSaude
	    +getValorBase() double
	    +isAtivo() boolean
    }

    class Paciente {
	    -PlanoSaude plano
	    -String numeroCarteirinha
	    -LocalDate dataCadastro
	    -TipoSanguineo tipoSanguineo
	    -StatusPaciente status
	    -double peso
	    -double altura
	    -List~String~ alergias
	    -List~String~ doencasCronicas
	    -List~String~ historicoCirurgias
	    -List~String~ medicamentosEmUso
	    +vincularPlano(PlanoSaude)
	    +desvincularPlano()
	    +calcularIMC() double
	    +getPlanoSaude() PlanoSaude
	    +getStatus() StatusPaciente
	    +setStatus(StatusPaciente)
    }

    class Medico {
	    -Especialidades especialidade
	    -String crm
	    -LocalDate dataContratacao
	    -int salario
	    -LocalDate dataCadastro
	    +getEspecialidade() Especialidades
	    +getCrm() String
	    +setSalario(int)
    }

    class Administrador {
	    -String id
	    -String senha
	    -NivelAcesso nivelAcesso
	    +getId() String
	    +autenticar(String senha) boolean
    }

    class PlanoBasico {
	    +calcularMensalidade() double
	    +darAcesso()
    }

    class PlanoPremium {
	    -boolean atendimentoDomiciliar
	    -boolean coberturaInternacional
	    +calcularMensalidade() double
	    +darAcesso()
	    +isAtendimentoDomiciliar() boolean
    }

    class Consulta {
	    -Paciente paciente
	    -Medico medico
	    -Horario horario
	    -int idConsulta
	    -LocalTime hora
	    -LocalDate data
	    -String descricao
	    -String receita
	    -String anotacoes
	    -ConsultaStatus status
	    +getIdConsulta() int
	    +getPaciente() Paciente
	    +getMedico() Medico
	    +getStatus() ConsultaStatus
	    +setStatus(ConsultaStatus)
    }

    class Horario {
	    -int id
	    -Date dataHora
	    -boolean disponivel
	    -Paciente paciente
	    +getId() int
	    +isDisponivel() boolean
	    +ocupar()
	    +liberar()
    }

    class Sexo {
	    MASCULINO
	    FEMININO
	    OUTRO
    }

    class NivelAcesso {
	    ADMINISTRADOR
	    MEDICO
	    PACIENTE
	    INTERESSADO
    }

    class StatusPaciente {
	    ATIVO
	    INATIVO
	    BLOQUEADO
	    FALECIDO
    }

    class TipoSanguineo {
	    A_POSITIVO
	    A_NEGATIVO
	    B_POSITIVO
	    B_NEGATIVO
	    AB_POSITIVO
	    AB_NEGATIVO
	    O_POSITIVO
	    O_NEGATIVO
    }

    class Especialidades {
	    CARDIOLOGIA
	    DERMATOLOGIA
	    ORTOPEDIA
	    PEDIATRIA
	    GINECOLOGIA
	    PSIQUIATRIA
	    CLINICO_GERAL
    }

    class Cobertura {
	    AMBULATORIAL
	    HOSPITALAR
	    OBSTETRICA
	    ODONTOLOGICA
	    COMPLETA
    }

    class Abrangencia {
	    MUNICIPAL
	    ESTADUAL
	    NACIONAL
	    INTERNACIONAL
    }

    class TipoAcomodacao {
	    ENFERMARIA
	    APARTAMENTO
	    SUITE
    }

    class ConsultaStatus {
	    AGENDADA
	    CONFIRMADA
	    REALIZADA
	    CANCELADA
    }

    class PlanosDeSaude {
	    PLANO_BASICO
	    PLANO_PREMIUM
    }

	<<abstract>> Pessoa
	<<abstract>> PlanoSaude
	<<enumeration>> Sexo
	<<enumeration>> NivelAcesso
	<<enumeration>> StatusPaciente
	<<enumeration>> TipoSanguineo
	<<enumeration>> Especialidades
	<<enumeration>> Cobertura
	<<enumeration>> Abrangencia
	<<enumeration>> TipoAcomodacao
	<<enumeration>> ConsultaStatus
	<<enumeration>> PlanosDeSaude

	note for Paciente "1 Paciente pode ter 0 ou 1 Plano"
	note for Medico "1 Médico pode ter N Consultas"
	note for Consulta "1 Consulta pertence a 1 Paciente e 1 Médico"

    Pessoa <|-- Paciente : extends
    Pessoa <|-- Medico : extends
    Pessoa <|-- Administrador : extends
    PlanoSaude <|-- PlanoBasico : extends
    PlanoSaude <|-- PlanoPremium : extends
    Paciente "1" --> "0..1" PlanoSaude : tem
    Paciente --> StatusPaciente : usa
    Paciente --> TipoSanguineo : tem
    Medico --> Especialidades : tem
    Pessoa --> Sexo : tem
    Pessoa --> NivelAcesso : tem
    Consulta "N" --> "1" Paciente : atende
    Consulta "N" --> "1" Medico : realiza
    Consulta "1" --> "1" Horario : ocorre_em
    Consulta --> ConsultaStatus : possui
    Horario --> Paciente : reservado_por
    PlanoSaude --> PlanosDeSaude : tipo
    PlanoSaude --> Cobertura : possui
    PlanoSaude --> Abrangencia : tem
    PlanoSaude --> TipoAcomodacao : oferece
```

---

## 📐 Diagrama de Relacionamentos Simplificado

```
                    Pessoa (abstract)
                    /      |      \
                   /       |       \
            Paciente    Medico   Administrador
                |          |
                |          |
            PlanoSaude  Horario
            /      \       |
           /        \      |
    PlanoBasico  PlanoPremium
                            |
                        Consulta
```

---

## 🔗 Relacionamentos Detalhados

### **Herança (Generalização)**
- `Pessoa` ← `Paciente` (é um)
- `Pessoa` ← `Medico` (é um)
- `Pessoa` ← `Administrador` (é um)
- `PlanoSaude` ← `PlanoBasico` (é um)
- `PlanoSaude` ← `PlanoPremium` (é um)

### **Composição/Agregação**
- `Paciente` → `PlanoSaude` (1:0..1) - Paciente **tem** plano
- `Consulta` → `Paciente` (N:1) - Consulta **atende** paciente
- `Consulta` → `Medico` (N:1) - Consulta **realizada por** médico
- `Consulta` → `Horario` (1:1) - Consulta **ocorre em** horário
- `Horario` → `Paciente` (1:0..1) - Horário **reservado por** paciente

### **Dependência (Uso)**
- `Paciente` usa `StatusPaciente` (enum)
- `Paciente` usa `TipoSanguineo` (enum)
- `Medico` usa `Especialidades` (enum)
- `Pessoa` usa `Sexo` (enum)
- `Pessoa` usa `NivelAcesso` (enum)
- `Consulta` usa `ConsultaStatus` (enum)
- `PlanoSaude` usa `Cobertura`, `Abrangencia`, `TipoAcomodacao` (enums)

---

## 📊 Cardinalidades

| Relacionamento | Cardinalidade | Descrição |
|----------------|---------------|-----------|
| Paciente → PlanoSaude | 1:0..1 | Um paciente pode ter zero ou um plano |
| Paciente → Consulta | 1:N | Um paciente pode ter várias consultas |
| Medico → Consulta | 1:N | Um médico pode realizar várias consultas |
| Medico → Horario | 1:N | Um médico pode ter vários horários |
| Consulta → Horario | N:1 | Várias consultas podem compartilhar referência a horários (histórico) |
| Horario → Paciente | 1:0..1 | Um horário pode ser reservado por zero ou um paciente |

---

## 🎨 Diagrama de Packages (Organização)

```
br.com.sistemaPlanoSaude
│
├── model
│   ├── pessoas
│   │   ├── Pessoa (abstract)
│   │   └── Paciente
│   │
│   ├── funcionarios
│   │   ├── Medico
│   │   └── Administrador
│   │
│   ├── planos
│   │   ├── PlanoSaude (abstract)
│   │   ├── PlanoBasico
│   │   └── PlanoPremium
│   │
│   ├── consulta
│   │   ├── Consulta
│   │   └── Horario
│   │
│   └── enums
│       ├── Sexo
│       ├── NivelAcesso
│       ├── StatusPaciente
│       ├── TipoSanguineo
│       ├── Especialidades
│       ├── PlanosDeSaude
│       ├── Cobertura
│       ├── Abrangencia
│       ├── TipoAcomodacao
│       └── ConsultaStatus
│
├── service
│   ├── PacienteService
│   ├── MedicoService
│   ├── ConsultaService
│   ├── HorarioService
│   ├── AgendaService
│   └── PlanoDeSaudeService
│
├── database
│   ├── PacienteDataBase
│   ├── FuncionariosDataBase
│   ├── ConsultaDatabase
│   ├── AgendaDataBase
│   └── LogDatabase
│
├── view
│   ├── menu
│   │   └── MenuPrincipal
│   │
│   ├── interfaces
│   │   ├── InterfaceAdministrador
│   │   ├── InterfaceMedico
│   │   ├── InterfacePaciente
│   │   └── InterfaceConsulta
│   │
│   ├── formularios
│   │   ├── FormularioPaciente
│   │   ├── FormularioMedico
│   │   ├── FormularioAdministrador
│   │   ├── FormularioPlanoDeSaude
│   │   └── FormularioAgendaMedico
│   │
│   ├── consulta
│   │   ├── GeracaoConsultaPaciente
│   │   ├── GeracaoConsultaMedico
│   │   └── GeracaoConsultaAdministrador
│   │
│   └── admin
│       ├── AdminPacienteView
│       ├── AdminMedicoView
│       └── AdminPlanoView
│
├── util
│   ├── ConsoleColors
│   ├── ValidacaoUtil
│   └── PacienteMockUtil
│
└── main
    └── Main
```

---

## 🔄 Diagrama de Sequência - Agendar Consulta

```mermaid
sequenceDiagram
    actor Paciente
    participant View as InterfacePaciente
    participant CS as ConsultaService
    participant HS as HorarioService
    participant PS as PacienteService
    participant DB as ConsultaDatabase
    
    Paciente->>View: Escolhe "Agendar Consulta"
    View->>View: Exibe especialidades
    Paciente->>View: Seleciona especialidade
    View->>HS: buscarHorariosDisponiveis(especialidade)
    HS-->>View: Lista de horários livres
    View->>View: Exibe horários disponíveis
    Paciente->>View: Seleciona horário + Descrição
    View->>PS: buscarPorCpf(cpf)
    PS-->>View: Paciente
    View->>CS: agendarConsulta(horario, paciente, descricao)
    CS->>CS: validarPacienteAtivo()
    CS->>CS: validarHorarioDisponivel()
    CS->>CS: verificarCoberturaPlan()
    CS->>DB: cadastrar(consulta)
    DB-->>CS: Consulta criada
    CS->>HS: ocuparHorario(horario)
    CS-->>View: Consulta agendada com sucesso
    View-->>Paciente: Exibe confirmação
```

---

## 📈 Métricas do Sistema

| Métrica | Valor |
|---------|-------|
| Total de Classes | 29 |
| Classes Abstratas | 2 (Pessoa, PlanoSaude) |
| Classes Concretas | 17 |
| Enums | 10 |
| Interfaces | 0 |
| Herança (profundidade máxima) | 2 níveis |
| Relacionamentos | 25+ |

---

## 🏛️ Padrões de Design Aplicados

### **1. Template Method**
- `PlanoSaude.calcularMensalidade()` - Método abstrato implementado por subclasses

### **2. Strategy**
- Diferentes estratégias de cálculo de mensalidade (PlanoBasico vs PlanoPremium)

### **3. Inheritance (Herança)**
- Hierarquia `Pessoa` → `Paciente/Medico/Administrador`
- Hierarquia `PlanoSaude` → `PlanoBasico/PlanoPremium`

### **4. Repository**
- Classes Database atuam como repositories (PacienteDataBase, etc)

### **5. Service Layer**
- Camada de serviços isolando lógica de negócio (PacienteService, etc)

### **6. Enum Pattern**
- Uso extensivo de enums para valores fixos (StatusPaciente, Especialidades, etc)

---

**Gerado em:** 25/11/2025  
**Versão:** 1.0.0  
**Sistema:** Gerenciador de Plano de Saúde
