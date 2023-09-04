package es.jocasolo.nodetobean.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import javax.jcr.Node;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import es.jocasolo.nodetobean.annotation.ConvertNode;
import es.jocasolo.nodetobean.annotation.ConvertProperty;

public class Node2Bean {
    
    private Node2Bean() {
    }
    
	public static <T> T transform(Node node, Class<T> clazz) {
	    
	    T bean = null;
	    
	    try {
	        // Default constructor
            bean = clazz.getDeclaredConstructor().newInstance();
            
            // Iterate fields
            for(Field field : clazz.getFields()) {
                resolveField(field, node, bean);
            }
            
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | 
                InvocationTargetException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
		
		return bean;
	}

    private static <T> void resolveField(Field field, Node node, T bean) {
        for(Annotation annotation : field.getAnnotations()) {
            resolveAnnotation(field, annotation, node, bean);
        }
    }

    private static <T> void resolveAnnotation(Field field, Annotation annotation, Node node, T bean) {
        if(annotation instanceof ConvertProperty) {
            ConvertProperty propertyAnnotation = (ConvertProperty) annotation;
            resolveProperty(field, propertyAnnotation, node, bean);
        } else if (annotation instanceof ConvertNode) {
            ConvertNode nodeAnnotation = (ConvertNode) annotation;
            resolveNode(field, nodeAnnotation, node, bean);
        }
    }

    private static <T> void resolveNode(Field field, ConvertNode annotation, Node node, T bean) {
        
    }

    private static <T> void resolveProperty(Field field, ConvertProperty annotation, Node node, T bean) {
        final String propertyName = getName(field, annotation);
        try {
            String value = NodeUtil.getString(node, propertyName);
            if(value != null) {
                BeanUtils.setProperty(bean, field.getName(), value);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static String getName(Field field, ConvertProperty annotation) {
        return StringUtils.isNotEmpty(annotation.name()) ? annotation.name() : field.getName();
        
    }


}
