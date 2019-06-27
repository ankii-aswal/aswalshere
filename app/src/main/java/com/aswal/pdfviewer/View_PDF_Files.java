package com.aswal.pdfviewer;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class View_PDF_Files extends AppCompatActivity {
    ListView myPDFListView;
    DatabaseReference databaseReference;
    List<uploadPDF> uploadPDFS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__pdf__files);
        getSupportActionBar().setTitle("My Books");
        myPDFListView=(ListView)findViewById(R.id.myListView);
        uploadPDFS= new ArrayList<>();

        viewAllFiles();

        myPDFListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                uploadPDF uploadPDF=uploadPDFS.get(position);

                Intent intent=new Intent();
                intent.setData(Uri.parse(uploadPDF.getUrl()));
                startActivity(intent);
            }
        });
    }

    private void viewAllFiles() {
        databaseReference= FirebaseDatabase.getInstance().getReference("uploads");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    uploadPDF uploadPDF=postSnapshot.getValue(com.aswal.pdfviewer.uploadPDF.class);
                    uploadPDFS.add(uploadPDF);
                }
                String[] uploads=new String[uploadPDFS.size()];
                for(int i=0;i<uploads.length;i++){
                    uploads[i]=uploadPDFS.get(i).getName(); //getName is a method declared in uploadPDF class.
                }

                ArrayAdapter<String> adapter= new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, uploads){
                    @Override
                    public View getView(int position,  View convertView, ViewGroup parent) {

                        View view=super.getView(position, convertView, parent);
                       // TextView myText=(TextView)findViewById(R.id.txt_pdfName);  // Check for error
                       // myText.setTextColor(Color.GREEN);
                        return super.getView(position, convertView, parent);
                    }
                };
                myPDFListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
