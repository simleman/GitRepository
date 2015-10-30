package demo.service.impl;

import java.util.List;

import org.hibernate.Session;

import demo.dao.UserDao;
import demo.entity.Mydemo;
import demo.entity.Userclass;
import demo.service.UserService;
import demo.util.HibernateSessionFactory;

public class UserServiceImpl implements UserService {

	private UserDao userdao;

	public UserDao getUserdao() {
		return userdao;
	}

	public void setUserdao(UserDao userdao) {
		this.userdao = userdao;
	}

	@Override
	public java.util.List<Mydemo> list() throws Exception {
		return userdao.list();
	}

	@Override
	public Mydemo getOneById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return userdao.getOneById(id);
	}

	@Override
	public void save(Mydemo mydemo) throws Exception {
		Session session = HibernateSessionFactory.getSession();
		try {
			session.beginTransaction();
			userdao.save(mydemo);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateSessionFactory.closeSession();
		}

	}

	@Override
	public void delete(Mydemo mydemo) throws Exception {
		Session session = HibernateSessionFactory.getSession();
		try {
			session.beginTransaction();
			userdao.delete(mydemo);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateSessionFactory.closeSession();
		}
	}

	@Override
	public void update(Mydemo mydemo) throws Exception {
		Session session = HibernateSessionFactory.getSession();
		try {
			session.beginTransaction();
			userdao.update(mydemo);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateSessionFactory.closeSession();
		}
	}

	@Override
	public List<Userclass> classList() throws Exception {
		return userdao.classList();
	}

	@Override
	public Mydemo getUserByUAndP(String username, String password)
			throws Exception {
		// TODO Auto-generated method stub
		return userdao.getUserByUAndP(username, password);
	}

	@Override
	public Userclass getClassById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return userdao.getClassById(id);
	}

}
