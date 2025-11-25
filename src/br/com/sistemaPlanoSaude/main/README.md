# 🚀 Main Package

## 🎯 Objetivo

Esta pasta contém o **ponto de entrada** da aplicação - a classe `Main.java` com o método `main()` que inicia todo o sistema. É a "porta de entrada" que o Java executa quando você roda o programa.

## 📐 Arquitetura

O Main é a **camada mais externa** do sistema, responsável por:
1. Inicializar o sistema
2. Configurar dependências
3. Chamar o menu principal
4. Gerenciar o ciclo de vida da aplicação

```
JVM (Java Virtual Machine)
     ↓
Main.main() (VOCÊ ESTÁ AQUI) → Inicia tudo
     ↓
MenuPrincipal → Tela inicial
     ↓
Interfaces/Views → Interação com usuário
     ↓
Services → Lógica de negócio
     ↓
Database → Persistência
```

---

## 📦 **Main.java**

**Responsabilidade:** Ponto de entrada da aplicação Java.

### Estrutura Básica:

```java
package br.com.sistemaPlanoSaude.main;

import br.com.sistemaPlanoSaude.view.menu.MenuPrincipal;
import br.com.sistemaPlanoSaude.util.ConsoleColors;

public class Main {
    
    /**
     * Método principal - executado pela JVM ao iniciar programa
     * @param args Argumentos da linha de comando
     */
    public static void main(String[] args) {
        try {
            // 1. Exibir banner de boas-vindas
            exibirBanner();
            
            // 2. Inicializar sistema
            inicializarSistema();
            
            // 3. Executar menu principal
            MenuPrincipal menu = new MenuPrincipal();
            menu.executar();
            
            // 4. Finalizar
            System.out.println(ConsoleColors.GREEN + 
                "✅ Sistema encerrado com sucesso!" + 
                ConsoleColors.RESET);
            
        } catch (Exception e) {
            System.err.println(ConsoleColors.RED + 
                "❌ Erro crítico no sistema: " + e.getMessage() + 
                ConsoleColors.RESET);
            e.printStackTrace();
            System.exit(1); // Código 1 = erro
        }
    }
    
    private static void exibirBanner() {
        System.out.println(ConsoleColors.CYAN + """
            ╔══════════════════════════════════════════════╗
            ║                                              ║
            ║   SISTEMA DE GERENCIAMENTO DE PLANO DE SAÚDE ║
            ║                                              ║
            ║   Versão: 1.0.0                              ║
            ║   Desenvolvido em Java                       ║
            ║                                              ║
            ╚══════════════════════════════════════════════╝
            """ + ConsoleColors.RESET);
    }
    
    private static void inicializarSistema() {
        System.out.println(ConsoleColors.YELLOW + 
            "⏳ Inicializando sistema..." + 
            ConsoleColors.RESET);
        
        // Configurações iniciais
        configurarEncoding();
        inicializarDatabase();
        carregarConfiguracoes();
        
        System.out.println(ConsoleColors.GREEN + 
            "✅ Sistema inicializado!" + 
            ConsoleColors.RESET);
        System.out.println();
    }
    
    private static void configurarEncoding() {
        // Garantir encoding UTF-8 para caracteres especiais
        System.setProperty("file.encoding", "UTF-8");
    }
    
    private static void inicializarDatabase() {
        // Opcional: Popular banco com dados iniciais
        // PacienteMockUtil.popularDatabaseComMocks();
    }
    
    private static void carregarConfiguracoes() {
        // Futuro: Carregar arquivo config.properties
        // Properties props = new Properties();
        // props.load(new FileInputStream("config.properties"));
    }
}
```

---

## 🔄 Fluxo de Execução

### 1️⃣ **Inicialização**
```
JVM inicia → Encontra Main.main() → Executa linha por linha
```

### 2️⃣ **Banner**
```java
exibirBanner();
```
Exibe logo e informações do sistema.

### 3️⃣ **Configuração**
```java
inicializarSistema();
  ├─ configurarEncoding() → UTF-8 para acentos
  ├─ inicializarDatabase() → Conexões/dados iniciais
  └─ carregarConfiguracoes() → Arquivos de config
```

### 4️⃣ **Menu Principal**
```java
MenuPrincipal menu = new MenuPrincipal();
menu.executar(); // Loop infinito até usuário sair
```

### 5️⃣ **Finalização**
```java
System.out.println("✅ Sistema encerrado");
// JVM termina processo
```

---

## 🎯 Responsabilidades do Main

### ✅ O que Main DEVE fazer:

1. **Inicializar aplicação**
   ```java
   // Configurar ambiente
   System.setProperty("file.encoding", "UTF-8");
   
   // Conectar banco de dados (futuro)
   DatabaseConnection.initialize();
   
   // Carregar configurações
   Config.load();
   ```

2. **Chamar menu principal**
   ```java
   new MenuPrincipal().executar();
   ```

3. **Tratamento de erros críticos**
   ```java
   try {
       // Código principal
   } catch (Exception e) {
       System.err.println("❌ Erro fatal: " + e.getMessage());
       System.exit(1);
   }
   ```

4. **Mensagens de feedback**
   ```java
   System.out.println("✅ Sistema iniciado");
   System.out.println("👋 Até logo!");
   ```

### ❌ O que Main NÃO DEVE fazer:

- ❌ Lógica de negócio (isso é Service)
- ❌ Interface com usuário (isso é View)
- ❌ Acesso a dados (isso é Database)
- ❌ Validações (isso é Util/Service)

**Regra de ouro:** Main deve ser **simples** e **limpo** - apenas inicializa e delega.

---

## 🛠️ Configurações Avançadas

### 📝 Argumentos de Linha de Comando

```java
public static void main(String[] args) {
    // args[0] = primeiro argumento
    // args[1] = segundo argumento
    // ...
    
    if (args.length > 0) {
        String modo = args[0];
        
        switch (modo) {
            case "--debug" -> {
                System.setProperty("debug.mode", "true");
                System.out.println("🐛 Modo debug ativado");
            }
            case "--populate" -> {
                PacienteMockUtil.popularDatabaseComMocks();
                System.out.println("✅ Database populado com mocks");
            }
            case "--help" -> {
                exibirAjuda();
                System.exit(0);
            }
        }
    }
    
    // Continua execução normal...
}

private static void exibirAjuda() {
    System.out.println("""
        Uso: java Main [opções]
        
        Opções:
          --debug      Ativa modo debug com logs detalhados
          --populate   Popula database com dados de teste
          --help       Exibe esta mensagem de ajuda
        
        Exemplos:
          java Main
          java Main --debug
          java Main --populate
        """);
}
```

**Como executar:**
```bash
# Normal
java Main

# Com modo debug
java Main --debug

# Populando database
java Main --populate
```

---

### ⚙️ Variáveis de Ambiente

```java
private static void carregarConfiguracoes() {
    // Ler variáveis de ambiente
    String ambiente = System.getenv("AMBIENTE");
    String dbUrl = System.getenv("DATABASE_URL");
    
    if (ambiente == null) {
        ambiente = "desenvolvimento"; // Padrão
    }
    
    System.out.println("🌍 Ambiente: " + ambiente);
    
    if ("producao".equals(ambiente)) {
        // Configurações de produção
        System.setProperty("log.level", "ERROR");
    } else {
        // Configurações de desenvolvimento
        System.setProperty("log.level", "DEBUG");
    }
}
```

**Como definir:**
```bash
# Windows (PowerShell)
$env:AMBIENTE = "producao"
java Main

# Linux/Mac
export AMBIENTE=producao
java Main
```

---

### 🔐 Tratamento de Exceções

```java
public static void main(String[] args) {
    try {
        executarSistema();
    } catch (DatabaseException e) {
        System.err.println("❌ Erro no banco de dados: " + e.getMessage());
        System.err.println("💡 Verifique as configurações de conexão");
        System.exit(2);
    } catch (ConfigurationException e) {
        System.err.println("❌ Erro de configuração: " + e.getMessage());
        System.err.println("💡 Verifique o arquivo config.properties");
        System.exit(3);
    } catch (Exception e) {
        System.err.println("❌ Erro desconhecido: " + e.getMessage());
        e.printStackTrace();
        System.exit(1);
    }
}

// Códigos de saída:
// 0 = Sucesso
// 1 = Erro genérico
// 2 = Erro de banco de dados
// 3 = Erro de configuração
```

---

## 📊 Ciclo de Vida da Aplicação

```
┌─────────────────────────────────────┐
│ 1. JVM inicia processo              │
└──────────────┬──────────────────────┘
               ↓
┌─────────────────────────────────────┐
│ 2. Main.main() é chamado            │
└──────────────┬──────────────────────┘
               ↓
┌─────────────────────────────────────┐
│ 3. Inicialização                    │
│    - Banner                         │
│    - Configurações                  │
│    - Database                       │
└──────────────┬──────────────────────┘
               ↓
┌─────────────────────────────────────┐
│ 4. Loop principal                   │
│    MenuPrincipal.executar()         │
│    ↓                                │
│    Usuário interage com sistema     │
│    ↓                                │
│    Usuário escolhe "Sair"           │
└──────────────┬──────────────────────┘
               ↓
┌─────────────────────────────────────┐
│ 5. Finalização                      │
│    - Fechar conexões                │
│    - Salvar estado                  │
│    - Mensagem de despedida          │
└──────────────┬──────────────────────┘
               ↓
┌─────────────────────────────────────┐
│ 6. JVM encerra processo             │
│    System.exit(0) ou return         │
└─────────────────────────────────────┘
```

---

## 🐛 Debug e Logs

### Modo Debug:
```java
public static void main(String[] args) {
    boolean debug = Arrays.asList(args).contains("--debug");
    
    if (debug) {
        System.setProperty("debug.mode", "true");
        System.out.println("🐛 DEBUG: Inicializando sistema...");
        System.out.println("🐛 DEBUG: Versão Java: " + System.getProperty("java.version"));
        System.out.println("🐛 DEBUG: OS: " + System.getProperty("os.name"));
    }
    
    // ... resto do código
}
```

### Informações do Sistema:
```java
private static void exibirInfoSistema() {
    System.out.println("📊 Informações do Sistema:");
    System.out.println("   Java: " + System.getProperty("java.version"));
    System.out.println("   OS: " + System.getProperty("os.name"));
    System.out.println("   Usuário: " + System.getProperty("user.name"));
    System.out.println("   Diretório: " + System.getProperty("user.dir"));
    System.out.println("   Encoding: " + System.getProperty("file.encoding"));
    System.out.println();
}
```

---

## 🎨 Exemplos de Banners

### Banner Simples:
```java
System.out.println("""
    ╔════════════════════════════╗
    ║  SISTEMA DE PLANO DE SAÚDE ║
    ╚════════════════════════════╝
    """);
```

### Banner ASCII Art:
```java
System.out.println("""
     ____  _                        _       ____             _      
    |  _ \\| | __ _ _ __   ___    __| | ___ / ___|  __ _ _   _| | ___ 
    | |_) | |/ _` | '_ \\ / _ \\  / _` |/ _ \\\\___ \\ / _` | | | | |/ _ \\
    |  __/| | (_| | | | | (_) || (_| |  __/ ___) | (_| | |_| | |  __/
    |_|   |_|\\__,_|_| |_|\\___/  \\__,_|\\___||____/ \\__,_|\\__,_|_|\\___|
    
    Versão 1.0.0 - Sistema de Gerenciamento Hospitalar
    """);
```

### Banner com Informações:
```java
System.out.println(ConsoleColors.CYAN + """
    ╔══════════════════════════════════════════════════════════╗
    ║                                                          ║
    ║           🏥 SISTEMA DE PLANO DE SAÚDE 🏥               ║
    ║                                                          ║
    ║  Versão:      1.0.0                                     ║
    ║  Ambiente:    Desenvolvimento                           ║
    ║  Data:        %s                              ║
    ║  Desenvolvido por: Equipe DevHealth                     ║
    ║                                                          ║
    ╚══════════════════════════════════════════════════════════╝
    """.formatted(new SimpleDateFormat("dd/MM/yyyy").format(new Date())) 
    + ConsoleColors.RESET);
```

---

## 🔄 Shutdown Hooks (Finalização Graceful)

Para garantir que recursos sejam liberados mesmo se o programa for interrompido:

```java
public static void main(String[] args) {
    // Registrar hook de finalização
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        System.out.println("\n⏹️  Encerrando sistema...");
        
        // Fechar conexões
        DatabaseConnection.close();
        
        // Salvar estado
        EstadoSistema.salvar();
        
        System.out.println("✅ Sistema encerrado corretamente");
    }));
    
    // Código principal...
    new MenuPrincipal().executar();
}
```

**Quando é executado:**
- Usuário aperta Ctrl+C
- System.exit() é chamado
- JVM é encerrada normalmente

---

## 📝 Boas Práticas

✅ **Manter Main simples:**
```java
// ✅ BOM - Delega responsabilidades
public static void main(String[] args) {
    inicializar();
    executar();
    finalizar();
}
```

```java
// ❌ RUIM - Main fazendo tudo
public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Digite CPF:");
    String cpf = scanner.nextLine();
    // 500 linhas de código...
}
```

✅ **Tratar exceções:**
```java
try {
    executar();
} catch (Exception e) {
    log.error("Erro", e);
    System.exit(1);
}
```

✅ **Feedback ao usuário:**
```java
System.out.println("⏳ Carregando...");
carregarDados();
System.out.println("✅ Pronto!");
```

---

## 🧪 Testando o Main

Como Main é o ponto de entrada, testar diretamente é difícil. Solução:

```java
// Main.java
public class Main {
    public static void main(String[] args) {
        new Main().run(args); // Delega para método de instância
    }
    
    // Método testável
    public void run(String[] args) {
        inicializar();
        executar();
        finalizar();
    }
}

// MainTest.java
@Test
public void deveInicializarSistema() {
    Main main = new Main();
    main.run(new String[]{});
    // Asserts...
}
```

---

**Última atualização:** 25/11/2025
