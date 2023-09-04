package es.jocasolo.nodetobean.util;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;

public class NodeUtil {
    
    private NodeUtil() {
    }

    public static Property getProperty(Node node, String name) {
        return getProperty(node, name, null);
    }

    public static Property getProperty(Node node, String name, String lang) {

        Property property = null;

        try {
            if (node.hasProperty(getNameWithLanguage(name, lang))) {
                property = node.getProperty(name);
            } else {
                property = node.getProperty(lang);
            }
        } catch (RepositoryException e) {
            e.printStackTrace();
        }

        return property;
    }
    
    public static String getString(Node node, String name) {
        return getString(node, name, null);
    }

    public static String getString(Node node, String name, String lang) {
        
        String result = null;

        try {
            Property property = getProperty(node, name, lang);
            if (property != null) {
                result = property.getString();
            }
        } catch (RepositoryException e) {
            e.printStackTrace();
        }

        return result;
    }

    private static String getNameWithLanguage(String name, String lang) {
        String result = name;
        if (StringUtils.isNotEmpty(lang)) {
            result += "_" + lang;
        }
        return result;
    }

}
