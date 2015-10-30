package demo.dao.impl;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import demo.dao.QuestionTypeDao;
import demo.entity.Questiontype;
import demo.util.HibernateSessionFactory;

/*
 * 题目类型Dao类的实现类
 */
public class QuestionTypeDaoImpl extends HibernateDaoSupport implements QuestionTypeDao {

	@Override
	public List<Questiontype> list(int pageNo, int pageSize) throws Exception {
		String hql = "from Questiontype qt order by qt.id asc";
		List list = HibernateSessionFactory.queryByPage4Extjs(hql, pageNo,
				pageSize);
		return list;
	}

	@Override
	public Questiontype getOneById(Integer id) throws Exception {
		Questiontype questiontype = (Questiontype) HibernateSessionFactory.findObjById(Questiontype.class, id);
		return questiontype;
	}

	@Override
	public void save(Questiontype type) throws Exception {
		Session session = HibernateSessionFactory.getSession();
		session.save(type);
	}

	@Override
	public void delete(Questiontype type) throws Exception {
		Session session = HibernateSessionFactory.getSession();
		session.delete(type);
	}

	@Override
	public void update(Questiontype type) throws Exception {
		Session session = HibernateSessionFactory.getSession();
		session.merge(type);
	}

	@Override
	public int getCount() throws Exception {
		String hql = "select count(*) from Questiontype qt order by qt.id asc";
		Long count = (Long) HibernateSessionFactory.queryByHqlConds(hql);
		return Integer.parseInt(count.toString());
	}

	@Override
	public List<Questiontype> listByType(int pageNo, int pageSize, String type)
			throws Exception {
		String hql = "from Questiontype qt where type like '%" + type
				+ "%' order by qt.id asc";
		List list = HibernateSessionFactory.queryByPage4Extjs(hql, pageNo,
				pageSize);
		return list;
	}

	@Override
	public int getCountByType(String type) throws Exception {
		String hql = "from Questiontype qt where type like '%" + type
				+ "%' order by qt.id asc";
		Long count = (Long) HibernateSessionFactory.queryByHqlConds(hql);
		return Integer.parseInt(count.toString());
	}
	
	//原生sql，嵌套子查询，查询试卷中的试题共含有哪些题目类型，用作预览页面
	@Override
	public List listquestiontype(Integer paperid) throws Exception{
		String sql = "select distinct qt.id from  testquestions tq  , questiontype qt where tq.questiontypeid=qt.id and tq.id in (select pt.id from paper_test pt where pt.paperid = "+paperid+")";
		Session session = HibernateSessionFactory.getSession();
		List list = session.createSQLQuery(sql).list();
		return list;
	}
	
	//原生sql ，查询该题目类型对应得题目个数，删除题型时判断
	@Override
	public int getTqnumByqtid(Integer qtid) throws Exception{
		String sql = "select count(tq.id)  from testquestions tq left join questiontype qt on tq.questiontypeid = qt.id where qt.id="+qtid;
		Session session = HibernateSessionFactory.getSession();
		BigInteger tqcount = (BigInteger) session.createSQLQuery(sql).uniqueResult();
		return Integer.parseInt(tqcount.toString());
	}
	
	

}
