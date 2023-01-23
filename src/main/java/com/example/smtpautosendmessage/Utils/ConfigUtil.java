package com.example.smtpautosendmessage.Utils;

import javafx.scene.control.Alert;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class ConfigUtil {
    private static String configFileName = "config";
    static String filePath;
    public static Properties config;

    static public void initialize() {
        filePath = "./"+configFileName+".properties";
        config = getConfig();
    }

    static public Properties getDefault() { // Конфигурация по умолчанию
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.mail.ru");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtps.ssl.checkserveridentity", "true");
        properties.put("mail.smtps.ssl.trust", "*");
        properties.put("mail.smtp.sender", "youremail@mail.ru");
        properties.put("mail.smtp.sender.password", "passwordFromYourEmail");
        properties.put("mail.smtp.recipient", "1recipientemail@mail.ru,2recipientemail@mail.ru,3recipientemail@mail.ru");
        properties.put("mail.smtp.title.prefix", "passwordFromYourEmail");
        properties.put("mail.smtp.title.suffix", "passwordFromYourEmail");
        properties.put("mail.smtp.message", "passwordFromYourEmail");
        properties.put("mail.smtp.ssl.enable", "true");
        return properties;
    }

    static public void createConfigWithDefaultProperties() {
        File file = new File(filePath);
        try {
            file.createNewFile();
        } catch (IOException e) {
            AlertUtil.showAlert("Ошибка: " + e.getMessage(), Alert.AlertType.ERROR);
            throw new RuntimeException(e);
        }
        setConfig(getDefault());
    }

    static public Properties getConfig() {
        boolean fileNotExists = Files.notExists(Path.of(filePath));
        if (fileNotExists) {
            createConfigWithDefaultProperties();
        }
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
        } catch (FileNotFoundException ex) {
            AlertUtil.showAlert("Config not found in \"" + filePath + "\"", Alert.AlertType.ERROR);
            return null;
        } catch (IOException ex) {
            AlertUtil.showAlert(ex.getMessage() + "\n\n" + ex.getStackTrace(), Alert.AlertType.ERROR);
            return null;
        }
        return properties;
    }

    static public void setConfig(Properties properties) {
        try (OutputStream output = new FileOutputStream(filePath)) {

            properties.store(output, null);

            System.out.println(properties);

            output.flush();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
