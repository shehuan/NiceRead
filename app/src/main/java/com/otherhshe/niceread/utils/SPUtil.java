package com.otherhshe.niceread.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.Method;

/**
 * Author: Othershe
 * Time: 2016/8/22 10:20
 */
public class SPUtil {
    private static SharedPreferences mSp;

    /**
     * 在Application中初始化
     *
     * @param context
     * @param spName
     */
    public static void init(Context context, String spName) {
        mSp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    /**
     * 保存
     *
     * @param key
     * @param value
     */
    public static void save(String key, Object value) {
        SharedPreferences.Editor editor = mSp.edit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else {
            editor.putString(key, value.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 取值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static Object get(String key, Object defaultValue) {
        if (defaultValue instanceof String) {
            return mSp.getString(key, (String) defaultValue);
        } else if (defaultValue instanceof Integer) {
            return mSp.getInt(key, (Integer) defaultValue);
        } else if (defaultValue instanceof Boolean) {
            return mSp.getBoolean(key, (Boolean) defaultValue);
        } else if (defaultValue instanceof Float) {
            return mSp.getFloat(key, (Float) defaultValue);
        } else if (defaultValue instanceof Long) {
            return mSp.getLong(key, (Long) defaultValue);
        } else {
            return null;
        }
    }

    /**
     * 删除
     *
     * @param key
     */
    public static void remove(String key) {
        SharedPreferences.Editor editor = mSp.edit();
        editor.remove(key);

        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清空
     */
    public static void clear() {
        SharedPreferences.Editor editor = mSp.edit();
        editor.clear();

        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询key是否存在
     *
     * @param key
     * @return
     */
    public static boolean contain(String key) {
        return mSp.contains(key);
    }

    /**
     * 自定义提交方法
     */
    private static class SharedPreferencesCompat {

        private static final Method sApplyMethod = findApplyMethod();

        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (Exception e) {
            }
            editor.commit();
        }
    }
}
