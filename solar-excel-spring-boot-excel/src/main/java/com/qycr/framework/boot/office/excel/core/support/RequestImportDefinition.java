package com.qycr.framework.boot.office.excel.core.support;

import com.alibaba.excel.read.listener.ReadListener;
import com.qycr.framework.boot.office.excel.core.parser.ExcelResolver;
import org.springframework.http.HttpInputMessage;

import java.io.Serializable;
import java.util.List;

public class RequestImportDefinition implements Serializable {

    private  String name;

    private boolean required;

    private Class<? extends RepositoryProvider> provider;

    private boolean multiFile;

    private List<MultiFile> multiFiles;

    private Class<?> returnType;

    private Class<? extends ExcelResolver> resolver;

    private int limitLineNumber;

    private  boolean ignoreEmptyRow;

    private  Integer headRowNumber;

    private String password;

    private List<List<String>> heads;

    private transient HttpInputMessage httpInputMessage;


    private Class<? extends ReadListener> configurationListener;


    public String getName() {
        return name;
    }

    public boolean isRequired() {
        return required;
    }

    public Class<? extends RepositoryProvider> getProvider() {
        return provider;
    }

    public boolean isMultiFile() {
        return multiFile;
    }

    public List<MultiFile> getMultiFiles() {
        return multiFiles;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public Class<? extends ExcelResolver> getResolver() {
        return resolver;
    }

    public int getLimitLineNumber() {
        return limitLineNumber;
    }

    public boolean isIgnoreEmptyRow() {
        return ignoreEmptyRow;
    }

    public Integer getHeadRowNumber() {
        return headRowNumber;
    }

    public String getPassword() {
        return password;
    }

    public Class<? extends ReadListener> getConfigurationListener() {
        return configurationListener;
    }

    public HttpInputMessage getHttpInputMessage() {
        return httpInputMessage;
    }

    public List<List<String>> getHeads() {
        return heads;
    }

    public static Builder builder(){
        return Builder.builder();
    }

    public static final class Builder {
        private String name;
        private boolean required;
        private Class<? extends RepositoryProvider> provider;
        private boolean multiFile;
        private List<MultiFile> multiFiles;
        private Class<?> returnType;
        private Class<? extends ExcelResolver> resolver;
        private int limitLineNumber;
        private boolean ignoreEmptyRow;
        private Integer headRowNumber;
        private String password;

        private HttpInputMessage httpInputMessage;
        private Class<? extends ReadListener> configurationListener;

        private Builder() {
        }

        private static Builder builder() {
            return new Builder();
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder required(boolean required) {
            this.required = required;
            return this;
        }

        public Builder provider(Class<? extends RepositoryProvider> provider) {
            this.provider = provider;
            return this;
        }

        public Builder multiFile(boolean multiFile) {
            this.multiFile = multiFile;
            return this;
        }

        public Builder multiFiles(List<MultiFile> multiFiles) {
            this.multiFiles = multiFiles;
            return this;
        }

        public Builder returnType(Class<?> returnType) {
            this.returnType = returnType;
            return this;
        }

        public Builder resolver(Class<? extends ExcelResolver> resolver) {
            this.resolver = resolver;
            return this;
        }

        public Builder limitLineNumber(int limitLineNumber) {
            this.limitLineNumber = limitLineNumber;
            return this;
        }

        public Builder ignoreEmptyRow(boolean ignoreEmptyRow) {
            this.ignoreEmptyRow = ignoreEmptyRow;
            return this;
        }

        public Builder headRowNumber(Integer headRowNumber) {
            this.headRowNumber = headRowNumber;
            return this;
        }

        public Builder httpInputMessage(HttpInputMessage httpInputMessage) {
            this.httpInputMessage = httpInputMessage;
            return this;
        }

        public Builder configurationListener(Class<? extends ReadListener> configurationListener) {
            this.configurationListener = configurationListener;
            return this;
        }

        public RequestImportDefinition build() {
            RequestImportDefinition requestImportDefinition = new RequestImportDefinition();
            requestImportDefinition.headRowNumber = this.headRowNumber;
            requestImportDefinition.required = this.required;
            requestImportDefinition.multiFiles = this.multiFiles;
            requestImportDefinition.provider = this.provider;
            requestImportDefinition.returnType = this.returnType;
            requestImportDefinition.httpInputMessage = this.httpInputMessage;
            requestImportDefinition.name = this.name;
            requestImportDefinition.multiFile = this.multiFile;
            requestImportDefinition.resolver = this.resolver;
            requestImportDefinition.ignoreEmptyRow = this.ignoreEmptyRow;
            requestImportDefinition.configurationListener = this.configurationListener;
            requestImportDefinition.limitLineNumber = this.limitLineNumber;
            requestImportDefinition.password = this.password;
            return requestImportDefinition;
        }
    }
}
