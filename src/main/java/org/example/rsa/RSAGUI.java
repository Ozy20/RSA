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

import java.time.Instant;
import java.time.chrono.*;
public class RSAGUI extends Application {

    private final RSA.RSAKeyPair keys = RSA.generateKeys(16);

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("RSA Encryption / Decryption");

        // Input field
        Label inputLabel = new Label("Enter text to encrypt or ciphertext to decrypt:");
        TextField inputField = new TextField();

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
        keyArea.setText("the key pair is "+"(e:"+keys.e+" ,n: "+keys.n+")");
        TextArea outputArea = new TextArea();
        outputArea.setWrapText(true);
        outputArea.setEditable(false);
        outputArea.setPrefHeight(200);

        // Main layout
        VBox layout = new VBox(10, inputLabel, inputField, buttonBox, outputLabel, keyArea,outputArea);
        layout.setPadding(new Insets(20));

        // Button actions
        encryptButton.setOnAction(e -> {
            String plainText = inputField.getText();
            if (!plainText.isEmpty()) {
                String encrypted = RSA.encryptText(plainText, keys.e, keys.n);
                outputArea.setText("Encrypted:\n" + encrypted);
            }
        });

        decryptButton.setOnAction(e -> {
            String cipherText = inputField.getText();
            if (!cipherText.isEmpty()) {
                try {
                    String decrypted = RSA.decryptText(cipherText, keys.d, keys.n);
                    outputArea.setText("Decrypted:\n" + decrypted);
                } catch (Exception ex) {
                    outputArea.setText("Decryption failed.\nMake sure input is valid encrypted text.");
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
