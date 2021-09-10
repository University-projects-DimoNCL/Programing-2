//Name: Dimo Hristov Dimchev
//Student No: 18032257
//Date: 06/05/2019
//
//

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class HashDirectory implements Directory {

	private LinkedList<Entry>[] array = new LinkedList[26];

	public HashDirectory() {
		for (int i = 0; i < array.length; i++) {
			array[i] = new LinkedList<Entry>();
		}
	}

	@Override
	public void insert(Entry e) throws Exception {
		// TODO Auto-generated method stub

		Entry enter = new Entry(e.name, e.initials, e.extension);

// this gets the first letter of a word
		int index = e.name.toUpperCase().charAt(0) - 'A'; // finds the first letter of name in upper case and subtract
															// from its hashcode of A - 65
		for (int k = 0; k < array[index].size(); k++) {

			if (array[index].get(k).compareTo(enter) == 0) {
				array[index].add(k + 1, enter);
				return;
			} else if (array[index].get(k).compareTo(enter) > 0) {
				if (k == 0) {
					array[index].addFirst(enter);
					return;
				} else {
					array[index].add(k, enter);
					return;
				}
			} else {
				if (e.name.length() == array[index].get(k).getName().length()) {

					String temp = e.name.toUpperCase();
					for (int j = 1; j < e.name.length(); j++) {
						if (temp.charAt(j) > array[index].get(k).getName().toUpperCase().charAt(j)) {
							break;
						}
					}
				}
			}
		}
		array[index].add(enter);
		return;
	}

	@Override
	public void deleteByName(String name) {
		// TODO Auto-generated method stub
		int del = name.toUpperCase().charAt(0) - 'A';
		for (int i = 0; i < array[del].size(); i++) {
			if (array[del].get(i).name == name) {
				array[del].remove(i);
			}
		}
	}

	@Override
	public void deleteByNumber(String no) {
		// TODO Auto-generated method stub
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].size(); j++) {
				if (array[i].get(j).getNumber() == no) {
					array[i].remove(j);
				}
			}
		}
	}

	@Override
	public String lookup(String name) {
		String look = " ";
		int index = name.toUpperCase().charAt(0) - 'A';
		for (int k = 0; k < array[index].size(); k++) {
			if (array[index].get(k).compareTo(name) == 1) {
				look = array[index].get(k).extension;
			}

		}

		return look;
	}

	@Override
	public void changeNumber(String name, String newNumber) {
		// TODO Auto-generated method stub
		for (Entry e : array[name.toUpperCase().charAt(0) - 'A']) {
			if (e.name == name) {
				e.setNumber(newNumber);
			}
		}
	}

	@Override
	public String print() {
		// TODO Auto-generated method stub

		for (int i = 0; i < 26; i++) {
			for (Entry k : array[i]) {
				System.out.println(k.name + "\t" + k.initials + "\t" + k.extension + "\n");
			}

		}

		return null;
	}

	private static HashDirectory ad = new HashDirectory();

	private static void Test() throws Exception {
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

		ad.insert(new Entry("best", "j.u.", "2232"));
		ad.print();

		ad.changeNumber("best", "1");
		ad.print();

		System.out.println("lkp test");

		ad.lookup("best");
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

	private static void testChangeNumber(Entry e) {
		ad.changeNumber(e.name, "10100");
	}

	public static void TestFromFile(String file, TestPerformance t)
			throws FileNotFoundException, IOException, Exception {
		long totalElapsedTime = 0, average = 0;
		int totalEntries = 0;
		StopWatch tim = new StopWatch();
		for (int i = 0; i < 2000; i++) {
			try (Scanner br = new Scanner(new FileReader(file))) {
				while (br.hasNextLine()) {
					String[] entryInput = br.nextLine().split("\t");

					tim.start();
					t.test(new Entry(entryInput[0], entryInput[1], entryInput[2]));
					tim.stop();

					totalEntries++;
					totalElapsedTime = totalElapsedTime + tim.getElapsedTime();

				}
			}
		}
		average = totalElapsedTime / totalEntries;
		System.out.println("Total time: " + totalElapsedTime + " nano secs, Entries " + totalEntries);
		System.out.println("Average time: " + average + "nano secs");
	}

	public static void main(String[] args) {// this is the main program that te that uses the TestFromFile method and
											// test all the methods

		try {
			System.out.println("INSERT");
			TestFromFile("C:\\\\Users\\\\dhdim\\\\projects\\\\Coursework\\\\src\\\\Coursework\\\\test.txt",
					HashDirectory::testInsert);
			System.out.println("LOOKUP");
			TestFromFile("C:\\\\Users\\\\dhdim\\\\projects\\\\Coursework\\\\src\\\\Coursework\\\\test.txt",
					HashDirectory::testLookup);
			System.out.println("DELETE BY NAME");
			TestFromFile("C:\\Users\\dhdim\\projects\\Coursework\\src\\Coursework\\test.txt",
					HashDirectory::testDeleteByName);
			System.out.println("DELETE BY NUMBER");
			TestFromFile("C:\\\\Users\\\\dhdim\\\\projects\\\\Coursework\\\\src\\\\Coursework\\\\test.txt",
					HashDirectory::testDeleteByNumber);
			System.out.println("CHANGE NUMBER");
			TestFromFile("C:\\\\Users\\\\dhdim\\\\projects\\\\Coursework\\\\src\\\\Coursework\\\\test.txt",
					HashDirectory::testChangeNumber);

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

	}
}
