package structPhase1;

import java.util.Date;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SummeryScreen extends Stage {
	LinkedList<Martyer> list;
	BorderPane bPane = new BorderPane();
	VBox vBox = new VBox(10);
	Button nextBT = new Button("Next");
	Button privousBT = new Button("previos");

	public SummeryScreen(Location l) {
		list = l.getList();
		Label locationL = new Label(l.getLocation());
		Label genderLabel = new Label(numberByGender());
		Label averageLabel = new Label("the average age of martyers is : " + averageAgeOfMartyers());
		Label maxLabel = new Label("the date that has the max number of martyers : " + maxNumberOfMartyer());
		averageLabel.setFont(new Font("Verdana", 15));
		maxLabel.setFont(new Font("Verdana", 15));
		genderLabel.setFont(new Font("Verdana", 15));
		locationL.setFont(new Font("Verdana", 15));
		bPane.setAlignment(locationL, Pos.CENTER);
		vBox.getChildren().addAll(genderLabel, averageLabel, maxLabel);
		bPane.setTop(locationL);

		bPane.setBottom(vBox);
		bPane.setAlignment(vBox, Pos.CENTER);
		bPane.setLeft(privousBT);
		bPane.setRight(nextBT);
		bPane.setAlignment(privousBT, Pos.CENTER);

		bPane.setAlignment(nextBT, Pos.CENTER);

		TableView<Martyer> tableView = new TableView<>();
		TableColumn<Martyer, Character> AgeCol = new TableColumn<>("age");
		AgeCol.setCellValueFactory(new PropertyValueFactory<>("Age"));

		TableColumn<Martyer, Character> AgeNumCol = new TableColumn<>("age num");
		AgeNumCol.setCellValueFactory(new PropertyValueFactory<>("numberOfAge"));

		tableView.getColumns().add(AgeCol);
		tableView.getColumns().add(AgeNumCol);
		for (int i = 0; i < list.getCount(); i++) {

			int count = 0;
			for (int j = 1; j < list.getCount(); j++) {

				if (list.get(i).getAge() == list.get(j).getAge()) {
					count++;
				}
			}
			list.get(i).setNumberOfAge(count);
			tableView.getItems().add(list.get(i));
		}

	

		nextBT.setOnAction(e -> {
			Location location;
			// that mean you reached the end so return to the first one.
			if (StartClass.linked.search(l) == StartClass.linked.getCount() - 1)
				location = StartClass.linked.get(0);
			else
				location = StartClass.linked.get(StartClass.linked.search(l) + 1);

			this.hide();
			new SummeryScreen(location);
		});
		privousBT.setOnAction(e -> {
			Location location;
			// that mean you are at the biggening so return to the last one.
			if (StartClass.linked.search(l) == 0)
				location = StartClass.linked.get(StartClass.linked.getCount() - 1);
			else
				location = StartClass.linked.get(StartClass.linked.search(l) - 1);

			this.hide();
			new SummeryScreen(location);
		});
		bPane.setCenter(tableView);
		Scene scene = new Scene(bPane, 650, 650);
		this.setScene(scene);
		this.show();

	}

	public String numberByGender() {
		int male = 0;
		int female = 0;
		for (int i = 0; i < list.getCount(); i++) {
			if (list.get(i).getGender() == 'F')
				female++;
			else if (list.get(i).getGender() == 'M')
				male++;

		}

		return "number of male is " + male + "  number of female is " + female;

	}

	public double averageAgeOfMartyers() {
		double average = 0;
		for (int i = 0; i < list.getCount(); i++) {
			average += list.get(i).getAge();
		}

		return average / list.getCount();
	}

	public Date maxNumberOfMartyer() {

		int max_count = 0;
		Date freqDate = null;
		for (int i = 0; i < list.getCount(); i++) {

			int count = 0;
			for (int j = 1; j < list.getCount(); j++) {

				if (list.get(i).getDateOfDeath().equals(list.get(j).getDateOfDeath())) {
					count++;
				}
			}

			if (count > max_count) {
				max_count = count;
				freqDate = list.get(i).getDateOfDeath();
			}
		}

		return freqDate;
	}

}
