package movies_reviews;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

public class Directory2arff {

	
	
	
	
	public void d2arff(String source, String dest){
		File dir = new File(source);
		ArrayList<Attribute> atts = new ArrayList<>();
		ArrayList<String> klaseBalio = new ArrayList<>();
		klaseBalio.add("neg");
		klaseBalio.add("pos");
	    atts.add(new Attribute("klasea",klaseBalio));
	    atts.add(new Attribute("testua",true));
	    Instances data = new Instances("text_files_in_" + source, atts, 0);
		
		String[] karpetak = dir.list();
		for(String kar : karpetak){
			this.instantziakGehitu(source+File.separator+kar, kar, data);
		}
		String name;
		if(dest!=null){
			name = dest;
		}
		else{
			name = dir.getName()+".arff";
		}
		ArffKargatu.arffSortu(name, data);
		
	}
	

	
	
	
	private Instances instantziakGehitu(String source, String klasea, Instances data) {
		File karpeta = new File(source);
		int i =0;
		for(String fiIzena : karpeta.list()){
			i++;
			double[] newInst = new double[2];
			if(klasea!=null){
				newInst[0] = (double)data.attribute(0).indexOfValue(klasea);
			}
		    try {
		    	File fi = new File(source+File.separator+fiIzena);
		    	InputStreamReader is;
		        is = new InputStreamReader(new FileInputStream(fi));
		        StringBuffer txtStr = new StringBuffer();
		        int c;
		        while ((c = is.read()) != -1) {
		          txtStr.append((char)c);
		        }
		        String text = txtStr.toString();
		        String newstr = text.replaceAll("-", " ");
		        String newstr2 = newstr.replaceAll("[\\x00-\\x1F\\x21-\\x2F\\x3A-\\x40\\x5B-\\x60\\x7B-\\x7F]", "");
		        String zuriGabe = newstr2.replaceAll(" +", " ");
		        if(i==17){
		        	System.out.println(text);
		        	System.out.println("\n\n\n"+newstr);
		        	System.out.println("\n\n\n"+zuriGabe);
		        }
//		        String newstr = text.replaceAll("[\\x20,\\x30-\\x39,\\x41-\\x5A,\\x61-\\x7A]", "");
//		        String newstr = text.replaceAll("[\\p{ASCII}&&\\P{Alnum}]", "");
		        newInst[1] = (double)data.attribute(1).addStringValue(zuriGabe);
		        DenseInstance ins =new DenseInstance(1.0, newInst);
		        if(klasea==null){
		        	ins.setMissing(0);
		        }
		        data.add(ins);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		}
		
		return data;
	}


	public void blind(String source, String dest){
		File dir = new File(source);
		ArrayList<Attribute> atts = new ArrayList<>();
		ArrayList<String> klaseBalio = new ArrayList<>();
		klaseBalio.add("neg");
		klaseBalio.add("pos");
	    atts.add(new Attribute("klasea",klaseBalio));
	    atts.add(new Attribute("testua",true));
	    Instances data = new Instances("text_files_in_" + source, atts, 0);
		
	    this.instantziakGehitu(source, null, data);

		String name;
		if(dest!=null){
			name = dest;
		}
		else{
			name = dir.getName()+".arff";
		}
		ArffKargatu.arffSortu(name, data);
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Directory2arff d = new  Directory2arff();
		d.d2arff("C:\\Users\\amitx\\Downloads\\movies_reviews\\dev",null);
		
	}

}