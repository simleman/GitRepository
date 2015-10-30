package demo.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import demo.dao.PaperDao;
import demo.entity.Paper;
import demo.util.HibernateSessionFactory;
import demo.util.TqsVo;

/*
 * 试卷Dao类的实现类
 */
public class PaperDaoImpl extends HibernateDaoSupport implements PaperDao {

	@Override
	public List<TqsVo> list(int pageNo, int pageSize) throws Exception {
		String hql = "select new demo.util.TqsVo(s.id,s.name,p.id,p.papername,p.makedate,p.memo)from Paper p left join p.station s order by p.id asc";
		List list = HibernateSessionFactory.queryByPage4Extjs(hql, pageNo,
				pageSize);
		return list;
	}

	@Override
	public List<TqsVo> listByName(int pageNo, int pageSize, String type)
			throws Exception {
		String hql = "select new demo.util.TqsVo(s.name,p.id,p.papername,p.makedate,p.memo) from Paper p left join p.station s where p.papername like '%"
				+ type + "'% order by p.id asc";
		List list = HibernateSessionFactory.queryByPage4Extjs(hql, pageNo,
				pageSize);
		return list;
	}

	@Override
	public int getCount() throws Exception {
		String hql = "select count(*) from Paper p left join p.station s order by p.id asc";
		Long count = (Long) HibernateSessionFactory.queryByHqlConds(hql);
		return Integer.parseInt(count.toString());
	}

	@Override
	public int getCountByName(String name) throws Exception {
		String hql = "from Paper p left join p.station s where p.papername like '%"
				+ name + "'% order by p.id asc";
		Long count = (Long) HibernateSessionFactory.queryByHqlConds(hql);
		return Integer.parseInt(count.toString());
	}

	@Override
	public Paper getOneById(Integer id) throws Exception {
		Paper paper = (Paper) HibernateSessionFactory.findObjById(Paper.class, id);
		return paper;
	}
	
	@Override
	public void save(Paper tq) throws Exception {
		Session session = HibernateSessionFactory.getSession();
		session.save(tq);

	}

	@Override
	public void delete(Paper tq) throws Exception {
		Session session = HibernateSessionFactory.getSession();
		session.delete(tq);

	}

	@Override
	public void update(Paper tq) throws Exception {
		Session session = HibernateSessionFactory.getSession();
		session.update(tq);

	}

}
