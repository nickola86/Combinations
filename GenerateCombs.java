import java.util.Vector;
import java.math.BigDecimal;
public class GenerateCombs{

	public static void main(String[] args){
		
		int n=0;
		try{
			n=new Integer(args[0]);
		}catch(Exception e){
			n=10;
		}
		
		//test1(n);	
		//test2(n);
		test3(n);
	}
	
	public static void test1(int n){
		Generatore g;
		CombinationList comblist;
		CombinationList cleancomblist;
		
		for(double p=2; p<100;p++){
			CombinationList.perc=p/100.0;
			//System.out.println("\nCLEAN PERC :\t"+CombinationList.perc);
			for(n=1;n<=10;n++){
				g = new Generatore(n);
				comblist = new CombinationList(g.toVector());
				cleancomblist = comblist.cleanX();
				//csv format
				System.out.println(n+";"+CombinationList.getCleanRatio(comblist,cleancomblist)+";"+CombinationList.percX+";");
			}
		}
	}
	
	public static void test2(int n){
		Generatore g;
		CombinationList comblist;
		CombinationList cleancomblist;
		
		g = new Generatore(n);
		comblist = new CombinationList(g.toVector());
		comblist.stats();
		cleancomblist = comblist.cleanX();
		cleancomblist.stats();		
		cleancomblist = cleancomblist.clean1();
		cleancomblist.stats();
		cleancomblist = cleancomblist.clean2();
		cleancomblist.stats();
		System.out.println(n+")\t"+"CLEAN RATIO: "+CombinationList.getCleanRatio(comblist,cleancomblist)+" %");
		
	}
	public static void test3(int n){
		Generatore g;
		CombinationList comblist;
		CombinationList cleancomblist;
		
		g = new Generatore(n);
		comblist = new CombinationList(g.toVector());
		comblist.printSize();
		cleancomblist = comblist.cleanX();
		cleancomblist.printSize();		
		cleancomblist = cleancomblist.clean1();
		cleancomblist.printSize();
		cleancomblist = cleancomblist.clean2();
		cleancomblist.printSize();
		System.out.println(n+")\t"+"CLEAN RATIO: "+CombinationList.getCleanRatio(comblist,cleancomblist)+" %");
		
		cleancomblist.print();
		
	}
}

class CombinationList{
	private Vector<String> comblist;
	public static double perc=0.0;
	public static double perc1=8.0/10.0;
	public static double perc2=7.0/10.0;
	public static double percX=6.0/10.0;
	private int countSymbols(String seq,char sym){
		int count = 0;
		for (int i = 0 ; i<seq.length(); i++){
			if(seq.charAt(i) == sym){
				count ++ ;
			}
		}
		//System.out.println(seq+"\t"+sym+"\t"+count);
		return count;
	}
	public static double getCleanRatio(CombinationList dirty, CombinationList clean){
		return (new BigDecimal(100.0*(1.0-(new Double(clean.size())/new Double(dirty.size()))))).setScale(6,BigDecimal.ROUND_UP).doubleValue();
	}
	public CombinationList(){
		this.comblist = new Vector<String>();
	}
	public CombinationList(Vector<String> cl){
		this.comblist = cl;
	}
	public Vector<String> getCombinationList(){
		return this.comblist;
	}
	public void setCombinationList(Vector<String> cl){
		this.comblist = cl;
	}
	public void print(){
		if(this.comblist.size()==0){
			System.out.println("Empty List");
			return;
		}
		for(int i=0; i<this.comblist.size(); i++){
			System.out.println(this.comblist.get(i));
		}
	}
	
	private CombinationList clean(char sym,double perc){
		System.out.println("CLEANING FROM "+sym);
		Vector<String> out = new Vector<String>();
		for (int i=0; i<this.comblist.size(); i++){
			if((new Double(countSymbols(this.comblist.get(i),sym))/new Double(this.comblist.get(i).length())) <= perc ){
				out.add(this.comblist.get(i));
			}
		}
		return new CombinationList(out);
	}
	public CombinationList clean1(){
		return clean('1',CombinationList.perc1);
	}
	public CombinationList clean2(){
		return clean('2',CombinationList.perc2);		
	}
	public CombinationList cleanX(){
		return clean('X',CombinationList.percX);
	}
	public String get(int i){
		return this.comblist.get(i);
	}
	public void set(int i,String s){
		this.comblist.set(i,s);
	}
	public int size(){
		return this.comblist.size();
	}
	public void stats(){
		this.print();
		this.printSize();
	}
	public void printSize(){
		System.out.println("LIST SIZE: "+size());
	}
}



class Generatore{
	
	private int length;
	public Generatore(int n){
		this.length = n;
	}
	
	public Vector<String> toVector(){
		Vector<String> out = new Vector<String>();
		int i = 0;
		String s;
		s=substitute(zeropadding(tobase3(i)));
		while(s.length() <= this.length){
			out.add(s);
			i++;
			s=substitute(zeropadding(tobase3(i)));
		}
		return out;
	}
	
	public void print(){
		int i = 0;
		String s;
		s=substitute(zeropadding(tobase3(i)));
		while(s.length() <= this.length){
			System.out.println(s);
			i++;
			s=substitute(zeropadding(tobase3(i)));
		}
	}
	public String tobase3(int i){
		int j = i;
		String s = "";
		while(j/3 >= 1){
			s = (j%3) + s;
			j=(j-j%3)/3;
		}
		return (j%3)+s;
	}
	public String zeropadding(String s){
		while(s.length()<this.length)
			s="0"+s;
		return s;
	}
	public String substitute(String s){
		String t = "";
		for(int i=0;i<s.length();i++)
			if(s.charAt(i)=='0')
				t+='X';
			else
				t+=s.charAt(i);
		return t;
	}
}