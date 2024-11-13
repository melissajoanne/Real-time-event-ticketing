package lk.ac.iit.RealTimeTicket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Scanner;

@Component
public class TicketSystemConfig implements Serializable {
    private int totalTickets;
    private double ticketReleaseRate;
    private double customerRetrievalRate;
    private int maxTicketCapacity;


    public TicketSystemConfig() {
        this.totalTickets = totalTickets;
        this.customerRetrievalRate = customerRetrievalRate;
        this.ticketReleaseRate = ticketReleaseRate;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public void configureSystem() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n=== Ticket System Configuration ===");

        // Configure Total Number of Tickets
        while (true) {
            try {
                System.out.print("\nEnter Total Number of Tickets (current: " + totalTickets + "): ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    break;
                }
                int newValue = Integer.parseInt(input);
                if (newValue <= 0) {
                    System.out.println("Error: Total tickets must be greater than 0");
                    continue;
                }
                totalTickets = newValue;
                break;
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number");
            }
        }

        // Configure Ticket Release Rate
        while (true) {
            try {
                System.out.print("\nEnter Ticket Release Rate per minute (current: " + ticketReleaseRate + "): ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    break;
                }
                double newValue = Double.parseDouble(input);
                if (newValue <= 0) {
                    System.out.println("Error: Release rate must be greater than 0");
                    continue;
                }
                ticketReleaseRate = newValue;
                break;
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number");
            }
        }

        // Configure Customer Retrieval Rate
        while (true) {
            try {
                System.out.print("\nEnter Customer Retrieval Rate per minute (current: " + customerRetrievalRate + "): ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    break;
                }
                double newValue = Double.parseDouble(input);
                if (newValue <= 0) {
                    System.out.println("Error: Retrieval rate must be greater than 0");
                    continue;
                }
                customerRetrievalRate = newValue;
                break;
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number");
            }
        }

        // Configure Maximum Ticket Capacity
        while (true) {
            try {
                System.out.print("\nEnter Maximum Ticket Capacity (current: " + maxTicketCapacity + "): ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    break;
                }
                int newValue = Integer.parseInt(input);
                if (newValue <= 0) {
                    System.out.println("Error: Maximum capacity must be greater than 0");
                    continue;
                }
                if (newValue > totalTickets) {
                    System.out.println("Error: Maximum capacity cannot exceed total tickets");
                    continue;
                }
                maxTicketCapacity = newValue;
                break;
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number");
            }
        }

        displayConfiguration();
    }

    public void displayConfiguration() {
        System.out.println("\n=== Current Configuration ===");
        System.out.println("Total Tickets: " + totalTickets);
        System.out.println("Ticket Release Rate: " + ticketReleaseRate + " per minute");
        System.out.println("Customer Retrieval Rate: " + customerRetrievalRate + " per minute");
        System.out.println("Maximum Ticket Capacity: " + maxTicketCapacity);
    }

    // Getter methods
    public int getTotalTickets() {
        return totalTickets;
    }

    public double getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public double getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

}
