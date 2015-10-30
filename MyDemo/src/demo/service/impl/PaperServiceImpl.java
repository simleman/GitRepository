package demo.service.impl;

import org.hibernate.Session;

import demo.dao.PaperDao;
import demo.entity.Paper;
import demo.service.PaperService;
import demo.util.HibernateSessionFactory;
import demo.util.PageModel;
import demo.util.TqsVo;
import demo.util.TypeUtil;

/*
 * 试卷的service ，因hibernate的session较难管理，开始时使用的是spring配置，后来感觉手动配置更稳妥
 */
public class PaperServiceImpl implements PaperService {

	private PaperDao paperDao;

	public PaperDao getPaperDao() {
		return paperDao;
	}

	public void setPaperDao(PaperDao paperDao) {
		this.paperDao = paperDao;
	}

	@Override
	public PageModel<TqsVo> getPageModel(int pageNo, int pageSize)
			throws Exception {
		Session session = HibernateSessionFactory.getSession();

		PageModel<TqsVo> pageModel = new PageModel<TqsVo>();
		try {
			pageModel.setCount(paperDao.getCount());
			pageModel.setPageData(paperDao.list(pageNo, pageSize));
			session.flush();
			session.clear();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return pageModel;
	}

	@Override
	public PageModel<TqsVo> getPageModelByName(int pageNo, int pageSize,
			String name) throws Exception {
		
		PageModel<TqsVo> pageModel = new PageModel<TqsVo>();
		try {
			pageModel.setCount(paperDao.getCount());
			pageModel.setPageData(paperDao.listByName(pageNo, pageSize, name));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return pageModel;
	}

	@Override
	public Paper getOneById(Integer id) throws Exception {
		// TODO Auto-generated method stub

		Paper paper = paperDao.getOneById(id);
		return paper;
		
		
	

	}

	@Override
	public void save(Paper tq) throws Exception {
		Session session = HibernateSessionFactory.getSession();
		try {
			session.beginTransaction();
			paperDao.save(tq);
			session.flush();
			session.clear();
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
				Paper type = paperDao.getOneById(integerIds[i]);
				paperDao.delete(type);
				session.flush();
				session.clear();
				session.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				HibernateSessionFactory.closeSession();
			}
		}
	}

	@Override
	public void update(Paper tq) throws Exception {
		Session session = HibernateSessionFactory.getSession();
		try {
			session.beginTransaction();
			paperDao.update(tq);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateSessionFactory.closeSession();
		}

	}

}
