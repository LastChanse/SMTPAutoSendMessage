package com.example.smtpautosendmessage;

import com.example.smtpautosendmessage.Utils.ConfigUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class TestUtils {

    /**
     * Тест работы сохранения и загрузки конфигурации
     */
    @Test
    public void testSaveLoadConfig() {
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
        properties.put("data.files.path", "./sendingFiles");
        properties.put("ydata.", "{{asd}:{dsa}};{{111}:{222}};");
        ConfigUtil.setConfig(properties);
        Properties config = ConfigUtil.getConfig();
        Assert.assertEquals(config, properties);
    }

    /**
     * Тест работы сохранения и загрузки групп получателей
     */
    @Test
    public void testSaveLoadRecipients() {
        ObservableList<StringProperty[]> recipients = FXCollections.observableArrayList();
        recipients.add(new StringProperty[] {new SimpleStringProperty("Группа 1"),
                new SimpleStringProperty("1@mail.ru,2@gmail.com,3@yandex.ru")});
        recipients.add(new StringProperty[] {new SimpleStringProperty("Группа 2"),
                new SimpleStringProperty("21@mail.ru,22@gmail.com,23@yandex.ru")});
        recipients.add(new StringProperty[] {new SimpleStringProperty("Группа 3"),
                new SimpleStringProperty("31@mail.ru,32@gmail.com,33@yandex.ru")});
        recipients.add(new StringProperty[] {new SimpleStringProperty("Группа 4"),
                new SimpleStringProperty("41@mail.ru,42@gmail.com,43@yandex.ru")});
        recipients.add(new StringProperty[] {new SimpleStringProperty("Группа 5"),
                new SimpleStringProperty("51@mail.ru,52@gmail.com,53@yandex.ru")});
        ConfigUtil.setRecipients(recipients);
        ObservableList<StringProperty[]> recipientsConfig = ConfigUtil.getRecipients();
        for (int i = 0; i < recipients.size(); i++) {
            String recipient0 = recipients.get(i)[0].getValue();
            String recipient1 = recipients.get(i)[1].getValue();
            String recipientsConfig0 = recipientsConfig.get(i)[0].getValue();
            String recipientsConfig1 = recipientsConfig.get(i)[1].getValue();
            if (!(
                    recipient0.equals(recipientsConfig0) && recipient1.equals(recipientsConfig1)
            )) {
                Assert.assertTrue(false);
            }
        };
        Assert.assertTrue(true);
    }

    /**
     * Тест работы Экспорта и Импорта конфигурационного файла
     */
    @Test
    public void testExportImportFileConfig() {
        Properties configOut = new Properties();
        configOut.put("mail.smtp.auth", "true");
        configOut.put("mail.smtp.starttls.enable", "true");
        configOut.put("mail.smtp.host", "smtp.mail.ru");
        configOut.put("mail.smtp.port", "465");
        configOut.put("mail.smtps.ssl.checkserveridentity", "true");
        configOut.put("mail.smtps.ssl.trust", "*");
        configOut.put("mail.smtp.sender", "youremail@mail.ru");
        configOut.put("mail.smtp.sender.password", "passwordFromYourEmail");
        configOut.put("mail.smtp.recipient", "1recipientemail@mail.ru,2recipientemail@mail.ru,3recipientemail@mail.ru");
        configOut.put("mail.smtp.title.prefix", "префикс");
        configOut.put("mail.smtp.title.suffix", "суффикс");
        configOut.put("mail.smtp.message", "тело сообщения");
        configOut.put("mail.smtp.ssl.enable", "true");
        configOut.put("data.files.path", "./sendingFiles");
        configOut.put("ydata.", "{{asd}:{dsa}};{{111}:{222}};");
        File configFileOut = new File("testConf.properties");
        try {
            configFileOut.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ConfigUtil.config = configOut;
        ConfigUtil.saveInFile(configFileOut);
        ConfigUtil.loadFromFile(new File("testConf.properties"));
        Properties configIn = ConfigUtil.getConfig();
        Assert.assertEquals(configOut,configIn);
    }

    /**
     * Тест создания конфигурационного файла по умолчанию
     */
    @Test
    public void testCreateDefaultConfigFile() {
        Properties configDefault = ConfigUtil.getDefault();
        ConfigUtil.createConfigWithDefaultProperties();
        Properties configFromDefaultFile = ConfigUtil.getConfig();
        Assert.assertEquals(configDefault,configFromDefaultFile);
    }
}
