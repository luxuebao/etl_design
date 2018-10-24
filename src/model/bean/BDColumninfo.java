package model.bean;

public class BDColumninfo {

		    /**  
		     * 系统名称  
		     */   
		    private String sysName;  
		    /**  
		     * 模式名称  
		     */   
		    private String schemaName;  
   
		    /**  
		     * 表名称  
		     */   
		    private String tableName; 
		    /**  
		     * 表名称  (中文)
		     */   
		    private String tableChnName;   
		    /**  
		     * 表名称  ID
		     */   
		    private String tableId;   
		   
		    /**  
		     * 列顺序 
		     */   
		    private int seqNo;   
		    /**  
		     * 列名称(字段名称)  
		     */   
		    private String name;   
		    /**  
		     * 列名称(字段名称中文)  
		     */   
		    private String chnName;   
		    /**  
		     * 是否主键  
		     */   
		    private  int isPk;   
		    /**  
		     * 默认值  
		     */   
		    private String value;   
		    /**  
		     * 是否为空  
		     */   
		    private int isNotNull;   
		    /**  
		     * 数据类型  
		     */   
		    private String type;   
		    /**  
		     * 数据长度  
		     */   
		    private int length;   
		    /**  
		     * 代码类型  
		     */   
		    private int codeType; 
		    /**  
		     * 列注释 
		     */   
		    private  String comments;
		    /**  
		     * 小数位数 
		     */ 
		    private  int decimalDigits;
		    /**  
		     * 是否分区列 
		     */ 
		    private  String isPartition;
		    /**  
		     * 字段状态（是否要获取 0：正常，1，不获取）
		     */ 
		    private  String status;
		    
		    
		    
		    
		    public String getSysName() {   
		        return sysName;   
		    }   
		   
		    public void setSysName(String sysName) {   
		        this.sysName = sysName;   
		    }   
		    public String getSchemaName() {   
		        return schemaName;   
		    }   
		   
		    public void setSchemaName(String schemaName) {   
		        this.schemaName = schemaName;   
		    }   
		    public String getTableId() {   
		        return tableId;   
		    }   
		   
		    public void setTableId(String tableId) {   
		        this.tableId = tableId;   
		    }   
		    public String getTableName() {   
		        return tableName;   
		    }   
		   
		    public void setTableName(String tableName) {   
		        this.tableName = tableName;   
		    }  
		    public String getTableChnName() {   
		        return tableChnName;   
		    }   
		   
		    public void setTableChnName(String tableChnName) {   
		        this.tableChnName = tableChnName;   
		    }   
		    public String getName() {   
		        return name;   
		    }   
		   
		    public void setName(String name) {   
		        this.name = name;   
		    }   
		    public String getChnName() {   
		        return chnName;   
		    }   
		   
		    public void setChnName(String chnName) {   
		        this.chnName = chnName;   
		    }   
		    public int getPk() {   
		        return isPk;   
		    }   
		   
		    public void setPk(int pk) {   
		        isPk = pk;   
		    }   
		   
		    public String getValue() {   
		        return value;   
		    }   
		   
		    public void setValue(String value) {   
		        this.value = value;   
		    }   
		   
		    public int getNotNull() {   
		        return isNotNull;   
		    }   
		   
		    public void setNotNull(int notNull) {   
		        isNotNull = notNull;   
		    }   
		   
		    public String getType() {   
		        return type;   
		    }   
		   
		    public void setType(String type) {   
		        this.type = type;   
		    }   
		   
		    public int getLength() {   
		        return length;   
		    }   
		   
		    public void setLength(int length) {   
		        this.length = length;   
		    }   
		   
		    public int getCodeType() {   
		        return codeType;   
		    }   
		   
		    public void setCodeType(int codeType) {   
		        this.codeType = codeType;   
		    }   
		    public int getSeqNo() {   
		        return seqNo;   
		    }   
		   
		    public void setSeqNo(int seqNo) {   
		        this.seqNo = seqNo;   
		    }   
		    public String getComments() {   
		        return comments;   
		    }   
		   
		    public void setComments(String comments) {   
		        this.comments = comments;   
		    }  
		 
		    public int getDecimalDigits() {   
		        return decimalDigits;   
		    }   
		   
		    public void setDecimalDigits(int decimalDigits) {   
		        this.decimalDigits = decimalDigits;   
		    }   
		    public String getIsPartition() {   
		        return isPartition;   
		    }   
		   
		    public void setIsPartition(String isPartition ) {   
		        this.isPartition  = isPartition ;   
		    }   
		    public String getStatus() {   
		        return status;   
		    }   
		   
		    public void setStatus(String status ) {   
		        this.status  = status ;   
		    }   
		    @Override   
		    public String toString() {   
		        return "Column{" +   
		        		"sysName='" + sysName + '\'' +
		        		",schemaName='" + schemaName + '\'' +
		                ",tableName='" + tableName + '\'' +
		                ", seqNum='" + seqNo + '\'' +   
		                ", name='" + name + '\'' +   
		                ", isPk=" + isPk + '\'' +   
		                ", value='" + value + '\'' +   
		                ", isNotNull=" + isNotNull + '\'' +   
		                ", type='" + type + '\'' +   
		                ", length=" + length +   '\'' +
		                ", decimalDigits=" + decimalDigits + '\'' +   
		                ", comments=" + comments +   
		                '}';   
		    }   
		    public String toString_exp() {   
		       
		    	return tableName + '|' + seqNo + '|' + name + '|' + type  + '|' + length + '|' + isPk + '|' + isNotNull + '|' + value +'|' + decimalDigits +'|' + comments +'|'+'\n';
		        
		    }
		}   