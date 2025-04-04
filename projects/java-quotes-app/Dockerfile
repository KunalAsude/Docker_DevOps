# Use the official OpenJDK 17 image as the base image
FROM openjdk:17-jdk-alpine


# Set the working directory inside the container
WORKDIR /app

# Copy the source code into the container
COPY src/Main.java /app/Main.java

COPY quotes.txt quotes.txt

# Compile the Java code
RUN javac Main.java


EXPOSE 8000

# Run the Java application when the container starts
CMD ["java", "Main"]

