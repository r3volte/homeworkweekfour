package homeworkweekfour.demo.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import homeworkweekfour.demo.model.Car;
import homeworkweekfour.demo.service.CarService;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.WeakHashMap;

import static homeworkweekfour.demo.service.MagicWorlds.*;

@Component
public class CarEditorGrid {


  public Editor<Car> getCarEditor(Grid<Car> grid, Binder<Car> binder) {
    com.vaadin.flow.component.grid.editor.Editor<Car> editor = grid.getEditor();
    editor.setBinder(binder);
    editor.setBuffered(true);
    return editor;
  }

  public void fieldEditor(Grid.Column<Car> colorColumn, Binder<Car> binder, Div validationStatus, String value) {
    TextField colorField = new TextField();
    binder.forField(colorField)
            .withStatusLabel(validationStatus).bind(value + "");
    colorColumn.setEditorComponent(colorField);
  }

  public Button createRemoveButton(Grid<Car> grid, Car car, CarService carService) {
    return new Button(USER_DELETE, clickEvent -> {
      ListDataProvider<Car> dataProvider =
              (ListDataProvider<Car>) grid.getDataProvider();
      dataProvider.getItems().remove(car);
      carService.deleteCar(car);
      dataProvider.refreshAll();
    });
  }

  public void buttonGrid(Grid<Car> grid, com.vaadin.flow.component.grid.editor.Editor<Car> editor) {
    Collection<Button> editButtons = Collections
            .newSetFromMap(new WeakHashMap<>());
    Grid.Column<Car> editorColumn = grid.addComponentColumn(superior -> {
      Button edit = new Button(USER_EDIT);
      edit.addClassName(EDIT);
      edit.addClickListener(e -> {
        editor.editItem(superior);
      });
      edit.setEnabled(!editor.isOpen());
      editButtons.add(edit);
      return edit;
    });
    editGridOptions(grid, editor, editButtons, editorColumn);
  }

  public void saveListener(CarService carService, Grid<Car> grid, com.vaadin.flow.component.grid.editor.Editor<Car> editor) {
    editor.addSaveListener(event -> {
      Optional<Car> car = carService.getCar(event.getItem().getCarId());
      car.map(theCar -> {
        theCar.setMark(event.getItem().getMark());
        theCar.setModel(event.getItem().getModel());
        theCar.setColor(event.getItem().getColor());
        return theCar;
      });
      editor.cancel();
      grid.getDataProvider().refreshAll();
    });
  }

  public void editGridOptions(Grid grid, com.vaadin.flow.component.grid.editor.Editor editor,
                              Collection<Button> editButtons, Grid.Column editorColumn) {
    editor.addOpenListener(e -> editButtons.stream()
            .forEach(button -> button.setEnabled(!editor.isOpen())));
    editor.addCloseListener(e -> editButtons.stream()
            .forEach(button -> button.setEnabled(!editor.isOpen())));

    Button save = new Button(USER_SAVE, e -> editor.save());
    save.addClassName(SAVE);

    Button cancel = new Button(USER_CANCEL, e -> editor.cancel());
    cancel.addClassName(CANCEL);

    grid.getElement().addEventListener(KEY_EVENT, event -> editor.cancel())
            .setFilter(KEY_FILTER);

    Div buttons = new Div(save, cancel);
    editorColumn.setEditorComponent(buttons);
  }
}
