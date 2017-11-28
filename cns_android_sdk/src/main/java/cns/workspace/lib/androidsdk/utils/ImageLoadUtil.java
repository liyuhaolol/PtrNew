package cns.workspace.lib.androidsdk.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.EmptySignature;

/**
 * Created by liyuhao on 2017/6/7.
 * 图片加载工具
 */

public class ImageLoadUtil {
    private static final int UNSET = -1;


    private static RequestOptions options;

    static {
        options = new RequestOptions();
        options
                .sizeMultiplier(1f)
                .useUnlimitedSourceGeneratorsPool(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.NORMAL)
                .error(null)
                .placeholder(null)
                .skipMemoryCache(false)
                .override(UNSET,UNSET)
                .signature(EmptySignature.obtain())
                .fallback(null)
                .theme(null)
                //.transform()
                .onlyRetrieveFromCache(false);

    }

    public static void displayImage(Context context, Object res, ImageView target) {
        if (options != null) {
            Glide.with(context)
                    .asDrawable()
                    .apply(options)
                    .load(res)
                    .into(target);
        } else {
            Log.e("ImageLoadUtil", "无法加载Glide的默认options,options为空");
        }
    }

    public static void displayImage(Context context, Object res, ImageView target, RequestOptions option) {
        RequestBuilder<Drawable> builder;
        if (options != null) {

            builder = Glide.with(context)
                    .asDrawable()
                    .apply(options);

            if (option != null) {

                builder.apply(option)
                        .load(res)
                        .into(target);
            } else {
                Log.e("ImageLoadUtil", "option为空,加载默认options");

                builder.load(res)
                        .into(target);
            }
        } else {
            Log.e("ImageLoadUtil", "无法加载Glide的默认options,options为空");
        }
    }

}
