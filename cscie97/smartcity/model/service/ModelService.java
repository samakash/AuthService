package cscie97.smartcity.model.service;

import cscie97.smartcity.model.observer.EventBroker;
import cscie97.smartcity.model.observer.Subject;
import cscie97.smartcity.model.domain.*;

public interface ModelService extends Subject {

    static ModelServiceImpl getInstance(){
        return ModelServiceImpl.getInstance();
    }

    /**
     * This method is used to create a new city and add it to the cities map of th service.
     * @param auth_token auth token will be used in the controller implementation
     * @param cityId city ID
     * @param city_name City name
     * @param accountAddress city blockchain account addresss
     * @param lat location lat
     * @param _long location long
     * @param radius city radius
     * @return City
     */
    City createCity(String auth_token, String cityId, String city_name, String accountAddress, float lat, float _long, float radius);

    /**
     * This method is used to show a city contents if found.
     * @param auth_token auth token will be used in the controller implementation
     * @param city_id city ID
     * @return
     */
    City showCity(String auth_token, String city_id);

    /**
     * This method is used to create a street sign in a city
     * @param auth_token auth token will be used in the controller implementation
     * @param city_id city id
     * @param deviceId device id
     * @param accountAddress blockchain account address
     * @param lat location lat
     * @param _long location long
     * @param enabled device enabled status
     * @param displayText text to be displayed at the street sign
     * @return StreetSign
     */
    StreetSign createStreetSign(String auth_token, String city_id, String deviceId, String accountAddress,
                                       float lat, float _long, boolean enabled, String displayText);

    /**
     * Method used to update an existing street sign
     * @param auth_token auth token will be used in the controller implementation
     * @param city_id city id
     * @param deviceId device id
     * @param accountAddress blockchain account address of the device
     * @param enabled enabled value
     * @param displayText text to be displayed at the street sign
     */
    void updateStreetSign(String auth_token, String city_id, String deviceId, String accountAddress, boolean enabled, String displayText) ;

    /**
     * This method is used to create an information kiosk in a city
     * @param auth_token auth token will be used in the controller implementation
     * @param city_id city id
     * @param deviceId device id
     * @param accountAddress blockchain account address
     * @param lat location lat
     * @param _long location long
     * @param enabled device enabled status
     * @param imgUrl image url to be displayed at kiosk screen
     * @param redirectingURL ticketing or event website for selling tickets.
     * @return StreetSign
     */
    InformationKiosk createInfoKiosk(String auth_token, String city_id, String deviceId, String accountAddress,
                                            float lat, float _long, boolean enabled, String imgUrl, String redirectingURL ) ;

    /**
     * method used to update an existing info-kiosk in a city
     * @param auth_token auth token will be used in the controller implementation
     * @param city_id city id
     * @param deviceId device id
     * @param accountAddress blockchain account address
     * @param enabled device enabled status
     * @param imgUrl image url to be displayed at kiosk screen
     * @param redirectingURL ticketing or event website for selling tickets.
     */
    void updateInfoKiosk(String auth_token, String city_id, String deviceId,String accountAddress, boolean enabled, String imgUrl,
                                String redirectingURL) ;

    /**
     * This method is used to create a street light in a city
     * @param auth_token auth token will be used in the controller implementation
     * @param city_id city id
     * @param deviceId device id
     * @param accountAddress blockchain account address
     * @param lat location lat
     * @param _long location long
     * @param enabled device enabled status
     * @param brightness light brightness level
     * @return StreetLight
     */
    StreetLight createStreetLight(String auth_token, String city_id, String deviceId, String accountAddress,
                                         float lat, float _long, boolean enabled, int brightness);

    /**
     * method used to update an existing Street Light in a city
     * @param auth_token auth token will be used in the controller implementation
     * @param city_id city id
     * @param deviceId device id
     * @param accountAddress blockchain account address
     * @param enabled device enabled status
     * @param brightness light brightness level
     */
    void updateStreetLight(String auth_token, String city_id, String deviceId, String accountAddress, boolean enabled, int brightness);

    /**
     * This method is used to create a Robot in a city
     * @param auth_token auth token will be used in the controller implementation
     * @param city_id city id
     * @param deviceId device id
     * @param accountAddress blockchain account address
     * @param lat location lat
     * @param _long location long
     * @param enabled device enabled status
     * @param activity robot activity
     * @return Robot
     */
    Robot createRobot(String auth_token, String city_id, String deviceId, String accountAddress,
                             float lat, float _long, boolean enabled, String activity);

    /**
     * This method is used to update an existing Robot in a city
     * @param auth_token auth token will be used in the controller implementation
     * @param city_id city id
     * @param deviceId device id
     * @param accountAddress blockchain account address
     * @param lat location lat
     * @param _long location long
     * @param enabled device enabled status
     * @param activity robot activity
     */
    void updateRobot(String auth_token, String city_id, String deviceId, String accountAddress,float lat, float _long, boolean enabled, String activity) ;

    /**
     * This method is used to create a new parking space in a city
     * @param auth_token auth token will be used in the controller implementation
     * @param city_id city id
     * @param deviceId device id
     * @param accountAddress blockchain account address
     * @param lat location lat
     * @param _long location long
     * @param enabled device enabled status
     * @param hourlyRate hourly rate for this parking space
     * @return
     */
    ParkingSpace createParkingSpace(String auth_token, String city_id, String deviceId, String accountAddress,
                                           float lat, float _long, boolean enabled, double hourlyRate) ;

    /**
     * This method is used to update an existing new parking space in a city
     * @param auth_token auth token will be used in the controller implementation
     * @param city_id city id
     * @param deviceId device id
     * @param accountAddress blockchain account address
     * @param enabled device enabled status
     * @param hourlyRate hourly rate for this parking space
     */
    void updateParkingSpace(String auth_token, String city_id, String deviceId, String accountAddress, boolean enabled, double hourlyRate) ;

    /**
     * This method is used to create a new vehicle in a city
     * @param auth_token auth token will be used in the controller implementation
     * @param city_id city id
     * @param deviceId device id
     * @param accountAddress blockchain account address
     * @param lat location lat
     * @param _long location long
     * @param enabled device enabled status
     * @param type type of the vehicle
     * @param capacity capacity of the vehicle
     * @param fee fee of riding the vehicle
     * @return Vehicle
     */
    Vehicle createVehicle(String auth_token, String city_id, String deviceId, String accountAddress,
                                 float lat, float _long, boolean enabled, String type, int capacity, double fee);

    /**
     * This method is used to update a vehicle in a city
     * @param auth_token auth token will be used in the controller implementation
     * @param city_id city id
     * @param deviceId device id
     * @param accountAddress blockchain account address
     * @param lat location lat
     * @param _long location long
     * @param enabled device enabled status
     * @param fee fee of riding the vehicle
     */
    void updateVehicle(String auth_token, String city_id, String deviceId, String accountAddress,
                              float lat, float _long, boolean enabled, String activity, double fee) ;

    /**
     * This method is for showing a device in a city. If device not found, method will return all devices in that city.
     * @param auth_token auth token will be used in the controller implementation
     * @param city_id city id
     * @param device_id device id
     * @return
     */
    Object showDevice(String auth_token, String city_id, String device_id);

    /**
     * this method is to simulate a device sensor event
     * @param auth_token auth token will be used in the controller implementation
     * @param city_id city id
     * @param device_id device id
     * @param sensorType type of the sensor
     * @param action action captured by the sensor
     * @param subjectId person Id
     * @return Event
     */
    Event createSensorEvent(String auth_token, String city_id, String device_id, String sensorType, String action, String subjectId) ;

    /**
     * Method for sending a device output
     * @param auth_token auth token will be used in the controller implementation
     * @param city_id city id
     * @param device_id device id
     * @param sensorType type of the sensor
     * @param action action captured by the sensor
     * @return Event
     */
    Event createSensorOutput(String auth_token, String city_id, String device_id, String sensorType, String action) ;

    /**
     * This method is for creating a new resident in a city.
     * @param auth_token auth token will be used in the controller implementation
     * @param city_id city id
     * @param personId person id
     * @param name person name
     * @param bioId biometric id
     * @param phone person phone
     * @param role person role
     * @param lat location lat
     * @param _long location long
     * @param accountAddress person block-chain account address
     * @return Resident
     */
    Resident createResident(String auth_token, String city_id, String personId, String name, String bioId, String phone, String role, float lat,
                                   float _long, String accountAddress);

    /**
     * This method is for updating a resident in a city.
     * @param auth_token auth token will be used in the controller implementation
     * @param city_id city id
     * @param personId person id
     * @param name person name
     * @param bioId biometric id
     * @param phone person phone
     * @param role person role
     * @param lat location lat
     * @param _long location long
     * @param accountAddress person block-chain account address
     */
    void updateResident(String auth_token, String city_id, String personId, String name, String bioId, String phone, String role, float lat,
                               float _long, String accountAddress) ;

    /**
     * This method is for creating a new visitor of a city
     * @param auth_token auth token will be used in the controller implementation
     * @param city_id city id
     * @param personId person id
     * @param bioId biometric id
     * @param lat location lat
     * @param _long location long
     * @return
     */
    Visitor createVisitor(String auth_token, String city_id, String personId, String bioId, float lat, float _long);

    /**
     * This method is for updating a visitor of a city
     * @param auth_token auth token will be used in the controller implementation
     * @param city_id city id
     * @param personId person id
     * @param bioId biometric id
     * @param lat location lat
     * @param _long location long
     */
    void updateVisitor(String auth_token, String city_id, String personId, String bioId, float lat, float _long) ;

    /**
     * This method is used show person
     * @param auth_token auth token will be used in the controller implementation
     * @param city_id city id
     * @param person_id person id
     * @return Person
     */
    Object showPerson(String auth_token, String city_id, String person_id);




}
