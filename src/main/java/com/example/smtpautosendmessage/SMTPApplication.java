package com.example.smtpautosendmessage;

import com.example.smtpautosendmessage.Utils.CharsetChangerUtil;
import com.example.smtpautosendmessage.Utils.ConfigUtil;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/** Класс служит для запуска графического приложения
 @author LastChanse
 @version 0.1.0
 */
public class SMTPApplication extends Application {
    /**
     * Инициализирует конфигурационный файл, запускает главное окно приложения
     * @param stage Окно приложения
     * @throws IOException Кидает исключение ввода вывода
     */
    @Override
    public void start(Stage stage) throws IOException {
        ConfigUtil.initialize(); // Генерация конфигурационного файла, если он отсутствует
        FXMLLoader fxmlLoader = new FXMLLoader();
        Scene scene = new Scene(fxmlLoader.load(SMTPApplication.class.getResource("main-view.fxml")), 800, 400);
        stage.setMinHeight(400);
        stage.setMinWidth(400);
        stage.setTitle("SMTPASM");
        stage.setScene(scene);
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("logo.png")))); // Установка логотипа
        stage.show();

        File file = new File(String.valueOf(getClass().getResource("tutorTEST.pdf")));
        HostServices hostServices = getHostServices();
        hostServices.showDocument(file.getAbsolutePath());
        String pathpdf = "tutorTEST.pdf";
        String[] params = {"cmd", "/c", pathpdf};

        try {
            Runtime.getRuntime().exec(params);
        } catch (Exception e) {
        }


    }

    /**
     * Главный метод программы
     * @param args Параметры командной строки
     */
    public static void main(String[] args) {
        launch();
    }
}