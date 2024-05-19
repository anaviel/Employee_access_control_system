# «Android-приложение для контроля доступа сотрудников к предприятию» 
## Описание приложения
Приложение для платформы Android, разработанное на языке программирования Kotlin предназначено для эффективного отслеживания доступа сотрудников к предприятию и предоставляет удобные инструменты для анализа деятельности персонала.
Приложение предназначено для эффективного отслеживания доступа сотрудников к предприятию и предоставляет удобные инструменты для анализа деятельности персонала.
Основные функции:
- Регистрация сотрудников: приложение обеспечивает удобный процесс регистрации сотрудников, позволяя им легко войти в систему.
- Мониторинг входов и выходов: сотрудники имеют возможность просматривать историю своих входов и выходов через пропускную систему.
- Управление доступом: управляющий может мониторить и анализировать активность каждого сотрудника, просматривая историю их посещения предприятия.

## Приложение
### Структура
Язык программирования: Kotlin  
База данных: Firebase  
Среда разработки: Android Studio 
### Осуществление входа
Для входа сотрудникам и администратору необходимо ввести логин и пароль.  

Экран входа в личный кабинет:  
![Первый скриншот](https://github.com/anaviel/Employee_access_control_system/blob/master/screens/entrance.png)
### Функционал сотрудника
Сотруднику необходимо выполнить вход в приложении для дальнейшего пользования.
После чего сотрудник оказывается в личном кабинете, где указано его ФИО, номер телефона и должность.
У сотрудника есть 2 кнопки для пользования: "Показать QR-код" и "Открыть историю посещений". В первой кнопке сотруднику генерируется разовый QR-код для осуществления входа или выхода из помещения. Во второй кнопке сотруднику предоставляется его полная история посещений. 

Экран личного кабинета сотрудника:  
![Второй скриншот](https://github.com/anaviel/Employee_access_control_system/blob/master/screens/employee.png)
### Функционал администратора
Администратору необходимо выполнить вход в приложении для дальнейшего пользования.
После чего администратор оказывается в личном кабинете, где указано его ФИО, номер телефона и должность.
У администратора есть 3 кнопки для пользования: "Сканировать QR-код", "История посещений" и "Добавить сотрудника". В первой кнопке администратор сканирует QR-код входящих и выходящих сотрудников для занесения их в базу данных. Во второй кнопке администратору доступны все истории посещений сотрудников, нажав на любого сотрудника, он попадет на его полную историю взодов и выходов. В тертьей кнопке есть возможность добавить нового сотрудника в базу данных.   

Экран личного кабинета администратора:  
![Третий скриншот](https://github.com/anaviel/Employee_access_control_system/blob/master/screens/admin.png)
## Пользование приложением
### Инструкция по запуску приложения
Скачивание приложения:    
1. Нажмите на зелёную кнопку "Code" и выберите "Download ZIP".  
2. Разархивируйте скачанный ZIP-файл в удобную для вас директорию на компьютере.
3. Запустите Android Studio и в главном меню Android Studio выберите "File" -> "Open".
4. Перейдите в папку с клонированным или распакованным проектом и выберите его.
5. Если вы используете эмулятор, убедитесь, что он настроен и запущен. Это можно сделать через "AVD Manager" в Android Studio. Если вы используете реальное устройство, подключите его к компьютеру через USB и убедитесь, что на нем включена отладка по USB.
6. Нажмите зеленую кнопку "Run" в верхней части Android Studio или выберите "Run" -> "Run 'app'".
7. Выберите устройство (эмулятор или реальное) и нажмите "OK".
8. После запуска приложение откроется на выбранном устройстве (эмуляторе или реальном устройстве).
9. Для начала работы войдите в личный кабинет.

## Авторы
Ковалева Ольга Юрьевна 4216  
Маркова Анастасия Андреевна 4217  
Николаева Елизавета Олеговна 4217
