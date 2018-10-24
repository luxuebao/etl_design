package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.bean.TgtTableEtlRulesEntity;
import model.bean.SrcTableListEntity;

public class TgtTableListDao {
	
	public static List<SrcTableListEntity> initTgtTableList(Connection metaDBConn)
	{
		List<SrcTableListEntity> ttleList = new ArrayList<SrcTableListEntity>();
		List<SrcTableListEntity> ttleInfoList = new ArrayList<SrcTableListEntity>();
		Statement statement = null;
		ResultSet rs = null;
		try {
			statement= metaDBConn.createStatement();
			rs = statement.executeQuery("select distinct TGT_ENTITY ,TGT_DB_SCHEMA, PHY_NAME from  MODEL_SRC_TABLE_LIST ");
	        while (rs.next()) {
	        	SrcTableListEntity ttle = new SrcTableListEntity();
	        	ttle.setTgtEntity(rs.getString("TGT_ENTITY"));
	        	ttle.setTgtDbSchema(rs.getString("TGT_DB_SCHEMA"));
	        	ttle.setPhyName(rs.getString("PHY_NAME"));
	        	ttleList.add(ttle);
		    }
	        rs.close();
	        for(SrcTableListEntity ttle :ttleList)
	        {
	        	
	        	SrcTableListEntity ttleEntity = getTgtTableInfo(ttle, metaDBConn);
	        	if(null == ttleEntity )
	        		continue;
	        	ttleInfoList.add(ttleEntity);
	        }
	        return ttleInfoList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally{
			if(rs!=null)
			{
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(statement!=null)
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
	public static String getTableNameByPhyName(String phyName,Connection metaDBConn)
	{
		String tableName = "";
		Statement statement = null;
		ResultSet rs = null;
		try {
			statement= metaDBConn.createStatement();
			rs = statement.executeQuery("select distinct TGT_ENTITY ,TGT_DB_SCHEMA, PHY_NAME from  MODEL_SRC_TABLE_LIST WHERE PHY_NAME=\'"+phyName+"\'");
	        while (rs.next()) {
	        		tableName = rs.getString("TGT_ENTITY");
	        }
	        if(tableName.length()== 0)
	        	return null;
	     
	        return phyName+"-"+tableName;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally{
			if(rs!=null)
			{
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(statement!=null)
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
	public static List<SrcTableListEntity> initTgtTableListTbl(String tableName,Connection metaDBConn)
	{
		List<SrcTableListEntity> ttleList = new ArrayList<SrcTableListEntity>();
		List<SrcTableListEntity> ttleInfoList = new ArrayList<SrcTableListEntity>();
		Statement statement = null;
		ResultSet rs = null;
		try {
			statement= metaDBConn.createStatement();
			rs = statement.executeQuery("select distinct TGT_ENTITY ,TGT_DB_SCHEMA, PHY_NAME from  MODEL_SRC_TABLE_LIST WHERE PHY_NAME=\'"+tableName+"\'");
	        while (rs.next()) {
	        	SrcTableListEntity ttle = new SrcTableListEntity();
	        	ttle.setTgtEntity(rs.getString("TGT_ENTITY"));
	        	ttle.setTgtDbSchema(rs.getString("TGT_DB_SCHEMA"));
	        	ttle.setPhyName(rs.getString("PHY_NAME"));
	        	ttleList.add(ttle);
		    }
	        rs.close();
	        for(SrcTableListEntity ttle :ttleList)
	        {
	        	
	        	SrcTableListEntity ttleEntity = getTgtTableInfo(ttle, metaDBConn);
	        	if(null == ttleEntity )
	        		continue;
	        	ttleInfoList.add(ttleEntity);
	        }
	        return ttleInfoList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally{
			if(rs!=null)
			{
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(statement!=null)
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
	
	public static SrcTableListEntity getTgtTableInfo(SrcTableListEntity entity,Connection metaDBConn)
	{
		
		 ResultSet rs = null;
	     PreparedStatement statement = null;
	     List<SrcTableListEntity> ttleList = new ArrayList<SrcTableListEntity>();
	     List<TgtTableEtlRulesEntity> ttereList = new ArrayList<TgtTableEtlRulesEntity>();
	     
	     try {
		     statement=metaDBConn.prepareStatement("select * from MODEL_SRC_TABLE_LIST where phy_name=?");
		     
		     statement.setString(1,entity.getPhyName());
		     System.out.println("phyname="+entity.getPhyName());
			 rs = statement.executeQuery();
			 
			 while (rs.next()) {
				 SrcTableListEntity ttle = new SrcTableListEntity();
				 ttle.setTgtEntity(rs.getString("TGT_ENTITY"));
				 ttle.setTgtDbSchema(rs.getString("TGT_DB_SCHEMA"));
				 ttle.setPhyName(rs.getString("PHY_NAME"));
				 ttle.setLoadBatch(rs.getString("LOAD_BATCH"));
				 ttle.setSrcTableName(rs.getString("SRC_TABLE_NAME"));
				 ttle.setSrcSysName(rs.getString("SRC_SYS_NAME"));
				 ttle.setSrcTableAlias(rs.getString("SRC_TABLE_ALIAS"));
				 ttle.setSrcSchemaName(rs.getString("PHY_NAME"));
				 ttle.setInterfaceName(rs.getString("INTERFACE_NAME"));
				 ttle.setSrcTableDesc(rs.getString("SRC_TABLE_DESC"));
				 ttle.setGroupId(rs.getString("GROUP_ID"));
				 ttle.setJoinOrder(rs.getString("JOIN_ORDER"));
				 ttle.setJoinCondition(rs.getString("JOIN_CONDITION"));
				 ttle.setJoinType(rs.getString("JOIN_TYPE"));
				 ttle.setFilterCondition(rs.getString("FILTER_CONDITION"));
				 ttle.setIsIncremental(rs.getString("IS_INCREMENTAL"));
				 if(ttle.getIsIncremental().equals("1"))
					 ttle.setLoadMode("I");
				 else
					 ttle.setLoadMode("A");
				 ttle.setIncExtractDays(rs.getString("INC_DAYS"));
				 ttle.setIsSingleSource(rs.getString("IS_SINGLE_SOURCE"));
				 ttle.setComments(rs.getString("COMMENTS"));
				 ttleList.add(ttle);
			 }
			 rs.close();
			 Map<String, List> ttleMap = new HashMap<String, List>();
	 		    //按loadBatch分组
	 		    for (SrcTableListEntity ttle:ttleList){
	 		         String keyType = ttle.getLoadBatch();
	 		         if (ttleMap.containsKey(keyType)){
	 		        	 ttleMap.get(keyType).add(ttle);
	 		         }else{
	 		        	 List<SrcTableListEntity> newl = new ArrayList<SrcTableListEntity>();
	 		        	 newl.add(ttle);
	 		        	 ttleMap.put(keyType, newl);
	 		         }
	 		         
	 		    }
	 		 entity.setTgtTblLstMap(ttleMap);
			 statement=metaDBConn.prepareStatement("select * from MODEL_TGT_TABLE_ETL_RULES where phy_name=?");
		     
		     statement.setString(1,entity.getPhyName());
			 rs = statement.executeQuery();
			 while (rs.next()) {
					TgtTableEtlRulesEntity ttere = new TgtTableEtlRulesEntity();
					ttere.setTgtEntity(rs.getString("TGT_ENTITY"));
					ttere.setTgtDbSchema(rs.getString("TGT_DB_SCHEMA"));
					ttere.setPhyName(rs.getString("PHY_NAME"));
					ttere.setColumnId(rs.getString("COLUMN_ID"));
					ttere.setColumnNameEn(rs.getString("COLUMN_NAME_EN"));
					ttere.setColumnNameCh(rs.getString("COLUMN_NAME_CH"));
					ttere.setDataType(rs.getString("DATA_TYPE"));
					ttere.setIsPk(rs.getString("IS_PK"));
					ttere.setIsPartitionKey(rs.getString("IS_PARTITION_KEY"));
					ttere.setLoadBatch(rs.getString("LOAD_BATCH"));
					ttere.setGroupId(rs.getString("GROUP_ID"));
					ttere.setSrcTableName(rs.getString("SRC_TABLE_NAME"));
					ttere.setSrcColumnName(rs.getString("SRC_COLUMN_NAME"));
					ttere.setSrcColumnType(rs.getString("SRC_COLUMN_TYPE"));
					ttere.setComputeExpression(rs.getString("COMPUTE_EXPRESSION"));
					ttere.setExpressionComments(rs.getString("EXPRESSION_COMMENTS"));
					
					ttereList.add(ttere);
			  }
			 rs.close();
			 Map<String, List> ttereMap = new HashMap<String, List>();
	 		    //按loadBatch分组
	 		    for (TgtTableEtlRulesEntity ttere:ttereList){
	 		         String keyType = ttere.getLoadBatch();
	 		         if ( ttereMap.containsKey(keyType)){
	 		        	 ttereMap.get(keyType).add(ttere);
	 		         }else{
	 		        	 List<TgtTableEtlRulesEntity> newl = new ArrayList<TgtTableEtlRulesEntity>();
	 		        	 newl.add(ttere);
	 		        	 ttereMap.put(keyType, newl);
	 		         }
	 		         
	 		    }
	 		 entity.setTgtTblRuleMap(ttereMap);
			 statement.close();
			 return entity;
	     } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}finally{
				if(rs!=null)
				{
					try {
						rs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(statement!=null)
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
	public static void insertIntoSrcTableList(List<SrcTableListEntity> srcTLsList,Connection conn) throws SQLException {
		
		PreparedStatement statement= null;
		conn.setAutoCommit(false);
		statement = conn.prepareStatement("delete from MODEL_SRC_TABLE_LIST");
	
        statement.execute();
        statement.close();
		//System.out.println("src_table_list size="+srcTLsList.size());
		statement= conn.prepareStatement("insert into MODEL_SRC_TABLE_LIST (TGT_ENTITY,TGT_DB_SCHEMA,PHY_NAME,LOAD_BATCH,SRC_TABLE_NAME,SRC_SCHEMA_NAME,SRC_SYS_NAME,SRC_TABLE_DESC,SRC_TABLE_ALIAS,INTERFACE_NAME,GROUP_ID,JOIN_ORDER,JOIN_TYPE,JOIN_CONDITION,FILTER_CONDITION,IS_INCREMENTAL,INC_DAYS,COMMENTS,LOAD_MODE,IS_SINGLE_SOURCE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
		int num = 0;
		for(SrcTableListEntity srcTLs:srcTLsList) {
			statement.setString(1, srcTLs.getTgtEntity());
			statement.setString(2, srcTLs.getTgtDbSchema());
			statement.setString(3, srcTLs.getPhyName());
			statement.setString(4, srcTLs.getLoadBatch());
			statement.setString(5, srcTLs.getSrcTableName());
			statement.setString(6, srcTLs.getSrcSchemaName());
			statement.setString(7, srcTLs.getSrcSysName());
			statement.setString(8, srcTLs.getSrcTableDesc());
			statement.setString(9, srcTLs.getSrcTableAlias());
			statement.setString(10, srcTLs.getInterfaceName());
			statement.setString(11, srcTLs.getGroupId());
			statement.setString(12, srcTLs.getJoinOrder());
			statement.setString(13, srcTLs.getJoinType());
			statement.setString(14, srcTLs.getJoinCondition());
			statement.setString(15, srcTLs.getFilterCondition());
			statement.setString(16, srcTLs.getIsIncremental());
			//System.out.println("inincremental值："+srcTLs.getIsIncremental());
			statement.setString(17, srcTLs.getIncExtractDays());
			statement.setString(18, srcTLs.getComments());
			statement.setString(19, srcTLs.getLoadMode());
			//System.out.println("loadmode:"+srcTLs.getLoadMode());
			statement.setString(20, srcTLs.getIsSingleSource());
			//System.out.println("=="+srcTLs.getTgtEntity()+"=="+srcTLs.getSrcTableName()+srcTLs.getLoadBatch()+srcTLs.getGroupId()+srcTLs.getJoinOrder());
			statement.addBatch();
		}
        statement.executeBatch();
		conn.commit();   
        statement.close();
		
	}
	
	public static void insertIntoTgtTableEtlRules(List<TgtTableEtlRulesEntity> tRulesList,Connection conn) throws SQLException {
		PreparedStatement statement= null;
		conn.setAutoCommit(false);
		statement = conn.prepareStatement("delete from MODEL_TGT_TABLE_ETL_RULES ");

        statement.execute();
        statement.close();
        //System.out.println("TGT_TABLE_ETL_RULES size="+ tRulesList.size());
		statement = conn.prepareStatement("insert into MODEL_TGT_TABLE_ETL_RULES (TGT_ENTITY,TGT_DB_SCHEMA,PHY_NAME,COLUMN_ID,COLUMN_NAME_EN,COLUMN_NAME_CH,DATA_TYPE,IS_PK,IS_PARTITION_KEY,LOAD_BATCH,GROUP_ID,SRC_TABLE_NAME,SRC_TABLE_NAME_CN,SRC_COLUMN_NAME,SRC_COLUMN_NAME_CN,SRC_COLUMN_TYPE,COMPUTE_EXPRESSION,EXPRESSION_COMMENTS) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
		for(TgtTableEtlRulesEntity tRules:tRulesList) {
			statement.setString(1, tRules.getTgtEntity());
			statement.setString(2, tRules.getTgtDbSchema());
			statement.setString(3, tRules.getPhyName());
			statement.setString(4, tRules.getColumnId());
			statement.setString(5, tRules.getColumnNameEn());
			statement.setString(6, tRules.getColumnNameCh());
			statement.setString(7, tRules.getDataType());
			statement.setString(8, tRules.getIsPk());
			statement.setString(9, tRules.getIsPartitionKey());
			statement.setString(10, tRules.getLoadBatch());
			statement.setString(11, tRules.getGroupId());
			statement.setString(12, tRules.getSrcTableName());
			statement.setString(13, tRules.getSrcTableNameCn());
			statement.setString(14, tRules.getSrcColumnName());
			statement.setString(15, tRules.getSrcColumnNameCn());
			statement.setString(16, tRules.getSrcColumnType());
			statement.setString(17, tRules.getComputeExpression());
			statement.setString(18, tRules.getExpressionComments());
			statement.addBatch();
		}
        statement.executeBatch();
        conn.commit();   
        statement.close();		
	}
public static void insertIntoSrcTableListTbl(String phyName,List<SrcTableListEntity> srcTLsList,Connection conn) throws SQLException {
		
		PreparedStatement statement= null;
		conn.setAutoCommit(false);
		statement = conn.prepareStatement("delete from MODEL_SRC_TABLE_LIST WHERE PHY_NAME=\'"+phyName+"\'");
	
        statement.execute();
        statement.close();
		//System.out.println("src_table_list size="+srcTLsList.size());
		statement= conn.prepareStatement("insert into MODEL_SRC_TABLE_LIST (TGT_ENTITY,TGT_DB_SCHEMA,PHY_NAME,LOAD_BATCH,SRC_TABLE_NAME,SRC_SCHEMA_NAME,SRC_SYS_NAME,SRC_TABLE_DESC,SRC_TABLE_ALIAS,INTERFACE_NAME,GROUP_ID,JOIN_ORDER,JOIN_TYPE,JOIN_CONDITION,FILTER_CONDITION,IS_INCREMENTAL,INC_DAYS,COMMENTS,LOAD_MODE,IS_SINGLE_SOURCE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
		int num = 0;
		for(SrcTableListEntity srcTLs:srcTLsList) {
			statement.setString(1, srcTLs.getTgtEntity());
			statement.setString(2, srcTLs.getTgtDbSchema());
			statement.setString(3, srcTLs.getPhyName());
			statement.setString(4, srcTLs.getLoadBatch());
			statement.setString(5, srcTLs.getSrcTableName());
			statement.setString(6, srcTLs.getSrcSchemaName());
			statement.setString(7, srcTLs.getSrcSysName());
			statement.setString(8, srcTLs.getSrcTableDesc());
			statement.setString(9, srcTLs.getSrcTableAlias());
			statement.setString(10, srcTLs.getInterfaceName());
			statement.setString(11, srcTLs.getGroupId());
			statement.setString(12, srcTLs.getJoinOrder());
			statement.setString(13, srcTLs.getJoinType());
			statement.setString(14, srcTLs.getJoinCondition());
			statement.setString(15, srcTLs.getFilterCondition());
			statement.setString(16, srcTLs.getIsIncremental());
			statement.setString(17, srcTLs.getIncExtractDays());
			statement.setString(18, srcTLs.getComments());
			statement.setString(19, srcTLs.getLoadMode());
			statement.setString(20, srcTLs.getIsSingleSource());
			//System.out.println("=="+srcTLs.getTgtEntity()+"=="+srcTLs.getSrcTableName()+srcTLs.getLoadBatch()+srcTLs.getGroupId()+srcTLs.getJoinOrder());
			statement.addBatch();
		}
        statement.executeBatch();
		conn.commit();   
        statement.close();
		
	}
	
	public static void insertIntoTgtTableEtlRulesTbl(String phyName,List<TgtTableEtlRulesEntity> tRulesList,Connection conn) throws SQLException {
		PreparedStatement statement= null;
		conn.setAutoCommit(false);
		statement = conn.prepareStatement("delete from MODEL_TGT_TABLE_ETL_RULES WHERE PHY_NAME=\'"+phyName+"\'");

        statement.execute();
        statement.close();
        //System.out.println("TGT_TABLE_ETL_RULES size="+ tRulesList.size());
		statement = conn.prepareStatement("insert into MODEL_TGT_TABLE_ETL_RULES (TGT_ENTITY,TGT_DB_SCHEMA,PHY_NAME,COLUMN_ID,COLUMN_NAME_EN,COLUMN_NAME_CH,DATA_TYPE,IS_PK,IS_PARTITION_KEY,LOAD_BATCH,GROUP_ID,SRC_TABLE_NAME,SRC_TABLE_NAME_CN,SRC_COLUMN_NAME,SRC_COLUMN_NAME_CN,SRC_COLUMN_TYPE,COMPUTE_EXPRESSION,EXPRESSION_COMMENTS) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
		for(TgtTableEtlRulesEntity tRules:tRulesList) {
			statement.setString(1, tRules.getTgtEntity());
			statement.setString(2, tRules.getTgtDbSchema());
			statement.setString(3, tRules.getPhyName());
			statement.setString(4, tRules.getColumnId());
			statement.setString(5, tRules.getColumnNameEn());
			statement.setString(6, tRules.getColumnNameCh());
			statement.setString(7, tRules.getDataType());
			statement.setString(8, tRules.getIsPk());
			statement.setString(9, tRules.getIsPartitionKey());
			statement.setString(10, tRules.getLoadBatch());
			statement.setString(11, tRules.getGroupId());
			statement.setString(12, tRules.getSrcTableName());
			statement.setString(13, tRules.getSrcTableNameCn());
			statement.setString(14, tRules.getSrcColumnName());
			statement.setString(15, tRules.getSrcColumnNameCn());
			statement.setString(16, tRules.getSrcColumnType());
			statement.setString(17, tRules.getComputeExpression());
			statement.setString(18, tRules.getExpressionComments());
			statement.addBatch();
		}
        statement.executeBatch();
        conn.commit();   
        statement.close();		
	}

}
