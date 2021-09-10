//Name: Dimo Hristov Dimchev
//Student No: 18032257
//Date: 15/04/2019
//
//
public interface Directory {
	  public void insert(Entry e) throws Exception;
	    public void deleteByName(String name);
	    public void deleteByNumber(String no);
	    public String lookup(String name);
	    public void changeNumber(String name, String newNumber);
	    public String print();
}
