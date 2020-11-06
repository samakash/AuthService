package cscie97.smartcity.model.utils;

import cscie97.smartcity.model.domain.Device;
import cscie97.smartcity.model.domain.Location;
import cscie97.smartcity.model.domain.Robot;
import cscie97.smartcity.model.service.ModelService;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

/**
 * this class is utility class that provides a set of tools for the developer of smart city
 */
public class SmartCityUtils {
    /**
     * Method used to calculate distance between any two locations
     * @return double value of distance
     */
    public static double calculateDistance(Location location1, Location location2) {
        float lat1 = location1.getLatitude();
        float lon1 = location1.getLongitude();
        float lat2 = location2.getLatitude();
        float lon2 = location2.getLongitude();

        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        } else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1))
                    * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            return dist;

        }
    }

    /**
     * This method is used to extract a certain value from the string
     * @param command command string value
     * @param value desired value to be extracted
     * @return String of the extracted value form the command line
     */
    public static String extractValue(String command, String value){
        String extractVal = Arrays.asList(command.replaceAll("^.*?"+value+" ", "")
                .split(" .*?("+value+"|$)")).get(0);
        Character firstChar = extractVal.charAt(0);
        if (firstChar.equals('\"')){
            String extractSentence = "";
            for (int i= command.indexOf(value)+value.length()+2; i<command.length(); i++){
                if(command.charAt(i) == '\"') break;
                extractSentence+=command.charAt(i);
            }
            return extractSentence;
        } else{
            return extractVal;
        }

    }

    /**
     * This method will find the nearest available robot in a specific city
     * @param cityId city id
     * @param location the location of request
     * @return
     */
    public static Robot getNearestRobot(String cityId, Location location){
        Robot nearestRobot = null;
        ModelService modelService = ModelService.getInstance();
        Map<String, Device> devicesMap = modelService.showCity("", cityId).getDevicesMap();

        LinkedList<Robot> allRobots = new LinkedList<>();
        for (Map.Entry mapElement : devicesMap.entrySet()){
            Device device = (Device) mapElement.getValue();
            if(device instanceof Robot){
                allRobots.add((Robot) device);
            }
        }
        //get the nearest robot
        nearestRobot = allRobots.get(0);

        for(Robot robot : allRobots){
            if(SmartCityUtils.calculateDistance(robot.getLocation(),location) <
                    SmartCityUtils.calculateDistance(nearestRobot.getLocation(),location)){
                nearestRobot = robot;
            }
        }

        return nearestRobot;
    }

    public static int getRandomInt(){
        Random rand = new Random(); //instance of random class
        int upperbound = 9999;
        //generate random values from 0-24
        int int_random = rand.nextInt(upperbound);
        return int_random;
    }

}
