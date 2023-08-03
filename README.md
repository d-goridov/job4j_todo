# Приложение "TODO список"

На главной странице отображается список заданий. С помощью кнопки "Добавить" создаете задание.
Оно добавляется в список со статусом "Новое". Если нажмете на описание задания, то перейдете на 
страницу с подробным описанием, на ней доступны следующие действия:
- Выполнить - статус задания меняется на "Выполнено"
- Редактировать - изменить описание задания
- Удалить - удаляет задание из списка

Также на главной странице в навигационном меню есть ссылки на страницы, которые отображают
задания со статусом "Новое" и "Выполнено".

# Технологии
- Java 17
- Spring boot
- Hibernate 5.6
- Maven 3.8,
- Postgres 14
- Thymeleaf
- Bootstrap
- Liquibase 4.15
- Lombok 1.18

## Запуск проекта
1. В Postgres создаете БД todo: username = postgres,  password = password.
2. Обновляете схему БД через ```liquibase:update```
3. Запускаете проект:
 ```shell
  mvn spring-boot run
 ```
4. Переходите по ссылке http://localhost:8080/tasks/

## Контакты
- Telegram: https://t.me/d_goridov
- Email: **d.goridov@mail.ru**
