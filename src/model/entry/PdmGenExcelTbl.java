package model.entry;

import model.bean.DwTableEntity;
import model.constant.Constant;
import model.dao.DwTableDao;
import model.dao.metaDbConn;
import model.handle.ExcelHandle;
import model.utils.FileUtils;
import model.utils.MyPathUtil;
import model.utils.PropertiesUtil;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by 
 */
public class PdmGenExcelTbl {

    private static Connection metaDBConn = null;
    private Map<String, List>   pcmeMap = new HashMap<String,List>();
    public static Connection getMetaDBConn() {
        return metaDBConn;
    }
   
    public static void main(String[] args) {

     
        ExcelHandle eh = new ExcelHandle();
        try {
        	String locpath=MyPathUtil.getProjectPath();
			String dbpropfile = locpath+File.separator+Constant.dbPropertiesfile;
			metaDBConn = metaDbConn.connectMetaDB(dbpropfile);
            
           
          //获取工作路径
            String workPath= args[0];
     	    if(!workPath.endsWith(File.separator))
    		   workPath=workPath+File.separator;
     	    
     	    String entityName = args[1].toUpperCase();
     	    DwTableEntity dwtbl = DwTableDao.initDwTableByTable("PDATA",entityName,metaDBConn);
            if(dwtbl == null)
            {
            	System.out.println("无法获取表定义，表名="+entityName);
            	System.exit(-1);
            }
     	    //生成Excel表
    		String tbName = dwtbl.getPhyName()+"-"+dwtbl.getTableName();
        	String filePath =workPath +"SDM"+File.separator+tbName+".xls";
        	int ret =eh.WritePdm2Excel(dwtbl,filePath,tbName);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
	private static boolean CreateDirectory(String outputDir )
	{
		File tgtFolder = new File(outputDir);
 	    if(tgtFolder.exists()){
 	    	if(FileUtils.deleteDirectory(outputDir))
 	    		FileUtils.createDirectory(outputDir);
 	    }
 	    else
 	    	FileUtils.createDirectory(outputDir);
 	    return true;
	}
  
}
