package homeworkweekfour.demo.service;


import homeworkweekfour.demo.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {

  Optional<Car> getCar(long id);

  List<Car> getCars();

  void addCar(Car car);

  void deleteCar(Car car);
}
