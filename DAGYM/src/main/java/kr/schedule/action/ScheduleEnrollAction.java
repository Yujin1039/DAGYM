package kr.schedule.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import kr.controller.Action;
import kr.schedule.dao.ScheduleDAO;
import kr.schedule.vo.ScheduleVO;

public class ScheduleEnrollAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Integer userNum = (Integer) session.getAttribute("user_num");
        if (userNum == null) {
            return "redirect:/member/loginForm.do";
        }

        String schDate = request.getParameter("sch_date");
        String[] timePeriods = request.getParameterValues("time_period");

        if (schDate == null || schDate.isEmpty() || timePeriods == null || timePeriods.length == 0) {
            request.setAttribute("error", "잘못된 입력입니다.");
            return "/WEB-INF/views/schedule/ScheduleEnrollForm.jsp";
        }

        List<ScheduleVO> schedules = new ArrayList<>();
        ScheduleDAO scheduleDAO = ScheduleDAO.getInstance();
        boolean hasDuplicate = false;

        for (String period : timePeriods) {
            if (period.equals("morning")) {
                for (int hour = 9; hour <= 11; hour++) {
                    if (scheduleDAO.isDuplicateSchedule(schDate, hour)) {
                        hasDuplicate = true;
                    } else {
                        schedules.add(createSchedule(userNum, schDate, hour));
                    }
                }
            } else if (period.equals("afternoon")) {
                for (int hour = 13; hour <= 20; hour++) {
                    if (scheduleDAO.isDuplicateSchedule(schDate, hour)) {
                        hasDuplicate = true;
                    } else {
                        schedules.add(createSchedule(userNum, schDate, hour));
                    }
                }
            }
        }

        try {
            for (ScheduleVO schedule : schedules) {
                scheduleDAO.insertSchedule(schedule);
            }
            if (hasDuplicate) {
                request.setAttribute("message", "스케줄이 성공적으로 등록되었지만, 일부 시간대는 이미 등록된 상태입니다.");
            } else {
                request.setAttribute("message", "스케줄이 성공적으로 등록되었습니다.");
            }
        } catch (Exception e) {
            request.setAttribute("error", "스케줄 등록 중 오류가 발생했습니다.");
        }

        return "/WEB-INF/views/schedule/scheduleEnrollForm.jsp";
    }

    private ScheduleVO createSchedule(int userNum, String schDate, int hour) {
        ScheduleVO schedule = new ScheduleVO();
        schedule.setMem_num(userNum);
        schedule.setSch_date(schDate);
        schedule.setSch_time(hour);
        schedule.setSch_status(0); // 스케줄 상태 초기화 (예: 신청 가능)
        return schedule;
    }
}