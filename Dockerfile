FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY target/BattleshipGamePlayer-2.0.jar app.jar
ENTRYPOINT ["java", "-Dlog4j2.disable.jmx=true", "-Dlog4j.skipJansi=true", "-cp", "app.jar", "battleship.MainConsole"]