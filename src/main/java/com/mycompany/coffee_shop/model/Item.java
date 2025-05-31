package com.mycompany.coffee_shop.model;

/**
 * Representa um item no sistema de gerenciamento do Coffee Shop.
 * Cada item possui um identificador, nome, quantidade, valor e uma foto.
 */
public class Item {

    private String idItem;
    private String nome;
    private int quantidade;
    private double valor;
    private String foto;

    /**
     * Construtor padrão necessário para bibliotecas como MongoDB Java Driver.
     */
    public Item() {
    }

    /**
     * Construtor que inicializa todos os campos do item.
     *
     * @param idItem     Identificador único do item.
     * @param nome       Nome do item.
     * @param quantidade Quantidade disponível em estoque.
     * @param valor      Valor monetário do item.
     * @param foto       Caminho para a foto do item.
     */
    public Item(String idItem, String nome, int quantidade, double valor, String foto) {
        this.idItem = idItem;
        this.nome = nome;
        this.quantidade = quantidade;
        this.valor = valor;
        this.foto = foto;
    }

    /**
     * Retorna o ID do item.
     *
     * @return id do item.
     */
    public String getIdItem() {
        return idItem;
    }

    /**
     * Define o ID do item.
     *
     * @param idItem id do item.
     */
    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

    /**
     * Retorna o nome do item.
     *
     * @return nome do item.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do item.
     *
     * @param nome nome do item.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna a quantidade em estoque.
     *
     * @return quantidade em estoque.
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Define a quantidade em estoque.
     *
     * @param quantidade quantidade em estoque.
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * Retorna o valor do item.
     *
     * @return valor do item.
     */
    public double getValor() {
        return valor;
    }

    /**
     * Define o valor do item.
     *
     * @param valor valor do item.
     */
    public void setValor(double valor) {
        this.valor = valor;
    }

    /**
     * Retorna o caminho da foto do item.
     *
     * @return caminho da foto.
     */
    public String getFoto() {
        return foto;
    }

    /**
     * Define o caminho da foto do item.
     *
     * @param foto caminho da foto.
     */
    public void setFoto(String foto) {
        this.foto = foto;
    }
}
