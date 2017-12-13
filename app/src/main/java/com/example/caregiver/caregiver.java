package com.example.caregiver;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * Created by rzhan on 10/22/17.
 */


public class caregiver extends AppCompatActivity {


    public static final int DEFAULT_MSG_LENGTH_LIMIT = 10;
    public static final String MESSAGES_CHILD = "hints";
    private static final String MESSAGE_SENT_EVENT = "message_sent";
    private Button mSendButton;
    private Button mSendButton1;
    private EditText mMessageEditText;
    private ImageView imageToUpload;
    private StorageReference myStorage;
    private DatabaseReference myRef;
    private FirebaseDatabase mFirebaseDatabase;
    private TextView textView;
    private FirebaseAnalytics mFirebaseAnalytics;

    private DatabaseReference mFirebaseDatabaseReference;
    private Uri selectedImage;

    public void onClick3(View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.caregiver);

        imageToUpload = (ImageView) findViewById(R.id.imageView3);
        textView = (TextView) findViewById(R.id.textView);
        mSendButton = (Button) findViewById(R.id.button);
        mSendButton1 = (Button) findViewById(R.id.button2);

        //imageToUpload.setOnClickListener(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mSendButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "btn_send");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Next Activity");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Button");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                Intent intent = new Intent(caregiver.this, Map.class);
                startActivity(intent);
            }
        });


        mMessageEditText = (EditText) findViewById(R.id.editText);

        mMessageEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();


        myRef.child("message").child("response").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                textView.setText(dataSnapshot.getValue().toString());
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                textView.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void sendMessege(View view) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mFirebaseDatabaseReference = database.getReference("message");
        String Message = mMessageEditText.getText().toString();
        mFirebaseDatabaseReference.child("message").push().setValue(Message);
        mFirebaseDatabaseReference.child("response").push().setValue("");
        mMessageEditText.setText("");
    }

    @Override // when click image, here will be called
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
        }
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            selectedImage = data.getData();   // uniform resours address of the image in the phone
            imageToUpload.setImageURI(selectedImage);
            //myRef.child("hi").push().setValue("yeah");
        }
    }

    public void sendImage(View view) { // send pic to somewhre

        FirebaseStorage storage = FirebaseStorage.getInstance();
        myStorage = storage.getReference(selectedImage.getLastPathSegment());
        myStorage.putFile(selectedImage).addOnCompleteListener(this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                @SuppressWarnings("VisibleForTests")
                Uri downloadUrl = task.getResult().getDownloadUrl();
                String url = downloadUrl.toString();
                myRef.child("message").child("url").push().setValue(url);
            }
        });
    }
}