Project contains build for surl service docker image and mysql docker image.

To trigger build with no deploy: 
	mvn clean install -Ddocker.skip
To trigger build with docker build and deploy:
	mvn clean install
	
Surl Service is available at localhost:8080/
Mysql will create tables and default user

POST request need to have Basic Auth enabled and the default user/pass:
	user/pass

Currently there is no login page so use default user for testing purpose
To remove the default user, edit the init.sql within mysql module -> src/main/docker/sql/init.sql

GET requesst require not AUTH