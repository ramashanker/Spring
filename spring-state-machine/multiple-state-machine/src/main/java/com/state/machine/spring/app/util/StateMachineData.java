package com.state.machine.spring.app.util;

public class StateMachineData {
	String source;
	String target;
	public StateMachineData() {
	
	}

	String event;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

}
