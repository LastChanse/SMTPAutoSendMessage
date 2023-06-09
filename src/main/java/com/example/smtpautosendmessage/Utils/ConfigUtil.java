package com.example.smtpautosendmessage.Utils;

import javafx.scene.control.Alert;

import java.io.*;
import java.nio.charset.Charset;
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
        properties.put("mail.smtp.title.prefix", "префикс");
        properties.put("mail.smtp.title.suffix", "суффикс");
        properties.put("mail.smtp.message", "тело сообщения");
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
            Reader reader = new InputStreamReader(fis);
            properties.load(reader);
            reader.close();
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
            Writer writer       = new OutputStreamWriter(output);
            properties.store(writer, null);

            System.out.println(properties);

            writer.flush();
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
            Writer writer       = new OutputStreamWriter(output);
            Properties properties = config;
            properties.store(writer, null);

            System.out.println(properties);

            writer.flush();
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
            Reader reader = new InputStreamReader(fis);
            properties.load(reader);
            reader.close();
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
