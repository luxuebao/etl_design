package model.handle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.bean.TgtTableEtlRulesEntity;
import model.bean.SrcTableListEntity;
import model.constant.Constant;
import model.utils.StringOperateUtils;


public class SqlScriptsHandle {
    protected String preProcessScript = null;
    protected String postProcessScript = null;
    private static String workingTable;
    private static int numGroupBlock;


    public static Map<String, String> genScripts(SrcTableListEntity entity) {
        Map<String, String> mapScripts = new HashMap<String, String>();

        for (String loadBatchNo : entity.getTgtTblLstMap().keySet())//按批次处理
        {
            String sysName = "";
            String loadMode = "";
            String phyName = "";
            List<SrcTableListEntity> ttleList = entity.getTgtTblLstMap().get(loadBatchNo);
            List<TgtTableEtlRulesEntity> ttereList = entity.getTgtTblRuleMap().get(loadBatchNo);
            for (SrcTableListEntity ttle : ttleList) {
                if (ttle.getJoinOrder().equals("0")) {
                    sysName = ttle.getSrcSysName();
                    loadMode = ttle.getIsIncremental();
                    if (loadMode.equals("1"))
                        loadMode = "I";
                    else
                        loadMode = "A";
                    phyName = ttle.getPhyName();
                    //System.out.println("phyName="+phyName+"\t sysname="+sysName+"\t loadMode="+loadMode+"\t isINC="+ttle.getIsIncremental());
                    break;
                }
            }

            try {
                mapScripts.put(phyName + "_" + sysName + "_" + loadMode + ".SQL", genBatchScript(loadBatchNo, loadMode, ttleList, ttereList));
                System.out.println("生成脚本：" + phyName + "_" + sysName + "_" + loadMode + ".SQL" + "     " + "loadBathNo:" + loadBatchNo);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


        return mapScripts;

    }

    protected static String genBatchScript(String loadBatchNo, String loadMode, List<SrcTableListEntity> ttleList, List<TgtTableEtlRulesEntity> ttereList) throws Exception {
        String scripts = genBatchPreprocess(loadBatchNo, ttleList) +
                genBatchBody(loadBatchNo, loadMode, ttleList, ttereList) + genBatchPostprocess(loadBatchNo);
        return scripts;
    }

    protected String genJobPreprocess() throws Exception {
        return "";
    }

    protected String genJobPostprocess() throws Exception {
        return "";
    }

    protected static String genBatchPreprocess(String loadBatch) throws Exception {
        return "\n-- Script for batch " + loadBatch + " begin";
    }

    protected static String genBatchPostprocess(String loadBatchNo) throws Exception {
        return "\n-- Script for batch " + loadBatchNo + " end ";
    }

    protected static String genGroupPreprocess(String loadGroupIdx) throws Exception {
        return "\n-- Script for group " + loadGroupIdx + " begin";
    }

    protected static String genGroupPostprocess(String loadGroupIdx) throws Exception {
        return "\n-- Script for group " + loadGroupIdx + " end";
    }


    protected static String genGroupScript(String loadBatchNo, String loadMode, String loadGroupIdx, List<SrcTableListEntity> ttleList, List<TgtTableEtlRulesEntity> ttereList) throws Exception {
        return genGroupPreprocess(loadGroupIdx) + genGroupBody(loadBatchNo, loadMode, loadGroupIdx, ttleList, ttereList) + genGroupPostprocess(loadGroupIdx);
    }

    protected static String genBatchBody(String loadBatchNo, String loadMode, List<SrcTableListEntity> ttleList, List<TgtTableEtlRulesEntity> ttereList) throws Exception {
        StringBuilder buffer = new StringBuilder();
        StringBuilder buffer4Groups = new StringBuilder();
        Map<String, List> ttereMap = new HashMap<String, List>();
        Map<String, List> ttleMap = new HashMap<String, List>();
        //表映射关系按groupId分组
        for (SrcTableListEntity ttle : ttleList) {
            String keyType = ttle.getGroupId();
            if (ttleMap.containsKey(keyType)) {
                ttleMap.get(keyType).add(ttle);
            } else {
                List<SrcTableListEntity> newl = new ArrayList<SrcTableListEntity>();
                newl.add(ttle);
                ttleMap.put(keyType, newl);
            }

        }

        //列映射关系按groupId分组
        //System.out.println("SrcTableList="+ttleList.size()+ttleList.get(0).getPhyName());
        for (TgtTableEtlRulesEntity ttere : ttereList) {
            String keyType = ttere.getGroupId();
            if (ttereMap.containsKey(keyType)) {
                ttereMap.get(keyType).add(ttere);
            } else {
                List<TgtTableEtlRulesEntity> newl = new ArrayList<TgtTableEtlRulesEntity>();
                newl.add(ttere);
                ttereMap.put(keyType, newl);
            }

        }


        numGroupBlock = 0;
        for (int k = 0; k < ttleMap.size(); k++) {
            String groupIdx = (k + 1) + "";
            List<SrcTableListEntity> ttleGroupList = ttleMap.get(groupIdx);
            List<TgtTableEtlRulesEntity> ttereGroupList = ttereMap.get(groupIdx);


            Collections.sort(ttleGroupList, new ETLSourceTableComparator());

            //buffer.append(genFromClause(groupIdx,ttleGroupList));

            Collections.sort(ttereGroupList, new ETLSourceColumnComparator());
            buffer4Groups.append(genGroupScript(loadBatchNo, loadMode, groupIdx, ttleGroupList, ttereGroupList));

            if (numGroupBlock > 0) {
                buffer.append(buffer4Groups);
                buffer.append("\n;");
                buffer4Groups.setLength(0);
            } else
                buffer = buffer4Groups;

        }

        return buffer.toString();
    }


    private static String genWorkingTable(SrcTableListEntity entity) {
        StringBuilder buffer = new StringBuilder();

        if (null != entity.getIsIncremental() && entity.getIsIncremental().equals("更新")) {
            workingTable = entity.getTgtDbSchema() + "." + entity.getPhyName() + "_" + Constant.workDateVarName;
            buffer.append("\nDROP TABLE IF EXISTS ").append(workingTable).append(";");
            buffer.append("\nCREATE TABLE ").append(workingTable).append(" LIKE ").append(entity.getPhyName()).append(";");
        } else
            workingTable = entity.getTgtDbSchema() + "." + entity.getPhyName();

        return buffer.toString();

    }

    protected static String genBatchPreprocess(String loadBatchNo, List<SrcTableListEntity> ttleList) throws Exception {

        if (null == ttleList || ttleList.size() == 0)
            return "";

        StringBuilder buffer = new StringBuilder();

        buffer.append(genBatchPreprocess(loadBatchNo));

        buffer.append("\nUSE ${hivevar:targetDB};");

        buffer.append(genWorkingTable(ttleList.get(0)));

        return buffer.toString();

    }

    protected String genBatchPostprocess(List<SrcTableListEntity> ttleList) throws Exception {

        if (null == ttleList || ttleList.size() == 0)
            return "";

        if (!ttleList.get(0).getIsIncremental().equals("更新")) {
            return "";
        }

        return genDataMerge();
    }

    private String genDataMerge() {
        StringBuilder buffer = new StringBuilder();

	       /* buffer.append("\nINSERT OVERWRITE TABLE ").append(etlTask.getEtlEntity().getPhyTableName());
	        buffer.append(String.format("\nPARTITION (%s = '%s',%s = '%s'", JobSQLGeneratorConfig.loadDateColName, JobSQLGeneratorConfig.workDateVarName,JobSQLGeneratorConfig.srcSysCode,sysName));

	        for (ETLEntityAttribute partKey : getPartitionKeys())
	            buffer.append(", ").append(partKey.getPhyName());
	        buffer.append(")");

	        boolean isFirstColumn = true;
	        buffer.append("\nSELECT ");
	        for (ETLEntityAttribute column : getNonPartitionKeys()) {
	            if (!isFirstColumn)
	                buffer.append("\n, ");
	            else
	                buffer.append("\n");

	            buffer.append("coalesce(t1.").append(column.getPhyName()).append(", t2.").append(column.getPhyName()).append(")");
	            isFirstColumn = false;
	        }

	        for (ETLEntityAttribute column : getPartitionKeys()) {
	            buffer.append("\n, ").append("coalesce(t1.").append(column.getPhyName()).append(", t2.").append(column.getPhyName()).append(")");
	        }

	        buffer.append("\nFROM ").append(workingTable).append(" t1 FULL OUTER JOIN ");

	        buffer.append("(SELECT * FROM ").append(etlTask.getEtlEntity().getPhyTableName())
	                .append(String.format(" WHERE %s = '%s')", JobSQLGeneratorConfig.loadDateColName, JobSQLGeneratorConfig.lastWorkDateVarName))
	                .append(" t2");

	        buffer.append("\nON");

	        isFirstColumn = true;
	        for (ETLEntityAttribute column : getPrimaryKeys()) {
	            if (!isFirstColumn)
	                buffer.append(" AND");
	            buffer.append(" t1.").append(column.getPhyName()).append(" =").append(" t2.").append(column.getPhyName());
	            isFirstColumn = false;
	        }

	        buffer.append(";");

	        buffer.append("\nDROP TABLE IF EXISTS ").append(workingTable).append(";");*/

        return buffer.toString();


    }


    protected static String genGroupBody(String loadBatchNo, String loadMode, String LoadGroupIdx, List<SrcTableListEntity> ttleList, List<TgtTableEtlRulesEntity> ttereList) throws Exception {
        StringBuilder buffer = new StringBuilder();
        StringBuilder selectListBuf = new StringBuilder();
        StringBuilder columnListBuf = new StringBuilder();
        String sysName = "";
        String tableName = "";
        String phyName = "";

        //System.out.println("workingTable="+workingTable);
        if (StringOperateUtils.stringToInt(LoadGroupIdx) > 1)
            buffer.append("\nINSERT INTO TABLE ").append(workingTable);
        else
            buffer.append("\nINSERT OVERWRITE TABLE ").append(workingTable);
        //获取源系统名和表名以及增全量日期
        for (SrcTableListEntity ttle : ttleList) {
            if (ttle.getJoinOrder().equals("0")) {
                sysName = ttle.getSrcSysName();
                String interfaceName = ttle.getInterfaceName();

                String[] str = interfaceName.split("\\.");
                System.out.println(interfaceName);
                tableName = str[1];

                phyName = ttle.getPhyName();
                break;
            }
        }
        String dynamicPartStr = "";
        if (!phyName.substring(0, 3).equals("B99")) {

//		        if(loadMode.equals("I"))
//		        {
//		        	buffer.append(String.format("\nPARTITION (%s = '%s',%s ",Constant.srcSysCode,sysName, Constant.loadDateColName));
//		        	dynamicPartStr = "\n,t0.ppi AS  BDW_Data_Dt \n";
//		        }
//		        else
//		        	buffer.append(String.format("\nPARTITION (%s = '%s',%s = '%s'",Constant.srcSysCode,sysName, Constant.loadDateColName, Constant.workDateVarName));
            if (loadMode.equals("I")) {
                buffer.append("\nPARTITION (data_dt = ${hivevar:data_dt}");
                dynamicPartStr = "\n";
            } else
                buffer.append("\nPARTITION (data_dt = ${hivevar:data_dt}");

            buffer.append(")");
        }


        buffer.append("\nSELECT ");
        boolean isFirstColumn = true;
        for (TgtTableEtlRulesEntity ttere : ttereList) {
            if (!isFirstColumn)
                buffer.append("\n,");
            else
                buffer.append("\n");
            String columnExpr = ttere.getComputeExpression();
            if (null == columnExpr || columnExpr.trim().length() == 0) {
                String c = ttere.getSrcColumnName();
                buffer.append(c);
                //System.out.println("列英文名："+c);

            } else
                buffer.append(columnExpr);
            buffer.append(" -- ").append(ttere.getColumnNameCh());
            isFirstColumn = false;
        }
        buffer.append(addColumnStr(tableName));
        buffer.append(dynamicPartStr);
        buffer.append(genFromClause(loadMode, LoadGroupIdx, ttleList));
        buffer.append(genWhereClause(loadMode, ttleList));

        numGroupBlock += 1;

        return buffer.toString();
    }

    private static String addColumnStr(String tableName) {
        //StringBuilder buffer = new StringBuilder();
        String buffer = String.format("\n,'%s' --数据来源表 \n,%s -- 数据来源代码 ", tableName, "'stat'");
        return buffer;
    }

    private static String genWhereClause(String loadMode, List<SrcTableListEntity> ttleGroupList) {
        //StringBuilder buffer = new StringBuilder();
        String filterString = "";
        String incDays = "";
        String loadModeEntity = "";
        String interfaceName = "";
        for (SrcTableListEntity entity : ttleGroupList) {
            if (StringOperateUtils.stringToInt(entity.getJoinOrder()) == 0) {
                filterString = entity.getFilterCondition().trim();
                loadModeEntity = entity.getLoadMode();
                incDays = entity.getIncExtractDays();
                interfaceName = entity.getInterfaceName();
                //System.out.println("tableName="+entity.getInterfaceName()+"filterString =["+filterString+"]\tloadModyEntity=" + loadModeEntity + "\tincDays="+incDays);
                break;
            }
        }
//	        if(loadMode.equals("I")) //增量加载
//	        {
//	        	int incDaysNum = StringOperateUtils.stringToInt(incDays);
//	        	interfaceName = interfaceName.substring(interfaceName.lastIndexOf(".")+1);
//
//	        	String DateStr =String.format("from_unixtime(to_unix_timestamp(date_sub(from_unixtime(to_unix_timestamp(\'${BDW_DATA_DT}\',\'yyyyMMdd\'),\'yyyy-MM-dd\'),${%s.TBL_EXT_SPAN_DAYS}),\'yyyy-MM-dd\'),\'yyyyMMdd\')",interfaceName);
//	        	String DateCondition = String.format(" t0.ppi>=%s AND t0.ppi<=%s",DateStr,"\'${BDW_DATA_DT}\'");
//	        	if(filterString.length()>0)
//	        		filterString =filterString +"\n AND"+ DateCondition;
//	        	else
//	        		filterString = "\n WHERE "+DateCondition;
//	        }
        if (loadMode.equals("I")) //增量加载
        {
            int incDaysNum = StringOperateUtils.stringToInt(incDays);
            interfaceName = interfaceName.substring(interfaceName.lastIndexOf(".") + 1);

            //String DateStr =String.format("from_unixtime(to_unix_timestamp(date_sub(from_unixtime(to_unix_timestamp(\'${BDW_DATA_DT}\',\'yyyyMMdd\'),\'yyyy-MM-dd\'),${%s.TBL_EXT_SPAN_DAYS}),\'yyyy-MM-dd\'),\'yyyyMMdd\')",interfaceName);
            String DateCondition = String.format(" t0.access_partition_year>=%s AND t0.access_partition_month>=%s AND tO.access_partition_day>=%s", "\'${hivevar:DW_DATA_DT_YEAR}\'", "\'${hivevar:DW_DATA_DT_MONTH}\'", "\'${hivevar:DW_DATA_DT_DAY}\'");
            if (filterString.length() > 0)
                filterString = filterString + "\n AND" + DateCondition;
            else
                filterString = "\n WHERE " + DateCondition;
        } else //全量
        {
//	        	if(filterString.length()>0)
//	        		filterString =filterString + "\n AND t0.ppi='${BDW_DATA_DT}'";
//	        	else
//	        		filterString = "\n WHERE t0.ppi='${BDW_DATA_DT}'";
            if (filterString.length() > 0)
                filterString = filterString + "\n ";
            else
                filterString = "\n ";
        }

        return filterString;
    }

    private static String genFromClause(String loadMode, String groupIdx, List<SrcTableListEntity> ttleGroupList) {
        StringBuilder buffer = new StringBuilder();

        buffer.append("\nFROM ");

        boolean isFirstTable = true;
        for (SrcTableListEntity sourceTable : ttleGroupList) {
            buffer.append("\n");

            if (isFirstTable) {
	            	/*if(sourceTable.getInterfaceName().contains("${ODSDB}."))
	            	{
	            		String tempStr ="${ODSDB}."+ sourceTable.getInterfaceName().substring(7);
	            		
	            		buffer.append(tempStr).append(" ").append(sourceTable.getSrcTableAlias());
	            	}
	            	else*/
                buffer.append(sourceTable.getInterfaceName()).append(" ").append(sourceTable.getSrcTableAlias());
                isFirstTable = false;
                continue;
            }

	            /*String joinType = sourceTable.getJoinType().trim().toLowerCase();
	            if (joinType.length() == 0 || joinType.contains("inner"))
	                buffer.append(" INNER JOIN");
	            else if (joinType.contains("left"))
	                buffer.append(" LEFT OUTER JOIN");
	            else if (joinType.contains("right"))
	                buffer.append(" RIGHT OUTER JOIN");
	            else if (joinType.contains("full"))
	                buffer.append(" FULL OUTER JOIN");*/
            buffer.append(" " + sourceTable.getJoinType() + " JOIN");
            buffer.append(" ").append(sourceTable.getInterfaceName()).append(" ").append(sourceTable.getSrcTableAlias());

            buffer.append("\n");

            buffer.append(" ").append(sourceTable.getJoinCondition());
            String addStr = "";
	           
	            /*if(loadMode.equals("A"))
	            {*/
            if (sourceTable.getInterfaceName().contains("${ODSDB}."))
                addStr = " AND t0.ppi=" + sourceTable.getSrcTableAlias() + ".ppi";
            else if (sourceTable.getInterfaceName().contains("${hivevar:targetDB}.")) {
                if (sourceTable.getInterfaceName().contains("${hivevar:targetDB}.T99"))
                    addStr = "";
                else
                    addStr = " AND t0.ppi=" + sourceTable.getSrcTableAlias() + ".BDW_Data_Dt";
            } else addStr = "";
            //}
            if (addStr.length() > 0)
                buffer.append("  ").append(addStr);
        }

        return buffer.toString();
    }


    //按照连接次序排序
    private static class ETLSourceTableComparator implements Comparator<SrcTableListEntity> {
        @Override
        public int compare(SrcTableListEntity t1, SrcTableListEntity t2) {
            if (StringOperateUtils.stringToInt(t1.getJoinOrder()) < StringOperateUtils.stringToInt(t2.getJoinOrder()))
                return -1;
            else
                return 1;
        }
    }

    //按照column_id次序排序
    private static class ETLSourceColumnComparator implements Comparator<TgtTableEtlRulesEntity> {
        @Override
        public int compare(TgtTableEtlRulesEntity t1, TgtTableEtlRulesEntity t2) {
            if (StringOperateUtils.stringToInt(t1.getColumnId()) < StringOperateUtils.stringToInt(t2.getColumnId()))
                return -1;
            else
                return 1;
        }
    }
}
