mvn clean package -DskipTests && docker build -f alpine.Dockerfile -t microservice/sda-catalogue:latest . && docker-compose up -d
