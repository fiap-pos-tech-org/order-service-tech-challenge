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
	docker compose up -d

docker-stop:
	docker compose down

sonar-analysis:
	mvn -B verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Dsonar.token=$(SONAR_TOKEN) -Pcoverage
