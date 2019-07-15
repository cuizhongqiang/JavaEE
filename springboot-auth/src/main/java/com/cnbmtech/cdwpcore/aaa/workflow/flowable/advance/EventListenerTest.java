package com.cnbmtech.cdwpcore.aaa.workflow.flowable.advance;

import org.flowable.engine.common.api.delegate.event.FlowableEvent;
import org.flowable.engine.common.api.delegate.event.FlowableEventListener;

public class EventListenerTest implements FlowableEventListener {

	  @Override
	  public void onEvent(final FlowableEvent event) {
		  System.out.println("Event received: " + event.getType());
	  }

	  @Override
	  public boolean isFailOnException() {
	    // The logic in the onEvent method of this listener is not critical, exceptions
	    // can be ignored if logging fails...
	    return false;
	  }
	}