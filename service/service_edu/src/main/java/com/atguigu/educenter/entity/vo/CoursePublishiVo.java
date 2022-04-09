package com.atguigu.educenter.entity.vo;

import lombok.Data;

@Data
public class CoursePublishiVo {
    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;//只用于显示
}
