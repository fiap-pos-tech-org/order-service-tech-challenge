docker-build:
	docker build -t tech-challenge:local -f ./Dockerfile .

docker-start:
	docker compose -f docker-compose.yml up -d

docker-stop:
	docker compose -f docker-compose.yml down

sonar-analysis:
	mvn clean verify sonar:sonar -Dsonar.host.url=$(SONARQUBE_URL) -Dsonar.token=$(SONAR_TOKEN)