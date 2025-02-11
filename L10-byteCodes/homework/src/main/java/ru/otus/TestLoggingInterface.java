package ru.otus;

public interface TestLoggingInterface {
    void calculation(int param1);

    void calculation(int param1, int param2);

    void calculation(int param1, int param2, String param3);

    void calculation(int param1, int param2, String param3, double param4);

    void calculation(int param1, int param2, String param3, boolean param4);
}
