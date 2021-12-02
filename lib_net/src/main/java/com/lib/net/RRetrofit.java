package com.lib.net;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import com.lib.net.base.NetApplication;

import org.json.JSONObject;
import com.lib.net.BuildConfig;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.goldze.mvvmhabit.utils.SPUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author wengyiheng
 * @date 2020/5/19.
 * description：
 */
public class RRetrofit {

    //只提供debug模式修改 ,debug包只出测试，release包只出生产
    public static String BASE_URL = BuildConfig.API_HOST;


    /*
     **打印retrofit信息部分
     */
    static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            //打印retrofit日志
            Log.e("RetrofitLog","retrofitBack = "+message);
        }
    });

    public static String clientType = "sxyUIT2jE";
    private static String ua= String.format("%s/%s (Android %s; %s Build/%s)", "IWu",
            getVersionName(NetApplication.getIns()), Build.VERSION.RELEASE, Build.MANUFACTURER, Build.ID);
    private static OkHttpClient httpClient = getHttpClient();
    //每次网络请求携带请求头(token上传，校验唯一登陆)
    private static Retrofit.Builder builder= new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient);

    public static String getVersionName(Context mContext) {
        if (mContext != null) {
            try {
                return mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException ignored) {
                return "1.0";
            }
        }

        return "";
    }

    //刷新token后重构网络请求
    public static void reCreateHttpClient(){
        //删除网络构建数据
        _caches.clear();
        //网络数据重构
        httpClient = getHttpClient();
        builder= new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient);
    }

    /**
     * 构造httpclient
     *
     * @return
     */
    public static OkHttpClient getHttpClient() {
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //设置连接超时时间 ;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//                .cookieJar(new CookieManger(context));
        if (BuildConfig.ISDEBUG) {
            builder.addInterceptor(loggingInterceptor);
        }
        OkHttpClient client = builder
                .readTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        HttpUrl url = chain.request().url();
                        Request.Builder builder = chain.request().newBuilder();
                        builder
                                .addHeader("Content-Type", "application/json;charset=UTF-8")
                                .addHeader("client-type", clientType);
                        builder.addHeader("Authorization", SPUtils.getInstance().getString("token"));
                        Log.e("wyh","当前token="+ SPUtils.getInstance().getString("token"));
                        builder.addHeader("User-Agent", ua);
                        Response proceed = null;
                        try {
                            proceed = chain.proceed(builder.build());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        int code = proceed.code();
                        if (code == 500) {

                        } else if (code == 401) {

                        }


                        return proceed;
                    }
                })
                .build();

        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, null, new SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            client.newBuilder().sslSocketFactory(sslSocketFactory, new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }).hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return client;
    }


    /**网络服务接口缓存**/
    static private Map<String, Object> _caches = Collections.synchronizedMap(new HashMap<String, Object>());
    /**
     * 获取接口服务
     * @param serviceClass
     */
    public <T> T getApiService(Class<T> serviceClass) {
        if (serviceClass == null) return null;
        String serviceKey = serviceClass.getSimpleName();
        T service = (T) _caches.get(serviceClass.getSimpleName());
        if (service == null) {
            service = new NetProxy(builder.build()).getProxy(serviceClass);//代理API
            _caches.put(serviceKey, service);
        }
        return service;
    }


    //手动传url，适配第三方请求
    public <S> S createNormal(Class<S> service,String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(getHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .build();
        return retrofit.create(service);
    }

    /**
     * 构建传输参数
     * @return
     */
    protected HttpParam getTransBody() {
        return new HttpParam();
    }



    public static RequestBody createJsBody(JSONObject js) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), js.toString());
        return body;
    }
    public static <T> ObservableTransformer<T, T> setThread() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
