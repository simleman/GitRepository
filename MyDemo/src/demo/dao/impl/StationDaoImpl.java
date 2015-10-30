package demo.dao.impl;

import java.math.BigInteger;

import java.util.List;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import demo.dao.StationDao;
import demo.entity.Station;
import demo.util.HibernateSessionFactory;

/*
 * 岗位Dao类的实现类
 */
public class StationDaoImpl extends HibernateDaoSupport implements StationDao {

	@Override
	public List<Station> list(int pageNo, int pageSize) throws Exception {
		String hql = "from Station s order by s.id asc";
		List list = HibernateSessionFactory.queryByPage4Extjs(hql, pageNo,
				pageSize);
		return list;
	}

	@Override
	public List<Station> listByType(int pageNo, int pageSize, String name)
			throws Exception {
		String hql = "from Station s where s.name like '%" + name
				+ "%' order by s.id asc";
		List list = HibernateSessionFactory.queryByPage4Extjs(hql, pageNo,
				pageSize);
		return list;
	}

	@Override
	public int getCount() throws Exception {
		String hql = "select count(*) from Station s order by s.id asc";
		Long count = (Long) HibernateSessionFactory.queryByHqlConds(hql);
		return Integer.parseInt(count.toString());
	}

	@Override
	public int getCountByName(String name) throws Exception {
		String hql = "from Station s where s.name like '%" + name
				+ "%' order by s.id asc";
		Long count = (Long) HibernateSessionFactory.queryByHqlConds(hql);
		return Integer.parseInt(count.toString());
	}

	@Override
	public Station getOneById(Integer id) throws Exception {
		String hql = "from Station s where s.id=?";
		Station st = (Station) HibernateSessionFactory.queryByHqlConds(hql, id);
		return st;
	}

	@Override
	public void save(Station station) throws Exception {
		Session session = HibernateSessionFactory.getSession();
		session.save(station);
	}

	@Override
	public void delete(Station station) throws Exception {
		Session session = HibernateSessionFactory.getSession();
		session.delete(station);
	}

	@Override
	public void update(Station station) throws Exception {
		Session session = HibernateSessionFactory.getSession();
		session.update(station);
	}
	
	//原生sql， 查询该岗位类型对应得题目个数，删除岗位时判断
	@Override
	public int getTqnumBystid(Integer sid) throws Exception{
		String sql = "select count(tq.id)  from testquestions tq left join station s on tq.stationid = s.id where s.id="+sid;
		Session session = HibernateSessionFactory.getSession();
		BigInteger tqcount = (BigInteger) session.createSQLQuery(sql).uniqueResult();
		return Integer.parseInt(tqcount.toString());
	}

}
