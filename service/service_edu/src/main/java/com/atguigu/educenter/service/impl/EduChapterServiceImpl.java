package com.atguigu.educenter.service.impl;

import com.atguigu.educenter.entity.EduChapter;
import com.atguigu.educenter.entity.EduVideo;
import com.atguigu.educenter.entity.chapter.ChapterVo;
import com.atguigu.educenter.entity.chapter.VideoVo;
import com.atguigu.educenter.mapper.EduChapterMapper;
import com.atguigu.educenter.service.EduChapterService;
import com.atguigu.educenter.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-03-24
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //查询课程id里的章节
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        List<EduChapter> eduChapterslist = baseMapper.selectList(wrapper);

        QueryWrapper wrappervideo = new QueryWrapper();
        wrappervideo.eq("course_id",courseId);
        List<EduVideo> eduVideolist = videoService.list(wrappervideo);


        List<ChapterVo> finallList = new ArrayList<>();

        for(int i = 0;i < eduChapterslist.size();i++){
            EduChapter eduChapter = eduChapterslist.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            finallList.add(chapterVo);

            List<VideoVo> videoVoList = new ArrayList<>();
            for (int j = 0; j < eduVideolist.size(); j++) {
                EduVideo eduVideo = eduVideolist.get(j);
                if(eduVideo.getChapterId().equals(eduChapter.getId())){

                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    videoVoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVoList);
        }

        return finallList;
    }

    //在修改章节时删除的方法
    @Override
    public boolean deleteChapter(String chapterId) {
        //根据chapterid章节id 查询小节表，如果查询数据，不进行删除
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper);
        //判断
        if(count >0) {//查询出小节，不进行删除
            throw new GuliException(20001,"不能删除");
        } else { //不能查询数据，进行删除
            //删除章节  result影响行数
            int result = baseMapper.deleteById(chapterId);
            //成功  1>0   0>0
            return result>0;
        }
    }
    //在课程列表删除章节
    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
