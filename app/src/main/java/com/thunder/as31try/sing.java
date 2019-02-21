package com.thunder.as31try;

class sing {
    private static final sing ourInstance = new sing();

    static sing getInstance() {
        return ourInstance;
    }

    private sing() {
    }
}
