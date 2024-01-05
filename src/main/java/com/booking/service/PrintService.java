package com.booking.service;

import java.util.List;

import com.booking.models.Reservation;
import com.booking.models.Service;
import com.booking.models.Customer;
import com.booking.models.Employee;
import com.booking.models.Person;

public class PrintService {
    public static void printMenu(String title, String[] menuArr){
        int num = 1;
        System.out.println(title);
        for (int i = 0; i < menuArr.length; i++) {
            if (i == (menuArr.length - 1)) {   
                num = 0;
            }
            System.out.println(num + ". " + menuArr[i]);   
            num++;
        }
    }

    public String printServices(List<Service> serviceList){
        String result = "";
        // Bisa disesuaikan kembali
        for (Service service : serviceList) {
            result += service.getServiceName() + ", ";
        }
        return result;
    }

    // Function yang dibuat hanya sebgai contoh bisa disesuaikan kembali
    public void showRecentReservation(List<Reservation> reservationList){
        int num = 1;
        System.out.printf("| %-4s | %-4s | %-11s | %-15s | %-15s | %-15s | %-10s |\n",
                "No.", "ID", "Nama Customer", "Service", "Biaya Service", "Pegawai", "Workstage");
        System.out.println("+========================================================================================+");
        for (Reservation reservation : reservationList) {
            if (reservation.getWorkstage().equalsIgnoreCase("Waiting") || reservation.getWorkstage().equalsIgnoreCase("In Process")) {
                System.out.printf("| %-4s | %-4s | %-11s | %-15s | %-15s | %-15s | %-10s |\n",
                num, reservation.getReservationId(), reservation.getCustomer().getName(), printServices(reservation.getServices()), reservation.getReservationPrice(), reservation.getEmployee().getName(), reservation.getWorkstage());
                num++;
            }
        }
    }
    
    

    public void showAllCustomer(List<Person> personList) {
    	int num = 1;
        System.out.println("List of All Customers:");
        System.out.printf("| %-4s | %-8s | %-8s | %-11s | %-8s | %-12s |\n",
                "No.", "ID", "Nama", "Alamat", "Member", "Wallet");
        System.out.println("+========================================================================================+");
        for (Person person : personList) {
            if (person instanceof Customer) {
                Customer customer = (Customer) person;
                System.out.printf("| %-4s | %-8s | %-8s | %-11s | %-8s | Rp.%-10.2f |\n",
                       num, customer.getId(), customer.getName(), customer.getAddress(),
                        customer.getMember().getMembershipName(), customer.getWallet());
                num++;
            }
        }
       
    }


    public void showAllEmployee(List<Person> personList){
    	int num = 1;
        System.out.println("List of All Customers:");
        System.out.printf("| %-4s | %-8s | %-8s | %-11s | %-8s |\n",
                "No.", "ID", "Nama", "Alamat", "Pengalaman");
        System.out.println("+========================================================================================+");
        for (Person person : personList) {
            if (person instanceof Employee) {
            	Employee employe = (Employee) person;
                System.out.printf("| %-4s | %-8s | %-8s | %-11s | %-8s |\n",
                       num, employe.getId(), employe.getName(), employe.getAddress(),
                       employe.getExperience());
                num++;
            }
        }
        
    }

    public void showHistoryReservation(List<Reservation> reservationList) {
        int num = 1;
        double totalProfitFinish = 0;
        double totalProfitCancel = 0;

        System.out.printf("| %-4s | %-4s | %-11s | %-15s | %-15s | %-15s | %-10s |\n",
                "No.", "ID", "Nama Customer", "Service", "Biaya Service", "Pegawai", "Workstage");
        System.out.println("+========================================================================================+");
        for (Reservation reservation : reservationList) {
            if (reservation.getWorkstage().equalsIgnoreCase("Finish")) {
                System.out.printf("| %-4s | %-4s | %-11s | %-15s | %-15s | %-15s | %-10s |\n",
                        num, reservation.getReservationId(), reservation.getCustomer().getName(), printServices(reservation.getServices()), reservation.getReservationPrice(), reservation.getEmployee().getName(), reservation.getWorkstage());
                num++;

                totalProfitFinish += reservation.getReservationPrice(); // Menambahkan biaya reservasi yang selesai ke total keuntungan selesai
            } else if (reservation.getWorkstage().equalsIgnoreCase("Cancel")) {
                // Menampilkan detail reservasi yang dibatalkan
                System.out.printf("| %-4s | %-4s | %-11s | %-15s | %-15s | %-15s | %-10s |\n",
                        num, reservation.getReservationId(), reservation.getCustomer().getName(), printServices(reservation.getServices()), reservation.getReservationPrice(), reservation.getEmployee().getName(), reservation.getWorkstage());
                num++;

                totalProfitCancel += reservation.getReservationPrice(); // Menambahkan biaya reservasi yang dibatalkan ke total keuntungan cancel
            }
        }
        System.out.println("Total Keuntungan dari Finish: " + totalProfitFinish);
        System.out.println("Total  dari Cancel: " + totalProfitCancel);
    }

}
