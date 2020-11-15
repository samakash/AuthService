package cscie97.smartcity.controller.commands;

import cscie97.smartcity.authentication.AuthenticationService;
import cscie97.smartcity.authentication.domain.*;
import cscie97.smartcity.controller.Controller;
import cscie97.smartcity.model.observer.EventBroker;
import cscie97.smartcity.ledger.LedgerService;
import cscie97.smartcity.model.domain.Person;
import cscie97.smartcity.model.domain.Resident;
import cscie97.smartcity.model.domain.Vehicle;
import cscie97.smartcity.model.service.ModelService;
import cscie97.smartcity.model.utils.SmartCityUtils;

import java.util.List;

/**
 * This command implementation class is for Board Bus command
 */
public class BoardBusCmd implements Command {
    ModelService modelService;
    LedgerService ledgerService;
    AuthenticationService authenticationService;

    /**
     * constructor of Board Bus command
     */
    public BoardBusCmd() {
        this.modelService = ModelService.getInstance();
        this.ledgerService = LedgerService.getInstance();
        this.authenticationService = AuthenticationService.getInstance();
    }

    /**
     * this method will execute Board Bus command if rules are applicable
     * @param eventBroker
     */
    public void execute(EventBroker eventBroker){
        try{
            System.out.println("Controller processing board bus command");

            //send speaker command

            //get user credentials and use it to authenticate, then use the authToken in the model service.
            String userId = eventBroker.getEvent().getSubject().getId();
            Credential userCredential = authenticationService.getUserList().get(userId).getCredentials().get(0);
            AuthToken authToken = null;

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
                    "hello, good to see you "+ eventBroker.getEvent().getSubject().getId());

            //If the person is a resident and has a positive account balance, charge persons account for the rate of the bus.
            AuthToken authToken1 = authenticationService.login("controller","controller");
            Person person = (Person) modelService.showPerson(authToken1.getAuthValue(),eventBroker.getCityId(),eventBroker.getEvent().getSubject().getId());
            if (person instanceof Resident){
                Resident resident = (Resident) person;
                authToken1 = authenticationService.login("controller","controller");
                Vehicle vehicle = (Vehicle) modelService.showDevice(authToken1.getAuthValue(),eventBroker.getCityId(),eventBroker.getDeviceId());
                ledgerService.processTransaction("t"+ SmartCityUtils.getRandomInt(), (int)vehicle.getFee(), 10,
                        "Bus ride fee", resident.getAccountAddress(), vehicle.getAccountAddress()
                );

            }
        } catch (Exception e){
            System.out.println(e);
        }

    }

}
