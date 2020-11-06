package cscie97.smartcity.model.observer;

/**
 * Interface class of Subject that is part implementing the Observer Pattern. this interface is extended by the subject classes (which the model service)
 */
public interface Subject {


    /**
     * method used to attach an observer to the subject
     * @param observer
     */
    void attach(Observer observer);

    /**
     * method used to detach an observer of a subject
     * @param observer
     */
    void detach(Observer observer);

    /**
     * method used to notify all observers about an update
     * @param m
     */
    void notify(EventBroker m);


}
