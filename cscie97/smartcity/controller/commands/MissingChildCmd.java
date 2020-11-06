package cscie97.smartcity.controller.commands;

import cscie97.smartcity.controller.ControllerException;
import cscie97.smartcity.ledger.LedgerService;
import cscie97.smartcity.model.observer.EventBroker;
import cscie97.smartcity.model.domain.*;
import cscie97.smartcity.model.service.ModelService;
import cscie97.smartcity.model.utils.SmartCityUtils;

/**
 * This command implementation class is for Missing Child command
 */
public class MissingChildCmd implements Command {

    ModelService modelService;
    LedgerService ledgerService;

    /**
     * constructor of Missing Child command
     */
    public MissingChildCmd() {
        this.modelService = ModelService.getInstance();
        this.ledgerService = LedgerService.getInstance();
    }

    /**
     * this method will execute Missing Child command if rules are applicable
     * @param eventBroker
     */
    public void execute(EventBroker eventBroker){
        System.out.println("Controller processing missing child command");
        //get model service

        //get missing child location
        Object person = modelService.showPerson("", eventBroker.getCityId(), eventBroker.getEvent().getSubject().getId());

        //send speaker command
        if(person instanceof Resident){
            Resident resident = (Resident) person;
            if(resident.getRole().equals(Role.child)){
                modelService.createSensorOutput("", eventBroker.getCityId(), eventBroker.getDeviceId(),"speaker",
                        "person "+resident.getId()+" is at lat "+resident.getLocation().getLatitude()+
                                " long "+resident.getLocation().getLongitude()+", a robot is retrieving now, stay where you are.");
                //send nearest robot to do task
                Robot robot = SmartCityUtils.getNearestRobot(eventBroker.getCityId(), eventBroker.getLocation());
                modelService.updateRobot("", eventBroker.getCityId(), robot.getId(), robot.getAccountAddress(), robot.getLocation().getLatitude(),
                        robot.getLocation().getLongitude(),true,
                        "retrieve person "+ ((Person)person).getId()+" and bring to lat "+eventBroker.getLocation().getLatitude()+
                                " long "+eventBroker.getLocation().getLongitude());
            } else{
                ControllerException ex = new ControllerException("Missing Child Command",
                        "Person ID doesn't belong to a child. Please provide a person ID that belongs to a child.");
                System.out.println(ex);
            }

        }



    }
}
