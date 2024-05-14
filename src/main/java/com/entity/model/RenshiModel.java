package com.entity.model;

import com.entity.RenshiEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;


/**
 * 人事
 * 接收传参的实体类
 *（实际开发中配合移动端接口开发手动去掉些没用的字段， 后端一般用entity就够用了）
 * 取自ModelAndView 的model名称
 */
public class RenshiModel implements Serializable {
    private static final long serialVersionUID = 1L;




    /**
     * 主键
     */
    private Integer id;


    /**
     * 账户
     */
    private String username;


    /**
     * 密码
     */
    private String password;


    /**
     * 人事姓名
     */
    private String renshiName;


    /**
     * 人事手机号
     */
    private String renshiPhone;


    /**
     * 人事身份证号
     */
    private String renshiIdNumber;


    /**
     * 人事头像
     */
    private String renshiPhoto;


    /**
     * 性别
     */
    private Integer sexTypes;


    /**
     * 电子邮箱
     */
    private String renshiEmail;


    /**
     * 添加时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    private Date insertTime;


    /**
     * 创建时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    private Date createTime;


    /**
	 * 获取：主键
	 */
    public Integer getId() {
        return id;
    }


    /**
	 * 设置：主键
	 */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
	 * 获取：账户
	 */
    public String getUsername() {
        return username;
    }


    /**
	 * 设置：账户
	 */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
	 * 获取：密码
	 */
    public String getPassword() {
        return password;
    }


    /**
	 * 设置：密码
	 */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
	 * 获取：人事姓名
	 */
    public String getRenshiName() {
        return renshiName;
    }


    /**
	 * 设置：人事姓名
	 */
    public void setRenshiName(String renshiName) {
        this.renshiName = renshiName;
    }
    /**
	 * 获取：人事手机号
	 */
    public String getRenshiPhone() {
        return renshiPhone;
    }


    /**
	 * 设置：人事手机号
	 */
    public void setRenshiPhone(String renshiPhone) {
        this.renshiPhone = renshiPhone;
    }
    /**
	 * 获取：人事身份证号
	 */
    public String getRenshiIdNumber() {
        return renshiIdNumber;
    }


    /**
	 * 设置：人事身份证号
	 */
    public void setRenshiIdNumber(String renshiIdNumber) {
        this.renshiIdNumber = renshiIdNumber;
    }
    /**
	 * 获取：人事头像
	 */
    public String getRenshiPhoto() {
        return renshiPhoto;
    }


    /**
	 * 设置：人事头像
	 */
    public void setRenshiPhoto(String renshiPhoto) {
        this.renshiPhoto = renshiPhoto;
    }
    /**
	 * 获取：性别
	 */
    public Integer getSexTypes() {
        return sexTypes;
    }


    /**
	 * 设置：性别
	 */
    public void setSexTypes(Integer sexTypes) {
        this.sexTypes = sexTypes;
    }
    /**
	 * 获取：电子邮箱
	 */
    public String getRenshiEmail() {
        return renshiEmail;
    }


    /**
	 * 设置：电子邮箱
	 */
    public void setRenshiEmail(String renshiEmail) {
        this.renshiEmail = renshiEmail;
    }
    /**
	 * 获取：添加时间
	 */
    public Date getInsertTime() {
        return insertTime;
    }


    /**
	 * 设置：添加时间
	 */
    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
    /**
	 * 获取：创建时间
	 */
    public Date getCreateTime() {
        return createTime;
    }


    /**
	 * 设置：创建时间
	 */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    }
