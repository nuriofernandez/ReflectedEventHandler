package me.nurio.events;

import me.nurio.events.internal.eventmanager.ElasticEventManager;
import me.nurio.events.internal.eventmanager.StandardEventManager;

public interface EventManager extends StandardEventManager, ElasticEventManager {}