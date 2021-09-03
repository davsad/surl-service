FROM mysql:latest

ENV MYSQL_DATABASE=surldb \
    MYSQL_ROOT_PASSWORD=password

ADD sql/init.sql /docker-entrypoint-initdb.d