package cscie97.smartcity.controller.commands;

import cscie97.smartcity.authentication.AuthenticationService;
import cscie97.smartcity.authentication.domain.AuthToken;
import cscie97.smartcity.controller.ControllerException;
import cscie97.smartcity.ledger.LedgerService;
import cscie97.smartcity.model.observer.EventBroker;
import cscie97.smartcity.model.domain.Device;
import cscie97.smartcity.model.domain.Robot;
import cscie97.smartcity.model.service.ModelService;
import cscie97.smartcity.model.utils.SmartCityUtils;
import java.util.LinkedList;
import java.util.Map;

/**
 * This command implementation class is for Emergency command
 */
public class EmergencyCmd implements Command {

    ModelService modelService;
    LedgerService ledgerService;
    AuthenticationService authenticationService;

    /**
     * constructor of Emergency command
     */
    public EmergencyCmd() {
        this.modelService = ModelService.getInstance();
        this.ledgerService = LedgerService.getInstance();
        this.authenticationService = AuthenticationService.getInstance();
    }

    /**
     * this method will execute Emergency command if rules are applicable
     * @param eventBroker
     */
    public void execute(EventBroker eventBroker){
        Map<String, Device> devicesMap = modelService.showCity("", eventBroker.getCityId()).getDevicesMap();

        //get emergency type
        try{
            EmergencyType emergencyType = EmergencyType.valueOf(SmartCityUtils.extractValue(eventBroker.getEvent().getAction(),"emergency")) ;
            if(emergencyType.equals(EmergencyType.traffic_accident)){
                System.out.println("Controller Processing EmergencyCmd Command: "+emergencyType);
                //send command: announce: "Stay calm, help is on its way"
                AuthToken authToken = authenticationService.login("controller","controller");
                modelService.createSensorOutput(authToken.getAuthValue(),eventBroker.getCityId(), eventBroker.getDeviceId(),
                        "speaker","Stay calm, help is on its way");
                //get a list of all robots
                LinkedList<Robot> allRobots = new LinkedList<>();
                for (Map.Entry mapElement : devicesMap.entrySet()){
                    Device device = (Device) mapElement.getValue();
                    if(device instanceof Robot){
                        allRobots.add((Robot) device);
                    }
                }
                //get the nearest two robots
                Robot nearestRobot = allRobots.get(0);
                Robot secondNearestRobot = allRobots.get(0);

                for(Robot robot : allRobots){
                    if(SmartCityUtils.calculateDistance(robot.getLocation(),eventBroker.getLocation()) <
                            SmartCityUtils.calculateDistance(nearestRobot.getLocation(),eventBroker.getLocation())){
                        secondNearestRobot = nearestRobot;
                        nearestRobot = robot;
                    } else if (SmartCityUtils.calculateDistance(robot.getLocation(),eventBroker.getLocation()) <
                            SmartCityUtils.calculateDistance(secondNearestRobot.getLocation(),eventBroker.getLocation())){
                        secondNearestRobot = robot;
                    }
                }

                //send commands to the nearest two robots
                String commandValue = "address "+emergencyType+" at lat "+eventBroker.getLocation().getLatitude()+" long "+
                        eventBroker.getLocation().getLongitude();
                authToken = authenticationService.login("controller","controller");
                modelService.updateRobot(authToken.getAuthValue(), eventBroker.getCityId(), nearestRobot.getId(), nearestRobot.getAccountAddress(),
                        nearestRobot.getLocation().getLatitude(), nearestRobot.getLocation().getLongitude(), true,
                        commandValue);
                authToken = authenticationService.login("controller","controller");
                modelService.updateRobot(authToken.getAuthValue(), eventBroker.getCityId(), secondNearestRobot.getId(), secondNearestRobot.getAccountAddress(),
                        secondNearestRobot.getLocation().getLatitude(), secondNearestRobot.getLocation().getLongitude(), true,
                        commandValue);
            } else {
                System.out.println("Controller Processing EmergencyCmd Command: "+emergencyType);
                // send speaker command to all devices
                for (Map.Entry mapElement : devicesMap.entrySet()){
                    Device device = (Device) mapElement.getValue();
                    AuthToken authToken = authenticationService.login("controller","controller");
                    modelService.createSensorOutput(authToken.getAuthValue(),eventBroker.getCityId(), device.getId(),
                            "speaker","There is a "+emergencyType+" in "+eventBroker.getCityId()+", please find shelter immediately");

                }
                //send half robots to handle emergency and the other half to help people take shelter
                LinkedList<Robot> allRobots = new LinkedList<>();
                for (Map.Entry mapElement : devicesMap.entrySet()){
                    Device device = (Device) mapElement.getValue();
                    if(device instanceof Robot){
                        allRobots.add((Robot) device);
                    }
                }
                int robotsCount = allRobots.size();
                for(int i =0; i< (robotsCount/2); i++){
                    Robot robot = allRobots.get(i);
                    AuthToken authToken = authenticationService.login("controller","controller");
                    modelService.updateRobot(authToken.getAuthValue(), eventBroker.getCityId(), robot.getId(), robot.getAccountAddress(),
                            robot.getLocation().getLatitude(),robot.getLocation().getLongitude(),true,
                            "address "+emergencyType+" at lat "+eventBroker.getLocation().getLatitude()+" long "+
                            eventBroker.getLocation().getLongitude());
                }

                for(int i = robotsCount/2; i<=robotsCount-1; i++){
                    Robot robot = allRobots.get(i);
                    AuthToken authToken = authenticationService.login("controller","controller");
                    modelService.updateRobot(authToken.getAuthValue(), eventBroker.getCityId(), robot.getId(), robot.getAccountAddress(),
                            robot.getLocation().getLatitude(),robot.getLocation().getLongitude(),true,
                            "Help people find shelter");
                }

            }
        } catch (IllegalArgumentException e){
            ControllerException ex = new ControllerException("Controller EmergencyCmd command","emergency type is not supported");
            System.out.println(ex);
        }


    }






}
