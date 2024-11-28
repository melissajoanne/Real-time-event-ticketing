package lk.ac.iit.RealTimeEventTicketing.dto;

public class TicketReleaseRequest {
    private int ticketsPerRelease;
    private String ticketType;
    private double ticketPrice;
    private int ticketReleaseInterval;

    public int getTicketReleaseInterval() {
        return ticketReleaseInterval;
    }

    public void setTicketReleaseInterval(int ticketReleaseInterval) {
        this.ticketReleaseInterval = ticketReleaseInterval;
    }

    // Getters and Setters
    public int getTicketsPerRelease() {
        return ticketsPerRelease;
    }

    public void setTicketsPerRelease(int ticketsPerRelease) {
        this.ticketsPerRelease = ticketsPerRelease;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
}

