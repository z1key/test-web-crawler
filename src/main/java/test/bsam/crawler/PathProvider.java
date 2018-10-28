package test.bsam.crawler;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;

import java.util.Set;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class PathProvider {

    private Element element;
    private StringBuilder stringBuilder;

    public PathProvider(Element element) {
        this.element = element;
        this.stringBuilder = new StringBuilder();
    }

    public String getPath() {
        if (element == null) {
            return StringUtils.EMPTY;
        }
        buildPath(element);
        return stringBuilder.toString();
    }

    private void buildPath(Element element) {
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
            buildPath(parent);
        }
    }
}
