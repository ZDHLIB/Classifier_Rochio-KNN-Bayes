package CreateWordsTable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import FileOperate.ReadFromFile;
import FileOperate.WriteFile;

/*
 * �������õĴʱ���н�ά����ȥͣ�ô˱��еĴ�
 */

public class UsefulWordsTable {

	public void GetWorthlessWords() {
		String useLessWords = "";
		String splitWord = "";   
		String[] SplitString = new String []{};
		
		ReadFromFile ReadFile = new ReadFromFile();
		WriteFile writeFile = new WriteFile();
		
		//��ȡͣ�ôʱ��еĴʣ���������ַ���������
		System.out.println("��ȡͣ�ô�");
		useLessWords = ReadFile.readFileByLines("WorthlessWords.txt");
		System.out.println("��ȡ�ʱ�,ȥͣ�ô�");
		splitWord = ReadFile.readFileByLines("WordsTable.txt");
		SplitString = useLessWords.split("#");//��ȡ�ļ����ݣ��Կո�ָ� 
		for( int i = 0; i < SplitString.length; i++ ) {
			splitWord = splitWord.replaceAll("#[\\r\\n]*"+SplitString[i]+"[\\r\\n]*", "");
		}

		writeFile.writeFile(splitWord, "WordsTable", "txt", "GBK");
		
	}
	
	public static void main(String[] args) {
		UsefulWordsTable secondWordsTable = new UsefulWordsTable();
		secondWordsTable.GetWorthlessWords();
	}
	
}
