package com.suyi.filereceiver;

import static android.content.Context.DOWNLOAD_SERVICE;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.suyi.filereceiver.fragments.FileFragment;
import com.suyi.filereceiver.fragments.ReceiveFragment;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder>
{
    private Context context;
    private List<File> files;

    public FileAdapter(Context context, List<File> files)
    {
        this.files = files;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.item_files, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        File file = files.get(position);
        holder.bind(file);
    }

    @Override
    public int getItemCount()
    {
        return files.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvSender;
        ImageView ivImage;
        TextView tvReceiver;
        TextView tvDate;
        Button btnDownload;


        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tvSender = itemView.findViewById(R.id.tvSender);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvReceiver = itemView.findViewById(R.id.tvReceiver);
            tvDate = itemView.findViewById(R.id.tvDate);
            btnDownload = itemView.findViewById(R.id.btnDownload);

        }


        public void bind(File file) {
            tvSender.setText(file.getSender());
            tvReceiver.setText(file.getReceiver());
            tvDate.setText(file.getDate());
            ivImage.setImageResource(R.drawable.ic_baseline_file_copy_24);


            ParseFile fileImage = file.getFile();
            if (fileImage != null) {
                Glide.with(context).load(fileImage.getUrl()).into(ivImage);
            }
            btnDownload.setOnClickListener(new View.OnClickListener() {

                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View view) {
                    Fragment fragment = new ReceiveFragment();
                    FragmentManager fragmentManager =((FragmentActivity)context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.flContainer,fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();



                }
            });
        }




    }

}
