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
 * (DW_TABLE_MAPPING)
 * 
 * @author bianj
 * @version 1.0.0 2017-09-25
 */
@Entity
@Table(name = "DW_TABLE_MAPPING")
public class DwTableMappingEntity implements java.io.Serializable {
    /** 版本号 */
  //  private static final long serialVersionUID = 5374099129021987138L;
    
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
    @Column(name = "SRC_SYS_NAME", unique = true, nullable = false, length = 64)
    private String srcSysName;
    
    /**  */
    @Id
    @Column(name = "SRC_SCHEMA", unique = true, nullable = false, length = 64)
    private String srcSchema;
    
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
    @Column(name = "LOAD_BATCH", unique = true, nullable = false, length = 10)
    private Integer loadBatch;
    
    /**  */
    @Id
    @Column(name = "JOIN_ORDER", unique = true, nullable = false, length = 10)
    private Integer joinOrder;
    
    /**  */
    @Column(name = "TABLE_ALIAS", nullable = true, length = 64)
    private String tableAlias;
    
    /**  */
    @Column(name = "JOIN_TYPE", nullable = true, length = 20)
    private String joinType;
    
    /**  */
    @Column(name = "JOIN_CONDITION", nullable = true, length = 65535)
    private String joinCondition;
    
    /**  */
    @Column(name = "FILTER_CONDITION", nullable = true, length = 65535)
    private String filterCondition;
    
    /**  */
    @Column(name = "COMMENTS", nullable = true, length = 512)
    private String comments;
   
    private int rowCount;
    private String interfaceTable;
    private String isIncExtract;   //增量抽取标识
    private int incExtractDays; //增量抽取天数

    private int groupIdx;//组号
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
     * 获取
     * 
     * @return 
     */
    public String getSrcTableNameCn() {
        return this.srcTableNameCn;
    }
     
    /**
     * 设置
     * 
     * @param tableName
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
    public Integer getJoinOrder() {
        return this.joinOrder;
    }
     
    /**
     * 设置
     * 
     * @param joinOrder
     *          
     */
    public void setJoinOrder(Integer joinOrder) {
        this.joinOrder = joinOrder;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getTableAlias() {
        return this.tableAlias;
    }
     
    /**
     * 设置
     * 
     * @param tableAlias
     *          
     */
    public void setTableAlias(String tableAlias) {
        this.tableAlias = tableAlias;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getJoinType() {
        return this.joinType;
    }
     
    /**
     * 设置
     * 
     * @param joinType
     *          
     */
    public void setJoinType(String joinType) {
        this.joinType = joinType;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getJoinCondition() {
        return this.joinCondition;
    }
     
    /**
     * 设置
     * 
     * @param joinCondition
     *          
     */
    public void setJoinCondition(String joinCondition) {
        this.joinCondition = joinCondition;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getFilterCondition() {
        return this.filterCondition;
    }
     
    /**
     * 设置
     * 
     * @param filterCondition
     *          
     */
    public void setFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
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
    
    public String getIsIncExtract() {
        return isIncExtract;
    }

    public void setIsIncExtract(String isIncExtract) {
        this.isIncExtract = isIncExtract;
    }
    public int getIncExtractDays() {
        return incExtractDays;
    }

    public void setIncExtractDays(int incExtractDays) {
        this.incExtractDays = incExtractDays;
    }
    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public String getInterfaceTable() {
        return interfaceTable;
    }

    public void setInterfaceTable(String interfaceTable) {
        this.interfaceTable = interfaceTable;
    }
    public int getGroupIdx() {
        return groupIdx;
    }

    public void setGroupIdx(int groupIdx ) {
        this.groupIdx = groupIdx;
    }
}
