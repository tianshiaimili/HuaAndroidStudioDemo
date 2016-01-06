package ctrip.android.bundle2.framework;

import java.io.InputStream;

public interface Bundle {


    long getBundleId();


    String getLocation();


    int getState();


    void update(InputStream inputStream) throws BundleException;
}
