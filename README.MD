# Java Reflection Event System

Event system that allows you to create an event driven architecture in a easy and beautiful way.

# Maven

```xml
<repository>
    <id>nurio-repo</id>
    <url>https://nurio.me/repo/mvn/</url>
</repository>
```

```xml
<dependency>
    <groupId>me.nurio</groupId>
    <artifactId>events</artifactId>
    <version>0.0.3</version>
    <scope>compile</scope>
</dependency>
```

# Usage example

For this example, some person is going to send a message, then our event management will process the sended message. What we want is the sended messages always **start with upper case letter** and **end with a final dot**.

Then when the message sent is:
```
hi this is random message
```
The message will be converted to:
```
Hi this is random message.
```

## Register an EventManager instance for your application.

EventManager records the listening of events and manages event calls. You will need an instance for your application. You can have more than one instance, but the event priority will not work.

> Note: That Lombok annotation creates getEventManager() method.
```java
public class Main {

    @Getter
    private static EventManager eventManager = new EventManager();

    public static void main(String[] args) {
        System.out.println("Hello world!");
    }

}
```

## Creating basic event

An event is a class that manages state of actions, in this case the sended message. For that, we are are creating the class `MessageSendEvent`. With the `sender` and `message` fields.
> Note: These lombok annotations will automatically create *Getters*/*Setters* & *Constuctor*.
```java
@Data
@AllArgsConstructor
class MessageSendEvent extends Event {
    public String senderName;
    public String message;
}
```

## Calling the event

Then, where we are performing the send message operation we are placing this new code:
```java
// EventManager instance for your whole application.
private EventManager eventManager = Main.getEventManager();

// Method that manages the message sending operation.
public void sendMessage(String username, String message) {
    // Creating MessageSendEvent event instance and setting the senderName & message.
    MessageSendEvent msgEvent = new MessageSendEvent(username, message);
    // Calling the events related to the MessageSenderEvent.
    eventManager.callEvent(connectEvent);
    // Printing the message of the MessageSendEvent instance.
    System.out.println(msgEvent.getMessage());
}
```

## Create event listener

To create an event listener we just need to create a class implementing `EventListener`.

Then, we need to register a method to handle the event, to do that, you have to create a method for that. It doesn't matter which name you choose, but you should use the `@EventHandler` annotation. Also, you have to specify a parameter with the event that you want to handle.

You should have something like this: (You can register as many events as you want)
```java
public class MessageListener implements EventListener {

    @EventHandler
    public void appendDotOnMessage(MessageSendEvent event) {
        // If the message actually ends with a dot, stop the flow performing a return.
        if(event.getMessage().endsWith(".")) return;

        // Add an end dot to sentences that don't have it.
        event.setMessage( event.getMessage() + "." );
    }

    @EventHandler
    public void capitalizeOnMessage(MessageSendEvent event) {
        // If the message was null or empty, stop the flow performing a return.
        if(event.getMessage() == null || event.getMessage().isEmpty()) return;
        String message = event.getMessage();

        // If the message already starts with a capital character
        // stop the flow performing a return.
        if(Character.isUpperCase( message.charAt(0) )) return;

        // Capitalize first character of sentences that doesn't has it already.
        event.setMessage( message.substring(0,1).toUpperCase() + message.substring(1) );
    }

}
```

Once you have created that class you have to register it, doesn't matter where you place teh register, but it has to be registered before calling the `EventManager#callEvent` or the `MessageListener` events won't be called.

```java
public class Main {

    @Getter
    private static EventManager eventManager = new EventManager();

    public static void main(String[] args) {
        // Register MessageListener class has event lister.
        eventManager.registerEvents(new MessageListener());
    }

}
```

With that, the event listeners methods at `MessageListener` will be fired every time that `EventManger.callEvent()` gets called with a `MessageSendEvent` event instance.

Now when we use the `sendMessage` method the output sentence will have an end dot.
```java 
sendMessage("Nurio", "Hello");
> Output: "Hello."
```