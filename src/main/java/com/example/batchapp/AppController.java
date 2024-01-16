package com.example.batchapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class AppController {
    //private Label welcomeText;
    @FXML
    private Label inputPromptText;

    @FXML
    private Label logoFile;

    @FXML
    private Label logoSize_valueLabel;
    @FXML
    private Slider logoSize_valueSlider;

    int logoSizeValue;

    int initialLogoSizeSliderValue = 3; // Set your desired initial value here
/*
    @FXML
    private Label H_valueLabel;

    @FXML
    private Slider H_valueSlider;
*/
    int logoHorValue, logoVerValue;
/*
    @FXML
    private Label V_valueLabel;

    @FXML
    private Slider V_valueSlider;
*/
    @FXML
    private Label laceFile;

    @FXML
    private Label outFolder;

    @FXML
    private Label runBatch;

    @FXML
    private Label progressLabel;

    @FXML
    private ProgressBar progressBar;

    String inputPath, outputPath, logoPath, lacePath;

    int userOpacityInput = 30;

    @FXML
    private TextField laceOinput;

    @FXML
    private Label resultLabel;




    @FXML
    protected void onInputButtonClick() {
        // Create a DirectoryChooser
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select a Folder");

        // Get the stage to show the dialog
        Stage stage = (Stage) inputPromptText.getScene().getWindow();

        // Show the folder chooser dialog
        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory != null) {
            // Get the path of the selected folder
            inputPath = selectedDirectory.getAbsolutePath();

            inputPromptText.setText("Selected Folder: " + inputPath);
        } else {
            inputPromptText.setText("No input folder selected.");
            showAlert("missing path for input", "Please select a folder with jpg/jpeg files");
        }
    }

    @FXML
    protected void onLogoInputButtonClick() {
        // Create a FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a PNG file");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG Files", "*.png")
        );

        // Get the stage to show the dialog
        Stage stage = (Stage) logoFile.getScene().getWindow();

        // Show the file chooser dialog
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            // Get the path of the selected PNG file
            logoPath = selectedFile.getAbsolutePath();

            logoFile.setText("Selected File: " + logoPath);
        } else {
            logoFile.setText("No file selected.");
            showAlert("missing path for logo", "Please select the logo");
        }
    }

    @FXML
    public void handleButtonClick(ActionEvent event) {
        if (event.getSource() instanceof Button clickedButton) {

            // Example conditional logic based on which button is clicked
            if (clickedButton.getId().equals("button1")) {
                // Assign values for the first set of variables if Button 1 is clicked
                logoHorValue = 5;
                logoVerValue = 5;

                resultLabel.setText("Selected Position: " + logoHorValue + ", " + logoVerValue);
            } else if (clickedButton.getId().equals("button2")) {
                // Assign values for the second set of variables if Button 2 is clicked
                logoHorValue = 80;
                logoVerValue = 5;

                resultLabel.setText("Selected Position: " + logoHorValue + ", " + logoVerValue);
            } else if (clickedButton.getId().equals("button3")) {

                // Assign values for the second set of variables if Button 3 is clicked
                logoHorValue = 5;
                logoVerValue = 90;

                resultLabel.setText("Selected Position: " + logoHorValue + ", " + logoVerValue);

            } else if (clickedButton.getId().equals("button4")) {

                // Assign values for the second set of variables if Button 2 is clicked
                logoHorValue = 80;
                logoVerValue = 90;

                resultLabel.setText("Selected Position: " + logoHorValue + ", " + logoVerValue);
            }

            //resultLabel.setText("You clicked: " + clickedButton.getText());
        }
    }


    @FXML
    public void initialize() {

        logoSize_valueSlider.setValue(initialLogoSizeSliderValue); // Set initial value
        logoSizeValue = initialLogoSizeSliderValue;
        logoSize_valueLabel.setText("Logo Size: " + initialLogoSizeSliderValue); // Update label with initial value


        logoSize_valueSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            logoSizeValue = newValue.intValue();
            logoSize_valueLabel.setText("Logo Size: " + logoSizeValue);
        });
    }

/*
    @FXML
    public void initialize() {
        H_valueSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            logoHorValue = newValue.intValue();
            H_valueLabel.setText("Selected Value: " + logoHorValue);
        });


        V_valueSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                logoVerValue = newValue.intValue();
                V_valueLabel.setText("Selected Value: " + logoVerValue);
        });
    }
*/
    @FXML
    protected void onLaceInputButtonClick() {

        // Create a FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a PNG file");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG Files", "*.png")
        );

        // Get the stage to show the dialog
        Stage stage = (Stage) laceFile.getScene().getWindow();

        // Show the file chooser dialog
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            // Get the path of the selected PNG file
            lacePath = selectedFile.getAbsolutePath();

            laceFile.setText("Selected File: " + lacePath);
        } else {
            laceFile.setText("No lace file selected.");
            showAlert("missing path for lace", "Please select lace image");
        }
    }

    @FXML
    protected void onBatchOutputButtonClick() {
        // Create a DirectoryChooser
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select a Folder");

        // Get the stage to show the dialog
        Stage stage = (Stage) outFolder.getScene().getWindow();

        // Show the folder chooser dialog
        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory != null) {
            // Get the path of the selected folder
            outputPath = selectedDirectory.getAbsolutePath();

            outFolder.setText("Selected Folder: " + outputPath);
        } else {
            outFolder.setText("No Output folder selected.");
            showAlert("missing path for output", "Please select an output folder");
        }
    }




    @FXML
    protected void onBatchButtonClick() throws IOException {

        progressBar.setProgress(0.0);
        // Make progress bar and label visible
        progressBar.setVisible(true);
        progressLabel.setVisible(true);

        progressBar.setPrefWidth(300); // Set preferred width
        progressBar.setPrefHeight(30);

/*
        if((inputPath!=null) && (logoPath!=null) && (lacePath!=null) && (outputPath!=null) && (logoHorValue!=0) && (logoVerValue!=0) ) {

            runBatch.setText("processing!");

            try {
                userOpacityInput = Integer.parseInt(input.getText());
                if (userOpacityInput >= 1 && userOpacityInput <= 100) {
                    showAlert("Valid Input", "lace opacity will be: " + userOpacityInput);


                    ProcessingMethods.processImages(inputPath, logoPath, lacePath, outputPath, userOpacityInput, logoHorValue, logoVerValue, logoSizeValue);
                    // Use the updated method and listen to progress
                    //int totalFiles = ProcessingMethods.processImages("F:\\PICX\\BatchTest\\input", "F:\\PICX\\BatchTest\\logo\\logoCtrans.png", "F:\\PICX\\BatchTest\\output");

                    // Use the updated method and listen to progress
                    //ProcessingMethods.processImages("F:\\PICX\\BatchTest\\input", "F:\\PICX\\BatchTest\\logo\\logoCtrans.png", "F:\\PICX\\BatchTest\\Lace\\lace.png", "F:\\PICX\\BatchTest\\output", 30, logoHorValue, logoVerValue);

                    progressBar.setProgress(100);
                    //runBatch.setText("Done! Processed " + totalFiles + " images.");
                    runBatch.setText("Done!");

                } else {
                    showAlert("Invalid Opacity", "Please enter a number between 1 and 100");
                }
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Please enter a valid integer");
            }
        }else {
            showAlert("Invalid Input", "One or more input details might be missing.");
        }


*/

            //this part for quick testing without needing to select everything everytime.
//star placement

            runBatch.setText("processing!");

            try {
                userOpacityInput = Integer.parseInt(laceOinput.getText());
                if (userOpacityInput >= 1 && userOpacityInput <= 100) {
                    showAlert("Valid Input", "lace opacity will be: " + userOpacityInput);


                    //ProcessingMethods.processImages(inputPath, logoPath, lacePath, outputPath, userOpacityInput, logoHorValue, logoVerValue);
                    // Use the updated method and listen to progress
                    //int totalFiles = ProcessingMethods.processImages("F:\\PICX\\BatchTest\\input", "F:\\PICX\\BatchTest\\logo\\logoCtrans.png", "F:\\PICX\\BatchTest\\output");

                    // Use the updated method and listen to progress
                    ProcessingMethods.processImages("F:\\PICX\\BatchTest\\input", "F:\\PICX\\BatchTest\\logo\\logoCtrans.png", "F:\\PICX\\BatchTest\\Lace\\lace.png", "F:\\PICX\\BatchTest\\output", 30, logoHorValue, logoVerValue, logoSizeValue);

                    progressBar.setProgress(100);
                    //runBatch.setText("Done! Processed " + totalFiles + " images.");
                    runBatch.setText("Done!");

                } else {
                    showAlert("Invalid Opacity", "Please enter a number between 1 and 100");
                }
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Please enter a valid integer");
            }

//star placement


    }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



}