package com.abc.framework.interceptor;

import cn.hutool.core.bean.BeanUtil;
import com.abc.common.constant.Constants;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class MybatisInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws InvocationTargetException, IllegalAccessException {
        // 在执行SQL前执行的代码
        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        Object parameter = invocation.getArgs()[1];
        if (ms.getId().matches(".*insert.*")) {
            Map<String, Object> paramMap = BeanUtil.beanToMap(parameter);
            Date now = new Date();
            paramMap.put("createTime", now);
            paramMap.put("updateTime", now);
            paramMap.put("status", Constants.YES);
            paramMap.put("ver", Constants.DEFAULT_VER);
            invocation.getArgs()[1] = paramMap;
            // 暴力反射设置参数
//            setFieldValue(parameter, "id", id);
        } else if (ms.getId().matches(".*update.*")) {
            Map<String, Object> paramMap = BeanUtil.beanToMap(parameter);
//            Map<String, Object> paramMap = BeanUtil.beanToMap(paramTmpMap.get("et"));
            if (Objects.nonNull(paramMap.get("updateTime"))) {
                Date now = new Date();
                paramMap.put("updateTime", now);
            }
            if (Objects.nonNull(paramMap.get("ver"))) {
                paramMap.put("ver", (int) paramMap.get("ver") + 1);
            }
            Map<String, Object> resMap = new HashMap<>();
            resMap.put("param1", paramMap);
            resMap.put("et", paramMap);
            invocation.getArgs()[1] = resMap;
        }
        // 继续执行原来的逻辑
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // 设置额外属性，可忽略
    }

    // 工具方法，用于设置实体对象的字段值
    private void setFieldValue(Object obj, String fieldName, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
//            throw new RuntimeException("Failed to set field value", e);
        }
    }

}