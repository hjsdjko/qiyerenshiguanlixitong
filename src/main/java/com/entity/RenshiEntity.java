package com.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.beanutils.BeanUtils;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 人事
 *
 * @author 
 * @email
 */
@TableName("renshi")
public class RenshiEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;


	public RenshiEntity() {

	}

	public RenshiEntity(T t) {
		try {
			BeanUtils.copyProperties(this, t);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    @TableField(value = "id")

    private Integer id;


    /**
     * 账户
     */
    @TableField(value = "username")

    private String username;


    /**
     * 密码
     */
    @TableField(value = "password")

    private String password;


    /**
     * 人事姓名
     */
    @TableField(value = "renshi_name")

    private String renshiName;


    /**
     * 人事手机号
     */
    @TableField(value = "renshi_phone")

    private String renshiPhone;


    /**
     * 人事身份证号
     */
    @TableField(value = "renshi_id_number")

    private String renshiIdNumber;


    /**
     * 人事头像
     */
    @TableField(value = "renshi_photo")

    private String renshiPhoto;


    /**
     * 性别
     */
    @TableField(value = "sex_types")

    private Integer sexTypes;


    /**
     * 电子邮箱
     */
    @TableField(value = "renshi_email")

    private String renshiEmail;


    /**
     * 添加时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    @TableField(value = "insert_time",fill = FieldFill.INSERT)

    private Date insertTime;


    /**
     * 创建时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    @TableField(value = "create_time",fill = FieldFill.INSERT)

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
	 * 设置：账户
	 */
    public String getUsername() {
        return username;
    }


    /**
	 * 获取：账户
	 */

    public void setUsername(String username) {
        this.username = username;
    }
    /**
	 * 设置：密码
	 */
    public String getPassword() {
        return password;
    }


    /**
	 * 获取：密码
	 */

    public void setPassword(String password) {
        this.password = password;
    }
    /**
	 * 设置：人事姓名
	 */
    public String getRenshiName() {
        return renshiName;
    }


    /**
	 * 获取：人事姓名
	 */

    public void setRenshiName(String renshiName) {
        this.renshiName = renshiName;
    }
    /**
	 * 设置：人事手机号
	 */
    public String getRenshiPhone() {
        return renshiPhone;
    }


    /**
	 * 获取：人事手机号
	 */

    public void setRenshiPhone(String renshiPhone) {
        this.renshiPhone = renshiPhone;
    }
    /**
	 * 设置：人事身份证号
	 */
    public String getRenshiIdNumber() {
        return renshiIdNumber;
    }


    /**
	 * 获取：人事身份证号
	 */

    public void setRenshiIdNumber(String renshiIdNumber) {
        this.renshiIdNumber = renshiIdNumber;
    }
    /**
	 * 设置：人事头像
	 */
    public String getRenshiPhoto() {
        return renshiPhoto;
    }


    /**
	 * 获取：人事头像
	 */

    public void setRenshiPhoto(String renshiPhoto) {
        this.renshiPhoto = renshiPhoto;
    }
    /**
	 * 设置：性别
	 */
    public Integer getSexTypes() {
        return sexTypes;
    }


    /**
	 * 获取：性别
	 */

    public void setSexTypes(Integer sexTypes) {
        this.sexTypes = sexTypes;
    }
    /**
	 * 设置：电子邮箱
	 */
    public String getRenshiEmail() {
        return renshiEmail;
    }


    /**
	 * 获取：电子邮箱
	 */

    public void setRenshiEmail(String renshiEmail) {
        this.renshiEmail = renshiEmail;
    }
    /**
	 * 设置：添加时间
	 */
    public Date getInsertTime() {
        return insertTime;
    }


    /**
	 * 获取：添加时间
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

    @Override
    public String toString() {
        return "Renshi{" +
            "id=" + id +
            ", username=" + username +
            ", password=" + password +
            ", renshiName=" + renshiName +
            ", renshiPhone=" + renshiPhone +
            ", renshiIdNumber=" + renshiIdNumber +
            ", renshiPhoto=" + renshiPhoto +
            ", sexTypes=" + sexTypes +
            ", renshiEmail=" + renshiEmail +
            ", insertTime=" + insertTime +
            ", createTime=" + createTime +
        "}";
    }
}
