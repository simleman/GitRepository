package demo.service;

import java.util.List;

import demo.entity.Station;
import demo.util.PageModel;

/*
 * 岗位的service ，因hibernate的session较难管理，开始时使用的是spring配置，后来感觉手动配置更稳妥
 */
public interface StationService {
	
    int getTqnumBystid(Integer sid) throws Exception;
	
	List<Station> list() throws Exception;
	
	PageModel<Station> getPageModel(int pageNo, int pageSize)
			throws Exception;

	PageModel<Station> getPageModelByName(int pageNo, int pageSize,
			String type) throws Exception;

	Station getOneById(Integer id) throws Exception;

	void save(Station station) throws Exception;

	void delete(String[] ids) throws Exception;

	void update(Station station) throws Exception;

}
