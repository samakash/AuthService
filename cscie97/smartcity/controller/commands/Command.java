package cscie97.smartcity.controller.commands;

import cscie97.smartcity.model.observer.EventBroker;

public interface Command {

    /**
     * This method will invoke the command to the ModelService after validating the rules if applicable
     * @param eventBroker
     */
    void execute(EventBroker eventBroker);

}
