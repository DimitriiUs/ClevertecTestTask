package by.home.aspect.service;


import by.home.aspect.entity.Car;

public interface Service {

    Car save(Car car);

    Car get(int id);

    void delete(int id);

    Car update(int id, Car car);
}
