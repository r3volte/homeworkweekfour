package homeworkweekfour.demo.service;


import homeworkweekfour.demo.model.Car;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {

  private List<Car> cars;

  public CarServiceImpl() {
    this.cars = new ArrayList<>();
    cars.add(new Car(1L, "Audi", "A4", "Red"));
    cars.add(new Car(2L, "Cupra", "Ateca", "Black"));
    cars.add(new Car(3L, "Fiat", "Uno", "Blue"));
  }

  @Override
  public Optional<Car> getCar(long id) {
    return getCars()
            .stream()
            .filter(car -> car.getCarId() == id)
            .findFirst();
  }

  @Override
  public void addCar(Car car) {
    cars.add(car);
  }

  @Override
  public List<Car> getCars() {
    return this.cars;
  }


  @Override
  public void deleteCar(Car car) {
    cars.remove(car);
  }

}
