# ⚙️ Service Package

## 🎯 Objetivo

Esta pasta contém a **camada de serviço** do sistema - responsável pela **lógica de negócio** e orquestração entre as camadas View e Database. É o "cérebro" da aplicação, onde ficam validações, regras de negócio e processamento de dados.

## 📐 Arquitetura

Os services seguem o padrão **Service Layer**, atuando como intermediários entre a interface do usuário e o banco de dados. Implementam o princípio de **separação de responsabilidades**.

```
View Layer (Interface do usuário)
     ↓
Service Layer (VOCÊ ESTÁ AQUI) → Regras de negócio + Validações
     ↓
Database Layer (Persistência)
```

## 🎭 Responsabilidades

### O que a Service Layer FAZ:
✅ **Validações de negócio** (ex: paciente bloqueado não pode agendar)  
✅ **Transformação de dados** (ex: formatar CPF antes de salvar)  
✅ **Orquestração** (ex: agendar consulta → ocupar horário → notificar paciente)  
✅ **Tratamento de exceções** (ex: converter erros técnicos em mensagens amigáveis)  
✅ **Logging e auditoria** (ex: registrar ações importantes)  

### O que a Service Layer NÃO FAZ:
❌ **Interface com usuário** (isso é responsabilidade da View)  
❌ **Acesso direto ao banco de dados** (delega para Database Layer)  
❌ **Lógica de apresentação** (ex: formatação visual)  

---

## 📦 **AgendaService.java**

**Responsabilidade:** Gerenciar horários disponíveis na agenda dos médicos.

### Métodos Principais:

#### 📝 `adicionarHorario(Horario horario)`
**O que faz:** Adiciona novo horário na agenda do médico.

**Validações:**
- ✅ Verifica se horário não está no passado
- ✅ Verifica se médico já não tem horário no mesmo dia/hora
- ✅ Valida se horário está em horário comercial (8h-18h)

**Fluxo:**
```java
1. Recebe Horario da View
2. valida(horario)
   ├─ Se inválido → throw new IllegalArgumentException()
   └─ Se válido → continua
3. agendaDB.adicionar(horario)
4. logDB.registrar("HORARIO_CRIADO", medico.getCrm(), detalhes)
5. Retorna horario
```

**Exemplo de uso:**
```java
AgendaService service = new AgendaService();
Horario horario = new Horario(medico, dataFutura, true);
service.adicionarHorario(horario); // Valida e salva
```

---

#### 🔍 `buscarHorariosDisponiveis(String crmMedico)`
**O que faz:** Retorna apenas horários livres de um médico específico.

**Lógica:**
```java
1. Busca todos horários do médico → agendaDB.buscarPorMedico(crm)
2. Filtra apenas disponíveis → horarios.stream().filter(h -> h.isDisponivel())
3. Filtra apenas futuros → .filter(h -> h.getData().after(new Date()))
4. Ordena por data → .sorted(Comparator.comparing(Horario::getData))
5. Retorna List<Horario>
```

**Por que é importante:** Paciente não deve ver horários já ocupados ou passados.

---

#### ❌ `cancelarHorario(int idHorario)`
**O que faz:** Remove horário da agenda (se não tiver consulta agendada).

**Validações:**
- ✅ Verifica se horário existe
- ✅ Verifica se NÃO tem consulta vinculada
- ❌ Se tiver consulta → throw new IllegalStateException("Cancele a consulta primeiro")

**Fluxo de segurança:**
```
Horário com consulta? 
├─ SIM → ERRO: "Não pode excluir horário com consulta"
└─ NÃO → agendaDB.remover(id) → OK
```

---

## 📦 **ConsultaService.java**

**Responsabilidade:** Gerenciar todo o ciclo de vida das consultas médicas.

### Métodos Principais:

#### 📝 `agendarConsulta(Horario horario, Paciente paciente, String descricao)`
**O que faz:** Cria nova consulta e vincula ao horário.

**Validações complexas:**
```java
1. Paciente está ATIVO?
   ├─ BLOQUEADO → throw new BusinessException("Paciente bloqueado por inadimplência")
   ├─ FALECIDO → throw new BusinessException("Não é possível agendar consulta")
   └─ INATIVO → throw new BusinessException("Paciente com cadastro inativo")

2. Horário disponível?
   ├─ Já ocupado → throw new BusinessException("Horário indisponível")
   └─ Disponível → continua

3. Paciente tem plano ativo?
   ├─ Sem plano → throw new BusinessException("Paciente sem plano de saúde")
   └─ Com plano → continua

4. Especialidade do médico está coberta pelo plano?
   ├─ Não coberta → throw new BusinessException("Plano não cobre esta especialidade")
   └─ Coberta → continua
```

**Fluxo de sucesso:**
```java
1. Valida todas regras acima
2. Cria objeto Consulta
3. horario.ocupar() // Marca como indisponível
4. agendaDB.atualizar(horario)
5. consultaDB.cadastrar(consulta)
6. paciente.adicionarConsulta(consulta)
7. logDB.registrar("CONSULTA_AGENDADA", paciente.getCpf(), detalhes)
8. // Enviar email/SMS de confirmação (futuro)
9. Retorna consulta
```

**Por que é importante:** Garante integridade - não permite agendar em horário ocupado, nem paciente bloqueado usar o sistema.

---

#### ✅ `confirmarConsulta(int idConsulta)`
**O que faz:** Paciente confirma que irá comparecer.

**Estado:**
```
AGENDADA → CONFIRMADA
```

**Validação:**
- Só pode confirmar se status == AGENDADA
- Se já REALIZADA ou CANCELADA → erro

---

#### ❌ `cancelarConsulta(int idConsulta)`
**O que faz:** Cancela consulta e libera horário.

**Fluxo:**
```java
1. Busca consulta → consultaDB.buscarPorId(id)
2. Verifica se pode cancelar
   ├─ REALIZADA → ERRO: "Consulta já realizada"
   └─ AGENDADA/CONFIRMADA → OK para cancelar
3. consulta.setStatus(CANCELADA)
4. horario = consulta.getHorario()
5. horario.liberar() // Marca como disponível novamente
6. agendaDB.atualizar(horario)
7. consultaDB.atualizar(consulta)
8. logDB.registrar("CONSULTA_CANCELADA", ...)
```

**Regra de negócio crítica:** Sempre liberar horário quando cancelar consulta.

---

#### 🏁 `finalizarConsulta(int idConsulta)`
**O que faz:** Médico registra que atendeu o paciente.

**Estado:**
```
CONFIRMADA → REALIZADA
```

**Ações adicionais:**
```java
1. Atualiza status para REALIZADA
2. Define dataRealizacao = new Date()
3. Adiciona ao histórico do paciente
4. Calcula copagamento do plano
5. Gera comprovante (futuro)
```

---

## 📦 **HorarioService.java**

**Responsabilidade:** Gerenciar horários isoladamente (diferente de AgendaService que é mais abrangente).

### Métodos Principais:

#### 🔍 `listarHorariosLivres()`
**O que faz:** Retorna TODOS horários disponíveis de TODOS médicos.

**Uso:** Tela inicial do paciente mostrando opções de agendamento.

---

#### 🔍 `buscarPorMedico(String crm)`
**O que faz:** Lista horários de um médico específico (livres e ocupados).

**Uso:** Médico visualizando própria agenda completa.

---

#### 📊 `gerarRelatorioOcupacao(String crm)`
**O que faz:** Calcula taxa de ocupação da agenda.

**Fórmula:**
```java
int total = horariosTotais.size();
int ocupados = horariosTotais.stream().filter(h -> !h.isDisponivel()).count();
double taxa = (ocupados * 100.0) / total;
return taxa + "%"; // Ex: "75.5%"
```

**Uso:** Dashboard administrativo.

---

## 📦 **MedicoService.java**

**Responsabilidade:** Gerenciar cadastro e operações relacionadas a médicos.

### Métodos Principais:

#### 📝 `cadastrar(Medico medico)`
**Validações:**
- ✅ CRM único (não pode duplicar)
- ✅ CRM válido (formato: "123456-SP")
- ✅ Especialidade informada
- ✅ Senha forte (mínimo 8 caracteres)

**Processamento:**
```java
1. valida(medico)
2. senhaCriptografada = BCrypt.hash(medico.getSenha()) // Futuro
3. medico.setSenha(senhaCriptografada)
4. funcionariosDB.cadastrarMedico(medico)
5. logDB.registrar("MEDICO_CADASTRADO", admin.getId(), medico.getCrm())
```

---

#### 🔐 `autenticar(String crm, String senha)`
**O que faz:** Valida login do médico.

**Fluxo:**
```java
1. medico = funcionariosDB.buscarMedicoPorCrm(crm)
2. Se medico == null → return null (usuário não existe)
3. senhaCorreta = verificarSenha(senha, medico.getSenha())
4. Se senhaCorreta → return medico
5. Senão → 
   ├─ incrementarTentativasErradas(crm)
   ├─ Se tentativas >= 5 → bloquearConta(crm)
   └─ return null
```

**Segurança:** Limite de tentativas previne brute force.

---

#### 🔍 `buscarPorEspecialidade(Especialidades especialidade)`
**O que faz:** Lista médicos de uma especialidade.

**Uso:** Paciente procurando cardiologista.

---

## 📦 **PacienteService.java**

**Responsabilidade:** Gerenciar cadastro e status de pacientes.

### Métodos Principais:

#### 📝 `cadastrar(Paciente paciente)`
**Validações:**
- ✅ CPF único e válido (algoritmo validador)
- ✅ Idade >= 0 e <= 120 anos
- ✅ Email válido (regex)
- ✅ Telefone válido

**Processamento:**
```java
1. valida(paciente)
2. numeroCarteirinha = gerarCarteirinha() // Auto-incremento
3. paciente.setNumeroCarteirinha(numeroCarteirinha)
4. paciente.setStatus(StatusPaciente.ATIVO) // Padrão
5. pacienteDB.cadastrar(paciente)
6. logDB.registrar("PACIENTE_CADASTRADO", admin.getId(), paciente.getCpf())
```

---

#### 🔄 `bloquearPaciente(String cpf)`
**O que faz:** Bloqueia acesso (geralmente por inadimplência).

**Efeitos:**
```
Status → BLOQUEADO
├─ Não pode agendar novas consultas
├─ Consultas futuras são CANCELADAS automaticamente
└─ Pode ver histórico passado
```

**Fluxo:**
```java
1. paciente = buscarPorCpf(cpf)
2. paciente.setStatus(BLOQUEADO)
3. consultasFuturas = consultaDB.buscarFuturasPorPaciente(cpf)
4. Para cada consulta:
   └─ consultaService.cancelarConsulta(consulta.getId())
5. pacienteDB.atualizar(paciente)
```

---

#### 🔄 `desbloquearPaciente(String cpf)`
**O que faz:** Restaura acesso.

**Validação:** Verificar se pagamentos estão em dia (integração futura com financeiro).

---

#### 🔄 `desativarPaciente(String cpf)`
**O que faz:** Desativa temporariamente (ex: mudou de cidade).

**Diferença de BLOQUEADO:**
- BLOQUEADO = Punição (inadimplência)
- INATIVO = Voluntário (não quer mais usar)

---

#### 🔄 `marcarComoFalecido(String cpf)`
**O que faz:** Registra óbito.

**Efeitos:**
```
Status → FALECIDO
├─ Cancela todas consultas futuras
├─ Mantém histórico (fins legais)
├─ Não pode ser reativado
└─ Plano é cancelado automaticamente
```

---

#### 🔍 `buscarPorCpf(String cpf)`
**O que faz:** Localiza paciente por CPF.

**Uso:** Formulário de agendamento de consulta.

---

## 📦 **PlanoDeSaudeService.java**

**Responsabilidade:** Gerenciar planos de saúde dos pacientes.

### Métodos Principais:

#### 📝 `atribuirPlano(Paciente paciente, PlanoSaude plano)`
**Validações:**
- ✅ Paciente não pode ter 2 planos ativos
- ✅ Plano deve estar ativo na operadora

**Processamento:**
```java
1. Se paciente.getPlano() != null:
   └─ throw new BusinessException("Paciente já tem plano. Cancele o anterior.")
2. plano.setTitular(paciente)
3. paciente.setPlanoSaude(plano)
4. planoSaudeDB.cadastrar(plano)
5. pacienteDB.atualizar(paciente)
```

---

#### ❌ `cancelarPlano(String cpf)`
**O que faz:** Remove plano do paciente.

**Efeitos:**
```
1. Cancela consultas futuras
2. paciente.setPlanoSaude(null)
3. plano.setStatus(CANCELADO)
4. Calcula multa rescisória (se contrato)
```

---

#### 💰 `calcularValorConsulta(Paciente paciente, Consulta consulta)`
**O que faz:** Calcula quanto paciente vai pagar.

**Fórmula:**
```java
PlanoSaude plano = paciente.getPlano();
double valorBase = 150.00; // Valor tabelado da consulta

if (plano instanceof PlanoBasico) {
    return valorBase * 0.30; // 30% de copagamento
} else if (plano instanceof PlanoPremium) {
    return 0.0; // Sem copagamento
}
```

---

## 🔗 Interação Entre Services

```
PacienteService ←→ PlanoDeSaudeService
       ↓
ConsultaService ←→ AgendaService/HorarioService
       ↓
    MedicoService
```

**Exemplo de fluxo completo:**
```java
1. View: Usuário clica "Agendar Consulta"
2. View → PacienteService.buscarPorCpf(cpf)
3. View → MedicoService.buscarPorEspecialidade(CARDIOLOGIA)
4. View → AgendaService.buscarHorariosDisponiveis(crm)
5. View → ConsultaService.agendarConsulta(horario, paciente, descricao)
   ├─ ConsultaService valida regras
   ├─ ConsultaService.atualizaHorario(horario)
   └─ ConsultaService.salvaConsulta(consulta)
6. View exibe "✅ Consulta agendada com sucesso!"
```

---

## 🎨 Princípios de Design

✅ **Single Responsibility:** Cada service cuida de uma entidade  
✅ **DRY (Don't Repeat Yourself):** Lógica centralizada, não duplicada nas Views  
✅ **Fail-Fast:** Valida cedo, falha rápido com exceções claras  
✅ **Transaction Script:** Cada método é uma transação de negócio  
✅ **Separation of Concerns:** Service não sabe sobre Views ou Database internals  

---

## 🧪 Exemplo de Teste Unitário

```java
@Test
public void naoDevePermitirAgendarConsultaParaPacienteBloqueado() {
    // Arrange
    Paciente paciente = new Paciente("João", "123.456.789-00");
    paciente.setStatus(StatusPaciente.BLOQUEADO);
    
    Medico medico = new Medico("Dr. Silva", "123456-SP", CARDIOLOGIA);
    Horario horario = new Horario(medico, dataFutura, true);
    
    ConsultaService service = new ConsultaService();
    
    // Act & Assert
    assertThrows(BusinessException.class, () -> {
        service.agendarConsulta(horario, paciente, "Checkup");
    });
}
```

---

## 🔒 Tratamento de Exceções

Services devem lançar exceções **de negócio**, não técnicas:

❌ **Ruim:**
```java
throw new SQLException("Duplicate entry for key 'PRIMARY'");
```

✅ **Bom:**
```java
throw new BusinessException("CPF já cadastrado no sistema");
```

**Hierarquia de exceções:**
```
Exception
└── BusinessException (criada por você)
    ├── ValidationException
    ├── NotFoundException
    ├── DuplicateException
    └── UnauthorizedException
```

---

## 📊 Métricas de Qualidade

Para manter Services saudáveis:

- ✅ Cada método < 30 linhas
- ✅ Cada classe < 300 linhas
- ✅ Cobertura de testes >= 80%
- ✅ Complexidade ciclomática < 10

---

**Última atualização:** 25/11/2025
