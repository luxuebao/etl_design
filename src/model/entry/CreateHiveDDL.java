package model.entry;

import java.io.File;
import java.sql.Connection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import model.bean.BDColumninfo;
import model.constant.Constant;
import model.dao.BdTableColumnInfoDao;
import model.dao.metaDbConn;
import model.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;

public class CreateHiveDDL {
	
	 
	/**
	 * 
	 * @author Yanxb
	 * 
	 */
	private static Log logger = null;
	static{
		
	}

	   
	public static void main(String[] args) {
	    	
		    int type=0;
		    String area = new String();
		    //String sqlStr1=CreateDDLImpl("ABC.TABLE",null,"STEMP");
		    //System.out.println("sqlStr1="+sqlStr1);
		    Map<String, List>  tablemap = new HashMap<String, List>();
		    if(args.length<1)
		    {
		    	System.out.println("参数错误，程序不能执行！");
		    	System.exit(-1);
		    }
		    if(args[0].toUpperCase().equals("STEMP"))
		    {
		    	type = 1;
		    	area=args[0].toUpperCase();
		    }
		    else if(args[0].toUpperCase().equals("PDATA"))
		    {
		    	type =2;
		    	area=args[0].toUpperCase();
		    }
		    else if(args[0].toUpperCase().equals("ODS"))
		    {
		    	type =1;
		    	area=args[0].toUpperCase();
		    }
		    else
		    {
		    	System.out.println("参数错误，程序不能执行！");
		    	System.exit(-1);
		    }
		    String workPath = args[1];
			if(!workPath.endsWith(File.separator))
				workPath=workPath+File.separator;
			String SQLFilePath = workPath+"DDL"+File.separator;
		    
			Connection conn=null;
			try {
				conn = metaDbConn.connectMetaDB(Constant.dbPropertiesfile);
			} catch (Exception e) {
				System.err.println(e);
				System.exit(-1);
			}
		
	   		tablemap = BdTableColumnInfoDao.getTableColumnInfolist(type,conn);
	   	    
	   	    String fileName  =workPath+"Create"+area+".sql";
	   	    System.out.println("fileName="+fileName);
		    FileUtils.deleteFile(fileName);
	   		for (Entry<String, List> entry : tablemap.entrySet()) {
	   			String tableid =entry.getKey();
    			//得到批处理数据字典表中对应系统所有的表
    			List<BDColumninfo> fieldlst =  entry.getValue();
    		    Collections.sort(fieldlst,new SortbySeqno());     //按照seqNo升序排列
    		 
    		    String sqlStr = CreateHiveDDLImpl(tableid,fieldlst,area);
    		
    		    FileUtils.writeToFile(fileName, sqlStr, Constant.charset,true);
	   		}	
	}
	
	  
	    private static String  CreateHiveDDLImpl(String tableid,List<BDColumninfo> cidlst,String area)
	    {
	    	String sqlStr = new String();
	    	StringBuffer sql = new StringBuffer();
	    	 
	    	String sysName = tableid.substring( 0,tableid.indexOf("."));
	    	String tableName = tableid.substring( tableid.lastIndexOf(".")+1,tableid.length());
	  //  	System.out.println("sysname="+sysName+"|tablename="+tableName);
	    	String tgtTableName =tableName;
	    	
	    	String colstr = "";
	    	String partitionstr = "dt String";
	    	String tablechnname="";
	    	String storeStr="";
	    	for(BDColumninfo cid: cidlst)
	    	{
	    		 
    		    if(StringUtils.isNotBlank(colstr)){
                    colstr = colstr+",\n";
                }
    		    if(null != cid.getIsPartition()&&cid.getIsPartition().trim().length()>0 )
    		    {
	    		    if(StringUtils.isNotBlank(partitionstr)){
	    		    	partitionstr = partitionstr+",";
	                }
	    		    partitionstr= cid.getName()+" " + "String" ;
    		    }
    		    
    		    if(null != cid.getTableChnName())
    		    	tablechnname=cid.getTableChnName();
                colstr =colstr+"\t" + cid.getName() + "\t";
                String ColumnTypeName= cid.getType();
                if (StringUtils.equalsIgnoreCase(ColumnTypeName,"timestamp")
                        ||StringUtils.equalsIgnoreCase(ColumnTypeName,"int")
                        ||StringUtils.equalsIgnoreCase(ColumnTypeName,"datetime")
                        ||StringUtils.equalsIgnoreCase(ColumnTypeName,"long")
                        ||StringUtils.equalsIgnoreCase(ColumnTypeName,"date")
                        ||StringUtils.equalsIgnoreCase(ColumnTypeName,"text")
                        ||StringUtils.equalsIgnoreCase(ColumnTypeName,"image")
                        ||StringUtils.equalsIgnoreCase(ColumnTypeName,"bit")
                        ||StringUtils.equalsIgnoreCase(ColumnTypeName,"string")
                         ||StringUtils.equalsIgnoreCase(ColumnTypeName,"bigint")
                        ||StringUtils.equalsIgnoreCase(ColumnTypeName,"ntext")
                        ) {
                	 	colstr =colstr+"\t\t\t\t"+ColumnTypeName + "";
                    } else if (StringUtils.equalsIgnoreCase(ColumnTypeName,"decimal")
                            || StringUtils.equalsIgnoreCase(ColumnTypeName,"number")
                            || StringUtils.equalsIgnoreCase(ColumnTypeName,"double")) {
                        if (cid.getDecimalDigits() == 0)
                            colstr =colstr+"\t\t\t\t"+ColumnTypeName + "(" + cid.getLength()+ ")";
                        else
                            colstr =colstr+"\t\t\t\t"+ColumnTypeName + "(" + cid.getLength()+ "," + cid.getDecimalDigits() + ")";
                    } else {
                        colstr =colstr+"\t\t\t\t"+ColumnTypeName + "(" + cid.getLength() + ")";
                    }
                	//colstr =colstr+ "(" + cid.getLength() + ")";
                	if(null != cid.getChnName() ||cid.getChnName().length()>0)
                		colstr = colstr + "\t\t\t\t\tCOMMENT \'"+cid.getChnName()+"\'";
	    	}
	    	if(area.equals("STEMP"))
	    	{
	    		colstr = colstr+",\n"+"\t"+"END_FLAG"+"\t"+"\t\t\t\t"+"STRING"+ "\t\t\t\t\tCOMMENT \'行结束符\'";
	    		storeStr  = "\t ROW FORMAT SERDE \'org.apache.hadoop.hive.contrib.serde2.MultiDelimitSerDe\' WITH SERDEPROPERTIES ("
	    			+ "\"field.delim\" = \""	+ "\\u007C\\u001C\")"
	    			+ " stored AS textfile;\n\n\n";
	    		partitionstr="";
	    	}
	    	else
	    	{
	    		tgtTableName=area+"_"+tgtTableName;
	    		storeStr = storeStr + " stored AS parquet;\n\n\n";
	    		partitionstr="DT STRING COMMENT \'数据日期\' ";
	    	}
	    	tgtTableName= area+"."+tgtTableName;
	    	sql.append ("\nDROP TABLE IF EXISTS "+tgtTableName+";\n\n");
	    	
	    	sql.append("CREATE TABLE IF NOT EXISTS "+ tgtTableName + "\n("+colstr+"\n)"+"COMMENT \'");
	    	sql.append(tablechnname+"\'\n");
	     
	    	
	    	if(StringUtils.isNotBlank(partitionstr))
	    	{
	    		sql.append("PARTITIONED BY (");
	    		sql.append(partitionstr);
	    		sql.append(")");
	    	}
	    	sqlStr = sqlStr +storeStr;
	    	
	    	sql.append(sqlStr);
	    	return sql.toString();
	    	
	    }
	  
	   public static class SortbySeqno implements Comparator{ 
	    	  
	    	 public int compare(Object obj1, Object obj2) { 
	    	    BDColumninfo cid1=(BDColumninfo)obj1; 
	    		BDColumninfo cid2=(BDColumninfo)obj2; 
	    		if (cid1.getSeqNo() > cid2.getSeqNo())
	    			   return 1;
	    			return -1;

	    	 } 
	    	   
	    	} 
	   

	 

}
