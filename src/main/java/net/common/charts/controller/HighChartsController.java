package net.common.charts.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.common.charts.service.HighChartsService;
import net.common.charts.vo.DataVO;
import net.common.charts.vo.SeriesTypeOneVO;
import net.common.charts.vo.SeriesTypeTwoVO;
import net.mwav.common.module.Common_Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;



@Controller
public class HighChartsController {

	@Autowired
	HighChartsService chartService;

	Map<String, Object> chartVar;

	@RequestMapping({"/", "/charts"})
	public String showCharts() {

		return "charts";
	}

	@RequestMapping({"/charts/linechart1.mwav"})
	@ResponseBody
	public DataVO showLineChart1() {
		return chartService.getLineChartData1();
	}

	@RequestMapping({"/charts/linechart2.mwav"})
	@ResponseBody
	public DataVO showLineChart2() {
		return chartService.getLineChartData2();
	}


	@RequestMapping({"/charts/highsofts/WeeklyUsers.mwav"})
	@ResponseBody
	public DataVO selectListWeeklyUsers(HttpServletRequest request) throws JsonProcessingException {
		chartVar = Common_Utils.typeToChar(request);
		chartVar.put("stPromoterId", chartVar.get("value"));
		ObjectMapper mapper = new ObjectMapper();
		DataVO vo = chartService.selectListWeeklyUsers(chartVar);
		List<SeriesTypeTwoVO> abc = vo.getSeries();
	
				 String json = new ObjectMapper().writeValueAsString(abc);
				 System.out.println("!+0"+json);
		
		return chartService.selectListWeeklyUsers(chartVar);
	}

	@RequestMapping({"/charts/highsofts/Top10PageList.mwav"})
	@ResponseBody
	public DataVO selectListTop10Page(HttpServletRequest request) {
		chartVar = Common_Utils.typeToChar(request);
		chartVar.put("stPromoterId", chartVar.get("value"));

		return chartService.selectListTop10Page(chartVar);
	}



	@RequestMapping({"/charts/highsofts/ClientScreenSize.mwav"})
	@ResponseBody
	public DataVO selectListClientScreenSize(HttpServletRequest request) {

		chartVar = Common_Utils.typeToChar(request);
		chartVar.put("stPromoterId", chartVar.get("value"));

		return chartService.selectListClientScreenSize(chartVar);
	}


	@RequestMapping({"/charts/highsofts/ClientBrowerInfo.mwav"})
	@ResponseBody
	public DataVO selectListClientBrowerInfo(HttpServletRequest request) {

		chartVar = Common_Utils.typeToChar(request);
		chartVar.put("stPromoterId", chartVar.get("value"));

		return chartService.selectListClientBrowerInfo(chartVar);
	}



}
