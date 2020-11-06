package cscie97.smartcity.model.observer;

import java.util.LinkedList;
import java.util.List;

/**
 * The implementation class of Subject interface that is part implementing the Observer Pattern.
 * this interface is extended by the subject implementation classes (which the model service implementation class )
 */
public class SubjectImpl implements Subject{

    private List<Observer> observers;

    /**
     * Constructor of subject interface impl
     */
    public SubjectImpl() {
        this.observers = new LinkedList<>();
    }

    /**
     * getter for the list of all observers of a subject
     * @return
     */
    public List<Observer> getObservers() {
        return observers;
    }

    /**
     * method used to attach an observer to the subject
     * @param observer
     */
    public void attach(Observer observer){
        this.observers.add(observer);
    }

    /**
     * method used to detach an observer of a subject
     * @param observer
     */
    public void detach(Observer observer){
        this.observers.remove(observer);
    }

    /**
     * method used to notify all observers about an update
     * @param m
     */
    public void notify(EventBroker m){
        for(Observer o: observers) {
            o.update(m);
        }

    }
}
