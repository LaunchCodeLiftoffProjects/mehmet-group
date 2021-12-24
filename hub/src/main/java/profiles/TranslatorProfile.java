package profiles;

import org.springframework.context.annotation.Profile;

@Profile("translator")
public class TranslatorProfile implements BaseProfile{
}
