package homework;

import java.util.Deque;
import java.util.LinkedList;

// @SuppressWarnings({"java:S1186", "java:S1135", "java:S1172"}) // при выполнении ДЗ эту аннотацию надо удалить
public class CustomerReverseOrder {

    // todo: 2. надо реализовать методы этого класса
    // надо подобрать подходящую структуру данных, тогда решение будет в "две строчки"
    private final Deque<Customer> customerDeque = new LinkedList<>();

    public void add(Customer customer) {
        customerDeque.push(customer);
    }

    public Customer take() {
        //        return null; // это "заглушка, чтобы скомилировать"
        return customerDeque.pop();
    }
}
