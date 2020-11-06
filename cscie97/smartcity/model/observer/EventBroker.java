package cscie97.smartcity.model.observer;

import cscie97.smartcity.model.domain.Event;
import cscie97.smartcity.model.domain.Location;

/**
 * This class is used to represent the event message that will be processed by the observers (the controller).
 */
public class EventBroker {

    String cityId;
    String deviceId;
    Event event;
    Location location;

    /**
     * constructor of the event broker
     * @param cityId
     * @param deviceId
     * @param event
     * @param location
     */
    public EventBroker(String cityId, String deviceId, Event event, Location location) {
        this.cityId = cityId;
        this.deviceId = deviceId;
        this.event = event;
        this.location = location;
    }

    /**
     * city id getter
     * @return
     */
    public String getCityId() {
        return cityId;
    }

    /**
     * device id getter
     * @return
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * Device Event getter
     * @return
     */
    public Event getEvent() {
        return event;
    }

    /**
     * event setter
     * @param event
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     * location getter
     * @return
     */
    public Location getLocation() {
        return location;
    }

    /**
     * location setter
     * @param location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Overridden formatted toString() method
     * @return
     */
    @Override
    public String toString() {
        return "EventBroker{" +
                "cityId='" + cityId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", event=" + event +
                ", location=" + location +
                '}';
    }


}
