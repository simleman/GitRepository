package demo.service.impl;

import java.util.List;

import org.hibernate.Session;

import demo.dao.StationDao;
import demo.entity.Station;
import demo.service.StationService;
import demo.util.HibernateSessionFactory;
import demo.util.PageModel;
import demo.util.TypeUtil;

/*
 * 岗位的service ，因hibernate的session较难管理，开始时使用的是spring配置，后来感觉手动配置更稳妥
 */
public class StationServiceImpl implements StationService {

	private StationDao sDao;

	public StationDao getsDao() {
		return sDao;
	}

	public void setsDao(StationDao sDao) {
		this.sDao = sDao;
	}

	@Override
	public PageModel<Station> getPageModel(int pageNo, int pageSize)
			throws Exception {
		PageModel<Station> pageModel = new PageModel<Station>();
		try {
			pageModel.setCount(sDao.getCount());
			pageModel.setPageData(sDao.list(pageNo, pageSize));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return pageModel;
	}

	@Override
	public PageModel<Station> getPageModelByName(int pageNo, int pageSize,
			String type) throws Exception {
		PageModel<Station> pageModel = new PageModel<Station>();
		try {
			pageModel.setCount(sDao.getCount());
			pageModel.setPageData(sDao.listByType(pageNo, pageSize, type));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateSessionFactory.closeSession();
		}
		return pageModel;
	}

	@Override
	public Station getOneById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return sDao.getOneById(id);
	}

	@Override
	public void save(Station station) throws Exception {
		Session session = HibernateSessionFactory.getSession();
		try {
			session.beginTransaction();
			sDao.save(station);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateSessionFactory.closeSession();
		}
	}

	public void delete(String[] ids) throws Exception {
		Integer[] integerIds = TypeUtil.StringArray2IntegerArray(ids);
		for (int i = 0; i < integerIds.length; i++) {
			Session session = HibernateSessionFactory.getSession();
			try {
				session.beginTransaction();
				Station type = sDao.getOneById(integerIds[i]);
				sDao.delete(type);
				session.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				HibernateSessionFactory.closeSession();
			}
		}
	}

	@Override
	public void update(Station station) throws Exception {
		Session session = HibernateSessionFactory.getSession();
		try {
			session.beginTransaction();
			sDao.update(station);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateSessionFactory.closeSession();
		}
	}

	@Override
	public List<Station> list() throws Exception {
		// TODO Auto-generated method stub
		List list = null;
		try {
			list = sDao.list(0, 0);
		} catch (Exception e) {
			e.printStackTrace();
		} /*finally {
			HibernateSessionFactory.closeSession();
		}*/
		return list;
	}

	@Override
	public int getTqnumBystid(Integer sid) throws Exception {
		// TODO Auto-generated method stub
		return sDao.getTqnumBystid(sid);
	}

}
