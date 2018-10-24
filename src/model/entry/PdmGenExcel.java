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
public class PdmGenExcel {

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
        	
            List<DwTableEntity> dwtblList = DwTableDao.initDwTable(metaDBConn);
          //获取工作路径
            String workPath= args[0];
     	    if(!workPath.endsWith(File.separator))
    		   workPath=workPath+File.separator;
     	    //清空目标文件夹
     	    String outputDir=workPath+"SDM" +File.separator;
     	    CreateDirectory(outputDir);
     	  
            //生成Excel表
			System.out.println("生成Excel表！");
			List<DwTableEntity> nodefineTable = new ArrayList<DwTableEntity>();
            for(DwTableEntity dtel: dwtblList){
            		String tbName = dtel.getPhyName()+"-"+dtel.getTableName();
	            	String filePath =workPath +"SDM"+File.separator+tbName+".xls";
	            	int ret =eh.WritePdm2Excel(dtel,filePath,tbName);
	            	if(ret == 1)
	            		nodefineTable.add(dtel);
        			
        	}
            eh.createNoDefEntity(nodefineTable,workPath+"SDM"+File.separator);
            //生成SQL脚本
          /*  String outputDir = rootPath+"SQL"+File.separator;
            for(EtlTasksEntity task: etlTasksList){
            	
            		Map<String,String > scripts = SqlScriptsHandle.genScripts(task);
                    for (String scriptFile : scripts.keySet()) {
                        FileWriter fileWriter = new FileWriter(outputDir + File.separator + scriptFile);

                        fileWriter.write(scripts.get(scriptFile));

                        fileWriter.close();
                    }
                }
            */
        }catch (Exception e){
            e.printStackTrace();
        }
    }
	private static boolean CreateDirectory(String outputDir )
	{
		File tgtFolder = new File(outputDir);
 	    if(tgtFolder.exists()){
 	    	FileUtils.deleteDirectory(outputDir);
 	    	FileUtils.createDirectory(outputDir);
 	    }
 	    else
 	    	FileUtils.createDirectory(outputDir);
 	    return true;
	}
  
}
