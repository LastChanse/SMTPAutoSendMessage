package com.example.smtpautosendmessage.Utils;

import javafx.scene.control.Alert;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Утилита для работы с файлом конфигурации
 */
public class ConfigUtil {
    /** Название файла конфигурации */
    private static String configFileName = "config";

    /** Путь к файлу конфигурации */
    static String filePath;
    /** Конфигурация */
    public static Properties config;

    /**
     * Инициализация файла конфигурации
     */
    static public void initialize() {
        filePath = "./"+configFileName+".properties";
        config = getConfig();
    }

    /**
     * Получить конфигурацию по умолчанию
     * @return конфигурация
     */
    static public Properties getDefault() {
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

    /**
     * Создание файла конфигурации по умолчанию
     */
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

    /**
     * Получение конфигурации из файла
     * @return конфигурация
     */
    static public Properties getConfig() {
        boolean fileNotExists = Files.notExists(Path.of(filePath));
        if (fileNotExists) {
            createConfigWithDefaultProperties();
        }
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
        } catch (FileNotFoundException ex) {
            AlertUtil.showAlert("Конфигурация не найдена в файле:\n\"" + filePath + "\"", Alert.AlertType.ERROR);
            return null;
        } catch (IOException ex) {
            AlertUtil.showAlert(ex.getMessage() + "\n\n" + ex.getStackTrace(), Alert.AlertType.ERROR);
            return null;
        }
        return properties;
    }

    /**
     * Сохранение конфигурации в файл
     * @param properties конфигурация
     */
    static public void setConfig(Properties properties) {
        try (OutputStream output = new FileOutputStream(filePath)) {

            properties.store(output, null);

            System.out.println(properties);

            output.flush();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    /**
     * Сохранение конфигурации в файл
     * @param file Файл в который будет сохранена конфигурация
     */
    static public void saveInFile(File file) {
        try (OutputStream output = new FileOutputStream(file.getPath())) {
            Properties properties = config;
            properties.store(output, null);
            System.out.println(properties);
            output.flush();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    /**
     * Сохранение конфигурации в файл
     * @param file Файл из которого будет загружена конфигурация
     */
    static public void loadFromFile(File file) {
        String path = file.getPath();
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(path)) {
            properties.load(fis);
        } catch (FileNotFoundException ex) {
            AlertUtil.showAlert("Конфигурация не найдена в файле:\n\"" + path + "\"", Alert.AlertType.ERROR);
            return;
        } catch (IOException ex) {
            AlertUtil.showAlert(ex.getMessage() + "\n\n" + ex.getStackTrace(), Alert.AlertType.ERROR);
            return;
        }
        ConfigUtil.config = properties;
        setConfig(properties);
    }
}
