package cscie97.smartcity.controller.commands;

import cscie97.smartcity.ledger.LedgerService;
import cscie97.smartcity.model.observer.EventBroker;
import cscie97.smartcity.model.domain.Robot;
import cscie97.smartcity.model.service.ModelService;
import cscie97.smartcity.model.utils.SmartCityUtils;

/**
 * This command implementation class is for Broken Glass command
 */
public class BrokenGlassCmd implements Command {

    ModelService modelService;
    LedgerService ledgerService;

    /**
     * constructor of Broken Glass command
     */
    public BrokenGlassCmd() {
        this.modelService = ModelService.getInstance();
        this.ledgerService = LedgerService.getInstance();
    }

    /**
     * this method will execute Broken Glass command if rules are applicable
     * @param eventBroker
     */
    public void execute(EventBroker eventBroker){
        System.out.println("Controller processing broken glass command");

        //get model service and devices
        ModelService modelService = ModelService.getInstance();

        //send nearest robot to clean broken glass
        Robot robot = SmartCityUtils.getNearestRobot(eventBroker.getCityId(), eventBroker.getLocation());
        modelService.updateRobot("", eventBroker.getCityId(), robot.getId(), robot.getAccountAddress(), robot.getLocation().getLatitude(),
                robot.getLocation().getLongitude(),true,
                "clean up broken glass lat "+eventBroker.getLocation().getLatitude()+" long "+eventBroker.getLocation().getLongitude());
    }
}
