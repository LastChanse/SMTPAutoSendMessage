package com.example.smtpautosendmessage;

import com.example.smtpautosendmessage.Utils.AlertUtil;
import com.example.smtpautosendmessage.Utils.CharsetChangerUtil;
import com.example.smtpautosendmessage.Utils.ConfigUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class MainController {
    // DONE: Сделать так, чтобы из конфига подтягивалось в графику параметры письма
    // TODO: Сделать окно ввода параметров SMTP-сервера и чтобы из конфига подтягивалось в графику параметры SMTP-сервера
    // DONE: Сделать отправку писем от графических параметров, а не напрямую из конфига
    // TODO: Сделать группы получателей
    // TODO: Сделать сообщение об успехе сохранения данных в конфигурационный файл
    /** Основные элементы **/
    @FXML // Поля формы отправки письма
    public TextField recipientField,prefixField,suffixField,titleField;
    @FXML // Поле описания к письму
    public TextArea description;
    @FXML
    public void initialize() {
        loadPropertiesFromConfig();
    }

    /** Логика формы заполнения письма **/

    /**
     * loadPropertiesFromConfig -- функция загрузки данных для отправки письма из конфига на форму отправки письма
     */
    private void loadPropertiesFromConfig() {
        if ((ConfigUtil.config) == null) {
            ConfigUtil.config = ConfigUtil.getConfig();
        }
        Properties config = ConfigUtil.config;
        recipientField.setText(config.getProperty("mail.smtp.recipient"));
        prefixField.setText(config.getProperty("mail.smtp.title.prefix"));
        suffixField.setText(config.getProperty("mail.smtp.title.suffix"));
        titleField.setText(config.getProperty("mail.smtp.title.prefix") + generateTitle() + config.getProperty("mail.smtp.title.suffix"));
        description.setText(config.getProperty("mail.smtp.message"));
    }

    @FXML
    public void prefixFieldChange(KeyEvent keyEvent) {
        titleField.setText(prefixField.getText() + generateTitle() + suffixField.getText());
    }

    @FXML
    public void suffixFieldChange(KeyEvent keyEvent) {
        titleField.setText(prefixField.getText() + generateTitle() + suffixField.getText());
    }

    /** Логика отправки писем **/ // TODO: Отправить логику в MessageUtils
    @FXML // Функция работающая при нажатии на кнопку "Отправить письмо"
    public void onSendButtonClick() {
        sendMessage(); // Отправка письма используя данные формы отправки письма
    }

    /**
     * sendMessage -- функция отправляет письмо используя данный формы отправки письма
     */
    private void sendMessage() {
        if (recipientField.getText().equals("")) {
            AlertUtil.showAlert("Поле получателей не может быть пустым", Alert.AlertType.ERROR);
            return;
        }
        if ((ConfigUtil.config) == null) {
            ConfigUtil.config = ConfigUtil.getConfig(); // Получение конфига и, при необходимости, генерация конфига
        }
        Properties config = ConfigUtil.config; // Загрузка конфига в переменную

        // Упаковка настроек подключения к SMTP-серверу в одну переменную настроек
        Properties props = new Properties();
        props.put("mail.smtp.auth", config.get("mail.smtp.auth")); // Наличие авторизации (true или false)
        props.put("mail.smtp.starttls.enable", config.get("mail.smtp.starttls.enable")); // Использовать tls (true или false)
        props.put("mail.smtp.host", config.get("mail.smtp.host")); // Адрес сервера (smtp.mail.ru , 192.168.0.5 или подобное)
        props.put("mail.smtp.port", config.get("mail.smtp.port")); // Порт сервера (от 0 до 65535)
        props.put("mail.smtps.ssl.checkserveridentity", true); // Проверка сертификата сервера при шифровании (true или false)
        props.put("mail.smtps.ssl.trust", config.get("mail.smtps.ssl.trust")); // Сертификат доверия для клиента (* - любой)
        props.put("mail.smtp.ssl.enable", config.get("mail.smtp.ssl.enable")); // Использовать ssl (true или false)

        // Создание новой сессии
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                // Передать адрес отправителя
                                (String) config.get("mail.smtp.sender"),
                                // Передать пароль для, отправляющего письмо, приложения (которое регистрирует сам отправитель)
                                (String) config.get("mail.smtp.sender.password"));
                    }
                });

        try {
            // Создание письма в созданной сессии
            Message message = new MimeMessage(session);

            // Установить От кого посылается письмо
            message.setFrom(new InternetAddress((String) config.get("mail.smtp.sender")));
            message.setRecipients(
                    Message.RecipientType.TO,
                    // Установить список тех кому посылается письмо через запятую (a@b.ru,c@d.com или a@b.ru если 1 получатель)
                    InternetAddress.parse(recipientField.getText()));
            // Установить тему письма
            message.setSubject(titleField.getText());

            // Создание тела сообщения
            BodyPart messageBodyPart = new MimeBodyPart();

            // Заполнение тела сообщения
            messageBodyPart.setText(description.getText());

            // Создание составного сообщения
            Multipart multipart = new MimeMultipart();

            // Добавление в состав составного сообщения тело сообщения
            multipart.addBodyPart(messageBodyPart);

            // Создание вложений
            File dir = new File("./sendingFiles"); // Путь к папке с прикрепляемыми файлами
            File[] arrFiles = dir.listFiles(); // Получение списка файлов
            List<File> lst = Arrays.asList(arrFiles); // Список файлов в директории
            for (File f :
                    lst) { // Добавление во вложения каждого файла
                messageBodyPart = new MimeBodyPart(); // Тело вложения
                DataSource source = new FileDataSource(f.getAbsolutePath()); // Ресурсы (файл)
                messageBodyPart.setDataHandler(new DataHandler(source)); // Добавление ресурсов во вложение
                messageBodyPart.setFileName(f.getName()); // Добавление имени файла во вложении
                multipart.addBodyPart(messageBodyPart); // Добавление вложение в состав составного сообщения
            }

            // Отправить собранное составное сообщение
            message.setContent(multipart);

            // Отправить сообщение
            Transport.send(message);

            AlertUtil.showAlert("Сообщение успешно отправлено", Alert.AlertType.INFORMATION);
        } catch (MessagingException e) {
            AlertUtil.showAlert("Сообщение не отправлено", Alert.AlertType.ERROR);
            throw new RuntimeException(e);
        }
    }

    /**
     * generateTitle -- генерирует заголовок письма из списка файлов во вложениях
     * @return -- заголовок письма
     */
    private String generateTitle() { // FIXME: Переместить в FileUtils
        String pathToSendingFiles = "./sendingFiles";
        File dir = new File(pathToSendingFiles);
        if(!dir.exists() || !dir.isDirectory()) { // Если файл не существует или это не директория
            AlertUtil.showAlert("Путь к папке с отправляемыми файлами не верен:\n"+pathToSendingFiles, Alert.AlertType.ERROR);
            return " "; // Прекратить выполнение функции
        }
        File[] arrFiles = dir.listFiles();
        List<File> lst = Arrays.asList(arrFiles);
        StringBuffer stringBuffer = new StringBuffer();
        for (File f :
                lst) {
            stringBuffer.append(f.getName().split("\\.")[0] + ", ");
        }

        return stringBuffer.substring(0, stringBuffer.length() - 2);
    }
    /** Логика меню **/
    /** Конфигурация **/
    @FXML
    public void onSavePropertiesClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
        fileChooser.setTitle("Экспорт конфигурации");//Заголовок диалога
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Configuration files (*.properties)", "*.properties");//Расширение
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(description.getScene().getWindow()); //Указываем текущую сцену
        if (file != null) {
            ConfigUtil.saveInFile(file); // Сохранение в конфигурационный файл
        }
    }

    @FXML
    public void onLoadPropertiesClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
        fileChooser.setTitle("Импорт конфигурации");//Заголовок диалога
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Configuration files (*.properties)", "*.properties");//Расширение
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(description.getScene().getWindow());//Указываем текущую сцену
        if (file != null) {
            ConfigUtil.loadFromFile(file); // Загрузка из конфигурационного файла FIXME: Ошибка кодировки, при импорте данных на русском языке возникает ошибка кодировки
            loadPropertiesFromConfig();
        }
    }

    /** Форма **/
    @FXML
    public void onCleanFormClick(ActionEvent actionEvent) {
        cleanForm();
    }

    /**
     * Чистит форму
     */
    private void cleanForm() {
        recipientField.setText("");
        prefixField.setText("");
        suffixField.setText("");
        titleField.setText("");
        description.setText("");
    }

    @FXML
    public void onLoadFromPropertiesClick(ActionEvent actionEvent) {
        loadPropertiesFromConfig();
    }

    @FXML
    public void onLoadIntoPropertiesClick(ActionEvent actionEvent) {
        Properties config = ConfigUtil.config;
        config.setProperty("mail.smtp.recipient", recipientField.getText());
        config.setProperty("mail.smtp.title.prefix", prefixField.getText());
        config.setProperty("mail.smtp.title.suffix", suffixField.getText());
        config.setProperty("mail.smtp.message", description.getText());
        ConfigUtil.setConfig(config);
    }

    /** Настройки **/
    @FXML
    public void onSettingsClick(ActionEvent actionEvent) {
    }

    /** Руководство **/
    @FXML
    public void onTutorialClick(ActionEvent actionEvent) {
    }
}