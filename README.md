# SMTPAutoSendMessage
#### Приложение для автоматизации отправки и оформления писем с файлами на почту
## Важно!
* В конфиге не настроен отправитель и получатель. Поэтому чтобы настроить конфиг необходимо открыть файл "config.properties"
(путь src/main/resources/config.properties)
```
...
# Настройки отправителя
mail.smtp.sender=почта@mail.ru # Здесь
mail.smtp.sender.password=пароль приложения почты # Здесь

# Настройки получателя(если получателей несколько, то перечислить через запятую)
mail.smtp.recipient=почта получателя@gmail.com # И здесь
...
```
* Также в файле "HelloController.java" не настроен отправитель и получатель в случае отсутствия конфига.
(Путь src/main/java/com/example/smtpautosendmessage/HelloController.java)
```
51        final String from = "секрет"; # Здесь
52        final String password = "секретная информация"; # B здесь
```

### Как работает?
* Чтобы отправить файлы необходимо загрузить их в папку "sendingFiles" (путь src/main/resources/sendingFiles)
* Затем настроить конфиг "config.properties" (на данный момент проверено и работает только с mail.ru)
* Далее запустить приложение в IntelliJ IDEA
* Нажать кнопку "Отправить письмо"