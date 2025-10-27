package structPhase1;

import javafx.scene.control.Button;

public class Location implements Comparable<Location> {

	private String location;
	private LinkedList<Martyer> list = new LinkedList<>();
	Button infoBT = new Button("info");
	Button summeryBT = new Button("summery");

	public Location(String location) {
		this.location = location;
		infoActin();
		summeryAction();
	}

	public Location(String location, Martyer martyer) {
		list.addLast(martyer);
		setLocation(location);
		infoActin();
		summeryAction();
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public LinkedList<Martyer> getList() {
		return list;
	}

	public void setList(LinkedList<Martyer> list) {
		this.list = list;
	}

	public void setMartyer(Martyer martyer) {
		list.addLast(martyer);
	}

	public Button getInfoBT() {
		return infoBT;
	}

	public void setInfoBT(Button infoBT) {
		this.infoBT = infoBT;
	}

	public Button getSummeryBT() {
		return summeryBT;
	}

	public void setSummeryBT(Button summeryBT) {
		this.summeryBT = summeryBT;
	}

	public void infoActin() {
		infoBT.setOnAction(e -> {
			getList().sortList();
			new MartyesInfo(this);

		});

	}

	public void summeryAction() {
		summeryBT.setOnAction(e -> {
			new SummeryScreen(this);
		});

	}

	@Override
	public int compareTo(Location o) {

		if (this.getLocation().compareTo(o.getLocation()) >= 1) {
			return 1;

		}

		else if (this.getLocation().compareTo(o.getLocation()) < 1) {
			return -1;

		}

		return 0;
	}
}
