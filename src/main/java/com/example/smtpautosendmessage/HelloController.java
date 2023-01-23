package com.example.smtpautosendmessage;

import com.example.smtpautosendmessage.Utils.ConfigUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onSendButtonClick() {
        if ((ConfigUtil.getConfig() == null) || (ConfigUtil.getConfig().isEmpty())) {
            sendMessage("31240000a@gmail.com", "Список файлов: ", ". МДК04.01");
        } else {
            sendMessageFromConfig(); // FIXME: Баг с кодировкой
// FIXME: Почему при смене кодировки конфига на доступную для русского языка, ломается кодировка писем для русского языка?
        }
    }

    String generateTitle() {
        // директория c отправляемыми файлами
        File dir = new File("src/main/resources/sendingFiles");
        File[] arrFiles = dir.listFiles();
        List<File> lst = Arrays.asList(arrFiles);
        StringBuffer stringBuffer = new StringBuffer();
        for (File f :
                lst) {
            stringBuffer.append(f.getName().split("\\.")[0] + ", ");
        }

        return stringBuffer.substring(0, stringBuffer.length() - 2);
    }

    void sendMessage(String to, String prefix, String suffix) {
        // Необходимо указать адрес электронной почты отправителя и пароль для приложения
        final String from = "секрет";
        final String password = "секретная информация";
        // Предполагая, что вы отправляете электронное письмо с smtp.mail.ru
        String host = "smtp.mail.ru";
        // Необходимо указать адрес электронной почты получателя



        // Создать новые свойства
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        // Настроить почтовый сервер
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "465");
        props.put("mail.smtps.ssl.checkserveridentity", true);
        props.put("mail.smtps.ssl.trust", "*");
        props.put("mail.smtp.ssl.enable", "true");

        // Получение объекта Session по умолчанию
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                // Установить От кого посылается письмо
                                from,
                                // Установить Пароль от кого посылается письмо
                                password);
                    }
                });

        try {
            // Создание объекта MimeMessage по умолчанию
            Message message = new MimeMessage(session);

            // Установить Кому посылается письмо
            message.setFrom(new InternetAddress(from));
            message.setRecipients(
                    Message.RecipientType.TO,
                    // Установить список тех кому посылается письмо через запятую
                    InternetAddress.parse(to));

            // Установить тему письма
            message.setSubject(prefix + generateTitle() + suffix);

            // Создание части сообщения
            BodyPart messageBodyPart = new MimeBodyPart();

            // Заполнение сообщения
            messageBodyPart.setText("Сообщение было отправлено библиотекой JavaMailApi");

            // Создание составного сообщения
            Multipart multipart = new MimeMultipart();

            // Установить часть текстового сообщения
            multipart.addBodyPart(messageBodyPart);

            // Часть вторая вложения
            File dir = new File("src/main/resources/sendingFiles"); // Целевая директория с файлами
            File[] arrFiles = dir.listFiles();
            List<File> lst = Arrays.asList(arrFiles); // Список файлов в директории
            for (File f :
                    lst) { // Добавление во вложения каждого файла
                messageBodyPart = new MimeBodyPart(); // Часть тела сообщения
                DataSource source = new FileDataSource(f.getAbsolutePath()); // Ресурсы (файл)
                messageBodyPart.setDataHandler(new DataHandler(source)); // Добавление ресурсов к части тела сообщения
                messageBodyPart.setFileName(f.getName()); // Добавление имени файлу
                multipart.addBodyPart(messageBodyPart); // Добавление части тела сообщения к части текстового сообщения
            }

            // Отправить полные части сообщения
            message.setContent(multipart);

            // Отправить сообщение
            Transport.send(message);

            System.out.println("Success");
            welcomeText.setText("Сообщение успешно отправлено....");
        } catch (MessagingException e) {
            welcomeText.setText("Сообщение не отправлено");
            throw new RuntimeException(e);
        }
    }

    void sendMessageFromConfig() {
        Properties config = ConfigUtil.getConfig();
        // Необходимо указать адрес электронной почты отправителя и пароль для приложения
        final String from = config.getProperty("mail.smtp.sender");
        final String password = config.getProperty("mail.smtp.sender.password");
        // Предполагая, что вы отправляете электронное письмо с smtp.mail.ru
        String host = config.getProperty("mail.smtp.host");
        // Необходимо указать адрес электронной почты получателя



        // Создать новые свойства
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        // Настроить почтовый сервер
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "465");
        props.put("mail.smtps.ssl.checkserveridentity", true);
        props.put("mail.smtps.ssl.trust", "*");
        props.put("mail.smtp.ssl.enable", "true");

        // Получение объекта Session по умолчанию
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                // Установить От кого посылается письмо
                                from,
                                // Установить Пароль от кого посылается письмо
                                password);
                    }
                });

        try {
            // Создание объекта MimeMessage по умолчанию
            Message message = new MimeMessage(session);

            // Установить Кому посылается письмо
            message.setFrom(new InternetAddress(from));
            message.setRecipients(
                    Message.RecipientType.TO,
                    // Установить список тех кому посылается письмо через запятую
                    InternetAddress.parse(config.getProperty("mail.smtp.recipient")));

            // Установить тему письма
            message.setSubject(config.getProperty("mail.smtp.title.prefix") + generateTitle() + config.getProperty("mail.smtp.title.suffix"));

            // Создание части сообщения
            BodyPart messageBodyPart = new MimeBodyPart();

            // Заполнение сообщения
            messageBodyPart.setText(config.getProperty("mail.smtp.message"));

            // Создание составного сообщения
            Multipart multipart = new MimeMultipart();

            // Установить часть текстового сообщения
            multipart.addBodyPart(messageBodyPart);

            // Часть вторая вложения
            File dir = new File("src/main/resources/sendingFiles"); // Целевая директория с файлами
            File[] arrFiles = dir.listFiles();
            List<File> lst = Arrays.asList(arrFiles); // Список файлов в директории
            for (File f :
                    lst) { // Добавление во вложения каждого файла
                messageBodyPart = new MimeBodyPart(); // Часть тела сообщения
                DataSource source = new FileDataSource(f.getAbsolutePath()); // Ресурсы (файл)
                messageBodyPart.setDataHandler(new DataHandler(source)); // Добавление ресурсов к части тела сообщения
                messageBodyPart.setFileName(f.getName()); // Добавление имени файлу
                multipart.addBodyPart(messageBodyPart); // Добавление части тела сообщения к части текстового сообщения
            }

            // Отправить полные части сообщения
            message.setContent(multipart);

            // Отправить сообщение
            Transport.send(message);

            System.out.println("Success");
            welcomeText.setText("Сообщение успешно отправлено....");
        } catch (MessagingException e) {
            welcomeText.setText("Сообщение не отправлено");
            throw new RuntimeException(e);
        }
    }
}