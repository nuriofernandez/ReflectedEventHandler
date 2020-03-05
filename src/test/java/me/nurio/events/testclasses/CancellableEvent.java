package me.nurio.events.testclasses;

import lombok.*;
import me.nurio.events.Cancellable;
import me.nurio.events.Event;

@Data
@RequiredArgsConstructor
public class CancellableEvent extends Event implements Cancellable {
    private boolean cancelled;
    @NonNull private String name;
}