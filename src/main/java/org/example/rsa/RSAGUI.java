package org.example.rsa;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.math.BigInteger;
import java.time.Instant;
import java.time.chrono.*;
public class RSAGUI extends Application {

    private final RSA.RSAKeyPair keys = RSA.generateKeys(128);

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("RSA Encryption / Decryption");

        // Input field
        Label instructions = new Label("Note: If keys were provided or one of them was missing the program will generate them by default");
        instructions.styleProperty().set("-fx-font-size: 12px; -fx-font-weight: bold");
        TextField inputField = new TextField();
        inputField.setPromptText("enter the test you want to encrypt or decrypt");
        TextField eORd =  new TextField();
        eORd.setPromptText("enter the e or d key (e for encryption and d for decryption)");
        TextField N =  new TextField();
        N.setPromptText("enter the n key (n for encryption and decryption must be > 255)");
        // Buttons
        Button encryptButton = new Button("Encrypt");
        Button decryptButton = new Button("Decrypt");

        HBox buttonBox = new HBox(10, encryptButton, decryptButton);
        buttonBox.setPadding(new Insets(10));

        // Output area
        Label outputLabel = new Label("Result:");
        TextArea keyArea = new TextArea();
        keyArea.setEditable(false);
        keyArea.setWrapText(true);
        keyArea.setMaxWidth(Double.MAX_VALUE);
        keyArea.setMaxHeight(50);
        TextArea outputArea = new TextArea();
        outputArea.setWrapText(true);
        outputArea.setEditable(false);
        outputArea.setPrefHeight(200);

        // Main layout
        VBox layout = new VBox(10, inputField,N,eORd, buttonBox, keyArea, outputLabel,outputArea,instructions);
        layout.setPadding(new Insets(20));

        // Button actions
        encryptButton.setOnAction(e -> {
            String plainText = inputField.getText();
            if (!plainText.isEmpty()) {
                try{
                    if (eORd.getText().isEmpty() || N.getText().isEmpty()) {
                        keyArea.setText("the key pair is "+"(e:"+keys.e+" ,n: "+keys.n+")");
                        String encrypted = RSA.encryptText(plainText, keys.e, keys.n);
                        outputArea.setText("Encrypted:\n" + encrypted);
                    } else {
                        BigInteger NValue = new BigInteger(N.getText());
                        BigInteger eORdValue = new BigInteger(eORd.getText());
                        String encrypted = RSA.encryptText(plainText, eORdValue, NValue);
                        outputArea.setText("Encrypted:\n" + encrypted);
                    }
                }catch (Exception ex){
                    outputArea.setText("Encryption failed.\nCheck that the input text and keys are valid.");

                }
            }
        });

        decryptButton.setOnAction(e -> {
            String cipherText = inputField.getText();
            if (!cipherText.isEmpty()) {
                try{
                    if (eORd.getText().isEmpty() || N.getText().isEmpty()) {
                        keyArea.setText("the key pair is "+"(d:"+keys.d+" ,n: "+keys.n+")");
                        String decrypted = RSA.decryptText(cipherText, keys.d, keys.n);
                        outputArea.setText("Decrypted:\n" + decrypted);
                    } else {
                        BigInteger NValue = new BigInteger(N.getText());
                        BigInteger eORdValue = new BigInteger(eORd.getText());
                        String decrypted = RSA.decryptText(cipherText, eORdValue, NValue);
                        outputArea.setText("Decrypted:\n" + decrypted);
                    }
                }catch (Exception ex){
                    outputArea.setText("Decryption failed.\nMake sure input is valid encrypted cipher text.");

                }
            }
        });

        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
