package com.example.edytor_tekstu;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Label label = new Label("Ścieżka:");

        Button wczytaj = new Button("Wczytaj");
        TextArea edytorTop = new TextArea();
        edytorTop.setPrefSize(350, 120);
        edytorTop.setWrapText(true);

        TextArea edytorDown = new TextArea();
        edytorDown.setPrefSize(350, 120);
        edytorDown.setWrapText(true);

        Button zapisz = new Button("Zapisz");
        Button wyjdz = new Button("Wyjdź");

        wczytaj.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Wybierz plik do wczytania");
            File selectedFile = fileChooser.showOpenDialog(stage);

            if (selectedFile != null) {
                try {
                    String content = Files.readString(selectedFile.toPath());
                    edytorTop.setText(content);
                } catch (IOException ex) {
                    edytorTop.setText("Błąd podczas wczytywania pliku: " + ex.getMessage());
                }
            }
        });

        zapisz.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Zapisz plik");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Pliki tekstowe", "*.txt"));
            File fileToSave = fileChooser.showSaveDialog(stage);

            if (fileToSave != null) {
                try {
                    String textToSave = edytorDown.getText();
                    Files.writeString(fileToSave.toPath(), textToSave, StandardOpenOption.CREATE);
                } catch (IOException ex) {
                    edytorDown.setText("Błąd podczas zapisywania pliku: " + ex.getMessage());
                }
            }
        });

        wyjdz.setOnAction(e -> {
            Platform.exit();
            System.exit(0);
        });

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(4);
        gridPane.setVgap(4);

        gridPane.add(label, 0, 0);
        gridPane.add(wczytaj, 1, 0);
        gridPane.add(edytorTop, 1, 2);
        gridPane.add(edytorDown, 1, 3);
        gridPane.add(zapisz, 0, 4);
        gridPane.add(wyjdz, 1, 4);

        Scene scene = new Scene(gridPane, 500, 350);
        stage.setScene(scene);
        stage.setTitle("Edytor tekstu");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
