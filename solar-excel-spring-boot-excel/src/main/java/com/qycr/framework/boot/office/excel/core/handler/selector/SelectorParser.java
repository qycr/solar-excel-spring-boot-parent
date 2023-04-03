package com.qycr.framework.boot.office.excel.core.handler.selector;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface SelectorParser {

    String[] selector();

    default Map<String, List<String>> join() {return Collections.emptyMap();}


    default List<SelectorLinkageCorrelation> correlation() {return Collections.emptyList();}


}
