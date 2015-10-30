package demo.dao;

import java.util.List;
import java.util.Map;

import demo.entity.Testquestions;
import demo.util.TqsVo;
/*
 * 题目的Dao类
 */
public interface TestQuestionsDao {
	
	int getPapernumBytqid(Integer tqid) throws Exception;
	/*List<TqsVo> list(int pageNo, int pageSize) throws Exception;*/
	List <TqsVo>getComBox(TqsVo tqvso) throws Exception;
    
	List<TqsVo> list(int pageNo, int pageSize,TqsVo tqvso) throws Exception;
	
	List<TqsVo> listByName(int pageNo, int pageSize, String type)
			throws Exception;

	int getCount() throws Exception;
	
	List<Map<String, Object>> RandTestquestions(TqsVo tqsvo,Integer questiontypeid,Integer limit )throws Exception;

	int getCountByName(String name) throws Exception;

	Testquestions getOneById(Integer id) throws Exception;

	void save(Testquestions tq) throws Exception;

	void delete(Testquestions tq) throws Exception;

	void update(Testquestions tq) throws Exception;

}
