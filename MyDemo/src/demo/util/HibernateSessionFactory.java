package demo.util;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

/**
 * Configures and provides access to Hibernate sessions, tied to the current
 * thread of execution. Follows the Thread Local Session pattern, see
 * {@link http://hibernate.org/42.html }.
 */
public class HibernateSessionFactory {

	/**
	 * Location of hibernate.cfg.xml file. Location should be on the classpath
	 * as Hibernate uses #resourceAsStream style lookup for its configuration
	 * file. The default classpath location of the hibernate config file is in
	 * the default package. Use #setConfigFile() to update the location of the
	 * configuration file for the current session.
	 */
	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
	private static org.hibernate.SessionFactory sessionFactory;

	private static Configuration configuration = new Configuration();
	private static String CONFIG_FILE_LOCATION = "/hibernate.cfg.xml";
	private static String configFile = CONFIG_FILE_LOCATION;

	static {
		try {
			configuration.configure(configFile);
			sessionFactory = configuration.buildSessionFactory();
		} catch (Exception e) {
			System.err.println("%%%% Error Creating SessionFactory %%%%");
			e.printStackTrace();
		}
	}

	private HibernateSessionFactory() {
	}

	/**
	 * Returns the ThreadLocal Session instance. Lazy initialize the
	 * <code>SessionFactory</code> if needed.
	 * 
	 * @return Session
	 * @throws HibernateException
	 */
	public static Session getSession() throws HibernateException {
		Session session = (Session) threadLocal.get();

		if (session == null || !session.isOpen()) {
			if (sessionFactory == null) {
				rebuildSessionFactory();
			}
			session = (sessionFactory != null) ? sessionFactory.openSession()
					: null;
			threadLocal.set(session);
		}

		return session;
	}

	/**
	 * Rebuild hibernate session factory
	 * 
	 */
	public static void rebuildSessionFactory() {
		try {
			configuration.configure(configFile);
			sessionFactory = configuration.buildSessionFactory();
		} catch (Exception e) {
			System.err.println("%%%% Error Creating SessionFactory %%%%");
			e.printStackTrace();
		}
	}

	/**
	 * Close the single hibernate session instance.
	 * 
	 * @throws HibernateException
	 */
	public static void closeSession() throws HibernateException {
		Session session = (Session) threadLocal.get();
		threadLocal.set(null);

		if (session != null) {
			session.close();
		}
	}

	/**
	 * return session factory
	 * 
	 */
	public static org.hibernate.SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * return session factory
	 * 
	 * session factory will be rebuilded in the next call
	 */
	public static void setConfigFile(String configFile) {
		HibernateSessionFactory.configFile = configFile;
		sessionFactory = null;
	}

	/**
	 * return hibernate configuration
	 * 
	 */
	public static Configuration getConfiguration() {
		return configuration;
	}

	//获得单条数据的通用方法
	public static Object queryByHqlConds(String hql, Object... params) {
		Query query = HibernateSessionFactory.getSession().createQuery(hql);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		return query.uniqueResult();
	}

	/*
	 * public static List queryListByHqlConds(String hql, Object... params) {
	 * Query query = HibernateSessionFactory.getSession().createQuery(hql); if
	 * (params != null && params.length > 0) { for (int i = 0; i <
	 * params.length; i++) { query.setParameter(i, params[i]); } } return
	 * query.list(); }
	 */
	
	//获得单条数据的通用方法
	public static Object findObjById(Class clazz, int id) {
		Object obj = HibernateSessionFactory.getSession().get(clazz, id);
		return obj;
	}

	//分页时的方法
	public static List getPageData(String hql, final int pageNo,
			final int pageSize, Object... params) {
		Query query = HibernateSessionFactory.getSession().createQuery(hql);
		if (pageNo >= 1 && pageSize != 0) {
			query.setFirstResult((pageNo - 1) * pageSize);
			query.setMaxResults(pageSize);
		}
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		List list = query.list();
		return list;
	}

	//为适应extjs分页使用的方法
	public static List queryByPage4Extjs(final String hql, final int start,
			final int limit, final Object... objects) throws Exception {
		int curpage = 0;
		if (limit != 0) {
			curpage = start / limit + 1;
		}
		return getPageData(hql, curpage, limit, objects);
	}

	//获得总页数的方法
	public long getAllPageCount(long count, int pageSize) {
		return count % pageSize == 0 ? count / pageSize
				: (count / pageSize + 1);
	}
}
