package hello;

import hello.annotation.LogExecutionTime;

public class Main {
    @LogExecutionTime
    public static void main(String[] args) {
        System.out.printf("Main");
    }
}
