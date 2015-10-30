package demo.service.impl;

import java.util.List;

import org.hibernate.Session;

import demo.dao.QuestionTypeDao;
import demo.entity.Questiontype;
import demo.service.QuestionTypeService;
import demo.util.HibernateSessionFactory;
import demo.util.PageModel;
import demo.util.TypeUtil;
/*
 * 题型的service ，因hibernate的session较难管理，开始时使用的是spring配置，后来感觉手动配置更稳妥
 */
public class QuestionTypeServiceImpl implements QuestionTypeService {

	private QuestionTypeDao qDao;

	public QuestionTypeDao getqDao() {
		return qDao;
	}

	public void setqDao(QuestionTypeDao qDao) {
		this.qDao = qDao;
	}

	@Override
	public Questiontype getOneById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		Session session = HibernateSessionFactory.getSession();
			Questiontype questiontype = qDao.getOneById(id);
			session.flush();
		return questiontype;
	}

	@Override
	public void save(Questiontype type) {
		Session session = HibernateSessionFactory.getSession();
		try {
			session.beginTransaction();
			qDao.save(type);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateSessionFactory.closeSession();
		}
	}

	@Override
	public void update(Questiontype type) {
		Session session = HibernateSessionFactory.getSession();
		try {
			session.beginTransaction();
			qDao.update(type);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateSessionFactory.closeSession();
		}
	}

	@Override
	public PageModel<Questiontype> getPageModel(int pageNo, int pageSize) {
		PageModel<Questiontype> pageModel = new PageModel<Questiontype>();
		try {
			pageModel.setCount(qDao.getCount());
			pageModel.setPageData(qDao.list(pageNo, pageSize));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return pageModel;
	}

	@Override
	public PageModel<Questiontype> getPageModelByType(int pageNo, int pageSize,
			String type) throws Exception {
		PageModel<Questiontype> pageModel = new PageModel<Questiontype>();
		try {
			pageModel.setCount(qDao.getCount());
			pageModel.setPageData(qDao.listByType(pageNo, pageSize, type));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return pageModel;
	}

	public void delete(String[] ids) throws Exception {
		Integer[] integerIds = TypeUtil.StringArray2IntegerArray(ids);
		for (int i = 0; i < integerIds.length; i++) {
			Session session = HibernateSessionFactory.getSession();
			try {
				session.beginTransaction();
				Questiontype type = qDao.getOneById(integerIds[i]);
				qDao.delete(type);
				session.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				HibernateSessionFactory.closeSession();
			}
		}
	}

	@Override
	public List<Questiontype> list() throws Exception {
		// TODO Auto-generated method stub
		return qDao.list(0, 0);
	}

	@Override
	public List listquestiontype(Integer paperid) throws Exception {
		// TODO Auto-generated method stub

		List list = qDao.listquestiontype(paperid);

		return list;
	}

	@Override
	public int getTqnumByqtid(Integer qtid) throws Exception {
		// TODO Auto-generated method stub
		return qDao.getTqnumByqtid(qtid);
	}
}
