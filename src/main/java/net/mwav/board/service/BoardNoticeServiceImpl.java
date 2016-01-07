package net.mwav.board.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.mwav.board.dao.BoardDAO;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
 
@Service("boardNoticeService")
public class BoardNoticeServiceImpl implements BoardNoticeService{
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="boardDAO")
	private BoardDAO boardDAO;
	
	
	/*========================================등록========================================*/
	@Override
	public void insertBuForm(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("map = "+map);
		boardDAO.insertBuForm(map);
	}

	/*========================================보기========================================*/
	@Override
	public Map<String, Object> selectOneBuView(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		boardDAO.updateHitCnt(map);
		Set set = map.entrySet();
     	Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
				    Map.Entry entry = (Map.Entry)iterator.next();
				    System.out.println("key : "+entry.getKey()+", value : "+entry.getValue());
				}
		
		Map<String, Object> resultMap = boardDAO.selectOneBuView(map);
		return resultMap;
	}
	
	
	/*========================================수정========================================*/
	@Override
	public void updateBuform(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		boardDAO.updateBuform(map);		
	}
	
	/*========================================리스트(SelectOne, SelectList 순)========================================*/

	@Override
	public int selectNoticeOneGetTotalCount() {
		// TODO Auto-generated method stub
		return boardDAO.selectNoticeOneGetTotalCount();		
	}
	
	@Override
	public List<Map<String, Object>> selectListBuFrontList(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return boardDAO.selectListBuFrontList(map);
	}
	

	@Override
	public List<Map<String, Object>> selectListBuList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
		return boardDAO.selectListBuList(map);
	}

	/*========================================삭제========================================*/
	@Override
	public void deleteBuDelete(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		boardDAO.deleteBuDelete(map);		
	}
	

	
 
}