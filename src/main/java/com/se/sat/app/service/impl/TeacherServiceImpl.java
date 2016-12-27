package com.se.sat.app.service.impl;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.se.sat.app.entity.Teacher;
import com.se.sat.app.repository.TeacherRepo;
import com.se.sat.app.service.TeacherService;
@Service
public class TeacherServiceImpl implements TeacherService {	

	private static final Logger log = LoggerFactory.getLogger(TeacherServiceImpl.class);
	
	private TeacherRepo teacherRepo;
	
	@Autowired
	public TeacherServiceImpl(TeacherRepo teacherRepo) {
		this.teacherRepo = teacherRepo;
	}

	@Override
	public Teacher findTeacherInfo(Integer teacherId) {
		Teacher teacher = teacherRepo.findTeacher(teacherId);		
		return teacher;
	}

}
