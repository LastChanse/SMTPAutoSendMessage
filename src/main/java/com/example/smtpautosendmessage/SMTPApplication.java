package com.example.smtpautosendmessage;

import com.example.smtpautosendmessage.Utils.CharsetChangerUtil;
import com.example.smtpautosendmessage.Utils.ConfigUtil;
import javafx.application.Application;
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
//        ArrayList recipientsGroupList = new ArrayList();
//        recipientsGroupList.add(new String[] {"4ИСиП19-2","test1@gmail.com,test2@mail.ru"});
//        recipientsGroupList.add(new String[] {"3ИСиП20-2","9test9@gmail.com,777test@mail.ru"});
//        recipientsGroupList.add(new String[] {"2ИСиП21-1","10test@gmail.com,test@mail.ru"});
//        String str = ConfigUtil.convertRecipientsGroupListToString(recipientsGroupList);
//        System.out.println(str);
//        ArrayList arr = ConfigUtil.convertRecipientsGroupStringToList(str);
//        System.out.println(arr.equals(recipientsGroupList));
//        System.out.println(((String[]) arr.get(2))[0]);
//        System.out.println(((String[]) arr.get(2))[1]);
    }

    /**
     * Главный метод программы
     * @param args Параметры командной строки
     */
    public static void main(String[] args) {
        launch();
    }
}