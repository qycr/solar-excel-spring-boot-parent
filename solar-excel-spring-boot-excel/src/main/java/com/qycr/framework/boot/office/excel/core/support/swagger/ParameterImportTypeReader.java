package com.qycr.framework.boot.office.excel.core.support.swagger;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.qycr.framework.boot.office.excel.annotation.RequestImport;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.Types;
import springfox.documentation.service.ResolvedMethodParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterContext;
import static java.lang.String.format;

import static com.google.common.base.Strings.isNullOrEmpty;

public class ParameterImportTypeReader implements ParameterBuilderPlugin {

    @Override
    public void apply(ParameterContext parameterContext) {
        String name = findParameterNameFromAnnotations(parameterContext.resolvedMethodParameter());
        if (isNullOrEmpty(name)) {
            Optional<String> discoveredName = parameterContext.resolvedMethodParameter().defaultName();
            name = discoveredName.isPresent()
                    ? discoveredName.get()
                    : format("param%s", parameterContext.resolvedMethodParameter().getParameterIndex());
        }
        final Optional<RequestImport> optional = parameterContext.resolvedMethodParameter().findAnnotation(RequestImport.class);
        final ParameterBuilder parameterBuilder = parameterContext.parameterBuilder();
        parameterBuilder
                .name(name)
                .description(name);
        if(!ObjectUtils.isEmpty(optional) && optional.isPresent()){
            final TypeResolver typeResolver = new TypeResolver();
            parameterBuilder.parameterType("form");
            parameterBuilder.required(optional.get().required());
            parameterBuilder.modelRef(new ModelRef(Types.typeNameFor(MultipartFile.class)));
            parameterBuilder.allowMultiple(false);
            parameterBuilder.type(typeResolver.resolve(MultipartFile.class));
        }
    }

    private String findParameterNameFromAnnotations(ResolvedMethodParameter resolvedMethodParameter) {
      return   resolvedMethodParameter.findAnnotation(RequestImport.class).transform(requestImportValue()).orNull();
    }
    private Function<RequestImport, String> requestImportValue() {
        return RequestImport::value;
    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        return Boolean.TRUE;
    }

}
