package top.summersea.di.dicontainer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class BeansFactory {

    private final ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    public Object getBean(String beanId) {
        return createBean(beanDefinitionMap.get(beanId));
    }

    public void addBeanDefinitions(List<BeanDefinition> beanDefinitionList) {
        for (BeanDefinition beanDefinition : beanDefinitionList) {
            this.beanDefinitionMap.putIfAbsent(beanDefinition.getId(), beanDefinition);


        }

        // 这两个循环不能合并，必须先把所有的数据加载进map才能进行创建
        for (BeanDefinition beanDefinition : beanDefinitionList) {
            if (!beanDefinition.isLazyInit() && beanDefinition.isSingleton()) {
                createBean(beanDefinition);
            }
        }
    }

    /**
     * 创建bean
     * 1. 通过反射加载类
     * 2. 创建该类的对象
     * 2.1 如果没有构造参数则直接创建
     * 2.2 如果有构造参数按以下情况分类
     * 2.2.1 构造参数为Class-value时，正常使用该数据找到构造器以及创建
     * 2.2.2 构造参数为ref时，递归调用此方法，转换成Class-value参数
     * 3. 返回bean对象
     * @param beanDefinition bean的定义
     * @return bean
     */
    private Object createBean(BeanDefinition beanDefinition) {
        if (beanDefinition.isSingleton() && singletonObjects.contains(beanDefinition.getId())) {
            return singletonObjects.get(beanDefinition.getId());
        }

        Object bean = null;

        try {
            Class<?> beanClass = Class.forName(beanDefinition.getClassName());
            List<BeanDefinition.ConstructorArg> args = beanDefinition.getConstructorArgList();
            if (args.isEmpty()) {
                bean = beanClass.getConstructor().newInstance();
            } else {
                Class[] argClasses = new Class[args.size()];
                Object[] argObjects = new Object[args.size()];
                for (int i = 0; i < args.size(); i++) {
                    BeanDefinition.ConstructorArg arg = args.get(i);
                    if (!arg.isRef()) {
                        argClasses[i] = arg.getType();
                        Object argValue = arg.getArg();
                        if (Integer.class.equals(argClasses[i])) {
                            argValue = Integer.valueOf((String) argValue);
                        }
                        argObjects[i] = argValue;
                    } else {
                        BeanDefinition refBeanDefinition = beanDefinitionMap.get(arg.getArg());
                        if (refBeanDefinition == null) {
                            throw new RuntimeException("bean is not defined");
                        }
                        argClasses[i] = Class.forName(refBeanDefinition.getClassName());
                        argObjects[i] = createBean(refBeanDefinition);
                    }
                }
                bean = beanClass.getConstructor(argClasses).newInstance(argObjects);
            }


        } catch (ClassNotFoundException |
                InvocationTargetException |
                InstantiationException |
                IllegalAccessException |
                NoSuchMethodException e) {
            e.printStackTrace();
        }

        return bean;
    }
}
