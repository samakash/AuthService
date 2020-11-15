package cscie97.smartcity.controller.commands;

import cscie97.smartcity.authentication.AuthenticationService;
import cscie97.smartcity.authentication.domain.*;
import cscie97.smartcity.model.observer.EventBroker;
import cscie97.smartcity.ledger.LedgerService;
import cscie97.smartcity.model.domain.Device;
import cscie97.smartcity.model.domain.InformationKiosk;
import cscie97.smartcity.model.domain.Person;
import cscie97.smartcity.model.domain.Resident;
import cscie97.smartcity.model.service.ModelService;
import cscie97.smartcity.model.utils.SmartCityUtils;

/**
 * This command implementation class is for Movie Reservation command
 */
public class MovieReservationCmd implements Command {

    ModelService modelService;
    LedgerService ledgerService;

    /**
     * constructor of Movie reservation command
     */
    public MovieReservationCmd() {
        this.modelService = ModelService.getInstance();
        this.ledgerService = LedgerService.getInstance();
    }


    /**
     * this method will execute Movie reservation command if rules are applicable
     * @param eventBroker
     */
    public void execute(EventBroker eventBroker){
        AuthenticationService authenticationService = AuthenticationService.getInstance();
        AuthToken authToken = authenticationService.login("controller","controller");
        Device device = (Device) modelService.showDevice(authToken.getAuthValue(),eventBroker.getCityId(), eventBroker.getDeviceId());

        if(device instanceof InformationKiosk){
            System.out.println("Controller Processing movie reservation command");

            //get person and device
            authToken = authenticationService.login("controller","controller");
            Person person = (Person) modelService.showPerson(authToken.getAuthValue(),eventBroker.getCityId(),eventBroker.getEvent().getSubject().getId());
            authToken = authenticationService.login("controller","controller");
            InformationKiosk kiosk = (InformationKiosk) modelService.showDevice(authToken.getAuthValue(),eventBroker.getCityId(),eventBroker.getDeviceId());

            //get total seats required for reservation
            int totalSeats = Integer.parseInt(SmartCityUtils.extractValue(eventBroker.getEvent().getAction(),"reserve"));

            //If the person is a resident and has a positive account balance, charge the person 10 units
            if (person instanceof Resident){
                int balance = ledgerService.getAccountBalance(((Resident) eventBroker.getEvent().getSubject()).getAccountAddress());
                if (balance >= totalSeats*10){
                    ledgerService.processTransaction("t"+SmartCityUtils.getRandomInt(),
                            totalSeats*10,10,"Movie reservation fees",((Resident) person).getAccountAddress(),
                            kiosk.getAccountAddress());
                    //send speaker command

                    //get user credentials and use it to authenticate, then use the authToken in the model service.
                    try{
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
                        modelService.createSensorOutput(authToken.getAuthValue(), eventBroker.getCityId(), eventBroker.getDeviceId(),"speaker",
                                "your seats are reserved; please arrive a few minutes early.");
                    } catch (Exception e){

                    }

                }

            }
        }
    }
}
