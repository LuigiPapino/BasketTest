package net.dragora.bjsstest.network;

import android.support.annotation.NonNull;

import net.dragora.bjsstest.commons.Instrumentation;

public interface NetworkInstrumentation<T> extends Instrumentation
{

    @NonNull
    T decorateNetwork(@NonNull final T httpClient);

}