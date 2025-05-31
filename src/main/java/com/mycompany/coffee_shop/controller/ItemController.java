package com.mycompany.coffee_shop.controller;

import com.mycompany.coffee_shop.model.Item;
import com.mycompany.coffee_shop.util.MongoConnection;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por controlar operações de CRUD para itens.
 * Aplica o padrão Singleton.
 */
public class ItemController {

    private static ItemController instance;

    private final MongoCollection<Document> collection;

    /**
     * Construtor privado para garantir Singleton.
     */
    private ItemController() {
        MongoDatabase database = MongoConnection.getInstance().getDatabase();
        collection = database.getCollection("itens");
    }

    /**
     * Retorna a instância única da classe.
     * @return instância Singleton
     */
    public static ItemController getInstance() {
        if (instance == null) {
            instance = new ItemController();
        }
        return instance;
    }

    /**
     * Adiciona um novo item ao banco de dados.
     * @param item Item a ser adicionado
     */
    public void adicionarItem(Item item) {
        Document doc = new Document("id_item", item.getIdItem())
                .append("nome", item.getNome())
                .append("quantidade", item.getQuantidade())
                .append("valor", item.getValor())
                .append("foto", item.getFoto());
        collection.insertOne(doc);
    }

    /**
     * Retorna todos os itens cadastrados.
     * @return lista de itens
     */
    public List<Item> listarItens() {
        List<Item> itens = new ArrayList<>();
        for (Document doc : collection.find()) {
           String id = doc.getString("id_item");
           String nome = doc.getString("nome");
           Integer quantidade = doc.getInteger("quantidade", 0);
           Double valor = doc.getDouble("valor");
           if (valor == null) valor = 0.0;
           String foto = doc.getString("foto");

           Item item = new Item(id, nome, quantidade, valor, foto);
           itens.add(item);
        }
        return itens;
    }


    /**
     * Remove um item com base no ID.
     * @param id ID do item a ser removido
     */
    public void removerItem(String id) {
        collection.deleteOne(new Document("id_item", id));
    }

    /**
     * Atualiza um item com base no ID.
     * @param item Novo item com os dados atualizados
     */
    public void atualizarItem(Item item) {
        Document filtro = new Document("id_item", item.getIdItem());
        Document atualizacao = new Document("$set", new Document("nome", item.getNome())
                .append("quantidade", item.getQuantidade())
                .append("valor", item.getValor())
                .append("foto", item.getFoto()));
        collection.updateOne(filtro, atualizacao);
    }
    
    /**
    * Verifica se já existe um item com o ID fornecido.
    *
    * @param id O ID do item a ser verificado.
    * @return true se o ID já existir, false caso contrário.
    */
    public boolean idExiste(String id) {
        Document filtro = new Document("id_item", id);
        return collection.find(filtro).first() != null;
    }

}
