package cscie97.smartcity.controller.commands;

import cscie97.smartcity.controller.Controller;
import cscie97.smartcity.model.observer.EventBroker;
import cscie97.smartcity.ledger.LedgerService;
import cscie97.smartcity.model.domain.Person;
import cscie97.smartcity.model.domain.Resident;
import cscie97.smartcity.model.domain.Vehicle;
import cscie97.smartcity.model.service.ModelService;
import cscie97.smartcity.model.utils.SmartCityUtils;

/**
 * This command implementation class is for Board Bus command
 */
public class BoardBusCmd implements Command {
    ModelService modelService;
    LedgerService ledgerService;

    /**
     * constructor of Board Bus command
     */
    public BoardBusCmd() {
        this.modelService = ModelService.getInstance();
        this.ledgerService = LedgerService.getInstance();
    }

    /**
     * this method will execute Board Bus command if rules are applicable
     * @param eventBroker
     */
    public void execute(EventBroker eventBroker){

        System.out.println("Controller processing board bus command");

        //send speaker command
        modelService.createSensorOutput("", eventBroker.getCityId(), eventBroker.getDeviceId(),"speaker",
                "hello, good to see you "+ eventBroker.getEvent().getSubject().getId());

        //If the person is a resident and has a positive account balance, charge persons account for the rate of the bus.
        Person person = (Person) modelService.showPerson("",eventBroker.getCityId(),eventBroker.getEvent().getSubject().getId());
        if (person instanceof Resident){
            Resident resident = (Resident) person;
            Vehicle vehicle = (Vehicle) modelService.showDevice("",eventBroker.getCityId(),eventBroker.getDeviceId());
            ledgerService.processTransaction("t"+ SmartCityUtils.getRandomInt(), (int)vehicle.getFee(), 10,
                    "Bus ride fee", resident.getAccountAddress(), vehicle.getAccountAddress()
                    );

        }

    }

}
