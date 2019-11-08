package com.zybooks.to_dolist;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ToDoList {

    public static final String FILENAME = "todolist.txt";

    private Context mContext;    //
    private List<String> mList;  // Contains all to-do list items

    // Constructor
    public ToDoList(Context context) {
        mContext = context;
        mList = new ArrayList<>();
    }

    // Adds a specified item to the to-do list
    public void addItem(String item) {
        mList.add(item);
    }

    // Returns an String array containing all to-do list items
    public String[] getItems() {
        String[] items = new String[mList.size()];
        return mList.toArray(items);
    }

    // Removes all items from the to-do list
    public void clear() {
        mList.clear();
    }

    // Writes the entire to-do list to internal storage
    public void saveToFile() throws IOException {

        // Write list to internal file
        FileOutputStream outputStream = mContext.openFileOutput(FILENAME, Context.MODE_PRIVATE);
        writeListToStream(outputStream);
    }

    // Reads the entire to-do list from internal storage.
    // Populates mList with all to-do list items.
    public void readFromFile() throws IOException {

        BufferedReader reader = null;

        try {
            // Read in list from internal file
            FileInputStream inputStream = mContext.openFileInput(FILENAME);
            reader = new BufferedReader(new InputStreamReader(inputStream));

            mList.clear();

            String line;
            while ((line = reader.readLine()) != null) {
                mList.add(line);
            }
        }
        catch (FileNotFoundException ex) {
            // Ignore
        }
        finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    // Writes the entire to-do list to external storage
    public boolean downloadFile() throws IOException {

        // Make sure SD card is available
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {

            // Get the location of the Downloads directory
            File downloadsDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS);

            // Create the Downloads directory if it doesn't exist
            if (!downloadsDir.exists()) {
                downloadsDir.mkdirs();
            }

            // Save list to Downloads directory
            File file = new File(downloadsDir, FILENAME);
            writeListToStream(new FileOutputStream(file));

            return true;
        }

        return false;
    }

    // Writes the entire to-do list to a specified file output stream
    private void writeListToStream(FileOutputStream outputStream) {
        PrintWriter writer = new PrintWriter(outputStream);
        for (String item : mList) {
            writer.println(item);
        }
        writer.close();
    }
}