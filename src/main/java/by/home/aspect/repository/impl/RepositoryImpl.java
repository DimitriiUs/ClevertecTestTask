package by.home.aspect.repository.impl;

import by.home.aspect.entity.Car;
import by.home.aspect.repository.Repository;

import java.util.HashMap;

public class RepositoryImpl implements Repository {

    private final HashMap<Integer, Object> hashMap = new HashMap<>();

    public HashMap<Integer, Object> getHashMap() {
        return hashMap;
    }

    @Override
    public Car save(Car car) {
        System.out.println("Car was saved into DAO");
        return car;
    }

    @Override
    public Car get(int id) {
        System.out.println("Got Car from DAO");
        return null;
    }

    @Override
    public void delete(int id) {
        System.out.println("Car deleted from DAO");
    }

    @Override
    public Car update(int id, Car car) {
        System.out.println("Car was updated in DAO");
        return car;
    }

}
