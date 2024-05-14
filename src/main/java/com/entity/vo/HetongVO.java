package com.entity.vo;

import com.entity.HetongEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * 合同
 * 手机端接口返回实体辅助类
 * （主要作用去除一些不必要的字段）
 */
@TableName("hetong")
public class HetongVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */

    @TableField(value = "id")
    private Integer id;


    /**
     * 合同编号
     */

    @TableField(value = "hetong_uuid_number")
    private String hetongUuidNumber;


    /**
     * 合同文件
     */

    @TableField(value = "hetong_file")
    private String hetongFile;


    /**
     * 员工
     */

    @TableField(value = "yuangong_id")
    private Integer yuangongId;


    /**
     * 签订日期
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat

    @TableField(value = "qianding_time")
    private Date qiandingTime;


    /**
     * 合同生效时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat

    @TableField(value = "shengxiao_time")
    private Date shengxiaoTime;


    /**
     * 合同到期时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat

    @TableField(value = "daoqi_time")
    private Date daoqiTime;


    /**
     * 备注
     */

    @TableField(value = "beizhu_content")
    private String beizhuContent;


    /**
     * 上传日时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat

    @TableField(value = "insert_time")
    private Date insertTime;


    /**
     * 创建时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat

    @TableField(value = "create_time")
    private Date createTime;


    /**
	 * 设置：主键
	 */
    public Integer getId() {
        return id;
    }


    /**
	 * 获取：主键
	 */

    public void setId(Integer id) {
        this.id = id;
    }
    /**
	 * 设置：合同编号
	 */
    public String getHetongUuidNumber() {
        return hetongUuidNumber;
    }


    /**
	 * 获取：合同编号
	 */

    public void setHetongUuidNumber(String hetongUuidNumber) {
        this.hetongUuidNumber = hetongUuidNumber;
    }
    /**
	 * 设置：合同文件
	 */
    public String getHetongFile() {
        return hetongFile;
    }


    /**
	 * 获取：合同文件
	 */

    public void setHetongFile(String hetongFile) {
        this.hetongFile = hetongFile;
    }
    /**
	 * 设置：员工
	 */
    public Integer getYuangongId() {
        return yuangongId;
    }


    /**
	 * 获取：员工
	 */

    public void setYuangongId(Integer yuangongId) {
        this.yuangongId = yuangongId;
    }
    /**
	 * 设置：签订日期
	 */
    public Date getQiandingTime() {
        return qiandingTime;
    }


    /**
	 * 获取：签订日期
	 */

    public void setQiandingTime(Date qiandingTime) {
        this.qiandingTime = qiandingTime;
    }
    /**
	 * 设置：合同生效时间
	 */
    public Date getShengxiaoTime() {
        return shengxiaoTime;
    }


    /**
	 * 获取：合同生效时间
	 */

    public void setShengxiaoTime(Date shengxiaoTime) {
        this.shengxiaoTime = shengxiaoTime;
    }
    /**
	 * 设置：合同到期时间
	 */
    public Date getDaoqiTime() {
        return daoqiTime;
    }


    /**
	 * 获取：合同到期时间
	 */

    public void setDaoqiTime(Date daoqiTime) {
        this.daoqiTime = daoqiTime;
    }
    /**
	 * 设置：备注
	 */
    public String getBeizhuContent() {
        return beizhuContent;
    }


    /**
	 * 获取：备注
	 */

    public void setBeizhuContent(String beizhuContent) {
        this.beizhuContent = beizhuContent;
    }
    /**
	 * 设置：上传日时间
	 */
    public Date getInsertTime() {
        return insertTime;
    }


    /**
	 * 获取：上传日时间
	 */

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
    /**
	 * 设置：创建时间
	 */
    public Date getCreateTime() {
        return createTime;
    }


    /**
	 * 获取：创建时间
	 */

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
