package model.bean;

public enum TableHeader {
   
	TASK_DEF("作业定义表", 0), TGT_TABLES_LIST("源表列表", 1), TGT_TABLE_ETL_RULES("目标表ETL规则", 2);
    
    
	private String header;
	private int colFlag;
    
	private TableHeader(String header, int colFlag){
	   this.header = header;
	   this.colFlag = colFlag;
	}
    
	public String getHeader() {
		return header;
	}

	public int getColFlag() {
		return colFlag;
	}
}
