package com.atguigu.educenter.service;

import com.atguigu.educenter.entity.EduCourse;
import com.atguigu.educenter.entity.vo.CourseInfoVo;
import com.atguigu.educenter.entity.vo.CoursePublishiVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-03-24
 */
public interface EduCourseService extends IService<EduCourse> {

    //添加课程基本信息
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishiVo publishCourseInfo(String id);

    void removeCourse(String courseId);
}
