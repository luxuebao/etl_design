package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.bean.BDTableinfo;
import model.constant.Constant;
import model.utils.DateUtil;


public class BdTableInfoDao {
    public static List<BDTableinfo> getBDTablelistBySysid(String sysid,String batchDate,Connection conn)
    {
    	List<BDTableinfo> tablelst = new ArrayList<BDTableinfo>();
    	
    	PreparedStatement  statement = null;
    	ResultSet resultSet = null;
    	
		try {
				statement = conn.prepareStatement("SELECT SRC_SYS_CD,SRC_SCHEMA,SRC_TBL_EN_NAME,SRC_TBL_TYPE_CD,SRC_TBL_INC_COLUMN_NAME,SRC_TBL_INC_TIME_TYPE_CD ,SRC_TBL_EXT_TYPE_CD,SRC_TBL_EXT_INC_DAY_COUNT from BD_TABLE_INFO where TGT_TBL_STATUS_CD='0' and (SRC_TBL_IS_INODS='1' OR SRC_TBL_IS_INPDATA='1') and SRC_SYS_CD=?");
			
	    	
		    	statement.setString(1,sysid);
		    	resultSet = statement.executeQuery();  
		    	while (resultSet.next()) {
					BDTableinfo tid = new BDTableinfo();
					tid.setSysid(sysid);
					String tableid =resultSet.getString("SRC_SYS_CD")+"."+ resultSet.getString("SRC_SCHEMA")+"."+resultSet.getString("SRC_TBL_EN_NAME");
		    		tid.setTableid(tableid);
					tid.setSchema(resultSet.getString("SRC_SCHEMA"));
					tid.setTablename(resultSet.getString("SRC_TBL_EN_NAME").trim().toUpperCase());
					tid.setTabletype(resultSet.getString("SRC_TBL_TYPE_CD").trim());
					String tmpStr = resultSet.getString("SRC_TBL_EXT_TYPE_CD").trim();
					tid.setIncFlag(tmpStr);
					if(tid.getIncFlag().equals(Constant.ISINCREMNETAL))//处理增量抽取
					{
						 tid.setSpanDays(resultSet.getInt("SRC_TBL_EXT_INC_DAY_COUNT"));
						 tid.setIncColumn(resultSet.getString("SRC_TBL_INC_COLUMN_NAME"));
						 tid.setIncColumntype(resultSet.getString("SRC_TBL_INC_TIME_TYPE_CD"));
						 tid.setStartDate(DateUtil.dateToStr(DateUtil.addDayByDate(DateUtil.strToDate(batchDate),-1*tid.getSpanDays())));
					}
					tablelst.add(tid);
		    		}
		    	resultSet.close();
		    	statement.close();
 		
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		return  tablelst;
		
    }
    public static List<BDTableinfo> getBDTablelistAll(Connection conn)
    {
    	List<BDTableinfo> tablelst = new ArrayList<BDTableinfo>();
    	
    	Statement statement = null;
   	  	ResultSet resultSet = null;
   	  	try {
					statement = conn.createStatement();
					resultSet = statement.executeQuery("SELECT SRC_SYS_CD,SRC_SCHEMA,SRC_TBL_EN_NAME,SRC_TBL_TYPE_CD,SRC_TBL_INC_COLUMN_NAME,SRC_TBL_INC_TIME_TYPE_CD ,SRC_TBL_EXT_TYPE_CD,SRC_TBL_EXT_INC_DAY_COUNT from BD_TABLE_INFO where TGT_TBL_STATUS_CD='0' and (SRC_TBL_IS_INODS='1' OR SRC_TBL_IS_INPDATA='1') ORDER BY SRC_SYS_CD");
			
	    	
		    	 
			    	while (resultSet.next()) {
			    		BDTableinfo tid = new BDTableinfo();
			    		String syscd= resultSet.getString("SRC_SYS_CD");
						tid.setSysid(syscd);
						String tableid =syscd+"."+ resultSet.getString("SRC_SCHEMA")+"."+resultSet.getString("SRC_TBL_EN_NAME");
			    		tid.setTableid(tableid);
						tid.setSchema(resultSet.getString("SRC_SCHEMA"));
						tid.setTablename(resultSet.getString("SRC_TBL_EN_NAME").trim().toUpperCase());
						tid.setTabletype(resultSet.getString("SRC_TBL_TYPE_CD").trim());
						String tmpStr = resultSet.getString("SRC_TBL_EXT_TYPE_CD").trim();
						tid.setIncFlag(tmpStr);
						if(tid.getIncFlag().equals(Constant.ISINCREMNETAL))//处理增量抽取
						{
							 tid.setSpanDays(resultSet.getInt("SRC_TBL_EXT_INC_DAY_COUNT"));
							 tid.setIncColumn(resultSet.getString("SRC_TBL_INC_COLUMN_NAME"));
							 tid.setIncColumntype(resultSet.getString("SRC_TBL_INC_TIME_TYPE_CD"));
						//	 tid.setStartDate(DateUtil.dateToStr(DateUtil.addDayByDate(DateUtil.strToDate(batchDate),-1*tid.getSpanDays())));
						}
						tablelst.add(tid);
		    		}
		    	resultSet.close();
		    	statement.close();
 		
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		return  tablelst;
		
    }
    public static BDTableinfo getBDTableInfo(String sysid,String schema,String tableName,Connection conn)
    {
    	
    	PreparedStatement  statement = null;
   	  	ResultSet resultSet = null;
   	  	BDTableinfo tid = new BDTableinfo();
   	  	try {
   	  				statement = conn.prepareStatement("SELECT SRC_SYS_CD,SRC_SCHEMA,SRC_TBL_EN_NAME,SRC_TBL_TYPE_CD,SRC_TBL_INC_COLUMN_NAME,SRC_TBL_INC_TIME_TYPE_CD ,SRC_TBL_EXT_TYPE_CD,SRC_TBL_EXT_INC_DAY_COUNT from BD_TABLE_INFO"
					          + " where TGT_TBL_STATUS_CD='0' AND SRC_SYS_CD=? AND SRC_SCHEMA=? AND TGT_TBL_EN_NAME=?");
   	  				statement.setString(1,sysid);
   	  				statement.setString(2,schema);
   	  				statement.setString(3,tableName);
   	  				resultSet = statement.executeQuery();
   	  				
   	  				while (resultSet.next()) {
			    		
			    		String syscd= resultSet.getString("SRC_SYS_CD");
						tid.setSysid(syscd);
						String tableid =syscd+"."+ resultSet.getString("SRC_SCHEMA")+"."+resultSet.getString("SRC_TBL_EN_NAME");
			    		tid.setTableid(tableid);
						tid.setSchema(resultSet.getString("SRC_SCHEMA"));
						tid.setTablename(resultSet.getString("SRC_TBL_EN_NAME").trim().toUpperCase());
						tid.setTabletype(resultSet.getString("SRC_TBL_TYPE_CD").trim());
						String tmpStr = resultSet.getString("SRC_TBL_EXT_TYPE_CD").trim();
						tid.setIncFlag(tmpStr);
						if(tid.getIncFlag().equals(Constant.ISINCREMNETAL))//处理增量抽取
						{
							 tid.setSpanDays(resultSet.getInt("SRC_TBL_EXT_INC_DAY_COUNT"));
							 tid.setIncColumn(resultSet.getString("SRC_TBL_INC_COLUMN_NAME"));
							 tid.setIncColumntype(resultSet.getString("SRC_TBL_INC_TIME_TYPE_CD"));
						//	 tid.setStartDate(DateUtil.dateToStr(DateUtil.addDayByDate(DateUtil.strToDate(batchDate),-1*tid.getSpanDays())));
						}
					
		    		}
		    	resultSet.close();
		    	statement.close();
 		
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		return  tid;
		
    }
}
