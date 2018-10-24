package model.entry;

import model.bean.SrcTableListEntity;
import model.constant.Constant;
import model.dao.TgtTableListDao;
import model.dao.metaDbConn;
import model.handle.ExcelHandle;
import model.handle.SqlScriptsHandle;
import model.utils.FileUtils;
import model.utils.MyPathUtil;
import model.utils.PropertiesUtil;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by 
 */
public class PdmGenSql {

    private static Connection metaDBConn = null;
    private Map<String, List>   pcmeMap = new HashMap<String,List>();
//    public static Connection getMetaDBConn() {
//        return metaDBConn;
//    }
   
    public static void main(String[] args) {

      /*  String entityName = args[0];
        String outputDir = args[1];
    	*/
    	String entityName ="";
        Properties metaDBConnParameters = new Properties();
       
        ExcelHandle eh = new ExcelHandle();
        try {
        	//获取工作路径
            String workPath= args[0];
     	    if(!workPath.endsWith(File.separator))
    		   workPath=workPath+File.separator;
     	    String locpath=MyPathUtil.getProjectPath();
			String dbpropfile = locpath+File.separator+Constant.dbPropertiesfile;
			metaDBConn = metaDbConn.connectMetaDB(dbpropfile);
           //初始化数据
            List<SrcTableListEntity> ttleList = TgtTableListDao.initTgtTableList(metaDBConn);
            String outputDir = workPath+"SQL"+File.separator;
            File tgtFolder = new File(outputDir);
     	    if(tgtFolder.exists()){
     	    	if(FileUtils.deleteDirectory(outputDir))
     	    		FileUtils.createDirectory(outputDir);
     	    }
     	    else
     	    	FileUtils.createDirectory(outputDir);
     	  
            //生成SQL脚本
            for(SrcTableListEntity entity: ttleList)
            {
            	Map<String,String > scripts = SqlScriptsHandle.genScripts(entity);
                    for (String scriptFile : scripts.keySet()) {
                                                
                        String targetFile = outputDir + File.separator + scriptFile;
            	        FileUtils.createFile(targetFile);
            	        FileUtils.writeToFile(targetFile,scripts.get(scriptFile), Constant.charset,false);
                    }
                }
            
	        }catch (Exception e){
	            e.printStackTrace();
	        }
	  }
   
  
}
