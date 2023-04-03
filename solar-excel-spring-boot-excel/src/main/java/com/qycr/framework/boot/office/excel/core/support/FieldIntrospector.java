package com.qycr.framework.boot.office.excel.core.support;

import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public final class FieldIntrospector {




    private FieldIntrospector() {
    }

    public static <T> Map<Field, T> selectFields(Class<?> targetType, final MetadataLookup<T> metadataLookup) {
        final Map<Field, T> fieldMap = new LinkedHashMap<>();
        Set<Class<?>> handlerTypes = new LinkedHashSet<>();
        if (!Proxy.isProxyClass(targetType)) {
            Class<?>  specificHandlerType = ClassUtils.getUserClass(targetType);
            handlerTypes.add(specificHandlerType);

        }
        handlerTypes.addAll(ClassUtils.getAllInterfacesForClassAsSet(targetType));

        for (Class<?> currentHandlerType : handlerTypes) {

            ReflectionUtils.doWithFields(currentHandlerType, field -> {
                T result = metadataLookup.inspect(field);
                if (result != null) {
                    fieldMap.put(field,result);
                }
            }, ReflectionUtils.COPYABLE_FIELDS);
        }
        return fieldMap;
    }

    public static Set<Field> selectFields(Class<?> targetType, final ReflectionUtils.FieldFilter filter) {
        return selectFields(targetType, (MetadataLookup<Boolean>) filter::matches).keySet();
    }


    @FunctionalInterface
    public interface MetadataLookup<T> {
        @Nullable
        T inspect(Field field);
    }






}
