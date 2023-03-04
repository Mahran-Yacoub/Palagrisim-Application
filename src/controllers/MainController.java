package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import plagiarism.CheckPlagiarism;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController {

    @FXML
    private Button goButton;

    @FXML
    private TextField input;

    @FXML
    private Label output;

    @FXML
    private Label title;

    @FXML
    void goAction(ActionEvent event) {

        String inputText = input.getText().trim();
        if(inputText.isEmpty()){
            output.setText("Enter Text To Check");
            return;
        }

        CheckPlagiarism checkPlagiarism = new CheckPlagiarism(inputText);
        int percentageOfPlagiarism = checkPlagiarism.getPercentageOfPlagiarism();
        output.setText(percentageOfPlagiarism+" %");
    }
}

