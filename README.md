# Лабораторные работы по Java

Здесь находятся пять самостоятельных проектов. У каждого свои исходники, инструкция, сборка и исполняемый JAR.

| Папка | Задание | Запуск |
| --- | --- | --- |
| [`01-game-characters`](01-game-characters/) | Основы Java и ООП: игровые персонажи | `java -Dfile.encoding=UTF-8 -jar game-characters.jar` |
| [`02-attendance-journal`](02-attendance-journal/) | Файлы и Java Collections: журнал посещаемости | `java -Dfile.encoding=UTF-8 -jar attendance-journal.jar` |
| [`03-home-appliances`](03-home-appliances/) | Бинарные файлы, импорт/экспорт и тесты: учёт бытовой техники | `java -Dfile.encoding=UTF-8 -jar home-appliances.jar` |
| [`04-film-catalog`](04-film-catalog/) | Клиент-серверный TCP-каталог фильмов с оценками | Сервер: `java -jar film-server.jar 5050`; клиент: `java -Dfile.encoding=UTF-8 -jar film-client.jar 127.0.0.1 5050` |
| [`05-film-database`](05-film-database/) | PostgreSQL и Java JDBC: фильмы, актёры и роли | `java -Dfile.encoding=UTF-8 -jar film-database.jar` |

Чтобы пересобрать проект, запустите `build.bat` в его папке. Требуется JDK 17 или новее. Подробности и формат данных описаны в README каждого задания.
