package demo.dao;

import java.util.List;

import demo.entity.Station;
/*
 * 岗位的Dao类
 */
public interface StationDao {
	
	int getTqnumBystid(Integer sid) throws Exception;
	
	List<Station> list(int pageNo, int pageSize) throws Exception;

	List<Station> listByType(int pageNo, int pageSize, String name)
			throws Exception;

	int getCount() throws Exception;

	int getCountByName(String name) throws Exception;

	Station getOneById(Integer id) throws Exception;

	void save(Station station) throws Exception;

	void delete(Station station) throws Exception;

	void update(Station station) throws Exception;

}
