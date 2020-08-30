package com.yinlei.androidnetworksecurity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yinlei.androidnetworksecurity.http.HttpRequest;
import com.yinlei.crypto.AES;
import com.yinlei.crypto.DH;
import com.yinlei.crypto.RSA;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import javax.crypto.spec.DHGenParameterSpec;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    // 服务器地址URL
    private static final String URL = "http://192.168.101.104/";

    private static final String TAG = "yinlei";

    private byte[] mAesKey;

    private static final String PUB_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCrAXZLvzq5sWod84ltsHXkt++B\n" +
            "hIchDsHj26zlOd6NwmUKKwFT5RLdxME3lYK3XoHTZ6/u+uYBLp1U+/tzxNXZtTYT\n" +
            "Ut+a3LEXOu442/ToXmxR8pEHjegw6x6GUqbmPrP8f3Y6FxA6YlaRn+VkqU4w4X8p\n" +
            "1mqjL3/2as/i3Ty3nwIDAQAB";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final HttpRequest request = new HttpRequest(URL);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                // 判断是否需要握手，AES密钥不可用则直接握手
                final DH dh = new DH();
                final int pubKey = dh.getPublicKey();
                if (mAesKey == null || mAesKey.length <= 0) {
                    Log.d(TAG, "public key is :" + pubKey);
                    request.handshake(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            Log.e(TAG, "Get握手失败");
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            byte[] pubKey = response.body().bytes();
                            mAesKey = dh.getSecretKey(pubKey);
                            Log.d(TAG, "aes key: " + new String(mAesKey));
                        }
                    }, RSA.encrypt(pubKey, PUB_KEY));
                } else {
                    request.request(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            Log.e(TAG, "Get请求失败");
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            byte[] responseContent = response.body().bytes();
                            AES aes = new AES(mAesKey);
                            String content = new String(aes.decrypt(responseContent));

                            Log.d(TAG, "Get请求成功: "+content);
                        }
                    });
                }
            }
        });
    }
}