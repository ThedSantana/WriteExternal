package com.example.apollo.writeext;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private EditText commentEditText;
    private static final int BLOCK_SIZE = 128;

    // Here we define file path on the internal memory
    String path, sdCardPath, destPath;
    String dirName="mg_comments/";

    // Here we define full file name with its path
    String commentFileName = "comments.txt";

    File file;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container,
                false);


        path = getActivity().getFilesDir().getAbsolutePath() + "/";
        //commentFileName = path + commentFileName;

        commentEditText =(EditText) rootView.findViewById(R.id.et_comment);
        Button saveButton =(Button) rootView.findViewById(R.id.btn_save);
        Button loadButton =(Button) rootView.findViewById(R.id.btn_load);


        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String text = commentEditText.getText().toString();
                try {

                    file = new File(commentFileName);

                    //Here we make the file readable by other applications too.
                    file.setReadable(true, false);

                    FileOutputStream fileOutputStream = getActivity().openFileOutput(commentFileName, Context.MODE_PRIVATE);

                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);

                    //Here we write the text to the file
                    outputStreamWriter.write(text);
                    outputStreamWriter.close();


                    Toast.makeText(getView().getContext(), getString(R.string.file_save_fb), Toast.LENGTH_LONG).show();


                    commentEditText.setText("");


                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }



                // TODO Auto-generated method stub

            }
        });



        loadButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                FileInputStream fileInputStream;
                InputStreamReader inputStreamReader;

                try {
                    fileInputStream = getActivity().openFileInput(commentFileName);

                    inputStreamReader = new InputStreamReader(fileInputStream);


                    char[] inputBuffer = new char[BLOCK_SIZE];
                    String fileContent="";


                    int charRead;

                    while((charRead = inputStreamReader.read(inputBuffer))>0) {

                        //Here we convert chars to string
                        String readString =String.copyValueOf(inputBuffer, 0, charRead);
                        fileContent+=readString;


                        //Here we re-initialize the inputBuffer array to remove its content
                        inputBuffer = new char[BLOCK_SIZE];

                    }


                    //Here we set the text of the commentEditText to the one, which has been read
                    commentEditText.setText(fileContent);


                    Toast.makeText(getView().getContext(), getString(R.string.file_load_fb)+" "+path+commentFileName, Toast.LENGTH_LONG).show();





                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            }
        });




        return inflater.inflate(R.layout.fragment_main, container, false);
    }
}
