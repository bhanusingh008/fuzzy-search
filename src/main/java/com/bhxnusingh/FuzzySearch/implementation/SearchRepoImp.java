package com.bhxnusingh.FuzzySearch.implementation;

import com.bhxnusingh.FuzzySearch.repository.SearchRepo;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.bson.Document;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class SearchRepoImp implements SearchRepo {

    @Autowired
    MongoClient mongoClient;

    @Autowired
    MongoConverter converter;

    @Override
    public List<Object> findByText(String text) {

        List<Object> list = new ArrayList<>();

        MongoDatabase database = mongoClient.getDatabase("foodList");
        MongoCollection<org.bson.Document> collection = database.getCollection("jsons");
        AggregateIterable<Document> result = collection.aggregate(List.of(new Document("$search",
                new Document("text",
                        new Document("query", text)
                                .append("path", "title")
                                .append("fuzzy",
                                        new Document())))));


        result.forEach(document -> list.add(converter.read(Object.class, document)));

        return list;

    }
}
