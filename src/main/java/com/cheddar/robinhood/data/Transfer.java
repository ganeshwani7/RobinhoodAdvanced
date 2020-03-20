package com.cheddar.robinhood.data;

import java.util.Date;

public class Transfer {
	private String id;
	private String ref_id;
	private String url;
	private String cancel;
	private String ach_relationship;
	private String account;
	private Float amount;
	private String direction;
	private String state;
	private Float fees;
	private String status_description;
	private Boolean scheduled;
	private Date expected_landing_date;
	private Float early_access_amount;
	private Date created_at;
	private Date updated_at;
	private String rhs_state;
	private Date expected_sweep_at;
	private Date expected_landing_datetime;
	private String investment_schedule_id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRef_id() {
		return ref_id;
	}

	public void setRef_id(String ref_id) {
		this.ref_id = ref_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCancel() {
		return cancel;
	}

	public void setCancel(String cancel) {
		this.cancel = cancel;
	}

	public String getAch_relationship() {
		return ach_relationship;
	}

	public void setAch_relationship(String ach_relationship) {
		this.ach_relationship = ach_relationship;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Float getFees() {
		return fees;
	}

	public void setFees(Float fees) {
		this.fees = fees;
	}

	public String getStatus_description() {
		return status_description;
	}

	public void setStatus_description(String status_description) {
		this.status_description = status_description;
	}

	public Boolean getScheduled() {
		return scheduled;
	}

	public void setScheduled(Boolean scheduled) {
		this.scheduled = scheduled;
	}

	public Date getExpected_landing_date() {
		return expected_landing_date;
	}

	public void setExpected_landing_date(Date expected_landing_date) {
		this.expected_landing_date = expected_landing_date;
	}

	public Float getEarly_access_amount() {
		return early_access_amount;
	}

	public void setEarly_access_amount(Float early_access_amount) {
		this.early_access_amount = early_access_amount;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	public String getRhs_state() {
		return rhs_state;
	}

	public void setRhs_state(String rhs_state) {
		this.rhs_state = rhs_state;
	}

	public Date getExpected_sweep_at() {
		return expected_sweep_at;
	}

	public void setExpected_sweep_at(Date expected_sweep_at) {
		this.expected_sweep_at = expected_sweep_at;
	}

	public Date getExpected_landing_datetime() {
		return expected_landing_datetime;
	}

	public void setExpected_landing_datetime(Date expected_landing_datetime) {
		this.expected_landing_datetime = expected_landing_datetime;
	}

	public String getInvestment_schedule_id() {
		return investment_schedule_id;
	}

	public void setInvestment_schedule_id(String investment_schedule_id) {
		this.investment_schedule_id = investment_schedule_id;
	}
}
