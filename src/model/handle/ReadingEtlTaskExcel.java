package model.handle;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import model.bean.SrcTableListEntity;
import model.bean.TableHeader;
import model.bean.TgtTableEtlRulesEntity;
import model.constant.Constant;

public class ReadingEtlTaskExcel {

	public static Map<String, List> readXls(List<File> fileList) throws IOException {
		Map<String, List> map= new HashMap();
		List<SrcTableListEntity> srcTLsList = new ArrayList();
		List<TgtTableEtlRulesEntity> tRulesList = new ArrayList();	
		for(File f: fileList) {
			InputStream is = new FileInputStream(f.toString());
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);	
			for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
				HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
				if (hssfSheet == null) {
					continue;
				}
				int colFlag = -1;
				String table_header = null;
				String tgtEntity = null;
				String tgtDbSchema = null;
				String tgtPhyName = null;
				String loadModel = null;
				String isSingleSource = null;
				// ѭ����Row
				for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
					HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					SrcTableListEntity srcTLs = new SrcTableListEntity();
					TgtTableEtlRulesEntity tRules = new TgtTableEtlRulesEntity();
					if (hssfRow != null) {
						table_header = String.valueOf(hssfRow.getCell(0));
						if(table_header.equals(TableHeader.TASK_DEF.getHeader())) {
							rowNum = rowNum + 2;
							colFlag = TableHeader.TASK_DEF.getColFlag();
							continue;
						}
						else if(table_header.equals(TableHeader.TGT_TABLES_LIST.getHeader())) {
							rowNum = rowNum + 1;
							colFlag = TableHeader.TGT_TABLES_LIST.getColFlag();
							continue;
						}
						else if(table_header.equals(TableHeader.TGT_TABLE_ETL_RULES.getHeader())) {
							rowNum = rowNum + 1;
							colFlag = TableHeader.TGT_TABLE_ETL_RULES.getColFlag();
							continue;
							
						}
						
						if(colFlag == TableHeader.TASK_DEF.getColFlag()) {
							tgtEntity = String.valueOf(hssfRow.getCell(0));
							tgtDbSchema = String.valueOf(hssfRow.getCell(1));
							tgtPhyName = String.valueOf(hssfRow.getCell(2));
							loadModel = String.valueOf(hssfRow.getCell(3));
							isSingleSource = String.valueOf(hssfRow.getCell(4));
							continue;
						}
						
						if(colFlag == TableHeader.TGT_TABLES_LIST.getColFlag()) {
							srcTLs.setTgtEntity(tgtEntity);
							srcTLs.setTgtDbSchema(tgtDbSchema);
							srcTLs.setPhyName(tgtPhyName);
							srcTLs.setLoadMode(loadModel);
							srcTLs.setIsSingleSource(isSingleSource);
							srcTLs.setLoadBatch(String.valueOf(hssfRow.getCell(0)));
							srcTLs.setSrcTableName(String.valueOf(hssfRow.getCell(1)));
							srcTLs.setSrcSchemaName(String.valueOf(hssfRow.getCell(2)));
							srcTLs.setSrcSysName(String.valueOf(hssfRow.getCell(3)));
							srcTLs.setSrcTableDesc(String.valueOf(hssfRow.getCell(4)));
							srcTLs.setSrcTableAlias(String.valueOf(hssfRow.getCell(5)));
							srcTLs.setInterfaceName(String.valueOf(hssfRow.getCell(6)));
							srcTLs.setGroupId(String.valueOf(hssfRow.getCell(7)));
							srcTLs.setJoinOrder(String.valueOf(hssfRow.getCell(8)));
							srcTLs.setJoinType(String.valueOf(hssfRow.getCell(9)));
							srcTLs.setJoinCondition(String.valueOf(hssfRow.getCell(10)));
							srcTLs.setFilterCondition(String.valueOf(hssfRow.getCell(11)));
							srcTLs.setIsIncremental(String.valueOf(hssfRow.getCell(12)));
							srcTLs.setIncExtractDays(String.valueOf(hssfRow.getCell(13)));
							srcTLs.setComments(String.valueOf(hssfRow.getCell(14)));
							srcTLsList.add(srcTLs);
						}

						if(colFlag == TableHeader.TGT_TABLE_ETL_RULES.getColFlag()) {
							tRules.setTgtEntity(tgtEntity);
							tRules.setTgtDbSchema(tgtDbSchema);
							tRules.setPhyName(tgtPhyName);
							tRules.setColumnId(String.valueOf(hssfRow.getCell(0)));
							tRules.setColumnNameEn(String.valueOf(hssfRow.getCell(1)));
							tRules.setColumnNameCh(String.valueOf(hssfRow.getCell(2)));
							tRules.setDataType(String.valueOf(hssfRow.getCell(3)));
							tRules.setIsPk(String.valueOf(hssfRow.getCell(4)));
							tRules.setIsPartitionKey(String.valueOf(hssfRow.getCell(5)));
							tRules.setLoadBatch(String.valueOf(hssfRow.getCell(6)));
							tRules.setGroupId(String.valueOf(hssfRow.getCell(7)));
							tRules.setSrcTableName(String.valueOf(hssfRow.getCell(8)));
							tRules.setSrcTableNameCn(String.valueOf(hssfRow.getCell(9)));
							tRules.setSrcColumnName(String.valueOf(hssfRow.getCell(10)));
							tRules.setSrcColumnNameCn(String.valueOf(hssfRow.getCell(11)));
							tRules.setSrcColumnType(String.valueOf(hssfRow.getCell(12)));
							tRules.setComputeExpression(String.valueOf(hssfRow.getCell(13)));
							tRules.setExpressionComments(String.valueOf(hssfRow.getCell(14)));
							tRulesList.add(tRules);
						}
					}
				}
			}
		}
		map.put("srcTLsList", srcTLsList);
		map.put("tRulesList", tRulesList);
		return map;
	}
	
	 @SuppressWarnings("static-access")
	private String getValue(HSSFCell hssfCell) {
	        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {   
	            return String.valueOf(hssfCell.getBooleanCellValue());
	        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
	            return String.valueOf(hssfCell.getNumericCellValue());
	        } else {
				System.out.println(String.valueOf(hssfCell.getStringCellValue()));
				return String.valueOf(hssfCell.getStringCellValue());
	        }
	    }

	
	
}
