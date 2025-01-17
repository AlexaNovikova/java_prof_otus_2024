package homework;

import java.util.*;

// @SuppressWarnings({"java:S1186", "java:S1135", "java:S1172"}) // при выполнении ДЗ эту аннотацию надо удалить
public class CustomerService {

    // todo: 3. надо реализовать методы этого класса
    // важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны
    private final NavigableMap<Customer, String> customersMap =
            new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        // Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        //        return null; // это "заглушка, чтобы скомилировать"
        Map.Entry<Customer, String> smallestEntry = customersMap.firstEntry();
        return (smallestEntry != null
                ? new AbstractMap.SimpleEntry<>(new Customer(smallestEntry.getKey()), smallestEntry.getValue())
                : null);
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        //        return null; // это "заглушка, чтобы скомилировать"
        Map.Entry<Customer, String> nextEntry = customersMap.higherEntry(customer);
        return (nextEntry != null
                ? new AbstractMap.SimpleEntry<>(new Customer(nextEntry.getKey()), nextEntry.getValue())
                : null);
    }

    public void add(Customer customer, String data) {
        customersMap.put(customer, data);
    }
}
