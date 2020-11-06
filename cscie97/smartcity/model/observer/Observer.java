package cscie97.smartcity.model.observer;

/**
 * Interface class of Observer
 */
public interface Observer {

    /**
     * Method to update observers
     * @param m
     */
    void update(EventBroker m);

}
