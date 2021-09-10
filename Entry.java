//Name: Dimo Hristov Dimchev
//Student No: 18032257
//Date: 15/04/2019
//
//
public class Entry { // sets the values of name,initials,extension
	public String name;
	public String initials;
	public String extension;

	public Entry(String n, String i, String e) {
		name = n;
		initials = i;
		extension = e;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNumber(String extension) {
		this.extension = extension;
	}

	public String getNumber() {
		return extension;
	}

	public int compareTo(Entry e) {
		return this.name.toUpperCase().compareTo(e.name.toUpperCase());
	}

	public int compareTo(String e) {
		return this.name.toUpperCase().compareTo(e.toUpperCase());
	}
}
