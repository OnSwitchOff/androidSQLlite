package org.lightoff.mysqlliteapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class CustomDialogFragment extends DialogFragment {

    ArrayList<String> filepath = new ArrayList<String>();//contains list of all files ending with
    String selectedImgPath;
    ProductActivity activity = (ProductActivity) getActivity();
    View view;

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

       if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            ///mounted
            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            walkdir(dir);

       }

        view= LayoutInflater.from(getContext()).inflate(R.layout.dialog, null, false);

        ListView pathList = (ListView) view.findViewById(R.id.imgListt);
        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1, filepath);
        Log.d("adapter", adapter.toString());
        Log.d("pathList", pathList.toString());
        // устанавливаем для списка адаптер
        pathList.setAdapter(adapter);
        // добвляем для списка слушатель
        pathList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                // по позиции получаем выбранный элемент
                selectedImgPath = filepath.get(position);
                Log.d("selected img",selectedImgPath);
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder
                .setTitle("imgList")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(getContext(),ProductActivity.class);
                        Bundle b = new Bundle();
                        b.putString("selectedImgPath", selectedImgPath); //sets new window
                        intent.putExtras(b);
                        startActivity(intent);
                    }
                })

                .setNegativeButton("Отмена", null)
                .create();

    }

    public void walkdir(File dir) {
        File listFile[] = dir.listFiles();

        if (listFile != null) {
            for (int i = 0; i < listFile.length; i++) {

                if (listFile[i].isDirectory()) {// if its a directory need to get the files under that directory
                    walkdir(listFile[i]);
                } else
                    {// add path of  files to your arraylist for later use
                    //Do what ever u want
                    filepath.add(listFile[i].getAbsolutePath());
                }
            }
        }
    }
}