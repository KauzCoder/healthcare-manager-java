package br.com.sistemaPlanoSaude.util;

public class MetodosAuxiliares {
    
    public static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public static void pausarTela() {
        System.out.print("\n👉 Pressione ENTER para continuar...");
        new java.util.Scanner(System.in).nextLine();
    }
}