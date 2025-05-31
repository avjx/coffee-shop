package com.mycompany.coffee_shop.util;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

/**
 * Singleton para acesso ao banco MongoDB.
 */
public class MongoConnection {
    private static MongoConnection instance;
    private MongoClient client;
    private MongoDatabase database;

    private MongoConnection() {
        client = MongoClients.create("mongodb://localhost:27017");
        database = client.getDatabase("coffee_shop_db");
    }

    public static MongoConnection getInstance() {
        if (instance == null) {
            instance = new MongoConnection();
        }
        return instance;
    }

    public MongoDatabase getDatabase() {
        return database;
    }
}
