# 🖥️ View Package

## 🎯 Objetivo

Esta pasta contém a **camada de apresentação** do sistema - toda interação com o usuário acontece aqui. São as "telas" da aplicação (mesmo sendo console), responsáveis por capturar entrada, exibir informações e navegar entre funcionalidades.

## 📐 Arquitetura

As views seguem o padrão **MVC (Model-View-Controller)**, sendo a camada **View**. Comunicam-se APENAS com a Service Layer, nunca diretamente com Database.

```
User (Console Input/Output)
     ↕
View Layer (VOCÊ ESTÁ AQUI) → Interface + Navegação
     ↓
Service Layer (Lógica de negócio)
     ↓
Database Layer (Persistência)
```

## 🎭 Responsabilidades

### O que a View Layer FAZ:
✅ **Capturar entrada do usuário** (Scanner)  
✅ **Validar formato** (ex: CPF tem 11 dígitos)  
✅ **Exibir informações** formatadas (menus, tabelas, mensagens)  
✅ **Navegação** entre telas (menus, submenu)  
✅ **Feedback visual** (cores, emojis, mensagens de sucesso/erro)  

### O que a View Layer NÃO FAZ:
❌ **Regras de negócio** (ex: "paciente bloqueado não pode agendar")  
❌ **Acesso ao banco de dados** (sempre via Service)  
❌ **Processamento complexo** (cálculos, validações complexas)  

---

## 📁 Estrutura de Subpastas

### 📂 `/menu`
Menus principais de navegação do sistema.

### 📂 `/interfaces`
Interfaces específicas para cada tipo de usuário (Admin, Médico, Paciente).

### 📂 `/formularios`
Formulários de cadastro e edição de dados.

### 📂 `/consulta`
Telas de gerenciamento de consultas médicas.

### 📂 `/admin`
Painéis administrativos para gerenciamento do sistema.

---

## 📦 Subpasta: `/menu`

### 🏠 **MenuPrincipal.java**
**Responsabilidade:** Ponto de entrada da aplicação - tela inicial.

**Fluxo:**
```
╔════════════════════════════════════╗
║   SISTEMA DE PLANO DE SAÚDE       ║
╠════════════════════════════════════╣
║ 1 - Login como Administrador      ║
║ 2 - Login como Médico             ║
║ 3 - Login como Paciente           ║
║ 4 - Cadastro de Novo Paciente     ║
║ 5 - Sobre o Sistema               ║
║ 0 - Sair                          ║
╚════════════════════════════════════╝
```

**Métodos:**
- `exibirMenu()` - Desenha menu principal
- `processarOpcao(int opcao)` - Roteia para tela correta
- `realizarLogin(TipoUsuario tipo)` - Chama interface de login

**Navegação:**
```java
switch (opcao) {
    case 1 -> new InterfaceAdministrador().executar();
    case 2 -> new InterfaceMedico().executar();
    case 3 -> new InterfacePaciente().executar();
    case 4 -> new FormularioPaciente().cadastrar();
}
```

---

## 📦 Subpasta: `/interfaces`

### 👨‍💼 **InterfaceAdministrador.java**
**Responsabilidade:** Painel de controle do administrador.

**Menu:**
```
╔════════════════════════════════════╗
║      PAINEL ADMINISTRATIVO        ║
╠════════════════════════════════════╣
║ 1 - Gerenciar Pacientes           ║
║ 2 - Gerenciar Médicos             ║
║ 3 - Gerenciar Planos de Saúde     ║
║ 4 - Relatórios e Estatísticas     ║
║ 5 - Visualizar Logs do Sistema    ║
║ 6 - Dados Pessoais                ║
║ 0 - Logout                        ║
╚════════════════════════════════════╝
```

**Métodos:**
- `gerenciarPacientes()` → Abre `AdminPacienteView`
- `gerenciarMedicos()` → Abre `AdminMedicoView`
- `gerenciarPlanos()` → Abre `AdminPlanoView`
- `visualizarRelatorios()` → Dashboard com estatísticas
- `visualizarLogs()` → Histórico de ações

**Características:**
- ✅ Acesso total ao sistema
- ✅ Pode criar/editar/remover usuários
- ✅ Visualiza dados sensíveis

---

### 👨‍⚕️ **InterfaceMedico.java**
**Responsabilidade:** Painel do médico logado.

**Menu:**
```
╔════════════════════════════════════╗
║      BEM-VINDO, Dr. João Silva    ║
║      CRM: 123456-SP               ║
╠════════════════════════════════════╣
║ 1 - Gerenciar Agenda              ║
║ 2 - Minhas Consultas              ║
║ 3 - Histórico de Atendimentos     ║
║ 4 - Dados Pessoais                ║
║ 0 - Logout                        ║
╚════════════════════════════════════╝
```

**Métodos:**
- `gerenciarAgenda()` → `new FormularioAgendaMedico(medicoLogado)`
- `minhasConsultas()` → `new GeracaoConsultaMedico(medicoLogado)`
- `visualizarHistorico()` → Lista consultas realizadas

**Contexto Importante:**
```java
private final Medico medicoLogado; // Passado no construtor

public InterfaceMedico(Medico medico) {
    this.medicoLogado = medico; // Armazena referência
}
```

**Por que:** Evita pedir CRM toda hora - sistema já sabe quem está logado.

---

### 🏥 **InterfacePaciente.java**
**Responsabilidade:** Painel do paciente logado.

**Menu:**
```
╔════════════════════════════════════╗
║      Olá, Maria Santos            ║
║      Carteirinha: 987654          ║
║      Status: ATIVO ✅             ║
╠════════════════════════════════════╣
║ 1 - Agendar Consulta              ║
║ 2 - Minhas Consultas              ║
║ 3 - Cancelar Consulta             ║
║ 4 - Meu Plano de Saúde            ║
║ 5 - Histórico Médico              ║
║ 6 - Dados Pessoais                ║
║ 0 - Logout                        ║
╚════════════════════════════════════╝
```

**Métodos:**
- `agendarConsulta()` → `new GeracaoConsultaPaciente(pacienteLogado)`
- `minhasConsultas()` → Lista consultas futuras
- `cancelarConsulta()` → Seleciona e cancela
- `visualizarPlano()` → Detalhes do plano contratado

**Restrições:**
- ❌ Paciente BLOQUEADO não pode agendar
- ❌ Paciente INATIVO precisa reativar cadastro
- ✅ Pode ver histórico mesmo bloqueado

---

### 👤 **interfaceInterresado.java**
**Responsabilidade:** Tela para visitantes (sem login).

**Menu:**
```
╔════════════════════════════════════╗
║      ÁREA DO INTERESSADO          ║
╠════════════════════════════════════╣
║ 1 - Conhecer Planos Disponíveis   ║
║ 2 - Encontrar Médicos             ║
║ 3 - Localizar Unidades            ║
║ 4 - Cadastrar como Paciente       ║
║ 0 - Voltar                        ║
╚════════════════════════════════════╝
```

**Métodos:**
- `exibirPlanos()` → Lista PlanoBasico e PlanoPremium com preços
- `buscarMedicos()` → Filtro por especialidade
- `cadastrarPaciente()` → Abre formulário

---

### 🩺 **InterfaceConsulta.java**
**Responsabilidade:** Interface base para consultas (abstrata).

**Métodos abstratos:**
- `agendarConsulta()`
- `listarConsultas()`
- `cancelarConsulta()`

**Implementações:**
- `GeracaoConsultaPaciente` (paciente agendando)
- `GeracaoConsultaMedico` (médico gerenciando)
- `GeracaoConsultaAdministrador` (admin monitorando)

---

## 📦 Subpasta: `/formularios`

### 📝 **FormularioPaciente.java**
**Responsabilidade:** Cadastro e edição de pacientes.

**Fluxo de cadastro:**
```
1. Nome completo: [input]
2. CPF: [input + validação formato]
3. Data nascimento (dd/MM/yyyy): [input + parse]
4. Sexo (M/F/O): [input]
5. Telefone: [input + máscara]
6. Email: [input + validação regex]
7. Endereço: [input]

→ Confirmação:
╔════════════════════════════════════╗
║ Confirmar cadastro?               ║
║ Nome: Maria Santos                ║
║ CPF: 123.456.789-00               ║
║ ...                               ║
║ [S] Sim  [N] Não                  ║
╚════════════════════════════════════╝
```

**Métodos:**
- `cadastrar()` - Novo paciente
- `editar(Paciente paciente)` - Atualiza dados
- `validarCampos()` - Valida formatos (CPF, email, etc)

**Validações de formato:**
```java
// CPF: 11 dígitos
if (cpf.replaceAll("[^0-9]", "").length() != 11) {
    System.out.println("❌ CPF inválido");
    return false;
}

// Email: contém @ e .
if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$")) {
    System.out.println("❌ Email inválido");
    return false;
}
```

---

### 📝 **FormularioMedico.java**
**Responsabilidade:** Cadastro e edição de médicos.

**Campos adicionais:**
- CRM (formato: "123456-SP")
- Especialidade (enum)
- Senha de acesso

**Validações:**
```java
// CRM único
if (medicoService.buscarPorCrm(crm) != null) {
    System.out.println("❌ CRM já cadastrado");
    return;
}

// Senha forte
if (senha.length() < 8) {
    System.out.println("❌ Senha deve ter no mínimo 8 caracteres");
    return;
}
```

---

### 📝 **FormularioAdministrador.java**
**Responsabilidade:** Cadastro de novos administradores.

**Campos:**
- ID único
- Nome
- Senha master (mais rigorosa)
- Nível de acesso

**Segurança:**
- Apenas SUPER_ADMIN pode criar novos admins
- Senha deve ter letras, números e símbolos

---

### 📝 **FormularioPlanoDeSaude.java**
**Responsabilidade:** Atribuir plano a paciente.

**Fluxo:**
```
1. Buscar paciente por CPF: [input]
   → Exibe: Nome, Status, Plano Atual

2. Escolher tipo de plano:
   ╔════════════════════════════════╗
   ║ 1 - Plano Básico (R$ 150/mês) ║
   ║ 2 - Plano Premium (R$ 350/mês)║
   ╚════════════════════════════════╝

3. Escolher operadora:
   ╔════════════════════════════════╗
   ║ 1 - Unimed                    ║
   ║ 2 - Amil                      ║
   ║ 3 - Bradesco Saúde            ║
   ║ 4 - SulAmérica                ║
   ╚════════════════════════════════╝

4. Escolher abrangência:
   ╔════════════════════════════════╗
   ║ 1 - Municipal                 ║
   ║ 2 - Estadual                  ║
   ║ 3 - Nacional                  ║
   ║ 4 - Internacional             ║
   ╚════════════════════════════════╝

5. Confirmar → planoService.atribuirPlano(paciente, plano)
```

**Validação:**
- Paciente não pode ter 2 planos ativos
- Se já tiver plano, perguntar se deseja substituir

---

### 📝 **FormularioAgendaMedico.java**
**Responsabilidade:** Médico gerenciar própria agenda (criar/remover horários).

**Construtor:**
```java
public FormularioAgendaMedico(Medico medicoLogado) {
    this.medicoLogado = medicoLogado; // Contexto de quem está logado
}
```

**Menu:**
```
╔════════════════════════════════════╗
║      GERENCIAR AGENDA             ║
╠════════════════════════════════════╣
║ 1 - Adicionar Horário             ║
║ 2 - Remover Horário               ║
║ 3 - Listar Horários Disponíveis   ║
║ 4 - Listar Todos Horários         ║
║ 0 - Voltar                        ║
╚════════════════════════════════════╝
```

**Fluxo de adicionar horário:**
```java
1. Data (dd/MM/yyyy): 25/11/2025
2. Horário (HH:mm): 14:00

→ Combina: "25/11/2025 14:00"
→ Parse: SimpleDateFormat("dd/MM/yyyy HH:mm")
→ Cria: new Horario(medicoLogado, dataHora, true)
→ Salva: horarioService.adicionar(horario)
```

**Mudança recente:** Antes pedia para selecionar médico toda vez. Agora usa `medicoLogado` direto.

---

## 📦 Subpasta: `/consulta`

### 🩺 **GeracaoConsultaPaciente.java**
**Responsabilidade:** Paciente agendar consulta.

**Fluxo:**
```
1. Escolher especialidade:
   ╔════════════════════════════════╗
   ║ 1 - Cardiologia               ║
   ║ 2 - Dermatologia              ║
   ║ 3 - Ortopedia                 ║
   ║ ...                           ║
   ╚════════════════════════════════╝

2. Listar médicos da especialidade:
   ╔════════════════════════════════╗
   ║ CRM: 123456-SP                ║
   ║ Nome: Dr. João Silva          ║
   ║ Horários disponíveis: 5       ║
   ║ ─────────────────────────────  ║
   ║ [1] Selecionar                ║
   ╚════════════════════════════════╝

3. Escolher horário disponível:
   ╔════════════════════════════════╗
   ║ ID: 1 - 25/11/2025 14:00      ║
   ║ ID: 2 - 26/11/2025 10:00      ║
   ║ ID: 3 - 27/11/2025 16:00      ║
   ╚════════════════════════════════╝

4. Descrição do problema: [input texto livre]

5. Confirmar → consultaService.agendarConsulta(...)
```

**Validações no Service:**
- Verifica se paciente está ATIVO
- Verifica se horário está disponível
- Verifica cobertura do plano

---

### 🩺 **GeracaoConsultaMedico.java**
**Responsabilidade:** Médico gerenciar consultas agendadas com ele.

**Menu:**
```
╔════════════════════════════════════╗
║      MINHAS CONSULTAS             ║
╠════════════════════════════════════╣
║ 1 - Listar Consultas Pendentes    ║
║ 2 - Confirmar Consulta            ║
║ 3 - Finalizar Consulta            ║
║ 4 - Cancelar Consulta             ║
║ 5 - Agendar Consulta (Manual)     ║
║ 0 - Voltar                        ║
╚════════════════════════════════════╝
```

**Agendar manual:**
```
1. CPF do paciente: [input]
   → Busca: pacienteService.buscarPorCpf(cpf)
   
2. Listar horários do médico logado
   
3. Selecionar horário
   
4. Descrição: [input]
   
5. Confirmar → consultaService.agendarConsulta(...)
```

**Correção recente:** Agora usa `buscarPorCpf()` corretamente (antes usava `buscarPorCarteirinha()`).

---

### 🩺 **GeracaoConsultaAdministrador.java**
**Responsabilidade:** Admin visualizar/gerenciar TODAS consultas do sistema.

**Filtros:**
- Por paciente (CPF)
- Por médico (CRM)
- Por data
- Por status (AGENDADA, REALIZADA, etc)

**Ações:**
- ✅ Visualizar detalhes
- ❌ Cancelar (com motivo administrativo)
- 📊 Gerar relatórios

---

## 📦 Subpasta: `/admin`

### 👥 **AdminPacienteView.java**
**Responsabilidade:** Admin gerenciar pacientes.

**Menu:**
```
╔════════════════════════════════════╗
║   GERENCIAMENTO DE PACIENTES      ║
╠════════════════════════════════════╣
║ 1 - Cadastrar Novo Paciente       ║
║ 2 - Listar Todos Pacientes        ║
║ 3 - Buscar Paciente (CPF)         ║
║ 4 - Alterar Status                ║
║ 5 - Editar Dados                  ║
║ 6 - Remover Paciente              ║
║ 0 - Voltar                        ║
╚════════════════════════════════════╝
```

**Alterar Status (opção 4):**
```
╔════════════════════════════════════╗
║ 1 - Ativar/Desbloquear            ║
║ 2 - Desativar (Inativo)           ║
║ 3 - Bloquear                      ║
║ 4 - Marcar como Falecido          ║
╚════════════════════════════════════╝
```

**Integração com Service:**
```java
switch (opcao) {
    case 1 -> pacienteService.desbloquearPaciente(cpf);
    case 2 -> pacienteService.desativarPaciente(cpf);
    case 3 -> pacienteService.bloquearPaciente(cpf);
    case 4 -> pacienteService.marcarComoFalecido(cpf);
}
```

**Mudança recente:** Removida opção "Resetar Senha" (era fake).

---

### 👨‍⚕️ **AdminMedicoView.java**
**Responsabilidade:** Admin gerenciar médicos.

**Menu:**
```
╔════════════════════════════════════╗
║   GERENCIAMENTO DE MÉDICOS        ║
╠════════════════════════════════════╣
║ 1 - Cadastrar Novo Médico         ║
║ 2 - Listar Todos Médicos          ║
║ 3 - Buscar Médico (CRM)           ║
║ 4 - Editar Dados                  ║
║ 5 - Remover Médico                ║
║ 6 - Filtrar por Especialidade     ║
║ 0 - Voltar                        ║
╚════════════════════════════════════╝
```

**Integração com Service:**
```java
medicoService.cadastrar(medico);
medicoService.listarTodos();
medicoService.buscarPorCrm(crm);
```

---

### 💳 **AdminPlanoView.java**
**Responsabilidade:** Admin gerenciar planos de saúde.

**Menu:**
```
╔════════════════════════════════════╗
║   GERENCIAMENTO DE PLANOS         ║
╠════════════════════════════════════╣
║ 1 - Atribuir Plano a Paciente     ║
║ 2 - Cancelar Plano                ║
║ 3 - Alterar Plano                 ║
║ 4 - Listar Pacientes Sem Plano    ║
║ 5 - Relatório de Planos           ║
║ 0 - Voltar                        ║
╚════════════════════════════════════╝
```

---

## 🎨 Padrões de Interface

### 🎨 Cores (usando ConsoleColors)
```java
System.out.println(ConsoleColors.GREEN + "✅ Sucesso!" + ConsoleColors.RESET);
System.out.println(ConsoleColors.RED + "❌ Erro!" + ConsoleColors.RESET);
System.out.println(ConsoleColors.YELLOW + "⚠️  Atenção!" + ConsoleColors.RESET);
System.out.println(ConsoleColors.BLUE + "ℹ️  Informação" + ConsoleColors.RESET);
```

### 📋 Formatação de Tabelas
```java
System.out.println("╔════════════════════════════════════╗");
System.out.println("║  ID  │  Nome       │  Status     ║");
System.out.println("╠════════════════════════════════════╣");
System.out.println("║  001 │  João Silva │  ATIVO ✅   ║");
System.out.println("╚════════════════════════════════════╝");
```

### ⌨️ Entrada do Usuário
```java
// Sempre usar nextLine() para evitar buffer
System.out.print("Digite o CPF: ");
String cpf = scanner.nextLine().trim();

// Converter manualmente quando precisar de número
System.out.print("Digite a opção: ");
String input = scanner.nextLine().trim();
int opcao = Integer.parseInt(input);
```

---

## 🔄 Fluxo de Navegação

```
MenuPrincipal
├─ Login Admin → InterfaceAdministrador
│  ├─ Gerenciar Pacientes → AdminPacienteView
│  ├─ Gerenciar Médicos → AdminMedicoView
│  └─ Gerenciar Planos → AdminPlanoView
│
├─ Login Médico → InterfaceMedico
│  ├─ Gerenciar Agenda → FormularioAgendaMedico
│  └─ Consultas → GeracaoConsultaMedico
│
└─ Login Paciente → InterfacePaciente
   ├─ Agendar → GeracaoConsultaPaciente
   └─ Minhas Consultas → ListagemConsultas
```

---

## 🧪 Exemplo de View Completa

```java
public class ExemploView {
    private final Scanner scanner = new Scanner(System.in);
    private final PacienteService service = new PacienteService();
    
    public void executar() {
        while (true) {
            exibirMenu();
            int opcao = lerOpcao();
            
            if (opcao == 0) break;
            
            processarOpcao(opcao);
        }
    }
    
    private void exibirMenu() {
        System.out.println("╔════════════════╗");
        System.out.println("║ 1 - Cadastrar  ║");
        System.out.println("║ 2 - Listar     ║");
        System.out.println("║ 0 - Sair       ║");
        System.out.println("╚════════════════╝");
    }
    
    private int lerOpcao() {
        try {
            System.out.print("Opção: ");
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("❌ Número inválido!");
            return -1;
        }
    }
    
    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> cadastrar();
            case 2 -> listar();
            default -> System.out.println("❌ Opção inválida!");
        }
    }
}
```

---

**Última atualização:** 25/11/2025
