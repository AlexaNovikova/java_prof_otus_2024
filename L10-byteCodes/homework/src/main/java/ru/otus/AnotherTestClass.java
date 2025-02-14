package ru.otus;

import ru.otus.annotations.Log;

public class AnotherTestClass implements AnotherLoggingInterface {

    @Log
    @Override
    public void anotherCalculation(int param1) {}

    @Log
    @Override
    public void anotherCalculation(int param1, int param2) {}

    @Override
    public void anotherCalculation(int param1, int param2, String param3) {}
}
