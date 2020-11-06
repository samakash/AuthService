package cscie97.smartcity.controller;

import cscie97.smartcity.controller.commands.*;
import cscie97.smartcity.model.observer.EventBroker;
import cscie97.smartcity.model.observer.ObserverImpl;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller interface implementation class
 */
public class ControllerImpl extends ObserverImpl implements Controller{

    public static ControllerImpl instance;
    private List<EventBroker> eventsLogger; // logger of events received
    private List<Command> commandsLogger; // logger of commands executed

    /**
     * Singleton instance getter
     * @return
     */
    public static ControllerImpl getInstance() {
        if (instance == null){
            instance = new ControllerImpl();
        }
        return instance;
    }

    /**
     * constructor for the controller
     */
    private ControllerImpl(){
        this.eventsLogger = new ArrayList<>();
        this.commandsLogger = new ArrayList<>();
    }

    /**
     * getter for eventsLogger
     * @return
     */
    public List<EventBroker> getEventsLogger() {
        return eventsLogger;
    }

    /**
     * Command logger getter
     * @return
     */
    public List<Command> getCommandsLogger() {
        return commandsLogger;
    }

    /**
     * overridden update method that will invoke processing an event broker
     * @param eventBroker
     */



    @Override
    public void update(EventBroker eventBroker){
        ProcessEventBroker(eventBroker);
    }

    /**
     * This method will take the eventBroker and find the proper rule, and trigger the command based on the sensor type and sensor read.
     * The rules logic can be found here and inside the command implementation
     * @param eventBroker
     */
    public void ProcessEventBroker(EventBroker eventBroker){
        //add event to eventsLogger list
        eventsLogger.add(eventBroker);

        String sensorRead = eventBroker.getEvent().getAction();
        switch (eventBroker.getEvent().getType()){
            case camera:
                if (sensorRead.contains("emergency")){
                    Command command = new EmergencyCmd();
                    command.execute(eventBroker);
                    commandsLogger.add(command);
                } else if (sensorRead.contains("throws garbage on ground")){
                    Command command = new LitterEventCmd();
                    command.execute(eventBroker);
                    commandsLogger.add(command);
                } else if (sensorRead.contains("person") && sensorRead.contains("seen")){
                    Command command = new PersonSeenCmd();
                    command.execute(eventBroker);
                    commandsLogger.add(command);
                } else if (sensorRead.contains("vehicle") && sensorRead.contains("parked")){
                    Command command = new ParkingEventCmd();
                    command.execute(eventBroker);
                    commandsLogger.add(command);
                } else if (sensorRead.contains("boards bus")){
                    Command command = new BoardBusCmd();
                    command.execute(eventBroker);
                    commandsLogger.add(command);
                }
                break;
            case microphone:
                if (sensorRead.contains("sound of breaking glass")){
                    Command command = new BrokenGlassCmd();
                    command.execute(eventBroker);
                    commandsLogger.add(command);
                } else if(sensorRead.contains("find my child")){
                    Command command = new MissingChildCmd();
                    command.execute(eventBroker);
                    commandsLogger.add(command);
                } else if (sensorRead.contains("what movies are showing tonight")){
                    Command command = new MovieInfoCmd();
                    command.execute(eventBroker);
                    commandsLogger.add(command);
                } else if (sensorRead.contains("reserve") && sensorRead.contains("seats")){
                    Command command = new MovieReservationCmd();
                    command.execute(eventBroker);
                    commandsLogger.add(command);
                } else if (sensorRead.contains("does this bus go")){
                    Command command = new BusRouteCmd();
                    command.execute(eventBroker);
                    commandsLogger.add(command);
                }
                break;
            case CO2Meter:
                if(sensorRead.contains("CO2 level")){
                    Command command = new Co2EventCmd();
                    command.execute(eventBroker);
                    commandsLogger.add(command);
                }
                break;
        }

    }
}
