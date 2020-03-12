package me.nurio.events.testclasses;

import lombok.*;
import me.nurio.events.handler.EventCancellable;
import me.nurio.events.handler.Event;

@Data
@RequiredArgsConstructor
public class CancellableEvent extends Event implements EventCancellable {
    private boolean cancelled;
    @NonNull private String name;
}