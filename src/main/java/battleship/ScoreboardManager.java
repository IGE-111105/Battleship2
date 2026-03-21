package battleship;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ScoreboardManager {

    private static final String FILE_PATH = "data/scoreboard.json";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    private ScoreboardManager() {
    }

    public static List<ScoreboardEntry> loadScoreboard() {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        try {
            return OBJECT_MAPPER.readValue(file, new TypeReference<List<ScoreboardEntry>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler scoreboard.", e);
        }
    }

    public static void saveScoreboard(List<ScoreboardEntry> entries) {
        File file = new File(FILE_PATH);

        try {
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            OBJECT_MAPPER.writeValue(file, entries);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao guardar scoreboard.", e);
        }
    }

    public static void addGameRecord(Game game) {
        List<ScoreboardEntry> entries = loadScoreboard();

        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        ScoreboardEntry entry = new ScoreboardEntry(
                timestamp,
                game.getTotalMoves(),
                game.getTotalShots(),
                game.getHits(),
                game.getSunkShips(),
                "DERROTA"
        );

        entries.add(entry);
        saveScoreboard(entries);
    }

    public static void printScoreboard() {
        List<ScoreboardEntry> entries = loadScoreboard();

        System.out.println();
        System.out.println("=============== SCOREBOARD ===============");

        if (entries.isEmpty()) {
            System.out.println("Ainda não existem jogos registados.");
            System.out.println("==========================================");
            System.out.println();
            return;
        }

        for (int i = 0; i < entries.size(); i++) {
            ScoreboardEntry e = entries.get(i);
            System.out.println((i + 1) + ". " + e.getTimestamp()
                    + " | Jogadas: " + e.getMoves()
                    + " | Tiros: " + e.getShots()
                    + " | Acertos: " + e.getHits()
                    + " | Afundados: " + e.getSunkShips()
                    + " | Resultado: " + e.getResult());
        }

        System.out.println("==========================================");
        System.out.println();
    }
}