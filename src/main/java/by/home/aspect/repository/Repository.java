package by.home.aspect.repository;

import by.home.aspect.entity.Car;

public interface Repository {
    Car save(Car car);

    Car get(int id);

    void delete(int id);

    Car update(int id, Car car);
}
