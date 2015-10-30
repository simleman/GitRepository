package demo.service;

import java.util.List;

import demo.entity.Testquestions;
import demo.util.PageModel;
import demo.util.TqsVo;

/*
 * 题目的service ，因hibernate的session较难管理，开始时使用的是spring配置，后来感觉手动配置更稳妥
 */
public interface TestQuestionsService {
	
	int getPapernumBytqid(Integer tqid) throws Exception;
	
	List<TqsVo> getComBox(TqsVo tqsvo) throws Exception;
	
	List<TqsVo> list(TqsVo tqsvo) throws Exception;
	
	List RandTestquestions(TqsVo tqsvo,Integer questiontypeid,Integer limit )throws Exception;
	
	PageModel<TqsVo> getPageModel(int pageNo, int pageSize) throws Exception;

	PageModel<TqsVo> getPageModelByName(int pageNo, int pageSize, String name)throws Exception;

	Testquestions getOneById(Integer id) throws Exception;

	void save(Testquestions tq) throws Exception;

	void delete(String[] ids) throws Exception;

	void update(Testquestions tq) throws Exception;

}
