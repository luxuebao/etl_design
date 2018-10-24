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

import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * (TGT_TABLE_LIST)
 * 
 * @author bianj
 * @version 1.0.0 2017-09-28
 */
@Entity
@Table(name = "TGT_TABLE_LIST")
public class SrcTableListEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -4992124424527648466L;
    
    /** 目标实体 */
    @Column(name = "TGT_ENTITY", nullable = true, length = 64)
    private String tgtEntity;
    
    /** 目标数据库名 */
    @Column(name = "TGT_DB_SCHEMA", nullable = true, length = 64)
    private String tgtDbSchema;
    
    /** 物理表名 */
    @Column(name = "PHY_NAME", nullable = true, length = 64)
    private String phyName;
    
    /** 加载批次 */
    @Id
    @Column(name = "LOAD_BATCH", unique = true, nullable = false, length = 2)
    private String loadBatch;
    
    /** 源表名 */
    @Id
    @Column(name = "SRC_TABLE_NAME", unique = true, nullable = false, length = 64)
    private String srcTableName;
    
    /** 源数据库名 */
    @Column(name = "SRC_SCHEMA_NAME", nullable = true, length = 64)
    private String srcSchemaName;
    
    /** 源系统 */
    @Column(name = "SRC_SYS_NAME", nullable = true, length = 64)
    private String srcSysName;
    
    /** 源表描述 */
    @Column(name = "SRC_TABLE_DESC", nullable = true, length = 512)
    private String srcTableDesc;
    
    /** 源表别名 */
    @Column(name = "SRC_TABLE_ALIAS", nullable = true, length = 64)
    private String srcTableAlias;
    
    /** 接口名 */
    @Column(name = "INTERFACE_NAME", nullable = true, length = 64)
    private String interfaceName;
    
    /** 组别 */
    @Id
    @Column(name = "GROUP_ID", unique = true, nullable = false, length = 2)
    private String groupId;
    
    /** 连接次序 */
    @Id
    @Column(name = "JOIN_ORDER", unique = true, nullable = false, length = 2)
    private String joinOrder;
    
    /** 连接类型 */
    @Column(name = "JOIN_TYPE", nullable = true, length = 32)
    private String joinType;
    
    /** 连接条件 */
    @Column(name = "JOIN_CONDITION", nullable = true, length = 256)
    private String joinCondition;
    
    /** 筛选条件 */
    @Column(name = "FILTER_CONDITION", nullable = true, length = 512)
    private String filterCondition;
    
    /** 增量抽取 */
    @Column(name = "IS_INCREMENTAL", nullable = true, length = 2)
    private String isIncremental;
    
    /** 备注 */
    @Column(name = "COMMENTS", nullable = true, length = 512)
    private String comments;
    /**  */
    @Column(name = "LOAD_MODE", nullable = true, length = 20)
    private String loadMode;
    
    /**  */
    @Column(name = "IS_SINGLE_SOURCE", nullable = true, length = 20)
    private String isSingleSource;
    
    /** 增量抽取天数 */
    @Column(name = "INC_EXTRACT_DAYS", nullable = true, length = 20)
    private String incExtractDays;
    
    //表映射关系
    private Map<String,List> tgtTblLstMap;
    //字段映射关系
    private Map<String,List> tgtTblRuleMap;
  
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
     * 获取源数据库名
     * 
     * @return 源数据库名
     */
    public String getSrcSchemaName() {
        return this.srcSchemaName;
    }
     
    /**
     * 设置源数据库名
     * 
     * @param srcSchemaName
     *          源数据库名
     */
    public void setSrcSchemaName(String srcSchemaName) {
        this.srcSchemaName = srcSchemaName;
    }
    
    /**
     * 获取源系统
     * 
     * @return 源系统
     */
    public String getSrcSysName() {
        return this.srcSysName;
    }
     
    /**
     * 设置源系统
     * 
     * @param srcSysName
     *          源系统
     */
    public void setSrcSysName(String srcSysName) {
        this.srcSysName = srcSysName;
    }
    
    /**
     * 获取源表描述
     * 
     * @return 源表描述
     */
    public String getSrcTableDesc() {
        return this.srcTableDesc;
    }
     
    /**
     * 设置源表描述
     * 
     * @param srcTableDesc
     *          源表描述
     */
    public void setSrcTableDesc(String srcTableDesc) {
        this.srcTableDesc = srcTableDesc;
    }
    
    /**
     * 获取源表别名
     * 
     * @return 源表别名
     */
    public String getSrcTableAlias() {
        return this.srcTableAlias;
    }
     
    /**
     * 设置源表别名
     * 
     * @param srcTableAlias
     *          源表别名
     */
    public void setSrcTableAlias(String srcTableAlias) {
        this.srcTableAlias = srcTableAlias;
    }
    
    /**
     * 获取接口名
     * 
     * @return 接口名
     */
    public String getInterfaceName() {
        return this.interfaceName;
    }
     
    /**
     * 设置接口名
     * 
     * @param interfaceName
     *          接口名
     */
    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
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
     * 获取连接次序
     * 
     * @return 连接次序
     */
    public String getJoinOrder() {
        return this.joinOrder;
    }
     
    /**
     * 设置连接次序
     * 
     * @param joinOrder
     *          连接次序
     */
    public void setJoinOrder(String joinOrder) {
        this.joinOrder = joinOrder;
    }
    
    /**
     * 获取连接类型
     * 
     * @return 连接类型
     */
    public String getJoinType() {
        return this.joinType;
    }
     
    /**
     * 设置连接类型
     * 
     * @param joinType
     *          连接类型
     */
    public void setJoinType(String joinType) {
        this.joinType = joinType;
    }
    
    /**
     * 获取连接条件
     * 
     * @return 连接条件
     */
    public String getJoinCondition() {
        return this.joinCondition;
    }
     
    /**
     * 设置连接条件
     * 
     * @param joinCondition
     *          连接条件
     */
    public void setJoinCondition(String joinCondition) {
        this.joinCondition = joinCondition;
    }
    
    /**
     * 获取筛选条件
     * 
     * @return 筛选条件
     */
    public String getFilterCondition() {
        return this.filterCondition;
    }
     
    /**
     * 设置筛选条件
     * 
     * @param filterCondition
     *          筛选条件
     */
    public void setFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
    }
    
    /**
     * 获取增量抽取
     * 
     * @return 增量抽取
     */
    public String getIsIncremental() {
        return this.isIncremental;
    }
     
    /**
     * 设置增量抽取
     * 
     * @param isIncrement
     *          增量抽取
     */
    public void setIsIncremental(String isIncremental) {
        this.isIncremental = isIncremental;
    }
    /**
     * 获取增量抽取天数
     * 
     * @return 增量抽取天数
     */
    public String getIncExtractDays() {
        return this.incExtractDays;
    }
     
    /**
     * 设置增量抽取天数
     * 
     * @param isIncrement
     *          增量抽取天数
     */
    public void setIncExtractDays(String incExtractDays) {
        this.incExtractDays = incExtractDays;
    }
    /**
     * 获取备注
     * 
     * @return 备注
     */
    public String getComments() {
        return this.comments;
    }
     
    /**
     * 设置备注
     * 
     * @param comments
     *          备注
     */
    public void setComments(String comments) {
        this.comments = comments;
    }
    
    
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getLoadMode() {
        return this.loadMode;
    }
     
    /**
     * 设置
     * 
     * @param loadMode
     *          
     */
    public void setLoadMode(String loadMode) {
        this.loadMode = loadMode;
    }
    /**
     * 获取
     * 
     * @return 
     */
    public String getIsSingleSource() {
        return this.isSingleSource;
    }
     
    /**
     * 设置
     * 
     * @param isSingleSource
     *          
     */
    public void setIsSingleSource(String isSingleSource) {
        this.isSingleSource = isSingleSource;
    }
    
    
    /**
     * 获取
     * 
     * @return 
     */
    public Map<String ,List> getTgtTblLstMap() {
        return this.tgtTblLstMap;
    }
  
   
    /**
     * 设置
     * 
     * @param DcmMap
     *          
     */
    public void setTgtTblLstMap(Map<String ,List> tgtTblLstMap) {
    	this.tgtTblLstMap = tgtTblLstMap;
    }
    /**
     * 获取
     * 
     * @return 
     */
    public Map<String ,List> getTgtTblRuleMap() {
        return this.tgtTblRuleMap;
    }
     
    /**
     * 设置
     * 
     * @param DcmMap
     *          
     */
    public void setTgtTblRuleMap(Map<String ,List> tgtTblRuleMap) {
    	this.tgtTblRuleMap = tgtTblRuleMap;
    }
}