package com.aik.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/9/9.
 */
public class BeansUtils {

    public static <T> T transMap2Bean(Map<String, Object> map, Class<T> t) {
        T obj = null;
        try {
            obj = t.newInstance();
            BeanInfo beanInfo = Introspector.getBeanInfo(t);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                if (map.containsKey(key)) {
                    Object value = map.get(key);
                    // 得到property对应的setter方法
                    Method setter = property.getWriteMethod();

                    // 针对mybatis返回Long类型特殊处理
                    Class<?>[] parameterTypes = setter.getParameterTypes();
                    if (value instanceof Long && parameterTypes[0] == Integer.class) {
                        setter.invoke(obj, Integer.valueOf(value.toString()));
                    } else {
                        setter.invoke(obj, value);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("transMap2Bean Error " + e);
        }

        return obj;
    }

    public static <T> List<T> transListMap2ListBean(List<Map<String, Object>> listMap, Class<T> t) {
        List<T> list = new ArrayList<>();
        try {
            for (Map<String, Object> map : listMap) {
                list.add(transMap2Bean(map, t));
            }
        } catch (Exception e) {
            System.out.println("transListMap2ListBean Error " + e);
        }

        return list;
    }

    public static Map<String, Object> transBean2Map(Object obj) {
        if (obj == null) {
            return null;
        }

        Map<String, Object> map = new HashMap<>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);

                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            System.out.println("transBean2Map Error " + e);
        }
        return map;
    }
}
