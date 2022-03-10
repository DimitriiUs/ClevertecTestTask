package by.home.aspect.service.impl;

import by.home.aspect.annotations.Cache;
import by.home.aspect.entity.Car;
import by.home.aspect.repository.Repository;
import by.home.aspect.repository.impl.RepositoryImpl;
import by.home.aspect.service.Service;

public class CarServiceImpl implements Service {

    Repository repository = new RepositoryImpl();

    @Cache
    @Override
    public Car save(Car car) {
        return repository.save(car);
    }

    @Cache
    @Override
    public Car get(int id) {
        return repository.get(id);
    }

    @Cache
    @Override
    public void delete(int id) {
        repository.delete(id);
    }

    @Cache
    @Override
    public Car update(int id, Car car) {
        return repository.update(id, car);
    }


}
