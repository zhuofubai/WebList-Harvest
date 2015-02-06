
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * 
 */

/**
 * @author zhuofu
 *
 */
public class Excel {

	/**
	 * 
	 */
	public Excel() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		          WritableWorkbook wwb = null;    
		          String fileName="C:/ab.xls";
		         try {    
		              
		               wwb = Workbook.createWorkbook(new File(fileName)); 
		               
		          } catch (IOException e) {    
		             e.printStackTrace();    
		          }    
		         if(wwb!=null){    
		          
		             WritableSheet ws = wwb.createSheet("sheet1", 0);    
		                
		             
		            for(int i=0;i<10;i++){    
		                for(int j=0;j<5;j++){    
		                       
		                     Label labelC = new Label(j, i, "+(i+1)+""+(j+1)+"");    
		                    try {    
		                         
		                       ws.addCell(labelC);    
		                    } catch (RowsExceededException e) {    
		                       e.printStackTrace();    
		                   } catch (WriteException e) {    
		                       e.printStackTrace();    
		                     }    
		                 }    
		              }    
		   
		             try {    
		                    
		                 wwb.write();    
		                   
		                wwb.close();    
		             } catch (IOException e) {    
		                e.printStackTrace();    
		            } catch (WriteException e) {    
	                e.printStackTrace();    
		             }    
		        }    
		    }    
	public static void generate(ArrayList<Records>Table) {
		// TODO Auto-generated method stub
		          WritableWorkbook wwb = null;    
		          String fileName="Table.xls";
		         try {    
		              
		               wwb = Workbook.createWorkbook(new File(fileName)); 
		               
		          } catch (IOException e) {    
		             e.printStackTrace();    
		          }    
		         if(wwb!=null){    
		                 
		             WritableSheet ws = wwb.createSheet("sheet1", 0);    
		                
		               
		            for(int i=0;i<Table.size();i++){    
		                for(int j=0;j<Table.get(0).record.size();j++){    
		                      
		                	String text=contend(Table,  i, j);
		                     Label labelC = new Label(j, i, text);    
		                    try {    
		                           
		                       ws.addCell(labelC);    
		                    } catch (RowsExceededException e) {    
		                       e.printStackTrace();    
		                   } catch (WriteException e) {    
		                       e.printStackTrace();    
		                     }    
		                 }    
		              }    
		   
		             try {    
		                   
		                 wwb.write();    
		                     
		                wwb.close();    
		             } catch (IOException e) {    
		                e.printStackTrace();    
		            } catch (WriteException e) {    
	                e.printStackTrace();    
		             }    
		        }    
		    } 
	public static String contend(ArrayList<Records>Table, int row, int col){
		String text="";
		for(int i=0;i<Table.get(row).record.get(col).list.size();i++){
			
			
			text=text.concat(Table.get(row).record.get(col).list.get(i).contend);
			text=text.concat(" ");
		}
		return text;
	}

}
