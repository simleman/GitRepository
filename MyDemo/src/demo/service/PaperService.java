package demo.service;

import demo.entity.Paper;
import demo.util.PageModel;
import demo.util.TqsVo;

/*
 * 试卷的service ，因hibernate的session较难管理，开始时使用的是spring配置，后来感觉手动配置更稳妥
 */
public interface PaperService {
	
	PageModel<TqsVo> getPageModel(int pageNo, int pageSize) throws Exception;

	PageModel<TqsVo> getPageModelByName(int pageNo, int pageSize, String name)throws Exception;

	Paper getOneById(Integer id) throws Exception;

	void save(Paper tq) throws Exception;

	void delete(String[] ids) throws Exception;

	void update(Paper tq) throws Exception;


}
