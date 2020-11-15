package cscie97.smartcity.controller.commands;

import cscie97.smartcity.authentication.AuthenticationService;
import cscie97.smartcity.authentication.domain.*;
import cscie97.smartcity.ledger.LedgerService;
import cscie97.smartcity.model.observer.EventBroker;
import cscie97.smartcity.model.domain.Device;
import cscie97.smartcity.model.domain.Vehicle;
import cscie97.smartcity.model.domain.VehicleType;
import cscie97.smartcity.model.service.ModelService;
import cscie97.smartcity.model.utils.SmartCityUtils;

/**
 * This command implementation class is for Bus Route command
 */
public class BusRouteCmd implements Command {

    ModelService modelService;
    LedgerService ledgerService;
    AuthenticationService authenticationService;

    /**
     * constructor of Bus Route command
     */
    public BusRouteCmd() {
        this.modelService = ModelService.getInstance();
        this.ledgerService = LedgerService.getInstance();
        this.authenticationService = AuthenticationService.getInstance();

    }

    /**
     * this method will execute Bus Route command if rules are applicable
     * @param eventBroker
     */
    public void execute(EventBroker eventBroker){
        System.out.println("Controller Processing bus route command");
        try {
            if(eventBroker.getEvent().getAction().contains("does this bus go to central square")){
                AuthenticationService authenticationService = AuthenticationService.getInstance();
                AuthToken authToken = authenticationService.login("controller","controller");
                Device device = (Device) modelService.showDevice(authToken.getAuthValue(),eventBroker.getCityId(),eventBroker.getDeviceId());
                if (device instanceof Vehicle && ((Vehicle) device).getVehicleType().equals(VehicleType.bus)){
                    //get user credentials and use it to authenticate, then use the authToken in the model service.
                    String userId = eventBroker.getEvent().getSubject().getId();
                    Credential userCredential = authenticationService.getUserList().get(userId).getCredentials().get(0);
                    if(userCredential instanceof Login){
                        authToken = authenticationService.login(((Login) userCredential).getUsername(),
                                SmartCityUtils.decrypt(((Login) userCredential).getPassword()));
                    }else if(userCredential instanceof FacePrint){
                        authToken = authenticationService.login(userId,
                                SmartCityUtils.decrypt(((FacePrint) userCredential).getFacePrintValue()));
                    } else if (userCredential instanceof VoicePrint){
                        authToken = authenticationService.login(userId,
                                SmartCityUtils.decrypt(((VoicePrint) userCredential).getVoicePrintValue()));
                    }
                    authToken = authenticationService.login("controller","controller");
                    modelService.createSensorOutput(authToken.getAuthValue(), eventBroker.getCityId(), eventBroker.getDeviceId(),"speaker",
                            "Yes, this bus goes to Central Square.");
                }
            }

        } catch (Exception e){

        }
    }
}
