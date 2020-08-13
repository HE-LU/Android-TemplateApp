package cz.helu.core.binding;

// this is in Java so that it can be used in Data Binding together with lambda functionality in kotlin
public interface OnItemClickListener<T>
{
    void onItemClick(T item);
}