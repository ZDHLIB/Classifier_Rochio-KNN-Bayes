package CreateWordsTable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import FileOperate.ReadFromFile;
import FileOperate.WriteFile;

/*
 * 将建立好的词表进行降维，除去停用此表中的词
 */

public class UsefulWordsTable {

	public void GetWorthlessWords() {
		String useLessWords = "";
		String splitWord = "";   
		String[] SplitString = new String []{};
		
		ReadFromFile ReadFile = new ReadFromFile();
		WriteFile writeFile = new WriteFile();
		
		//提取停用词表中的词，将其存入字符串数组中
		System.out.println("读取停用词");
		useLessWords = ReadFile.readFileByLines("WorthlessWords.txt");
		System.out.println("读取词表,去停用词");
		splitWord = ReadFile.readFileByLines("WordsTable.txt");
		SplitString = useLessWords.split("#");//读取文件内容，以空格分割 
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
