package cscie97.smartcity.controller.commands;

import cscie97.smartcity.authentication.AuthenticationService;
import cscie97.smartcity.authentication.domain.AuthToken;
import cscie97.smartcity.ledger.LedgerService;
import cscie97.smartcity.model.observer.EventBroker;
import cscie97.smartcity.model.domain.Device;
import cscie97.smartcity.model.domain.Vehicle;
import cscie97.smartcity.model.domain.VehicleType;
import cscie97.smartcity.model.service.ModelService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This command implementation class is for Co2 Event command
 */
public class Co2EventCmd implements Command {

    ModelService modelService;
    LedgerService ledgerService;

    /**
     * constructor of Co2 Event command
     */
    public Co2EventCmd() {
        this.modelService = ModelService.getInstance();
        this.ledgerService = LedgerService.getInstance();
    }

    /**
     * this method will execute Co2 Event command if rules are applicable
     * @param eventBroker
     */
    public void execute(EventBroker eventBroker){

        try{
            //get model service and devices
            AuthenticationService authenticationService = AuthenticationService.getInstance();
            AuthToken authToken = authenticationService.login("controller","controller");
            Map<String, Device> devicesMap = modelService.showCity(authToken.getAuthValue(), eventBroker.getCityId()).getDevicesMap();

            //get a list of all cars in the city
            List<Vehicle> allCars = new ArrayList<>();
            for (Map.Entry mapElement : devicesMap.entrySet()){
                Device device = (Device) mapElement.getValue();
                if(device instanceof Vehicle && ((Vehicle)device).getVehicleType()==VehicleType.car){
                    allCars.add((Vehicle) device);
                }
            }
            // disable all cars in the city
            if(eventBroker.getEvent().getAction().contains("level over 1000")){
                int eventCount = 0;
                for (Map.Entry mapElement : devicesMap.entrySet()){
                    try{
                        Device device = (Device) mapElement.getValue();
                        if(device.getLastEvent().getAction().contains("CO2 level over 1000")){
                            eventCount++;
                        }
                    } catch (NullPointerException e){

                    }
                }
                if(eventCount >3){
                    System.out.println("Controller Processing CO2 level over 1000 command");
                    for(Vehicle car : allCars){
                        authToken = authenticationService.login("controller","controller");
                        modelService.updateVehicle(authToken.getAuthValue(), eventBroker.getCityId(), car.getId(), car.getAccountAddress(), car.getLocation().getLatitude(),
                                car.getLocation().getLongitude(),false,"Car is disabled due to high CO2 level", car.getFee());
                    }
                }

            }
            //enable cars in the city
            else if (eventBroker.getEvent().getAction().contains("level under 1000")){
                int eventCount = 0;
                for (Map.Entry mapElement : devicesMap.entrySet()){
                    try{
                        Device device = (Device) mapElement.getValue();
                        if(device.getLastEvent().getAction().contains("CO2 level under 1000")){
                            eventCount++;
                        }
                    } catch (NullPointerException e){

                    }
                }
                if(eventCount >3){
                    System.out.println("Controller Processing CO2 level under 1000 command");
                    for(Vehicle car : allCars){
                        authToken = authenticationService.login("controller","controller");
                        modelService.updateVehicle(authToken.getAuthValue(), eventBroker.getCityId(), car.getId(), car.getAccountAddress(), car.getLocation().getLatitude(),
                                car.getLocation().getLongitude(),true,"Car is enabled due to stable CO2 level", car.getFee());
                    }
                }
            }
        } catch (Exception e){
            System.out.println(e);
        }

    }
}
