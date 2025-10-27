package structPhase1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.converter.CharacterStringConverter;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class MartyesInfo extends Stage {

	BorderPane bPane = new BorderPane();
	Button addBT = new Button("add");
	Button deleteBT = new Button("delete");
	Button nextBT = new Button("Next");
	Button privousBT = new Button("previos");
	TextField nameTF = new TextField("Name");
	TextField ageTF = new TextField("age");
	TextField dateOfDeathTF = new TextField("DD/MM/YY");
	TextField genderTF = new TextField("gender");
	HBox textFiledHB = new HBox(10, nameTF, ageTF, dateOfDeathTF, genderTF);
	HBox buttonsHB = new HBox(10, addBT, deleteBT);
	VBox vBox = new VBox(10, textFiledHB, buttonsHB);

	Button searchBT = new Button("search");
	TextField searchTF = new TextField();
	HBox searchHB = new HBox(10, searchTF, searchBT);

	public MartyesInfo(Location l) {

		l.getList().sortList();
		Label label = new Label("the percatge is : " + percetageToOthers(l.getList()));
		label.setFont(new Font(15));
		vBox.getChildren().add(label);
		Label locationL = new Label(l.getLocation());
		VBox topVBox = new VBox(10, locationL, searchHB);
		bPane.setTop(topVBox);
		searchHB.setAlignment(Pos.CENTER);
		topVBox.setAlignment(Pos.CENTER);
		locationL.setFont(new Font("Verdana", 15));
		TableView<Martyer> tableView = new TableView<>();
		tableView.setEditable(true);
		TableColumn<Martyer, String> nameCol = new TableColumn<>("name");
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		nameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Martyer, String>>() {

			@Override
			public void handle(CellEditEvent<Martyer, String> e) {
				Martyer martyer = e.getRowValue();
				martyer.setName(e.getNewValue());
			}
		});

		TableColumn<Martyer, Integer> ageCol = new TableColumn<>("age");
		ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
		ageCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		ageCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Martyer, Integer>>() {

			@Override
			public void handle(CellEditEvent<Martyer, Integer> e) {
				Martyer martyer = e.getRowValue();
				martyer.setAge(e.getNewValue());
			}

		});
		TableColumn<Martyer, Date> dataOfDeathCol = new TableColumn<>("date of death");
		dataOfDeathCol.setCellValueFactory(new PropertyValueFactory<>("DateOfDeath"));
		dataOfDeathCol.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));
		dataOfDeathCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Martyer, Date>>() {

			@Override
			public void handle(CellEditEvent<Martyer, Date> e) {
				Martyer martyer = e.getRowValue();
				martyer.setDateOfDeath(e.getNewValue());
				// remove it from table view
				tableView.getItems().remove(martyer);
				l.getList().sortList();
				int index = l.getList().search(martyer);
				tableView.getItems().add(index, martyer);
			}
		});
		TableColumn<Martyer, Character> genderCol = new TableColumn<>("gender");
		genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
		genderCol.setCellFactory(TextFieldTableCell.forTableColumn(new CharacterStringConverter()));
		genderCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Martyer, Character>>() {

			@Override
			public void handle(CellEditEvent<Martyer, Character> e) {
				Martyer martyer = e.getRowValue();
				martyer.setGender(e.getNewValue());
			}
		});
		tableView.getColumns().addAll(nameCol, ageCol, dataOfDeathCol, genderCol);
		bPane.setLeft(privousBT);
		bPane.setAlignment(privousBT, Pos.CENTER);
		bPane.setRight(nextBT);
		bPane.setAlignment(nextBT, Pos.CENTER);

		deleteBT.setOnAction(e -> {
			// tableView.getSelectionModel().getSelectedItem() return object location.
			l.getList().remove(tableView.getSelectionModel().getSelectedItem());// remove it from saved data
			tableView.getItems().remove(tableView.getSelectionModel().getSelectedItem());// remove it from table view

		});

		addBT.setOnAction(e -> {

			try {
				String name = nameTF.getText();
				int age = Integer.parseInt(ageTF.getText());
				Date dateOfDeath = new SimpleDateFormat("dd/MM/yyyy").parse(dateOfDeathTF.getText());
				char gender = genderTF.getText().charAt(0);
				Martyer martyer = new Martyer(name, age, dateOfDeath, gender);
				l.getList().addLast(martyer);
				l.getList().sortList();
				// to know where to put it in the table
				int index = l.getList().search(martyer);

				tableView.getItems().add(index, martyer);
				ageTF.setText("");
				nameTF.setText("");
				dateOfDeathTF.setText("");
				genderTF.setText("");

			} catch (ParseException e1) {
				e1.printStackTrace();
			}

		});
		nextBT.setOnAction(e -> {
			Location location;
			// that mean you reached the end so return to the first one.
			if (StartClass.linked.search(l) == StartClass.linked.getCount() - 1)
				location = StartClass.linked.get(0);
			else
				location = StartClass.linked.get(StartClass.linked.search(l) + 1);

			this.hide();
			new MartyesInfo(location);
		});
		privousBT.setOnAction(e -> {
			Location location;
			// that mean you are at the biggening so return to the last one.
			if (StartClass.linked.search(l) == 0)
				location = StartClass.linked.get(StartClass.linked.getCount() - 1);
			else
				location = StartClass.linked.get(StartClass.linked.search(l) - 1);

			this.hide();
			new MartyesInfo(location);
		});

		searchBT.setOnAction(e -> {
			LinkedList<Martyer> list = l.getList();
			if (searchTF.getText().isEmpty()) {
				this.hide();
				new MartyesInfo(l);
			}

			for (int i = 0; i < list.getCount(); i++) {
				if (list.get(i).getName().contains(searchTF.getText()) == false) {
					tableView.getItems().removeAll(list.get(i));
				}
			}

		});

		for (int i = 0; i < l.getList().getCount(); i++) {

			tableView.getItems().add(l.getList().get(i));
		}
		bPane.setCenter(tableView);
		bPane.setBottom(vBox);
		Scene scene = new Scene(bPane, 650, 650);
		this.setScene(scene);
		this.show();

	}

	public double percetageToOthers(LinkedList<Martyer> list) {

		// counter method return number of all martyers..
		return (list.getCount() / StartClass.linked.counter()) * 100.0;

	}

}
