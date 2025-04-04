FROM openjdk:17-jdk-alpine


WORKDIR /app

copy . .


RUN javac /src/main/java/com/houarizegai/calculator/App.java


CMD ["java","App"]
