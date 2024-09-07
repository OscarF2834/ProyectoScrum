FROM openJDK:17
COPY "./target/com.sistema.trailers-1.0.jar" "app.jar"
EXPOSE 8090
ENTRYPOINT [ "java", "-jar", "app.jar" ]
