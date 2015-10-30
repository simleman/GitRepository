package demo.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import demo.dao.UserDao;
import demo.entity.Mydemo;
import demo.entity.Userclass;
import demo.util.HibernateSessionFactory;
/*
 * 小Demo，与试卷系统无关
 */
public class UserDaoImpl extends HibernateDaoSupport implements UserDao {

	public Mydemo getUserByUAndP(String username, String password)
			throws Exception {
		String hql = "from Mydemo m where m.name=? and m.password=?";
		Mydemo user = (Mydemo) HibernateSessionFactory.queryByHqlConds(hql,
				username, password);
		return user;
	}

	public List<Mydemo> list() throws Exception {
		String hql = "from Mydemo m left join fetch m.userclass order by m.id asc";
		List list = (List) HibernateSessionFactory.queryByHqlConds(hql);
		return list;
	}

	public Mydemo getOneById(Integer id) throws Exception {
		String hql = "from Mydemo m where m.id=?";
		Mydemo mydemo = (Mydemo) HibernateSessionFactory.queryByHqlConds(hql,
				id);
		return mydemo;
	}

	public void save(Mydemo mydemo) throws Exception {
		Session session = HibernateSessionFactory.getSession();
		session.save(mydemo);
	}

	public void delete(Mydemo mydemo) throws Exception {
		Session session = HibernateSessionFactory.getSession();
		session.delete(mydemo);
	}

	public void update(Mydemo mydemo) throws Exception {
		Session session = HibernateSessionFactory.getSession();
		session.update(mydemo);
	}

	public List<Userclass> classList() throws Exception {
		List list = (List) HibernateSessionFactory
				.queryByHqlConds("from Userclass");
		return list;
	}

	public Userclass getClassById(Integer id) throws Exception {
		String hql = "from Userclass u where u.id=?";
		Userclass u = (Userclass) HibernateSessionFactory.queryByHqlConds(hql,
				id);
		return u;
	}
}
