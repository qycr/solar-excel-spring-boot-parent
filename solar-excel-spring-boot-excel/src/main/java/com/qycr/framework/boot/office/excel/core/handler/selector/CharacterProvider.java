package com.qycr.framework.boot.office.excel.core.handler.selector;

public enum CharacterProvider {

    A('A', 1),
    B('B', 2),
    C('C', 3),
    D('D', 4),
    E('E', 5),
    F('F', 6),
    G('G', 7),
    H('H', 8),
    I('I', 9),
    J('J', 10),
    K('K', 11),
    L('L', 12),
    M('M', 13),
    N('N', 14),
    O('O', 15),
    P('P', 16),
    Q('Q', 17),
    R('R', 18),
    S('S', 19),

    T('T', 20),
    U('U', 21),
    V('V', 22),
    W('W', 23),
    X('X', 24),
    Y('Y', 25),
    Z('Z', 26);


    private char character;
    private int value;

    CharacterProvider(char character, int value) {
        this.character = character;
        this.value = value;
    }
    public char getCharacter() {
        return character;
    }

    public int getValue() {
        return value;
    }

}
