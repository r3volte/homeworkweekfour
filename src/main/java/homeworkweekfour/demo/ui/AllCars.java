package homeworkweekfour.demo.ui;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import homeworkweekfour.demo.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;

import static homeworkweekfour.demo.service.MagicWorlds.ALL_CARS;

@Route(value = ALL_CARS)
public class AllCars extends HorizontalLayout {

  private CarGrid carGrid;
  private CarService carService;

  @Autowired
  public AllCars(CarGrid carGrid, CarService carService) {
    this.carGrid = carGrid;
    this.carService = carService;
    add(carGrid.setupGrid(carService));
  }
}
