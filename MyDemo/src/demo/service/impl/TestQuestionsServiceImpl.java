package demo.service.impl;

import java.util.List;

import org.hibernate.Session;

import demo.dao.TestQuestionsDao;
import demo.entity.Paper;
import demo.entity.Testquestions;
import demo.service.TestQuestionsService;
import demo.util.HibernateSessionFactory;
import demo.util.PageModel;
import demo.util.TqsVo;
import demo.util.TypeUtil;
/*
 * 试题的service ，因hibernate的session较难管理，开始时使用的是spring配置，后来感觉手动配置更稳妥
 */
public class TestQuestionsServiceImpl implements TestQuestionsService {

	private TestQuestionsDao tqDao;

	public TestQuestionsDao getTqDao() {
		return tqDao;
	}

	public void setTqDao(TestQuestionsDao tqDao) {
		this.tqDao = tqDao;
	}

	@Override
	public PageModel<TqsVo> getPageModel(int pageNo, int pageSize)
			throws Exception {
		PageModel<TqsVo> pageModel = new PageModel<TqsVo>();
		try {
			pageModel.setCount(tqDao.getCount());
			pageModel.setPageData(tqDao.list(pageNo, pageSize, null));
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			HibernateSessionFactory.closeSession();
		}
		return pageModel;
	}

	@Override
	public PageModel<TqsVo> getPageModelByName(int pageNo, int pageSize,
			String name) throws Exception {
		PageModel<TqsVo> pageModel = new PageModel<TqsVo>();
		try {
			pageModel.setCount(tqDao.getCount());
			pageModel.setPageData(tqDao.listByName(pageNo, pageSize, name));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return pageModel;
	}

	@Override
	public Testquestions getOneById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		Testquestions testquestions =tqDao.getOneById(id);
		return testquestions;
	}

	@Override
	public void save(Testquestions tq) throws Exception {
		Session session = HibernateSessionFactory.getSession();
		try {
			session.beginTransaction();
			tqDao.save(tq);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateSessionFactory.closeSession();
		}

	}

	@Override
	public void delete(String[] ids) throws Exception {
		Integer[] integerIds = TypeUtil.StringArray2IntegerArray(ids);
		for (int i = 0; i < integerIds.length; i++) {
			Session session = HibernateSessionFactory.getSession();
			try {
				session.beginTransaction();
				Testquestions tq = tqDao.getOneById(integerIds[i]);
				tqDao.delete(tq);
				session.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				HibernateSessionFactory.closeSession();
			}
		}

	}

	@Override
	public void update(Testquestions tq) throws Exception {
		Session session = HibernateSessionFactory.getSession();
		try {
			session.beginTransaction();
			tqDao.update(tq);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateSessionFactory.closeSession();
		}

	}

	@Override
	public List<TqsVo> list(TqsVo tqvso) throws Exception {
		// TODO Auto-generated method stub
		return tqDao.list(0, 0, tqvso);
	}

	@Override
	public List<TqsVo> getComBox(TqsVo tqsvo) throws Exception {
		List<TqsVo> list = null;
		try {
			list = tqDao.getComBox(tqsvo);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return list;
	}

	@Override
	public List RandTestquestions(TqsVo tqsvo, Integer questiontypeid,
			Integer limit) throws Exception {
		// TODO Auto-generated method stub
		List list = null;
		try {
			list = tqDao.RandTestquestions(tqsvo, questiontypeid, limit);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return list;
	}

	@Override
	public int getPapernumBytqid(Integer tqid) throws Exception {
		// TODO Auto-generated method stub
		return tqDao.getPapernumBytqid(tqid);
	}

}
