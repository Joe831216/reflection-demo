import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.springframework.cloud.netflix.feign.FeignClient;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class ReflectionDemo {
    public static void main(String args[]) {
        // 取得套件下所有類別
        Reflections reflections = new Reflections("", new SubTypesScanner(false));
        Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);
        for (Class c : classes) {
            // 若該類別為介面且@FeignClient不為空，則印出詳細資訊
            if (c.isInterface() && c.getAnnotation(FeignClient.class) != null) {
                System.out.println("Value of " + c.getName());
                Annotation feignClientAnnotation = c.getAnnotation(FeignClient.class);
                Method[] methods = feignClientAnnotation.annotationType().getDeclaredMethods();
                for (Method method : methods) {
                    try {
                        Object value = method.invoke(feignClientAnnotation, (Object[])null);
                        System.out.println("    " + method.getName() + " : " + value);
                    } catch (IllegalAccessException e) {
                        System.out.println("IllegalAccessException");
                    } catch (InvocationTargetException e) {
                        System.out.println("InvocationTargetException");
                    }
                }
            }
        }
    }
}
