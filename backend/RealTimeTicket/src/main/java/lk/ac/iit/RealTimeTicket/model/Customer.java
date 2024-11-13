package lk.ac.iit.RealTimeTicket.model;

public class Customer implements Runnable {
    private long id;
    private int retrievalInterval;

    public Customer(long id, int retrievalInterval) {
        this.id = id;
        this.retrievalInterval = retrievalInterval;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRetrievalInterval() {
        return retrievalInterval;
    }

    public void setRetrievalInterval(int retrievalInterval) {
        this.retrievalInterval = retrievalInterval;
    }

    @Override
    public void run() {
     
    }
}
