package cscie97.smartcity.controller;

import cscie97.smartcity.controller.commands.Command;
import cscie97.smartcity.model.observer.EventBroker;
import cscie97.smartcity.model.observer.Observer;

import java.util.List;

/**
 * Controller Interface class
 */
public interface Controller extends Observer {

    /**
     * Static getter of the singleton instance of controller service
     * @return
     */
    static ControllerImpl getInstance(){
        return ControllerImpl.getInstance();
    }

    /**
     * getter for commands logger
     * @return
     */
    List<Command> getCommandsLogger();

    /**
     * This method will take the eventBroker and find the proper rule, and trigger the command based on the sensor type and sensor read.
     * The rules logic can be found here and inside the command implementation
     * @param eb
     */
    void ProcessEventBroker(EventBroker eb);


    /**
     * getter for events logger
     * @return
     */
    List<EventBroker> getEventsLogger();




}
