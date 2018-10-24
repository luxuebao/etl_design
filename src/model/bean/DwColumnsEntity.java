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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * (DW_COLUMNS)
 * 
 * @author bianj
 * @version 1.0.0 2017-09-25
 */
@Entity
@Table(name = "DW_COLUMNS")
public class DwColumnsEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 3262255642596492440L;
    
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
    @Column(name = "COLUMN_ID", nullable = true, length = 10)
    private Integer columnId;
    
    /**  */
    @Column(name = "DATA_TYPE", nullable = true, length = 64)
    private String dataType;
    
    /**  */
    @Column(name = "PHY_NAME", nullable = true, length = 64)
    private String phyName;
    
    /**  */
    @Column(name = "AGG_PERIOD", nullable = true, length = 20)
    private String aggPeriod;
    
    /**  */
    @Column(name = "IS_PK", nullable = true, length = 20)
    private boolean isPk;
    
    /**  */
    @Column(name = "CHAIN_COMPARE", nullable = true, length = 20)
    private String chainCompare;
    
    /**  */
    @Column(name = "IS_PARTITION_KEY", nullable = true, length = 20)
    private String isPartitionKey;
    
    /**  */
    @Column(name = "COMMENTS", nullable = true, length = 512)
    private String comments;
    
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
    public Integer getColumnId() {
        return this.columnId;
    }
     
    /**
     * 设置
     * 
     * @param columnId
     *          
     */
    public void setColumnId(Integer columnId) {
        this.columnId = columnId;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getDataType() {
        return this.dataType;
    }
     
    /**
     * 设置
     * 
     * @param dataType
     *          
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getPhyName() {
        return this.phyName;
    }
     
    /**
     * 设置
     * 
     * @param phyName
     *          
     */
    public void setPhyName(String phyName) {
        this.phyName = phyName;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getAggPeriod() {
        return this.aggPeriod;
    }
     
    /**
     * 设置
     * 
     * @param aggPeriod
     *          
     */
    public void setAggPeriod(String aggPeriod) {
        this.aggPeriod = aggPeriod;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public boolean getIsPk() {
        return this.isPk;
    }
     
    /**
     * 设置
     * 
     * @param isPk
     *          
     */
    public void setIsPk(boolean isPk) {
        this.isPk = isPk;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getChainCompare() {
        return this.chainCompare;
    }
     
    /**
     * 设置
     * 
     * @param chainCompare
     *          
     */
    public void setChainCompare(String chainCompare) {
        this.chainCompare = chainCompare;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getIsPartitionKey() {
        return this.isPartitionKey;
    }
     
    /**
     * 设置
     * 
     * @param isPartitionKey
     *          
     */
    public void setIsPartitionKey(String isPartitionKey) {
        this.isPartitionKey = isPartitionKey;
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
}