package cscie97.smartcity.controller.commands;

import cscie97.smartcity.authentication.AuthenticationService;
import cscie97.smartcity.authentication.domain.AuthToken;
import cscie97.smartcity.model.observer.EventBroker;
import cscie97.smartcity.ledger.LedgerService;
import cscie97.smartcity.model.domain.Resident;
import cscie97.smartcity.model.domain.Robot;
import cscie97.smartcity.model.service.ModelService;
import cscie97.smartcity.model.utils.SmartCityUtils;

/**
 * This command implementation class is for Litter Event command
 */
public class LitterEventCmd implements Command {

    ModelService modelService;
    LedgerService ledgerService;

    /**
     * constructor of Litter Event command
     */
    public LitterEventCmd() {
        this.modelService = ModelService.getInstance();
        this.ledgerService = LedgerService.getInstance();
    }

    /**
     * this method will execute Litter event command if rules are applicable
     * @param eventBroker
     */
    public void execute(EventBroker eventBroker){
        try{
            //send speak command
            System.out.println("Controller processing litter event command");
            AuthenticationService authenticationService = AuthenticationService.getInstance();
            AuthToken authToken = authenticationService.login("controller","controller");
            modelService.createSensorOutput(authToken.getAuthValue(), eventBroker.getCityId(), eventBroker.getDeviceId(),"speaker",
                    "Please do not litter");

            //send nearest robot to location to clean garbage
            Robot robot = SmartCityUtils.getNearestRobot(eventBroker.getCityId(), eventBroker.getLocation());
            authToken = authenticationService.login("controller","controller");
            modelService.updateRobot(authToken.getAuthValue(), eventBroker.getCityId(), robot.getId(), robot.getAccountAddress(), robot.getLocation().getLatitude(),
                    robot.getLocation().getLongitude(),true,
                    "clean garbage at lat "+eventBroker.getLocation().getLatitude()+" long "+eventBroker.getLocation().getLongitude());

            //charge resident 50 unites
            //ledger logic here
            if (eventBroker.getEvent().getSubject() instanceof Resident){
                ledgerService.processTransaction("t"+SmartCityUtils.getRandomInt(), 50, 10,"Fine for littering garbage",
                        (((Resident) eventBroker.getEvent().getSubject()).getAccountAddress()),"master");
            }
        } catch (Exception e){
            System.out.println(e);
        }

    }


}
