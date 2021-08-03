package top.summersea.di.dicontainer;

public class ClassPathXmlApplicationContext implements ApplicationContext {

    private BeansFactory beansFactory;
    private BeanConfigParser beanConfigParser;

    public ClassPathXmlApplicationContext(String configLocation) {
        beansFactory = new BeansFactory();
        beanConfigParser = new XmlBeanConfigParser();
    }

    private void loadBeanDefinitions(String configLocation) {
    }

    @Override
    public Object getBeans(String beanId) {
        return null;
    }
}
