package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import model.bean.BDColumninfo;
import model.utils.StringOperateUtils;

public class BdTableColumnInfoDao {
	public static Map<String, List> getTableColumnInfolist(int type, Connection conn) 
    {
    	List<BDColumninfo> columnlst = new ArrayList<BDColumninfo>();
    	  Map<String, List> map = new HashMap<String, List>();
    	  Statement statement = null;
    	  ResultSet resultSet = null;
    	  String conditionStr = "";
    	  try {
					statement = conn.createStatement();
					if(type == 1)//入ODS
						 conditionStr=" AND T.TGT_TBL_IS_INODS='1'";
					else if(type == 2)//入PDATA
						 conditionStr=" AND T.TGT_TBL_IS_INPDATA='1'";
					else 
						 conditionStr="";
					String sqlStr ="SELECT C.TGT_SYS_CD,C.SRC_SCHEMA,C.TGT_TBL_EN_NAME,C.TGT_TBL_CN_NAME,C.TGT_COL_SEQNO, C.TGT_COL_EN_NAME,C.TGT_COL_CN_NAME, C.TGT_COL_TYPE_CD,C.TGT_COL_LENGTH, C.TGT_COL_DEC_PLACE,C.TGT_COL_ISPK ,C.TGT_COL_ISNOTNULL, C.TGT_COL_DEFAULT_VALUE, C.TGT_COL_STATUS_CD "
							+ "FROM BD_TABLE_COLUMN_INFO C ,BD_TABLE_INFO T "
							+ "WHERE C.TGT_TBL_EN_NAME=T.TGT_TBL_EN_NAME AND C.TGT_SYS_CD=T.TGT_SYS_CD AND C.SRC_SCHEMA=T.SRC_SCHEMA AND C.TGT_COL_STATUS_CD='0' ";  
					sqlStr= sqlStr+conditionStr;
					sqlStr = sqlStr + " ORDER BY T.TGT_SYS_CD,T.SRC_SCHEMA,C.TGT_TBL_EN_NAME";
					System.out.println("sqlStr="+sqlStr);
					resultSet = statement.executeQuery(sqlStr);		
					while (resultSet.next()) {
			    		BDColumninfo cid = new BDColumninfo();
			    		String tablename=resultSet.getString("TGT_TBL_EN_NAME");
			    		String sysname = resultSet.getString("TGT_SYS_CD");
			    		String schema =resultSet.getString("SRC_SCHEMA");
			    		cid.setTableId(sysname+"."+schema+"."+tablename);
			    		cid.setSysName(sysname);
			    		cid.setTableName(tablename);
			    		String tmpStr =resultSet.getString("TGT_TBL_CN_NAME");
						if(null == tmpStr)
							tmpStr="";
						cid.setTableChnName(tmpStr.trim().replace(";","").replace("'",""));
						cid.setSeqNo(resultSet.getInt("TGT_COL_SEQNO"));
						cid.setName(resultSet.getString("TGT_COL_EN_NAME"));
						
						tmpStr =resultSet.getString("TGT_COL_CN_NAME");
						if(null == tmpStr)
							tmpStr="";
						cid.setChnName(tmpStr.trim().replace(";","").replace("'",""));
						cid.setType(resultSet.getString("TGT_COL_TYPE_CD"));
						cid.setLength(StringOperateUtils.stringToInt(resultSet.getString("TGT_COL_LENGTH")));
						cid.setDecimalDigits(StringOperateUtils.stringToInt(resultSet.getString("TGT_COL_DEC_PLACE")));
						cid.setPk( resultSet.getInt("TGT_COL_ISPK"));
						cid.setNotNull( resultSet.getInt("TGT_COL_ISNOTNULL"));
						cid.setValue(resultSet.getString("TGT_COL_DEFAULT_VALUE"));
						cid.setStatus(resultSet.getString("TGT_COL_STATUS_CD"));
						cid.setIsPartition("");//暂时定位空，等表中加上后增加
						columnlst.add(cid);
			    	}
					
					map = classifyColumn(columnlst);
					return  map;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}finally
						{
							if(null !=resultSet)
							{
									try {
										resultSet.close();
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
							}
							if(null != statement)
							{
									try {
										statement.close();
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
							}
						
				}
      }
	
      private  static Map classifyColumn(List<BDColumninfo> columnlst) {
    	        // 步骤1
    			Map<String, List> map = new HashMap<String, List>();
    	        for (Iterator it = columnlst.iterator(); it.hasNext();) {
    	            BDColumninfo cid = (BDColumninfo) it.next();
    	 
    	            if (map.containsKey(cid.getTableId())) { // 如果已经存在这个数组，就放在这里
    	                List columnList = map.get(cid.getTableId());
    	                columnList.add(cid);
    	            } else {
    	                List columnList = new ArrayList(); // 重新声明一个数组list
    	                columnList.add(cid);
    	                map.put(cid.getTableId(), columnList);
    	            }
    	        }
    	        return map;
    		}
      
      
      
      

    	}