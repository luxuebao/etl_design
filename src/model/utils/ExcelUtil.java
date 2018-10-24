package model.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
//import org.apache.poi.hssf.record.formula.functions.T;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/*import entity.EstateTbl;
import utils.StringUtils;*/
@SuppressWarnings("unchecked")
public class ExcelUtil<T> {
	
	private static final int DEFAUL_COLUMN_WIDTH = 4000;

    /**
     *
    * 
    * 从代码不难发现其处理逻辑
    * 1.先用InputStream获取excel文件的io流
    * 2.然后穿件一个内存中的excel文件HSSFWorkbook类型对象，这个对象表示了整个excel文件。
    * 3.对这个excel文件的每页做循环处理
    * 4.对每页中每行做循环处理
    * 5.对每行中的每个单元格做处理，获取这个单元格的值
    * 6.把这行的结果添加到一个List数组中
    * 7.把每行的结果添加到最后的总结果中
    * 8.解析完以后就获取了一个List<List<String>>类型的对象了
    * 
     */
    private List<List<String>> readXls(String path) throws Exception {
        InputStream is = new FileInputStream(path);
        // HSSFWorkbook 标识整个excel
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        List<List<String>> result = new ArrayList<List<String>>();
        int size = hssfWorkbook.getNumberOfSheets();
        // 循环每一页，并处理当前循环页
        for (int numSheet = 0; numSheet < size; numSheet++) {
            // HSSFSheet 标识某一页
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // 处理当前页，循环读取每一行
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                // HSSFRow表示行
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                 
                int minColIx = hssfRow.getFirstCellNum();
                int maxColIx = hssfRow.getLastCellNum();
                
                List<String> rowList = new ArrayList<String>();
                
                // 遍历改行，获取处理每个cell元素
                for (int colIx = minColIx; colIx < maxColIx; colIx++) {
                    // HSSFCell 表示单元格
                    HSSFCell cell = hssfRow.getCell(colIx);
                    if (cell == null) {
                        continue;
                    }
                    rowList.add(getStringVal(cell));
                }
                result.add(rowList);
            }
        }
        return result;
    }

    /**
     * 
    * @Title: readXlsx 
    * @Description: 处理Xlsx文件
    * @param @param path
    * @param @return
    * @param @throws Exception    设定文件 
    * @return List<List<String>>    返回类型 
    * @throws
     */
    /**
     * 
    * @Title: readXlsx 
    * @Description: 处理Xlsx文件
    * @param @param path
    * @param @return
    * @param @throws Exception    设定文件 
    * @return List<List<String>>    返回类型 
    * @throws
     */
    public static List<List<String>> readXlsx(String path) throws Exception {
        InputStream is = new FileInputStream(path);
        Workbook xssfWorkbook = null; 
        xssfWorkbook = new XSSFWorkbook(is);
        List<List<String>> result = new ArrayList<List<String>>();
        // 循环每一页，并处理当前循环页
        int numberOfSheets = xssfWorkbook .getNumberOfSheets();
        for(int i=0;i<numberOfSheets;i++){
        	Sheet xssfSheet = xssfWorkbook.getSheetAt(i);
            if (xssfSheet == null) {
                continue;
            }
            // 处理当前页，循环读取每一行
            Row xssfRow0 = xssfSheet.getRow(0);
            int minColIx = xssfRow0.getFirstCellNum();
            int maxColIx = xssfRow0.getLastCellNum();
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                Row xssfRow = xssfSheet.getRow(rowNum);
              //  System.out.println("rowNum="+rowNum);
                List<String> rowList = new ArrayList<String>();
                for (int colIx = minColIx; colIx < maxColIx; colIx++) {
                    Cell cell = xssfRow.getCell(colIx);
                    if (cell == null) {
                        continue;
                    }
                    rowList.add(cell.toString());
                }
                result.add(rowList);
            }
        }
        return result;
    }
    @SuppressWarnings("deprecation")
	public static Map<String,List> readXlsx2Map(String path) throws Exception {
        InputStream is = new FileInputStream(path);
        Workbook xssfWorkbook = null; 
        xssfWorkbook = new XSSFWorkbook(is);
        
        Map<String,List> map = new HashMap<String,List>();
        // 循环每一页，并处理当前循环页
        int numberOfSheets = xssfWorkbook .getNumberOfSheets();
        for(int i=0;i<numberOfSheets;i++){
        	String sheetName= xssfWorkbook .getSheetName(i);
        	Sheet xssfSheet = xssfWorkbook.getSheetAt(i);
        	List<List<String>> result = new ArrayList<List<String>>();
            if (xssfSheet == null) {
                continue;
            }
            // 处理当前页，循环读取每一行
            Row xssfRow0 = xssfSheet.getRow(0);
            int minColIx = xssfRow0.getFirstCellNum();
            int maxColIx = xssfRow0.getLastCellNum();
            //System.out.println("sheetName="+sheetName+"|minCloIx="+minColIx+"|maxColIx="+maxColIx);
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                Row xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow == null || xssfRow.getCell(0) == null ){
    				continue;
    			}
                else
                {
                	 //跳过第一列为空的行
                    Cell cell0 = xssfRow.getCell(0);
                    String cellValue0= null;
                    if (cell0.getCellType() == Cell.CELL_TYPE_STRING) {
                       cellValue0 = cell0.getStringCellValue();
                    } else if (cell0.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    	cellValue0 = cell0.getNumericCellValue()+"";
                    } else  {
                    	cellValue0="";
                    }

                	 //跳过第一列为空的行
                    if(cellValue0.trim().length() == 0)
                    	continue;
                }
                //System.out.println("rowNum="+rowNum);
                List<String> rowList = new ArrayList<String>();
                for (int colIx = minColIx; colIx < maxColIx; colIx++) {
                	System.out.println("sheetName="+sheetName+"\trowNum="+rowNum+"\tcolIx="+colIx);
                	Cell cell = xssfRow.getCell(colIx);
                	String cellValue = "";
                	if(cell == null)
                	{
                		rowList.add(cellValue);
                		continue;
                	}
                	
                	if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC)  // 数字
                	{
                        Long  longValue = Math.round(cell.getNumericCellValue());
                        Double doubleVal = cell.getNumericCellValue();
                         if(Double.parseDouble(longValue + ".0") == doubleVal){   //判断是否含有小数位.0
                             cellValue = longValue+"";
                          }else{
                              cellValue=doubleVal+"";
                          }
                         System.out.println("longVaue="+longValue+"doubleVal="+doubleVal+"cellValue="+cellValue);
                	}
                	else if(cell.getCellType() == Cell.CELL_TYPE_STRING)
                		// 字符串
                        cellValue = cell.getStringCellValue();
                	else if(cell.getCellType() == Cell.CELL_TYPE_BOOLEAN)  // Boolean
                        cellValue = cell.getBooleanCellValue() + "";
                        
                	else if(cell.getCellType() == Cell.CELL_TYPE_FORMULA) // 公式
                	{
                		FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
                		CellValue tempCellValue = evaluator.evaluate(cell); 
                	    double cellValue1 =tempCellValue.getNumberValue(); 

                		cellValue=String.valueOf(cellValue1);
                	}	
                	else if (cell.getCellType() == Cell.CELL_TYPE_BLANK) // 空值
                        cellValue = "";
                	else if (cell.getCellType() == Cell.CELL_TYPE_ERROR) // 故障
                        cellValue = "非法字符";
                	else
                        cellValue = "未知类型";
                  
                    	rowList.add(cellValue);
                }
                //System.out.println(rowList.size());
                result.add(rowList);
            }
            map.put(sheetName,result);
        }
        return map;
    }


    // 存在的问题
    /*
     * 其实有时候我们希望得到的数据就是excel中的数据，可是最后发现结果不理想
     * 如果你的excel中的数据是数字，你会发现Java中对应的变成了科学计数法。
     * 所以在获取值的时候就要做一些特殊处理来保证得到自己想要的结果
     * 网上的做法是对于数值型的数据格式化，获取自己想要的结果。
     * 下面提供另外一种方法，在此之前，我们先看一下poi中对于toString()方法:
     * 
     * 该方法是poi的方法，从源码中我们可以发现，该处理流程是：
     * 1.获取单元格的类型
     * 2.根据类型格式化数据并输出。这样就产生了很多不是我们想要的
     * 故对这个方法做一个改造。
     */
    /*public String toString(){
        switch(getCellType()){
            case CELL_TYPE_BLANK:
                return "";
            case CELL_TYPE_BOOLEAN:
                return getBooleanCellValue() ? "TRUE" : "FALSE";
            case CELL_TYPE_ERROR:
                return ErrorEval.getText(getErrorCellValue());
            case CELL_TYPE_FORMULA: 
                return getCellFormula();
            case CELL_TYPE_NUMERIC:
                if(DateUtil.isCellDateFormatted(this)){
                    DateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy")
                    return sdf.format(getDateCellValue());
                }
                return getNumericCellValue() + "";
            case CELL_TYPE_STRING:  
                return getRichStringCellValue().toString();
            default :
                return "Unknown Cell Type:" + getCellType();
        }
    }*/

    /**
     * 改造poi默认的toString（）方法如下
    * @Title: getStringVal 
    * @Description: 1.对于不熟悉的类型，或者为空则返回""控制串
    *               2.如果是数字，则修改单元格类型为String，然后返回String，这样就保证数字不被格式化了
    * @param @param cell
    * @param @return    设定文件 
    * @return String    返回类型 
    * @throws
     */
    @SuppressWarnings("deprecation")
	public static String getStringVal(Cell cell) {
        switch (cell.getCellType()) {
        case Cell.CELL_TYPE_BOOLEAN:
            return cell.getBooleanCellValue() ? "TRUE" : "FALSE";
        case Cell.CELL_TYPE_FORMULA:
            return cell.getCellFormula();
        case Cell.CELL_TYPE_NUMERIC:
            cell.setCellType(Cell.CELL_TYPE_STRING);
            return cell.getStringCellValue();
        case Cell.CELL_TYPE_STRING:
            return cell.getStringCellValue();
        default:
            return "";
        }
    }
  //判断行为空  
    private int CheckRowNull(XSSFRow xssfRow){  
    int num = 0;  
    Iterator<Cell> cellItr =xssfRow.iterator();  
    while(cellItr.hasNext()){  
     Cell c =cellItr.next();                          
     if(c.getCellType() ==XSSFCell.CELL_TYPE_BLANK){  
     num++;  
     }  
    }  
    return num;  
    }  
    /**
	 * 3.写入表头信息
	 * 
	 * @param hssfWorkbook {@link HSSFWorkbook}
	 * @param hssfSheet {@link HSSFSheet}
	 * @param title 标题
	 * @param startRow 开始行
	 * @param endColumn 合并列单元列个数
	 */
	public void writeHeader(HSSFWorkbook hssfWorkbook, HSSFSheet hssfSheet,String title,int startRow,int columnNum) {
		//LOGGER.info("【写入表头信息】");
		// 初始化标题和表头单元格样式
		HSSFCellStyle titleCellStyle = createTitleCellStyle(hssfWorkbook);
		// 标题栏
		HSSFRow titleRow = hssfSheet.createRow(startRow);
		titleRow.setHeight((short) 800);
		HSSFCell titleCell = titleRow.createCell(0);
		// 设置标题文本
		titleCell.setCellValue(new HSSFRichTextString(title));
		// 设置单元格样式
		titleCell.setCellStyle(titleCellStyle);

		// 处理单元格合并，四个参数分别是：起始行，终止行，起始列，终止列
		hssfSheet.addMergedRegion(new CellRangeAddress(startRow, startRow, (short) 0,columnNum));

		// 设置合并后的单元格的样式
		titleRow.createCell(columnNum - 1).setCellStyle(titleCellStyle);

	}
    /**
	 * 3.写入表头信息
	 * 
	 * @param hssfWorkbook {@link HSSFWorkbook}
	 * @param hssfSheet {@link HSSFSheet}
	 * @param headers 列标题，数组形式，
	 * 				   如{"列标题1@beanFieldName1@columnWidth","列标题2@beanFieldName2@columnWidth","列标题3@beanFieldName3@columnWidth"}
	 * 				   其中参数@columnWidth可选，columnWidth为整型数值
	 * @param title 标题
	 */
	public void writeHeader(HSSFWorkbook hssfWorkbook, HSSFSheet hssfSheet, String[] headers,
			String title,int startRow) {
		//LOGGER.info("【写入表头信息】");
		// 初始化标题和表头单元格样式
		HSSFCellStyle titleCellStyle = createTitleCellStyle(hssfWorkbook);
		// 标题栏
		HSSFRow titleRow = hssfSheet.createRow(startRow);
		titleRow.setHeight((short) 500);
		HSSFCell titleCell = titleRow.createCell(0);
		// 设置标题文本
		titleCell.setCellValue(new HSSFRichTextString(title));
		// 设置单元格样式
		titleCell.setCellStyle(titleCellStyle);

		// 处理单元格合并，四个参数分别是：起始行，终止行，起始列，终止列
		hssfSheet.addMergedRegion(new CellRangeAddress(startRow, startRow, (short) 0,
				(short) (headers.length - 1)));

		// 设置合并后的单元格的样式
		titleRow.createCell(headers.length - 1).setCellStyle(titleCellStyle);
		titleCellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
		// 表头
		HSSFRow headRow = hssfSheet.createRow(startRow+1);
		headRow.setHeight((short) 500);
		HSSFCell headCell = null;
		String[] headInfo = null;
		// 处理excel表头
		for (int i = 0, len = headers.length; i < len; i++) {
			headInfo = headers[i].split("@");
			headCell = headRow.createCell(i);
			headCell.setCellValue(headInfo[0]);
			headCell.setCellStyle(titleCellStyle);
			// 设置列宽度
			setColumnWidth(i, headInfo, hssfSheet);
		}
	}
	/**
	 * 设置列宽度
	 * @param i 列的索引号
	 * @param headInfo 表头信息，其中包含了用户需要设置的列宽
	 */
	private void setColumnWidth(int i, String[] headInfo, HSSFSheet hssfSheet) {
		if (headInfo.length < 3) {
			// 用户没有设置列宽，使用默认宽度
			hssfSheet.setColumnWidth(i, DEFAUL_COLUMN_WIDTH);
			return;
		}
		if (StringUtils.isBlank(headInfo[2])) {
			// 使用默认宽度
			hssfSheet.setColumnWidth(i, DEFAUL_COLUMN_WIDTH);
			return;
		}
		// 使用用户设置的列宽进行设置
		hssfSheet.setColumnWidth(i, Integer.parseInt(headInfo[2]));
	}
	/**
	 * 创建标题和表头单元格样式
	 * @param hssfWorkbook {@link HSSFWorkbook}
	 * @return {@link HSSFCellStyle}
	 */
	private HSSFCellStyle createTitleCellStyle(HSSFWorkbook hssfWorkbook) {
	
		// 单元格的样式
		HSSFCellStyle cellStyle = hssfWorkbook.createCellStyle();
		// 设置字体样式，改为不变粗
		HSSFFont font = hssfWorkbook.createFont();
		font.setFontHeightInPoints((short) 13);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cellStyle.setFont(font);
		// 设置通用的单元格属性
		setCommonCellStyle(cellStyle);
		return cellStyle;
	}
	/**
	 * 设置通用的单元格属性
	 * @param cellStyle 要设置属性的单元格
	 */
	private void setCommonCellStyle(HSSFCellStyle cellStyle) {
		// 居中
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		// 设置边框
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
	}
	/**
	 * 1.创建 workbook
	 * 
	 * @return {@link HSSFWorkbook}
	 */
	public HSSFWorkbook getHSSFWorkbook() {
		//LOGGER.info("【创建 workbook】");
		return new HSSFWorkbook();
	}

	/**
	 * 2.创建 sheet
	 * 
	 * @param hssfWorkbook {@link HSSFWorkbook}
	 * @param sheetName sheet 名称
	 * @return {@link HSSFSheet}
	 */
	public HSSFSheet getHSSFSheet(HSSFWorkbook hssfWorkbook, String sheetName) {
		//LOGGER.info("【创建 sheet】sheetName ： " + sheetName);
		return hssfWorkbook.createSheet(sheetName);
	}
	/**
	 * 4.写入内容部分
	 * 
	 * @param hssfWorkbook {@link HSSFWorkbook}
	 * @param hssfSheet {@link HSSFSheet}
	 * @param headers 列标题，数组形式，
	 * 				   如{"列标题1@beanFieldName1@columnWidth","列标题2@beanFieldName2@columnWidth","列标题3@beanFieldName3@columnWidth"}
	 * 				   其中参数@columnWidth可选，columnWidth为整型数值
	 * @param dataList 要导出的数据集合
	 * @throws Exception 
	 */
	
	public void writeContent(HSSFWorkbook hssfWorkbook, HSSFSheet hssfSheet, String[] headers,
			Collection<T> dataset,int startRow) throws Exception {
		//LOGGER.info("【写入Excel内容部分】");

		HSSFRow row = null;
		HSSFCell cell = null;
		// 单元格的值
		Object cellValue = null;
		// 数据写入行索引
	//	int rownum = 2;
		
	     //遍历集合取出数据
	    Iterator<T> it = dataset.iterator();
	    int index = startRow;
	    while (it.hasNext()){
	         index++;
	        row = hssfSheet.createRow(index);
	        T t = (T) it.next();
	        // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
	        Field[] fields = t.getClass().getDeclaredFields();
	        for (int i=0;i<fields.length;i++){
	            cell= row.createCell(i);
	            //cell.setCellStyle(cellStyle2);
	            Field field = fields[i];
                String fieldName = field.getName();
                String getMethodName = "get"
                        + fieldName.substring(0, 1).toUpperCase()
                        + fieldName.substring(1);
                
                Class tCls = t.getClass();
                Method getMethod = tCls.getMethod(getMethodName,
                            new Class[] {});
                Object value = getMethod.invoke(t, new Object[] {});
                
	             // 判断值的类型后进行强制类型转换
	            String textValue = null;
	            if(value instanceof Boolean){
	                 boolean bValue = (Boolean) value;
	                textValue = "是";
	                if (!bValue)
	                 {
	                     textValue = "否";
	                }
	             }else if (value instanceof Date)
	             {
	                 Date date = (Date) value;
	                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	                textValue = sdf.format(date);
	            }else
	            {
	                 // 其它数据类型都当作字符串简单处理
	            	if(null == value)
	            		textValue=" ";
	            	else
	            	{
	            		textValue = value.toString();
	            		if(isNumeric(textValue))
	            		try{
	            				textValue=Integer.parseInt(textValue)+"";  
	            			}catch(Exception e){
	            			    
	            		}
	            				
	            	}
	            }
	             if (textValue!=null){
	                     cell.setCellValue(textValue);
	            }

	         }
	     }
	}

	/**
	 * 创建内容单元格样式
	 * @param hssfWorkbook {@link HSSFWorkbook}
	 * @return {@link HSSFCellStyle}
	 */
	private HSSFCellStyle createContentCellStyle(HSSFWorkbook hssfWorkbook) {
		//LOGGER.info("【创建内容单元格样式】");
		// 单元格的样式
		HSSFCellStyle cellStyle = hssfWorkbook.createCellStyle();
		// 设置字体样式，改为不变粗
		HSSFFont font = hssfWorkbook.createFont();
		font.setFontHeightInPoints((short) 11);
		cellStyle.setFont(font);
		// 设置通用的单元格属性
		setCommonCellStyle(cellStyle);
		return cellStyle;
	}
	/**
	 * 将生成的Excel输出到指定目录
	 * @param hssfWorkbook {@link HSSFWorkbook}
	 * @param filePath 文件输出目录，包括文件名（.xls）
	 */
	public void write2FilePath(HSSFWorkbook hssfWorkbook, String filePath) {
	//	LOGGER.info("【将生成的Excel输出到指定目录】filePath ：" + filePath);
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(filePath);
			hssfWorkbook.write(fileOut);
		} catch (Exception e) {
			//LOGGER.error("【将生成的Excel输出到指定目录失败】", e);
			throw new RuntimeException("将生成的Excel输出到指定目录失败");
		} finally {
			IOUtils.closeQuietly(fileOut);
		}
	}
	/**
	 * 生成Excel，存放到指定目录
	 * @param sheetName sheet名称
	 * @param title 标题
	 * @param filePath 要导出的Excel存放的文件路径
	 * @param headers 列标题，数组形式，
	 * 				   如{"列标题1@beanFieldName1@columnWidth","列标题2@beanFieldName2@columnWidth","列标题3@beanFieldName3@columnWidth"}
	 * 				   其中参数@columnWidth可选，columnWidth为整型数值
	 * @param dataList 要导出数据的集合
	 * @throws Exception
	 */
	public static void createExcel2FilePath(String sheetName, String title, String filePath,
			String[] headers, List<?> dataList) throws Exception {
		//LOGGER.info("【生成Excel,并存放到指定文件夹目录下】sheetName : " + sheetName + " , title : " + title
//				+ " , filePath : " + filePath + " , headers : " + headers.toString());
		if (ArrayUtils.isEmpty(headers)) {
	//		LOGGER.warn("【表头为空】");
			throw new RuntimeException("表头不能为空");
		}
		/*if (CollectionUtils.isEmpty(dataList)) {
		//	LOGGER.warn("【要导出的数据为空】");
			throw new RuntimeException("要导出的数据为空");
		}*/

		ExcelUtil eu = new ExcelUtil();
		// 1.创建 Workbook
		HSSFWorkbook hssfWorkbook = eu.getHSSFWorkbook();
		// 2.创建 Sheet
		HSSFSheet hssfSheet = eu.getHSSFSheet(hssfWorkbook, sheetName);
		// 3.写入 head
		int startRow = 0;
		eu.writeHeader(hssfWorkbook, hssfSheet, headers, title,startRow);
		// 4.写入内容
		eu.writeContent(hssfWorkbook, hssfSheet, headers, dataList,startRow+1);
		// 5.保存文件到filePath中
		eu.write2FilePath(hssfWorkbook, filePath);
	}

//用正则表达式判断字符串是否为数字（含负数）

public static boolean isNumeric(String str) {
	    String regEx = "^-?[0-9]+$";
	    Pattern pat = Pattern.compile(regEx);
	    Matcher mat = pat.matcher(str);
	
	    if (mat.find()) {
	        return true;
	    }
	    else {
	        return false;
	    }
	}

}
	 