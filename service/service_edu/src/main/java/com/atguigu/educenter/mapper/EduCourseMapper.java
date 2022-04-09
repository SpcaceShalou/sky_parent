package com.atguigu.educenter.mapper;

import com.atguigu.educenter.entity.EduCourse;
import com.atguigu.educenter.entity.vo.CoursePublishiVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2022-03-24
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    public CoursePublishiVo getPublishCourseInfo(String courseId);

}
