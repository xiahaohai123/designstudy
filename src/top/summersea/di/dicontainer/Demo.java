package top.summersea.di.dicontainer;

import top.summersea.di.mybeans.RateLimiter;
import top.summersea.di.mybeans.TestClass;

public class Demo {
    public static void main(String[] args) {
        String resourceRoot = "/top/summersea/di/";
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext(resourceRoot + "beans.xml");
        //RateLimiter rateLimiter = (RateLimiter) applicationContext.getBean("rateLimiter");
        //rateLimiter.test();

        TestClass testClass = (TestClass) applicationContext.getBean("testClass");
        testClass.test();
    }
}
