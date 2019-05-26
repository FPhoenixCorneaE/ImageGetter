package com.wkz.imagegetter.utils;

public class ResourceUtils {

    public static int getLayoutId(String name) {
        return ContextUtils.getContext().getResources().getIdentifier(name, "layout",
                ContextUtils.getContext().getPackageName());
    }

    public static int getStringId(String name) {
        return ContextUtils.getContext().getResources().getIdentifier(name, "string",
                ContextUtils.getContext().getPackageName());
    }

    public static int getDrawableId(String name) {
        return ContextUtils.getContext().getResources().getIdentifier(name, "drawable",
                ContextUtils.getContext().getPackageName());
    }

    public static int getMipmapId(String name) {
        return ContextUtils.getContext().getResources().getIdentifier(name, "mipmap",
                ContextUtils.getContext().getPackageName());
    }

    public static int getStyleId(String name) {
        return ContextUtils.getContext().getResources().getIdentifier(name, "style",
                ContextUtils.getContext().getPackageName());
    }

    public static int getId(String name) {
        return ContextUtils.getContext().getResources().getIdentifier(name, "id",
                ContextUtils.getContext().getPackageName());
    }

    public static int getColorId(String name) {
        return ContextUtils.getContext().getResources().getIdentifier(name, "color",
                ContextUtils.getContext().getPackageName());
    }

    public static int getArrayId(String name) {
        return ContextUtils.getContext().getResources().getIdentifier(name, "array",
                ContextUtils.getContext().getPackageName());
    }
}