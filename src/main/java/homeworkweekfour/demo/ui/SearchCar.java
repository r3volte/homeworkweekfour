package homeworkweekfour.demo.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import homeworkweekfour.demo.model.Car;
import homeworkweekfour.demo.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static homeworkweekfour.demo.service.MagicWorlds.*;

@Route(value = SEARCH_CAR)
public class SearchCar extends VerticalLayout {

  private CarService carService;

  @Autowired
  public SearchCar(CarService carService) {
    this.carService = carService;
    add(form());
  }

  private FormLayout form() {
    FormLayout formLayout = new FormLayout();
    formLayout.setWidth(WIDTH);
    TextField textField = new TextField(SEARCH_CAR_LABEL);
    Button button = new Button(SEARCH_CAR_LABEL);
    TextArea area = new TextArea();
    formLayout.add(textField, button, area);
    searchForCar(textField, button, area);
    return formLayout;
  }

  private void searchForCar(TextField textField, Button button, TextArea area) {
    button.addClickListener(click -> {
      long id = Long.parseLong(textField.getValue());
      Optional<Car> car = carService.getCar(id);
      car.ifPresentOrElse(theCar -> area
                      .setValue(theCar.getMark()
                              + " " + theCar.getModel()
                              + " " + theCar.getColor()),
              () -> area.setValue(CAR_NOT_FOUND));
    });
  }
}
