package br.com.sistemaPlanoSaude.view.interfaces;

import br.com.sistemaPlanoSaude.model.funcionarios.Administrador;
import br.com.sistemaPlanoSaude.view.formularios.FormularioAdministrador;

public class InterfaceAdministrador {
    
    public static void main(String[] args) {
        
        // 1. Instancia a classe que contém o formulário
        FormularioAdministrador formulario = new FormularioAdministrador();
        
        // 2. Chama o método de cadastro. 
        // O resultado (o objeto Administrador criado) é armazenado na variável 'novoAdmin'
        System.out.println("Iniciando o Cadastro do Administrador...");
        System.out.println("---------------------------------------");
        
        Administrador novoAdmin = formulario.criarAdministrador();
        
        // 3. Você pode agora usar o objeto 'novoAdmin' para salvar no banco de dados, 
        // em uma lista, ou qualquer outra operação.
        System.out.println("\n--- Resumo de Dados no Main ---");
        System.out.println("Objeto Administrador criado com ID: " + novoAdmin.getCpf());
        System.out.println("Pronto para persistência de dados!");
    }
}