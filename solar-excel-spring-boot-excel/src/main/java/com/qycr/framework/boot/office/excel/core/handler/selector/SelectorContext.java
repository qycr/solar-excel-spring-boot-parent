package com.qycr.framework.boot.office.excel.core.handler.selector;

import com.alibaba.excel.write.handler.WriteHandler;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.ObjectUtils;

public class SelectorContext implements ValueProvider<String[]>{

    private String[] source;

    private SelectorParser selector;
    private String selectorExpression;

    private WriteHandler writeHandler;

    private boolean multi;

    private boolean primary;

    private int startRow;

    private int lastRow;

    private boolean linkage;

    private int level;

    private int startColumn;

    private int lastColumn;

    private boolean expression;

    private SolarExcelBox errorBox;

    private SolarExcelBox promptBox;

    private  BeanResolver resolver;


    private static final ExpressionParser parser =  new SpelExpressionParser();

    private SelectorContext(){

    }

    public String[] getSource() {
        return source;
    }

    public SelectorParser getSelector() {
        return selector;
    }

    public String getSelectorExpression() {
        return selectorExpression;
    }

    public WriteHandler getWriteHandler() {
        return writeHandler;
    }

    public boolean isMulti() {
        return multi;
    }

    public boolean isPrimary() {
        return primary;
    }


    public int getLastRow() {
        return lastRow;
    }

    public int getStartRow() {
        return startRow == 0 ? 1 : startRow;
    }

    public int getStartColumn() {
        return startColumn;
    }

    public int getLastColumn() {
        return lastColumn;
    }

    public boolean isExpression() {
        return expression;
    }

    public boolean isLinkage() {
        return linkage;
    }

    public int getLevel() {
        return level;
    }

    public SolarExcelBox getErrorBox() {
        return errorBox;
    }

    public SolarExcelBox getPromptBox() {
        return promptBox;
    }

    @Override
    public String[] getValue() {

        if (!ObjectUtils.isEmpty(source)) {
            return source;
        }

        if (isExpression()) {
            final Expression parseExpression = parser.parseExpression(this.getSelectorExpression());
            final StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
            evaluationContext.setBeanResolver(resolver);
            return source = parseExpression.getValue(evaluationContext, String[].class);
        }
        return source = selector.selector();
    }

    public static  Builder builder(){
        return Builder.builder();
    }


    public static final class Builder {
        private String[] source;
        private SelectorParser selector;
        private String selectorExpression;
        private WriteHandler writeHandler;
        private boolean multi;
        private boolean primary;
        private int startRow;
        private int lastRow;
        private boolean linkage;
        private int level;
        private int startColumn;
        private int lastColumn;
        private boolean expression;
        private SolarExcelBox errorBox;
        private SolarExcelBox promptBox;
        private BeanResolver resolver;
        private ExpressionParser parser;

        private Builder() {
        }

        private static Builder builder() {
            return new Builder();
        }

        public Builder source(String[] source) {
            this.source = source;
            return this;
        }

        public Builder selector(SelectorParser selector) {
            this.selector = selector;
            return this;
        }

        public Builder selectorExpression(String selectorExpression) {
            this.selectorExpression = selectorExpression;
            return this;
        }

        public Builder writeHandler(WriteHandler writeHandler) {
            this.writeHandler = writeHandler;
            return this;
        }

        public Builder multi(boolean multi) {
            this.multi = multi;
            return this;
        }

        public Builder primary(boolean primary) {
            this.primary = primary;
            return this;
        }

        public Builder startRow(int startRow) {
            this.startRow = startRow;
            return this;
        }

        public Builder lastRow(int lastRow) {
            this.lastRow = lastRow;
            return this;
        }

        public Builder linkage(boolean linkage) {
            this.linkage = linkage;
            return this;
        }

        public Builder level(int level) {
            this.level = level;
            return this;
        }

        public Builder startColumn(int startColumn) {
            this.startColumn = startColumn;
            return this;
        }

        public Builder lastColumn(int lastColumn) {
            this.lastColumn = lastColumn;
            return this;
        }

        public Builder expression(boolean expression) {
            this.expression = expression;
            return this;
        }

        public Builder errorBox(SolarExcelBox errorBox) {
            this.errorBox = errorBox;
            return this;
        }

        public Builder promptBox(SolarExcelBox promptBox) {
            this.promptBox = promptBox;
            return this;
        }

        public Builder resolver(BeanResolver resolver) {
            this.resolver = resolver;
            return this;
        }


        public SelectorContext build() {
            SelectorContext selectorContext = new SelectorContext();
            selectorContext.multi = this.multi;
            selectorContext.selector = this.selector;
            selectorContext.selectorExpression = this.selectorExpression;
            selectorContext.writeHandler = this.writeHandler;
            selectorContext.lastColumn = this.lastColumn;
            selectorContext.promptBox = this.promptBox;
            selectorContext.linkage = this.linkage;
            selectorContext.errorBox = this.errorBox;
            selectorContext.level = this.level;
            selectorContext.source = this.source;
            selectorContext.expression = this.expression;
            selectorContext.lastRow = this.lastRow;
            selectorContext.primary = this.primary;
            selectorContext.startColumn = this.startColumn;
            selectorContext.resolver = this.resolver;
            selectorContext.startRow = this.startRow;
            return selectorContext;
        }
    }
}
