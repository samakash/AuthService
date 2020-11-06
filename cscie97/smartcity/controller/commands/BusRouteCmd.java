package cscie97.smartcity.controller.commands;

import cscie97.smartcity.ledger.LedgerService;
import cscie97.smartcity.model.observer.EventBroker;
import cscie97.smartcity.model.domain.Device;
import cscie97.smartcity.model.domain.Vehicle;
import cscie97.smartcity.model.domain.VehicleType;
import cscie97.smartcity.model.service.ModelService;

/**
 * This command implementation class is for Bus Route command
 */
public class BusRouteCmd implements Command {

    ModelService modelService;
    LedgerService ledgerService;

    /**
     * constructor of Bus Route command
     */
    public BusRouteCmd() {
        this.modelService = ModelService.getInstance();
        this.ledgerService = LedgerService.getInstance();
    }

    /**
     * this method will execute Bus Route command if rules are applicable
     * @param eventBroker
     */
    public void execute(EventBroker eventBroker){
        System.out.println("Controller Processing bus route command");

        if(eventBroker.getEvent().getAction().contains("does this bus go to central square")){
            Device device = (Device) modelService.showDevice("",eventBroker.getCityId(),eventBroker.getDeviceId());
            if (device instanceof Vehicle && ((Vehicle) device).getVehicleType().equals(VehicleType.bus)){
                modelService.createSensorOutput("", eventBroker.getCityId(), eventBroker.getDeviceId(),"speaker",
                        "Yes, this bus goes to Central Square.");
            }

        }
    }
}
