package com.sun.health.flowable.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;

/**
 * Created by 华硕 on 2018-04-25.
 */
@Configuration
@Import(HelloWorldConfig.class)
public class TextEditorConfig {

    @Bean
    @Scope("prototype")
//    @Scope("singleton")
    public SpellChecker spellChecker() {
        return new SpellChecker();
    }

    @Bean
    @Conditional({
        CustomCondition.class
    })
    public TextEditor textEditor(SpellChecker spellChecker) {
        return new TextEditor(spellChecker);
    }

//    @Bean
//    public TextEditor textEditor() {
//        return new TextEditor(spellChecker());
//    }

    @Bean
    public CustomApplicationListener customApplicationListener() {
        return new CustomApplicationListener();
    }

    @Bean
    public CustomEventPublisher customEventPublisher() {
        return new CustomEventPublisher();
    }

}
