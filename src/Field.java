import java.util.ArrayList;

/**
 * 
 */

/**
 * @author zhuofu
 *
 */
public class Field implements Comparable{
	ArrayList<node> list;
	int type=0;
	int fieldnum=0;
	/**
	 * 
	 */
	
	
       

	public Field() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


	

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		int newstate = this.list.get(0).index - ((Field)o).list.get(0).index;
        return newstate;
		
	}

}
