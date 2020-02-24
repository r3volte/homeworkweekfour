package homeworkweekfour.demo.ui;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.data.binder.Binder;
import homeworkweekfour.demo.model.Car;
import homeworkweekfour.demo.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static homeworkweekfour.demo.service.MagicWorlds.*;

@Component
public class CarGrid {

  private CarEditorGrid carEditorGrid;

  @Autowired
  public CarGrid(CarEditorGrid carEditorGrid) {
    this.carEditorGrid = carEditorGrid;
  }

  public Grid<Car> setupGrid(CarService carService) {
    Grid<Car> grid = new Grid<>();
    grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
    grid.setItems(carService.getCars());
    grid.addColumn(Car::getCarId).setHeader(ID);
    Grid.Column<Car> markColumn = grid.addColumn(Car::getMark).setHeader(MARK);
    Grid.Column<Car> modelColumn = grid.addColumn(Car::getModel).setHeader(MODEL);
    Grid.Column<Car> colorColumn = grid.addColumn(Car::getColor).setHeader(COLOR);
    grid.addComponentColumn(item -> carEditorGrid.createRemoveButton(grid, item, carService))
            .setHeader(USER_OPTIONS);

    Binder<Car> binder = new Binder<>(Car.class);
    Editor<Car> editor = carEditorGrid.getCarEditor(grid, binder);

    Div validationStatus = new Div();
    validationStatus.setId(VALIDATION);

    carEditorGrid.fieldEditor(markColumn, binder, validationStatus, VALUE_MARK);

    carEditorGrid.fieldEditor(modelColumn, binder, validationStatus, VALUE_MODEL);

    carEditorGrid.fieldEditor(colorColumn, binder, validationStatus, VALUE_COLOR);

    carEditorGrid.buttonGrid(grid, editor);
    carEditorGrid.saveListener(carService, grid, editor);
    return grid;
  }

}
