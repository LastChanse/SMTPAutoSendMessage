package com.example.smtpautosendmessage;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 * Контроллер окна руководства пользователя
 */
public class TutorialController {
    @FXML
    public Label homeTitle, serverTitle;

    @FXML
    public AnchorPane pgHomeTutorial, pgServerTutorial, pgClientTutorial;

    /** Предварительные действия над элементами окна руководства пользователя при инициализации контроллера */
    public void initialize() {
        // Делаем автоматический перенос строк для заголовков и описания руководства пользователя
        homeTitle.setWrapText(true);
        serverTitle.setWrapText(true);
        clientTitle.setWrapText(true);
        clientDescription.setWrapText(true);

        // При изменении размеров окна, менять размеры изображения
        imgParent.widthProperty().addListener((obs, oldVal, newVal) -> clientTutorImage.setFitWidth((Double) newVal-10));
        imgParent.heightProperty().addListener((obs, oldVal, newVal) -> clientTutorImage.setFitHeight((Double) newVal - 10));

        // Запрет на изменения соотношения сторон изображения
        clientTutorImage.setPreserveRatio(true);
        // Задание первой отображаемой страницы
        pgHomeTutorial.toFront();
    }

    /**
     * Открыть страницу ссылок на инструкции по настройке сервера
     */
    @FXML
    public void openPgServer() {
        pgServerTutorial.toFront();
    }

    /**
     * Открыть страницу руководства по приложению
     */
    @FXML
    public void openPgClient() {
        numStepPageClient = 1;
        updateClientTutorPg();
        pgClientTutorial.toFront();
    }

    /**
     * Открыть главную страницу руководства
     */
    @FXML
    public void openPgHome() {
        pgHomeTutorial.toFront();
    }

    /**
     * Инструкция по настройке сервера
     */
    /**
     * Открыть гиперссылку по её содержимому
     * @param event -- событие
     */
    @FXML
    public void openLink(MouseEvent event) {
        Hyperlink link = (Hyperlink) event.getSource();
        SMTPApplication.hostServicesStatic.showDocument(link.getText());
    }

    /**
     * Общее руководство по приложению
     */
    @FXML
    public ImageView clientTutorImage;
    @FXML
    public HBox imgParent;
    @FXML
    public Label clientTitle, clientDescription;
    /** Номер страницы общего руководства */
    private int numStepPageClient = 1;
    /** Заголовки страниц общего руководства */
    private String[] titlesPageClient = new String[] {
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
    /** Описания страниц общего руководства */
    private String[] descriptionPageClient = new String[] {
            "В данном окне можно сформировать и отправить письмо. Поле \"Получатель(и):\" можно заполнить вручную или выбрать в выпадающем списке группу получателей. Поле \"Заголовок(и):\" формируется из полей \"Суффикс:\" и \"Префикс:\", а также из названий прикрепляемых файлов.",
            "В данном меню кнопка \"Экспортировать\" позволяет сохранить копию конфигурационного файла. Кнопка \"Импортировать\" позволяет загрузить настройки выбранного конфигурационного файла, текущий конфигурационный файл будет перезаписан. Копии не перезаписываются.",
            "В данном меню кнопка \"Очистить\" стирает все данные с полей формы. Кнопка \"Загрузить из конфигурации\" позволяет загрузить данные полей формы из конфигурации. Кнопка \"Сохранить в конфигурацию\" позволяет сохранить данные полей формы в конфигурацию.",
            "В данном окне можно настроить отправляемые файлы, группы получателей и SMTP подключение к серверу, которое нужно смотреть в меню \"Руководство\" > Страница \"Список гиперссылок на инструкции для настройки SMTP-серверов\".",
            "На данной странице можно выбрать папку с прикрепляемыми файлами. Названия файлов будут отображаться в сгенерированном поле \"Заголовок(и):\" окна \"Форма\". Кнопка \"Отмена\" закрывает окно, а кнопка \"Сохранить\" сохраняет изменения в конфигурацию.",
            "На данной странице задаются настройки SMTP подключения к серверу. По умолчанию все флажки убраны, если о них нет информации, то просто уберите их. Кнопка \"Отмена\" закрывает окно, а кнопка \"Сохранить\" сохраняет изменения в конфигурацию.",
            "На данной странице редактируются группы получателей. Поле \"Фильтр:\" фильтрует по названию группы. Создание: правой кнопкой мыши (ПКМ) по строке таблицы, затем выберите добавление. Удаление: выделите строки, нажмите по ним ПКМ и выберите удаление.",
            "Изменение: нажмите дважды по ячейке, поменяйте, нажмите \"Enter\" для применения, для отмены \"Esc\". Кнопка \"Отмена\" закрывает окно, а кнопка \"Сохранить\" сохраняет изменения в конфигурацию.",
            "В данном окне расположены: кнопка \"Ссылки на инструкции по настройке SMTP-серверов\" ведёт на страницу с гиперссылками на инструкции по настройке различных SMTP-серверов; кнопка \"Общее руководство по приложению\" ведёт на общее руководство по приложению. Кнопка \"<\" в левом верхнем углу возвращает на главную окна руководства.",
            "На данной странице расположены ссылки для некоторых популярных почтовых серверов, перейдя по ссылкам для выбранного SMTP-сервера можно узнать что вводить в настройки SMTP подключения. Кнопка \"<\" в левом верхнем углу возвращает на главную окна руководства.",
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

    /**
     * Переход на следующий слайд
     */
    @FXML
    public void nextStepClient() {
        numStepPageClient++;
        updateClientTutorPg();
    }


    /**
     * Переход на предыдущий слайд
     */
    @FXML
    public void previousStepClient() {
        numStepPageClient--;
        updateClientTutorPg();
    }

}
