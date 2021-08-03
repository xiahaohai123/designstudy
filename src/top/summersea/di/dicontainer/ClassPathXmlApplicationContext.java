package top.summersea.di.dicontainer;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ClassPathXmlApplicationContext implements ApplicationContext {

    private final BeansFactory beansFactory;
    private final BeanConfigParser beanConfigParser;

    public ClassPathXmlApplicationContext(String configLocation) {
        beansFactory = new BeansFactory();
        beanConfigParser = new XmlBeanConfigParser();
        loadBeanDefinitions(configLocation);
    }

    private void loadBeanDefinitions(String configLocation) {
        try (InputStream in = this.getClass().getResourceAsStream(configLocation)) {
            if (in == null) {
                throw new RuntimeException("Can not find config file: ".concat(configLocation));
            }
            List<BeanDefinition> beanDefinitionList = beanConfigParser.parse(in);
            beansFactory.addBeanDefinitions(beanDefinitionList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getBean(String beanId) {
        return beansFactory.getBean(beanId);
    }
}
