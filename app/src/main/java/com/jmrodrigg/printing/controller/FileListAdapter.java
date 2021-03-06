package com.jmrodrigg.printing.controller;

import com.jmrodrigg.printing.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Author: jrodriguezg
 * Date: 03/06/16.
 */
public class FileListAdapter extends ArrayAdapter<File> {

    Context mContext;
    int mResourceId;
    List<File> mObjects;

    public FileListAdapter(Context context, int resource, List<File> objects) {
        super(context, resource, objects);

        mContext = context;
        mResourceId = resource;
        mObjects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imgIcon;
        TextView txtName, txtSize, txtDate;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResourceId, null);
        }

        File file = mObjects.get(position);
        if (file != null) {
            imgIcon = (ImageView) convertView.findViewById(R.id.ImgViewFiletype);
            txtName = (TextView) convertView.findViewById(R.id.txtFileName);
            txtSize = (TextView) convertView.findViewById(R.id.txtSize);
            txtDate = (TextView) convertView.findViewById(R.id.txtDate);

            int resource = R.drawable.type_generic;

            String fileName = file.getName();
            long size = file.length();
            long lastModified = file.lastModified();

            if (file.isDirectory()) {
                resource = R.drawable.type_folder;
                txtSize.setText("");
            } else {
                // Determine mime type:
                if (fileName.toUpperCase().endsWith(".PDF")) {
                    // PDF:
                    resource = R.drawable.type_pdf;
                } else if (fileName.toUpperCase().endsWith(".JPG") || file.getName().toUpperCase().endsWith(".JPEG")) {
                    // JPG:
                    resource = R.drawable.type_jpg;
                } else if (fileName.toUpperCase().endsWith(".PNG")) {
                    // PNG:
                    resource = R.drawable.type_png;
                }

                // If file, set size:
                if (size <= 1024)
                    txtSize.setText(mContext.getString(R.string.file_size_bytes, size));
                else if (size <= (1024 * 1024))
                    txtSize.setText(mContext.getString(R.string.file_size_Kbytes, String.format(Locale.getDefault(), "%.2f", (float) size / 1024)));
                else
                    txtSize.setText(mContext.getString(R.string.file_size_Mbytes, String.format(Locale.getDefault(), "%.2f", (float) size / (1024 * 1024))));
            }

            // Set Icon:
            imgIcon.setImageResource(resource);
            // Set name:
            txtName.setText(fileName);
            // Set Last Modified date:
            SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy - hh:mm", Locale.getDefault());
            txtDate.setText(sd.format(new Date(lastModified)));

        }

        return convertView;
    }
}
