package com.se.sat.app.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.se.sat.app.dao.AbstractDao;
import com.se.sat.app.dao.TeacherDao;
import com.se.sat.app.entity.Student;
import com.se.sat.app.entity.Teacher;

@Repository("teacherDao")
@Transactional
public class TeacherDaoImpl extends AbstractDao<Integer, Teacher> implements TeacherDao {

	private static final Logger log = LoggerFactory.getLogger(TeacherDaoImpl.class);
	
	@Override
	public Teacher findById(int id) {
		Teacher teacher = getByKey(id);
		return teacher;
	}

	@Override
	public void saveTeacher(Teacher teacher) {
		persist(teacher);
	}
	
	@Override
	public void updateTeacher(Teacher teacher) {
		update(teacher);
	}

	@Override
	public void deleteById(int id) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("id", id));
		Teacher teacher = (Teacher) crit.uniqueResult();
		if(teacher != null)
			delete(teacher);
	}

	@Override
	public List<Teacher> findAllTeachers() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("firstname"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); //To avoid duplicates.
        List<Teacher> teachers = (List<Teacher>) criteria.list();
        return teachers;
	}

}
