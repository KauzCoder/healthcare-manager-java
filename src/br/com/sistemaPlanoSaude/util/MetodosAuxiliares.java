package br.com.sistemaPlanoSaude.util;

public class MetodosAuxiliares {
    
    public static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public static void pausarTela() {
    try {
        Thread.sleep(2000); // pausa 2 segundos
    } catch (InterruptedException ignored) {
        Thread.currentThread().interrupt();
    }
    }
}