package com.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model.RaceDataLoader;
import com.pckeiba.analysis.UmagotoAnalysis;
import com.pckeiba.racedata.RaceDataSet;
import com.pckeiba.umagoto.UmagotoDataIndexSet;
import com.pckeiba.umagoto.UmagotoDataSet;
import com.pckeiba.umagoto.UmagotoDrunSet;

/**
 * Servlet implementation class DanceTableGraph
 */
@WebServlet("/DanceTableGraph")
public class DanceTableGraph extends HttpServlet {
	private RaceDataLoader loader;
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DanceTableGraph() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//URLパラメータからレースコードを取得する
		String raceCode = request.getParameter("racecode");
		loader.setRaceData(raceCode, 4);

		//各レース詳細オブジェクトを取得する
		RaceDataSet raceData = loader.getRaceDataSet();
		List<UmagotoDataSet> umaList = loader.getNowRaceDataList();
		List<Map<String,UmagotoDataSet>> umaMap = loader.getKakoRaceDataMapList();
		List<UmagotoDrunSet> drunList = loader.getDrunSortList();
		UmagotoAnalysis analysis = new UmagotoAnalysis(loader.getUmaLoad());
		List<UmagotoDataIndexSet> indexList = loader.getIndexList();

		//各レース詳細オブジェクトをフォワードする
		request.setAttribute("drunList", drunList);
		request.setAttribute("raceData", raceData);
		request.setAttribute("umaList", umaList);
		request.setAttribute("umaMap", umaMap);
		request.setAttribute("analysis", analysis);
		request.setAttribute("index", indexList);

		//フォワード
		RequestDispatcher di = request.getRequestDispatcher("/WEB-INF/jsp/danceTableGraph.jsp");
		di.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO 自動生成されたメソッド・スタブ
		super.init(config);
		loader = new RaceDataLoader();
	}

}
