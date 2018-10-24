package model.constant;

public class Constant {
	
		//public final static String cfgPropertiesfile ="D:\\tableData\\PDATA\\ods2pdata.properties";
		public static final String dbPropertiesfile="db.config.properties";
	
		public final static String loadDateColName = "BDW_Data_Dt";
	    public final static String workDateVarName = "${BDW_DATA_DT}";
	    public final static String lastWorkDateVarName = "${LAST_DATA_DT}";
	    
	    public final static String srcSysCode = "BDW_Src_Sys_Cd";
	    public final static String srcSysVarName = "${BDW_Src_Sys_Cd}";
	   
	    public final static String bdwMinDateVarName = "${BDW_MIN_DT}";
	    public final static String bdwMAXDateVarName = "${BDW_MAX_DT}";
	    
	    public final static String srcTableName ="BDW_Src_Tbl";//数据来源表;
	    public final static String etlOptDateName ="BDW_ETL_Tm";  //操作时间日期;
	    public final static String charset="UTF-8";
	    
	    public static final String ISINCREMNETAL = "1";    //增量抽取标识，1：增量，0：全量
	
}
