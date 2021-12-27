package com.translator.hub.profiles;

import org.springframework.context.annotation.Profile;

@Profile("translator") //sets up a springboot profile to limit access to translators only
public class TranslatorProfile implements BaseProfile{
}
