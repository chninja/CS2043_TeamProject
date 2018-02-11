package project.group4;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;

public class ExcelIO
{
	public static void main(String [] args) throws IOException
	{
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("New Sheet!");
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("Today's date:");
		
		cell = row.createCell(1);
		DataFormat df = workbook.createDataFormat();
		CellStyle style = workbook.createCellStyle();
		style.setDataFormat(df.getFormat("dd/mm/yyyy"));
		cell.setCellStyle(style);
		cell.setCellValue(new Date());
		
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		
		workbook.write(new FileOutputStream("testing.xlsx"));
		workbook.close();
	}
}
