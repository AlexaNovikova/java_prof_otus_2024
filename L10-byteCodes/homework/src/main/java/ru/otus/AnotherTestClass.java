package ru.otus;

public class AnotherTestClass implements TestLoggingInterface {

    @Override
    public void calculation(int param1) {}

    @Override
    public void calculation(int param1, int param2) {}

    @Override
    public void calculation(int param1, int param2, String param3) {}

    @Log
    @Override
    public void calculation(int param1, int param2, String param3, double param4) {}

    @Log
    @Override
    public void calculation(int param1, int param2, String param3, boolean param4) {}
}
