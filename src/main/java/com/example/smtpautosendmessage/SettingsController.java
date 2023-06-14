package com.example.smtpautosendmessage;

import com.example.smtpautosendmessage.Utils.AlertUtil;
import com.example.smtpautosendmessage.Utils.ConfigUtil;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Path;
import java.util.Properties;

import static javafx.scene.control.cell.TextFieldTableCell.*;

/**
 * Контроллер окна настроек
 */
public class SettingsController {
    /**
     * Страницы настроек
     */
    @FXML
    private AnchorPane pgSMTPSettings, pgReceiverSettings, pgPathSettings, pgHelloSettings;
    /**
     * Список страниц настроек
     */
    @FXML
    private ListView pageList;

    /** Загрузка конфигурационного файла и настройка страниц настроек, при инициализации контроллера */
    @FXML
    public void initialize() {
        Properties config = ConfigUtil.config;
        pgHelloSettings.toFront();
        // Загрузка данных из конфигурационного файла
        // Добавление страниц настроек
        pageList.getItems().add(pageList.getItems().size(), "Прикрепляемые файлы");
        pageList.getItems().add(pageList.getItems().size(), "SMTP подключение");
        pageList.getItems().add(pageList.getItems().size(), "Группы получателей");
        // Добавление смены страницы при выборе страницы в списке страниц
        pageList.setOnMouseClicked(event -> {
            String pageName;
            Object page;
            if ((page = pageList.getSelectionModel().getSelectedItem()) == null) {
                pageName = "";
            } else pageName = page.toString();
            switch (pageName) {
                case ("Прикрепляемые файлы") -> {
                    textFieldSelectCatDirSendingFiles.setText(config.getProperty("data.files.path", ""));
                    pgPathSettings.toFront();
                }
                case ("SMTP подключение") -> {
                    // Задание пароля скрытым по умолчанию
                    eyeImg.setVisible(true);
                    hiddenEyeImg.setVisible(false);
                    passwordFieldPassword.setVisible(true);
                    textFieldPassword.setVisible(false);
                    textFieldHost.setText(config.getProperty("mail.smtp.host", ""));
                    textFieldPort.setText(config.getProperty("mail.smtp.port", ""));
                    checkBoxAuth.setSelected(Boolean.parseBoolean(config.getProperty("mail.smtp.auth", "")));
                    textFieldLogin.setText(config.getProperty("mail.smtp.sender", ""));
                    passwordFieldPassword.setText(config.getProperty("mail.smtp.sender.password", ""));
                    textFieldPassword.setText(config.getProperty("mail.smtp.sender.password", ""));
                    checkBoxSSL.setSelected(Boolean.parseBoolean(config.getProperty("mail.smtp.ssl.enable", "")));
                    checkBoxTLS.setSelected(Boolean.parseBoolean(config.getProperty("mail.smtp.starttls.enable", "")));
                    checkBoxSSLCheckServer.setSelected(Boolean.parseBoolean(config.getProperty("mail.smtps.ssl.checkserveridentity", "")));
                    textFieldTrustServerList.setText(config.getProperty("mail.smtps.ssl.trust", ""));
                    pgSMTPSettings.toFront();
                }
                case ("Группы получателей") -> {
                    recipientsGroupList = ConfigUtil.getRecipients(); // Получение групп получателей

                    // Привязка столбцов к данным
                    groupNameCol.setCellValueFactory(data -> data.getValue()[0]);
                    receiversCol.setCellValueFactory(data -> data.getValue()[1]);
                    // Добавление возможности двойным кликом левой мыши редактировать данные столбцов
                    groupNameCol.setCellFactory(forTableColumn());
                    groupNameCol.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow())[0] = new SimpleStringProperty(e.getNewValue()));
                    receiversCol.setCellFactory(forTableColumn());
                    receiversCol.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow())[1] = new SimpleStringProperty(e.getNewValue()));
                    // Делаем таблицу редактируемой
                    tableRecipientsGroup.setEditable(true);
                    // Даём возможность множественного выделения
                    tableRecipientsGroup.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                    // Загружаем группы получателей в таблицу
                    tableRecipientsGroup.setItems(recipientsGroupList);
                    // Добавление контекстного меню для добавления и удаления строк (вызывается правым кликом мыши)
                    tableRecipientsGroup.setRowFactory(tableView -> {
                        final TableRow<StringProperty[]> row = new TableRow<>();
                        final ContextMenu tableMenu = new ContextMenu(); // Для таблицы, где строк нет

                        MenuItem addLastItem = new MenuItem("Добавить в конец");
                        addLastItem.setOnAction(event1 -> recipientsGroupList.add(new StringProperty[]{new SimpleStringProperty(""), new SimpleStringProperty("")}));

                        tableMenu.getItems().addAll(addLastItem);

                        final ContextMenu rowMenu = new ContextMenu(); // Для каждой строки

                        MenuItem addItem = new MenuItem("Добавить после");
                        addItem.setOnAction(event1 -> recipientsGroupList.add(row.getIndex() + 1, new StringProperty[]{new SimpleStringProperty(""), new SimpleStringProperty("")}));

                        MenuItem removeItem = new MenuItem("Удалить");
                        removeItem.setOnAction(event1 -> {
                            // Удаляет все выделенные строки
                            recipientsGroupList.removeAll(tableView.getSelectionModel().getSelectedItems());
                            if (recipientsGroupList.size() == 0) {
                                recipientsGroupList.add(new StringProperty[]{new SimpleStringProperty(""), new SimpleStringProperty("")});
                            }
                        });

                        rowMenu.getItems().addAll(addItem, removeItem);

                        // Задание контекстного меню для строк в зависимости от того пустые ли строки
                        row.contextMenuProperty().bind(
                                Bindings.when(row.emptyProperty())
                                        .then(tableMenu)
                                        .otherwise(rowMenu));
                        return row;
                    });

                    // 1. Wrap the ObservableList in a FilteredList (initially display all data).
                    FilteredList<StringProperty[]> filteredData = new FilteredList<>(recipientsGroupList, p -> true);

                    // 2. Set the filter Predicate whenever the filter changes.
                    filterField.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(stringProperties -> {
                        // If filter text is empty, display all persons.
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }

                        // Compare first name and last name of every person with filter text.
                        String lowerCaseFilter = newValue.toLowerCase();

                        if (stringProperties[0].getValue().toLowerCase().contains(lowerCaseFilter)) {
                            return true; // Filter matches first name.
                        }
                        return false; // Does not match.
                    }));

                    // 3. Wrap the FilteredList in a SortedList.
                    SortedList<StringProperty[]> sortedData = new SortedList<>(filteredData);

                    // 4. Bind the SortedList comparator to the TableView comparator.
                    sortedData.comparatorProperty().bind(tableRecipientsGroup.comparatorProperty());

                    // 5. Add sorted (and filtered) data to the table.
                    tableRecipientsGroup.setItems(sortedData);
                    pgReceiverSettings.toFront();
                }
                default -> {
                }
            }
        });

    }

    /**
     * Кнопка закрытия окна настроек
     */
    @FXML
    private Button closeButton;

    /**
     * Закрыть окно настроек при нажатии на кнопку "Отмена"
     */
    @FXML
    void closeSettings() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    /* Логика Страниц */

    /* Страница Прикрепляемые файлы */
    /**
     * Сохранить настройки страницы прикрепляемых файлов
     */
    @FXML
    void saveSettingsPaths() {
        Properties newConfig = ConfigUtil.getConfig();
        assert newConfig != null;
        newConfig.setProperty("data.files.path", textFieldSelectCatDirSendingFiles.getText());
        ConfigUtil.setConfig(newConfig);
        ConfigUtil.config = newConfig;
        AlertUtil.showAlert("Настройки успешно сохранены");
    }

    /**
     * Поле с выбранным путем к папке с прикрепляемыми файлами
     */
    @FXML
    private TextField textFieldSelectCatDirSendingFiles;

    /**
     * Выбор относительного пути к папке с вложениями к письму
     */
    @FXML
    void selectCatDirSendingFiles() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File dir = directoryChooser.showDialog(pageList.getScene().getWindow());
        directoryChooser.setTitle("Выбор каталога");
        if (dir != null) {
            // Получение относительного пути
            Path dirPath = Path.of(dir.getAbsolutePath());
            Path folder = Path.of(new File(".").getAbsolutePath());
            Path relativePath = folder.relativize(dirPath);
            textFieldSelectCatDirSendingFiles.setText(String.valueOf(relativePath));
        }
    }

    /* Страница SMTP подключение */
    /**
     * Поле адреса SMTP сервера
     */
    @FXML
    private TextField textFieldHost,
    /**
     * Поле порта SMTP сервера
     */
    textFieldPort,
    /**
     * Поле логина для отправки писем с помощью SMTP сервера
     */
    textFieldLogin,
    /**
     * Поле списка доверенных серверов
     */
    textFieldTrustServerList;

    /**
     * Видимое поле пароля для отправки писем с помощью SMTP сервера
     */
    @FXML
    private TextField textFieldPassword;

    /**
     * Скрытое поле пароля для отправки писем с помощью SMTP сервера
     */
    @FXML
    public PasswordField passwordFieldPassword;

    /** При изменении видимого поля пароля его данные копируются в поле скрытого поля пароля */
    @FXML
    public void onChangeTextFieldPassword () {
        passwordFieldPassword.setText(textFieldPassword.getText());
    }

    /** При изменении скрытого поля пароля его данные копируются в поле видимого поля пароля */
    @FXML
    public void onChangePasswordFieldPassword () {
        textFieldPassword.setText(passwordFieldPassword.getText());
    }

    /** Переключатель видимости пароля */
    @FXML
    private ToggleButton toggleBtn;

    /** Изображения открытого и скрытого глаза */
    @FXML
    private ImageView eyeImg, hiddenEyeImg;

    /** Показать\скрыть пароль */
    public void toggleButtonShowOtHide() {
        if (toggleBtn.isSelected()) {
            eyeImg.setVisible(false);
            hiddenEyeImg.setVisible(true);
            passwordFieldPassword.setVisible(false);
            textFieldPassword.setVisible(true);
        } else {
            eyeImg.setVisible(true);
            hiddenEyeImg.setVisible(false);
            passwordFieldPassword.setVisible(true);
            textFieldPassword.setVisible(false);
        }
    }

    /**
     * Флажок наличия авторизации
     */
    @FXML
    private CheckBox checkBoxAuth,
    /**
     * Флажок использования сертификата SSL
     */
    checkBoxSSL,
    /**
     * Флажок использования сертификата TLS
     */
    checkBoxTLS,
    /**
     * Флажок проверки SSL сертификата сервера
     */
    checkBoxSSLCheckServer;

    /**
     * Сохранить настройки страницы SMTP подключение
     */
    @FXML
    void saveSettingsSMTP() {
        Properties newConfig = ConfigUtil.getConfig();
        assert newConfig != null;
        newConfig.setProperty("mail.smtp.host", textFieldHost.getText());
        newConfig.setProperty("mail.smtp.port", textFieldPort.getText());
        newConfig.setProperty("mail.smtp.auth", String.valueOf(checkBoxAuth.isSelected()));
        newConfig.setProperty("mail.smtp.sender", textFieldLogin.getText());
        newConfig.setProperty("mail.smtp.sender.password", textFieldPassword.getText());
        newConfig.setProperty("mail.smtp.ssl.enable", String.valueOf(checkBoxSSL.isSelected()));
        newConfig.setProperty("mail.smtp.starttls.enable", String.valueOf(checkBoxTLS.isSelected()));
        newConfig.setProperty("mail.smtps.ssl.checkserveridentity", String.valueOf(checkBoxSSLCheckServer.isSelected()));
        newConfig.setProperty("mail.smtps.ssl.trust", textFieldTrustServerList.getText());
        ConfigUtil.setConfig(newConfig);
        ConfigUtil.config = newConfig;
        AlertUtil.showAlert("Настройки успешно сохранены");
    }

    /*
      Страница Настройки групп получателей
     */

    /** Таблица групп получателей */
    @FXML
    private TableView<StringProperty[]> tableRecipientsGroup;
    /** Столбец названия групп */
    @FXML
    private TableColumn<StringProperty[], String> groupNameCol;
    /** Столбец названия списка почтовых адресов */
    @FXML
    private TableColumn<StringProperty[], String> receiversCol;
    /** Список групп получателей */
    @FXML
    ObservableList<StringProperty[]> recipientsGroupList = FXCollections.observableArrayList();

    /**
     * Поле поиска групп пользователей
     */
    @FXML
    private TextField filterField;

    /**
     * Сохранить настройки страницы групп получателей
     */
    @FXML
    void saveSettingsRecipients() {
        ConfigUtil.setRecipients(recipientsGroupList);
        AlertUtil.showAlert("Настройки успешно сохранены");
    }
}