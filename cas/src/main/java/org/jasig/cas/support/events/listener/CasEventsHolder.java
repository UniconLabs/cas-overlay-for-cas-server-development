package org.jasig.cas.support.events.listener;

import org.jasig.cas.support.events.AbstractCasEvent;
import org.springframework.core.NamedThreadLocal;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dmitriy Kopylenko
 */
public abstract class CasEventsHolder {

    public static final String TGT_CREATED_EVENT = "tgt_created_event";

    private static final ThreadLocal<Map<String, AbstractCasEvent>> eventsData = new NamedThreadLocal<>("CAS events");

    public static void bindEventData(String key, AbstractCasEvent event) {
        Assert.notNull(event, "Event must not be null");
        Assert.notNull(key, "Key must not be null");
        Map<String, AbstractCasEvent> map = eventsData.get();
        // set ThreadLocal Map if none found
        if (map == null) {
            map = new HashMap<>();
            eventsData.set(map);
        }
        map.put(key, event);
    }

    public static <T> T getEventData(String key, Class<T> requiredType) {
        Map<String, AbstractCasEvent> map = eventsData.get();
        if (map == null) {
            return null;
        }

        AbstractCasEvent event = map.get(key);
        if((event != null) && AbstractCasEvent.class.isAssignableFrom(requiredType)) {
            return requiredType.cast(event);
        }
        return null;
    }

    public static void unbindEventData(String key) {
        Map<String, AbstractCasEvent> map = eventsData.get();
        if (map == null) {
            throw new IllegalStateException(
                    "No value for key [" + key + "] bound to thread [" + Thread.currentThread().getName() + "]");
        }
        Object value = map.remove(key);
        // Remove entire ThreadLocal if empty...
        if (map.isEmpty()) {
            eventsData.remove();
        }
        if (value == null) {
            throw new IllegalStateException(
                    "No value for key [" + key + "] bound to thread [" + Thread.currentThread().getName() + "]");
        }
    }

    public static void clear() {
        eventsData.remove();
    }
}
