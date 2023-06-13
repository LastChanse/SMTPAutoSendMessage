# SMTPAutoSendMessage
#### ���������� ��� ������������� �������� � ���������� ����� � ������� �� �����
�������������� ������������ �������:  
![Linux](https://img.shields.io/badge/Linux-FCC624?style=for-the-badge&logo=linux&logoColor=black)
![Windows](https://img.shields.io/badge/Windows-0078D6?style=for-the-badge&logo=windows&logoColor=white)
## ������
>### Windows
>#### ����������
>��� ������ �������� [�������� ������ ��� Windows](https://download.oracle.com/java/19/archive/jdk-19_windows-x64_bin.zip) jdk 19 � ����������� �� ���������� ����� (����������� � ������) � ����� jdk-19.0.
>#### ���������� ��� ������
>��������� ���� build.bat
><br>����� �������:
>```
>"0/5 Build on Windows"
>��� ����������� ������� ����� ������� . . .
>```
>1. ������� Enter. ����� ������� Y � ������� Enter. ����� ����� ������� Y � ������� Enter.
>   <br>����� �������:
>```
>"1/5 Deleate target and out"
>target, �� ������� [Y(��)/N(���)]? Y
>out, �� ������� [Y(��)/N(���)]? Y
>��� ����������� ������� ����� ������� . . .
>```
>2. ������� Enter � ��������� ����� ������ (� ����������� ����� ����� �������� BUILD SUCCESS, ����� ����� ��� 2 ���� ����� �������, � ���� � �������� �������� ��������� �� �����)
>   <br>����� �������:
>```
>"2/5 Build jar with dependencies"
>��� ����������� ������� ����� ������� . . .
>```
>3. ������� Enter.
>   <br>����� �������:
>```
>"3/5 Make out folder"
>...
>����������� ������: 16.
>��� �������� ...\SMTPAutoSendMessage\out\jar\stmpautomess.jar:
>��� ����� ��� ��������
>(F = ����, D = �������)? F
>...\SMTPAutoSendMessage\target\SMTPAutoSendMessage-1.0-SNAPSHOT.jar -> ...\SMTPAutoSendMessage\out\jar\stmpautomess.jar
>����������� ������: 1.
>��� ����������� ������� ����� ������� . . .
>```
>4. ������� Enter.
>   <br>����� �������:
>```
>"4/5 Build exe"
>��� ����������� ������� ����� ������� . . .
>```
>5. ������� Enter.
>   <br>����� �������:
>```
>"5/5 Build done"
>��� ����������� ������� ����� ������� . . .
>```
>�� ������� Enter ������� ���������.

>### Linux
>#### ����������
>��� ������ �������� [�������� ������ ��� Linux](https://download.oracle.com/java/19/archive/jdk-19_linux-x64_bin.tar.gz) jdk 19 � ����������� �� ���������� ����� (����������� � ������) � ����� jdk-19.0.
>#### ���������� ��� ������
>��������� ���� build.sh
><br>����� �������:
>```
>0/5 Build on Linux
>Press any key to resume ...
>```
>1. ������� Enter.
>   <br>����� �������:
>```
>1/5 Deleate target and out
>Press any key to resume ...
>```
>2. ������� Enter � ��������� ����� ������ (������ ���� �������� BUILD SUCCESS)
>   <br>����� �������:
>```
>[INFO] ------------------------------------------------------------------------
>[INFO] BUILD SUCCESS
>[INFO] ------------------------------------------------------------------------
>[INFO] Total time:  2.593 s
>[INFO] Finished at: 2023-06-14T02:33:46+03:00
>[INFO] ------------------------------------------------------------------------
>Press any key to resume ...
>```
>3. ������� Enter.
>   <br>����� �������:
>```
>3/5 Make out folder
>Press any key to resume ...
>```
>4. ������� Enter.
>   <br>����� �������:
>```
>4/5 Build exe
>launch4j: Compiling resources
>launch4j: Linking
>launch4j: Wrapping
>WARNING: Sign the executable to minimize antivirus false positives or use launching instead of wrapping.
>launch4j: Successfully created D:\javaapps\SMTPAutoSendMessage\.\out\exe\smtpautomess.exe
>Press any key to resume ...
>```
>5. ������� Enter.
>   <br>����� �������:
>```
>5/5 Build done
>Press any key to resume ...
>```
>�� ������� Enter ������� ���������.

## ������
����� ��� ������� �������� � ����� out:
* ��� ������� � Windows ����� exe.
* ��� ������� � Linux ����� jar.

### ������ �� ������
��� ������� ���������� ���������������� ����� � ��������� ����������� ���� � ������������� �����.
�� ������ ����� ��������� ����������� � SMTP-������� ������������� � ���������������� �����.
������ �� ����� ������������� ����������.

### ��� ��������?
�������� ����������� ������ ���������� � ���� "�����������" > �������� "����� ����������� �� ����������"