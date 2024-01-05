package com.booking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.booking.models.Customer;
import com.booking.models.Employee;
import com.booking.models.Person;
import com.booking.models.Reservation;
import com.booking.models.Service;
import com.booking.repositories.PersonRepository;

public class ReservationService {
	
	    private static List<Person> customers = PersonRepository.getAllPerson();
	    private static List<Person> employees = PersonRepository.getAllPerson();
	    
	    private static PrintService printService = new PrintService();
	    
	    private static int lastReservationId = 0;
	    
	   
	 
	    public static Reservation createReservation(Scanner scanner, List<Service> serviceList, List<Reservation> reservationList, List<Person> personList) {
	        Customer customer = inputCustomer(scanner, personList);
	        Employee employee = inputEmployee(scanner, personList);
	        List<Service> selectedServices = selectServices(scanner, serviceList);

	        Reservation newReservation = createNewReservation(customer, employee, selectedServices);
	        addReservationToList(newReservation, reservationList);

	        System.out.println("Booking Berhasil!");

	        return newReservation;
	    }

	    private static Customer inputCustomer(Scanner scanner, List<Person> personList) {
	        String customerId;
	        Customer customer;
	        do {
	        	printService.showAllCustomer(personList);
	            System.out.println("Silahkan Masukkan Customer Id:");
	            customerId = scanner.nextLine();
	            customer = getCustomerById(customerId, customers);
	            if (customer == null) {
	                
	                System.out.println("Customer tidak ditemukan.");
	            }
	        } while (customer == null || !ValidationService.validateCustomerId(customerId, customers));
	        return customer;
	    }

	    private static Employee inputEmployee(Scanner scanner, List<Person> personList) {
	        String employeeId;
	        Employee employee;
	        do {
	        	printService.showAllEmployee(personList);
	            System.out.println("Silahkan Masukkan Employee Id:");
	            employeeId = scanner.nextLine();
	            employee = getEmployeeById(employeeId);
	            if (employee == null) {
	                System.out.println("Employee tidak ditemukan.");
	            }
	        } while (employee == null || !ValidationService.validateEmployeeId(employeeId, employees));
	        return employee;
	    }

	    private static List<Service> selectServices(Scanner scanner, List<Service> serviceList) {
	        List<Service> selectedServices = new ArrayList<>();
	        String input;
	        do {
	            System.out.println("Silahkan Masukkan Service Id:");
	            String serviceId = scanner.nextLine();
	            Service service = getServiceById(serviceId, serviceList);
	            if (service != null && ValidationService.validateServiceId(serviceId, serviceList)) {
	                selectedServices.add(service);
	            } else {
	                System.out.println("Service tidak valid.");
	            }

	            System.out.println("Ingin pilih service yang lain (Y/T)?");
	            input = scanner.nextLine();
	        } while (input.equalsIgnoreCase("Y"));
	        return selectedServices;
	    }
	    
	    
	    private static String generateNextReservationId() {
	        lastReservationId++; // Menaikkan nomor ID
	        return "Rsv-" + String.format("%03d", lastReservationId); // Format nomor ID dengan leading zeroes
	    }


	    private static Reservation createNewReservation(Customer customer, Employee employee, List<Service> selectedServices) {
	        Reservation newReservation = new Reservation();
	        newReservation.setReservationId(generateNextReservationId());
	        newReservation.setCustomer(customer);
	        newReservation.setEmployee(employee);
	        newReservation.setServices(selectedServices);
	        newReservation.setReservationPrice(newReservation.calculateReservationPrice());
	        newReservation.setWorkstage("In Process");
	        return newReservation;
	    }

	    private static void addReservationToList(Reservation newReservation, List<Reservation> reservationList) {
	        reservationList.add(newReservation);
	    }


	    // Fungsi-fungsi bantuan lainnya

	    private static Customer getCustomerById(String customerId, List<Person> customers) {
	        for (Person customer : customers) {
	            if (customer.getId().equals(customerId)) {
	                return (Customer) customer;
	            }
	        }
	        return null;
	    }

	    private static Employee getEmployeeById(String employeeId) {
	        for (Person employee : employees) {
	            if (employee.getId().equals(employeeId)) {
	                return (Employee) employee;
	            }
	        }
	        return null;
	    }

	    private static Service getServiceById(String serviceId, List<Service> serviceList) {
	        for (Service service : serviceList) {
	            if (service.getServiceId().equals(serviceId)) {
	                return service;
	            }
	        }
	        return null;
	    }


	    public static void editReservationWorkstage(Scanner scanner, List<Reservation> reservationList) {
	        System.out.print("Masukkan Reservation id: ");
	        String reservationId = scanner.nextLine();

	        Reservation reservationToEdit = ReservationWorkstageByReservationId(reservationId, reservationList);
	        if (reservationToEdit != null) {
	            System.out.print("Masukkan ID Reservasi: ");
	            String newWorkstage = scanner.nextLine();

	            if (isValidWorkstage(newWorkstage)) {
	                reservationToEdit.setWorkstage(newWorkstage);
	                System.out.println("Data Reservation berhasil diubah!");
	            } else {
	                System.out.println("Format Placement salah. Hanya boleh diisi dengan 'Finish' atau 'Cancel'.");
	            }
	        } else {
	            System.out.println("Reservation dengan ID tersebut tidak ditemukan.");
	        }
	    }

	    private static boolean isValidWorkstage(String workstage) {
	        return workstage.equalsIgnoreCase("Finish") || workstage.equalsIgnoreCase("Cancel");
	    }


    public static Reservation ReservationWorkstageByReservationId(String reservationId, List<Reservation> reservationList){
        for (Reservation reservation : reservationList) {
            if (reservation.getReservationId().equals(reservationId)) {
                return reservation;
            }
        }
        return null;
    }


}
