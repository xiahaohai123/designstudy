package top.summersea.di.dicontainer;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XmlBeanConfigParser implements BeanConfigParser {
    @Override
    public List<BeanDefinition> parse(InputStream inputStream) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);
            Node beans = document.getElementsByTagName("beans").item(0);
            return parseNodeList(beans);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<BeanDefinition> parseNodeList(Node beansDocument) {
        List<BeanDefinition> resultList = new ArrayList<>();
        NodeList beanListDocument = beansDocument.getChildNodes();
        for (int i = 0; i < beanListDocument.getLength(); i++) {
            Node bean = beanListDocument.item(i);
            if ("bean".equals(bean.getNodeName())) {
                BeanDefinition beanDefinition = parseNode(bean);
                resultList.add(beanDefinition);
            }
        }
        return resultList;
    }

    private BeanDefinition parseNode(Node bean) {
        NamedNodeMap attributes = bean.getAttributes();

        String id = attributes.getNamedItem("id").getNodeValue();
        String className = attributes.getNamedItem("class").getNodeValue();
        Node lazyInitNode = attributes.getNamedItem("lazy-init");
        Node scope = attributes.getNamedItem("scope");

        BeanDefinition beanDefinition = new BeanDefinition(id, className);
        if (lazyInitNode != null) {
            beanDefinition.setLazyInit(Boolean.parseBoolean(lazyInitNode.getNodeValue()));
        }
        if (scope != null) {
            beanDefinition.setScope(BeanDefinition.Scope.getInstance(scope.getNodeValue()));
        }
        NodeList constructArgNodeList = bean.getChildNodes();
        for (int i = 0; i < constructArgNodeList.getLength(); i++) {
            Node constructorNode = constructArgNodeList.item(i);
            if ("construct-arg".equalsIgnoreCase(constructorNode.getNodeName())) {
                beanDefinition.addConstructorArgList(parseConstructorArg(constructorNode));
            }
        }
        return beanDefinition;
    }

    private BeanDefinition.ConstructorArg parseConstructorArg(Node constructorNode) {
        BeanDefinition.ConstructorArg constructorArg = new BeanDefinition.ConstructorArg();
        NamedNodeMap attributes = constructorNode.getAttributes();
        Node refAttr = attributes.getNamedItem("ref");
        Node typeAttr = attributes.getNamedItem("type");
        Node valueAttr = attributes.getNamedItem("value");
        if (refAttr != null) {
            String refValue = refAttr.getNodeValue();
            if (refValue != null && !"".equals(refValue)) {
                constructorArg.setRef(true);
            }
            constructorArg.setArg(refValue);
        } else {
            if (typeAttr != null) {
                try {
                    constructorArg.setType(Class.forName(typeAttr.getNodeValue()));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            constructorArg.setArg(valueAttr.getNodeValue());
        }

        return constructorArg;
    }
}
