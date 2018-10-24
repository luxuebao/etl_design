package model.bean;

/*
 * Welcome to use the TableGo Tools.
 * 
 * http://vipbooks.iteye.com
 * http://blog.csdn.net/vipbooks
 * http://www.cnblogs.com/vipbooks
 * 
 * Author:bianj
 * Email:edinsker@163.com
 * Version:4.1.2
 */

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * (DW_COLUMN_MAPPING)
 * 
 * @author bianj
 * @version 1.0.0 2017-09-25
 */
@Entity
@Table(name = "DW_COLUMN_MAPPING")
public class DwColumnMappingEntity implements java.io.Serializable {
    /** 版本号 */
   // private static final long serialVersionUID = -1947673085805193092L;
    
    /**  */
    @Id
    @Column(name = "SYS_NAME", unique = true, nullable = false, length = 64)
    private String sysName;
    
    /**  */
    @Id
    @Column(name = "SCHEMA_NAME", unique = true, nullable = false, length = 64)
    private String schemaName;
    
    /**  */
    @Id
    @Column(name = "TABLE_NAME", unique = true, nullable = false, length = 64)
    private String tableName;
    
    /**  */
    @Id
    @Column(name = "COLUMN_NAME", unique = true, nullable = false, length = 64)
    private String columnName;
    
    /**  */
    @Id
    @Column(name = "SRC_SYS_NAME", unique = true, nullable = false, length = 64)
    private String srcSysName;
    
    /**  */
    @Id
    @Column(name = "SRC_SCHEMA", unique = true, nullable = false, length = 64)
    private String srcSchema;
    
    /**  */
    @Id
    @Column(name = "SRC_STBL_NAME", unique = true, nullable = false, length = 64)
    private String srcStblName;
    
    /**  */
    @Id
    @Column(name = "SRC_TABLE_NAME", unique = true, nullable = false, length = 64)
    private String srcTableName;
    /**  */
    @Id
    @Column(name = "SRC_TABLE_NAME_CN", unique = true, nullable = false, length = 64)
    private String srcTableNameCn;
    
    /**  */
    @Id
    @Column(name = "SRC_COLUMN_NAME", unique = true, nullable = false, length = 64)
    private String srcColumnName;
  
    /**  */
    @Id
    @Column(name = "SRC_COLUMN_NAME_CN", unique = true, nullable = false, length = 64)
    private String srcColumnNameCn;
  
    /**  */
    @Id
    @Column(name = "SRC_COLUMN_DATA_TYPE", unique = true, nullable = false, length = 64)
    private String srcColumnDataType;
    /**  */
    @Id
    @Column(name = "LOAD_BATCH", unique = true, nullable = false, length = 10)
    private Integer loadBatch;
    
    /**  */
    @Id
    @Column(name = "LOAD_GROUP", unique = true, nullable = false, length = 10)
    private Integer loadGroup;
    
    /**  */
    @Column(name = "COLUMN_EXPR", nullable = true, length = 65535)
    private String columnExpr;
    
    /**  */
    @Column(name = "COMMENTS", nullable = true, length = 512)
    private String comments;
    
    /**  */
    @Column(name = "INTE_FLAG", nullable = true, length = 8)
    private String inteFlag;
    
    /**  */
    @Column(name = "CODE_TABLE", nullable = true, length = 65535)
    private String codeTable;
    
    
    private DwColumnsEntity entityAttribute;
    private List<ETLSourceColumn> srcColumnList = new ArrayList<ETLSourceColumn>();
    private int groupIdx;
    /**
     * 获取
     * 
     * @return 
     */
    public String getSysName() {
        return this.sysName;
    }
     
    /**
     * 设置
     * 
     * @param sysName
     *          
     */
    public void setSysName(String sysName) {
        this.sysName = sysName;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getSchemaName() {
        return this.schemaName;
    }
     
    /**
     * 设置
     * 
     * @param schemaName
     *          
     */
    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getTableName() {
        return this.tableName;
    }
     
    /**
     * 设置
     * 
     * @param tableName
     *          
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getColumnName() {
        return this.columnName;
    }
     
    /**
     * 设置
     * 
     * @param columnName
     *          
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getSrcStblName() {
        return this.srcStblName;
    }
     
    /**
     * 设置
     * 
     * @param srcStblName
     *          
     */
    public void setSrcStblName(String srcStblName) {
        this.srcStblName = srcStblName;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getSrcSysName() {
        return this.srcSysName;
    }
     
    /**
     * 设置
     * 
     * @param srcSysName
     *          
     */
    public void setSrcSysName(String srcSysName) {
        this.srcSysName = srcSysName;
    }
    
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getSrcSchema() {
        return this.srcSchema;
    }
     
    /**
     * 设置
     * 
     * @param srcSchema
     *          
     */
    public void setSrcSchema(String srcSchema) {
        this.srcSchema = srcSchema;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getSrcTableName() {
        return this.srcTableName;
    }
    /**
     * 设置
     * 
     * @param srcTableName
     *          
     */
    public void setSrcTableName(String srcTableName) {
        this.srcTableName = srcTableName;
    }
    
    /**
     * 设置
     * 
     * @param
     *          
     */
    public void setSrcTableNameCn(String srcTableNameCn) {
        this.srcTableNameCn = srcTableNameCn;
    }
    /**
     * 获取
     * 
     * @return 
     */
    public String getSrcTableNameCn() {
        return this.srcTableNameCn;
    }
     
    
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getSrcColumnName() {
        return this.srcColumnName;
    }
     
    /**
     * 设置
     * 
     * @param srcColumnName
     *          
     */
    public void setSrcColumnName(String srcColumnName) {
        this.srcColumnName = srcColumnName;
    }
    /**
     * 获取
     * 
     * @return 
     */
    public String getSrcColumnNameCn() {
        return this.srcColumnNameCn;
    }
     
    /**
     * 设置
     * 
     * @param srcColumnNameCn
     *          
     */
    public void setSrcColumnNameCn(String srcColumnNameCn) {
        this.srcColumnNameCn = srcColumnNameCn;
    }
    
    /**
    * 获取
    * 
    * @return 
    */
   public String getSrcColumnDataType() {
       return this.srcColumnDataType;
   }
    
   /**
    * 设置
    * 
    * @param
    *          
    */
   public void setSrcColumnDataType(String srcColumnDataType) {
       this.srcColumnDataType = srcColumnDataType;
   }
    /**
     * 获取
     * 
     * @return 
     */
    public Integer getLoadBatch() {
        return this.loadBatch;
    }
     
    /**
     * 设置
     * 
     * @param loadBatch
     *          
     */
    public void setLoadBatch(Integer loadBatch) {
        this.loadBatch = loadBatch;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public Integer getLoadGroup() {
        return this.loadGroup;
    }
     
    /**
     * 设置
     * 
     * @param loadGroup
     *          
     */
    public void setLoadGroup(Integer loadGroup) {
        this.loadGroup = loadGroup;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getColumnExpr() {
        return this.columnExpr;
    }
     
    /**
     * 设置
     * 
     * @param columnExpr
     *          
     */
    public void setColumnExpr(String columnExpr) {
        this.columnExpr = columnExpr;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getComments() {
        return this.comments;
    }
     
    /**
     * 设置
     * 
     * @param comments
     *          
     */
    public void setComments(String comments) {
        this.comments = comments;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getInteFlag() {
        return this.inteFlag;
    }
     
    /**
     * 设置
     * 
     * @param
     *          
     */
    public void setInteFlag(String inteFlag) {
        this.inteFlag = inteFlag;
    }
    public DwColumnsEntity getEntityAttribute() {
        return entityAttribute;
    }

    public void setEntityAttribute(DwColumnsEntity entityAttribute) {
        this.entityAttribute = entityAttribute;
    }

    public List<ETLSourceColumn> getSrcColumnList() {
        return srcColumnList;
    }

    public void setSrcColumnList(List<ETLSourceColumn> srcColumnList) {
        this.srcColumnList = srcColumnList;
    }
    public int getGroupIdx() {
        return groupIdx;
    }

    public void setGroupIdx(int groupIdx ) {
        this.groupIdx = groupIdx;
    }
    /**
     * 设置
     * 
     * @param
     *          
     */
    public void setCodeTable(String codeTable) {
        this.codeTable = codeTable;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getCodeTable() {
        return this.codeTable;
    }
}
