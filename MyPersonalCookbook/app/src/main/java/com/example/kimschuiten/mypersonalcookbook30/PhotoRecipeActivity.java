package com.example.kimschuiten.mypersonalcookbook30;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoRecipeActivity extends AppCompatActivity {

    EditText photoTitleEditText;
    EditText photoCategoryEditText;
    Button takePhotoButton;
    ImageView extraPictureButton;
    ImageView recipeImageView;
    ImageView extraPictureImageView;

    static final int CAM_REQUEST = 1;
    private static int RESULT_LOAD_IMG = 1;
    Context context = this;
    String mCurrentPhotoPath;
    String mDishPicturePath;
    RecipeDatabaseHelper recipeDatabaseHelper;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_recipe);

        photoTitleEditText = (EditText) findViewById(R.id.titleEditText2);
        photoCategoryEditText = (EditText) findViewById(R.id.categoryEditText2);
        takePhotoButton = (Button) findViewById(R.id.takePhoto);
        recipeImageView = (ImageView) findViewById(R.id.recipePictureImageView);
        extraPictureButton = (ImageView) findViewById(R.id.addExtraPictureButton);
        extraPictureImageView = (ImageView) findViewById(R.id.showExtraPicture);

        Intent photoIntent = getIntent();
    }


    /*
 Open pop up menu to choose between photo or picture gallery
  */
    public void onAddPictureClick(View view) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.inflate(R.menu.picture_menu);
        popupMenu.show();

        // Set onclicklistener for the two options
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Open camera when clicked on "Take Photo"
                if (item.getItemId() == R.id.item1) {
                    // Start camera intent
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    // Ensure that there's a camera activity to handle the intent
                    if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                        // Create the file where the photo should go
                        File photo = null;
                        try {
                            photo = createImageFile();
                        } catch (IOException ex) {
                            // Error occurred while creating the File
                        }
                        // Continue only if the File was successfully created
                        if (photo != null) {
                            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
                            startActivityForResult(cameraIntent, CAM_REQUEST);
                        }
                    }
                }
                // Go to gallery when clicked on "Gallery"
                if (item.getItemId() == R.id.item2) {
                    // Create intent to open Gallery
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    // Start the Intent
                    startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
                }
                return false;
            }
        });
    }

    public void HomeButtonClick(View view) {
        // Go back to main screen
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO: If gallery option was choosen:
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mDishPicturePath = cursor.getString(columnIndex);
                cursor.close();
                // Set the Image in ImageView after decoding the String
                extraPictureImageView.setImageBitmap(BitmapFactory
                        .decodeFile(mDishPicturePath));
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
        // TODO: else:
        /*// Get the image and put it in the ImageView
        extraPictureImageView.setImageURI(Uri.parse(mCurrentPhotoPath));
*/
    }

    /*
    Create a folder where the photos will be stored.
    Also a new file name for each new photo
   */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        Log.e("PHOTO PATH1", mCurrentPhotoPath);

        return image;
    }

    /*
    Save title, category en text to Recipe Object
     */
    public void saveRecipeButtonClick(View view){
        // Get the information from the EditTexts
        String title = photoTitleEditText.getText().toString();
        String category = photoCategoryEditText.getText().toString();
        String photoRecipe = String.valueOf(Uri.parse(mCurrentPhotoPath));
        // Get the path of the photo as a String
        String photo = String.valueOf(Uri.parse(mDishPicturePath));

        // Initialize recipe db object + sqlitedatabase object
        recipeDatabaseHelper = new RecipeDatabaseHelper(context);
        sqLiteDatabase = recipeDatabaseHelper.getWritableDatabase();

        // Perform database insertion
        recipeDatabaseHelper.addPhotoRecipeInfo(photoRecipe, title, category, photo, sqLiteDatabase);

        // Close the database
        Toast.makeText(getBaseContext(), "Recipe Saved!", Toast.LENGTH_SHORT).show();
        recipeDatabaseHelper.close();
    }

    public void takePhotoClick(View view) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.inflate(R.menu.picture_menu);
        popupMenu.show();

        // Set onclicklistener for the two options
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Open camera when clicked on "Take Photo"
                if (item.getItemId() == R.id.item1_1) {
                    // Start camera intent
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    // Ensure that there's a camera activity to handle the intent
                    if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                        // Create the file where the photo should go
                        File photo = null;
                        try {
                            photo = createImageFile();
                        } catch (IOException ex) {
                            // Error occurred while creating the File
                        }
                        // Continue only if the File was successfully created
                        if (photo != null) {
                            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
                            startActivityForResult(cameraIntent, CAM_REQUEST);
                        }
                    }
                }
                // Go to gallery when clicked on "Gallery"
                if (item.getItemId() == R.id.item2_1) {
                    // Create intent to open Gallery
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    // Start the Intent
                    startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
                }
                return false;
            }
        });
    }
}