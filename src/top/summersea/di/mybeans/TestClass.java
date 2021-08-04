package top.summersea.di.mybeans;

public class TestClass {

    private String name;

    public TestClass(String name) {
        this.name = name;
    }

    public void test() {
        System.out.println("hello " + name);
    }
}
