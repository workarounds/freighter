package in.workarounds.samples.freighter.test;

import android.os.Bundle;

import in.workarounds.freighter.annotations.Cargo;
import in.workarounds.freighter.annotations.Freighter;

/**
 * Created by madki on 25/10/15.
 */
@Freighter
public class TestObject {
    @Cargo
    int one;

    public TestObject() {
    }

}
