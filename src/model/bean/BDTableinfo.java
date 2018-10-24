package model.bean;

import java.util.List;

public class BDTableinfo {
   
			 /**  
		     * 所属系统id  
		     */   
		    private String  sysid;   
		   
			 /**  
		     * 表id  
		     */   
		    private String  tableid;   
		   
			/**  
		     * 表名称  
		     */   
		    private String tablename;
		    /**  
		     * 表名称  
		     */   
		    private String tabletype;
		    
		    /**  
		     * 存储空间(库名)  
		     */   
		    private String schema;   
		    /**  
		     * 表中日期（或者时间）列  
		     */   
		    private String incColumn;   
		    /**  
		     * 表中日期（或者时间）列 类型（D：date,T:timestamp,C:char）
		     */   
		    private String incColumnType;   
		   
		    /**  
		     * 批量对应会计日期 
		     */   
		    private String batchDate;  
		    /**  
		     * 批量数据开始日期 
		     */   
		    private String startDate;  
		    /**  
		     * 表中所有列 
		     */   
		    List<BDColumninfo> columns; 
		    
		    /**  
		     * 增量抽取标识，1：增量，0：全量 
		     */   
		    private String incFlag;  
		    /**  
		     * 增量日期跨度
		     */   
		    private int spanDays;  
		    /**  
		     * 入仓标识 
		     */   
		    private String indwFlag;  
		    
		    public String getSysid() {   
		        return sysid;   
		    }   
		   
		    public void setSysid(String sysid) {   
		        this.sysid = sysid;   
		    }   
		   
		    public String getTableid() {   
		        return tableid;   
		    }   
		   
		    public void setTableid(String tableid) {   
		        this.tableid = tableid;   
		    }   
		   
		   
		    public String getTablename() {   
		        return tablename;   
		    }   
		   
		    public void setTablename(String tablename) {   
		        this.tablename = tablename;   
		    }   

		    public String getTabletype() {   
		        return tabletype;   
		    }   
		   
		    public void setTabletype(String tabletype) {   
		        this.tabletype = tabletype;   
		    }
		    public String getSchema() {   
		        return schema;   
		    }   
		   
		    public void setSchema(String schema) {   
		        this.schema = schema;   
		    }   
		   
		    public String getIncColumn() {   
		        return incColumn;   
		    }   
		    public void setIncColumn(String incColumn) {   
		        this.incColumn = incColumn;   
		    }  
		    public void setIncColumntype(String incColumnType) {   
		        this.incColumnType = incColumnType;   
		    }  
		    public String getInColumntype() {   
		        return incColumnType;   
		    }   
		   
		    
		    public String getBatchDate() {   
		        return batchDate;   
		    }   
		   
		    public void setBatchDate(String batchDate) {   
		        this.batchDate = batchDate;   
		    }   
		    public String getStartDate() {   
		        return startDate;   
		    }   
		   
		    public void setStartDate(String startDate) {   
		        this.startDate = startDate;   
		    }   
		    public String getIncFlag() {   
		        return incFlag;   
		    }   
		   
		    public void setIncFlag(String incFlag) {   
		        this.incFlag = incFlag;   
		    }   
		    public String getIndwFlag() {   
		        return indwFlag;   
		    }   
		   
		    public void setIndwFlag(String indwFlag) {   
		        this.indwFlag = indwFlag;   
		    }   
		    public int getSpanDays() {   
		        return spanDays;   
		    }   
		   
		    public void setSpanDays(int spanDays) {   
		        this.spanDays = spanDays;   
		    }   
		    public List<BDColumninfo> getColumns() {   
		        return columns;   
		    }
		    
		    public void setColumns(List<BDColumninfo> columns) {   
		        this.columns = columns;   
		    }   
		   
		    @Override   
		    public String toString() {   
		        return "Table{" +   
		                "name='" + tablename + '\'' +   
		                ", space='" + schema + '\'' +   
		                ", columns=" + columns +   
		                '}';   
		    }   
		}   
