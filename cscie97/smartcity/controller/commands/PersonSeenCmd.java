package cscie97.smartcity.controller.commands;

import cscie97.smartcity.authentication.AuthenticationService;
import cscie97.smartcity.authentication.domain.AuthToken;
import cscie97.smartcity.ledger.LedgerService;
import cscie97.smartcity.model.observer.EventBroker;
import cscie97.smartcity.model.domain.Resident;
import cscie97.smartcity.model.domain.Visitor;
import cscie97.smartcity.model.service.ModelService;

/**
 * This command implementation class is for Person Seen command
 */
public class PersonSeenCmd implements Command {

    ModelService modelService;
    LedgerService ledgerService;

    /**
     * constructor of Person Seen command
     */
    public PersonSeenCmd() {
        this.modelService = ModelService.getInstance();
        this.ledgerService = LedgerService.getInstance();
    }

    /**
     * this method will execute Person Seen command if rules are applicable
     * @param eventBroker
     */
    public void execute(EventBroker eventBroker){
        System.out.println("Controller processing person seen command");
        System.out.println("here"+eventBroker);
        //get model service

        //update person location
        AuthenticationService authenticationService = AuthenticationService.getInstance();
        AuthToken authToken = authenticationService.login("controller","controller");

        Object person = modelService.showPerson(authToken.getAuthValue(), eventBroker.getCityId(), eventBroker.getEvent().getSubject().getId());


        if(person instanceof Resident){
            Resident resident = (Resident) person;
            authToken = authenticationService.login("controller","controller");
            modelService.updateResident(authToken.getAuthValue(), eventBroker.getCityId(), resident.getId(), resident.getName(), resident.getBiometricId(),
                    resident.getPhone(), resident.getRole().toString(), eventBroker.getLocation().getLatitude(), eventBroker.getLocation().getLongitude(),
                    resident.getAccountAddress());
        } else if (person instanceof Visitor){
            Visitor visitor = (Visitor) person;
            authToken = authenticationService.login("controller","controller");
            modelService.updateVisitor(authToken.getAuthValue(), eventBroker.getCityId(), visitor.getId(),visitor.getId(),
                    eventBroker.getLocation().getLatitude(), eventBroker.getLocation().getLongitude());
        }




    }
}
