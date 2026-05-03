package battleship;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;

public class PDFExporter {

    public static void exportGame(Game game) {

        try {
            String filePath = "game_results.pdf";

            Document document = getDocument(filePath);

            // 🔹 Conteúdo do PDF
            document.add(new Paragraph("=== RESULTADOS DO JOGO ==="));
            document.add(new Paragraph("Jogo terminado."));
            document.add(new Paragraph("-------------------------"));

            // 🔹 Informação disponível
            document.add(new Paragraph("Navios restantes: " + game.getRemainingShips()));

            document.close();

            System.out.println("PDF criado com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static @NotNull Document getDocument(String filePath) throws FileNotFoundException {
        PdfWriter writer = new PdfWriter(filePath);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        return document;
    }
}