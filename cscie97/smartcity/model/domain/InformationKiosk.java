package cscie97.smartcity.model.domain;

public class InformationKiosk extends Device {

	private String imageUrl;
	private String redirectingURL;

	/**
	 * Constructor for creating new information kiosk object
	 * @param id
	 * @param enabled
	 * @param accountAddress
	 * @param location
	 * @param currentStatus
	 * @param imageUrl
	 * @param redirectingURL
	 */
	public InformationKiosk(String id, boolean enabled, String accountAddress, Location location, Status currentStatus,
							String imageUrl, String redirectingURL) {
		super(id, enabled, accountAddress, location, currentStatus);
		this.imageUrl = imageUrl;
		this.redirectingURL = redirectingURL;
	}

	/**
	 * info-kiosk image getter
	 * @return
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * info-kiosk image setter
	 * @param imageUrl
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * info-kiosk redirecting URL getter
	 * @return
	 */
	public String getRedirectingURL() {
		return redirectingURL;
	}

	/**
	 * info-kiosk redirecting URL setter
	 * @param redirectingURL
	 */
	public void setRedirectingURL(String redirectingURL) {
		this.redirectingURL = redirectingURL;
	}

	/**
	 * ToString method to show object contents
	 * @return
	 */
	@Override
	public String toString() {
		return "InformationKiosk{" +
				"imageUrl='" + imageUrl + '\'' +
				", redirectingURL='" + redirectingURL + '\'' +
				"} " + super.toString();
	}
}
