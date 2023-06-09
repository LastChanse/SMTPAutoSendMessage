package com.example.smtpautosendmessage;

import com.example.smtpautosendmessage.Utils.ConfigUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Path;
import java.util.Map;
import java.util.Properties;

/**
 * Контроллер окна настроек
 */
public class SettingsController {
    @FXML
    private AnchorPane pgSMTPSettings, pgRecieverSettings, pgPathSettings, pgHelloSettings;
    @FXML
    private ListView pageList;

    @FXML
    public void initialize() {
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
                String pageName = pageList.getSelectionModel().getSelectedItem().toString();
                switch (pageName) {
                    case ("Прикрепляемые файлы"):
                        Properties config = ConfigUtil.config;
                        textFieldSelectCatDirSendingFiles.setText(config.getProperty("data.files.path",""));
                        pgPathSettings.toFront();
                        break;
                    case ("SMTP подключение"):
                        pgSMTPSettings.toFront();
                        break;
                    case ("Группы получателей"):
                        pgRecieverSettings.toFront();
                        break;
                    default:
                        pgPathSettings.toFront();
                        break;
                }
            }
        });
    }

    /** Кнопка закрытия окна настроек */
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
    }

    /** Поле с выбранным путем к папке с прикрепляемыми файлами */
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
     * Сохранить настройки страницы SMTP подключение
     */

    @FXML
    void saveSettingsSMTP(MouseEvent event) {

    }

    /** Страница Настройки групп получателей */
    /**
     * Сохранить настройки страницы групп получателей
     */

    @FXML
    void saveSettingsRecipients(MouseEvent event) {

    }

}