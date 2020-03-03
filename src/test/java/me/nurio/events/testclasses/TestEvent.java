package me.nurio.events.testclasses;

import lombok.Getter;
import lombok.Setter;
import me.nurio.events.Event;

public class TestEvent extends Event {

    @Getter @Setter private String testName;

}