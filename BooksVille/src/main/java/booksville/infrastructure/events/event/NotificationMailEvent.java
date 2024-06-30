package booksville.infrastructure.events.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class NotificationMailEvent extends ApplicationEvent {
    private String firstName;
    private String email;
    private String subject;
    private String description;
    private String applicationUrl;

    public NotificationMailEvent(String email, String firstName, String subject, String description, String applicationUrl) {
        super(email);
        this.firstName = firstName;
        this.email = email;
        this.subject = subject;
        this.applicationUrl = applicationUrl;
        this.description = description;
    }
}
