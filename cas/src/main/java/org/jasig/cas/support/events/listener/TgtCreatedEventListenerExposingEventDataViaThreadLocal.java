package org.jasig.cas.support.events.listener;

import org.jasig.cas.support.events.CasTicketGrantingTicketCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.webflow.core.collection.AttributeMap;
import org.springframework.webflow.execution.FlowExecutionListenerAdapter;
import org.springframework.webflow.execution.FlowSession;
import org.springframework.webflow.execution.RequestContext;

import static org.jasig.cas.support.events.listener.CasEventsHolder.TGT_CREATED_EVENT;

/**
 * @author Dmitriy Kopylenko
 */

@Component
public class TgtCreatedEventListenerExposingEventDataViaThreadLocal extends FlowExecutionListenerAdapter {

    @Override
    public void sessionEnded(RequestContext context, FlowSession session, String outcome, AttributeMap<?> output) {
        CasEventsHolder.clear();
    }

    @EventListener
    public void handle(CasTicketGrantingTicketCreatedEvent event) {
        CasEventsHolder.bindEventData(TGT_CREATED_EVENT, event);
    }
}
