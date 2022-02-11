package com.example.randompasswordgenerator;

import android.content.Context;
import android.content.Intent;

public class InterfaceImplementation implements interfaceRedirect {

    @Override
    public void redirectActivity(Context packageContext, Class<?> cls2) {
        Intent intent = new Intent(packageContext, cls2);
        packageContext.startActivity(intent);
    }

}
