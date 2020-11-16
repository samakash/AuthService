package cscie97.smartcity.controller.commands;

import cscie97.smartcity.authentication.AuthenticationService;
import cscie97.smartcity.authentication.domain.*;
import cscie97.smartcity.ledger.LedgerService;
import cscie97.smartcity.model.observer.EventBroker;
import cscie97.smartcity.model.domain.Device;
import cscie97.smartcity.model.domain.InformationKiosk;
import cscie97.smartcity.model.service.ModelService;
import cscie97.smartcity.model.utils.SmartCityUtils;

/**
 * This command implementation class is for Movie Info command
 */
public class MovieInfoCmd implements Command {

    ModelService modelService;
    LedgerService ledgerService;

    /**
     * constructor of Movie Info command
     */
    public MovieInfoCmd() {
        this.modelService = ModelService.getInstance();
        this.ledgerService = LedgerService.getInstance();
    }

    /**
     * this method will execute Movie Info command if rules are applicable
     * @param eventBroker
     */
    public void execute(EventBroker eventBroker){
        AuthenticationService authenticationService = AuthenticationService.getInstance();
        AuthToken authToken = authenticationService.login("controller","controller");
        Device device = (Device) modelService.showDevice(authToken.getAuthValue(),eventBroker.getCityId(), eventBroker.getDeviceId());
        try{
            if(device instanceof InformationKiosk){
                System.out.println("Controller Processing movie info command");
                //send speaker output to device

                //get user credentials and use it to authenticate, then use the authToken in the model service.
                authenticationService = AuthenticationService.getInstance();
                String userId = eventBroker.getEvent().getSubject().getId();
                Credential userCredential = authenticationService.getUsersMap().get(userId).getCredentials().get(0);
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

                modelService.createSensorOutput(authToken.getAuthValue(),eventBroker.getCityId(),eventBroker.getDeviceId(),"speaker",
                        "Casablanca is showing at 9 pm");
                //show new url to kiosk
                authToken = authenticationService.login("controller","controller");
                modelService.updateInfoKiosk(authToken.getAuthValue(),eventBroker.getCityId(), device.getId(),device.getAccountAddress(),true,
                        "https://en.wikipedia.org/wiki/Casablanca_(film)#/media/File:CasablancaPoster-Gold.jpg",
                        ((InformationKiosk) device).getRedirectingURL());
            }
        } catch (Exception e){
            System.out.println(e);
        }


    }
}
