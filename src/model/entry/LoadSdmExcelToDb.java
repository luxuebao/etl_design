package model.entry;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import model.constant.Constant;
import model.dao.TgtTableListDao;
import model.dao.metaDbConn;
import model.bean.TgtTableEtlRulesEntity;
import model.bean.SrcTableListEntity;
import model.handle.ReadingEtlTaskExcel;
import model.utils.MyPathUtil;
import model.utils.PropertiesUtil;

public class LoadSdmExcelToDb {
	
	
	public static void main(String[] args) {
		Connection conn = null;

			String workPath = args[0];
			if(!workPath.endsWith(File.separator))
				workPath=workPath+File.separator;
			try {
				String locpath=MyPathUtil.getProjectPath();
				String dbpropfile = locpath+File.separator+Constant.dbPropertiesfile;
				conn = metaDbConn.connectMetaDB(dbpropfile);
			
				conn.setAutoCommit(false);
			
				int ret = ImportExcelToDb(workPath,conn);
			conn.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public static int ImportExcelToDb(String filePath,Connection conn)
	{
		
		File srcFilePath = new File(filePath+"SDM");
        if(!srcFilePath.exists()){
        	System.out.println("文件夹"+srcFilePath +"不存在！");
        	return -1;
        }
		List<File> fileList = getFileList(filePath+"SDM");
	    Map<String, List> map= new HashMap();
		List<SrcTableListEntity> srcTLsList = new ArrayList();
		List<TgtTableEtlRulesEntity> tRulesList = new ArrayList();
		
		try {
			
			map = ReadingEtlTaskExcel.readXls(fileList);
			srcTLsList = map.get("srcTLsList");
			tRulesList = map.get("tRulesList");
			TgtTableListDao.insertIntoSrcTableList(srcTLsList, conn);
			TgtTableListDao.insertIntoTgtTableEtlRules(tRulesList, conn);
			conn.commit();
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		return 0;

	}
	
	
	public static List<File> getFileList(String strPath) {
		List<File> fileList = new ArrayList<>(); 
        File dir = new File(strPath);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) { // 判断是文件还是文件夹
                    getFileList(files[i].getAbsolutePath()); // 获取文件绝对路径
                } else if (fileName.endsWith("xls")) { // 判断文件名是否以.xls结尾
                    String strFileName = files[i].getAbsolutePath();
                    System.out.println("---" + strFileName);
                    fileList.add(files[i]);
                } else {
                    continue;
                }
            }

        }
        return fileList;
    }
}