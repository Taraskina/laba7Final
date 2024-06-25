package com.dar.server.data.repositories;

import com.dar.server.data.entities.PersonEntity;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.stream.Stream;

/**
 * менеджер коллекции объектов,инкапсулирующий методы коллекции
 */
@Repository
public class CollectionRepository {
    PersonRepository spaceMarineRepository;


    private final HashSet<PersonEntity> collection;

    @Autowired
    public CollectionRepository(HashSet<PersonEntity> collection, PersonRepository spaceMarineRepository) {
        this.collection = collection;
        this.spaceMarineRepository =spaceMarineRepository;
    }

    @PostConstruct
    public void fillCollection() {
        this.collection.addAll(spaceMarineRepository.getAll());
    }


    public void add(PersonEntity spm) {
        collection.add(spm);
    }

    public void remove(PersonEntity spm) {
        collection.remove(spm);
    }

    public Stream<PersonEntity> getCollectionStream() {
        return collection.stream();

    }

    public int getCollectionSize() {
        return collection.size();
    }
}
