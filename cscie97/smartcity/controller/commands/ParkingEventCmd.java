package cscie97.smartcity.controller.commands;

import cscie97.smartcity.authentication.AuthenticationService;
import cscie97.smartcity.authentication.domain.AuthToken;
import cscie97.smartcity.model.observer.EventBroker;
import cscie97.smartcity.ledger.LedgerService;
import cscie97.smartcity.model.domain.Device;
import cscie97.smartcity.model.domain.ParkingSpace;
import cscie97.smartcity.model.domain.Vehicle;
import cscie97.smartcity.model.service.ModelService;
import cscie97.smartcity.model.utils.SmartCityUtils;

/**
 * This command implementation class is for Parking Event command
 */
public class ParkingEventCmd implements Command {

    ModelService modelService;
    LedgerService ledgerService;

    /**
     * constructor of Parking event command
     */
    public ParkingEventCmd() {
        this.modelService = ModelService.getInstance();
        this.ledgerService = LedgerService.getInstance();
    }

    /**
     * this method will execute Parking event command if rules are applicable
     * @param eventBroker
     */
    public void execute(EventBroker eventBroker){
        AuthenticationService authenticationService = AuthenticationService.getInstance();
        AuthToken authToken = authenticationService.login("controller","controller");
        Device device = (Device) modelService.showDevice(authToken.getAuthValue(),eventBroker.getCityId(), eventBroker.getDeviceId());
        if(device instanceof ParkingSpace){
            System.out.println("Controller Processing parking event command");

            String vehicleId = SmartCityUtils.extractValue(eventBroker.getEvent().getAction(),"vehicle");
            int hoursParked = Integer.parseInt(SmartCityUtils.extractValue(eventBroker.getEvent().getAction(),"for"));
            authToken = authenticationService.login("controller","controller");
            Vehicle vehicle =((Vehicle) modelService.showDevice(authToken.getAuthValue(),eventBroker.getCityId(),vehicleId));
            authToken = authenticationService.login("controller","controller");
            ParkingSpace parkingSpace =((ParkingSpace) modelService.showDevice(
                    authToken.getAuthValue(),eventBroker.getCityId(), eventBroker.getDeviceId()));
            int totalAmount = (int) parkingSpace.getHourlyRate()*hoursParked;
            ledgerService.processTransaction("t"+SmartCityUtils.getRandomInt(),totalAmount
                    ,10,"Parking space fees ",vehicle.getAccountAddress(), parkingSpace.getAccountAddress());
        }
    }
}
