package demo.dao;

import java.util.List;

import demo.entity.Paper;
import demo.util.TqsVo;

/*
 * 试卷的Dao类
 */
public interface PaperDao {
	
	List<TqsVo> list(int pageNo, int pageSize) throws Exception;

	List<TqsVo> listByName(int pageNo, int pageSize, String type)
			throws Exception;

	int getCount() throws Exception;

	int getCountByName(String name) throws Exception;

	Paper getOneById(Integer id) throws Exception;

	void save(Paper tq) throws Exception;

	void delete(Paper tq) throws Exception;

	void update(Paper tq) throws Exception;

	
	

}
