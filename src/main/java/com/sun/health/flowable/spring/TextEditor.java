package com.sun.health.flowable.spring;

/**
 * Created by 华硕 on 2018-04-25.
 */
public class TextEditor {

    private SpellChecker spellChecker;

    public TextEditor(SpellChecker spellChecker) {
        System.out.println("TextEditor Constructor");
        this.spellChecker = spellChecker;
    }

    public void spellCheck() {
        spellChecker.checkSpelling();
    }


}
