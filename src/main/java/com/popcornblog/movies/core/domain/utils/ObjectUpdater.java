package com.popcornblog.movies.core.domain.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class ObjectUpdater {

    public static void updateNonNullProperties(Object source, Object target) {
        try {
            BeanInfo sourceBeanInfo = Introspector.getBeanInfo(source.getClass());
            PropertyDescriptor[] sourcePropertyDescriptors = sourceBeanInfo.getPropertyDescriptors();

            BeanInfo targetBeanInfo = Introspector.getBeanInfo(target.getClass());
            PropertyDescriptor[] targetPropertyDescriptors = targetBeanInfo.getPropertyDescriptors();

            for (PropertyDescriptor sourcePropertyDescriptor : sourcePropertyDescriptors) {
                String propertyName = sourcePropertyDescriptor.getName();
                PropertyDescriptor targetPropertyDescriptor = getPropertyDescriptor(targetPropertyDescriptors, propertyName);

                if (targetPropertyDescriptor != null) {
                    Object sourceValue = sourcePropertyDescriptor.getReadMethod().invoke(source);
                    Object targetValue = sourcePropertyDescriptor.getReadMethod().invoke(target);

                    if (sourceValue != null && (targetValue == null || !sourceValue.equals(targetValue))) {
                        targetPropertyDescriptor.getWriteMethod().invoke(target, sourceValue);
                    }
                }
            }
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            // Handle exceptions as needed
            throw new RuntimeException("Error updating properties", e);
        }
    }

    private static PropertyDescriptor getPropertyDescriptor(PropertyDescriptor[] descriptors, String propertyName) {
        for (PropertyDescriptor descriptor : descriptors) {
            if (Objects.equals(descriptor.getName(), propertyName)) {
                return descriptor;
            }
        }
        return null;
    }
}
