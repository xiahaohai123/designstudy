package top.summersea.test;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TestIterator {

    @Test
    public void testIterator() {
        List<String> list = new LinkedList<>();

        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");

        for (String s : list) {
            System.out.println(s);
        }
    }
}
