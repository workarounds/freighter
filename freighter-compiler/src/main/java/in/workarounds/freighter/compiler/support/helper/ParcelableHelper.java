package in.workarounds.freighter.compiler.support.helper;

import com.squareup.javapoet.TypeName;

import javax.lang.model.util.Elements;

import in.workarounds.freighter.compiler.support.SupportResolver;

/**
 * Created by madki on 21/10/15.
 */
public class ParcelableHelper extends TypeHelper {

    public ParcelableHelper(TypeName typeName, Elements elementUtils) {
        super(typeName);
        if (!SupportResolver.isParcelable(type, elementUtils)) {
            throw new IllegalStateException("ParcelableHelper used for a non parcelable");
        }
    }

   @Override
    public String getBundleMethodSuffix() {
        return "Parcelable";
    }

    @Override
    public boolean requiresCasting() {
        return false;
    }
}
