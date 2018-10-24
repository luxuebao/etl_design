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
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * (DW_TABLE)
 * 
 * @author 
 * @version 1.0.0 2017-09-25
 */
@Entity
@Table(name = "DW_TABLE")
public class DwTableEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 4468765868232476627L;
    
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
    @Column(name = "PHY_NAME", nullable = true, length = 64)
    private String phyName;
    
    /**  */
    @Column(name = "LOAD_MODE", nullable = true, length = 20)
    private String loadMode;
    
    /**  */
    @Column(name = "CLEAR_MODE", nullable = true, length = 20)
    private String clearMode;
    
    /**  */
    @Column(name = "KEEP_LOAD_DT", nullable = true, length = 20)
    private String keepLoadDt;
    
    /**  */
    @Column(name = "DO_AGGREGATE", nullable = true, length = 20)
    private String doAggregate;
    
    /**  */
    @Column(name = "SUBJECT_NAME", nullable = true, length = 64)
    private String subjectName;
    
    /**  */
    @Column(name = "IS_FACT", nullable = true, length = 20)
    private String isFact;
    
    /**  */
    @Column(name = "IS_SINGLE_SOURCE", nullable = true, length = 20)
    private String isSingleSource;
    
    /**  */
    @Column(name = "COMMENTS", nullable = true, length = 512)
    private String comments;
    
   
    private List<DwTableMappingEntity> dtmeList;
    private List<DwColumnsEntity> dcList;
    private List<DwColumnMappingEntity> dcmList;
    private Map<String ,List> dcmMap;
   
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
    public String getClearMode() {
        return this.clearMode;
    }
     
    /**
     * 设置
     * 
     * @param clearMode
     *          
     */
    public void setClearMode(String clearMode) {
        this.clearMode = clearMode;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getKeepLoadDt() {
        return this.keepLoadDt;
    }
     
    /**
     * 设置
     * 
     * @param keepLoadDt
     *          
     */
    public void setKeepLoadDt(String keepLoadDt) {
        this.keepLoadDt = keepLoadDt;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getDoAggregate() {
        return this.doAggregate;
    }
     
    /**
     * 设置
     * 
     * @param doAggregate
     *          
     */
    public void setDoAggregate(String doAggregate) {
        this.doAggregate = doAggregate;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getSubjectName() {
        return this.subjectName;
    }
     
    /**
     * 设置
     * 
     * @param subjectName
     *          
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    
    /**
     * 获取
     * 
     * @return 
     */
    public String getIsFact() {
        return this.isFact;
    }
     
    /**
     * 设置
     * 
     * @param isFact
     *          
     */
    public void setIsFact(String isFact) {
        this.isFact = isFact;
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
    public List<DwTableMappingEntity> getDtmeList() {
        return this.dtmeList;
    }
     
    /**
     * 设置
     * 
     * @param comments
     *          
     */
    public void setDtmeList(List<DwTableMappingEntity> dtmeList) {
    	this.dtmeList = dtmeList;
    }
    /**
     * 获取
     * 
     * @return 
     */
    public List<DwColumnsEntity> getDcList() {
        return this.dcList;
    }
     
    /**
     * 设置
     * 
     * @param comments
     *          
     */
    public void setDcList(List<DwColumnsEntity> dcList) {
    	this.dcList = dcList;
    }
     
    /**
     * 获取
     * 
     * @return 
     */
    public List<DwColumnMappingEntity> getDcmList() {
        return this.dcmList;
    }
     
    /**
     * 设置
     * 
     * @param DcmList
     *          
     */
    public void setDcmList(List<DwColumnMappingEntity> dcmList) {
    	this.dcmList = dcmList;
    }
 
    
    /**
     * 获取
     * 
     * @return 
     */
    public Map<String ,List> getDcmMap() {
        return this.dcmMap;
    }
     
    /**
     * 设置
     * 
     * @param DcmMap
     *          
     */
    public void setDcmMap(Map<String ,List> dcmMap) {
    	this.dcmMap = dcmMap;
    }
   
  
}