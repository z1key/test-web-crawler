package test.bsam.crawler;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

public class SimilaritySearch {

    private Document original;
    private Document diff;

    public SimilaritySearch(Document original, Document source) {
        this.original = original;
        this.diff = source;
    }

    /**
     *
     * @param id of source origin element
     * @return found more suitable node in diff document
     */
    public Element findSimilar(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("Id must not be empty");
        }
        Element origin = original.getElementById(id);
        requireNonNull(origin, format("Element with id %s not found in original resource", id));
        return findForId(id).orElseGet(() -> findSimilarByAttrs(origin));
    }

    private Optional<Element> findForId(String tagId) {
        return Optional.ofNullable(diff.getElementById(tagId));
    }

    private Element findSimilarByAttrs(Element origin) {
        Queue<ElementWrapper> priorityQueue = new PriorityQueue<>();
        diff.select(origin.nodeName()).forEach(elem -> prioritizeElements(priorityQueue, origin, elem));
        ElementWrapper result = priorityQueue.poll();
        return (result != null) ? result.getElement() : null;
    }

    private void prioritizeElements(Queue<ElementWrapper> priorityQueue, Element origin, Element diff) {
        int priority = calculatePriority(diff, origin);
        if (priority > 0) {
            priorityQueue.add(new ElementWrapper(diff, priority));
        }
    }

    private int calculatePriority(Element diff, Element original) {
        AtomicInteger priority = new AtomicInteger(0);
        original.attributes().forEach(a -> {
            String diffAttribute = diff.attr(a.getKey());
            if (StringUtils.isNotEmpty(diffAttribute) && diffAttribute.equals(a.getValue())) {
                priority.incrementAndGet();
            }
        });
        return priority.get();
    }

    private static class ElementWrapper implements Comparable<ElementWrapper> {
        private int weight;
        private Element element;

        ElementWrapper(Element element, int weight) {
            this.weight = weight;
            this.element = element;
        }

        Element getElement() {
            return element;
        }

        int getWeight() {
            return weight;
        }

        @Override
        public int compareTo(ElementWrapper o) {
            return o.getWeight() - this.weight;
        }
    }
}
