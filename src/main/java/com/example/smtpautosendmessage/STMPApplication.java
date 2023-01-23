package com.example.smtpautosendmessage;

import com.example.smtpautosendmessage.Utils.ConfigUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class STMPApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ConfigUtil.initialize(); // Генерация конфигурационного файла, если он отсутствует
        FXMLLoader fxmlLoader = new FXMLLoader();//STMPApplication.class.getResource("main-view.fxml"));
        InputStream streamInput = STMPApplication.class.getResourceAsStream("main-view.fxml");
        assert streamInput != null;
        Scene scene = new Scene(fxmlLoader.load(streamInput), 800, 400);
        stage.setMinHeight(400);
        stage.setMinWidth(400);
        stage.setTitle("SMTPASM");
        stage.setScene(scene);
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("logo.png")))); // Установка логотипа
        stage.show();
//        System.out.println(new String(streamInput.readAllBytes(), StandardCharsets.UTF_8));
        streamInput.close();

    }

    public static void main(String[] args) {
        launch();
    }
}