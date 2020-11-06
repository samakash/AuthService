package cscie97.smartcity.model.observer;

import cscie97.smartcity.controller.Controller;

/**
 * Implementation class of the observer interface
 */
public class ObserverImpl implements Observer{

    /**
     * Method to update observers. It will update observers with new updated eventBroker
     * @param eventBroker the object that contains the updated message
     */
    public void update(EventBroker eventBroker){
        Controller controller = Controller.getInstance();
        controller.update(eventBroker);
    }

}
