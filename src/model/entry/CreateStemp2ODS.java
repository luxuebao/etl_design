package model.entry;

import java.io.File;
import java.sql.Connection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import model.bean.BDColumninfo;
import model.bean.BDTableinfo;
import model.constant.Constant;
import model.dao.BdTableColumnInfoDao;
import model.dao.BdTableInfoDao;
import model.dao.metaDbConn;
import model.utils.FileUtils;
import model.utils.PropertiesUtil;

import org.apache.commons.logging.Log;


public class CreateStemp2ODS {
	
	 
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
		    String incFlag="0";
		    //String sqlStr1=CreateDDLImpl("ABC.TABLE",null,"STEMP");
		    //System.out.println("sqlStr1="+sqlStr1);
		    Map<String, List>  tablemap = new HashMap<String, List>();
		    
		    String workPath = args[0];
			if(!workPath.endsWith(File.separator))
				   workPath=workPath+File.separator;
			String SQLFilePath = workPath+"SQL"+File.separator;
			Properties metaDBConnParameters = new Properties();

	        String dbConfigPath =Constant.dbPropertiesfile;
	        metaDBConnParameters.setProperty("dbHost", PropertiesUtil.GetValueByKey(dbConfigPath, "dbHost"));
	        metaDBConnParameters.setProperty("dbName", PropertiesUtil.GetValueByKey(dbConfigPath, "dbNameMgt"));
	        metaDBConnParameters.setProperty("userName", PropertiesUtil.GetValueByKey(dbConfigPath, "userName"));
	        metaDBConnParameters.setProperty("password", PropertiesUtil.GetValueByKey(dbConfigPath, "password"));  
			Connection conn=null;
			try {
				conn = metaDbConn.connectMetaDB(metaDBConnParameters);
			} catch (Exception e) {
				System.err.println(e);
				System.exit(-1);
			}
		
			//选出入ODS的所有表和字段
			type = 1; //1:入ODS，2：入PDATA
	   		tablemap = BdTableColumnInfoDao.getTableColumnInfolist(1,conn);
	   	   
	   	  
	   		for (Entry<String, List> entry : tablemap.entrySet()) {
	   			String tableid =entry.getKey();
    			//得到批处理数据字典表中对应系统所有的表
    			List<BDColumninfo> fieldlst =  entry.getValue();
    		    Collections.sort(fieldlst,new SortbySeqno());     //按照seqNo升序排列
    		    String para[] = tableid.split("\\.");
    		    BDTableinfo tid =BdTableInfoDao.getBDTableInfo(para[0],para[1],para[2],conn);
    		    if(null == tid.getIncFlag())
    		    	incFlag = "0";
    		    else
    		    	incFlag = tid.getIncFlag();
    		    //CreateStemp2ODSFile(workPath,tid,fieldlst,incFlag);
    		    CreateStemp2ODSFile(workPath,tid,fieldlst,incFlag);
    		   
	   		}	
	}
	private static void CreateStemp2ODSFile(String workPath,BDTableinfo tid,List<BDColumninfo> fieldlst,String incType)
	{
		String constant= "";
		String fileName="";
		StringBuffer hcmdStr = new StringBuffer();
		StringBuffer pkStr = new StringBuffer();
		StringBuffer notnullStr = new StringBuffer();
		String jobname = "";
		String loadMode ="";
		//文件名处理
        if(incType.equals(Constant.ISINCREMNETAL))//增量
        		loadMode="I";
        else//全量
            	loadMode="A";
        String phyname = "ODS_"+tid.getSysid()+"_"+tid.getTablename();
        jobname=phyname+"_"+loadMode;
        fileName  =workPath+"ODS"+File.separator + phyname+ File.separator + jobname+ File.separator+"bin"+File.separator+jobname+"0100.py";
        
	    FileUtils.deleteFile(fileName);
		//文件头处理
	    String templatePathH = workPath + "\\templates\\load2ods.header";
	    String headerStr = FileUtils.getFileContent(templatePathH, Constant.charset);
	    
	    //文件内容处理
	    String templatePathB = workPath + "\\templates\\load2ods.body";
	    String bodyStr = FileUtils.getFileContent(templatePathB, Constant.charset);
		//文件参数处理
		StringBuffer sb = new StringBuffer(); 
		sb.append(headerStr);
		sb.append("\n\n");
		
		sb.append("# 参数 \n");
		sb.append("sysname = \""+tid.getSysid()+"\"\n");
		sb.append("schemaname = \""+tid.getSchema()+"\"\n");
		sb.append("tablename = \""+tid.getTablename()+"\"\n");
		sb.append("jobname= \"" +jobname +"\"\n");
		sb.append("phyname= \"" +phyname +"\"\n");
		StringBuffer columnStr = new StringBuffer();                                                                                                                                                                                                                                                                                                                                                                                         
        for (BDColumninfo ci: fieldlst) {                                                                                                                                                                                                                                                                                                                                          
           		columnStr.append(ci.getName()+","); 
           		if(ci.getPk()==1)
           			pkStr.append(ci.getName()+","); 
           		if(ci.getNotNull() == 1)
           			notnullStr.append(ci.getName()+","); 
        }                                                                                                                                                                                                                                                                                                                                                                     
        columnStr.delete(columnStr.length()-1, columnStr.length()); 
        if(pkStr.length()>0)
        		pkStr.delete(pkStr.length()-1, pkStr.length()); 
        if(notnullStr.length()>0)
        	notnullStr.delete(notnullStr.length()-1, notnullStr.length()); 
        sb.append("columns= \""+columnStr+"\"\n");
        sb.append("pks= \""+pkStr+"\"\n");
        sb.append("nns= \""+notnullStr+"\"\n");
        sb.append("loadmode = \""+loadMode+"\"\n");
        sb.append("\n\n");
        sb.append(bodyStr);
        if(notnullStr.length()==0 && pkStr.length()==0 && incType.equals(Constant.ISINCREMNETAL))
        	System.out.println("No PK No not Null sysid="+tid.getSysid()+"	tablename="+tid.getTablename());
        FileUtils.writeToFile(fileName, sb.toString(), Constant.charset,true);
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
