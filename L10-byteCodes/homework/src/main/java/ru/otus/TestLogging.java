package ru.otus;

import ru.otus.annotations.Log;

public class TestLogging implements TestLoggingInterface {

    @Override
    @Log
    public void calculation(int param1) {
        System.out.println("Calculation(int param1)");
    }

    @Override
    public void calculation(int param1, int param2) {
        System.out.println("calculation(int param1, int param2)");
    }

    @Override
    @Log
    public void calculation(int param1, int param2, String param3) {
        System.out.println("calculation(int param1, int param2, String param3)");
    }

    @Override
    public void calculation(int param1, int param2, String param3, double param4) {
        System.out.println("calculation(int param1, int param2, String param3, double param4)");
    }

    @Override
    public void calculation(int param1, int param2, String param3, boolean param4) {
        System.out.println("calculation(int param1, int param2, String param3, boolean param4)");
    }

    @Override
    public String toString() {
        return "TestLogging{}";
    }
}
