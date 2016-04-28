package org.jasig.cas.support.events.listener;

import org.jasig.cas.support.events.CasTicketGrantingTicketCreatedEvent;
import org.springframework.stereotype.Component;
import org.springframework.webflow.execution.RequestContext;

import static org.jasig.cas.support.events.listener.CasEventsHolder.TGT_CREATED_EVENT;

/**
 * @author Dmitriy Kopylenko
 */
@Component
public class AuthnEventDataCheckingAction {

    public String checkAuthnEventData(RequestContext requestContext) {
        CasTicketGrantingTicketCreatedEvent event =
                CasEventsHolder.getEventData(TGT_CREATED_EVENT, CasTicketGrantingTicketCreatedEvent.class);

        CasEventsHolder.unbindEventData(TGT_CREATED_EVENT);

        return "success";
    }
}
