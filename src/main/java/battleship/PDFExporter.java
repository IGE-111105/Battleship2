package battleship;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

public class PDFExporter {

    public static void exportGame(Game game) {

        try {
            String filePath = "game_results.pdf";

            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

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
}