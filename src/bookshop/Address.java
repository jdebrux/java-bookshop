package bookshop;
/**
 * This class is used to construct the address object for each user
 * @author F015011
 *
 */
public class Address {
	private int number;
	private String postcode;
	private String city;

	public Address(int number, String postcode, String city) {
		this.setNumber(number);
		this.setPostcode(postcode);
		this.setCity(city);
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

}
