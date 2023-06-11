package com.example.smtpautosendmessage;

import com.example.smtpautosendmessage.Utils.AlertUtil;
import com.example.smtpautosendmessage.Utils.ConfigUtil;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

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
    private AnchorPane pgSMTPSettings, pgRecieverSettings, pgPathSettings, pgHelloSettings;
    /**
     * Список страниц настроек
     */
    @FXML
    private ListView pageList;

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
        pageList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String pageName;
                if ((pageName = pageList.getSelectionModel().getSelectedItem().toString()) == null) {
                    pageName = "";
                }
                switch (pageName) {
                    case ("Прикрепляемые файлы"):
                        textFieldSelectCatDirSendingFiles.setText(config.getProperty("data.files.path", ""));
                        pgPathSettings.toFront();
                        break;
                    case ("SMTP подключение"):
                        textFieldHost.setText(config.getProperty("mail.smtp.host", ""));
                        textFieldPort.setText(config.getProperty("mail.smtp.port", ""));
                        checkBoxAuth.setSelected(Boolean.valueOf(config.getProperty("mail.smtp.auth", "")));
                        textFieldLogin.setText(config.getProperty("mail.smtp.sender", ""));
                        textFieldPassword.setText(config.getProperty("mail.smtp.sender.password", ""));
                        checkBoxSSL.setSelected(Boolean.valueOf(config.getProperty("mail.smtp.ssl.enable", "")));
                        checkBoxTLS.setSelected(Boolean.valueOf(config.getProperty("mail.smtp.starttls.enable", "")));
                        checkBoxSSLCheckServer.setSelected(Boolean.valueOf(config.getProperty("mail.smtps.ssl.checkserveridentity", "")));
                        textFieldTrustServerList.setText(config.getProperty("mail.smtps.ssl.trust", ""));
                        pgSMTPSettings.toFront();
                        break;
                    case ("Группы получателей"):
                        recipientsGroupList = ConfigUtil.getRecipients(); // Получение групп получателей
                        // Привязка столбцов к данным
                        groupNameCol.setCellValueFactory(data -> data.getValue()[0]);
                        recieversCol.setCellValueFactory(data -> data.getValue()[1]);
                        // Добавление возможности двойным кликом левой мыши редактировать данные столбцов
                        groupNameCol.setCellFactory(forTableColumn());
                        groupNameCol.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow())[0] = new SimpleStringProperty(e.getNewValue()));
                        recieversCol.setCellFactory(forTableColumn());
                        recieversCol.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow())[1] = new SimpleStringProperty(e.getNewValue()));
                        // Делаем таблицу редактируемой
                        tableRecipientsGroup.setEditable(true);
                        // Загружаем группы получателей в таблицу
                        tableRecipientsGroup.setItems(recipientsGroupList);
                        // Добавление контекстного меню для добавления и удаления строк
                        tableRecipientsGroup.setRowFactory(new Callback<TableView<StringProperty[]>, TableRow<StringProperty[]>>() {
                            @Override
                            public TableRow<StringProperty[]> call(TableView<StringProperty[]> tableView) {
                                final TableRow<StringProperty[]> row = new TableRow<>();
                                final ContextMenu rowMenu = new ContextMenu();
                                final ContextMenu tableMenu = new ContextMenu();

                                MenuItem addLastItem = new MenuItem("Добавить в конец");
                                addLastItem.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                        tableRecipientsGroup.getItems().add(new StringProperty[]{new SimpleStringProperty(""), new SimpleStringProperty("")});
                                    }
                                });

                                MenuItem addItem = new MenuItem("Добавить после");
                                addItem.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                        tableRecipientsGroup.getItems().add(new StringProperty[]{new SimpleStringProperty(""), new SimpleStringProperty("")});
                                    }
                                });
                                MenuItem removeItem = new MenuItem("Удалить");
                                removeItem.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                        tableRecipientsGroup.getItems().remove(row.getItem());
                                    }
                                });
                                tableMenu.getItems().addAll(addLastItem);
                                rowMenu.getItems().addAll(addItem, removeItem);

                                // only display context menu for non-empty rows:
                                row.contextMenuProperty().bind(
                                        Bindings.when(row.emptyProperty())
                                                .then(tableMenu)
                                                .otherwise(rowMenu));
                                return row;
                            }
                        });
                        pgRecieverSettings.toFront();
                        break;
                    default:
                        pgPathSettings.toFront();
                        break;
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
    void closeSettings(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    /** Логика Страниц */

    /** Страница Прикрепляемые файлы */
    /**
     * Сохранить настройки страницы прикрепляемых файлов
     */
    @FXML
    void saveSettingsPaths(MouseEvent event) {
        Properties newConfig = ConfigUtil.getConfig();
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
    void selectCatDirSendingFiles(ActionEvent event) {
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

    /** Страница SMTP подключение */
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
     * Поле пароля для отправки писем с помощью SMTP сервера
     */
    textFieldPassword,
    /**
     * Поле списка доверенных серверов
     */
    textFieldTrustServerList;

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
    void saveSettingsSMTP(MouseEvent event) {
        Properties newConfig = ConfigUtil.getConfig();
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

    /**
     * Страница Настройки групп получателей
     */

    @FXML
    private TableView<StringProperty[]> tableRecipientsGroup;
    @FXML
    private TableColumn<StringProperty[], String> groupNameCol;
    @FXML
    private TableColumn<StringProperty[], String> recieversCol;
    @FXML
    ObservableList<StringProperty[]> recipientsGroupList = FXCollections.observableArrayList();

    /**
     * Добавление группы получателей
     */
    @FXML
    public void addRecipientsGroup(MouseEvent event) {
    }

    /**
     * Редактирование группы получателей
     */
    @FXML
    public void editRecipientsGroup(MouseEvent event) {

    }

    /**
     * Удаление группы получателей
     */
    @FXML
    public void removeRecipientsGroup(MouseEvent event) {

    }

    /**
     * Сохранить настройки страницы групп получателей
     */

    @FXML
    void saveSettingsRecipients(MouseEvent event) {
        ConfigUtil.setRecipients(recipientsGroupList);
        AlertUtil.showAlert("Настройки успешно сохранены");
    }

}