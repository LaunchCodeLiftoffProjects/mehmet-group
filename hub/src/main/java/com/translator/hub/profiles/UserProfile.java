package com.translator.hub.profiles;

import org.springframework.context.annotation.Profile;

@Profile("user")//sets up a springboot profile to limit access to users only
public class UserProfile implements BaseProfile {
}
