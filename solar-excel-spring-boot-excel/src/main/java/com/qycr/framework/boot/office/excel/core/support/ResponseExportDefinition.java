package com.qycr.framework.boot.office.excel.core.support;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.qycr.framework.boot.office.excel.core.filter.ResolverFilter;
import com.qycr.framework.boot.office.excel.core.parser.ExcelResolver;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ResponseExportDefinition {

    private Class<?> returnType;

    String fileName;

    String resolverBeanName;

    boolean template;

    String templatePath;

    Class<? extends ExcelResolver> resolver;

    String password;

    ExcelTypeEnum suffix;

    boolean  inMemory;

    Class<? extends ExcelHeadProvider> head;

    Class<? extends ResolverFilter >[] includeFilters;

    Class<? extends ResolverFilter > [] excludeFilters;

    private List<SimpleSheet> sheet;

    private HttpServletResponse httpServletResponse;


    public HttpServletResponse getHttpServletResponse() {
        return httpServletResponse;
    }

    public String getFileName() {
        return fileName;
    }

    public String getResolverBeanName() {
        return resolverBeanName;
    }

    public boolean isTemplate() {
        return template;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public Class<? extends ExcelResolver> getResolver() {
        return resolver;
    }

    public String getPassword() {
        return password;
    }

    public ExcelTypeEnum getSuffix() {
        return suffix;
    }

    public boolean isInMemory() {
        return inMemory;
    }

    public Class<? extends ExcelHeadProvider> getHead() {
        return head;
    }

    public Class<? extends ResolverFilter>[] getIncludeFilters() {
        return includeFilters;
    }

    public Class<? extends ResolverFilter>[] getExcludeFilters() {
        return excludeFilters;
    }

    public List<SimpleSheet> getSheet() {
        return sheet;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public  static Builder builder(){
        return Builder.builder();
    }


    public static final class Builder {
        private Class<?> returnType;
        private String fileName;
        private String resolverBeanName;
        private boolean template;
        private String templatePath;
        private Class<? extends ExcelResolver> resolver;
        private String password;
        private ExcelTypeEnum suffix;
        private boolean inMemory;
        private Class<? extends ExcelHeadProvider> head;
        private Class<? extends ResolverFilter>[] includeFilters;
        private Class<? extends ResolverFilter>[] excludeFilters;
        private List<SimpleSheet> sheet;

        private HttpServletResponse httpServletResponse;

        private Builder() {
        }

        private static Builder builder() {
            return new Builder();
        }

        public Builder returnType(Class<?> returnType) {
            this.returnType = returnType;
            return this;
        }

        public Builder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Builder resolverBeanName(String resolverBeanName) {
            this.resolverBeanName = resolverBeanName;
            return this;
        }

        public Builder template(boolean template) {
            this.template = template;
            return this;
        }

        public Builder templatePath(String templatePath) {
            this.templatePath = templatePath;
            return this;
        }

        public Builder resolver(Class<? extends ExcelResolver> resolver) {
            this.resolver = resolver;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder suffix(ExcelTypeEnum suffix) {
            this.suffix = suffix;
            return this;
        }

        public Builder inMemory(boolean inMemory) {
            this.inMemory = inMemory;
            return this;
        }

        public Builder head(Class<? extends ExcelHeadProvider> head) {
            this.head = head;
            return this;
        }

        public Builder includeFilters(Class<? extends ResolverFilter>[] includeFilters) {
            this.includeFilters = includeFilters;
            return this;
        }

        public Builder excludeFilters(Class<? extends ResolverFilter>[] excludeFilters) {
            this.excludeFilters = excludeFilters;
            return this;
        }

        public Builder sheet(List<SimpleSheet> sheet) {
            this.sheet = sheet;
            return this;
        }

        public Builder httpServletResponse(HttpServletResponse httpServletResponse) {
            this.httpServletResponse = httpServletResponse;
            return this;
        }

        public ResponseExportDefinition build() {
            ResponseExportDefinition responseExportDefinition = new ResponseExportDefinition();
            responseExportDefinition.excludeFilters = this.excludeFilters;
            responseExportDefinition.inMemory = this.inMemory;
            responseExportDefinition.templatePath = this.templatePath;
            responseExportDefinition.includeFilters = this.includeFilters;
            responseExportDefinition.suffix = this.suffix;
            responseExportDefinition.sheet = this.sheet;
            responseExportDefinition.resolverBeanName = this.resolverBeanName;
            responseExportDefinition.resolver = this.resolver;
            responseExportDefinition.fileName = this.fileName;
            responseExportDefinition.template = this.template;
            responseExportDefinition.head = this.head;
            responseExportDefinition.password = this.password;
            responseExportDefinition.returnType = this.returnType;
            responseExportDefinition.httpServletResponse = this.httpServletResponse;
            return responseExportDefinition;
        }
    }
}
