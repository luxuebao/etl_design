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
 * (TGT_TABLE_ETL_RULES)
 * 
 * @author bianj
 * @version 1.0.0 2017-09-28
 */
@Entity
@Table(name = "TGT_TABLE_ETL_RULES")
public class TgtTableEtlRulesEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 1721209077226476975L;
    
    /** 目标实体 */
    @Column(name = "TGT_ENTITY", nullable = true, length = 64)
    private String tgtEntity;
    
    /** 目标数据库名 */
    @Column(name = "TGT_DB_SCHEMA", nullable = true, length = 64)
    private String tgtDbSchema;
    
    /** 物理表名 */
    @Column(name = "PHY_NAME", nullable = true, length = 64)
    private String phyName;
    
    /** 字段序号 */
    @Id
    @Column(name = "COLUMN_ID", unique = true, nullable = false, length = 8)
    private String columnId;
    
    /** 字段名 */
    @Column(name = "COLUMN_NAME_EN", nullable = true, length = 64)
    private String columnNameEn;
    
    /** 字段中文名 */
    @Column(name = "COLUMN_NAME_CH", nullable = true, length = 64)
    private String columnNameCh;
    
    /** 数据类型 */
    @Column(name = "DATA_TYPE", nullable = true, length = 32)
    private String dataType;
    
    /** 主键 */
    @Column(name = "IS_PK", nullable = true, length = 2)
    private String isPk;
    
    /** 分区键 */
    @Column(name = "IS_PARTITION_KEY", nullable = true, length = 2)
    private String isPartitionKey;
    
    /** 加载批次 */
    @Id
    @Column(name = "LOAD_BATCH", unique = true, nullable = false, length = 2)
    private String loadBatch;
    
    /** 组别 */
    @Id
    @Column(name = "GROUP_ID", unique = true, nullable = false, length = 2)
    private String groupId;
    
    /** 源表名 */
    @Column(name = "SRC_TABLE_NAME", nullable = true, length = 64)
    private String srcTableName;
    /** 源表中文名 */
    @Column(name = "SRC_TABLE_NAME_CN", nullable = true, length = 64)
    private String srcTableNameCn;
    
    /** 源表字段 */
    @Column(name = "SRC_COLUMN_NAME", nullable = true, length = 64)
    private String srcColumnName;
   
    /** 源表字段中文名 */
    @Column(name = "SRC_COLUMN_NAME_CN", nullable = true, length = 64)
    private String srcColumnNameCn;
    
    /** 源表字段类型 */
    @Column(name = "SRC_COLUMN_TYPE", nullable = true, length = 32)
    private String srcColumnType;
    
    /** 计算表达式 */
    @Column(name = "COMPUTE_EXPRESSION", nullable = true, length = 512)
    private String computeExpression;
    
    /** 表达式备注 */
    @Column(name = "EXPRESSION_COMMENTS", nullable = true, length = 512)
    private String expressionComments;
    
    /**
     * 获取目标实体
     * 
     * @return 目标实体
     */
    public String getTgtEntity() {
        return this.tgtEntity;
    }
     
    /**
     * 设置目标实体
     * 
     * @param tgtEntity
     *          目标实体
     */
    public void setTgtEntity(String tgtEntity) {
        this.tgtEntity = tgtEntity;
    }
    
    /**
     * 获取目标数据库名
     * 
     * @return 目标数据库名
     */
    public String getTgtDbSchema() {
        return this.tgtDbSchema;
    }
     
    /**
     * 设置目标数据库名
     * 
     * @param tgtDbSchema
     *          目标数据库名
     */
    public void setTgtDbSchema(String tgtDbSchema) {
        this.tgtDbSchema = tgtDbSchema;
    }
    
    /**
     * 获取物理表名
     * 
     * @return 物理表名
     */
    public String getPhyName() {
        return this.phyName;
    }
     
    /**
     * 设置物理表名
     * 
     * @param phyName
     *          物理表名
     */
    public void setPhyName(String phyName) {
        this.phyName = phyName;
    }
    
    /**
     * 获取字段序号
     * 
     * @return 字段序号
     */
    public String getColumnId() {
        return this.columnId;
    }
     
    /**
     * 设置字段序号
     * 
     * @param columnId
     *          字段序号
     */
    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }
    
    /**
     * 获取字段名
     * 
     * @return 字段名
     */
    public String getColumnNameEn() {
        return this.columnNameEn;
    }
     
    /**
     * 设置字段名
     * 
     * @param columnNameEn
     *          字段名
     */
    public void setColumnNameEn(String columnNameEn) {
        this.columnNameEn = columnNameEn;
    }
    
    /**
     * 获取字段中文名
     * 
     * @return 字段中文名
     */
    public String getColumnNameCh() {
        return this.columnNameCh;
    }
     
    /**
     * 设置字段中文名
     * 
     * @param columnNameCh
     *          字段中文名
     */
    public void setColumnNameCh(String columnNameCh) {
        this.columnNameCh = columnNameCh;
    }
    
    /**
     * 获取数据类型
     * 
     * @return 数据类型
     */
    public String getDataType() {
        return this.dataType;
    }
     
    /**
     * 设置数据类型
     * 
     * @param dataType
     *          数据类型
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
    
    /**
     * 获取主键
     * 
     * @return 主键
     */
    public String getIsPk() {
        return this.isPk;
    }
     
    /**
     * 设置主键
     * 
     * @param isPk
     *          主键
     */
    public void setIsPk(String isPk) {
        this.isPk = isPk;
    }
    
    /**
     * 获取分区键
     * 
     * @return 分区键
     */
    public String getIsPartitionKey() {
        return this.isPartitionKey;
    }
     
    /**
     * 设置分区键
     * 
     * @param isPartitionKey
     *          分区键
     */
    public void setIsPartitionKey(String isPartitionKey) {
        this.isPartitionKey = isPartitionKey;
    }
    
    /**
     * 获取加载批次
     * 
     * @return 加载批次
     */
    public String getLoadBatch() {
        return this.loadBatch;
    }
     
    /**
     * 设置加载批次
     * 
     * @param loadBatch
     *          加载批次
     */
    public void setLoadBatch(String loadBatch) {
        this.loadBatch = loadBatch;
    }
    
    /**
     * 获取组别
     * 
     * @return 组别
     */
    public String getGroupId() {
        return this.groupId;
    }
     
    /**
     * 设置组别
     * 
     * @param groupId
     *          组别
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    
    /**
     * 获取源表名
     * 
     * @return 源表名
     */
    public String getSrcTableName() {
        return this.srcTableName;
    }
     
    /**
     * 设置源表名
     * 
     * @param srcTableName
     *          源表名
     */
    public void setSrcTableName(String srcTableName) {
        this.srcTableName = srcTableName;
    }
    /**
     * 获取源表中文名
     * 
     * @return 源表中文名
     */
    public String getSrcTableNameCn() {
        return this.srcTableNameCn;
    }
     
    /**
     * 设置源表中文名
     * 
     * @param srcTableNameCn
     *          源表中文名
     */
    public void setSrcTableNameCn(String srcTableNameCn) {
        this.srcTableNameCn = srcTableNameCn;
    }
    /**
     * 获取源表字段
     * 
     * @return 源表字段
     */
    public String getSrcColumnName() {
        return this.srcColumnName;
    }
     
    /**
     * 设置源表字段
     * 
     * @param srcColumnName
     *          源表字段
     */
    public void setSrcColumnName(String srcColumnName) {
        this.srcColumnName = srcColumnName;
    }
    /**
     * 获取源表字段中文名
     * 
     * @return 源表字段中文名
     */
    public String getSrcColumnNameCn() {
        return this.srcColumnNameCn;
    }
     
    /**
     * 设置源表字段中文名
     * 
     * @param srcColumnNameCn
     *          源表字段中文名
     */
    public void setSrcColumnNameCn(String srcColumnNameCn) {
        this.srcColumnNameCn = srcColumnNameCn;
    }
    /**
     * 获取源表字段类型
     * 
     * @return 源表字段类型
     */
    public String getSrcColumnType() {
        return this.srcColumnType;
    }
     
    /**
     * 设置源表字段类型
     * 
     * @param srcColumnType
     *          源表字段类型
     */
    public void setSrcColumnType(String srcColumnType) {
        this.srcColumnType = srcColumnType;
    }
    
    /**
     * 获取计算表达式
     * 
     * @return 计算表达式
     */
    public String getComputeExpression() {
        return this.computeExpression;
    }
     
    /**
     * 设置计算表达式
     * 
     * @param computeExpression
     *          计算表达式
     */
    public void setComputeExpression(String computeExpression) {
        this.computeExpression = computeExpression;
    }
    
    /**
     * 获取表达式备注
     * 
     * @return 表达式备注
     */
    public String getExpressionComments() {
        return this.expressionComments;
    }
     
    /**
     * 设置表达式备注
     * 
     * @param expressionComments
     *          表达式备注
     */
    public void setExpressionComments(String expressionComments) {
        this.expressionComments = expressionComments;
    }
}