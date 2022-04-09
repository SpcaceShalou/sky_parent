package com.atguigu.educenter.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.educenter.entity.EduSubject;
import com.atguigu.educenter.entity.excel.SubjectData;
import com.atguigu.educenter.entity.subject.OneSubject;
import com.atguigu.educenter.entity.subject.TwoSubject;
import com.atguigu.educenter.listener.SubExcelListener;
import com.atguigu.educenter.mapper.EduSubjectMapper;
import com.atguigu.educenter.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-03-23
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {
        try {
            //文件输入流
            InputStream in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class,new SubExcelListener(subjectService)).sheet().doRead();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {

        //先查一级分类的数据
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id","0");
        List<EduSubject> eduSubjectslist1 = baseMapper.selectList(wrapper);

        //二级数据
        QueryWrapper<EduSubject> wrappertwo = new QueryWrapper<>();
        wrappertwo.ne("parent_id",0);
        List<EduSubject> eduSubjectslist2 = baseMapper.selectList(wrappertwo);

        //创建封装数据类型list集合
        List<OneSubject> subjectlist = new ArrayList<>();

        //封装数据
        /*todo 可以把递归改为迭代*/
        for(int i = 0; i < eduSubjectslist1.size(); i++){
            EduSubject eduSubject1 = eduSubjectslist1.get(i);

            OneSubject oneSubject = new OneSubject();
//            oneSubject.setId(eduSubject.getId());
//            oneSubject.setTitle(eduSubject.getTitle());
            //利用beanutil 把edu的值给one
            BeanUtils.copyProperties(eduSubject1,oneSubject);
            subjectlist.add(oneSubject);

            List<TwoSubject> twoSubjectList = new ArrayList<>();
            for (int m = 0; m < eduSubjectslist2.size(); m++){
                EduSubject eduSubject2 = eduSubjectslist2.get(m);
                if(eduSubject2.getParentId().equals(eduSubject1.getId())){

                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(eduSubject2,twoSubject);
                    twoSubjectList.add(twoSubject);
                }
            }
            oneSubject.setChildren(twoSubjectList);
        }

        return subjectlist;
    }

}
