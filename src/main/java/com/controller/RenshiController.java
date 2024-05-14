
package com.controller;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import com.alibaba.fastjson.JSONObject;
import java.util.*;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.ContextLoader;
import javax.servlet.ServletContext;
import com.service.TokenService;
import com.utils.*;
import java.lang.reflect.InvocationTargetException;

import com.service.DictionaryService;
import org.apache.commons.lang3.StringUtils;
import com.annotation.IgnoreAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.*;
import com.entity.view.*;
import com.service.*;
import com.utils.PageUtils;
import com.utils.R;
import com.alibaba.fastjson.*;

/**
 * 人事
 * 后端接口
 * @author
 * @email
*/
@RestController
@Controller
@RequestMapping("/renshi")
public class RenshiController {
    private static final Logger logger = LoggerFactory.getLogger(RenshiController.class);

    @Autowired
    private RenshiService renshiService;


    @Autowired
    private TokenService tokenService;
    @Autowired
    private DictionaryService dictionaryService;

    //级联表service

    @Autowired
    private YuangongService yuangongService;


    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("page方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永不会进入");
        else if("员工".equals(role))
            params.put("yuangongId",request.getSession().getAttribute("userId"));
        else if("人事".equals(role))
            params.put("renshiId",request.getSession().getAttribute("userId"));
        if(params.get("orderBy")==null || params.get("orderBy")==""){
            params.put("orderBy","id");
        }
        PageUtils page = renshiService.queryPage(params);

        //字典表数据转换
        List<RenshiView> list =(List<RenshiView>)page.getList();
        for(RenshiView c:list){
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(c, request);
        }
        return R.ok().put("data", page);
    }

    /**
    * 后端详情
    */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("info方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        RenshiEntity renshi = renshiService.selectById(id);
        if(renshi !=null){
            //entity转view
            RenshiView view = new RenshiView();
            BeanUtils.copyProperties( renshi , view );//把实体数据重构到view中

            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
    * 后端保存
    */
    @RequestMapping("/save")
    public R save(@RequestBody RenshiEntity renshi, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,renshi:{}",this.getClass().getName(),renshi.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永远不会进入");

        Wrapper<RenshiEntity> queryWrapper = new EntityWrapper<RenshiEntity>()
            .eq("username", renshi.getUsername())
            .or()
            .eq("renshi_phone", renshi.getRenshiPhone())
            .or()
            .eq("renshi_id_number", renshi.getRenshiIdNumber())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        RenshiEntity renshiEntity = renshiService.selectOne(queryWrapper);
        if(renshiEntity==null){
            renshi.setInsertTime(new Date());
            renshi.setCreateTime(new Date());
            renshi.setPassword("123456");
            renshiService.insert(renshi);
            return R.ok();
        }else {
            return R.error(511,"账户或者人事手机号或者人事身份证号已经被使用");
        }
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody RenshiEntity renshi, HttpServletRequest request){
        logger.debug("update方法:,,Controller:{},,renshi:{}",this.getClass().getName(),renshi.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(false)
//            return R.error(511,"永远不会进入");
        //根据字段查询是否有相同数据
        Wrapper<RenshiEntity> queryWrapper = new EntityWrapper<RenshiEntity>()
            .notIn("id",renshi.getId())
            .andNew()
            .eq("username", renshi.getUsername())
            .or()
            .eq("renshi_phone", renshi.getRenshiPhone())
            .or()
            .eq("renshi_id_number", renshi.getRenshiIdNumber())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        RenshiEntity renshiEntity = renshiService.selectOne(queryWrapper);
        if("".equals(renshi.getRenshiPhoto()) || "null".equals(renshi.getRenshiPhoto())){
                renshi.setRenshiPhoto(null);
        }
        if(renshiEntity==null){
            renshiService.updateById(renshi);//根据id更新
            return R.ok();
        }else {
            return R.error(511,"账户或者人事手机号或者人事身份证号已经被使用");
        }
    }

    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        renshiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }


    /**
     * 批量上传
     */
    @RequestMapping("/batchInsert")
    public R save( String fileName){
        logger.debug("batchInsert方法:,,Controller:{},,fileName:{}",this.getClass().getName(),fileName);
        try {
            List<RenshiEntity> renshiList = new ArrayList<>();//上传的东西
            Map<String, List<String>> seachFields= new HashMap<>();//要查询的字段
            Date date = new Date();
            int lastIndexOf = fileName.lastIndexOf(".");
            if(lastIndexOf == -1){
                return R.error(511,"该文件没有后缀");
            }else{
                String suffix = fileName.substring(lastIndexOf);
                if(!".xls".equals(suffix)){
                    return R.error(511,"只支持后缀为xls的excel文件");
                }else{
                    URL resource = this.getClass().getClassLoader().getResource("static/upload/" + fileName);//获取文件路径
                    File file = new File(resource.getFile());
                    if(!file.exists()){
                        return R.error(511,"找不到上传文件，请联系管理员");
                    }else{
                        List<List<String>> dataList = PoiUtil.poiImport(file.getPath());//读取xls文件
                        dataList.remove(0);//删除第一行，因为第一行是提示
                        for(List<String> data:dataList){
                            //循环
                            RenshiEntity renshiEntity = new RenshiEntity();
//                            renshiEntity.setUsername(data.get(0));                    //账户 要改的
//                            //renshiEntity.setPassword("123456");//密码
//                            renshiEntity.setRenshiName(data.get(0));                    //人事姓名 要改的
//                            renshiEntity.setRenshiPhone(data.get(0));                    //人事手机号 要改的
//                            renshiEntity.setRenshiIdNumber(data.get(0));                    //人事身份证号 要改的
//                            renshiEntity.setRenshiPhoto("");//照片
//                            renshiEntity.setSexTypes(Integer.valueOf(data.get(0)));   //性别 要改的
//                            renshiEntity.setRenshiEmail(data.get(0));                    //电子邮箱 要改的
//                            renshiEntity.setInsertTime(date);//时间
//                            renshiEntity.setCreateTime(date);//时间
                            renshiList.add(renshiEntity);


                            //把要查询是否重复的字段放入map中
                                //账户
                                if(seachFields.containsKey("username")){
                                    List<String> username = seachFields.get("username");
                                    username.add(data.get(0));//要改的
                                }else{
                                    List<String> username = new ArrayList<>();
                                    username.add(data.get(0));//要改的
                                    seachFields.put("username",username);
                                }
                                //人事手机号
                                if(seachFields.containsKey("renshiPhone")){
                                    List<String> renshiPhone = seachFields.get("renshiPhone");
                                    renshiPhone.add(data.get(0));//要改的
                                }else{
                                    List<String> renshiPhone = new ArrayList<>();
                                    renshiPhone.add(data.get(0));//要改的
                                    seachFields.put("renshiPhone",renshiPhone);
                                }
                                //人事身份证号
                                if(seachFields.containsKey("renshiIdNumber")){
                                    List<String> renshiIdNumber = seachFields.get("renshiIdNumber");
                                    renshiIdNumber.add(data.get(0));//要改的
                                }else{
                                    List<String> renshiIdNumber = new ArrayList<>();
                                    renshiIdNumber.add(data.get(0));//要改的
                                    seachFields.put("renshiIdNumber",renshiIdNumber);
                                }
                        }

                        //查询是否重复
                         //账户
                        List<RenshiEntity> renshiEntities_username = renshiService.selectList(new EntityWrapper<RenshiEntity>().in("username", seachFields.get("username")));
                        if(renshiEntities_username.size() >0 ){
                            ArrayList<String> repeatFields = new ArrayList<>();
                            for(RenshiEntity s:renshiEntities_username){
                                repeatFields.add(s.getUsername());
                            }
                            return R.error(511,"数据库的该表中的 [账户] 字段已经存在 存在数据为:"+repeatFields.toString());
                        }
                         //人事手机号
                        List<RenshiEntity> renshiEntities_renshiPhone = renshiService.selectList(new EntityWrapper<RenshiEntity>().in("renshi_phone", seachFields.get("renshiPhone")));
                        if(renshiEntities_renshiPhone.size() >0 ){
                            ArrayList<String> repeatFields = new ArrayList<>();
                            for(RenshiEntity s:renshiEntities_renshiPhone){
                                repeatFields.add(s.getRenshiPhone());
                            }
                            return R.error(511,"数据库的该表中的 [人事手机号] 字段已经存在 存在数据为:"+repeatFields.toString());
                        }
                         //人事身份证号
                        List<RenshiEntity> renshiEntities_renshiIdNumber = renshiService.selectList(new EntityWrapper<RenshiEntity>().in("renshi_id_number", seachFields.get("renshiIdNumber")));
                        if(renshiEntities_renshiIdNumber.size() >0 ){
                            ArrayList<String> repeatFields = new ArrayList<>();
                            for(RenshiEntity s:renshiEntities_renshiIdNumber){
                                repeatFields.add(s.getRenshiIdNumber());
                            }
                            return R.error(511,"数据库的该表中的 [人事身份证号] 字段已经存在 存在数据为:"+repeatFields.toString());
                        }
                        renshiService.insertBatch(renshiList);
                        return R.ok();
                    }
                }
            }
        }catch (Exception e){
            return R.error(511,"批量插入数据异常，请联系管理员");
        }
    }


    /**
    * 登录
    */
    @IgnoreAuth
    @RequestMapping(value = "/login")
    public R login(String username, String password, String captcha, HttpServletRequest request) {
        RenshiEntity renshi = renshiService.selectOne(new EntityWrapper<RenshiEntity>().eq("username", username));
        if(renshi==null || !renshi.getPassword().equals(password))
            return R.error("账号或密码不正确");
        //  // 获取监听器中的字典表
        // ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
        // Map<String, Map<Integer, String>> dictionaryMap= (Map<String, Map<Integer, String>>) servletContext.getAttribute("dictionaryMap");
        // Map<Integer, String> role_types = dictionaryMap.get("role_types");
        // role_types.get(.getRoleTypes());
        String token = tokenService.generateToken(renshi.getId(),username, "renshi", "人事");
        R r = R.ok();
        r.put("token", token);
        r.put("role","人事");
        r.put("username",renshi.getRenshiName());
        r.put("tableName","renshi");
        r.put("userId",renshi.getId());
        return r;
    }

    /**
    * 注册
    */
    @IgnoreAuth
    @PostMapping(value = "/register")
    public R register(@RequestBody RenshiEntity renshi){
//    	ValidatorUtils.validateEntity(user);
        Wrapper<RenshiEntity> queryWrapper = new EntityWrapper<RenshiEntity>()
            .eq("username", renshi.getUsername())
            .or()
            .eq("renshi_phone", renshi.getRenshiPhone())
            .or()
            .eq("renshi_id_number", renshi.getRenshiIdNumber())
            ;
        RenshiEntity renshiEntity = renshiService.selectOne(queryWrapper);
        if(renshiEntity != null)
            return R.error("账户或者人事手机号或者人事身份证号已经被使用");
        renshi.setInsertTime(new Date());
        renshi.setCreateTime(new Date());
        renshiService.insert(renshi);
        return R.ok();
    }

    /**
     * 重置密码
     */
    @GetMapping(value = "/resetPassword")
    public R resetPassword(Integer  id){
        RenshiEntity renshi = new RenshiEntity();
        renshi.setPassword("123456");
        renshi.setId(id);
        renshi.setInsertTime(new Date());
        renshiService.updateById(renshi);
        return R.ok();
    }


    /**
     * 忘记密码
     */
    @IgnoreAuth
    @RequestMapping(value = "/resetPass")
    public R resetPass(String username, HttpServletRequest request) {
        RenshiEntity renshi = renshiService.selectOne(new EntityWrapper<RenshiEntity>().eq("username", username));
        if(renshi!=null){
            renshi.setPassword("123456");
            boolean b = renshiService.updateById(renshi);
            if(!b){
               return R.error();
            }
        }else{
           return R.error("账号不存在");
        }
        return R.ok();
    }


    /**
    * 获取用户的session用户信息
    */
    @RequestMapping("/session")
    public R getCurrRenshi(HttpServletRequest request){
        Integer id = (Integer)request.getSession().getAttribute("userId");
        RenshiEntity renshi = renshiService.selectById(id);
        if(renshi !=null){
            //entity转view
            RenshiView view = new RenshiView();
            BeanUtils.copyProperties( renshi , view );//把实体数据重构到view中

            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }
    }


    /**
    * 退出
    */
    @GetMapping(value = "logout")
    public R logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return R.ok("退出成功");
    }





}
