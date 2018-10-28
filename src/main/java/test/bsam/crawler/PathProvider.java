package test.bsam.crawler;

import org.jsoup.nodes.Element;

import java.util.Set;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class PathProvider {

    private Element element;
    private StringBuilder stringBuilder;

    public PathProvider(Element element) {
        this.element = element;
        this.stringBuilder = new StringBuilder();
        addPath(element);
    }

    private void addPath(Element element) {
        String id = element.id();
        Set<String> classes = element.classNames();
        if (isNotEmpty(id)) {
            stringBuilder.insert(0, id).insert(0, "#");
        } else if (!classes.isEmpty()) {
            classes.forEach(c -> stringBuilder.insert(0, c).insert(0, "."));
        }
        stringBuilder.insert(0, element.tagName());
        Element parent = element.parent();
        if (parent != null) {
            stringBuilder.insert(0, " > ");
            addPath(parent);
        }
    }

    public String getPath() {
        return stringBuilder.toString();
    }
}
