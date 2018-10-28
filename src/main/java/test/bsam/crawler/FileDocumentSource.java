package test.bsam.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

public class FileDocumentSource implements DocumentSource {

    private File source;

    public FileDocumentSource(String filePath) {
        source = new File(filePath);
    }

    @Override
    public Document getDocument() {
        try {
            return Jsoup.parse(source, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
