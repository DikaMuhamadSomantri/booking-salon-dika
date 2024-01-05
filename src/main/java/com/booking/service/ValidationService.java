package com.booking.service;

import java.util.List;


import com.booking.models.Person;
import com.booking.models.Service;

public class ValidationService {
    // Buatlah function sesuai dengan kebutuhan
	  public static boolean validateInput(String input) {
	        return input != null && !input.trim().isEmpty();
	    }

    public static boolean validateCustomerId(String customerId, List<Person> customers) {
        for (Person customer : customers) {
            if (customer.getId().equals(customerId)) {
                return true;
            }
        }
        return false;
    }

    public static boolean validateEmployeeId(String employeeId, List<Person> employees) {
        for (Person employee : employees) {
            if (employee.getId().equals(employeeId)) {
                return true;
            }
        }
        return false;
    }

    public static boolean validateServiceId(String serviceId, List<Service> services) {
        for (Service service : services) {
            if (service.getServiceId().equals(serviceId)) {
                return true;
            }
        }
        return false;
    }

}
