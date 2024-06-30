package booksville.infrastructure.events.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class CompleteRegistrationEvent extends ApplicationEvent {
    private String firstName;
    private String email;
    private String applicationUrl;

    public CompleteRegistrationEvent(String email, String firstName, String applicationUrl) {
        super(email);
        this.firstName = firstName;
        this.email = email;
        this.applicationUrl = applicationUrl;
    }
}
