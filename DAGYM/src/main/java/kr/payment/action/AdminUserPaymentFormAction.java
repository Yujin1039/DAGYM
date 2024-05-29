package kr.payment.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.payment.dao.PaymentDAO;
import kr.payment.vo.PaymentVO;
import kr.util.PagingUtil;

public class AdminUserPaymentFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		//로그인도 되어 있어야 하고 관리자만 들어올 수 있게 해야 한다.
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth != 9) {//관리자로 로그인하지 않은 경우
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) pageNum = "1";
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		//전송된 데이터 반환
		int mem_num = Integer.parseInt(request.getParameter("mem_num"));
		//int mem_name = Integer.parseInt(request.getParameter("mem_name"));
		PaymentDAO dao = PaymentDAO.getInstance();
		int count = dao.getPayMemCount(mem_num);
		int remain = dao.remainpayment(mem_num);
		String mem_name = dao.getMemberName(mem_num);
		//페이지 처리
		PagingUtil page = new PagingUtil(keyfield,keyword,Integer.parseInt(pageNum),count,20,10,"adminUserPaymentForm.do");
		
		List<PaymentVO> list = null;
		if(count > 0) {
			list = dao.getPayMemList(mem_num,page.getStartRow(),page.getEndRow());
			
		}
		
		
		request.setAttribute("mem_name", mem_name);
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		request.setAttribute("remain", remain);
		
		//JSP 경로 반환
		return "/WEB-INF/views/payment/detailPaymentUserForm.jsp";
	}

}