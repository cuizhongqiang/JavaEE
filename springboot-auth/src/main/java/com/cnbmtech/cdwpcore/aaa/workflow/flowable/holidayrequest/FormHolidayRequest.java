/**
 * @Description: TODO
 * @author zhengangwu
 */
package com.cnbmtech.cdwpcore.aaa.workflow.flowable.holidayrequest;

import javax.validation.constraints.NotNull;

public class FormHolidayRequest {

	String employee;
	@NotNull
	Integer nrOfHolidays;
	@NotNull
	String description;
	public String getEmployee() {
		return employee;
	}
	public void setEmployee(String employee) {
		this.employee = employee;
	}
	public Integer getNrOfHolidays() {
		return nrOfHolidays;
	}
	public void setNrOfHolidays(Integer nrOfHolidays) {
		this.nrOfHolidays = nrOfHolidays;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
