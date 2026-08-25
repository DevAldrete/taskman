package com.dev;

import java.time.Instant;

public class Task {
    private String title;
    private String department;
    private boolean done;
    private Urgency urgency;
    private Instant due_date;
    private Instant created_at;
    private Instant updated_at;

    public Task(String title, String department, Instant due_date) {
        this.title = title,
        this.department = department;
        this.urgency = Urgency.LOW;
        this.due_date = due_date;
        this.created_at = Instant.now();
        this.updated_at = Instant.now();
    }

    public Task(String title, String department, Urgency urgency, Instant due_date) {
        this.title = title,
        this.department = department;
        this.urgency = urgency;
        this.due_date = due_date;
        this.created_at = Instant.now();
        this.updated_at = Instant.now();
    }

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public boolean isDone() {
		return done;
	}
	public void setDone(boolean done) {
		this.done = done;
	}
	public Urgency getUrgency() {
		return urgency;
	}
	public void setUrgency(Urgency urgency) {
		this.urgency = urgency;
	}
	public Instant getDue_date() {
		return due_date;
	}
	public void setDue_date(Instant due_date) {
		this.due_date = due_date;
	}
	public Instant getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Instant created_at) {
		this.created_at = created_at;
	}
	public Instant getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Instant updated_at) {
		this.updated_at = updated_at;
	}
};
