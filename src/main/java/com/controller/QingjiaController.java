
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
 * 请假
 * 后端接口
 * @author
 * @email
*/
@RestController
@Controller
@RequestMapping("/qingjia")
public class QingjiaController {
    private static final Logger logger = LoggerFactory.getLogger(QingjiaController.class);

    @Autowired
    private QingjiaService qingjiaService;


    @Autowired
    private TokenService tokenService;
    @Autowired
    private DictionaryService dictionaryService;

    //级联表service
    @Autowired
    private YuangongService yuangongService;

    @Autowired
    private RenshiService renshiService;


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
        PageUtils page = qingjiaService.queryPage(params);

        //字典表数据转换
        List<QingjiaView> list =(List<QingjiaView>)page.getList();
        for(QingjiaView c:list){
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
        QingjiaEntity qingjia = qingjiaService.selectById(id);
        if(qingjia !=null){
            //entity转view
            QingjiaView view = new QingjiaView();
            BeanUtils.copyProperties( qingjia , view );//把实体数据重构到view中

                //级联表
                YuangongEntity yuangong = yuangongService.selectById(qingjia.getYuangongId());
                if(yuangong != null){
                    BeanUtils.copyProperties( yuangong , view ,new String[]{ "id", "createTime", "insertTime", "updateTime"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setYuangongId(yuangong.getId());
                }
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
    public R save(@RequestBody QingjiaEntity qingjia, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,qingjia:{}",this.getClass().getName(),qingjia.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永远不会进入");
        else if("员工".equals(role))
            qingjia.setYuangongId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));

        Wrapper<QingjiaEntity> queryWrapper = new EntityWrapper<QingjiaEntity>()
            .eq("qingjia_uuid_number", qingjia.getQingjiaUuidNumber())
            .eq("yuangong_id", qingjia.getYuangongId())
            .eq("qingjia_types", qingjia.getQingjiaTypes())
            .eq("qingjia_kaishi_time", new SimpleDateFormat("yyyy-MM-dd").format(qingjia.getQingjiaKaishiTime()))
            .eq("qingjia_jieshu_time", new SimpleDateFormat("yyyy-MM-dd").format(qingjia.getQingjiaJieshuTime()))
            .eq("qingjia_yesno_types", qingjia.getQingjiaYesnoTypes())
            .eq("qingjia_yesno_text", qingjia.getQingjiaYesnoText())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        QingjiaEntity qingjiaEntity = qingjiaService.selectOne(queryWrapper);
        if(qingjiaEntity==null){
            qingjia.setInsertTime(new Date());
            qingjia.setQingjiaYesnoTypes(1);
            qingjia.setCreateTime(new Date());
            qingjiaService.insert(qingjia);
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody QingjiaEntity qingjia, HttpServletRequest request){
        logger.debug("update方法:,,Controller:{},,qingjia:{}",this.getClass().getName(),qingjia.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(false)
//            return R.error(511,"永远不会进入");
//        else if("员工".equals(role))
//            qingjia.setYuangongId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));
        //根据字段查询是否有相同数据
        Wrapper<QingjiaEntity> queryWrapper = new EntityWrapper<QingjiaEntity>()
            .notIn("id",qingjia.getId())
            .andNew()
            .eq("qingjia_uuid_number", qingjia.getQingjiaUuidNumber())
            .eq("yuangong_id", qingjia.getYuangongId())
            .eq("qingjia_types", qingjia.getQingjiaTypes())
            .eq("insert_time", qingjia.getInsertTime())
            .eq("qingjia_kaishi_time", qingjia.getQingjiaKaishiTime())
            .eq("qingjia_jieshu_time", qingjia.getQingjiaJieshuTime())
            .eq("qingjia_yesno_types", qingjia.getQingjiaYesnoTypes())
            .eq("qingjia_yesno_text", qingjia.getQingjiaYesnoText())
            .eq("update_time", qingjia.getUpdateTime())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        QingjiaEntity qingjiaEntity = qingjiaService.selectOne(queryWrapper);
        qingjia.setUpdateTime(new Date());
        if(qingjiaEntity==null){
            qingjiaService.updateById(qingjia);//根据id更新
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        qingjiaService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }


    /**
     * 批量上传
     */
    @RequestMapping("/batchInsert")
    public R save( String fileName){
        logger.debug("batchInsert方法:,,Controller:{},,fileName:{}",this.getClass().getName(),fileName);
        try {
            List<QingjiaEntity> qingjiaList = new ArrayList<>();//上传的东西
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
                            QingjiaEntity qingjiaEntity = new QingjiaEntity();
//                            qingjiaEntity.setQingjiaUuidNumber(data.get(0));                    //请假唯一编号 要改的
//                            qingjiaEntity.setYuangongId(Integer.valueOf(data.get(0)));   //员工 要改的
//                            qingjiaEntity.setQingjiaTypes(Integer.valueOf(data.get(0)));   //请假类型 要改的
//                            qingjiaEntity.setInsertTime(date);//时间
//                            qingjiaEntity.setQingjiaContent("");//照片
//                            qingjiaEntity.setQingjiaKaishiTime(new Date(data.get(0)));          //请假开始日期 要改的
//                            qingjiaEntity.setQingjiaJieshuTime(new Date(data.get(0)));          //请假结束日期 要改的
//                            qingjiaEntity.setQingjiaYesnoTypes(Integer.valueOf(data.get(0)));   //审核状态 要改的
//                            qingjiaEntity.setQingjiaYesnoText(data.get(0));                    //审核意见 要改的
//                            qingjiaEntity.setUpdateTime(new Date(data.get(0)));          //审核时间 要改的
//                            qingjiaEntity.setCreateTime(date);//时间
                            qingjiaList.add(qingjiaEntity);


                            //把要查询是否重复的字段放入map中
                                //请假唯一编号
                                if(seachFields.containsKey("qingjiaUuidNumber")){
                                    List<String> qingjiaUuidNumber = seachFields.get("qingjiaUuidNumber");
                                    qingjiaUuidNumber.add(data.get(0));//要改的
                                }else{
                                    List<String> qingjiaUuidNumber = new ArrayList<>();
                                    qingjiaUuidNumber.add(data.get(0));//要改的
                                    seachFields.put("qingjiaUuidNumber",qingjiaUuidNumber);
                                }
                        }

                        //查询是否重复
                         //请假唯一编号
                        List<QingjiaEntity> qingjiaEntities_qingjiaUuidNumber = qingjiaService.selectList(new EntityWrapper<QingjiaEntity>().in("qingjia_uuid_number", seachFields.get("qingjiaUuidNumber")));
                        if(qingjiaEntities_qingjiaUuidNumber.size() >0 ){
                            ArrayList<String> repeatFields = new ArrayList<>();
                            for(QingjiaEntity s:qingjiaEntities_qingjiaUuidNumber){
                                repeatFields.add(s.getQingjiaUuidNumber());
                            }
                            return R.error(511,"数据库的该表中的 [请假唯一编号] 字段已经存在 存在数据为:"+repeatFields.toString());
                        }
                        qingjiaService.insertBatch(qingjiaList);
                        return R.ok();
                    }
                }
            }
        }catch (Exception e){
            return R.error(511,"批量插入数据异常，请联系管理员");
        }
    }






}
