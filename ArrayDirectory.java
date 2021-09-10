
//Name: Dimo Hristov Dimchev
//Student No: 18032257
//Date: 15/04/2019
//
//
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ArrayDirectory implements Directory {

	@Override
	public void insert(Entry e) throws Exception {//
		// TODO Auto-generated method stub
		int i = last++;

		if (last > arr.length) {
			throw new Exception("out of space");
		}

		Entry ent = new Entry(e.name, e.initials, e.extension);

		arr[i] = ent;

		while (i > 0 && arr[i - 1].name.compareTo(arr[i].name) > 0) {
			swap(i - 1, i--);
		}

	}

	@Override
	public void deleteByName(String name) {// finds the index for the name and deletes it
		int d = find(name);
		deleteByIndex(d);
	}

	@Override
	public void deleteByNumber(String no) {
		// TODO Auto-generated method stub
		deleteBy((e) -> e.extension.equals(no));
	}

	@Override
	public String lookup(String name) {// lookup method
		int i = find(name);
		String res = "";

		if (i != -1) {
			res = arr[i].extension;
		}

		return res;
	}

	@Override
	public void changeNumber(String name, String newNumber) {// it changes the number
		int n = find(name);

		if (n != -1) {
			arr[n].extension = newNumber;
		}

	}

	@Override
	public String print() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < last; i++) {
			sb.append(arr[i].name + "\t" + arr[i].initials + "\t" + arr[i].extension + "\n");
		}
		String ret = sb.toString();
		return ret;
	}

	private Entry[] arr = new Entry[50000];

	private int last = 0;

	private void swap(int i, int j) { // swaps the two
		Entry tmp = arr[j];
		arr[j] = arr[i];
		arr[i] = tmp;
	}

	private interface Comparable {
		public boolean check(Entry e);
	}

	// binary search
	private int binary_search(String name, int start, int end) {
		if (start > end) {
			return -1;
		}

		int m = start + (end - start) / 2;

		int r = name.compareTo(arr[m].name);
		if (r == 0) {
			return m;
		}
		if (r < 0) {
			return binary_search(name, start, m - 1);
		} else {
			return binary_search(name, m + 1, end);
		}
	}

	private int find(String name) {
		return binary_search(name, 0, last);
	}

	private int find(Comparable c) {
		int res = -1;
		for (int i = 0; i < last; i++) {
			if (c.check(arr[i])) {
				res = i;
				break;
			}
		}

		return res;
	}

	private void deleteByIndex(int d) {
		if (d != -1) {
			for (int i = d; d < last; d++) {
				swap(d, d + 1);
			}

			arr[d] = null;
			last--;
		}
	}

	private void deleteBy(Comparable c) {
		int d = find(c);
		deleteByIndex(d);
	}

	public static void Test() throws Exception { // this is the test class that test neatly prints all the methods
		ad.insert(new Entry("test5", "a.u.", "0132"));
		ad.insert(new Entry("test1", "b.u.", "01"));
		ad.insert(new Entry("test9", "h.u.", "012232"));
		ad.insert(new Entry("test3", "g.u.", "12232"));
		ad.insert(new Entry("test2", "j.u.", "2232"));
		ad.insert(new Entry("test4", "f.u.", "55"));
		ad.insert(new Entry("test7", "a.u.", "912232"));
		ad.insert(new Entry("test0", "y.u.", "014232"));

		ad.print();

		System.out.println("\n");

		ad.deleteByName("Aest");
		ad.deleteByName("test1");
		ad.deleteByName("test9");
		ad.deleteByName("test3");
		ad.deleteByName("test2");
		ad.deleteByName("test4");
		ad.deleteByName("test7");
		ad.deleteByName("test0");

		System.out.println("apf");

		ad.print();

		System.out.println("apf1");

		ad.insert(new Entry("test2", "j.u.", "2232"));
		ad.print();

		ad.changeNumber("test2", "1");
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

	private static ArrayDirectory ad = new ArrayDirectory();// this is the test directory
	/*
	 * public static void main(String[] args) {//this is the main program that te
	 * that uses the TestFromFile method and test all the methods try { Test(); }
	 * catch (Exception e1) { // TODO Auto-generated catch block
	 * e1.printStackTrace(); } try { System.out.println("INSERT"); TestFromFile(
	 * "C:\\\\Users\\\\dhdim\\\\projects\\\\Coursework\\\\src\\\\Coursework\\\\test.txt",
	 * ArrayDirectory::testInsert); System.out.println("LOOKUP"); TestFromFile(
	 * "C:\\\\Users\\\\dhdim\\\\projects\\\\Coursework\\\\src\\\\Coursework\\\\test.txt",
	 * ArrayDirectory::testLookup); System.out.println("DELETE BY NAME");
	 * TestFromFile(
	 * "C:\\Users\\dhdim\\projects\\Coursework\\src\\Coursework\\test.txt",
	 * ArrayDirectory::testDeleteByName); System.out.println("DELETE BY NUMBER");
	 * TestFromFile(
	 * "C:\\\\Users\\\\dhdim\\\\projects\\\\Coursework\\\\src\\\\Coursework\\\\test.txt",
	 * ArrayDirectory::testDeleteByNumber); ad.print(); } catch
	 * (FileNotFoundException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated catch
	 * block e.printStackTrace(); } catch (Exception e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); }
	 * 
	 * }
	 */

	private static void AddInsert(JPanel pan) {// makes the field for the where the DeleteByName method can be used
		JPanel pInsert = new JPanel();

		pInsert.setBorder(BorderFactory.createTitledBorder("Insert"));
		pInsert.setLayout(new BoxLayout(pInsert, BoxLayout.Y_AXIS));

		JPanel pLine1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel pLine2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel pLine3 = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JLabel lName = new JLabel("Name:");

		lName.setBorder(new EmptyBorder(0, 23, 0, 0));

		JTextField tName = new JTextField(15);

		JLabel lInitials = new JLabel("Initials:");

		lInitials.setBorder(new EmptyBorder(0, 19, 0, 0));

		JTextField tInitials = new JTextField(15);

		JLabel lExtension = new JLabel("Extension:");

		JTextField tExtension = new JTextField(15);

		JButton b = new JButton("Insert");

		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ad.insert(new Entry(tName.getText(), tInitials.getText(), tExtension.getText()));		// it performes insert method, and if there is an exception in run time then prints in the console that the element failed to insert
				} catch (Exception ex) {
					System.out.println("Failed to insert");
				}
			}
		});

		pLine1.add(lName);
		pLine1.add(tName);
		pLine1.add(b);

		pLine2.add(lInitials);
		pLine2.add(tInitials);

		pLine3.add(lExtension);
		pLine3.add(tExtension);

		pInsert.add(pLine1);
		pInsert.add(pLine2);
		pInsert.add(pLine3);
		pan.add(pInsert);
	}

	private static void AddDeleteByName(JPanel pan) { // makes the field for the where the DeleteByName method can be used
		JPanel pDelete = new JPanel();

		pDelete.setBorder(BorderFactory.createTitledBorder("Delete by name"));
		pDelete.setLayout(new BoxLayout(pDelete, BoxLayout.Y_AXIS));

		JPanel pLine1 = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JLabel lName = new JLabel("Name:");

		lName.setBorder(new EmptyBorder(0, 23, 0, 0));

		JTextField tName = new JTextField(15);

		JButton b = new JButton("Delete");

		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {// when buton clicked uses delete by name for the value that is in the text box
				ad.deleteByName(tName.getText());
			}
		});

		pLine1.add(lName);
		pLine1.add(tName);
		pLine1.add(b);

		pDelete.add(pLine1);
		pan.add(pDelete);
	}

	private static void AddDeleteByNumber(JPanel pan) {// makes the field for the where the DeleteByName method can be used
		JPanel pDelete = new JPanel();

		pDelete.setBorder(BorderFactory.createTitledBorder("Delete by number"));
		pDelete.setLayout(new BoxLayout(pDelete, BoxLayout.Y_AXIS));

		JPanel pLine1 = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JLabel lNumber = new JLabel("Number:");

		lNumber.setBorder(new EmptyBorder(0, 10, 0, 0));

		JTextField tNumber = new JTextField(15);

		JButton b = new JButton("Delete");

		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ad.deleteByNumber(tNumber.getText());    //It performes the delete by number method for the given value in the text field
			}
		});

		pLine1.add(lNumber);
		pLine1.add(tNumber);
		pLine1.add(b);

		pDelete.add(pLine1);
		pan.add(pDelete);
	}

	private static void AddLookup(JPanel pan) {// makes the field for the where the DeleteByName method can be used
		JPanel pLookup = new JPanel();

		pLookup.setBorder(BorderFactory.createTitledBorder("Lookup"));
		pLookup.setLayout(new BoxLayout(pLookup, BoxLayout.Y_AXIS));		//sets border and layout

		JPanel pLine1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel pLine2 = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JLabel lName = new JLabel("Name:");
		lName.setBorder(new EmptyBorder(0, 23, 0, 0));

		JTextField tName = new JTextField(15);

		JLabel lRes = new JLabel("Result:");
		lRes.setBorder(new EmptyBorder(0, 21, 0, 0));

		JTextField tRes = new JTextField(15);
		tRes.setEditable(false);

		JButton b = new JButton("Find");

		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tRes.setText(ad.lookup(tName.getText()));		//uses lookup to get the number for the inputted name
			}
		});

		pLine2.add(lRes);
		pLine2.add(tRes);

		pLine1.add(lName);
		pLine1.add(tName);
		pLine1.add(b);

		pLookup.add(pLine1);
		pLookup.add(pLine2);
		pan.add(pLookup);
	}

	private static void AddChangeNumber(JPanel pan) {// makes the field for the where the DeleteByName method can be used
		JPanel pChange = new JPanel();

		pChange.setBorder(BorderFactory.createTitledBorder("Change number"));
		pChange.setLayout(new BoxLayout(pChange, BoxLayout.Y_AXIS));		//sets border and layout

		JPanel pLine1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel pLine2 = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JLabel lName = new JLabel("Name:");
		lName.setBorder(new EmptyBorder(0, 23, 0, 0));

		JTextField tName = new JTextField(15);

		JLabel lNumber = new JLabel("Number:");
		lNumber.setBorder(new EmptyBorder(0, 10, 0,

				0));

		JTextField tNumber = new JTextField(15);

		JButton b = new JButton("Change");

		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ad.changeNumber(tName.getText(), tNumber.getText());			//performes the  change number method with the values of the two text boxes
			}
		});

		pLine1.add(lName);
		pLine1.add(tName);
		pLine1.add(b);

		pLine2.add(lNumber);
		pLine2.add(tNumber);

		pChange.add(pLine1);
		pChange.add(pLine2);
		pan.add(pChange);
	}

	private static void AddPrint(JPanel pan) {// makes the field for the where the print method can be used
		JPanel pPrint = new JPanel();

		pPrint.setBorder(BorderFactory.createTitledBorder("Print"));
		pPrint.setLayout(new BoxLayout(pPrint, BoxLayout.Y_AXIS));		//sets border and layout

		JTextArea tName = new JTextArea();
		tName.append(" ");
		tName.setBorder(new EmptyBorder(10, 0, 0, 0));

		JButton b = new JButton("Print");

		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {			
				tName.setText(ad.print());				//makes the text are name diplay the  string from the print method
			}
		});

		pPrint.add(b);
		pPrint.add(tName);
		pan.add(pPrint);
	}

	public static void main(String[] args) {
		JFrame f = new JFrame();				

		JPanel vertPan = new JPanel();

		vertPan.setSize(375, 650);
		vertPan.setLayout(new BoxLayout(vertPan, BoxLayout.Y_AXIS));

		AddInsert(vertPan);
		AddDeleteByName(vertPan);
		AddDeleteByNumber(vertPan);
		AddLookup(vertPan);
		AddChangeNumber(vertPan);
		AddPrint(vertPan);

		f.add(vertPan);

		f.setSize(375, 650);
		f.setLayout(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
}
