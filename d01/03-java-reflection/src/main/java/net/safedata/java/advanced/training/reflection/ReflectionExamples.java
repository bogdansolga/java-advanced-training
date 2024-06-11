package net.safedata.java.advanced.training.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionExamples {

    public static void main(String[] args) {
        try {
            Class<?> theClass = Class.forName("net.safedata.java.advanced.training.reflection.ProductService");
            Method[] methods = theClass.getMethods();
            for (Method method : methods) {
                //System.out.println(method.getName());
            }

            Method buildProduct = theClass.getMethod("buildProduct", int.class, String.class);
            final Annotation[] declaredAnnotations = buildProduct.getDeclaredAnnotations();

            final Object tablet = buildProduct.invoke(new ProductService(), 10, "Tablet");
            System.out.println(tablet);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
