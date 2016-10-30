# Selection Committee Web application
# Приемная комиссия

Для запуска приложения на сервере Tomcat7 необходимо:
	- запустить сервер для базы данных Derby (это можно сделать с помощью файла ant-derby.xml --> start-server)
	- выполнить SQL script sql/db-create.sql (ant-derby.xml --> run-db-create)
	- экспортировать проект в war файл и закинуть в папку $TOMCAT_HOME/webapps либо задеплоить через Tomcat manager
	
Вход под правами:
администратора: admin/admin
аббитуриента: entrant/entrant