package pt.ua.icm.bringme.models;

public class Delivery {

	private String originAddress, destinationAddress;
	private Order order;
	private int requestorId;
	private int courierId;
	private boolean finished = false, accepted = false;

	/**
	 * 
	 * @param originAddress
	 * @param destinationAddress
	 * @param requestorId
	 * @param courierId
	 */
	public Delivery(String originAddress, String destinationAddress,
			int requestorId, int courierId) {
		super();
		this.originAddress = originAddress;
		this.destinationAddress = destinationAddress;
		this.requestorId = requestorId;
		this.courierId = courierId;
	}

	/**
	 * @return the finished
	 */
	public boolean isFinished() {
		return finished;
	}

	/**
	 * @param finished
	 *            the finished to set
	 */
	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	/**
	 * @return the sourceAddress
	 */
	public String getOriginAddress() {
		return originAddress;
	}

	/**
	 * @param originAddress
	 *            the sourceAddress to set
	 */
	public void setOriginAddress(String originAddress) {
		this.originAddress = originAddress;
	}

	/**
	 * @return the targetAddress
	 */
	public String getDestinationAddress() {
		return destinationAddress;
	}

	/**
	 * @param destinationAddress
	 *            the targetAddress to set
	 */
	public void setDestinationtAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	/**
	 * 
	 * @return the Requestor Id
	 */
	public int getRequestorId() {
		return requestorId;
	}

	/**
	 * 
	 * @return the Courier Id
	 */
	public int getCourierId() {
		return courierId;
	}

	/**
	 * @return the order
	 */
	public Order getOrder() {
		return order;
	}

	/**
	 * @param order
	 *            the order to set
	 */
	public void setOrder(Order order) {
		this.order = order;
	}

	/**
	 * @return the accepted
	 */
	public boolean isAccepted() {
		return accepted;
	}

	/**
	 * @param accepted the accepted to set
	 */
	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

}
