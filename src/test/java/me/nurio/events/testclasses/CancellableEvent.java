package me.nurio.events.testclasses;

import lombok.*;
import me.nurio.events.handler.Cancellable;
import me.nurio.events.handler.Event;

@Data
@RequiredArgsConstructor
public class CancellableEvent extends Event implements Cancellable {
    private boolean cancelled;
    @NonNull private String name;
}