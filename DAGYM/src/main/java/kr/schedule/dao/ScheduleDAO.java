package kr.schedule.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.schedule.vo.ScheduleVO;
import kr.util.DBUtil;

public class ScheduleDAO {
    // 싱글턴 패턴
    private static ScheduleDAO instance = new ScheduleDAO();

    public static ScheduleDAO getInstance() {
        return instance;
    }
    public ScheduleDAO() {}

    // 스케줄 등록
    public void insertSchedule(ScheduleVO schedule) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = null;

        try {
            conn = DBUtil.getConnection();

            sql = "INSERT INTO schedule (sch_num, mem_num, sch_date, sch_time, sch_status) VALUES (schedule_seq.nextval, ?, ?, ?, ?)";

            // PreparedStatement 생성
            pstmt = conn.prepareStatement(sql);

            // ?에 데이터 바인딩
            pstmt.setInt(1, schedule.getMem_num());
            pstmt.setString(2, schedule.getSch_date());
            pstmt.setInt(3, schedule.getSch_time());
            pstmt.setInt(4, schedule.getSch_status());

            // SQL문 실행
            pstmt.executeUpdate();

        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(null, pstmt, conn);
        }
    }

    // 스케줄 중복 체크
    public boolean isDuplicateSchedule(String sch_date, int sch_time) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        boolean isDuplicate = false;

        try {
            conn = DBUtil.getConnection();

            sql = "SELECT COUNT(*) FROM schedule WHERE sch_date = ? AND sch_time = ?";

            // PreparedStatement 생성
            pstmt = conn.prepareStatement(sql);

            // ?에 데이터 바인딩
            pstmt.setString(1, sch_date);
            pstmt.setInt(2, sch_time);

            // SQL문 실행
            rs = pstmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                isDuplicate = true;
            }

        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }

        return isDuplicate;
    }
    
    
    public List<ScheduleVO> getAllSchedules() throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ScheduleVO> schedules = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM schedule"; // 적절한 SQL 쿼리로 변경해야 함

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ScheduleVO schedule = new ScheduleVO();
                // 스케줄 객체에 필요한 데이터를 설정
                schedule.setSch_num(rs.getInt("sch_num"));
                schedule.setMem_num(rs.getInt("mem_num"));
                schedule.setSch_date(rs.getString("sch_date"));
                schedule.setSch_time(rs.getInt("sch_time"));
                schedule.setSch_status(rs.getInt("sch_status"));
                schedules.add(schedule);
            }
        } catch (Exception e) {
        	throw new Exception(e);
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }

        return schedules;
    }
}
