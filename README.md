# SMTPAutoSendMessage
#### ���������� ��� ������������� �������� � ���������� ����� � ������� �� �����
## �����!
* � ������� �� �������� ����������� � ����������. ������� ����� ��������� ������ ���������� ������� ���� "config.properties"
(���� src/main/resources/config.properties)
```
...
# ��������� �����������
mail.smtp.sender=�����@mail.ru # �����
mail.smtp.sender.password=������ ���������� ����� # �����

# ��������� ����������(���� ����������� ���������, �� ����������� ����� �������)
mail.smtp.recipient=����� ����������@gmail.com # � �����
...
```
* ����� � ����� "HelloController.java" �� �������� ����������� � ���������� � ������ ���������� �������.
(���� src/main/java/com/example/smtpautosendmessage/HelloController.java)
```
51        final String from = "������"; # �����
52        final String password = "��������� ����������"; # B �����
```

### ��� ��������?
* ����� ��������� ����� ���������� ��������� �� � ����� "sendingFiles" (���� src/main/resources/sendingFiles)
* ����� ��������� ������ "config.properties" (�� ������ ������ ��������� � �������� ������ � mail.ru)
* ����� ��������� ���������� � IntelliJ IDEA
* ������ ������ "��������� ������"