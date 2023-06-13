package com.example.smtpautosendmessage;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Контроллер окна руководства пользователя
 */
public class TutorialController {
    @FXML
    private Label homeTitle, serverTitle;

    @FXML
    private AnchorPane pgHomeTutorial, pgServerTutorial, pgClientTutorial;

    public void initialize() {
        homeTitle.setWrapText(true);
        serverTitle.setWrapText(true);
        clientTitle.setWrapText(true);
        clientDescription.setWrapText(true);

        imgParent.widthProperty().addListener((obs, oldVal, newVal) -> {
            clientTutorImage.setFitWidth((Double) newVal-10);
        });
        imgParent.heightProperty().addListener((obs, oldVal, newVal) -> {
                clientTutorImage.setFitHeight((Double) newVal - 10);
        });
        clientTutorImage.setPreserveRatio(true);
        pgHomeTutorial.toFront();
    }

    public void openPgServer(MouseEvent event) {
        pgServerTutorial.toFront();
    }

    public void openPgClient(MouseEvent event) {
        numStepPageClient = 1;
        updateClientTutorPg();
        pgClientTutorial.toFront();
    }

    public void openPgHome(MouseEvent event) {
        pgHomeTutorial.toFront();
    }

    /**
     * Инструкция по настройке сервера
     */

    public void openLink(MouseEvent event) {
        Hyperlink link = (Hyperlink) event.getSource();
        SMTPApplication.hostServicesStatic.showDocument(link.getText());
    }

    /**
     * Общее руководство по приложению
     */
    @FXML
    private ImageView clientTutorImage;
    @FXML
    private HBox imgParent;
    @FXML
    private Label clientTitle, clientDescription;
    /** Номер страницы общего руководства */
    int numStepPageClient = 1;
    String[] titlesPageClient = new String[] {
            "Окно \"Форма\"",
            "Окно \"Форма\" > Меню \"Конфигурация\"",
            "Окно \"Форма\" > Меню \"Форма\"",
            "Окно \"Настройки\"",
            "Окно \"Настройки\" > Страница \"Прикрепляемые файлы\"",
            "Окно \"Настройки\" > Страница \"SMTP подключение\"",
            "Окно \"Настройки\" > Страница \"Группы получателей\" часть 1",
            "Окно \"Настройки\" > Страница \"Группы получателей\" часть 2",
            "Окно \"Руководство\"",
            "Окно \"Руководство\" > Страница \"Ссылки на инструкции для настройки SMTP-серверов\"",
            "Окно \"Руководство\" > Страница \"Общее руководство по приложению\""
    };
    String[] descriptionPageClient = new String[] {
            "В данном окне можно сформировать и отправить письмо. Поле \"Получател(и):\" можно заполнить вручную или выбрать в выпадающем списке группу получателей. Поле \"Заголовок(и):\" формируется из полей \"Суффикс:\" и \"Префикс:\", а также из названий прикрепляемых файлов.",
            "В данном меню кнопка \"Экспортировать\" позволяет сохранить копию конфигурационного файла. Кнопка \"Импортировать\" позволяет загрузить настройки выбранного конфигурационного файла, текущий конфигурационный файл будет перезаписан. Копии не перезаписываются.",
            "В данном меню кнопка \"Очистить\" стирает все данные с полей формы. Кнопка \"Загрузить из конфигурации\" позволяет загрузить данные полей формы из конфигурации. Кнопка \"Сохранить в конфигурацию\" позволяет сохранить данные полей формы в конфигурацию.",
            "В данном окне можно настроить отправляемые файлы, группы получателей и SMTP подключение к серверу, которое нужно смотреть в меню \"Руководство\" > Страница \"Список гиперссылок на инструкции для настройки SMTP-серверов\"",
            "На данной странице можно выбрать папку с прикрепляемыми файлами. Названия файлов будут отображаться в сгенерированном поле \"Заголовок(и):\" окна \"Форма\". Кнопка \"Отмена\" закрывает окно, а кнопка \"Сохранить\" сохраняет изменения в конфигурацию.",
            "На данной странице задаются настройки SMTP подключения к серверу. По умолчанию все флажки убраны, если о них нет информации, то просто уберите их. Кнопка \"Отмена\" закрывает окно, а кнопка \"Сохранить\" сохраняет изменения в конфигурацию.",
            "На данной странице редактируются группы получателей. Поле \"Фильтр:\" фильтрует по названию группы. Создание: правой кнопкой мыши (ПКМ) по строке таблицы, затем выберите добавление. Удаление: выделите строки, нажмите по ним ПКМ и выберите удаление.",
            "Изменение: нажмите дважды по ячейке, поменяйте, нажмите \"Enter\" для применения, для отмены \"Esc\". Кнопка \"Отмена\" закрывает окно, а кнопка \"Сохранить\" сохраняет изменения в конфигурацию.",
            "В данном окне расположены: кнопка \"Ссылки на инструкции по настройке SMTP-серверов\" ведёт на страницу с гиперссылками на инструкции по настройке различных SMTP-серверов; кнопка \"Общее руководство по приложению\" ведёт на общее руководство по приложению. Кнопка \"<\" в левом верхнем углу возвращает",
            "На данной странице расположены ссылки для некоторых популярных почтовых серверов, перейдя по ссылкам для выбранного SMTP-сервера можно узнать что вводить в настройки SMTP подключения, логин соответствует логину пользователя почтового сервера, а пароль это пароль приложения.",
            "На данной странице можно ознакомиться с функционалом приложения переходя по слайдам с помощью кнопок \"<\" и \">\", кнопка \"<\" в левом верхнем углу возвращает на главную страницу руководства."
    };

    /**
     * Обновление данных страницы общего руководства приложения
     */
    private void updateClientTutorPg() {
        if (titlesPageClient.length < numStepPageClient) {
            numStepPageClient = 1;
        }
        if (numStepPageClient <= 0) {
            numStepPageClient = titlesPageClient.length;
        }
        clientTitle.setText(titlesPageClient[numStepPageClient-1]);
        clientDescription.setText(descriptionPageClient[numStepPageClient-1]);
        clientTutorImage.setImage(new Image(String.valueOf(getClass().getResource("tutorialClient/"+numStepPageClient+".jpg"))));

    }

    public void nextStepClient(MouseEvent event) {
        numStepPageClient++;
        updateClientTutorPg();
    }

    public void previousStepClient(MouseEvent event) {
        numStepPageClient--;
        updateClientTutorPg();
    }

}
