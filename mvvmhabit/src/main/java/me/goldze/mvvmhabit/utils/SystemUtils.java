package me.goldze.mvvmhabit.utils;

import android.os.Build;

import java.util.Locale;

/**
 * @author wengyiheng
 * @date 2020/10/16.
 * description：系统工具类
 */
public class SystemUtils {
    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return  语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    public static String getSystemModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return  手机厂商
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 获取手机厂商类型
     * 用于推送类型初始化记录
     *
     * @return  对应手机型号 1：小米 2：华为 3：其他
     */
    public static DeviceType getDeviceType() {
        String manufacturer = Build.MANUFACTURER;
        //这个字符串可以自己定义,例如判断华为就填写huawei,魅族就填写meizu
        if ("xiaomi".equalsIgnoreCase(manufacturer)) {
            return DeviceType.XIAOMI;
        }

        if ("huawei".equalsIgnoreCase(manufacturer)) {
            return DeviceType.HUAWEI;
        }

        return DeviceType.OTHER;
    }

    public enum DeviceType{
        XIAOMI(1),
        HUAWEI(2),
        OTHER(3);

        private int type;

        DeviceType(int type) {
            this.type=type;
        }

        public int getType() {
            return type;
        }
    }


}
