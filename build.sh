mvn clean package -DskipTests && docker build -f alpine.Dockerfile -t microservice/sda-catalogue:latest . && docker-compose up -d --force-recreate && docker stop sda-catalog-rest-api && docker rm sda-catalog-rest-api