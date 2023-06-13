# SMTPAutoSendMessage
#### Приложение для автоматизации отправки и оформления писем с файлами на почту
Поддерживаемые операционные системы:  
![Linux](https://img.shields.io/badge/Linux-FCC624?style=for-the-badge&logo=linux&logoColor=black)
![Windows](https://img.shields.io/badge/Windows-0078D6?style=for-the-badge&logo=windows&logoColor=white)
## Сборка
>### Windows
>#### Подготовка
>Для работы сборщика [скачайте версию для Windows](https://download.oracle.com/java/19/archive/jdk-19_windows-x64_bin.zip) jdk 19 и переместите всё содержимое папки (находящейся в архиве) в папку jdk-19.0.
>#### Инструкция для сборки
>Запустите файл build.bat
><br>Текст консоли:
>```
>"0/5 Build on Windows"
>Для продолжения нажмите любую клавишу . . .
>```
>1. Нажмите Enter. Затем введите Y и нажмите Enter. Затем снова введите Y и нажмите Enter.
>   <br>Текст консоли:
>```
>"1/5 Deleate target and out"
>target, вы уверены [Y(да)/N(нет)]? Y
>out, вы уверены [Y(да)/N(нет)]? Y
>Для продолжения нажмите любую клавишу . . .
>```
>2. Нажмите Enter и дождитесь конца сборки (в открывшихся окнах будет написано BUILD SUCCESS, после этого эти 2 окна можно закрыть, а окно с основной консолью закрывать не нужно)
>   <br>Текст консоли:
>```
>"2/5 Build jar with dependencies"
>Для продолжения нажмите любую клавишу . . .
>```
>3. Нажмите Enter.
>   <br>Текст консоли:
>```
>"3/5 Make out folder"
>...
>Скопировано файлов: 16.
>Что означает ...\SMTPAutoSendMessage\out\jar\stmpautomess.jar:
>имя файла или каталога
>(F = файл, D = каталог)? F
>...\SMTPAutoSendMessage\target\SMTPAutoSendMessage-1.0-SNAPSHOT.jar -> ...\SMTPAutoSendMessage\out\jar\stmpautomess.jar
>Скопировано файлов: 1.
>Для продолжения нажмите любую клавишу . . .
>```
>4. Нажмите Enter.
>   <br>Текст консоли:
>```
>"4/5 Build exe"
>Для продолжения нажмите любую клавишу . . .
>```
>5. Нажмите Enter.
>   <br>Текст консоли:
>```
>"5/5 Build done"
>Для продолжения нажмите любую клавишу . . .
>```
>По нажатию Enter консоль закроется.

>### Linux
>#### Подготовка
>Для работы сборщика [скачайте версию для Linux](https://download.oracle.com/java/19/archive/jdk-19_linux-x64_bin.tar.gz) jdk 19 и переместите всё содержимое папки (находящейся в архиве) в папку jdk-19.0.
>#### Инструкция для сборки
>Запустите файл build.sh
><br>Текст консоли:
>```
>0/5 Build on Linux
>Press any key to resume ...
>```
>1. Нажмите Enter.
>   <br>Текст консоли:
>```
>1/5 Deleate target and out
>Press any key to resume ...
>```
>2. Нажмите Enter и дождитесь конца сборки (должно быть написано BUILD SUCCESS)
>   <br>Текст консоли:
>```
>[INFO] ------------------------------------------------------------------------
>[INFO] BUILD SUCCESS
>[INFO] ------------------------------------------------------------------------
>[INFO] Total time:  2.593 s
>[INFO] Finished at: 2023-06-14T02:33:46+03:00
>[INFO] ------------------------------------------------------------------------
>Press any key to resume ...
>```
>3. Нажмите Enter.
>   <br>Текст консоли:
>```
>3/5 Make out folder
>Press any key to resume ...
>```
>4. Нажмите Enter.
>   <br>Текст консоли:
>```
>4/5 Build exe
>launch4j: Compiling resources
>launch4j: Linking
>launch4j: Wrapping
>WARNING: Sign the executable to minimize antivirus false positives or use launching instead of wrapping.
>launch4j: Successfully created D:\javaapps\SMTPAutoSendMessage\.\out\exe\smtpautomess.exe
>Press any key to resume ...
>```
>5. Нажмите Enter.
>   <br>Текст консоли:
>```
>5/5 Build done
>Press any key to resume ...
>```
>По нажатию Enter консоль закроется.

## Запуск
Файлы для запуска хранятся в папке out:
* Для запуска в Windows папка exe.
* Для запуска в Linux папка jar.

### Запуск из релиза
Для запуска распакуйте заархивированную папку и запустите исполняемый файл в распакованной папке.
На данном этапе настройка подключения к SMTP-серверу исключительно в конфигурационном файле.
Письмо же можно редактировать графически.

### Как работает?
Следуйте руководству внутри приложения в меню "Руководство" > страница "Общее руководство по приложению"