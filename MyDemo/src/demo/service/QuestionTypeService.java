package demo.service;

import java.util.List;

import demo.entity.Questiontype;
import demo.util.PageModel;

/*
 * 题型的service ，因hibernate的session较难管理，开始时使用的是spring配置，后来感觉手动配置更稳妥
 */
public interface QuestionTypeService {
	
	int getTqnumByqtid(Integer qtid) throws Exception;
	
	List listquestiontype(Integer paperid)throws Exception;
	
	List<Questiontype> list()throws Exception;
	
	PageModel<Questiontype> getPageModel(int pageNo, int pageSize)throws Exception;
	
	PageModel<Questiontype> getPageModelByType(int pageNo, int pageSize,String type)throws Exception;
	    
	Questiontype getOneById(Integer id)throws Exception;
	
	void delete(String[] ids)throws Exception;
	
	void save(Questiontype type)throws Exception;
	
	void update(Questiontype type)throws Exception;

}
