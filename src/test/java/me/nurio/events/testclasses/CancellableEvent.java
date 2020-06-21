package me.nurio.events.testclasses;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.nurio.events.handler.Event;
import me.nurio.events.handler.EventCancellable;

@Data
@RequiredArgsConstructor
public class CancellableEvent extends Event implements EventCancellable {
    private boolean cancelled;
    @NonNull private String name;
}