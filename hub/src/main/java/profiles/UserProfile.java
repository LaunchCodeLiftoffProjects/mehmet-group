package profiles;

import org.springframework.context.annotation.Profile;

@Profile("user")
public class UserProfile implements BaseProfile {
}
