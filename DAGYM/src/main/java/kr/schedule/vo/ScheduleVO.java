package kr.schedule.vo;

public class ScheduleVO {
	private int sch_num;
	private int mem_num;
	private String sch_date;
	private int sch_status;
	
	public int getSch_num() {
		return sch_num;
	}
	public void setSch_num(int sch_num) {
		this.sch_num = sch_num;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public String getSch_date() {
		return sch_date;
	}
	public void setSch_date(String sch_date) {
		this.sch_date = sch_date;
	}
	public int getSch_status() {
		return sch_status;
	}
	public void setSch_status(int sch_status) {
		this.sch_status = sch_status;
	}
	
	
}