package com.mycompany.coffee_shop.dao;

import com.mycompany.coffee_shop.model.Item;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por acessar e manipular os dados de itens no banco de dados MongoDB.
 * Implementa o padrão Singleton para garantir apenas uma instância.
 */
public class ItemDAO {

    private static ItemDAO instance;
    private final MongoCollection<Document> collection;

    /**
     * Construtor privado para implementar o padrão Singleton.
     */
    private ItemDAO() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("coffee_shop_db");
        collection = database.getCollection("itens");
    }

    /**
     * Retorna a instância única da classe ItemDAO.
     *
     * @return instância única de ItemDAO.
     */
    public static ItemDAO getInstance() {
        if (instance == null) {
            instance = new ItemDAO();
        }
        return instance;
    }

    /**
     * Insere um novo item na coleção MongoDB.
     *
     * @param item o item a ser inserido.
     */
    public void inserir(Item item) {
        Document doc = new Document("idItem", item.getIdItem())
                .append("nome", item.getNome())
                .append("quantidade", item.getQuantidade())
                .append("valor", item.getValor())
                .append("foto", item.getFoto());
        collection.insertOne(doc);
    }

    /**
     * Retorna todos os itens armazenados no banco.
     *
     * @return lista de itens.
     */
    public List<Item> listarTodos() {
        List<Item> itens = new ArrayList<>();
        FindIterable<Document> docs = collection.find();

        for (Document doc : docs) {
            Item item = new Item(
                    doc.getString("idItem"),
                    doc.getString("nome"),
                    doc.getInteger("quantidade", 0),
                    doc.getDouble("valor"),
                    doc.getString("foto")
            );
            itens.add(item);
        }

        return itens;
    }

    /**
     * Atualiza um item existente com base no idItem.
     *
     * @param item item com os novos dados.
     */
    public void atualizar(Item item) {
        Document update = new Document("$set", new Document()
                .append("nome", item.getNome())
                .append("quantidade", item.getQuantidade())
                .append("valor", item.getValor())
                .append("foto", item.getFoto()));

        collection.updateOne(Filters.eq("idItem", item.getIdItem()), update);
    }

    /**
     * Remove um item com base no seu idItem.
     *
     * @param idItem ID do item a ser removido.
     */
    public void remover(String idItem) {
        collection.deleteOne(Filters.eq("idItem", idItem));
    }
}
