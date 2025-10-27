package structPhase1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class StartClass extends Application {
	Button startBT = new Button("start");
	Button loadBT = new Button("load");
	Button saveBT = new Button("save");
	static DoubleLinkedList<Location> linked = new DoubleLinkedList<>();
	HBox hBox = new HBox(10, startBT, loadBT, saveBT);

	public static void main(String[] args) {
		Application.launch(args);
	}
	

	@Override
	public void start(Stage stage) throws Exception {

		BorderPane bP = new BorderPane();
		Label mainL = new Label("\t\t\tWelcome to " + "\n" + "Martyers honor list: Comp 242 Project 1");
		mainL.setFont(new Font("Verdana", 15));
		bP.setAlignment(mainL, Pos.CENTER);
		bP.setTop(mainL);
		ImageView imageView = new ImageView("C:\\Users\\ahmad\\eclipse-workspace\\structPhase1\\data.jpg");
		imageView.setFitWidth(250);
		imageView.setFitHeight(250);

		loadBT.setOnAction(e -> {

			FileChooser fileChooser = new FileChooser();
			File load = fileChooser.showOpenDialog(null);
			load = new File(load.getAbsolutePath());
			readFromFile(load);

		});

		imageView.setRotate(35);
		hBox.setAlignment(Pos.CENTER);
		bP.setCenter(imageView);
		bP.setBottom(hBox);
		startBT.setStyle(" -fx-background-color: white;-fx-font-size: 12px;" + "    -fx-font-weight: bold;"
				+ "    -fx-text-fill: #333333;"
				+ "    -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );");
		loadBT.setStyle(" -fx-background-color: white;-fx-font-size: 12px;" + "    -fx-font-weight: bold;"
				+ "    -fx-text-fill: #333333;"
				+ "    -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );");

		saveBT.setStyle(" -fx-background-color: white;-fx-font-size: 12px;" + "    -fx-font-weight: bold;"
				+ "    -fx-text-fill: #333333;"
				+ "    -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );");
		startBT.setOnAction(e -> {
			// sort the data before showing it ...
			linked.sortList();
			new CitiesTableView();
		});
		saveBT.setOnAction(e -> {

			FileChooser fileChooser = new FileChooser();
			File save = fileChooser.showOpenDialog(null);
			save = new File(save.getAbsolutePath());
			writeInFile(save);
		});

		Scene scene = new Scene(bP, 450, 450);
		bP.setStyle("-fx-background-color: gray;");
		stage.setTitle("honor list");
		stage.setScene(scene);
		stage.show();

	}

	public void readFromFile(File file) {

		try {
			FileReader reader = new FileReader(file);
			BufferedReader br = new BufferedReader(reader);
			String line = br.readLine();
			String name;
			String location;
			int age;
			char gender;
			Date dateOfDeath;
			line = br.readLine();// to skip first row (name ,age .....
			String[] tkz = line.split(",");
			LinkedList<Martyer> list = new LinkedList<>();
			while ((line = br.readLine()) != null) {
				name = tkz[0];
				if (tkz[1].equals("")) {
					age = 0;
				} else {
					age = Integer.parseInt(tkz[1]);
				}
				location = tkz[2];
				dateOfDeath = new SimpleDateFormat("dd/MM/yyyy").parse(tkz[3]);

				gender = tkz[4].charAt(0);
				if (StartClass.linked.search(location) == -1) {
					StartClass.linked.addFisrt(new Location(location, new Martyer(name, age, dateOfDeath, gender)));
				} else if (StartClass.linked.search(location) != -1) {
					StartClass.linked.get(StartClass.linked.search(location))
							.setMartyer(new Martyer(name, age, dateOfDeath, gender));
					;
				}
				tkz = line.split(",");
			}
			br.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void writeInFile(File file) {
		try {
			LinkedList<Martyer> list;
			FileWriter fWriter = new FileWriter(file);
			BufferedWriter Bw = new BufferedWriter(fWriter);
			Bw.write("Name,");
			Bw.write("age,");
			Bw.write("location,");
			Bw.write("date of death,");
			Bw.write("gender");
			Bw.newLine();
			for (int i = 0; i < linked.getCount(); i++) {
				list = linked.get(i).getList();
				for (int j = 0; j < list.getCount(); j++) {
					Bw.write(list.get(j).getName() + ",");
					Bw.write(list.get(j).getAge() + ",");
					Bw.write(linked.get(i).getLocation() + ",");
					Bw.write(list.get(j).getDateOfDeath() + ",");
					Bw.write(list.get(j).getGender() + ",");
					Bw.newLine();

				}

			}
			Bw.close();
			fWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
