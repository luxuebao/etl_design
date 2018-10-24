package model.entry;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import model.constant.Constant;
import model.dao.metaDbConn;
import model.utils.ExcelUtil;
import model.utils.MyPathUtil;




public class impExcel2Mysql {
	
	
	public static void main(String[] args){ 
		
		String srcfileName = args[0];
		Connection conn = null;
		File file = new File(srcfileName);
		if (!file.exists()) {
			System.out.println("源文件 " + srcfileName + " 不存在!");
			System.exit(-1);
		}
		try {
			
			String locpath=MyPathUtil.getProjectPath();
			String dbpropfile = locpath+File.separator+Constant.dbPropertiesfile;
			conn = metaDbConn.connectMetaDB(dbpropfile);
			List<String> rowStrLst = new ArrayList<String>();
			List<List<String>> pageValue =new ArrayList<List<String>>();
			Map<String,List> map = new HashMap<String,List>();
		
			map = ExcelUtil.readXlsx2Map(srcfileName);
			insertCodeExchange(map,conn);
			System.exit(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
		finally {
         if(conn != null){
             try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         }
		}
		
	
	
	}
		public static void insertCodeExchange(Map<String ,List> map,Connection conn) throws SQLException
		{
			String sqlStr = "";
			long startTime = 0;
			PreparedStatement statement= null;
            String[] s = new String[50];
            try{
		         startTime=System.currentTimeMillis();
		         conn.setAutoCommit(false);
		     	 
		
		     	 for (String key : map.keySet()) {
		     		 //System.out.println("key= "+ key + " and value= " + map.get(key));
		     		 List<List<String>> ceList = map.get(key);
					 statement = conn.prepareStatement("delete from  Load_Code_Exchange where column1=?");
					 statement.setString(1,key);
			         statement.executeUpdate();
			         statement.close();
			         StringBuffer columnSb = new StringBuffer();
			         StringBuffer valueSb = new StringBuffer();
			         columnSb.append("(column1");
			         if(ceList.size() ==0 )
			        	 continue;
			         valueSb.append("(\'").append(key).append("\'");
			         for(int k=0;k<ceList.get(0).size();k++)
			         {
			        	  String columnName = String.format(",column%d",k+2);
		        		  columnSb.append(columnName);
		        		  valueSb.append(",?");
		        
			         }
			         columnSb.append(") ");
			         valueSb.append(")");
			         sqlStr = "INSERT INTO Load_Code_Exchange "+columnSb.toString()+" VALUES " +valueSb.toString() ;
			         statement = conn.prepareStatement(sqlStr );
			         //System.out.println("sqlStr="+sqlStr);
			         for (int i = 0;i< ceList.size();i++){
			        	  List<String> strLst = ceList.get(i);
			        	  
			        	  for(int j=0;j< strLst.size();j++)
			        	  {
			        		  if(null ==strLst.get(j))
			        			  s[j] ="";
			        		  else
			        			  s[j] = strLst.get(j);
			        		  statement.setString(j+1,s[j]);
			        	  }
			         
			        	  statement.addBatch();
			         }
			         
		        	  statement.executeBatch();
		            
		     	 }
     	      }catch (Exception e){
     	          e.printStackTrace();
     	          conn.rollback();
     			
     	      }finally{
     	    	  conn.commit();
	              statement.close();
     	    	  long endTime=System.currentTimeMillis();
     	          System.out.println("方法执行时间："+(endTime-startTime)+"ms");
     	     
     	      }
		}
		
		 /** 
	     * 连接Mysql 
	     *  
	     * @return  
	     */ 
	    public static Connection connectMysql(Properties metaDBParameters) throws Exception {

	        String jdbcURL = String.format("jdbc:mysql://%s/%s?user=%s&password=%s&useUnicode=true&characterEncoding=UTF8",
	                metaDBParameters.getProperty("dbHost"), metaDBParameters.getProperty("dbName"),
	                metaDBParameters.getProperty("userName"), metaDBParameters.getProperty("password"));

	        Class.forName("com.mysql.jdbc.Driver");
	        Connection metaDBConn = DriverManager.getConnection(jdbcURL);
	        return metaDBConn;
	    }
	     
}
