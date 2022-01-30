package homework;

import java.util.*;
import java.util.logging.Logger;

public class CustomerService {

    private final NavigableMap<Customer, String> customers = new TreeMap(Comparator.comparing(Customer::getScores));
    private final Logger log = Logger.getLogger(CustomerService.class.getName());

    public Map.Entry<Customer, String> getSmallest() {
        return getEntryClone(customers.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return getEntryClone(customers.higherEntry(customer));
    }

    public void add(Customer customer, String data) {
        customers.put(customer, data);
    }

    private Map.Entry<Customer, String> getEntryClone(Map.Entry<Customer, String> sourceEntry) {
        if (sourceEntry != null) {
            try {
                return new AbstractMap.SimpleImmutableEntry(sourceEntry.getKey().clone(), sourceEntry.getValue());
            } catch (CloneNotSupportedException e) {
                log.throwing(Customer.class.getName(), "clone", e);
            }
        }
        return null;
    }

}
