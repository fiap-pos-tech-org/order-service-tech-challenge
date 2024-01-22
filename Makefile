unit-test:
	echo "Executando testes unitários"
	mvn test

integration-test:
	echo "Executando testes de integração"
	mvn test -P integration-test

system-test:
	echo "Executando testes de sistema"
	mvn test -P system-test

test: unit-test integration-test

docker-build:
	docker build -t tech-challenge:local -f ./Dockerfile .

docker-start:
	docker compose -f docker-compose.yml up -d

docker-stop:
	docker compose -f docker-compose.yml down

sonar-analysis:
	mvn clean verify sonar:sonar -Dsonar.host.url=$(SONARQUBE_URL) -Dsonar.login=admin -Dsonar.password=admin

docker-start-sonar:
	docker compose -f docker-compose-sonar.yml up -d

docker-stop-sonar:
	docker compose -f docker-compose-sonar.yml down
