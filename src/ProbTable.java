import java.util.ArrayList;

/**
 * @author zhuofu
 *
 */
public class ProbTable {

	/**
	 * 
	 */
	node []library;
	double [][]table;
	public ProbTable() {
		// TODO Auto-generated constructor stub
		library=new node[5];
		library[0]=new node();
		library[0].contend="->";
		library[0].index=0;
		library[1]=new node();
		library[1].contend="<-";
		library[1].index=1;
		library[2]=new node();
		library[2].contend="<->";
		library[2].index=2;
		library[3]=new node();
		library[3].contend="Phosphate";
		library[3].index=3;
		library[4]=new node();
		library[4].contend="null";
		library[4].index=4;
		
		double[][]table0={{0,0,0,1,1},
						  {0,0,0,1,1},
						  {0,0,0,1,1},
						  {1,1,1,0,1},
						  {1,1,1,0,0.1}};
		table=table0;
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String Line="In flux Glucose glucose transporter";
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
		String a,b;
		int index1,index2;
		a="1-Phosphate";
		double Prob=0;
		double min=10, splitpoint=0;
		System.out.println(a.substring(a.length()-9,a.length()));
		ProbTable map=new ProbTable();
		for(int k=1;k<Row.size()-1;k++){
			if(k==1){}else{
			for(int i=0;i<k-1;i++){				
				a=Row.get(i);
				b=Row.get(i+1);
				if (a.compareTo(b)==0){
					Prob=Prob+0;
				}else{
				index2=map.search(a);
				index1=map.search(b);
				Prob+=map.table[index1][index2];}
				//System.out.println(Prob);
			}
			}
			if(k+1==Row.size()-1){}
			else{
			for(int i=k;i<Row.size()-1;i++){
				if(k==Row.size()-2){}else{
				a=Row.get(i);
				b=Row.get(i+1);
				if (a.compareTo(b)==0){
					
				}else{
				index2=map.search(a);
				index1=map.search(b);
				Prob+=map.table[index1][index2];}
				//System.out.println(Prob);
				}
			}
			}
			a=Row.get(k-1);
			b=Row.get(k);
			if (a.compareTo(b)==0){
				
			}else{
			index2=map.search(a);
			index1=map.search(b);
			Prob=Prob+map.table[index1][index2];}
			if (Prob<min){min=Prob; splitpoint=k;}
			Prob=0;
		}
		System.out.println("min "+min+"split point "+splitpoint);
		System.out.println("END");
	}
	
	public static int split(ArrayList<node> list){
		double max=0;
		double min=10;
		int splitpoint=-1;
		String a,b;
		ProbTable map=new ProbTable();
		double temp;
		int index1,index2;
		for(int i=0;i<list.size()-1;i++){
		a=list.get(i).contend;
		b=list.get(i+1).contend;
			if (a.compareTo(b)==0)
			{
				temp=0;
			}
			else
			{
				index2=map.search(a);
				index1=map.search(b);
				temp=map.table[index1][index2];
			}		
		if(temp>max){max=temp;}
		if(temp<min){min=temp;splitpoint=i;}
		}
		if(max==min){splitpoint=-1;}
		return splitpoint;		
	}
	public static ArrayList<Integer> FiedIndex(ArrayList<node> list){
		ArrayList<Integer> field=new ArrayList<Integer>();
		ArrayList<node> Leftlist=new ArrayList<node>();
		ArrayList<node> Rightlist=new ArrayList<node>();
		int temp=0;
		temp=split(list);
		if(temp>-1){field.add(temp);			
			for(int i=0;i<list.size();i++)
			{
				if(i>temp)
					{Rightlist.add(list.get(i));
					}else
					{
						Leftlist.add(list.get(i));
					}
			}
			
			temp=split(Rightlist);
			if(temp>-1){field.add(temp);}
			temp=split(Leftlist);
			if(temp>-1){field.add(temp);}
		}
		else{}
		
		return field;
	}
	public int search(String a){
		int s=4;
		int size=a.length();
		if(size>9){
			a=a.substring(size-9, size);
		}
		for (int i=0;i<library.length;i++){
			if(a.compareTo(library[i].contend)==0){
				s=i;
			}
		}
		
		return s;
	}
	
	public static void candifield(){
		
	}
}
