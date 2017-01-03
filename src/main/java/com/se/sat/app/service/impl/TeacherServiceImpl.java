package com.se.sat.app.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.se.sat.app.dao.TeacherDao;
import com.se.sat.app.entity.Teacher;
import com.se.sat.app.service.TeacherService;

@Service
public class TeacherServiceImpl implements TeacherService {

	private static final Logger log = LoggerFactory.getLogger(TeacherServiceImpl.class);

	private TeacherDao teacherDao;

	@Autowired
	public TeacherServiceImpl(TeacherDao teacherRepo) {
		this.teacherDao = teacherRepo;
	}

	@Override
	public boolean findTeacher(Integer id) {
		Teacher teacher = teacherDao.findById(id);
		if(teacher != null){
			return true;
		}
		else			
			return false;
	}

}
