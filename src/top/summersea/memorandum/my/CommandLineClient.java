package top.summersea.memorandum.my;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandLineClient {

    private static String hello = "欢迎来到简单命令行程序！";

    private List<String> inputList = new ArrayList<>();

    public static void main(String[] args) {
        new CommandLineClient().start();
    }

    private void start() {
        sayHello();
        startLoop();
    }

    private void sayHello() {
        System.out.println(hello);
    }

    private void startLoop() {
        Scanner sc = new Scanner(System.in);
        for (; ; ) {
            System.out.print(">");
            String input = sc.nextLine();
            Command command = Command.getCommand(input);
            command.execute(inputList, input);
        }
    }
}
