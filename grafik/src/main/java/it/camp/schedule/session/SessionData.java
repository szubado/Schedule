package it.camp.schedule.session;

import it.camp.schedule.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@NoArgsConstructor
@Getter
@Setter
@Component
@SessionScope
public class SessionData {
    private User user = null;

    public boolean isLogged() {
        return this.user != null;
    }
    public boolean isAdmin() {
        if(this.user == null) {
            return false;
        }
        return this.user.getRole() == User.Role.ADMIN;
    }
}
