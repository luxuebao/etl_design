package model.entry;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.print.DocFlavor.URL;

import model.bean.ScriptPyInfoBean;
import model.bean.SrcTableListEntity;
import model.constant.Constant;
import model.dao.TgtTableListDao;
import model.dao.metaDbConn;
import model.handle.SqlScriptsHandle;
import model.utils.EncodeUtil;
import model.utils.EncodingDetect;
import model.utils.FileUtils;
import model.utils.MyPathUtil;
import model.utils.PropertiesUtil;

import org.apache.commons.logging.Log;



public class PdmGenPython {
	
	 
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
	   Connection metaDBConn=null;
	   String locpath=MyPathUtil.getProjectPath();
	   String dbpropfile = locpath+File.separator+Constant.dbPropertiesfile;

	   try {
				metaDBConn = metaDbConn.connectMetaDB(dbpropfile);
				//metaDbConn.setAutoCommits(false);
	   	} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	  /* LoadSdmExcelToDb lsetd = new LoadSdmExcelToDb();
	   lsetd.ImportExcelToDb(workPath, metaDBConn);
	   
	   //初始化数据
        List<SrcTableListEntity> ttleList = TgtTableListDao.initTgtTableList(metaDBConn);
        String outputDir = workPath+"SQL"+File.separator;
        CreateDirectory(outputDir);
        
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
	   String charsetName=EncodeUtil.getFileEncode(templatePathB);
	   System.out.println("charsetName="+charsetName);
	   String bodyStr = FileUtils.getFileContent(templatePathB, charsetName);
	  
	   for(File sqlfile:getFileList(SQLFilePath))
	   {
		 
		   CreateODS2PDATAFile(sqlfile,headerStr,bodyStr, workPath);
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
                } else if (fileName.toUpperCase().endsWith("SQL")) { // 判断文件名是否以.SQL结尾
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
	public static void CreateODS2PDATAFile(File sqlfile,String headerStr,String bodyStr,String  workPath)
	{
		String str = null;
		FileReader reader;
	    StringBuilder sb = new StringBuilder();
	    StringBuilder sbsql = new StringBuilder();
	    String jobname="";
	    String phyname="";
	    String regionname="";
	    String targetPath ="";
	    ScriptPyInfoBean spib = new ScriptPyInfoBean();
		try {
				
				String fileEncode=EncodingDetect.getJavaEncode(sqlfile.getAbsolutePath());         
				
				BufferedReader br = new BufferedReader(new InputStreamReader( new FileInputStream(sqlfile),fileEncode));  

				
	            sb.append("#Header\n");
	          
			    sb.append(headerStr);
			    sb.append("\n"); 
		        String sqlfilename=sqlfile.getName();
		        
		        sbsql.append("sqlStr=\"\"\"");
		        
		        Pattern pattern = Pattern.compile("(?<=\\$\\{)(.+?)(?=\\})");
				while((str = br.readLine()) != null) {
					List<String> ls=new ArrayList<String>();
					if(str.startsWith("\uFEFF")){  
						str = str.replace("\uFEFF", "");  
					}  

					if(str.trim().toUpperCase().startsWith("USE "))
					{
						regionname = getRegionName(str);
						//System.out.println("regionname="+regionname);
					}
					if(str.trim().startsWith("--"))
						continue;
				    Matcher matcher = pattern.matcher(str); 
				    while(matcher.find())
			            ls.add(matcher.group());
				    for(int k =0;k<ls.size();k++)
				    {
				    	String paraStr = ls.get(k);
				    	
				    	if(paraStr.contains("hiveconf:"))
				    		continue;
				    	String patternString = "${"+paraStr+"}";
				    	String newstr = "${hiveconf:"+ls.get(k).toUpperCase()+"}";
				    	str=str.replace(patternString, newstr);
				    }
				    sbsql.append(str+"\n");
				}
				sbsql.append("\"\"\"\n");
				br.close();
				REGIONTYPE regiontype = parseREGIONTYPE(regionname); 
				spib=createScriptPyInfo(regiontype,sqlfilename );
				System.out.println("sqlfilename="+sqlfilename);
				jobname = spib.getJobName();
				String loadMode = String.format("%c",jobname.charAt(jobname.lastIndexOf("_")+1));
				spib.setLoadMode(loadMode);
				spib.setSqlStr(sbsql.toString());
				sb.append("#参数 ");
		        sb.append("\nphyname = \"\"\""+spib.getPhyName()+"\"\"\"\n");
				sb.append("loadmode =\"\"\""+ loadMode+"\"\"\"\n");
				sb.append("jobname = \"\"\""+jobname+spib.getTaskCode()+"\"\"\"\n");
		        sb.append("regionname =\"\"\""+ regionname+"\"\"\"\n");
		        sb.append(sbsql.toString());
		        
				sb.append("\n\n #Body");
				sb.append("\n");
			    sb.append(bodyStr);
			}catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		   targetPath = workPath+spib.getRegionName()+File.separator;
		   File tgtFolder = new File(targetPath);
		   if(!tgtFolder.exists())
		    	FileUtils.createDirectory(targetPath);
			String targetFile = targetPath+spib.getPhyName()+File.separator+jobname+File.separator+"bin"+File.separator+jobname+spib.getTaskCode()+".py";
	        FileUtils.createFile(targetFile);
	       
	        FileUtils.writeToFile(targetFile, sb.toString().replace("\r\n", "\n"), Constant.charset,false);

	}
	private static String getPhyName(String jobname)
	{
		String[] tgnameArr = jobname.split("_");
		String phyname="";
		boolean firstItem = true;
		for(int i=0;i<tgnameArr.length-2;i++)
		{
			if(!firstItem)
				phyname=phyname+"_";
			phyname=phyname+tgnameArr[i];
			firstItem = false;
		}
		return phyname;
	}
	/** 
     * 数据库类型,枚举 
     *  
     */  
    public static enum REGIONTYPE {  
        STEMP,ODS, PDATA, FXJX, FXGL,SUM, OTHER, EMPTY  
    }  
  
    /** 
     * 根据字符串,判断数据库类型 
     *  
     * @param databasetype 
     * @return 
     */  
    private static REGIONTYPE parseREGIONTYPE(String regionname) {  
        // 空类型  
        if (null == regionname || regionname.trim().length() < 1) {  
            return REGIONTYPE.EMPTY;  
        }  
     // 截断首尾空格,转换为大写  
        regionname = regionname.trim().toUpperCase();  
        if (regionname.contains("ODS")) {  
            //  
            return REGIONTYPE.ODS;  
        }  
        if (regionname.contains("STEMP")) {  
            //  
            return REGIONTYPE.STEMP;  
        }  
        if (regionname.contains("PDATA")) {  
            //  
            return REGIONTYPE.PDATA;  
        }  
        if (regionname.contains("FXJX")) {  
            //  
            return REGIONTYPE.FXJX;  
        }  
        if (regionname.contains("FXGL")) {  
            //  
            return REGIONTYPE.FXGL;  
        }  
        if (regionname.contains("SUM")) {  
            //  
            return REGIONTYPE.SUM;  
        } 
        // 默认,返回其他  
        return REGIONTYPE.OTHER;  
    }
    private static ScriptPyInfoBean createScriptPyInfo(REGIONTYPE regiontype,String instr )
    {
    	ScriptPyInfoBean spib = new ScriptPyInfoBean(); 
    	if(REGIONTYPE.PDATA.equals(regiontype))
    	{
    		spib.setTaskCode("0200");
    		spib.setRegionName("PDM");
    		spib.setJobName(instr.substring(0,instr.length()-4));
			spib.setPhyName(getPhyName(spib.getJobName()));
    	}
    	else if(REGIONTYPE.SUM.equals(regiontype)){
			spib.setTaskCode("0300");
    		spib.setRegionName("SUM");
    		spib.setJobName(instr.substring(0,instr.length()-4));
			spib.setPhyName(instr.substring(0,instr.lastIndexOf("_")));
			
		}
    	else if(REGIONTYPE.FXJX.equals(regiontype)){
    		spib.setTaskCode("0400");
    		spib.setRegionName("FXJX");
    		spib.setJobName(instr.substring(0,instr.length()-4));
			spib.setPhyName(instr.substring(0,instr.lastIndexOf("_")));
		}
    	else if(REGIONTYPE.FXGL.equals(regiontype)){
    		spib.setTaskCode("0400");
    		spib.setRegionName("FXGL");
    		spib.setJobName(instr.substring(0,instr.length()-4));
			spib.setPhyName(instr.substring(0,instr.lastIndexOf("_")));
		}
    	else if(REGIONTYPE.OTHER.equals(regiontype))
		{
    		spib.setTaskCode("0000");
    		spib.setRegionName("OTHER");
    		spib.setJobName(instr.substring(0,instr.length()-4));
			spib.setPhyName(instr.substring(0,instr.lastIndexOf("_")));
			
		}
    	else
    		return null;
    	return spib;
    }
	public static boolean CreateDirectory(String outputDir )
	{
		File tgtFolder = new File(outputDir);
 	    if(!tgtFolder.exists())
 	    	FileUtils.createDirectory(outputDir);
 	    return true;
	}
	private static String getRegionName(String sqlStr)
	{
		
				String regionname="";
				Pattern pattern = Pattern.compile("(?<=\\$\\{)(.+?)(?=\\})");
				sqlStr= sqlStr.trim().toUpperCase(); 
				Matcher matcher = pattern.matcher(sqlStr); 
				while(matcher.find())
			            regionname=matcher.group();
				if(regionname.length()>0)
					regionname= regionname.replace("HIVECONF:", "").replace("DB", "");
				return regionname;
	}
	public static List<File> getFileListTbl(String strPath,String tblName) {
		List<File> filelist = new ArrayList<File>();
        File dir = new File(strPath);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) { // 判断是文件还是文件夹
                    getFileList(files[i].getAbsolutePath()); // 获取文件绝对路径
                } else if (fileName.endsWith("SQL") && fileName.startsWith(tblName) ) { // 判断文件名是否以.SQL结尾
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
