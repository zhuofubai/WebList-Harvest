import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.*;

/**
 * @author zhuofu
 * 
 */
public class ReadFile {

	/**
	 * 
	 */
	public ReadFile() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		// String FileName = "roster";
		String FileName = "reaction";
		File file = new File(FileName + ".txt");
		Scanner scan = new Scanner(file);
		String Line;
		ArrayList<Records> Table = new ArrayList<Records>();
		int Lineindex = 1;
		/******************** Begin ************************************/

		while (scan.hasNextLine()) {
			Records R = new Records();
			R.index = Lineindex;
			Line = scan.nextLine();
			Line = Line.replace("\t", " ");
			Line = Line.replace("	", "");
			// Line=Line.replace("	", " ");
			String[] temp = Line.split("\\ ");

			System.out.println(Line);

			ArrayList<String> Row = new ArrayList<String>();
			for (int i = 0; i < temp.length; i++) {
				if (temp[i].compareTo("") != 0 && temp[i].compareTo(" ") != 0) {

					Row.add(temp[i]);
				}
			}

			/******************** Regular Expression **************************************/
			ArrayList<node> templist = new ArrayList<node>();

			Pattern datePatt = Pattern
					.compile("([0-9]{2})/([0-9]{2})/([0-9]{4})");
			// Pattern weightPatt = Pattern.compile("([0-9]{3})");
			Pattern heightPatt = Pattern.compile("([0-9]{1})-([0-9]{1,2})");
			Pattern ECnumPatt = Pattern
					.compile("([0-9]{1}).([0-9]{1}).([0-9]{1}).([0-9]{1,2})");
			for (int i = 0; i < Row.size(); i++) {
				String infoStr = Row.get(i);
				Matcher d = datePatt.matcher(infoStr);
				// Matcher w = weightPatt.matcher(infoStr);
				Matcher h = heightPatt.matcher(infoStr);
				Matcher ec = ECnumPatt.matcher(infoStr);
				if (d.matches()) {
					int day = Integer.parseInt(d.group(1));
					int month = Integer.parseInt(d.group(2));
					int year = Integer.parseInt(d.group(3));
					System.out.println(day + "/" + month + "/" + year);
					node k = new node();
					k.index = i;
					k.contend = Row.get(i);
					k.type = 1;
					templist.add(k);
				}
				/*
				 * if (w.matches()) { int weight = Integer.parseInt(w.group());
				 * System.out.println("weight: " + weight); }
				 */
				if (h.matches()) {
					int height1 = Integer.parseInt(h.group(1));
					int height2 = Integer.parseInt(h.group(2));
					System.out.println("height: " + height1 + "-" + height2);
					node k = new node();
					k.index = i;
					k.contend = Row.get(i);
					templist.add(k);
				}
				if (ec.matches()) {
					int ec1 = Integer.parseInt(ec.group(1));
					int ec2 = Integer.parseInt(ec.group(2));
					int ec3 = Integer.parseInt(ec.group(3));
					int ec4 = Integer.parseInt(ec.group(4));
					System.out.println("EC_num: " + ec1 + "." + ec2 + "." + ec3
							+ "." + ec4);
					node k = new node();
					k.index = i;
					k.contend = Row.get(i);
					templist.add(k);
				}

			}
			/*********************** Remaining List Collection *********************************************/
			ArrayList<sublist> Sub = new ArrayList<sublist>();
			ArrayList<Field> record = new ArrayList<Field>();
			if (templist.size() == 0) {
				sublist subline = new sublist();
				subline.list = new ArrayList<node>();
				for (int i = 0; i < Row.size(); i++) {
					node Node = new node();
					Node.contend = Row.get(i);
					Node.index = i;
					Node.type = 0;
					subline.list.add(Node);
				}
				Sub.add(subline);
			} else {
				int[] index = new int[templist.size()];
				for (int i = 0; i < templist.size(); i++) {
					index[i] = templist.get(i).index;
				}

				Arrays.sort(index);
				Sub = ListSplit(index, Row);
				record = new ArrayList<Field>();
				for (int i = 0; i < templist.size(); i++) {
					Field field = new Field();
					field.list = new ArrayList<node>();
					field.list.add(templist.get(i));
					field.type = 1;
					record.add(field);
				}
			}
			/****************************** Likelyhood Score **********************************************/
			ArrayList<Integer> fieldlist = new ArrayList<Integer>();
			for (int i = 0; i < Sub.size(); i++) {
				fieldlist = ProbTable.FiedIndex(Sub.get(i).list);
				Collections.sort(fieldlist);
				int startpoint = 0;
				while (fieldlist.size() != 0) {
					Field F = new Field();
					F.list = new ArrayList<node>();
					F.type = 0;
					for (int j = startpoint; j < fieldlist.get(0) + 1; j++) {
						F.list.add(Sub.get(i).list.get(j));
					}
					record.add(F);
					startpoint = fieldlist.get(0) + 1;
					fieldlist.remove(0);

				}
				Field F2 = new Field();
				F2.list = new ArrayList<node>();
				F2.type = 0;
				for (int j = startpoint; j < Sub.get(i).list.size(); j++) {
					F2.list.add(Sub.get(i).list.get(j));
				}
				record.add(F2);
			}
			//
			Collections.sort(record);
			Lineindex++;
			R.record = record;
			Table.add(R);
		}
		
		
		
		
		
		
		
		/********************* Allignment Step ************************************/
		
		
		
		
		
		int column = 3;
		for (int i = 0; i < Table.size(); i++) {
			if (Table.get(i).record.size() == column) {
				for (int j = 0; j < Table.get(i).record.size(); j++) {
					Table.get(i).record.get(j).fieldnum = j + 1;
				}
			} else if (Table.get(i).record.size() < column) {
				for (int j = 0; j < Table.get(i).record.size(); j++) {
					Table.get(i).record.get(j).fieldnum = j + 1;
				}
				Field NullField = new Field();
				NullField.fieldnum = 4;
				node nullnode = new node();
				nullnode.contend = "NULL";
				NullField.list = new ArrayList<node>();
				NullField.list.add(nullnode);
				Table.get(i).record.add(NullField);
			} else if (Table.get(i).record.size() > column) {
				String a, b;
				double min = 2;
				int index = -1;
				double prob = 0;
				int index1 = -1, index2 = -1;
				ProbTable map = new ProbTable();
				for (int j = 0; j < Table.get(i).record.size() - 1; j++) {
					if (Table.get(i).record.get(j).type
							+ Table.get(i).record.get(j + 1).type == 0) {
						a = Table.get(i).record.get(j).list
								.get(Table.get(i).record.get(j).list.size() - 1).contend;
						b = Table.get(i).record.get(j + 1).list.get(0).contend;
						index2 = map.search(a);
						index1 = map.search(b);
						prob = map.table[index1][index2];
					} else {
						prob = 50;
					}
					if (min > prob) {
						min = prob;
						index = j;
					}
				}
				for (int j = 0; j < Table.get(i).record.get(index + 1).list
						.size(); j++) {
					Table.get(i).record.get(index).list.add(Table.get(i).record
							.get(index + 1).list.get(j));
				}
				Table.get(i).record.remove(index + 1);
				System.out.println("d");
			}
		}
		/********************* Allignment Step ************************************/
		String text = "";
		for (int i = 0; i < Table.get(0).record.get(0).list.size(); i++) {

			text = text.concat(Table.get(0).record.get(0).list.get(i).contend);
			text = text.concat(" ");
			System.out.print(Table.get(0).record.get(0).list.get(i).contend
					+ " ");
		}
		System.out.println();
		System.out.println(text);
		Excel.generate(Table);
		System.out.println("over");

	}

	/********** Sublist Extraction ***************/
	public static ArrayList<sublist> ListSplit(int[] index,
			ArrayList<String> Row) {
		int t = index.length;
		ArrayList<sublist> Sub = new ArrayList<sublist>();
		if (index[0] > 0) {
			sublist s = new sublist();
			s.list = new ArrayList<node>();
			for (int i = 0; i < index[0]; i++) {
				node tnode = new node();
				tnode.contend = Row.get(i);
				tnode.index = i;
				s.list.add(tnode);
			}
			Sub.add(s);
		}
		if (index[index.length - 1] < Row.size() - 1) {
			sublist s = new sublist();
			s.list = new ArrayList<node>();
			for (int i = index[index.length - 1] + 1; i < Row.size(); i++) {
				node tnode = new node();
				tnode.contend = Row.get(i);
				tnode.index = i;
				s.list.add(tnode);
			}
			Sub.add(s);
		}
		if (index.length > 1) {
			int a = 0, b = 0;
			for (int i = 0; i < index.length - 1; i++) {
				a = index[i];
				b = index[i + 1];
				if (b - a > 1) {
					sublist s = new sublist();
					s.list = new ArrayList<node>();
					for (int j = a + 1; j < b; j++) {
						node tnode = new node();
						tnode.contend = Row.get(j);
						tnode.index = j;
						s.list.add(tnode);
					}
					Sub.add(s);
				}
			}

		}

		return Sub;
	}

}
