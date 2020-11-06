package cscie97.smartcity.controller.commands;

import cscie97.smartcity.ledger.LedgerService;
import cscie97.smartcity.model.observer.EventBroker;
import cscie97.smartcity.model.domain.Device;
import cscie97.smartcity.model.domain.InformationKiosk;
import cscie97.smartcity.model.service.ModelService;

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
        Device device = (Device) modelService.showDevice("",eventBroker.getCityId(), eventBroker.getDeviceId());

        if(device instanceof InformationKiosk){
            System.out.println("Controller Processing movie info command");
            //send speaker output to device
            modelService.createSensorOutput("",eventBroker.getCityId(),eventBroker.getDeviceId(),"speaker",
                    "Casablanca is showing at 9 pm");
            //show new url to kiosk
            modelService.updateInfoKiosk("",eventBroker.getCityId(), device.getId(),device.getAccountAddress(),true,
                    "https://en.wikipedia.org/wiki/Casablanca_(film)#/media/File:CasablancaPoster-Gold.jpg",
                    ((InformationKiosk) device).getRedirectingURL());
        }

    }
}
