//Aditya Rangavajhala, Aryan Dornala
package view;

import java.util.Optional;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;
import javax.swing.Action;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class ListController {

	@FXML
	ListView<Song> titleView;

	@FXML
	private Label testlabel1;

	@FXML
	private Label SongTitleDisplay;

	@FXML
	private Label SongArtistDisplay;

	@FXML
	private Label SongAlbumDisplay;

	@FXML
	private Label SongYearDisplay;

	@FXML
	private Button addSong;

	@FXML
	private Button deleteSong;

	@FXML
	private Label YearReleasedLabel;

	@FXML
	private Label AlbumTitleLabel;

	@FXML
	private Button editSong;

	private ObservableList<String> obsList;

	@FXML
	private TextField addSongField;

	@FXML
	private TextField addArtistField;

	@FXML
	private TextField addAlbumField;

	@FXML
	private TextField addYearField;

	public Stage mainstage;

	public ObservableList<Song> songList = FXCollections.observableArrayList();

	public void writeSongtoFile(ObservableList<Song> songList) {

		try {
			// Creates a Writer using FileWriter
			Writer output = new FileWriter("src\\songs.txt");
			String data = "";
			// Writes string to the file
			for (int i = 0; i < songList.size(); i++) {
				data += songList.get(i).getTitle() + ":" + songList.get(i).getArtist() + ":" + songList.get(i).getAlbum() + ":" + songList.get(i).getYear() + "\n";
			}
			output.write(data);
			output.close();
		}

		catch (Exception e) {
			e.getStackTrace();
		}
	}

	private void missingFieldAlert(Stage mainStage) {
		Alert alert = new Alert(AlertType.INFORMATION);

		alert.initOwner(mainStage);
		alert.setTitle("Missing One or More Fields");
		alert.setHeaderText(
				"Please add a field for Song Name and/or artist name");
		alert.showAndWait();

	}

	private void invalidFieldAlert(Stage mainStage) {
		Alert alert = new Alert(AlertType.INFORMATION);

		alert.initOwner(mainStage);
		alert.setTitle("Invalid Field");
		alert.setHeaderText(
				"Please enter a valid integer for the year the song was released");
		alert.showAndWait();

	}

	private void duplicateFoundError(Stage mainStage) {
		Alert alert = new Alert(AlertType.INFORMATION);

		alert.initOwner(mainStage);
		alert.setTitle("Duplicate Found");
		alert.setHeaderText(
				"This item already exists in the library!");
		alert.showAndWait();

	}

	private void editdeletionerror(Stage mainStage) {
		Alert alert = new Alert(AlertType.INFORMATION);

		alert.initOwner(mainStage);
		alert.setTitle("Edit/Deletetion Error");
		alert.setHeaderText(
				"Cannot delete or edit from an empty library!");
		alert.showAndWait();

	}

	private boolean additionConfirmationAlert(Stage mainStage, Song s) {
		Alert alert = new Alert(AlertType.CONFIRMATION);

		alert.initOwner(mainStage);
		alert.setTitle("Adding a New Song");
		alert.setHeaderText(
				"Are you sure you want to add " + s.toString());
		Optional<ButtonType> result = alert.showAndWait();
		if (!result.isPresent() || result.get() != ButtonType.OK) {
			return false;
		} else {
			return true;
		}

	}

	private boolean deletionConfirmationAlert(Stage mainStage, Song s) {
		Alert alert = new Alert(AlertType.CONFIRMATION);

		alert.initOwner(mainStage);
		alert.setTitle("Deleting Exisiting Song");
		alert.setHeaderText(
				"Are you sure you want to delete " + s.toString());
		Optional<ButtonType> result = alert.showAndWait();
		if (!result.isPresent() || result.get() != ButtonType.OK) {
			return false;
		} else {
			return true;
		}

	}

	private boolean editConfirmationAlert(Stage mainStage, Song s) {
		Alert alert = new Alert(AlertType.CONFIRMATION);

		alert.initOwner(mainStage);
		alert.setTitle("Editing Exisiting Song");
		alert.setHeaderText(
				"Are you sure you want to edit " + s.toString());
		Optional<ButtonType> result = alert.showAndWait();
		if (!result.isPresent() || result.get() != ButtonType.OK) {
			return false;
		} else {
			return true;
		}

	}

	public void deleteSong(ActionEvent e) {
		Button b = (Button) e.getSource();
		if (b == deleteSong) {

			if(songList.size()==0){
				editdeletionerror(mainstage);
				return;
			}
			int selected_index = titleView.getSelectionModel().getSelectedIndex();

			Song selected = songList.get(selected_index);
			if (deletionConfirmationAlert(mainstage, selected) == true) {
				System.out.println("DELETING SONG: "+selected_index);
				if (selected_index == songList.size() - 1) {
					songList.remove(selected_index);
					titleView.getSelectionModel().select(selected_index - 1);
				} else {
					songList.remove(selected_index);
					titleView.getSelectionModel().select(selected_index);
				}
			}
			writeSongtoFile(songList);
		}
	}

	public void editSong(ActionEvent e) {
		Button b = (Button) e.getSource();
		if (b == editSong) {
			if(songList.size()==0){
				editdeletionerror(mainstage);
				return;
			}
			Song newSong = new Song();
			int selected = titleView.getSelectionModel().getSelectedIndex();
			String song_name = addSongField.getText().strip();
			String artist_name = addArtistField.getText().strip();
			String song_year = addYearField.getText().strip();
			String album_name = addAlbumField.getText().strip();

			if (song_name.equals("") == false) {
				newSong.setTitle(song_name);
			}
			if (artist_name.equals("") == false) {
				newSong.setArtist(artist_name);
			}
			if (album_name.equals("") == false || album_name.equals("N/A") == false) {
				newSong.setAlbum(album_name);
			}
			if (song_year.equals("") == false || song_year.equals(null) == false) {
				newSong.setYear(song_year);
			} else {
				try {
					if (Integer.parseInt(song_year) > 0) {
						newSong.setYear(song_year);
					} else {
						invalidFieldAlert(mainstage);
						return;	
					}
				} catch (Exception NumberFormatException) {
					invalidFieldAlert(mainstage);
					return;
				}
			}

			if (artist_name.equals("") || song_name.equals("")) {
				missingFieldAlert(mainstage);
				return;
			} 
			else if(binarySearch(newSong) == -1){
				System.out.println("FOUND DUPLICATE");
				duplicateFoundError(mainstage);
				return;
			}

			if (editConfirmationAlert(mainstage, songList.get(selected)) == true) {
				System.out.println("WE THE BEST MUSIC");
				songList.remove(selected);
				int insertIndex = binarySearch(newSong);
				if(insertIndex >= songList.size() || songList.size() == 0){
					songList.add(newSong);
				}
				else{
					System.out.println("WE THE BEST MUSIC 2");
					songList.add(insertIndex, newSong);
				}
				titleView.getSelectionModel().select(insertIndex);
			}
			writeSongtoFile(songList);
		}
	}

	public void addSong(ActionEvent e) {
		Button b = (Button) e.getSource();
		if (b == addSong) {
			String song_name = addSongField.getText().strip();
			String artist_name = addArtistField.getText().strip();
			String song_year = addYearField.getText().strip();
			String album_name = addAlbumField.getText().strip();

			Song newSong = new Song();
			newSong.setTitle(song_name);
			newSong.setArtist(artist_name);
			newSong.setId(song_name, artist_name);
			if (album_name.equals("")) {
				album_name = "N/A";
			}
			newSong.setAlbum(album_name);
			Boolean skip = false;
			if (song_year.equals("")) {
				song_year = "N/A";
			} else {
				try {
					if (Integer.parseInt(song_year) > 0) {
						newSong.setYear(song_year);
					} else {
						invalidFieldAlert(mainstage);
						skip = true;
					}

				} catch (Exception NumberFormatException) {
					invalidFieldAlert(mainstage);
					skip = true;
				}
			}

			int insertIndex = binarySearch(newSong);
			// all fields are not filled out
			if (artist_name.equals("") || song_name.equals("")) {
				missingFieldAlert(mainstage);
			} else if (insertIndex == -1 && songList.size() != 0) {
				duplicateFoundError(mainstage);
			} else if (skip != true) {
				if (additionConfirmationAlert(mainstage, newSong) == true) {
					if(insertIndex >= songList.size() || songList.size() == 0){
						songList.add(newSong);
					}
					else{
						songList.add(insertIndex, newSong);
					}
					titleView.getSelectionModel().select(insertIndex);
				}
				writeSongtoFile(songList);
			}

		}
	}

	public void setModalLabel() {
		Song selected = titleView.getSelectionModel().getSelectedItem();
		SongTitleDisplay.setText(selected==null? "" : selected.getTitle());
		addSongField.setText(selected==null? "" : selected.getTitle());
		SongArtistDisplay.setText(selected==null? "" :"by " + selected.getArtist());
		addArtistField.setText(selected==null? "" : selected.getArtist());
		SongAlbumDisplay.setText(selected==null? "" : selected.getAlbum());
		addAlbumField.setText(selected==null? "" : selected.getAlbum());
		SongYearDisplay.setText(selected==null? "" : selected.getYear());
		addYearField.setText(selected==null || selected.getYear()==null? "" : selected.getYear());

	}

	int binarySearch(Song x) {
		int left = 0, right = songList.size() - 1;
		String check = x.getId();

		while (left <= right) {
			int mid = left + (right - left) / 2;

			if (songList.get(mid).getId().compareToIgnoreCase(check) == 0)
				return -1;

			if (songList.get(mid).getId().compareToIgnoreCase(check) < 0)
				left = mid + 1;

			else
				right = mid - 1;
		}

		return right+1;
	}

	public void start(Stage mainStage) {
		
		File file = new File("src\\songs.txt");
		Scanner sc;
		try {
			sc = new Scanner(file);
			while (sc.hasNextLine()){
				String[] curSong = sc.nextLine().split(":");
				String curTitle = curSong[0];
				String curArtist = curSong[1];
				String curAlbum = curSong[2];
				String curYear = curSong[3];
				Song newSong = new Song();
				newSong.setTitle(curTitle);
				newSong.setArtist(curArtist);
				newSong.setAlbum(curAlbum);
				newSong.setYear(curYear);
				songList.add(newSong);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		

		titleView.setItems(songList);

		titleView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> setModalLabel());
	
		if (!titleView.getItems().isEmpty()) {
			titleView.getSelectionModel().select(0);
		}

	}

}
