import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;



public class App {
    public static void main(String[] args) throws Exception {
        AnsiConsole.systemInstall();
        // Conexão HTTP
        String urlString = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        URI enderecamentoURL = URI.create(urlString);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(enderecamentoURL).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        String body = response.body();

        JsonParser parser = new JsonParser();
        List<Map<String, String>> filmeList = parser.parse(body);
        
        // Para cada item dentro da lista de filmes
        for (Map<String,String> filme : filmeList) {
            System.out.println(Ansi.ansi().bgBrightMagenta().a(filme.get("title")).reset());
            System.out.println("Nota: " + filme.get("imDbRating") + " " +  imprimirEstrelas(Double.parseDouble(filme.get("imDbRating"))));
            System.out.println(filme.get("image"));
            System.out.println();
            
        }

    }
    private static String imprimirEstrelas(double nota) {
        int numEstrelas = (int) Math.round(nota);
        StringBuilder sb = new StringBuilder();
        sb.append("★".repeat(Math.max(0, numEstrelas)));
        return sb.toString();
    }
}

