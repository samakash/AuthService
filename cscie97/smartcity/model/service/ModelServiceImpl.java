package cscie97.smartcity.model.service;

import cscie97.smartcity.authentication.AuthenticationService;
import cscie97.smartcity.model.observer.EventBroker;
import cscie97.smartcity.model.observer.Observer;
import cscie97.smartcity.model.observer.ObserverImpl;
import cscie97.smartcity.model.observer.SubjectImpl;
import cscie97.smartcity.model.domain.*;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is the implementation for ModelService Interface
 */
public class ModelServiceImpl extends SubjectImpl implements ModelService {

	private Map<String,City> citiesMap;
	private static ModelServiceImpl instance;
	private AuthenticationService authenticationService;

	/**
	 * constructor for modelServiceImpl
	 */
	private ModelServiceImpl() {
		super();
		this.citiesMap = new HashMap<>();
		this.instance = null;
		Observer obs = new ObserverImpl();
		attach(obs);
		authenticationService = AuthenticationService.getInstance();
	}

	/**
	 * ModelService instance getter
	 */
	public static ModelServiceImpl getInstance() {
		if (instance == null){
			instance = new ModelServiceImpl();
		}
		return instance;
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
	public City createCity(String auth_token, String cityId, String city_name, String accountAddress, float lat, float _long, float radius) {
		City newCity = null;
		if(authenticationService.checkAccess(auth_token,"scms_manage_city","")){
			try{
				if(citiesMap.containsKey(cityId)){
					throw new ModelServiceException("Create new city failed","City ID already exist.");
				}else {
					newCity = new City(cityId,city_name,accountAddress,radius,new Location(lat,_long));
					citiesMap.put(cityId,newCity);
					System.out.println("new city "+city_name+" is defined");
				}

			} catch(ModelServiceException e){
				System.out.println(e);
			}

		}
		return newCity;
	}

	/**
	 * This method is used to show a city contents if found.
	 * @param auth_token auth token will be used in the controller implementation
	 * @param city_id city ID
	 * @return
	 */
	public City showCity(String auth_token, String city_id) {
		City city = null;
		if(authenticationService.checkAccess(auth_token,"scms_manage_city","")){
			try{
				if(! citiesMap.containsKey(city_id)){
					throw new ModelServiceException("Show city failed","City ID is not found..");
				}else{
					city = citiesMap.get(city_id);
				}
			} catch(ModelServiceException e){
				System.out.println(e);
			}

		}
		return city;
	}

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
	public StreetSign createStreetSign(String auth_token, String city_id, String deviceId, String accountAddress,
									   float lat, float _long, boolean enabled, String displayText) {
		StreetSign streetSign = null;
		if(authenticationService.checkAccess(auth_token,"scms_manage_device","")){
			try{
				if(! citiesMap.containsKey(city_id)){
					throw new ModelServiceException("create device failed","City ID is not found.");
				} else{
					City city = citiesMap.get(city_id);
					if (city.getDevicesMap().containsKey(deviceId)){
						throw new ModelServiceException("create device failed","device ID already exists.");
					}
					else if(! city.validateLocation(lat,_long)){
						throw new ModelServiceException("Location is invalid","Location is not within the city radius borders");
					}
					else{
						streetSign = new StreetSign(deviceId, enabled, accountAddress, new Location(lat,_long), Status.offline, displayText);
						city.getDevicesMap().put(deviceId, streetSign);
						System.out.println("new street-sign "+deviceId+" is defined");
					}
				}
			} catch (ModelServiceException e){
				System.out.println(e);
			}
		}
		return streetSign;
	}

	/**
	 * Method used to update an existing street sign
	 * @param auth_token auth token will be used in the controller implementation
	 * @param city_id city id
	 * @param deviceId device id
	 * @param accountAddress blockchain account address of the device
	 * @param enabled enabled value
	 * @param displayText text to be displayed at the street sign
	 */
	public void updateStreetSign(String auth_token, String city_id, String deviceId, String accountAddress, boolean enabled, String displayText) {
		if(authenticationService.checkAccess(auth_token,"scms_manage_device","")){
			try{
				if(! citiesMap.containsKey(city_id)){
					throw new ModelServiceException("create device failed","City ID is not found.");
				} else{
					City city = citiesMap.get(city_id);
					if (! city.getDevicesMap().containsKey(deviceId)){
						throw new ModelServiceException("update device failed","device ID is not found within this city.");
					}
					else{
						city = citiesMap.get(city_id);
						StreetSign device = (StreetSign) city.getDevicesMap().get(deviceId);
						device.setAccountAddress(accountAddress);
						device.setEnabled(enabled);
						device.setDisplayText(displayText);
						System.out.println("street-sign "+deviceId+" is updated");
					}
				}

			} catch (ModelServiceException e){
				System.out.println(e);
			}
		}
	}

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
	public InformationKiosk createInfoKiosk(String auth_token, String city_id, String deviceId, String accountAddress,
											float lat, float _long, boolean enabled, String imgUrl, String redirectingURL ) {
		InformationKiosk informationKiosk = null;
		if(authenticationService.checkAccess(auth_token,"scms_manage_device","")){
			try{
				if(! citiesMap.containsKey(city_id)){
					throw new ModelServiceException("create device failed","City ID is not found.");
				} else {
					City city = citiesMap.get(city_id);
					if (city.getDevicesMap().containsKey(deviceId)){
						throw new ModelServiceException("create device failed","device ID already exists.");
					}
					else if (!city.validateLocation(lat, _long)) {
						throw new ModelServiceException("Location is invalid", "Location is not within the city radius borders");
					} else {
						informationKiosk = new InformationKiosk(deviceId, enabled, accountAddress, new Location(lat, _long),
								Status.offline, imgUrl, redirectingURL);
						city.getDevicesMap().put(deviceId, informationKiosk);
						System.out.println("new info-kiosk " + deviceId + " is defined");
					}
				}
			} catch (ModelServiceException e){
				System.out.println(e);
			}
		}

		return informationKiosk;
	}

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
	public void updateInfoKiosk(String auth_token, String city_id, String deviceId,String accountAddress, boolean enabled, String imgUrl,
								String redirectingURL) {
		if(authenticationService.checkAccess(auth_token,"scms_manage_device","")){
			try{
				if(! citiesMap.containsKey(city_id)){
					throw new ModelServiceException("create device failed","City ID is not found.");
				} else{
					City city = citiesMap.get(city_id);
					if (! city.getDevicesMap().containsKey(deviceId)){
						throw new ModelServiceException("update device failed","device ID is not found within this city.");
					}
					else{
						city = citiesMap.get(city_id);
						InformationKiosk device = (InformationKiosk) city.getDevicesMap().get(deviceId);
						device.setAccountAddress(accountAddress);
						device.setEnabled(enabled);
						device.setImageUrl(imgUrl);
						device.setRedirectingURL(redirectingURL);
						System.out.println("info-kiosk "+deviceId+" is updated");
					}
				}
			} catch (ModelServiceException e){
				System.out.println(e);
			}
		}

	}

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
	public StreetLight createStreetLight(String auth_token, String city_id, String deviceId, String accountAddress,
										 float lat, float _long, boolean enabled, int brightness) {
		StreetLight streetLight = null;
		if(authenticationService.checkAccess(auth_token,"scms_manage_device","")){
			try{
				if(! citiesMap.containsKey(city_id)){
					throw new ModelServiceException("create device failed","City ID is not found.");
				} else {
					City city = citiesMap.get(city_id);
					if (city.getDevicesMap().containsKey(deviceId)){
						throw new ModelServiceException("create device failed","device ID already exists.");
					}
					else if (!city.validateLocation(lat, _long)) {
						throw new ModelServiceException("Location is invalid", "Location is not within the city radius borders");
					} else {
						streetLight = new StreetLight(deviceId, accountAddress, new Location(lat, _long),
								enabled, Status.offline, brightness);
						city.getDevicesMap().put(deviceId, streetLight);
						System.out.println("new street-light " + deviceId + " is defined");
					}
				}
			} catch (ModelServiceException e){
				System.out.println(e);
			}
		}

		return streetLight;
	}

	/**
	 * method used to update an existing Street Light in a city
	 * @param auth_token auth token will be used in the controller implementation
	 * @param city_id city id
	 * @param deviceId device id
	 * @param accountAddress blockchain account address
	 * @param enabled device enabled status
	 * @param brightness light brightness level
	 */
	public void updateStreetLight(String auth_token, String city_id, String deviceId, String accountAddress, boolean enabled, int brightness) {
		if(authenticationService.checkAccess(auth_token,"scms_manage_device","")){
			try{
				if(! citiesMap.containsKey(city_id)){
					throw new ModelServiceException("create device failed","City ID is not found.");
				} else{
					City city = citiesMap.get(city_id);
					if (! city.getDevicesMap().containsKey(deviceId)){
						throw new ModelServiceException("update device failed","device ID is not found within this city.");
					}
					else{
						city = citiesMap.get(city_id);
						StreetLight device = (StreetLight) city.getDevicesMap().get(deviceId);
						device.setAccountAddress(accountAddress);
						device.setEnabled(enabled);
						device.setBrightness(brightness);
						System.out.println("street-light "+deviceId+" is updated");
					}
				}
			} catch (ModelServiceException e){
				System.out.println(e);
			}
		}
	}

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
	public Robot createRobot(String auth_token, String city_id, String deviceId, String accountAddress,
							 float lat, float _long, boolean enabled, String activity) {
		Robot robot = null;
		if(authenticationService.checkAccess(auth_token,"scms_manage_device","")){
			try{
				if(! citiesMap.containsKey(city_id)){
					throw new ModelServiceException("create device failed","City ID is not found.");
				} else {
					City city = citiesMap.get(city_id);
					if (city.getDevicesMap().containsKey(deviceId)){
						throw new ModelServiceException("create device failed","device ID already exists.");
					}
					else if (!city.validateLocation(lat, _long)) {
						throw new ModelServiceException("Location is invalid", "Location is not within the city radius borders");
					} else {
						robot = new Robot(deviceId, accountAddress, new Location(lat, _long), Status.offline, enabled, activity);
						city.getDevicesMap().put(deviceId, robot);
						System.out.println("new robot " + deviceId + " is defined");
					}
				}
			} catch (ModelServiceException e){
				System.out.println(e);
			}
		}
		return robot;
	}

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
	public void updateRobot(String auth_token, String city_id, String deviceId, String accountAddress,float lat, float _long,
							boolean enabled, String activity) {
		if(authenticationService.checkAccess(auth_token,"scms_manage_device","")){
			try{
				if(! citiesMap.containsKey(city_id)){
					throw new ModelServiceException("create device failed","City ID is not found.");
				} else {
					City city = citiesMap.get(city_id);
					if (! city.getDevicesMap().containsKey(deviceId)){
						throw new ModelServiceException("update device failed","device ID is not found within this city.");
					}
					else if (!city.validateLocation(lat, _long)) {
						throw new ModelServiceException("Location is invalid", "Location is not within the city radius borders");
					} else {
						Robot device = (Robot) city.getDevicesMap().get(deviceId);
						device.setAccountAddress(accountAddress);
						device.setEnabled(enabled);
						device.setActivity(activity);
						device.setLocation(new Location(lat, _long));
						System.out.println("robot " + deviceId + " is updated. activity: "+device.getActivity());
					}
				}
			} catch (ModelServiceException e){
				System.out.println(e);
			}
		}

	}

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
	public ParkingSpace createParkingSpace(String auth_token, String city_id, String deviceId, String accountAddress,
										   float lat, float _long, boolean enabled, double hourlyRate) {
		ParkingSpace parkingSpace = null;
		if(authenticationService.checkAccess(auth_token,"scms_manage_device","")){
			try{
				if(! citiesMap.containsKey(city_id)){
					throw new ModelServiceException("create device failed","City ID is not found.");
				} else {
					City city = citiesMap.get(city_id);
					if (city.getDevicesMap().containsKey(deviceId)){
						throw new ModelServiceException("create device failed","device ID already exists.");
					}
					else if (!city.validateLocation(lat, _long)) {
						throw new ModelServiceException("Location is invalid", "Location is not within the city radius borders");
					} else {
						parkingSpace = new ParkingSpace(deviceId, accountAddress, new Location(lat, _long), Status.offline, enabled, hourlyRate);
						city.getDevicesMap().put(deviceId, parkingSpace);
						System.out.println("new parking-space " + deviceId + " is defined");
					}
				}
			} catch (ModelServiceException e){
				System.out.println(e);
			}
		}
		return parkingSpace;
	}

	/**
	 * This method is used to update an existing new parking space in a city
	 * @param auth_token auth token will be used in the controller implementation
	 * @param city_id city id
	 * @param deviceId device id
	 * @param accountAddress blockchain account address
	 * @param enabled device enabled status
	 * @param hourlyRate hourly rate for this parking space
	 */
	public void updateParkingSpace(String auth_token, String city_id, String deviceId, String accountAddress, boolean enabled, double hourlyRate) {
		if(authenticationService.checkAccess(auth_token,"scms_manage_device","")){
			try{
				if(! citiesMap.containsKey(city_id)){
					throw new ModelServiceException("create device failed","City ID is not found.");
				} else{
					City city = citiesMap.get(city_id);
					if (! city.getDevicesMap().containsKey(deviceId)){
						throw new ModelServiceException("update device failed","device ID is not found within this city.");
					}
					else{
						city = citiesMap.get(city_id);
						ParkingSpace device = (ParkingSpace) city.getDevicesMap().get(deviceId);
						device.setAccountAddress(accountAddress);
						device.setEnabled(enabled);
						device.setHourlyRate(hourlyRate);
						System.out.println("street-light "+deviceId+" is updated");
					}
				}
			} catch (ModelServiceException e){
				System.out.println(e);
			}
		}
	}

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
	public Vehicle createVehicle(String auth_token, String city_id, String deviceId, String accountAddress,
								 float lat, float _long, boolean enabled, String type, int capacity, double fee) {

		Vehicle vehicle = null;
		if(authenticationService.checkAccess(auth_token,"scms_manage_device","")){
			try{
				if(! citiesMap.containsKey(city_id)){
					throw new ModelServiceException("create device failed","City ID is not found.");
				} else {
					City city = citiesMap.get(city_id);
					if (city.getDevicesMap().containsKey(deviceId)){
						throw new ModelServiceException("create device failed","device ID already exists.");
					}
					else if (!city.validateLocation(lat, _long)) {
						throw new ModelServiceException("Location is invalid", "Location is not within the city radius borders");
					} else {
						vehicle = new Vehicle(deviceId, accountAddress, new Location(lat, _long), Status.offline, enabled, capacity,
								fee, VehicleType.valueOf(type));
						city.getDevicesMap().put(deviceId, vehicle);
						System.out.println("new vehicle " + deviceId + " is defined");
					}
				}
			} catch (ModelServiceException e){
				System.out.println(e);
			}
		}
		return vehicle;
	}

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
	public void updateVehicle(String auth_token, String city_id, String deviceId, String accountAddress,
							  float lat, float _long, boolean enabled, String activity, double fee) {
		if(authenticationService.checkAccess(auth_token,"scms_manage_device","")){
			try{
				if(! citiesMap.containsKey(city_id)){
					throw new ModelServiceException("create device failed","City ID is not found.");
				} else {
					City city = citiesMap.get(city_id);
					if (! city.getDevicesMap().containsKey(deviceId)){
						throw new ModelServiceException("update device failed","device ID is not found within this city.");
					}
					else if (!city.validateLocation(lat, _long)) {
						throw new ModelServiceException("Location is invalid", "Location is not within the city radius borders");
					} else {
						Vehicle device = (Vehicle) city.getDevicesMap().get(deviceId);
						device.setAccountAddress(accountAddress);
						device.setEnabled(enabled);
						device.setActivity(activity);
						device.setFee(fee);
						device.setLocation(new Location(lat, _long));
						System.out.println("vehicle " + deviceId + " is updated");
					}
				}
			} catch (ModelServiceException e){
				System.out.println(e);
			}
		}
	}

	/**
	 * This method is for showing a device in a city. If device not found, method will return all devices in that city.
	 * @param auth_token auth token will be used in the controller implementation
	 * @param city_id city id
	 * @param device_id device id
	 * @return
	 */
	public Object showDevice(String auth_token, String city_id, String device_id){
		Object device = null;
		if(authenticationService.checkAccess(auth_token,"scms_manage_device","")){
			try{
				if(! citiesMap.containsKey(city_id)){
					throw new ModelServiceException("Show device failed","City ID is not found.");
				} else{
					City city = citiesMap.get(city_id);
					if (! city.getDevicesMap().containsKey(device_id)){
						device = city.getDevicesMap(); //return all devices within the city if device is not found. (System Requirements)
						throw new ModelServiceException("Show device failed","device ID is not found within this city. returning all devices within the city");
					}
					else device = city.getDevicesMap().get(device_id);
				}
			} catch(ModelServiceException e){
				System.out.println(e);
			}
		}
		return device;
	}

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
	public Event createSensorEvent(String auth_token, String city_id, String device_id, String sensorType, String action, String subjectId) {
		Event event = null;
		if(authenticationService.checkAccess(auth_token,"scms_manage_device","")){
			try{
				if(! citiesMap.containsKey(city_id)){
					throw new ModelServiceException("create device failed","City ID is not found.");
				} else {
					City city = citiesMap.get(city_id);
					if (! city.getDevicesMap().containsKey(device_id)){
						throw new ModelServiceException("create event failed","device ID is not found.");
					}
					else {
						Device device = city.getDevicesMap().get(device_id);
						if(subjectId.equals("")){
							event = new Event(action,Sensor.valueOf(sensorType));
						} else {
							Person person = city.getPeopleMap().get(subjectId);
							event = new Event(action,Sensor.valueOf(sensorType),person);
						}

						if(device.getLastEvent() == null){
							device.setCurrentStatus(Status.ready); // when first event arrives to device, device status change to online (ready)
						}

						device.setLastEvent(event);
						device.getEventsList().add(event);
						System.out.println("created sensor-event for device id: "+device_id+" "+event);

						//create event broker and update observers
						EventBroker newEventBroker = new EventBroker(
								city_id, device_id, event, device.getLocation()
						);
						//setEventBroker(newEventBroker);
						notify(newEventBroker);

					}
				}
			} catch (ModelServiceException e){
				System.out.println(e);
			}
		}
		return event;
	}

	/**
	 * Method for sending a device output
	 * @param auth_token auth token will be used in the controller implementation
	 * @param city_id city id
	 * @param device_id device id
	 * @param sensorType type of the sensor
	 * @param action action captured by the sensor
	 * @return Event
	 */
	public Event createSensorOutput(String auth_token, String city_id, String device_id, String sensorType, String action) {
		Event event = null;
		if(authenticationService.checkAccess(auth_token,"scms_manage_device","")){
			try{
				if(! citiesMap.containsKey(city_id)){
					throw new ModelServiceException("create device failed","City ID is not found.");
				} else {
					City city = citiesMap.get(city_id);
					if (! city.getDevicesMap().containsKey(device_id)){
						throw new ModelServiceException("create output event failed","device ID is not found .");
					} else if(! sensorType.equals("speaker")){
						throw new ModelServiceException("create output event failed","sensor does'nt support output.");
					} else {
						Device device = city.getDevicesMap().get(device_id);
						event = new Event(action,Sensor.valueOf(sensorType));
						device.setLastEvent(event);
						device.getEventsList().add(event);
						System.out.println("Model Service created sensor-output to device id# "+device_id+" : "+event.getAction());
					}
				}
			} catch (ModelServiceException e){
				System.out.println(e);
			}
		}
		return event;
	}

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
	public Resident createResident(String auth_token, String city_id, String personId, String name, String bioId, String phone, String role, float lat,
								   float _long, String accountAddress) {
		Resident resident = null;
		if(authenticationService.checkAccess(auth_token,"scms_manage_person","")){
			try{
				if(! citiesMap.containsKey(city_id)){
					throw new ModelServiceException("create device failed","City ID is not found.");
				} else {
					City city = citiesMap.get(city_id);
					if (city.getPeopleMap().containsKey(personId)){
						throw new ModelServiceException("create resident failed","Person ID already exists.");
					}
					else if (!city.validateLocation(lat, _long)) {
						throw new ModelServiceException("Location is invalid", "Location is not within the city radius borders");
					} else {
						resident = new Resident(personId, bioId, new Location(lat, _long), name, phone, accountAddress, Role.valueOf(role));
						city.getPeopleMap().put(personId, resident);
						System.out.println("new resident " + personId + " is defined");
					}
				}
			} catch (ModelServiceException e){
				System.out.println(e);
			}
		}
		return resident;
	}

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
	public void updateResident(String auth_token, String city_id, String personId, String name, String bioId, String phone, String role, float lat,
							   float _long, String accountAddress) {
		if(authenticationService.checkAccess(auth_token,"scms_manage_person","")){
			try{
				if(! citiesMap.containsKey(city_id)){
					throw new ModelServiceException("create device failed","City ID is not found.");
				} else {
					City city = citiesMap.get(city_id);
					if (! city.getPeopleMap().containsKey(personId)){
						throw new ModelServiceException("update resident failed","Person ID is not found within this city.");
					}
					else if (!city.validateLocation(lat, _long)) {
						throw new ModelServiceException("Location is invalid", "Location is not within the city radius borders");
					} else {
						Resident resident = (Resident) city.getPeopleMap().get(personId);
						resident.setName(name);
						resident.setBiometricId(bioId);
						resident.setPhone(phone);
						resident.setRole(Role.valueOf(role));
						resident.setLocation(new Location(lat, _long));
						System.out.println("Resident " + personId + " is updated");
					}
				}
			} catch (ModelServiceException e){
				System.out.println(e);
			}
		}

	}

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
	public Visitor createVisitor(String auth_token, String city_id, String personId, String bioId, float lat, float _long) {
		Visitor visitor = null;
		if(authenticationService.checkAccess(auth_token,"scms_manage_person","")){
			try{
				if(! citiesMap.containsKey(city_id)){
					throw new ModelServiceException("create device failed","City ID is not found.");
				} else {
					City city = citiesMap.get(city_id);
					if (city.getPeopleMap().containsKey(personId)){
						throw new ModelServiceException("create visitor failed","Person ID already exists.");
					}
					else if (!city.validateLocation(lat, _long)) {
						throw new ModelServiceException("Location is invalid", "Location is not within the city radius borders");
					} else {
						visitor = new Visitor(personId, bioId, new Location(lat, _long));
						city.getPeopleMap().put(personId, visitor);
						System.out.println("new visitor " + personId + " is defined");
					}
				}
			} catch (ModelServiceException e){
				System.out.println(e);
			}
		}
		return visitor;
	}

	/**
	 * This method is for updating a visitor of a city
	 * @param auth_token auth token will be used in the controller implementation
	 * @param city_id city id
	 * @param personId person id
	 * @param bioId biometric id
	 * @param lat location lat
	 * @param _long location long
	 */
	public void updateVisitor(String auth_token, String city_id, String personId, String bioId, float lat, float _long) {
		if(authenticationService.checkAccess(auth_token,"scms_manage_person","")){
			try{
				if(! citiesMap.containsKey(city_id)){
					throw new ModelServiceException("create device failed","City ID is not found.");
				} else {
					City city = citiesMap.get(city_id);
					if (! city.getPeopleMap().containsKey(personId)){
						throw new ModelServiceException("update visitor failed","Person ID is not found within this city.");
					}
					else if (!city.validateLocation(lat, _long)) {
						throw new ModelServiceException("Location is invalid", "Location is not within the city radius borders");
					} else {
						Visitor visitor = (Visitor) city.getPeopleMap().get(personId);
						visitor.setBiometricId(bioId);
						visitor.setLocation(new Location(lat, _long));
						System.out.println("Visitor " + personId + " is updated");
					}
				}
			} catch (ModelServiceException e){
				System.out.println(e);
			}
		}
	}

	/**
	 * This method is used show person
	 * @param auth_token auth token will be used in the controller implementation
	 * @param city_id city id
	 * @param person_id person id
	 * @return Person
	 */
	public Object showPerson(String auth_token, String city_id, String person_id) {
		Object person = null;
		if(authenticationService.checkAccess(auth_token,"scms_manage_person","")){
			try{
				if(! citiesMap.containsKey(city_id)){
					throw new ModelServiceException("Show person failed","City ID is not found.");
				} else {
					City city = citiesMap.get(city_id);
					if (! city.getPeopleMap().containsKey(person_id)){
						throw new ModelServiceException("Show person failed","Person ID is not found within this city.");
					}
					else person = city.getPeopleMap().get(person_id);
				}
			} catch (ModelServiceException e){
				System.out.println(e);
			}
		}

		return person;
	}




}
