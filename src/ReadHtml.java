import java.net.*;
import java.io.*;
/**
 * @author zhuofu
 * 
 */
class ReadHtml{

public static void main(String[] arg){
try{
URL url=new URL("http://home.roadrunner.com/~tuco/looney/50greatest.html");
System.out.println("Connecting to www.yahoo.com");
URLConnection ucon=url.openConnection();
System.out.println("Connectied to www.yahoo.com");
System.out.println("Retrieving contents from www.yahoo.com");
String htmlContents=getResponseData(ucon);
System.out.println("Retrieved contents from Yahoo! as follows");
System.out.println(htmlContents);
System.out.println("end");
}
catch(Exception e)
{
e.printStackTrace();
}

}

private static String getResponseData(URLConnection conn) throws Exception{
StringBuffer sb=new StringBuffer();
String data="";
InputStream is=conn.getInputStream();
int ch;
while((ch=is.read()) != -1){
sb.append((char)ch);
}

data=sb.toString();
is.close();
is=null;
sb=null;
System.gc();
return data;
}

}