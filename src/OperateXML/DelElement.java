package OperateXML;

import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class DelElement {
	
	public boolean formatXML(String fileName){
        boolean isOk = false;
        try{
            SAXReader reader = new SAXReader();
            org.dom4j.Document doc = reader.read(new File(fileName));
            XMLWriter formatWriter = null;
            /**
             *��ʽ�����,����IE���һ��
             *ָ��XML����
             */
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("GBK");
            formatWriter = new XMLWriter(new FileWriter(new File(fileName)), format);
            formatWriter.write(doc);
            formatWriter.close();

            isOk = true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return isOk;
    }

	public boolean delElement(String oldFileName,String newFileName,String delFileName){
		String fname=delFileName;
		DelElement  delelement=new DelElement();
		 boolean isOk = false;
	        try{
	            SAXReader reader = new SAXReader();
	            Document doc = reader.read(new File(oldFileName));
	            List list = doc.selectNodes("/files/file");
	            Iterator iter = list.iterator();
	            while(iter.hasNext()){
	                Element elem = (Element)iter.next();
	                Iterator iterElem = elem.attributeIterator();
	                Attribute attribute = (Attribute) iterElem.next();
	                if(attribute.getData().toString().equals(fname)){
	                        elem.detach();  
	                //detach()��������ɾ�������ڵ�����ӽڵ㣬remove()����ֻɾ�����������ԵĽڵ㣬�������ӽڵ�
	                }
	            }
	            //��doc�е�����д���ļ���
	            try{
	                FileWriter newFile = new FileWriter(new File(newFileName));
	                XMLWriter newWriter = new XMLWriter(newFile);
	                newWriter.write(doc);
	                newWriter.close();
	            }catch(Exception e){
	                e.printStackTrace();
	            }
	            delelement.formatXML(newFileName);
	            isOk = true;
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	        return isOk;
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DelElement de = new DelElement();
        String FileName = "D:\\Java\\lrc\\classify\\ymake.xml";
        de.delElement(FileName, FileName,"��������.doc");

	}

}
