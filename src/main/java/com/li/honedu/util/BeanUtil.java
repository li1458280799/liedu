package com.li.honedu.util;

import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;

import java.util.Iterator;
import java.util.List;

public class BeanUtil extends BeanUtils {
    public BeanUtil() {
    }

    public static <T> T mapToClass(Object sourceObject, Class<T> destinationClass) {
        T targetObject = instantiateClass(destinationClass);
        copyProperties(sourceObject, targetObject);
        return targetObject;
    }

    public static <T> List<T> mapToList(List sourceList, Class<T> destinationClass) {
        List<T> destinationList = Lists.newArrayListWithCapacity(sourceList.size());
        Iterator var3 = sourceList.iterator();

        while(var3.hasNext()) {
            Object sourceObject = var3.next();
            T targetObject = instantiateClass(destinationClass);
            copyProperties(sourceObject, targetObject);
            destinationList.add(targetObject);
        }

        return destinationList;
    }
}
