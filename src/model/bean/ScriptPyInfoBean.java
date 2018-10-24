package model.bean;

public class ScriptPyInfoBean {
	 /**  
     * 区域 名称  
     */   
    private String regionName;  
    /**  
     * 作业名称  
     */   
    private String jobName;  

    /**  
     * 任务代码  
     */   
    private String taskCode; 
    /**  
     * 区域路径
     */   
    private String phyName;   
    /**  
     * 加载模式
     */   
    private String loadMode;   
   
    /**  
     * sql语句
     */   
    private String sqlStr;   
   
    public String getRegionName() {   
        return regionName;   
    }   
   
    public void setRegionName(String regionName) {   
        this.regionName = regionName;   
    }   
    
    public String getJobName() {   
        return jobName;   
    }   
   
    public void setJobName(String jobName) {   
        this.jobName = jobName;   
    }   
    public String getTaskCode() {   
        return taskCode;   
    }   
   
    public void setTaskCode(String taskCode) {   
        this.taskCode = taskCode;   
    }   
    public String getPhyName() {   
        return phyName;   
    }   
   
    public void setPhyName(String phyName) {   
        this.phyName = phyName;   
    }  
    public String getSqlStr() {   
        return sqlStr;   
    }   
   
    public void setSqlStr(String sqlStr) {   
        this.sqlStr = sqlStr;   
    }   
    public String getLoadMode() {   
        return loadMode;   
    }   
   
    public void setLoadMode(String loadMode) {   
        this.loadMode = loadMode;   
    }   
}
