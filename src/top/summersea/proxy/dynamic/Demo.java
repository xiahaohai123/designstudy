package top.summersea.proxy.dynamic;

public class Demo {

    public static void main(String[] args) {
        ProxiedObject proxy = (ProxiedObject) new TestProxy().createProxy(new ProxiedObjectImpl());
        System.out.println(proxy.getInt());
    }
}
