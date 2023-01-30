package com.practica;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/*
 * Este código es un programa de Web Scraping en Java. 
 * Utiliza la biblioteca Jsoup para conectarse a una página web y extraer información de la misma. 
 * El programa se conecta a la página web "https://douglas.es/c/perfumes/" y selecciona los elementos deseados en la página 
 * (en este caso, los nombres de los perfumes) y los agrega a una lista de cadenas. 
 * Luego, imprime el contenido de la lista.
 *  La función "getHtml" se encarga de la conexión a la página y 
 * la función "getElementos" se encarga de la selección y extracción de los elementos deseados.
 *  La ejecución del programa se realiza en el método "main",
 *  donde se llaman a las funciones anteriores y se manejan las excepciones correspondientes.
 */

public class WebScraping {


    private static final int MAX_RESULTS = 119;
    private static final String BASE_URL = "https://douglas.es/c/perfumes/";

    public static void main(String[] args) throws IOException {
        // Crear un ArrayList vacío
        ArrayList<String> perfumes = new ArrayList<>();

        // Conectar a la página web y extraer los elementos deseados
        int page = 0;
        while (page < MAX_RESULTS) {
            Document html = getHtml(BASE_URL + page);
            perfumes.addAll(getPerfumeNames(html));
            page++;
            System.out.println(page);
        }

        // Imprimir el ArrayList de perfumes
        for (String perfume : perfumes) {
            System.out.println(perfume);
        }
    }

    private static ArrayList<String> getPerfumeNames(Document html) {
        ArrayList<String> perfumes = new ArrayList<>();
        // Seleccionar los elementos deseados
        Elements perfumeElements = html.select("div.rd__product-tile");
    
        for (Element perfumeElement : perfumeElements) {
            String perfumeUrl = perfumeElement.select("a").attr("abs:href");
            Document perfumeHtml = getHtml(perfumeUrl);
            if (perfumeHtml == null) {
                continue;
            }
            String perfumeName = perfumeHtml.select("h1.rd__headline--130").text();
            perfumes.add(perfumeName);
        }
        System.out.println(perfumes);
    
        return perfumes;
    }

    private static Document getHtml(String url) {
        Document html = null;
        try {
            html = Jsoup.connect(url).get();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return html;
    }
}