package com.booking.models;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Reservation {
    private String reservationId;
    private Customer customer;
    private Employee employee;
    private List<Service> services;
    private double reservationPrice;
    private String workstage;
    //   workStage (In Process, Finish, Canceled)

    public Reservation(String reservationId, Customer customer, Employee employee, List<Service> services,
            String workstage) {
        this.reservationId = reservationId;
        this.customer = customer;
        this.employee = employee;
        this.services = services;
        this.reservationPrice = calculateReservationPrice();
        this.workstage = workstage;
    };
    
    

    public double calculateTotalPrice(List<Service> selectedServices) {
        double totalPrice = 0;
        for (Service service : selectedServices) {
            totalPrice += service.getPrice();
        }
        return totalPrice;
    }

    public double calculateReservationPrice() {
        double totalPrice = calculateTotalPrice(this.services);

        // Diskon berdasarkan jenis keanggotaan pelanggan
        if (customer.getMember().getMembershipName().equals("Silver")) {
            totalPrice *= 0.95; // Diskon 5% untuk member Silver
        } else if (customer.getMember().getMembershipName().equals("Gold")) {
            totalPrice *= 0.90; // Diskon 10% untuk member Gold
        }

        return totalPrice; 
    }

}
