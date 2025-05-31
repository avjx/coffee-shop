package com.mycompany.coffee_shop;

/**
 * Classe principal que inicia a aplicação Coffee Shop.
 */
public class Main {

    /**
     * Método principal.
     *
     * @param args argumentos da linha de comando.
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new com.mycompany.coffee_shop.view.MainView().setVisible(true);
        });
    }
}
