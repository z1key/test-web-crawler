package test.bsam.crawler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Application {
    public static void main(String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("Missing required arguments: <origin_doc> <diff_doc> <tag_id>");
        }
        String originalPath = args[0];
        String diffPath = args[1];
        String id = args[2];

        Document originalDoc = new FileDocumentSource(originalPath).getDocument();
        Document diffDoc = new FileDocumentSource(diffPath).getDocument();

        SimilaritySearchImpl search = new SimilaritySearchImpl(originalDoc, diffDoc);

        Element origin = search.getOrigin(id);
        System.out.printf("\nOriginal: \n%s", new PathProvider(origin).getPath());

        Element similar = search.findSimilar(id);

        System.out.printf("\nSimilar: \n%s", new PathProvider(similar).getPath());
    }
}
