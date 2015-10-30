package demo.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import demo.dao.TestQuestionsDao;
import demo.entity.Testquestions;
import demo.util.HibernateSessionFactory;
import demo.util.TqsVo;

/*
 * 题目Dao类的实现类
 */
public class TestQuestionsDaoImpl extends HibernateDaoSupport implements
		TestQuestionsDao {

	/*
	 * @Override public List<TqsVo> list(int pageNo, int pageSize) throws
	 * Exception { String hql =
	 * "select new demo.util.TqsVo(tq.id,tq.correct,tq.questionname,tq.questionoption,s.id,s.name,qt.id,qt.type) from Station s right join s.testquestionses tq left join tq.questiontype qt order by tq.id asc"
	 * ; List list = HibernateSessionFactory.queryByPage4Extjs(hql, pageNo,
	 * pageSize); return list; }
	 */
	@Override
	public List<TqsVo> listByName(int pageNo, int pageSize, String name)
			throws Exception {
		String hql = "select new demo.util.TqsVo(tq.id,tq.correct,tq.questionname,tq.questionoption,s.id,s.name,qt.id,qt.type,qt.isjudge) from Station s right join s.testquestionses tq left join tq.questiontype qt where tq.questionname like '%"
				+ name + "%' order by tq.id asc";
		List list = HibernateSessionFactory.queryByPage4Extjs(hql, pageNo,
				pageSize);
		return list;
	}

	@Override
	public int getCount() throws Exception {
		String hql = "select count(*) from Station s right join s.testquestionses tq left join tq.questiontype qt order by tq.id asc";
		Long count = (Long) HibernateSessionFactory.queryByHqlConds(hql);
		return Integer.parseInt(count.toString());
	}

	@Override
	public int getCountByName(String name) throws Exception {
		String hql = "select count(*) from Station s right join s.testquestionses tq left join tq.questiontype qt where tq.questionname like '%"
				+ name + "%' order by tq.id asc";
		Long count = (Long) HibernateSessionFactory.queryByHqlConds(hql);
		return Integer.parseInt(count.toString());
	}

	@Override
	public Testquestions getOneById(Integer id) throws Exception {
		Testquestions testquestions = (Testquestions) HibernateSessionFactory.findObjById(Testquestions.class, id);
		return testquestions;
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public List<Map<String, Object>> RandTestquestions(Integer
	 * station,Integer questiontypeid, Integer limit) throws Exception { final
	 * Integer stations = station; final Integer questiontypeids =
	 * questiontypeid; final Integer limits = limit;
	 * 
	 * List<Map<String, Object>> list = (List<Map<String, Object>>) this
	 * .getHibernateTemplate().execute(new HibernateCallback() {
	 * 
	 * @Override public Object doInHibernate(Session session)throws
	 * HibernateException, SQLException { String hql =
	 * "select id from testquestions where  stationid=:stations and questiontypeid =: questiontypeids order by rand() limit:limits"
	 * ; SQLQuery query = session.createSQLQuery(hql);
	 * 
	 * query.addScalar("id", Hibernate.INTEGER);
	 * 
	 * query.setParameter("stations", stations);
	 * query.setParameter("questiontypeids", questiontypeids);
	 * query.setParameter("limits", limits);
	 * 
	 * List<Map<String, Object>> list =
	 * query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	 * return list; } });
	 * 
	 * return list; }
	 */
	// 原生sql，随机生成中，随即查询题目的方法
	public List RandTestquestions(TqsVo tqVso, Integer questiontypeid,
			Integer limit) throws Exception {
		StringBuffer sql = new StringBuffer("select id from testquestions where questiontypeid = "+questiontypeid); 
		List<Object> params = new ArrayList<Object>();
		if (tqVso != null) {
			if (tqVso.getStationid() != null && tqVso.getStationid() != 0) {
				sql.append(" and stationid= ? ");
				params.add(tqVso.getStationid());
			}
			
			if(tqVso.getStationids() != null && tqVso.getStationids().length>0){
				sql.append(" and stationid in(");
				for(int i=0;i<tqVso.getStationids().length;i++){
					sql.append("?");
					if (i != tqVso.getStationids().length - 1) {
						sql.append(",");
					}
					params.add(Integer.parseInt(tqVso.getStationids()[i]));
				}
				sql.append(") ");
			}
		}
		sql.append(" order by rand() limit " + limit);		
		Session session = HibernateSessionFactory.getSession();
		SQLQuery query = session.createSQLQuery(sql.toString());
		if (params != null && params.size() > 0) {
			for (int i = 0; i < params.size(); i++) {
				query.setParameter(i, params.get(i));
			}
		}
		List list = query.list();
		return list;
	}
	
	@Override
	public void save(Testquestions tq) throws Exception {
		Session session = HibernateSessionFactory.getSession();
		session.save(tq);

	}

	@Override
	public void delete(Testquestions tq) throws Exception {
		Session session = HibernateSessionFactory.getSession();
		session.delete(tq);

	}

	@Override
	public void update(Testquestions tq) throws Exception {
		Session session = HibernateSessionFactory.getSession();
		session.update(tq);

	}

	@Override
	public List<TqsVo> list(int pageNo, int pageSize, TqsVo tqvso)
			throws Exception {
		StringBuffer hql = new StringBuffer(
				"select new demo.util.TqsVo(tq.id,tq.correct,tq.questionname,tq.questionoption,s.id,s.name,qt.id,qt.type,qt.isjudge) from Station s right join s.testquestionses tq left join tq.questiontype qt where 1=1");
		List<Object> params = new ArrayList<Object>();
		setConds(tqvso, hql, params);
		hql.append(" order by tq.id asc");
		List list = HibernateSessionFactory.queryByPage4Extjs(hql.toString(),
				pageNo, pageSize, params.toArray());
		return list;
	}

	//系统生成中根据岗位动态生成相应题目类型中题目个数的方法
	public List<TqsVo> getComBox(TqsVo tqvso) throws Exception {
		StringBuffer hql =  new StringBuffer("select new demo.util.TqsVo(qt.id,qt.type, count(tqs.id)) from Questiontype qt left join qt.testquestionses tqs left join tqs.station s where 1=1 ");
		List<Object> params = new ArrayList<Object>();
		setConds(tqvso, hql, params);
		hql.append(" group by qt.id");
		List<TqsVo> list = HibernateSessionFactory.queryByPage4Extjs(hql.toString(), 0, 0, params.toArray());
		return list;
	}

	//为条件查询赋值
	private void setConds(TqsVo tqvso, StringBuffer hql, List<Object> params) {
		if (tqvso != null) {
			if (tqvso.getStationid() != null && tqvso.getStationid() != 0) {
				hql.append(" and s.id = ? ");
				params.add(tqvso.getStationid());
			}
			
			if(tqvso.getStationids() != null && tqvso.getStationids().length>0){
				hql.append(" and s.id in(");
				for(int i=0;i<tqvso.getStationids().length;i++){
					hql.append("?");
					if (i != tqvso.getStationids().length - 1) {
						hql.append(",");
					}
					params.add(Integer.parseInt(tqvso.getStationids()[i]));
				}
				hql.append(") ");
			}
			
			if (tqvso.getQtid() != null && tqvso.getQtid() != 0) {
				hql.append("and qt.id = ? ");
				params.add(tqvso.getQtid());
			}

			if (tqvso.getQuestionname() != null
					&& !tqvso.getQuestionname().equals("")) {
				hql.append("and tq.questionname like ? ");
				params.add("%" + tqvso.getQuestionname() + "%");
			}

		}
	}
	
	//原生sql，查询该试题对应得试卷个数，删除试题时判断
	@Override
	public int getPapernumBytqid(Integer tqid) throws Exception{
	String sql = "select count(p.id)  from paper p left join paper_test pt on p.id= pt.paperid where pt.id="+tqid;
		Session session = HibernateSessionFactory.getSession();
		BigInteger tqcount = (BigInteger) session.createSQLQuery(sql).uniqueResult();
		return Integer.parseInt(tqcount.toString());
	}
		

}
