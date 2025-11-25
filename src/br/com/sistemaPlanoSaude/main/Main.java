package br.com.sistemaPlanoSaude.main;
import br.com.sistemaPlanoSaude.view.menu.MenuPrincipal;

public class Main {
	    public static void main(String[] args) throws InterruptedException {
        System.out.println(
"""
  ▄         ██  ██ ██████ ▄████▄ ██    ██████ ██  ██   ▄█████ ▄████▄ █████▄  ██████ 
▄▄█▄▄       ██████ ██▄▄   ██▄▄██ ██      ██   ██████   ██     ██▄▄██ ██▄▄██▄ ██▄▄   
  █         ██  ██ ██▄▄▄▄ ██  ██ ██████  ██   ██  ██   ▀█████ ██  ██ ██   ██ ██▄▄▄▄ 
"""
);

    int maxGrupos = 10;

        System.out.println("\nCarregando programa:");

    for (int i = 1; i <= maxGrupos; i++) {
        printBarra(i, maxGrupos);
            Thread.sleep(300);
        }

        new MenuPrincipal().exibirMenuPrincipal();
    }

    private static void printBarra(int grupos, int total) {
        StringBuilder barra = new StringBuilder();
        int porcentagem = (grupos * 100) / total;

        for (int j = 0; j < grupos; j++) {
            barra.append(" ■ ");
        }

        System.out.print("\r" + barra + " " + porcentagem + "%");
    }
}