package commf.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.document.AbstractExcelView;

/**
 * 2014-12-04 Excel Download View Resolver
 * @author ntarget
 *
 */

@SuppressWarnings("all")
public class ExcelView extends AbstractExcelView {
	/** Logger for this class */
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		String filename	= (String)model.get("filename");

		if (filename == null || "".equals(filename))
			filename	= (String)model.get("filename");

		List   titleList	= (List)model.get("titleList");
		List   excelList	= (List)model.get("excelList");

		HSSFSheet worksheet = null;
		HSSFRow row = null;
		HSSFCellStyle cellHeadStyle = workbook.createCellStyle();
		cellHeadStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellHeadStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
		cellHeadStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellHeadStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellHeadStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellHeadStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellHeadStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);

		worksheet = workbook.createSheet("WorkSheet");
		row = worksheet.createRow(0);

		if (titleList != null) {
			List listKey	= new ArrayList();
			LinkedMap titleMap	= new LinkedMap();
			titleMap = (LinkedMap)titleList.get(0);

			// EXCEL TITLE Create
			Iterator k = titleMap.keySet().iterator();
			String key = "";
			int n = 0;
			while (k.hasNext()) {
				key = (String) k.next();
				HSSFCell cell = row.createCell(n);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellStyle(cellHeadStyle);
				cell.setCellValue((String)titleMap.get(key));

				listKey.add(n, key);
				n++;
			}

			// EXCEL Data Create
			if (excelList != null) {
				HSSFCellStyle cellLeftStyle = workbook.createCellStyle();
				cellLeftStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
				cellLeftStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				cellLeftStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				cellLeftStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
				cellLeftStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				cellLeftStyle.setFillForegroundColor(HSSFColor.WHITE.index);
				cellLeftStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);

				HSSFCellStyle cellRightStyle = workbook.createCellStyle();
				cellRightStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
				cellRightStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				cellRightStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				cellRightStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
				cellRightStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				cellRightStyle.setFillForegroundColor(HSSFColor.WHITE.index);
				cellRightStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);

				for(int i = 0; i < excelList.size(); i++){
					row = worksheet.createRow(i+1);
					Map map = (HashMap)excelList.get(i);

					for (int r = 0; r < listKey.size(); r++) {
						HSSFCell cell = row.createCell(r);
						if (map.get(listKey.get(r)) instanceof BigDecimal) {
							cell.setCellStyle(cellRightStyle);
							cell.setCellValue(new HSSFRichTextString( ((BigDecimal)map.get(listKey.get(r))).toString()));
						} else {
							cell.setCellStyle(cellLeftStyle);
							cell.setCellValue((String)map.get(listKey.get(r)));
						}
					}
				}

				for(int i = 0; i < listKey.size(); i++){
					worksheet.autoSizeColumn((short)i);
					worksheet.setColumnWidth(i, (worksheet.getColumnWidth(i))+512 );  // 윗줄만으로는 컬럼의 width 가 부족하여 더 늘려야 함.
				}
			}
		}

        response.setContentType("Application/Msexcel");
        response.setHeader("Content-Disposition", "ATTachment; Filename="+filename+".xls");

	}

}
