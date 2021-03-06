package in.workarounds.freighter.compiler.generator;

import com.squareup.javapoet.MethodSpec;

import java.util.List;

import javax.lang.model.element.Modifier;

import in.workarounds.freighter.compiler.Provider;
import in.workarounds.freighter.compiler.model.CargoModel;
import in.workarounds.freighter.compiler.model.FreighterModel;
import in.workarounds.freighter.compiler.model.StateModel;
import in.workarounds.freighter.compiler.util.CommonClasses;

/**
 * Created by madki on 24/10/15.
 */
public class ServiceWriter extends Writer {
    protected static final String SERVICE_VAR = "service";

    protected ServiceWriter(Provider provider, FreighterModel freighterModel, List<CargoModel> cargoList, List<StateModel> states) {
        super(provider, freighterModel, cargoList, states);
    }

    @Override
    protected List<MethodSpec> getAdditionalHelperMethods() {
        List<MethodSpec> methods = super.getAdditionalHelperMethods();
        methods.add(
                MethodSpec.methodBuilder(RETRIEVE_METHOD)
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .addParameter(CommonClasses.INTENT, INTENT_VAR)
                        .returns(RETRIEVER_CLASS)
                        .beginControlFlow("if($L == null)", INTENT_VAR)
                        .addStatement("return new $T(null)", RETRIEVER_CLASS)
                        .endControlFlow()
                        .addStatement("return $L($L.getExtras())", RETRIEVE_METHOD, INTENT_VAR)
                        .build()
        );
        methods.add(
                MethodSpec.methodBuilder(INJECT_METHOD)
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .addParameter(freighterModel.getClassName(), SERVICE_VAR)
                        .addParameter(CommonClasses.INTENT, INTENT_VAR)
                        .addStatement("$T $L = $L($L)", RETRIEVER_CLASS, RETRIEVER_VAR, RETRIEVE_METHOD, INTENT_VAR)
                        .beginControlFlow("if(!$L.$L())", RETRIEVER_VAR, IS_NULL_METHOD)
                        .addStatement("$L.$L($L)", RETRIEVER_VAR, INTO_METHOD, SERVICE_VAR)
                        .endControlFlow()
                        .build()
        );
        return methods;
    }

    @Override
    protected List<MethodSpec> getAdditionalSupplierMethods() {
        List<MethodSpec> methods = super.getAdditionalSupplierMethods();
        methods.add(
                MethodSpec.methodBuilder(INTENT_METHOD)
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(CommonClasses.CONTEXT, CONTEXT_VAR)
                        .returns(CommonClasses.INTENT)
                        .addStatement("$T $L = new $T($L, $T.class)", CommonClasses.INTENT, INTENT_VAR, CommonClasses.INTENT, CONTEXT_VAR, freighterModel.getClassName())
                        .addStatement("$L.putExtras($L())", INTENT_VAR, BUNDLE_METHOD)
                        .addStatement("return $L", INTENT_VAR)
                        .build()
        );
        methods.add(
                MethodSpec.methodBuilder(START_METHOD)
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(CommonClasses.CONTEXT, CONTEXT_VAR)
                        .addStatement("$L.startService($L($L))", CONTEXT_VAR, INTENT_METHOD, CONTEXT_VAR)
                        .build()
        );
        return methods;
    }
}
