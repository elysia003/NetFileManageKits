package com.example.nasmanage.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.nasmanage.GloabValue;
import com.example.nasmanage.R;
import com.example.nasmanage.bean.ApiFile;
import com.example.nasmanage.aa.ui.main.MainFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<ApiFile> lists;
    private Context context;
    private FragmentManager fragmentManager;
    private Fragment fragment;
    private String buju;

    public ListAdapter(Context context, FragmentManager fm, Fragment f) {
        lists = new ArrayList<>();
        this.fragment = f;
        this.fragmentManager = fm;
        this.context = context;
    }

    public static int c = 0;

    public void set_list(List list) {
        this.lists = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String buju = prefs.getString("buju", "list");
        View view = null;
        if ("列表".equals(buju)) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        } else if ("预览图".equals(buju)) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bigimg_item, parent, false);
        }
        this.buju = buju;
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    private static HashSet<String> VEDIO;
    private static HashSet<String> IMAGE;

    public static final String[] SET_VALUES = new String[]{"mp4", "avi", "mov", "rmvb", "rm", "flv", "3gp", "wmv", "ts", "mpeg", "mpg", "mkv", "flc", "m4v", "mod", "webm", "mst", "mpe", "flc", "swf", "cfg", "mts", "vob"};
    public static final String[] SET_IMAGE = new String[]{"jpg", "jpeg", "png", "tif", "bmp", "xbm", "tif", "pjp", "svgz", "jpg", "jpeg", "ico", "tiff", "gif", "svg", "jfif", "webp", "png", "bmp", "pjpeg", "avif"};
    static {
        VEDIO = new HashSet<>();
        IMAGE = new HashSet<>();
        for (String i : SET_VALUES) {
            VEDIO.add(i);
        }
        for (String i : SET_IMAGE) {
            IMAGE.add(i);
        }
    }

    public static String getType(String path) {
        String[] sl = path.split("\\.");
        if (sl != null && sl.length > 0) {
            String pre = sl[sl.length - 1].toLowerCase();
            Log.d("pre", pre);
            if (VEDIO.contains(pre)) {
                return "vedio";
            }
            if (IMAGE.contains(pre)) {
                return "image";
            }
        }
        return "other";
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ApiFile item = lists.get(position);
        holder.title.setText(item.getTitle());
        Log.d(item.getTitle(), item.isDir() == true ? "true" : "false");
        if ("列表".equals(buju)) {
            if (item.isDir()) {
                holder.imageView.setImageResource(R.drawable.wjj);
                holder.timeandsize.setText("");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("AAA", "AAA");
                        MainFragment nf = new MainFragment(item.getFileurl());
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(R.anim.in, R.anim.out, R.anim.in, R.anim.out)
                                .hide(fragment)
                                .add(R.id.nav_host_fragment_content_main22, nf, item.getFileurl())
                                .addToBackStack("name:" + item.getFileurl())
                                .commit();
                    }
                });
            } else {
                String url = GloabValue.base_vedio_url + item.getFilepath()
                        .replace(GloabValue.replace, "")
                        .replace("\\", "/")
                        .replace(" ", "%20");
                Log.d("url", url);
                String extension = MimeTypeMap.getFileExtensionFromUrl(url.substring(url.length() - 5, url.length() - 1));
                String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                String imgUrl;
                Log.d("type", mimeType + "");
                if ((mimeType + "").contains("image")) {
                    imgUrl = url;
                } else {
                    imgUrl = GloabValue.base_image_url + item.getId() + "/1.jpg";
                }
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.error(R.drawable.noimage);
                requestOptions.placeholder(R.drawable.noimage);
                requestOptions.apply(RequestOptions.bitmapTransform(new CircleCrop()));
                requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
                //requestOptions.skipMemoryCache(true);
                Glide.with(context)
                        .applyDefaultRequestOptions(requestOptions)
                        .load(imgUrl)
                        .into(holder.imageView);
                holder.timeandsize.setText("  " + formateSize(item.getSize()) + "  |  " + formateData(item.getCreatetime()));
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("DDDDDDD", "DDDDDDDDDD");
                    }
                });

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent mediaIntent = new Intent(Intent.ACTION_VIEW);
                        mediaIntent.setDataAndType(Uri.parse(url), mimeType);
                        context.startActivity(mediaIntent);
                    }
                });
            }
        }
        else if ("预览图".equals(buju)) {
            if (item.isDir()) {
                holder.imageView.setImageResource(R.drawable.wjj);
                holder.timeandsize.setText("");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainFragment nf = new MainFragment(item.getFileurl());
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(R.anim.in, R.anim.out, R.anim.in, R.anim.out)
                                .hide(fragment)
                                .add(R.id.nav_host_fragment_content_main22, nf, item.getFileurl())
                                .addToBackStack("name:" + item.getFileurl())
                                .commit();
                    }
                });
            } else {
                String url = GloabValue.base_vedio_url + item.getFilepath()
                        .replace(GloabValue.replace, "")
                        .replace("\\", "/")
                        .replace(" ", "%20");
                Log.d("url", url);
                String mimeType = null;
                String imgUrl=null;
                Log.d("AAA", url + "  " + getType(url));
                if ("image".equals(getType(url))) {
                    imgUrl = url;
                    mimeType="application/jpg";
                } else if("vedio".equals(getType(url))) {
                    mimeType="video/mp4";
                    imgUrl = GloabValue.base_image_url + item.getId() + "/" + GloabValue.imgSize + ".jpg";
                }
                else {
                    mimeType="text/html";
                }
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.error(R.mipmap.load_failed);
                requestOptions.placeholder(R.mipmap.load_failed);
                //requestOptions.apply(RequestOptions.bitmapTransform(new CircleCrop()));
                requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
                //requestOptions.skipMemoryCache(true);
                Glide.with(context)
                        .applyDefaultRequestOptions(requestOptions)
                        .load(imgUrl)
                        .into(holder.imageView);
                holder.timeandsize.setText("" + formateSize(item.getSize()) + "  |  " + formateData(item.getCreatetime()));
                /*
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("DDDDDDD", "DDDDDDDDDD");
                    }
                });*/
                String finalMimeType = mimeType;
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = GloabValue.base_vedio_url + item.getFilepath()
                                .replace(GloabValue.replace, "")
                                .replace("\\", "/")
                                .replace(" ", "%20");
                        Log.d("url", url);
                        Intent mediaIntent = new Intent(Intent.ACTION_VIEW);
                        mediaIntent.setDataAndType(Uri.parse(url), finalMimeType);
                        context.startActivity(mediaIntent);
                    }
                });
            }
        }
    }

    private static String formateData(Long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return sdf.format(time);
    }

    public static String formateSize(long size) {
        //如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        if (size < 1024) {
            return String.valueOf(size) + "B";
        } else {
            size = size / 1024;
        }
        //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        //因为还没有到达要使用另一个单位的时候
        //接下去以此类推
        if (size < 1024) {
            return String.valueOf(size) + "KB";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            //因为如果以MB为单位的话，要保留最后1位小数，
            //因此，把此数乘以100之后再取余
            size = size * 100;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "MB";
        } else {
            //否则如果要以GB为单位的，先除于1024再作同样的处理
            size = size * 100 / 1024;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "GB";
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        TextView timeandsize;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            title = (TextView) view.findViewById(R.id.textView);
            timeandsize = (TextView) view.findViewById(R.id.textView2);
        }
    }
}
