package model.handle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import model.bean.DwColumnMappingEntity;
import model.bean.DwColumnsEntity;
import model.bean.DwTableEntity;
import model.bean.DwTableMappingEntity;
import model.bean.NormalBean;
import model.utils.ExcelUtil;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelHandle {
	 //输出到Excel

    public static int  WritePdm2Excel(DwTableEntity dte,String filePath,String tbname)
    {
    
    	ExcelUtil eu = new ExcelUtil(); 
    	
    	// 1.创建 Workbook
    	
    	HSSFWorkbook hssfWorkbook = eu.getHSSFWorkbook();
    	// 2.创建 Sheet
    	HSSFSheet hssfSheet = eu.getHSSFSheet(hssfWorkbook, tbname);
    	
    	Workbook wb = new XSSFWorkbook();
    	Sheet sheet = wb.createSheet(tbname);
    	int startRow = 0;
    	String title = "作业定义表";
    	eu.writeHeader(hssfWorkbook, hssfSheet, title,0,14);
    	startRow += 1;
    	
    	title = new String("目标表ETL属性");
    	String[] header=new String[]{"目标实体","目标数据库名","物理表名","加载模式","分批加载"};
    
    	// 3.写入 head
    	eu.writeHeader(hssfWorkbook, hssfSheet, header, title,startRow);
    	startRow = startRow+1;
    	List<NormalBean> nbl = new ArrayList<NormalBean>();
    	NormalBean nb = new NormalBean();
    	nb.setColumn0(dte.getTableName());
    	nb.setColumn1(dte.getSysName());
    	nb.setColumn2(dte.getPhyName());
    	nb.setColumn3(dte.getLoadMode());
    	nb.setColumn4(dte.getIsSingleSource()+"");
    	nbl.add(nb);
    	try {
			eu.writeContent(hssfWorkbook,hssfSheet,header,nbl,startRow);
			startRow = startRow + nbl.size()+1;
    	
			title = new String("源表列表");
			header=new String[]{"加载批次","源表名","源数据库名","源系统","源表描述","源表别名","接口名","组别","连接次序","连接类型","连接条件","筛选条件","增量抽取标识","增量抽取天数","备注"};
			eu.writeHeader(hssfWorkbook, hssfSheet, header, title,startRow);
	    	startRow = startRow+1;
	    	List<NormalBean> nblistt = getSrcTableInfoList(dte.getDtmeList(),dte);
	    	
	    	if(null == nblistt||nblistt.size() == 0)
	    	{
	    		return 1;
	    	}
	    	eu.writeContent(hssfWorkbook,hssfSheet,header,nblistt,startRow);
	    	startRow = startRow+nblistt.size()+1;
	    	
	    	title = new String("目标表ETL规则");
	    	header=new String[]{"字段序号","字段名","字段中文名","数据类型	","主键","分区键","加载批次","组别","源表名","源表中文名","源表字段名","源表字段中文名","源表字段类型","计算表达式","表达式备注"};
	    	
	    	eu.writeHeader(hssfWorkbook, hssfSheet, header, title,startRow);
	    	startRow = startRow+1;
	    	Map<String,List> dcmMap = dte.getDcmMap();
	    	List<NormalBean> nblista = new ArrayList<NormalBean>();
	    	//System.out.println("dcmp.size="+dcmMap.size());
	    	for(String key : dcmMap.keySet()) {
	    		List<DwColumnMappingEntity>  dcmelst = dcmMap.get(key);
	        	List<NormalBean> nblistc = getColumnMappingInfoList(dcmelst,dte.getDcList());

	        	nblista.addAll(nblistc);
	    	}
	    	Collections.sort(nblista,new SortColumnMapping());     //按照 loadBatch,groupIdx,columnId升序排列
        	eu.writeContent(hssfWorkbook,hssfSheet,header,nblista ,startRow);
        	startRow = startRow +nblista.size();
	    	// 5.保存文件到filePath中
	    	eu.write2FilePath(hssfWorkbook, filePath); 
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
    	return 0;
    	
    }
    public static void createNoDefEntity(List<DwTableEntity> nodefineTable,String rootPath)
    {
 	   String sheetName="未定义映射的实体";
 	   String title = sheetName;
 	   String filePath = rootPath+sheetName+".xls";
 	   String[] header=new String[]{"目标实体","目标数据库名","物理表名"};
 	   
 	   List<NormalBean> nbl = new ArrayList<NormalBean>();
 	   for(DwTableEntity dtel : nodefineTable)
 	   {
 		   NormalBean nb = new NormalBean();
 		   nb.setColumn0(dtel.getTableName());
 		   nb.setColumn1(dtel.getSchemaName());
 		   nb.setColumn2(dtel.getPhyName());
 		   nbl.add(nb);
 		  }
 	   try {
 		   ExcelUtil eu = new ExcelUtil();
 		  
 		   eu.createExcel2FilePath(sheetName, title, filePath,header, nbl);
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}

    }
    
    
    
    private static List<NormalBean> getSrcTableInfoList(List<DwTableMappingEntity> dtmelist,DwTableEntity dte)
    {
    	List<NormalBean> nbl = new ArrayList<NormalBean>();
    	
    	for(DwTableMappingEntity dtme:dtmelist)
    	{
    		NormalBean nb= new NormalBean();
    		nb.setColumn0(String.format("%03d",dtme.getLoadBatch()));
    		nb.setColumn1(dtme.getSrcTableName());
    		nb.setColumn2(dtme.getSrcSchema());
    		nb.setColumn3(dtme.getSrcSysName());
    		nb.setColumn4(dtme.getSrcTableNameCn());
    		nb.setColumn5(dtme.getTableAlias());
    		nb.setColumn6(dtme.getInterfaceTable());
    		nb.setColumn7(String.format("%03d",dtme.getGroupIdx()));
    		nb.setColumn8(dtme.getJoinOrder()+"");
    		nb.setColumn9(dtme.getJoinType());
    		nb.setColumn10(dtme.getJoinCondition());
    		nb.setColumn11(dtme.getFilterCondition());
    		nb.setColumn12(dtme.getIsIncExtract());
    		nb.setColumn13(String.format("%d",dtme.getIncExtractDays()));
    		nb.setColumn14(dtme.getComments());
    		nbl.add(nb);
    	}
    	//按照批次号，组号 排序
    	Collections.sort(nbl, new SortSrcTableMapping());
    	return nbl;
    }
    private static List<NormalBean> getColumnMappingInfoList(List<DwColumnMappingEntity> dcmelist,List<DwColumnsEntity> dcelist)
    {
    	List<NormalBean> nbl = new ArrayList<NormalBean>();
    	//System.out.println("size="+dcmelist.size());
    	for(DwColumnMappingEntity dcme:dcmelist)
    	{
    		NormalBean nb= new NormalBean();
    		DwColumnsEntity dce = getColumnEntityInfo(dcme.getSrcSysName(),dcme.getTableName(),dcme.getColumnName(),dcelist);
    		if(null == dce)
    		{
    			System.out.println("无法获取字段信息：table="+dcme.getTableName()+"|column_name="+dcme.getColumnName());
    			//return null;
    			continue;
    		}
    		nb.setColumn1(dce.getPhyName());
    		nb.setColumn2(dcme.getColumnName());
    		nb.setColumn3(dce.getDataType());
    		String tempStr = dce.getIsPk()+""; 
    		nb.setColumn4(tempStr);
    		nb.setColumn5(dce.getIsPartitionKey());
    		nb.setColumn6(String.format("%03d",dcme.getLoadBatch()));       
    		nb.setColumn7(String.format("%03d",dcme.getGroupIdx()));
    		nb.setColumn8(dcme.getSrcTableName());
    		nb.setColumn9(dcme.getSrcTableNameCn());
    		nb.setColumn10(dcme.getSrcColumnName());
    		nb.setColumn11(dcme.getSrcColumnNameCn());
    		nb.setColumn12(dcme.getSrcColumnDataType());
    		nb.setColumn13(dcme.getColumnExpr());
    		nb.setColumn14(dcme.getComments());
    		nb.setColumn0(String.format("%03d",dce.getColumnId()));
    		nbl.add(nb);
    	}
    	return nbl;
    }
    private static DwColumnsEntity getColumnEntityInfo(String sysName,String tableName ,String columnName,List<DwColumnsEntity> dcelist)
    {
    	for(DwColumnsEntity dce: dcelist)
    		if(dce.getTableName().equals(tableName)&&dce.getColumnName().equals(columnName))
    			return dce;
    	return null;
    	
    }
   	
    public static boolean isEquals(List<String> list,String str)  {
		   for (int i = 0; i < list.size(); i++) {
			   if (str.equals(list.get(i).toString())) {
				   return true;
			   }
		   }
		   return false;
		  }
    public static class SortbyColumnId implements Comparator{ 
  	  
   	 public int compare(Object obj1, Object obj2) { 
   	    NormalBean nb1=(NormalBean)obj1; 
   	    NormalBean nb2=(NormalBean)obj2; 
   		if (Double.parseDouble(nb1.getColumn0())> Double.parseDouble(nb2.getColumn0()))
   			   return 1;
   			return -1;

   	 } 
   	   
   	} 
    public static class SortSrcTableMapping  implements Comparator<Object> {  
    
        public int compare(Object o1, Object o2) {  
        	NormalBean  com1 = (NormalBean ) o1;  
        	NormalBean  com2 = (NormalBean ) o2;  
            if (com1.getColumn0().compareTo(com2.getColumn0()) != 0){ //如果加载批次不一样  
                return com1.getColumn0().compareTo(com2.getColumn0());  
            } else {//按组别 
    	            return com1.getColumn7().compareTo(com2.getColumn7());  
    	        }  
    	    }  
    	  
    	}  
    public static class SortColumnMapping  implements Comparator<Object> {  
    
        public int compare(Object o1, Object o2) {  
        	NormalBean com1 = (NormalBean) o1;  
        	NormalBean com2 = (NormalBean) o2;  
            if (com1.getColumn6().compareTo(com2.getColumn6()) != 0){ //如果加载批次不一样   
                return com1.getColumn6().compareTo(com2.getColumn6());  
            } else if (com1.getColumn7().compareTo(com2.getColumn7())!=0) {//如果组别不一样   
                 return com1.getColumn7().compareTo(com2.getColumn7());  
            } else {//列序号  
                return com1.getColumn0().compareTo(com2.getColumn0());  
            }  
        }  
      
    }  

    
}
