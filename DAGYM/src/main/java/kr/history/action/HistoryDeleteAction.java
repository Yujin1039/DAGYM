package kr.history.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.history.dao.HistoryDAO;

public class HistoryDeleteAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		Integer user_num = (Integer) session.getAttribute("user_num");

		if (user_num == null) { // 로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}

		// 전송된 데이터 인코딩 타입 지정
		request.setCharacterEncoding("utf-8");

		// 삭제할 PT 신청번호와 회원번호 가져오기
		int sch_num = Integer.parseInt(request.getParameter("sch_num"));

		// HistoryDAO 객체 생성
		HistoryDAO historyDAO = HistoryDAO.getInstance();

		historyDAO.deleteHistory(sch_num, user_num);

		// JSP 경로 반환
		return "/WEB-INF/views/history/historyDeleteForm.jsp";
	}
}