FROM 100.100.20.216/aux-pub/jdk8

ADD target/*.jar app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom" ,"-jar", "-Xms256m","-Xmx256m" ,"/app.jar"]