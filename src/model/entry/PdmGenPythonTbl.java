package model.entry;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.bean.SrcTableListEntity;
import model.constant.Constant;
import model.dao.TgtTableListDao;
import model.dao.metaDbConn;
import model.handle.SqlScriptsHandle;
import model.utils.EncodingDetect;
import model.utils.FileUtils;
import model.utils.MyPathUtil;

import org.apache.commons.logging.Log;



public class PdmGenPythonTbl {
	
	 
	/**
	 * 
	 * @author Yanxb
	 * 
	 */
	private static Log logger = null;
	   
	public static void main(String[] args) {
	    	
	   String workPath = args[0];
	   if(!workPath.endsWith(File.separator))
		   workPath=workPath+File.separator;
	   String entityName = args[1].toUpperCase();
	   Connection metaDBConn=null;
	   try {
		   String locpath=MyPathUtil.getProjectPath();
			String dbpropfile = locpath+File.separator+Constant.dbPropertiesfile;
			metaDBConn = metaDbConn.connectMetaDB(dbpropfile);
				//metaDbConn.setAutoCommits(false);
	   	} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	 
	   
	   //初始化数据
      /*  List<SrcTableListEntity> ttleList = TgtTableListDao.initTgtTableListTbl(entityName,metaDBConn);
        String outputDir = workPath+"SQL"+File.separator;
        CreateDirectory(outputDir);
        
        LoadSdmExcelTbl lsetd = new LoadSdmExcelTbl();
	   	lsetd.ImportExcelToDb(workPath,entityName, metaDBConn);
        //生成SQL脚本
        for(SrcTableListEntity entity: ttleList)
        {
        	Map<String,String > scripts = SqlScriptsHandle.genScripts(entity);
                for (String scriptFile : scripts.keySet()) {
                                            
                    String targetFile = outputDir + File.separator + scriptFile;
        	        FileUtils.createFile(targetFile);
        	        FileUtils.writeToFile(targetFile,scripts.get(scriptFile), Constant.charset,false);
                }
            }*/
       
	   //生成python脚本
	   String SQLFilePath = workPath+"SQL"+File.separator;
	 //文件头
	   String templatePathH = workPath + "templates"+File.separator+"ods2pdata.header";
	   String headerStr = FileUtils.getFileContent(templatePathH, Constant.charset);
	  //文件内容
	   String templatePathB = workPath + "templates"+File.separator+"ods2pdata.body";
	   String bodyStr = FileUtils.getFileContent(templatePathB, Constant.charset);
	   
	   PdmGenPython pgp = new PdmGenPython();
	   for(File sqlfile:pgp.getFileListTbl(SQLFilePath,entityName))
	   {
		   pgp.CreateODS2PDATAFile(sqlfile,headerStr,bodyStr, workPath );
	   }
        
	}
	public static List<File> getFileList(String strPath) {
		List<File> filelist = new ArrayList<File>();
        File dir = new File(strPath);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) { // 判断是文件还是文件夹
                    getFileList(files[i].getAbsolutePath()); // 获取文件绝对路径
                } else if (fileName.endsWith("SQL")) { // 判断文件名是否以.SQL结尾
                    String strFileName = files[i].getAbsolutePath();
                    //System.out.println("---" + strFileName);
                    filelist.add(files[i]);
                } else {
                    continue;
                }
            }

        }
        return filelist;
    }
	
	
}
