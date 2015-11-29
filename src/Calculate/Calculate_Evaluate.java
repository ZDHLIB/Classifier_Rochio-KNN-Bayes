package Calculate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.jdom.Element;

public class Calculate_Evaluate {
	
	private static double [] KNNresult = new double[]{ 0.0,0.0,0.0,0.0};
	private static double [] ROCCHIOresult = new double[]{ 0.0,0.0,0.0,0.0};
	private static double [] BAYESresult = new double[]{ 0.0,0.0,0.0,0.0};
	private static TreeMap<String,Integer[]> evaluateKNN = new TreeMap<String,Integer[]>();
	private static TreeMap<String,Integer[]> evaluateROCCHIO = new TreeMap<String,Integer[]>();
	private static TreeMap<String,Integer[]> evaluateBAYES = new TreeMap<String,Integer[]>();
	
	public void setKNNCategory( List<Element> categoryList ){
		String category = "";
		for( Element e : categoryList ){
			//value:TP,FP,FN,TN
			Integer [] value = new Integer[]{0,0,0,0};
			category = e.getAttributeValue("name");
			evaluateKNN.put(category, value);
		}
	}
	
	public TreeMap<String,Integer[]> getKNNCategory(){
		return evaluateKNN;
	}
	
	public void setROCCHIOCategory( List<Element> categoryList ){
		String category = "";
		for( Element e : categoryList ){
			//value:TP,FP,FN,TN
			Integer [] value = new Integer[]{0,0,0,0};
			category = e.getAttributeValue("name");
			evaluateROCCHIO.put(category, value);
		}
	}
	
	public TreeMap<String,Integer[]> getROCCHIOCategory( ){
		return evaluateROCCHIO;
	}
	
	public void setBAYESCategory( List<Element> categoryList ){
		String category = "";
		for( Element e : categoryList ){
			//value:TP,FP,FN,TN
			Integer [] value = new Integer[]{0,0,0,0};
			category = e.getAttributeValue("name");
			evaluateBAYES.put(category, value);
		}
	}
	
	public TreeMap<String,Integer[]> getBAYESCategory( ){
		return evaluateBAYES;
	}
	
	public double [] getKnnEvaluate(){
		return KNNresult;
	}
	
	public double [] getROCCHIOEvaluate(){
		return ROCCHIOresult;
	}
	
	public double [] getBAYESEvaluate(){
		return BAYESresult;
	}
	

	public void CKNN_Evaluate( String real_Category, String test_Category ){
		
		Object category;
		
		System.out.println("!!!!!!!!!!!!!!!!!!!!real_Category="+real_Category+"  test_Category="+test_Category);
		
		
		for (Iterator it = evaluateKNN.keySet().iterator(); it.hasNext();) {
			category = it.next();
			Integer [] value = new Integer[]{0,0,0,0};
			value = evaluateKNN.get(category);
			
			//专家认为属于Category
			if( String.valueOf(category).equals(real_Category) ){
				if( real_Category.equals(test_Category) ){
					//TP++, 专家认为属于real_Category且分类器认为属于real_Category
					value[0]++;
				}else {
					//FN++, 专家认为属于real_Category但分类器认为不属于real_Category
					value[2]++;
				}
			} 
			//专家认为不属于Category
			else {
				if( String.valueOf(category).equals(test_Category) ){
					//FP++, 专家认为不属于Category但分类器认为属于real_Category
					value[1]++;
				}else {
					//TN++, 专家认为不属于Category且分类器认为不属于real_Category
					value[3]++;
				}
			}
			
			evaluateKNN.put(String.valueOf(category), value);
		}
		
	}
	
	public void CROCCHIO_Evaluate( String real_Category, String test_Category ){
		
		Object category;
		
		for (Iterator it = evaluateROCCHIO.keySet().iterator(); it.hasNext();) {
			category = it.next();
			Integer [] value = new Integer[]{0,0,0,0};
			value = evaluateROCCHIO.get(category);
			
			//专家认为属于Category
			if( String.valueOf(category).equals(real_Category) ){
				if( real_Category.equals(test_Category) ){
					//TP++, 专家认为属于real_Category且分类器认为属于real_Category
					value[0]++;
				}else {
					//FN++, 专家认为属于real_Category但分类器认为不属于real_Category
					value[2]++;
				}
			} 
			//专家认为不属于Category
			else {
				if( String.valueOf(category).equals(test_Category) ){
					//FP++, 专家认为不属于Category但分类器认为属于real_Category
					value[1]++;
				}else {
					//TN++, 专家认为不属于Category且分类器认为不属于real_Category
					value[3]++;
				}
			}
			
			evaluateROCCHIO.put(String.valueOf(category), value);
		}
		
	}
	
	public void CBAYES_Evaluate( String real_Category, String test_Category ){
		
		Object category;
		
		for (Iterator it = evaluateBAYES.keySet().iterator(); it.hasNext();) {
			category = it.next();
			Integer [] value = new Integer[]{0,0,0,0};
			value = evaluateBAYES.get(category);
			
			//专家认为属于Category
			if( String.valueOf(category).equals(real_Category) ){
				if( real_Category.equals(test_Category) ){
					//TP++, 专家认为属于real_Category且分类器认为属于real_Category
					value[0]++;
				}else {
					//FN++, 专家认为属于real_Category但分类器认为不属于real_Category
					value[2]++;
				}
			} 
			//专家认为不属于Category
			else {
				if( String.valueOf(category).equals(test_Category) ){
					//FP++, 专家认为不属于Category但分类器认为属于real_Category
					value[1]++;
				}else {
					//TN++, 专家认为不属于Category且分类器认为不属于real_Category
					value[3]++;
				}
			}
			
			evaluateBAYES.put(String.valueOf(category), value);
		}
		
	}
	
	public void C_KNNEvaluate(TreeMap<String,Integer[]> evaluate){
		
		Double [] value = new Double[]{0.0,0.0,0.0,0.0};
		DecimalFormat df = new DecimalFormat("##.000");
		
		for (Iterator it = evaluate.keySet().iterator(); it.hasNext();) {

			Integer [] temp = new Integer[]{0,0,0,0};
			temp = evaluate.get(it.next());
			value[0] += temp[0];
			value[1] += temp[1];
			value[2] += temp[2];
			value[3] += temp[3];
		}
		
//		value[0] = value[0] / evaluate.size();
//		value[1] = value[1] / evaluate.size();
//		value[2] = value[2] / evaluate.size();
//		value[3] = value[3] / evaluate.size();
		System.out.println("@@@@@@@@@@@@@@value="+value[0]+"  "+value[1]+"  "+value[2]+"  "+value[3]);
		
		//计算查全率
		KNNresult[0] = Double.valueOf(df.format( value[0]/( value[0] + value[2] ) ) );
		
		//计算查准率
		KNNresult[1] = Double.valueOf(df.format( value[0] / ( value[0] + value[1] ) ) );
		
		//计算F值
		KNNresult[2] = Double.valueOf(df.format( 2 * ( KNNresult[0] * KNNresult[1] ) / ( KNNresult[0] + KNNresult[1] ) ) );
		
		//计算正确率
		KNNresult[3] = Double.valueOf(df.format(( value[0] + value[3] ) / ( value[0] + value[1] + value[2] + value[3] )) );
		
		System.out.println("#################KNNresult="+KNNresult[0]+" "+KNNresult[1]+" "+KNNresult[2]+" "+KNNresult[3]);
	}
	
	public void C_ROCCHIOEvaluate(TreeMap<String,Integer[]> evaluate){
		
		Double [] value = new Double[]{0.0,0.0,0.0,0.0};
		DecimalFormat df = new DecimalFormat("##.000");
		
		for (Iterator it = evaluate.keySet().iterator(); it.hasNext();) {

			Integer [] temp = new Integer[]{0,0,0,0};
			temp = evaluate.get(it.next());
			value[0] += temp[0];
			value[1] += temp[1];
			value[2] += temp[2];
			value[3] += temp[3];
		}
		
		//计算查全率
		ROCCHIOresult[0] = Double.valueOf(df.format( value[0]/( value[0] + value[2] ) ) );
		
		//计算查准率
		ROCCHIOresult[1] = Double.valueOf(df.format( value[0] / ( value[0] + value[1] ) ) );
		
		//计算F值
		ROCCHIOresult[2] = Double.valueOf(df.format( 2 * ( ROCCHIOresult[0] * ROCCHIOresult[1] ) / ( ROCCHIOresult[0] + ROCCHIOresult[1] ) ) );
		
		//计算正确率
		ROCCHIOresult[3] = Double.valueOf(df.format(( value[0] + value[3] ) / ( value[0] + value[1] + value[2] + value[3] )) );
		
		System.out.println("#################ROCCHIOresult="+ROCCHIOresult[0]+" "+ROCCHIOresult[1]+" "+ROCCHIOresult[2]+" "+ROCCHIOresult[3]);
	}
	
	public void C_BAYESEvaluate(TreeMap<String,Integer[]> evaluate){
		
		Double [] value = new Double[]{0.0,0.0,0.0,0.0};
		DecimalFormat df = new DecimalFormat("##.000");
		
		for (Iterator it = evaluate.keySet().iterator(); it.hasNext();) {

			Integer [] temp = new Integer[]{0,0,0,0};
			temp = evaluate.get(it.next());
			value[0] += temp[0];
			value[1] += temp[1];
			value[2] += temp[2];
			value[3] += temp[3];
		}
		System.out.println("@@@@@@@@@@@@@@value="+value[0]+"  "+value[1]+"  "+value[2]+"  "+value[3]);
		
		
		//计算查全率
		BAYESresult[0] = Double.valueOf(df.format( value[0]/( value[0] + value[2] ) ) );
		
		//计算查准率
		BAYESresult[1] = Double.valueOf(df.format( value[0] / ( value[0] + value[1] ) ) );
		
		//计算F值
		BAYESresult[2] = Double.valueOf(df.format( 2 * ( BAYESresult[0] * BAYESresult[1] ) / ( BAYESresult[0] + BAYESresult[1] ) ) );
		
		//计算正确率
		BAYESresult[3] = Double.valueOf(df.format(( value[0] + value[3] ) / ( value[0] + value[1] + value[2] + value[3] )) );
		
		System.out.println("#################BAYESresult="+BAYESresult[0]+" "+BAYESresult[1]+" "+BAYESresult[2]+" "+BAYESresult[3]);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
