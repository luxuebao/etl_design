package model.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.bean.DwColumnMappingEntity;
import model.bean.DwColumnsEntity;
import model.bean.DwTableEntity;
import model.bean.DwTableMappingEntity;
import model.utils.StringOperateUtils;

public class DwTableDao {
	public static List<DwTableEntity> initDwTable(Connection metaDBConn)
	{
		List<DwTableEntity> dwtblList = new ArrayList<DwTableEntity>();
		List<DwTableEntity> dwtblEntityList = new ArrayList<DwTableEntity>();
		Statement statement = null;
		ResultSet rs = null;
		try {
			statement= metaDBConn.createStatement();
			rs = statement.executeQuery("select * from  dw_table");
	        while (rs.next()) {
	        	DwTableEntity dte = new DwTableEntity();
	        	dte.setSysName(rs.getString("SYS_NAME"));
	        	dte.setTableName(rs.getString("TABLE_NAME"));
	        	dte.setPhyName(rs.getString("PHY_NAME"));
	        	dte.setLoadMode(rs.getString("LOAD_MODE"));
	        	dte.setSubjectName(rs.getString("SUBJECT_NAME"));
	        	dte.setIsSingleSource(rs.getString("IS_SINGLE_SOURCE"));
	            dte.setClearMode(rs.getString("CLEAR_MODE"));
	            dte.setKeepLoadDt(rs.getString("KEEP_LOAD_DT"));
	            dte.setDoAggregate(rs.getString("DO_AGGREGATE"));
	            dte.setIsFact(rs.getString("IS_FACT"));
	            dte.setComments(rs.getString("COMMENTS"));
	            dwtblList.add(dte);
		    }
	        for(DwTableEntity dte: dwtblList)
	        {
	        	DwTableEntity etlEntity = DwTableDao.initDwTableInfo(dte.getSysName(), dte.getTableName(),metaDBConn);
	            if(null == etlEntity )
	            	continue;
	            else
	            	dwtblEntityList.add(etlEntity);
		        }
			
	    	return dwtblEntityList;
	    	
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
	public static DwTableEntity initDwTableByTable(String sysName,String phyName,Connection metaDBConn)
	{
		List<DwTableEntity> dwtblList = new ArrayList<DwTableEntity>();
		List<DwTableEntity> dwtblEntityList = new ArrayList<DwTableEntity>();
		DwTableEntity dte = new DwTableEntity();
		Statement statement = null;
		ResultSet rs = null;
		try {
			statement= metaDBConn.createStatement();
			rs = statement.executeQuery("select * from  dw_table where PHY_NAME = \'"+phyName+"\' AND SYS_NAME = \'"+sysName+"\'");
	        while (rs.next()) {
	        	
	        	dte.setSysName(rs.getString("SYS_NAME"));
	        	dte.setTableName(rs.getString("TABLE_NAME"));
	        	dte.setPhyName(rs.getString("PHY_NAME"));
	        	dte.setLoadMode(rs.getString("LOAD_MODE"));
	        	dte.setSubjectName(rs.getString("SUBJECT_NAME"));
	        	dte.setIsSingleSource(rs.getString("IS_SINGLE_SOURCE"));
	            dte.setClearMode(rs.getString("CLEAR_MODE"));
	            dte.setKeepLoadDt(rs.getString("KEEP_LOAD_DT"));
	            dte.setDoAggregate(rs.getString("DO_AGGREGATE"));
	            dte.setIsFact(rs.getString("IS_FACT"));
	            dte.setComments(rs.getString("COMMENTS"));
	            break;
	        }
	        
	        DwTableEntity etlEntity= DwTableDao.initDwTableInfo(dte.getSysName(), dte.getTableName(),metaDBConn);
	        return etlEntity ;
	            	
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
	 public static DwTableEntity  initDwTableInfo( String sysName,String entityName,Connection metaDBConn) {
	        Statement statement = null;
	        ResultSet rs = null;
	        PreparedStatement statement_p = null;
	        DwTableEntity dte = new DwTableEntity();
	        try{
	        	//System.out.println("schemaName"+schemaName+"| entityName"+entityName);
	        	statement =  metaDBConn.createStatement();
	        	rs = statement.executeQuery("select * from dw_table where sys_name = '" + sysName + "' and table_name = '" + entityName + "'" );
	            
	        	while (rs.next()) {
		        	
	        		dte.setSysName(rs.getString("SYS_NAME"));
		        	dte.setTableName(rs.getString("TABLE_NAME"));
		        	dte.setPhyName(rs.getString("PHY_NAME"));
		        	dte.setLoadMode(rs.getString("LOAD_MODE"));
		        	dte.setSubjectName(rs.getString("SUBJECT_NAME"));
		        	dte.setIsSingleSource(rs.getString("IS_SINGLE_SOURCE"));
		            dte.setClearMode(rs.getString("CLEAR_MODE"));
		            dte.setKeepLoadDt(rs.getString("KEEP_LOAD_DT"));
		            dte.setDoAggregate(rs.getString("DO_AGGREGATE"));
		            dte.setIsFact(rs.getString("IS_FACT"));
		            dte.setComments(rs.getString("COMMENTS"));
		        	break;
	        	}
		        rs.close();
		       
		        		
		        //表级映射关系
		       // System.out.println("schemaName="+sysName+"| entityName="+entityName);
	        		List<DwTableMappingEntity> dtmeList = new ArrayList<DwTableMappingEntity>();
	        		String sqlStr="select dcm.* ,sta.schema_name,bti.src_tbl_ext_type_cd,bti.src_tbl_ext_inc_day_count,bti.src_tbl_cn_name "
                    + "from  dw_column_mapping dcm "
                    + "left join src_table_analysis sta on dcm.src_stbl_name=sta.stbl_name  "
                    + "left join bd_table_info bti on dcm.src_stbl_name=bti.tgt_tbl_en_name "
                    + "where dcm.table_name = '" + entityName + "' group by dcm.src_stbl_name ,load_group";
	        		
	        		rs = statement.executeQuery(sqlStr);
	        		//System.out.println("sqlStr="+sqlStr);
	 				String loadMode = dte.getLoadMode();
	 				
	 				int index=0;
					while (rs.next()) {
			        	DwTableMappingEntity dtme = new DwTableMappingEntity();
			         	
			        	dtme.setTableName(rs.getString("TABLE_NAME"));
			        	dtme.setInterfaceTable(rs.getString("SCHEMA_NAME")+"."+rs.getString("SRC_STBL_NAME"));
			        	dtme.setSrcSysName(rs.getString("SRC_SYS_NAME"));
			        	dtme.setSrcTableName(rs.getString("SRC_TABLE_NAME").toUpperCase());
			        	dtme.setSrcSchema(rs.getString("SCHEMA_NAME"));
			        	dtme.setSrcTableNameCn(rs.getString("SRC_TBL_CN_NAME"));
			        	int loadBatch =getLoadBatch(dtme,dtmeList);
			        	dtme.setLoadBatch(loadBatch);
			        	int groupIdx = 0;
			        	String tempStr = rs.getString("LOAD_GROUP");
			        	if(null == tempStr|| tempStr.trim().length() == 0)
			        			groupIdx =getGroupIdx(dtme,dtmeList);
			        	else
			        	{
			        			groupIdx = StringOperateUtils.stringToInt(tempStr);
			        	}
			        	dtme.setGroupIdx(groupIdx);
			        	dtme.setJoinOrder(0);
			        	dtme.setTableAlias("t0");
			        	dtme.setComments(rs.getString("COMMENTS"));
			        	dtme.setIsIncExtract(rs.getString("src_tbl_ext_type_cd"));
			        	BigDecimal bdcnt = rs.getBigDecimal("src_tbl_ext_inc_day_count");
			        	if(bdcnt == null)
			        		dtme.setIncExtractDays(0);
			        	else
			        		dtme.setIncExtractDays(bdcnt.intValue());
			        	
			        	//System.out.println("incFlag ="+dtme.getIsIncExtract()+"|incCount="+dtme.getIncExtractDays());
			        	dtmeList.add(dtme);
					}
		 		    dte.setDtmeList(dtmeList);
		 		    rs.close();
		 		   
		 	        
		 		    List<DwColumnsEntity> dcList = new ArrayList<DwColumnsEntity>();
		 		    statement_p=metaDBConn.prepareStatement("select dc.* from dw_columns dc "
		 		    		+ "where upper(dc.sys_name)=? and upper(dc.table_name) =?");
					statement_p.setString(1,dte.getSysName().toUpperCase());
					statement_p.setString(2,dte.getTableName().toUpperCase());
					rs = statement_p.executeQuery();
					while (rs.next()) {
						DwColumnsEntity dc = new DwColumnsEntity();
			        	dc.setSysName(rs.getString("SYS_NAME"));
			        	//dc.setSchemaName(rs.getString("SCHEMA_NAME"));
			        	dc.setTableName(rs.getString("TABLE_NAME"));
			        	dc.setColumnName(rs.getString("COLUMN_NAME"));
			        	dc.setColumnId(rs.getInt("COLUMN_ID"));
			        	dc.setDataType(rs.getString("DATA_TYPE"));
			        	dc.setPhyName(rs.getString("PHY_NAME"));
			        	dc.setAggPeriod(rs.getString("AGG_PERIOD"));
			        	if(rs.getString("IS_PK").toUpperCase().equals("YES"))
			        		dc.setIsPk(true);
			        	else
			        		dc.setIsPk(false);
			        	
			        	dc.setChainCompare(rs.getString("CHAIN_COMPARE"));
			        	
			        	dc.setIsPartitionKey(rs.getString("IS_PARTITION_KEY"));
			        	dc.setComments(rs.getString("COMMENTS"));
			        	
			        	dcList.add(dc);
			        }
		 		    dte.setDcList(dcList);
		 		    rs.close();
		 		   
		 		    
		 		    List<DwColumnMappingEntity> dcmList = new ArrayList<DwColumnMappingEntity>();
		 		   //字段级映射关系
		 		   	statement_p=metaDBConn.prepareStatement("select dcm.*,sca.data_type as sca_data_type,btci.tgt_tbl_cn_name, btci.tgt_col_cn_name ,sca.schema_name"
		 		   			+ " from dw_column_mapping dcm "
		 		   			+ " left join src_column_analysis sca on dcm.SRC_STBL_NAME=sca.STBL_NAME and dcm.src_column_name=sca.column_name "
		 		   			+ " left join  bd_table_column_info btci on dcm.src_stbl_name=btci.tgt_tbl_en_name and dcm.src_column_name=btci.tgt_col_en_name "
		 		   			+ " where upper(dcm.table_name) = ?");
					statement_p.setString(1,dte.getTableName().toUpperCase());
					rs = statement_p.executeQuery();
					while (rs.next()) {
						DwColumnMappingEntity dcm = new DwColumnMappingEntity();
			        	
			        	dcm.setTableName(rs.getString("TABLE_NAME"));
			        	dcm.setColumnName(rs.getString("COLUMN_NAME"));
			        	dcm.setSrcSysName(rs.getString("SRC_SYS_NAME"));
			        	dcm.setSrcStblName(rs.getString("SCHEMA_NAME")+"."+rs.getString("SRC_STBL_NAME"));
			        	dcm.setSrcTableName(rs.getString("SRC_TABLE_NAME").toUpperCase());
			        	dcm.setSrcColumnName(rs.getString("SRC_COLUMN_NAME"));
			        	dcm.setSrcColumnDataType(rs.getString("sca_data_type"));
			        	dcm.setSrcColumnNameCn(rs.getString("TGT_COL_CN_NAME"));
			        	dcm.setSrcTableNameCn(rs.getString("TGT_TBL_CN_NAME"));
			        	String tempStr = rs.getString("LOAD_GROUP");
			        	int groupIdx = 0;
			        	if(!(null == tempStr|| tempStr.trim().length() == 0))
			        	{
			        		groupIdx = StringOperateUtils.stringToInt(tempStr);
			        		dcm.setGroupIdx(groupIdx);
						}
			        	
			        	DwTableMappingEntity dtmel = getTableMapping(dtmeList,dcm.getSrcSysName(),dcm.getSrcStblName(),groupIdx); 
			        	if(dtmel == null)
			        	{
			        		System.out.println("获取对应的表映射失败，源系统名="+dcm.getSrcSysName()+" 表名 ="+dcm.getSrcTableName());
			        		continue;
			        	}
			        	dcm.setLoadBatch(dtmel.getLoadBatch());
			        	dcm.setGroupIdx(dtmel.getGroupIdx());
			        	dcm.setColumnExpr(rs.getString("COLUMN_EXPR"));
			        	dcm.setComments(rs.getString("COMMENTS"));
			        	dcm.setInteFlag(rs.getString("INTE_FLAG"));
			        	dcm.setCodeTable(rs.getString("CODE_TABLE"));
			        	String express =getExpress(dcm,dte);
			        	if(null == express)
			        	{
			        		System.out.println("获取代码转换表映射失败，批次号="+dcm.getLoadBatch()+"\t源系统名="+dcm.getSrcSysName()+"\t表名 ="+dcm.getSrcTableName()+"\t列名 ="+dcm.getSrcColumnName());
			        		continue;
			        	}
			        	dcm.setColumnExpr(express);
			        	dcmList.add(dcm);
			        }
					dte.setDcmList(dcmList);
		 		    rs.close();
		 		    statement_p.close();
		 		   
		 		    Map<String, List> dcmMap = new HashMap<String, List>();
		 		    //按loadBatch,groupIdx分组
		 		    for (DwColumnMappingEntity dcmel:dcmList){
		 		         String keyType = dcmel.getLoadBatch()+"_"+dcmel.getGroupIdx();
		 		         if (dcmMap.containsKey(keyType)){
		 		        	 dcmMap.get(keyType).add(dcmel);
		 		         }else{
		 		        	 List<DwColumnMappingEntity> newl = new ArrayList<DwColumnMappingEntity>();
		 		        	 newl.add(dcmel);
		 		        	 dcmMap.put(keyType, newl);
		 		         }
		 		         
		 		    }
		 		    //按批次/组别补齐字段
			        for(String dcmeKey : dcmMap.keySet()){
			        	List<DwColumnMappingEntity>  dcmelst = dcmMap.get(dcmeKey);
			        	for(DwColumnsEntity dc: dcList)
			        	{
			        		boolean flag = false;
			        		for(DwColumnMappingEntity dcmel:dcmelst)
			        		{
			        			if(dcmel.getTableName().equals(dc.getTableName()) &&dcmel.getColumnName().equals(dc.getColumnName()) )
			        			{
			        				flag= true;
			        				break;
			        			}
			        			flag = false;
			        		}
			        		if(!flag)
			        		{
			        			DwColumnMappingEntity dcme = new DwColumnMappingEntity();
			        			dcme.setSchemaName(dc.getSchemaName());
			        			dcme.setSrcSysName(dc.getSysName());
			        			dcme.setTableName(dc.getTableName());
			        			String defaultExpr=getDefaultExpr(dc.getDataType());
			        			dcme.setColumnExpr(defaultExpr);
			        			String p[] = dcmeKey.split("_");
			        			int loadBatch = Integer.parseInt(p[0]);
			        			int groupIdx = Integer.parseInt(p[1]);
			        			dcme.setLoadBatch(loadBatch);
					        	dcme.setGroupIdx(groupIdx);
			        			dcme.setColumnName(dc.getColumnName());
			        			/*dcme.setLoadBatch(Integer.parseInt(dcmeKey));*/
			        			dcmelst.add(dcme);
			        		}
			        	}
			       dte.setDcmMap(dcmMap);
		        }
	        }catch(Exception e) {
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
				if(statement_p!=null)
				{
					try {
						statement_p.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	    
			}
	        
	        return  dte;
	    }
	 private static String getExpress(DwColumnMappingEntity dcm,DwTableEntity entity)
	 {
		 String asmFlag = dcm.getInteFlag();
		 List<DwTableMappingEntity> dtmeList = entity.getDtmeList();
		 int k = dtmeList.size();
		 boolean flag = false;
		 String tableAliasNew ="t";
		 int loadBatchNo = dcm.getLoadBatch();
		 int groupIdx = dcm.getGroupIdx();
		 if(null== asmFlag||asmFlag.trim().length()==0)
			 return dcm.getColumnExpr();
		 else if(asmFlag.equals("P"))
		 {
			 DwTableMappingEntity dtme = new DwTableMappingEntity();
			 DwTableMappingEntity dtmeNew = new DwTableMappingEntity();
			 for(int i=0;i<k;i++)
			 {
				 dtme = dtmeList.get(i);
				 if(dtme.getLoadBatch() == loadBatchNo &&dtme.getSrcSysName().equals(dcm.getSrcSysName()) &&dtme.getInterfaceTable().equals(dcm.getSrcStblName())&&dtme.getSrcTableName().equals(dcm.getSrcTableName()))
				 {
					 flag = true;
					 break;
				 }
			 }
			 if(!flag)
				 return null;
			 int joinOrder =  getGroupJoinOrder(loadBatchNo,groupIdx,dtmeList);
			 tableAliasNew = tableAliasNew +(joinOrder);
			 String tableAliasLeft= dtme.getTableAlias();
			
			 String srcTbColumn1 = tableAliasLeft+"."+ dcm.getSrcColumnName();
			 String exchTbColumn1 = tableAliasNew +".Ptf_Id";
			 String express="CASE WHEN COALESCE("+srcTbColumn1+",\'\')=\'\' THEN \'\' ELSE COALESCE(" + exchTbColumn1+ ",CONCAT(\'@\'," + srcTbColumn1 + ")) END" ;
			 
			 dtmeNew.setSrcSysName("DW");
			 dtmeNew.setSrcSchema("PDATA");
			 dtmeNew.setSrcTableName("T97_PTF_ID_CHG");
			 dtmeNew.setInterfaceTable("${PDATADB}."+"T97_PTF_ID_CHG");
			 dtmeNew.setLoadBatch(loadBatchNo);
			 dtmeNew.setTableAlias(tableAliasNew);
			 dtmeNew.setGroupIdx(groupIdx);
			 String exchTbColumn2 = tableAliasNew +".Ptf_Oid";
			 String exchTbColumn3 = tableAliasNew +".Src_Sys_Cd";
			 dtmeNew.setJoinCondition("ON "+srcTbColumn1 +"="+exchTbColumn2 +" AND "+exchTbColumn3 + "=\'"+dcm.getSrcSysName()+"\'");
			 dtmeNew.setJoinType("LEFT OUTER");
			 dtmeNew.setJoinOrder(joinOrder);
			 entity.getDtmeList().add(dtmeNew);
			 return express;
		 
		 }
		 else if(asmFlag.charAt(0) =='C')//C0,C1,C2,C3,C9
		 {
			 DwTableMappingEntity dtme = new DwTableMappingEntity();
			 DwTableMappingEntity dtmeNew = new DwTableMappingEntity();
			 for(int i=0;i<k;i++)
			 {
				 dtme = dtmeList.get(i);
				 if(dtme.getLoadBatch() == loadBatchNo &&dtme.getSrcSysName().equals(dcm.getSrcSysName()) &&dtme.getInterfaceTable().equals(dcm.getSrcStblName())&&dtme.getSrcTableName().equals(dcm.getSrcTableName()))
				 {
					 flag = true;
					 break;
				 }
			 }
			 if(!flag)
				 return null;
			 String tableAliasLeft= dtme.getTableAlias();
			 int joinOrder =  getGroupJoinOrder(loadBatchNo,groupIdx,dtmeList);
			 tableAliasNew = tableAliasNew +(joinOrder);
			 String srcTbColumn1 = tableAliasLeft+"."+dcm.getSrcColumnName();
			 String exchTbColumn1 = tableAliasNew +".Cbd_Cd_Val";
			 String express="CASE WHEN COALESCE("+srcTbColumn1+",\'\')=\'\' THEN \'\' ELSE COALESCE(" + exchTbColumn1+ ",CONCAT(\'@\'," + srcTbColumn1 + ")) END" ;
			 int CodeNumber=StringOperateUtils.stringToInt( asmFlag.substring(1));
			 dtmeNew.setSrcSysName("DW");
			 dtmeNew.setSrcSchema("PDATA");
			 dtmeNew.setSrcTableName("B97_CD_CHG");
			 dtmeNew.setInterfaceTable("basedb."+"B97_CD_CHG");
			 dtmeNew.setLoadBatch(loadBatchNo);
			 dtmeNew.setTableAlias(tableAliasNew);
			 dtmeNew.setGroupIdx(groupIdx);
			 String exchTbColumn2 = tableAliasNew +".Src_Val";
			 String exchTbColumn3 = tableAliasNew +".Src_Sys_Cd";
			 String exchTbColumn4 = tableAliasNew +".Seq_Nbr";
			 String exchTbColumn5 = tableAliasNew +".Cdb_Cd_Tbl_Cn";
			/* String codeTable="";
			 for(DwTableEntity dwtbl: dwtblList)
			 {
				 if(dwtbl.getTableName().equals(dcm.getCodeTable()))
				 {
					 codeTable = dwtbl.getPhyName();
					 break;
				 }
			 }*/
			 String codeTable=dcm.getCodeTable();
			 dtmeNew.setJoinCondition("ON "+srcTbColumn1 +"="+exchTbColumn2 +" AND "+exchTbColumn3 + "=\'"+dcm.getSrcSysName()+"\' AND " + exchTbColumn4 +"="+CodeNumber  +" AND " + exchTbColumn5+"=\'"+codeTable+"\'");
			// System.out.println("asmFlag="+asmFlag+"CodeNumber="+CodeNumber+"JoinCOndition="+dtmeNew.getJoinCondition());
			 dtmeNew.setJoinType("LEFT OUTER");
			 dtmeNew.setJoinOrder(joinOrder);
			
			 dtmeList.add(dtmeNew);
			 return express;
		 }
		 else if(asmFlag.equals("I"))
		 {
			 DwTableMappingEntity dtme = new DwTableMappingEntity();
			 DwTableMappingEntity dtmeNew = new DwTableMappingEntity();
			 for(int i=0;i<k;i++)
			 {
				 dtme = dtmeList.get(i);
				 if(dtme.getLoadBatch() == loadBatchNo &&dtme.getSrcSysName().equals(dcm.getSrcSysName()) &&dtme.getInterfaceTable().equals(dcm.getSrcStblName())&&dtme.getSrcTableName().equals(dcm.getSrcTableName()))
				 {
					 flag = true;
					 break;
				 }
			 }
			 if(!flag)
				 return null;
			 int joinOrder =  getGroupJoinOrder(loadBatchNo,groupIdx,dtmeList);
			 tableAliasNew = tableAliasNew +(joinOrder);
			 String tableAliasLeft= dtme.getTableAlias();
			 String srcTbColumn1 = tableAliasLeft+"."+ dcm.getSrcColumnName();
			 String exchTbColumn1 = tableAliasNew +".Inv_Id";
			 String express="CASE WHEN COALESCE("+srcTbColumn1+",\'\')=\'\' THEN \'\' ELSE COALESCE(" + exchTbColumn1+ ",CONCAT(\'@\'," + srcTbColumn1 + ")) END" ;
			
			 dtmeNew.setSrcSysName("DW");
			 dtmeNew.setSrcSchema("PDATA");
			 dtmeNew.setSrcTableName("T97_INV_ID_CHG");
			 dtmeNew.setInterfaceTable("${PDATADB}."+"T97_INV_ID_CHG");

			 dtmeNew.setLoadBatch(loadBatchNo);
			 dtmeNew.setTableAlias(tableAliasNew);
			 dtmeNew.setGroupIdx(groupIdx);
			 String exchTbColumn2 = tableAliasNew +".Inv_Oid";
			 String exchTbColumn3 = tableAliasNew +".Src_Sys_Cd";
			 
			 dtmeNew.setJoinCondition("ON "+srcTbColumn1 +"="+exchTbColumn2 +" AND "+exchTbColumn3 + "=\'"+dcm.getSrcSysName()+"\'");
			 dtmeNew.setJoinType("LEFT OUTER");
			 dtmeNew.setJoinOrder(joinOrder);
			 dtmeList.add(dtmeNew);
			 return express;
		 }
		 else if(asmFlag.equals("U"))
		 {
			 DwTableMappingEntity dtme = new DwTableMappingEntity();
			 DwTableMappingEntity dtmeNew = new DwTableMappingEntity();
			 for(int i=0;i<k;i++)
			 {
				 dtme = dtmeList.get(i);
				 if(dtme.getLoadBatch() == loadBatchNo &&dtme.getSrcSysName().equals(dcm.getSrcSysName()) &&dtme.getInterfaceTable().equals(dcm.getSrcStblName())&&dtme.getSrcTableName().equals(dcm.getSrcTableName()))
				 {
					 flag = true;
					 break;
				 }
			 }
			 if(!flag)
				 return null;
			 int joinOrder =  getGroupJoinOrder(loadBatchNo,groupIdx,dtmeList);
			 tableAliasNew = tableAliasNew +(joinOrder);
			 String tableAliasLeft= dtme.getTableAlias();
			 String srcTbColumn1 = tableAliasLeft+"."+ dcm.getSrcColumnName();
			 String exchTbColumn1 = tableAliasNew +".Usr_Id";
			 String express=express="CASE WHEN COALESCE("+srcTbColumn1+",\'\')=\'\' THEN \'\' ELSE COALESCE(" + exchTbColumn1+ ",CONCAT(\'@\'," + srcTbColumn1 + ")) END" ;
			 
			 dtmeNew.setSrcSysName("DW");
			 dtmeNew.setSrcSchema("PDATA");
			 dtmeNew.setSrcTableName("T97_USR_ID_CHG");
			 dtmeNew.setInterfaceTable("${PDATADB}."+"T97_USR_ID_CHG");
			 dtmeNew.setLoadBatch(loadBatchNo);
			 dtmeNew.setTableAlias(tableAliasNew);
			 dtmeNew.setGroupIdx(groupIdx);
			 String exchTbColumn2 = tableAliasNew +".Usr_Oid";
			 String exchTbColumn3 = tableAliasNew +".Src_Sys_Cd";
			 dtmeNew.setJoinCondition("ON "+srcTbColumn1 +"="+exchTbColumn2 +" AND "+exchTbColumn3 + "=\'"+dcm.getSrcSysName()+"\'");
			 dtmeNew.setJoinType("LEFT OUTER");
			 dtmeNew.setJoinOrder(joinOrder);
			 dtmeList.add(dtmeNew);
			 return express;
		 }
		 else
			 return dcm.getColumnExpr();
			 
		 
	 }
	 private static int getLoadBatch(DwTableMappingEntity dtme,List<DwTableMappingEntity>dtmeList)
	 {
		 int idx = 1;
		 if(null == dtmeList ||dtmeList.size() ==0)
			 return idx;
		 else
		 {
			 for(DwTableMappingEntity dtmel: dtmeList )
			 {
				 if(dtmel.getSrcSysName().equals(dtme.getSrcSysName()))
				 {
					 idx=dtmel.getLoadBatch();
					 break;
				 }
				 else if(dtmel.getLoadBatch()>=idx)
					 idx= dtmel.getLoadBatch()+1;
			
			 }	
		 }
		 return idx;
	 }
	 private static int getGroupIdx(DwTableMappingEntity dtme,List<DwTableMappingEntity>dtmeList)
	 {
		 int idx = 1;
		 if(null == dtmeList ||dtmeList.size() ==0)
			 return idx;
		 else
		 {
			 for(DwTableMappingEntity dtmel: dtmeList )
			 {
				 if(dtmel.getSrcSysName().equals(dtme.getSrcSysName()))
					 {//判断是否属于某个批次
					 if(dtmel.getGroupIdx()>=idx)
						 idx= dtmel.getGroupIdx()+1;
					 }
			 }	
		 }
		 return idx;
	 }  
	 private static int getGroupJoinOrder(int loadBatchNo,int groupIdx,List<DwTableMappingEntity>dtmeList)
	 {
		 int idx = 1;
		 if(null == dtmeList ||dtmeList.size() ==0)
			 return idx;
		 else
		 {
			 for(DwTableMappingEntity dtmel: dtmeList )
			 {
				 if((dtmel.getLoadBatch()== loadBatchNo )&&(dtmel.getGroupIdx()== groupIdx))
					 {//判断是否属于某个批次,获取该组中最大的连接次序号+1
					 if(dtmel.getJoinOrder()>=idx)
						 idx= dtmel.getJoinOrder()+1;
					 }
			 }	
		 }
		 return idx;
	 }  
	 private static String getDefaultExpr(String dataType)
	 {
		 dataType = dataType.toUpperCase();
		 String defaultString = "\'\'";
		 dataType = StringOperateUtils.getStringByLocation(dataType, "(").toUpperCase();
		 if(dataType.equals("STRING") || dataType.equals("VARCHAR")||dataType.equals("CHAR"))
		 			 return defaultString;
		 else if(dataType.equals("DATE"))
			  return "'${BDW_MIN_DT}'";
		 else if(dataType.equals("TIMESTAMP"))
		 {
			  String fieldStr ="concat(unix_timestamp(\'${BDW_MIN_DT}\',\'yyyyMMdd\'),\'000\')";
			 /* Timestamp ts = new Timestamp(System.currentTimeMillis());
              ts = Timestamp.valueOf(fieldStr); 
              long ltime =  ts.getTime();*/
              return  fieldStr;
		 }
		 else if(dataType.equals("DECIMAL") ||dataType.equals("INTEGER")||dataType.equals("BIGINT")  )
		 {
			 return "0";
		 }
		 else 
			 return defaultString;
	 }
	 private static DwTableMappingEntity getTableMapping( List<DwTableMappingEntity> dtmeList,String sysName,String intfaceName,int groupIdx)
	 {
		 if(null == sysName || null == intfaceName)
			 return null;
		 sysName = sysName.toUpperCase();
		 //System.out.println("系统名是："+sysName);
		 
		 intfaceName = intfaceName.toUpperCase();
		 for(DwTableMappingEntity entity: dtmeList)
		 {
			 if(groupIdx >0)
			 {
				 //System.out.println("接口名："+entity.getInterfaceTable()+"_____"+intfaceName);
				 if(entity.getSrcSysName().toUpperCase().equals(sysName) && entity.getInterfaceTable().toUpperCase().equals(intfaceName) && entity.getGroupIdx()==groupIdx)
				 {

					 return entity;
				 }
			 }
			 else
			 {
				 if(entity.getSrcSysName().toUpperCase().equals(sysName) && entity.getInterfaceTable().toUpperCase().equals(intfaceName) )
				 {
					 return entity;
				 }
			 }
		 }
		 return null;
	 }
}
