package homeworkweekfour.demo.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.Route;
import homeworkweekfour.demo.model.Car;
import homeworkweekfour.demo.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;

import static homeworkweekfour.demo.service.MagicWorlds.*;

@Route(value = ADD_CAR)
public class AddCar extends HorizontalLayout {

  private CarService carService;

  @Autowired
  public AddCar(CarService carService) {
    this.carService = carService;
    add(layout());
  }

  public FormLayout layout() {
    FormLayout formLayout = new FormLayout();
    TextField model = new TextField(MODEL);
    TextField mark = new TextField(MARK);
    TextField color = new TextField(COLOR);
    Button addButton = new Button(ADD_CAR_BUTTON);
    formLayout.add(model, mark, color, addButton);
    BeanValidationBinder<Car> binder = carBinder(model, mark, color);
    addCar(addButton, binder);
    return formLayout;
  }

  private void addCar(Button addButton, BeanValidationBinder<Car> binder) {
    Car car = new Car();
    addButton.addClickListener(buttonClickEvent -> {
      if (binder.writeBeanIfValid(car)) {
        car.setCarId(carService.getCars().size() + 1L);
        carService.addCar(car);
        addButton.getUI()
                .ifPresent(ui -> ui.navigate(ALL_CARS));
      }
    });
  }

  private BeanValidationBinder<Car> carBinder(TextField model, TextField mark, TextField color) {
    BeanValidationBinder<Car> binder =
            new BeanValidationBinder<>(Car.class);
    binder.forField(model)
            .asRequired(MODEL_ERROR)
            .bind(Car::getModel, Car::setModel);
    binder.forField(mark)
            .asRequired(MARK_ERROR)
            .bind(Car::getMark, Car::setMark);
    binder.forField(color)
            .asRequired(COLOR_ERROR)
            .bind(Car::getColor, Car::setColor);
    return binder;
  }
}
