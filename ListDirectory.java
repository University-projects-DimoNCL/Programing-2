
//Name: Dimo Hristov Dimchev
//Student No: 18032257
//Date: 01/05/2019
//
//

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class ListDirectory implements Directory {
	LinkedList<Entry> linkedlist = new LinkedList<Entry>();

	@Override
	public void insert(Entry e) throws Exception {
		// TODO Auto-generated method stub
		Entry enter = new Entry(e.name, e.initials, e.extension);
		int index = 0;
		for (int i = 0; i < linkedlist.size(); i++) {
			if (linkedlist.get(i).compareTo(enter) > 0) {

				index = i;
			}
		}
		linkedlist.add(index, enter);
	}

	@Override
	public void deleteByName(String name) {
		// TODO Auto-generated method stub
		for (int i = 0; i < linkedlist.size(); i++) {
			if (linkedlist.get(i).name.contentEquals(name)) {

				linkedlist.remove(i);
			}
		}
	}

	@Override
	public void deleteByNumber(String no) {
		// TODO Auto-generated method stub
		for (int i = 0; i < linkedlist.size(); i++) {
			if (linkedlist.get(i).extension.contentEquals(no)) {

				linkedlist.remove(i);
			}
		}

	}

	@Override
	public String lookup(String name) {
		// TODO Auto-generated method stub
		String num = "";
		for (Entry ent : linkedlist) {
			String ret = ent.name;
			if (name.equals(ret)) {
				num = "The extension of " + name + " is" + "  " + ent.getNumber();
			}

		}
		System.out.println(num); // only if not testing
		return num;
	}

	@Override
	public void changeNumber(String name, String newNumber) {// it changes the number by first finding and comparing the
																// name and then changing the number
		// TODO Auto-generated method stub
		for (int i = 0; i < linkedlist.size(); i++) {
			if (linkedlist.get(i).getName().contains(name)) {
				linkedlist.get(i).setNumber(newNumber);
			}

		}
	}

	@Override
	public String print() { // it prints the directory,by first constructing every string and then returning
							// it
		StringBuilder sb = new StringBuilder();
		for (Entry k : linkedlist) {
			sb.append(k.name + "\t" + k.initials + "\t" + k.extension + "\n");

		}

		String ret = sb.toString();
		System.out.println(ret);
		return ret;

	}

	private static ListDirectory ad = new ListDirectory();

	public static void Test() throws Exception { // test class that tests all the method
		ad.insert(new Entry("Best", "a.u.", "0132"));
		ad.insert(new Entry("Test", "b.u.", "01"));

		ad.insert(new Entry("Dest", "h.u.", "012232"));
		ad.insert(new Entry("Gest", "g.u.", "12232"));
		ad.insert(new Entry("Zest", "j.u.", "2232"));
		ad.insert(new Entry("Hest", "f.u.", "55"));
		ad.insert(new Entry("Test", "a.u.", "912232"));
		ad.insert(new Entry("Test", "y.u.", "014232"));
		System.out.println("------------------");
		ad.print();
		System.out.println("------------");

		System.out.println("\n");
		System.out.println("-----------------");
		ad.deleteByName("Best");
		ad.deleteByName("Test");
		ad.deleteByName("Dest");
		ad.deleteByName("Gest");
		ad.deleteByName("Zest");
		ad.deleteByName("Hest");
		ad.deleteByName("Test");
		ad.deleteByName("Test");
		System.out.println("------------------");

		System.out.println("AFTER DELETE");

		ad.print();

		System.out.println("AFTER DELETE");

		ad.insert(new Entry("test2", "j.u.", "2232"));
		ad.print();

		ad.changeNumber("test2", "1");
		ad.print();

		System.out.println("lkp test");

		ad.lookup("test2");
		ad.print();

		ad.deleteByNumber("1");
		System.out.println("del test");
		ad.print();
	}

	private interface TestPerformance {
		public void test(Entry e) throws Exception; // using this as a refference to class entry
	}

	private static void testInsert(Entry e) throws Exception {
		ad.insert(e);
	}

	private static void testLookup(Entry e) throws Exception {
		ad.lookup(e.name);
	}

	private static void testDeleteByName(Entry e) throws Exception {
		ad.deleteByName(e.name);
	}

	private static void testDeleteByNumber(Entry e) throws Exception {
		ad.deleteByNumber(e.extension);
	}

	public static void TestFromFile(String file, TestPerformance t)
			throws FileNotFoundException, IOException, Exception {
		long totalElapsedTime = 0, average = 0;
		int totalEntries = 0;
		StopWatch tim = new StopWatch();
		for (int i = 0; i < 2000; i++) {// goes through the loop 2000 times so it can calculate the average time of an
										// operation
			try (Scanner br = new Scanner(new FileReader(file))) {
				while (br.hasNextLine()) { // checks if there is a next line in the file
					String[] entryInput = br.nextLine().split("\t");
					tim.start();
					t.test(new Entry(entryInput[0], entryInput[1], entryInput[2])); // adds every part of entry in a
																					// different slot of the array
					tim.stop();
					totalEntries++;
					totalElapsedTime = totalElapsedTime + tim.getElapsedTime(); // calculates the time

				}
			}
		}
		average = totalElapsedTime / totalEntries;
		System.out.println("Total time: " + totalElapsedTime + " nano secs, Entries " + totalEntries);
		System.out.println("Average time: " + average + "nano secs");
	}

	public static void main(String[] args) {

		System.out.println("INSERT");
		try {
			TestFromFile("C:\\\\Users\\\\dhdim\\\\projects\\\\Coursework\\\\src\\\\Coursework\\\\test.txt",
					ListDirectory::testInsert);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("LOOKUP");
		try {
			TestFromFile("C:\\\\Users\\\\dhdim\\\\projects\\\\Coursework\\\\src\\\\Coursework\\\\test.txt",
					ListDirectory::testLookup);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("DELETE BY NAME");
		try {
			TestFromFile("C:\\Users\\dhdim\\projects\\Coursework\\src\\Coursework\\test.txt",
					ListDirectory::testDeleteByName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("DELETE BY NUMBER");
		try {
			TestFromFile("C:\\\\Users\\\\dhdim\\\\projects\\\\Coursework\\\\src\\\\Coursework\\\\test.txt",
					ListDirectory::testDeleteByNumber);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ad.print();
	}
}
