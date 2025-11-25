# 🛠️ Util Package

## 🎯 Objetivo

Esta pasta contém **classes utilitárias** - funções auxiliares reutilizáveis em todo o sistema. São ferramentas genéricas que não pertencem a nenhuma camada específica (Model/View/Service), mas são usadas por todas elas.

## 📐 Arquitetura

Utilitários seguem o padrão **Helper/Utility Classes** - classes com métodos estáticos que fornecem funcionalidades comuns. Não mantêm estado (stateless).

```
┌─────────────────────────────────────┐
│  View / Service / Model / Database  │
│         ↓  ↓  ↓  ↓                  │
│    Util Package (Helpers)           │
│  - Validações                       │
│  - Formatações                      │
│  - Cores de Console                 │
│  - Dados Mock para Testes           │
└─────────────────────────────────────┘
```

## 🎭 Responsabilidades

### O que o Util Package FAZ:
✅ **Validações genéricas** (CPF, email, data, etc)  
✅ **Formatações** (máscaras, conversões, padronização)  
✅ **Constantes** (cores, mensagens, configurações)  
✅ **Dados de teste** (mocks, fixtures)  
✅ **Helpers** (cálculos, conversões, utilitários diversos)  

### O que o Util Package NÃO FAZ:
❌ **Lógica de negócio** (isso é responsabilidade do Service)  
❌ **Acesso a dados** (isso é responsabilidade do Database)  
❌ **Interface com usuário** (isso é responsabilidade da View)  

---

## 📦 **ConsoleColors.java**

**Responsabilidade:** Fornecer códigos ANSI para colorir texto no console.

### Constantes de Cores:

```java
// Cores de texto
public static final String RESET = "\u001B[0m";
public static final String BLACK = "\u001B[30m";
public static final String RED = "\u001B[31m";
public static final String GREEN = "\u001B[32m";
public static final String YELLOW = "\u001B[33m";
public static final String BLUE = "\u001B[34m";
public static final String PURPLE = "\u001B[35m";
public static final String CYAN = "\u001B[36m";
public static final String WHITE = "\u001B[37m";

// Cores de fundo
public static final String BLACK_BG = "\u001B[40m";
public static final String RED_BG = "\u001B[41m";
public static final String GREEN_BG = "\u001B[42m";
// ... etc

// Estilos
public static final String BOLD = "\u001B[1m";
public static final String UNDERLINE = "\u001B[4m";
```

### Exemplos de Uso:

```java
// Mensagem de sucesso (verde)
System.out.println(ConsoleColors.GREEN + "✅ Operação realizada com sucesso!" + ConsoleColors.RESET);

// Mensagem de erro (vermelho)
System.out.println(ConsoleColors.RED + "❌ Erro ao processar requisição!" + ConsoleColors.RESET);

// Aviso (amarelo)
System.out.println(ConsoleColors.YELLOW + "⚠️  Atenção: Dados incompletos!" + ConsoleColors.RESET);

// Informação (azul)
System.out.println(ConsoleColors.BLUE + "ℹ️  Processando..." + ConsoleColors.RESET);

// Texto com fundo e negrito
System.out.println(ConsoleColors.BOLD + ConsoleColors.WHITE + ConsoleColors.BLUE_BG + 
    "SISTEMA DE SAÚDE" + ConsoleColors.RESET);
```

### Por que usar:

**✅ Melhora UX:**
- Feedback visual imediato (sucesso = verde, erro = vermelho)
- Destaca informações importantes
- Torna interface console mais profissional

**⚠️ Importante:**
Sempre usar `RESET` no final para não "vazar" cor para próximas mensagens:

```java
// ❌ ERRADO - cor vaza
System.out.println(ConsoleColors.RED + "Erro!");
System.out.println("Esta mensagem também fica vermelha!");

// ✅ CORRETO
System.out.println(ConsoleColors.RED + "Erro!" + ConsoleColors.RESET);
System.out.println("Esta mensagem fica normal");
```

---

## 📦 **ValidacaoUtil.java**

**Responsabilidade:** Validar formatos e regras de dados (CPF, email, telefone, datas, etc).

### Métodos Principais:

#### ✅ `validarCPF(String cpf)`
**O que faz:** Verifica se CPF é válido usando algoritmo dos dígitos verificadores.

**Validações:**
1. Remove caracteres não-numéricos: `123.456.789-00` → `12345678900`
2. Verifica se tem 11 dígitos
3. Verifica se não é sequência repetida (`11111111111`, `00000000000`, etc)
4. Calcula primeiro dígito verificador
5. Calcula segundo dígito verificador
6. Compara com os dígitos informados

**Algoritmo:**
```java
public static boolean validarCPF(String cpf) {
    // 1. Limpar
    cpf = cpf.replaceAll("[^0-9]", "");
    
    // 2. Tamanho
    if (cpf.length() != 11) return false;
    
    // 3. Sequências inválidas
    if (cpf.matches("(\\d)\\1{10}")) return false;
    
    // 4. Cálculo do primeiro dígito
    int soma = 0;
    for (int i = 0; i < 9; i++) {
        soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
    }
    int primeiroDigito = 11 - (soma % 11);
    if (primeiroDigito >= 10) primeiroDigito = 0;
    
    // 5. Cálculo do segundo dígito
    soma = 0;
    for (int i = 0; i < 10; i++) {
        soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
    }
    int segundoDigito = 11 - (soma % 11);
    if (segundoDigito >= 10) segundoDigito = 0;
    
    // 6. Validação final
    return Character.getNumericValue(cpf.charAt(9)) == primeiroDigito &&
           Character.getNumericValue(cpf.charAt(10)) == segundoDigito;
}
```

**Exemplo de uso:**
```java
if (ValidacaoUtil.validarCPF("123.456.789-00")) {
    System.out.println("✅ CPF válido");
} else {
    System.out.println("❌ CPF inválido");
}
```

---

#### ✅ `validarEmail(String email)`
**O que faz:** Verifica se email está em formato válido.

**Regex:**
```java
public static boolean validarEmail(String email) {
    String regex = "^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$";
    return email.matches(regex);
}
```

**Validações:**
- `[\w.-]+` - Nome antes do @ (letras, números, ponto, hífen)
- `@` - Obrigatório
- `[\w.-]+` - Domínio (ex: gmail, hotmail)
- `\.` - Ponto obrigatório
- `[a-z]{2,}` - Extensão com no mínimo 2 letras (.com, .br, etc)

**Exemplos:**
```java
ValidacaoUtil.validarEmail("joao@gmail.com");      // ✅ true
ValidacaoUtil.validarEmail("maria.silva@uol.com"); // ✅ true
ValidacaoUtil.validarEmail("emailsemaroba.com");   // ❌ false
ValidacaoUtil.validarEmail("@semdominio.com");     // ❌ false
```

---

#### ✅ `validarTelefone(String telefone)`
**O que faz:** Verifica se telefone está no formato brasileiro.

**Formatos aceitos:**
- `(11) 98765-4321` - Celular com DDD
- `(11) 3456-7890` - Fixo com DDD
- `11987654321` - Sem formatação

**Regex:**
```java
public static boolean validarTelefone(String telefone) {
    telefone = telefone.replaceAll("[^0-9]", ""); // Remove caracteres
    return telefone.length() >= 10 && telefone.length() <= 11;
}
```

---

#### ✅ `validarData(String data, String formato)`
**O que faz:** Verifica se string é uma data válida no formato especificado.

**Exemplo:**
```java
public static boolean validarData(String data, String formato) {
    try {
        SimpleDateFormat sdf = new SimpleDateFormat(formato);
        sdf.setLenient(false); // Não aceita datas inválidas como 32/13/2025
        sdf.parse(data);
        return true;
    } catch (ParseException e) {
        return false;
    }
}

// Uso:
ValidacaoUtil.validarData("25/11/2025", "dd/MM/yyyy"); // ✅ true
ValidacaoUtil.validarData("32/13/2025", "dd/MM/yyyy"); // ❌ false
ValidacaoUtil.validarData("2025-11-25", "yyyy-MM-dd"); // ✅ true
```

---

#### ✅ `validarIdade(Date dataNascimento, int idadeMinima, int idadeMaxima)`
**O que faz:** Verifica se idade está dentro do intervalo.

```java
public static boolean validarIdade(Date dataNascimento, int min, int max) {
    Calendar hoje = Calendar.getInstance();
    Calendar nascimento = Calendar.getInstance();
    nascimento.setTime(dataNascimento);
    
    int idade = hoje.get(Calendar.YEAR) - nascimento.get(Calendar.YEAR);
    
    // Ajusta se ainda não fez aniversário este ano
    if (hoje.get(Calendar.MONTH) < nascimento.get(Calendar.MONTH) ||
        (hoje.get(Calendar.MONTH) == nascimento.get(Calendar.MONTH) &&
         hoje.get(Calendar.DAY_OF_MONTH) < nascimento.get(Calendar.DAY_OF_MONTH))) {
        idade--;
    }
    
    return idade >= min && idade <= max;
}

// Uso:
if (!ValidacaoUtil.validarIdade(dataNascimento, 18, 120)) {
    System.out.println("❌ Idade inválida: paciente deve ter entre 18 e 120 anos");
}
```

---

#### 🔧 `formatarCPF(String cpf)`
**O que faz:** Adiciona máscara ao CPF.

```java
public static String formatarCPF(String cpf) {
    cpf = cpf.replaceAll("[^0-9]", ""); // Remove não-numéricos
    if (cpf.length() != 11) return cpf; // Retorna sem formatar se inválido
    
    return cpf.substring(0, 3) + "." + 
           cpf.substring(3, 6) + "." + 
           cpf.substring(6, 9) + "-" + 
           cpf.substring(9);
}

// Entrada: "12345678900"
// Saída:   "123.456.789-00"
```

---

#### 🔧 `formatarTelefone(String telefone)`
**O que faz:** Adiciona máscara ao telefone.

```java
public static String formatarTelefone(String telefone) {
    telefone = telefone.replaceAll("[^0-9]", "");
    
    if (telefone.length() == 11) {
        // Celular: (11) 98765-4321
        return "(" + telefone.substring(0, 2) + ") " + 
               telefone.substring(2, 7) + "-" + 
               telefone.substring(7);
    } else if (telefone.length() == 10) {
        // Fixo: (11) 3456-7890
        return "(" + telefone.substring(0, 2) + ") " + 
               telefone.substring(2, 6) + "-" + 
               telefone.substring(6);
    }
    
    return telefone; // Retorna sem formatar se inválido
}
```

---

#### 🔧 `formatarData(Date data, String formato)`
**O que faz:** Converte Date para String formatada.

```java
public static String formatarData(Date data, String formato) {
    SimpleDateFormat sdf = new SimpleDateFormat(formato);
    return sdf.format(data);
}

// Uso:
Date hoje = new Date();
String dataFormatada = ValidacaoUtil.formatarData(hoje, "dd/MM/yyyy"); // "25/11/2025"
String horaFormatada = ValidacaoUtil.formatarData(hoje, "HH:mm");      // "14:30"
```

---

#### 🔧 `calcularIdade(Date dataNascimento)`
**O que faz:** Retorna idade em anos.

```java
public static int calcularIdade(Date dataNascimento) {
    Calendar hoje = Calendar.getInstance();
    Calendar nascimento = Calendar.getInstance();
    nascimento.setTime(dataNascimento);
    
    int idade = hoje.get(Calendar.YEAR) - nascimento.get(Calendar.YEAR);
    
    // Ajusta se ainda não fez aniversário
    if (hoje.get(Calendar.DAY_OF_YEAR) < nascimento.get(Calendar.DAY_OF_YEAR)) {
        idade--;
    }
    
    return idade;
}

// Uso:
int idade = ValidacaoUtil.calcularIdade(paciente.getDataNascimento());
System.out.println("Idade: " + idade + " anos");
```

---

## 📦 **PacienteMockUtil.java**

**Responsabilidade:** Gerar dados de teste (mock) para pacientes durante desenvolvimento.

### Por que usar Mocks?

Durante desenvolvimento, é útil ter dados prontos para testar sem precisar cadastrar manualmente toda vez.

### Métodos:

#### 🧪 `gerarPacienteMock()`
**O que faz:** Retorna um paciente com dados fictícios completos.

```java
public static Paciente gerarPacienteMock() {
    Paciente paciente = new Paciente();
    paciente.setNome("João da Silva Mock");
    paciente.setCpf("123.456.789-00");
    paciente.setDataNascimento(new Date(90, 0, 15)); // 15/01/1990
    paciente.setSexo(Sexo.MASCULINO);
    paciente.setTelefone("(11) 98765-4321");
    paciente.setEmail("joao.mock@email.com");
    paciente.setEndereco("Rua Mock, 123");
    paciente.setStatus(StatusPaciente.ATIVO);
    paciente.setNumeroCarteirinha("MOCK001");
    paciente.setTipoSanguineo(TipoSanguineo.O_POSITIVO);
    
    return paciente;
}
```

---

#### 🧪 `gerarListaPacientesMock(int quantidade)`
**O que faz:** Retorna lista com N pacientes fictícios.

```java
public static List<Paciente> gerarListaPacientesMock(int quantidade) {
    List<Paciente> pacientes = new ArrayList<>();
    
    for (int i = 1; i <= quantidade; i++) {
        Paciente p = new Paciente();
        p.setNome("Paciente Mock " + i);
        p.setCpf(String.format("%011d", i)); // 00000000001, 00000000002, etc
        p.setNumeroCarteirinha("MOCK" + String.format("%03d", i));
        p.setStatus(StatusPaciente.ATIVO);
        
        pacientes.add(p);
    }
    
    return pacientes;
}

// Uso:
List<Paciente> pacientes = PacienteMockUtil.gerarListaPacientesMock(10);
pacienteDB.cadastrarTodos(pacientes); // Popula banco com 10 pacientes
```

---

#### 🧪 `gerarPacienteComPlano(TipoPlano tipo)`
**O que faz:** Retorna paciente já com plano atribuído.

```java
public static Paciente gerarPacienteComPlano(TipoPlano tipo) {
    Paciente p = gerarPacienteMock();
    
    PlanoSaude plano;
    if (tipo == TipoPlano.BASICO) {
        plano = new PlanoBasico(PlanosDeSaude.UNIMED, "MOCK-BASICO", p);
    } else {
        plano = new PlanoPremium(PlanosDeSaude.UNIMED, "MOCK-PREMIUM", p);
    }
    
    p.setPlanoSaude(plano);
    return p;
}
```

---

#### 🧪 `popular DatabaseComMocks()`
**O que faz:** Popula todo o sistema com dados de teste.

```java
public static void popularDatabaseComMocks() {
    // 10 pacientes
    List<Paciente> pacientes = gerarListaPacientesMock(10);
    
    // 5 médicos
    List<Medico> medicos = gerarListaMedicosMock(5);
    
    // 20 horários
    for (Medico m : medicos) {
        for (int i = 0; i < 4; i++) {
            Date data = adicionarDias(new Date(), i);
            Horario h = new Horario(m, data, true);
            agendaDB.adicionar(h);
        }
    }
    
    System.out.println("✅ Database populado com dados de teste!");
}
```

**Uso:** Executar uma vez no início do desenvolvimento para ter dados prontos.

---

## 🎨 Padrões de Uso

### ✅ Boas Práticas:

```java
// ✅ Métodos estáticos (não precisa instanciar)
ValidacaoUtil.validarCPF(cpf);

// ✅ Sempre validar antes de salvar
if (!ValidacaoUtil.validarEmail(email)) {
    System.out.println(ConsoleColors.RED + "❌ Email inválido!" + ConsoleColors.RESET);
    return;
}

// ✅ Formatar antes de exibir
String cpfFormatado = ValidacaoUtil.formatarCPF(paciente.getCpf());
System.out.println("CPF: " + cpfFormatado);
```

### ❌ Evitar:

```java
// ❌ Não instanciar classes utilitárias
ValidacaoUtil util = new ValidacaoUtil(); // Desnecessário

// ❌ Não duplicar validações
// Se já existe ValidacaoUtil.validarCPF(), não criar outra versão na View

// ❌ Não misturar responsabilidades
// ValidacaoUtil não deve acessar banco de dados
// ValidacaoUtil não deve exibir mensagens para usuário (isso é View)
```

---

## 📊 Estrutura de Classes Utilitárias

```java
public final class MinhaUtil { // final = não pode ser herdada
    
    // Construtor privado = não pode ser instanciada
    private MinhaUtil() {
        throw new UnsupportedOperationException("Classe utilitária");
    }
    
    // Todos métodos são estáticos
    public static boolean validar(String valor) {
        // ...
    }
    
    public static String formatar(String valor) {
        // ...
    }
}
```

---

## 🧪 Testes Unitários

```java
@Test
public void deveValidarCPFCorreto() {
    assertTrue(ValidacaoUtil.validarCPF("123.456.789-09"));
}

@Test
public void deveRejeitarCPFInvalido() {
    assertFalse(ValidacaoUtil.validarCPF("111.111.111-11"));
    assertFalse(ValidacaoUtil.validarCPF("123.456.789-00")); // Dígito errado
}

@Test
public void deveFormatarCPFCorretamente() {
    String formatado = ValidacaoUtil.formatarCPF("12345678900");
    assertEquals("123.456.789-00", formatado);
}
```

---

## 🔗 Integração com Outras Camadas

```
Service Layer                     Util Package
─────────────────────────────────────────────
cadastrar(Paciente p) {
    if (!ValidacaoUtil.validarCPF(p.getCpf())) {
        throw new ValidationException("CPF inválido");
    }
    // ...
}

View Layer                        Util Package
─────────────────────────────────────────────
exibirPaciente(Paciente p) {
    String cpf = ValidacaoUtil.formatarCPF(p.getCpf());
    System.out.println(ConsoleColors.BLUE + "CPF: " + cpf + ConsoleColors.RESET);
}
```

---

## 📝 Checklist de Utilitários

Ao criar nova classe utilitária, garantir:

- ✅ Classe `final` (não pode ser herdada)
- ✅ Construtor `private` (não pode ser instanciada)
- ✅ Todos métodos `static`
- ✅ Métodos sem efeitos colaterais (stateless)
- ✅ Nomes descritivos (validarCPF, formatarData, etc)
- ✅ Documentação Javadoc
- ✅ Testes unitários

---

**Última atualização:** 25/11/2025
