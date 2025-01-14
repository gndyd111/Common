package com.d.lib.common.data.preference;

import android.content.Context;
import android.text.TextUtils;

import com.d.lib.common.data.preference.operation.OpOnline;
import com.d.lib.common.data.preference.operation.OpSetting;

/**
 * Preferences
 * Created by D on 2017/4/29.
 */
public class Preferences extends AbstractPreference {
    private volatile static Preferences INSTANCE;

    private OpSetting opSetting;
    private OpOnline opOnline;

    private Preferences(Context context) {
        super(context);
    }

    /**
     * Initialize operation handle
     */
    @Override
    protected void initOps() {
        opSetting = new OpSetting(mSettings, mEditor);
        opOnline = new OpOnline(mSettings, mEditor);
    }

    public static Preferences getIns(Context context) {
        if (INSTANCE == null) {
            synchronized (Preferences.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Preferences(context);
                }
            }
        }
        return INSTANCE;
    }

    /****************************** - Setting - ******************************/
    public OpSetting optSetting() {
        return opSetting;
    }

    /****************************** - Online - ******************************/
    public OpOnline optOnline() {
        return opOnline;
    }


    /****************************** - Operation - ******************************/
    public void clearUserData(String account) {
        if (!TextUtils.equals(account, getAccount())) {
            boolean isFirst = getIsFirst();
            clearAllData();
            putIsFirst(isFirst);
        }
    }

    /************************* Yes / No Whether the app is used for the first time *************************/
    public void putIsFirst(boolean isFirst) {
        mEditor.putBoolean(Keys.KEY_IS_FIRST, isFirst);
        save();
    }

    public boolean getIsFirst() {
        return mSettings.getBoolean(Keys.KEY_IS_FIRST, true);
    }

    /************************* Set / Get Login Account *************************/
    public void putAccount(String account) {
        mEditor.putString(Keys.KEY_ACCOUNT, account);
        save();
    }

    public String getAccount() {
        return mSettings.getString(Keys.KEY_ACCOUNT, "");
    }

}
