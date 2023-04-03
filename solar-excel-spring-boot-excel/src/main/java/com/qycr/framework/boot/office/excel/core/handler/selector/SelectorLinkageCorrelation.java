package com.qycr.framework.boot.office.excel.core.handler.selector;


/**
 * The tree structure is obtained to solve the problem  {@link SelectorParser#join()}
 * that  the name can not be repeated under different levels
 */
public interface SelectorLinkageCorrelation {

    int  getId();

    /**
     * Level 1 parentId is 0 by default
     */
    int  getParentId();



    String  getName();

}
