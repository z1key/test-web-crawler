package test.bsam.crawler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Application {
    public static void main(String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("Missing required arguments: <origin_doc> <diff_doc> <tag_id>");
        }
        String origin = args[0];
        String diff = args[1];
        String id = args[2];

        Document originalDoc = new FileDocumentSource(origin).getDocument();
        Document diffDoc = new FileDocumentSource(diff).getDocument();

        SimilaritySearch search = new SimilaritySearch(originalDoc, diffDoc);

        Element similar = search.findSimilar(id);
        System.out.println(new PathProvider(similar).getPath());
    }
}
