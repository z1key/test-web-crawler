package test.bsam.crawler;

import org.jsoup.nodes.Element;

public interface SimilaritySearch {

    /**
     * Returns the most similar node from diff according to origin element
     *
     * @param id of source origin element
     * @return more suitable node in diff document or null if nothing found
     *
     * @throws IllegalArgumentException when id is empty
     * @throws NullPointerException when origin element not found
     */
    Element findSimilar(String id);

    /**
     * Returns element from original document by id
     *
     * @param id of existing source origin element
     * @return source origin element
     *
     * @throws IllegalArgumentException when id is empty
     * @throws NullPointerException when element for id was not found
     */
    Element getOrigin(String id);
}
