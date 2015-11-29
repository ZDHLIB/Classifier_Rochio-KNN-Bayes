package CreateWordsTable;

import FileOperate.*;
import InterfaceMain.*;
/*
 * 建立关键词词表，存于WordsTable.txt
 */

public class CreateWordTable {

	public String CreateTable() {
		String ResultString = "";
		String[] SplitString = new String []{};
		
		ReadFromFile ReadFile = new ReadFromFile();
		WriteFile writeFile = new WriteFile();

		ResultString = ReadFile.readTxtFile("SplitResult.txt");
	//	System.out.println(ResultString);
		SplitString = ResultString.split(" ");//读取文件内容，以"/*.? "分割 
		ResultString = "";
		for( int i = 0; i < SplitString.length; i++ ){
			if( !SplitString[i].equals(null) ) {
				ResultString += "#"+SplitString[i];
				ResultString += "\n";
			//	System.out.println(ResultString);
			}
		}
		ResultString = ResultString.replaceAll("\\r\\n*", "");
		//建立分割后的词表WordsTable.txt
		writeFile.writeFile(ResultString, "WordsTable", "txt", "GBK");
		
		return ResultString;
	}
	/*
	public static void main(String[] args) {
		CreateWordTable createWordTable = new CreateWordTable();
		createWordTable.CreateTable();
	}*/
}


