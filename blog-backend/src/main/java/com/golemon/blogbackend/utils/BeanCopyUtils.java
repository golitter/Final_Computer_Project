package com.golemon.blogbackend.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * BeanCopyUtils provides utility methods for copying Java Bean properties.
 * It allows copying properties from one object to another, or batch copying properties from a list of objects to a target object list.
 */
public class BeanCopyUtils {

    // Private constructor to prevent instantiation of utility class
    private BeanCopyUtils() {
    }

    /**
     * Copies properties from the source object to the target object.
     *
     * @param sourceObject  The source object containing properties to be copied
     * @param targetClass   The class type of the target object, used to create the target object
     * @param <T>           The type of the target object
     * @return              The copied target object
     */
    public static <T> T copyBean(Object sourceObject, Class<T> targetClass) {
        // Create target object
        T targetObject = null;
        try {
            // Create an object of the target type
            targetObject = targetClass.getDeclaredConstructor().newInstance();
            // Copy properties from source object to target object
            BeanUtils.copyProperties(sourceObject, targetObject);
        } catch (Exception e) {
            // Catch exception and print stack trace
            e.printStackTrace();
        }
        return targetObject;
    }

    /**
     * Copies properties from a list of source objects to a list of target objects.
     *
     * @param sourceList    The source object list containing multiple objects to be copied
     * @param targetClass   The class type of the target object, used to create the target object
     * @param <S>           The type of the source object
     * @param <T>           The type of the target object
     * @return              The copied list of target objects
     */
    public static <S, T> List<T> copyBeanList(List<S> sourceList, Class<T> targetClass) {
        // Use stream to copy each source object to a target type object and return a new list
        return sourceList.stream()
                .map(source -> copyBean(source, targetClass)) // Copy each object
                .collect(Collectors.toList());  // Collect into target object list
    }
}