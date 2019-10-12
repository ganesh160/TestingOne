package com.example.testingone.workingwithAdunits;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Camera {

    public static final String IMAGE_JPG = "jpg";
    public static final String IMAGE_JPEG = "jpeg";
    public static final String IMAGE_PNG = "png";
    private static final String TAG = "Camera";
    /**
     * default values used by camera
     */
    private static final String IMAGE_FORMAT_JPG = ".jpg";
    private static final String IMAGE_FORMAT_JPEG = ".jpeg";
    private static final String IMAGE_FORMAT_PNG = ".png";
    private static final int IMAGE_HEIGHT = 1000;
    private static final int IMAGE_COMPRESSION = 75;
    private static final String IMAGE_DEFAULT_DIR = "capture";
    private static final String IMAGE_DEFAULT_NAME = "img_";
    /**
     * public variables to be used in the builder
     */
    public static int REQUEST_TAKE_PHOTO = 1234;
    /**
     * Private variables
     */
    private Context context;
    private Activity activity;
    private Fragment fragment;
    private androidx.fragment.app.Fragment compatFragment;
    private String cameraBitmapPath = null;
    private Bitmap cameraBitmap = null;
    private String dirName;
    private String imageName;
    private String imageType;
    private int imageHeight;
    private int compression;
    private boolean isCorrectOrientationRequired;
    private MODE mode;

    private String authority;
    private Uri uri;

    /**
     * @param builder to copy all the values from.
     */
    private Camera(Builder builder) {
        activity = builder.activity;
        context = builder.context;
        mode = builder.mode;
        fragment = builder.fragment;
        compatFragment = builder.compatFragment;
        dirName = builder.dirName;
        REQUEST_TAKE_PHOTO = builder.REQUEST_TAKE_PHOTO;
        imageName = builder.imageName;
        imageType = builder.imageType;
        isCorrectOrientationRequired = builder.isCorrectOrientationRequired;
        compression = builder.compression;
        imageHeight = builder.imageHeight;
        authority = context.getApplicationContext().getPackageName() + ".provider";
    }

    private void setUpIntent(Intent takePictureIntent) {
        File photoFile = CameraUtils.createImageFile(context, dirName, imageName, imageType);
        if (photoFile != null) {

            cameraBitmapPath = photoFile.getAbsolutePath();

            uri = FileProvider.getUriForFile(context, authority, photoFile);

            takePictureIntent.putExtra(
                    MediaStore.EXTRA_OUTPUT,
                    uri);
            List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                context.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
        } else {
            throw new NullPointerException("Image file could not be created");
        }
    }

    public Uri getUri(){
        return uri;
    }

    /**
     * Initiate the existing camera apps
     *
     * @throws NullPointerException
     */
    @SuppressWarnings("JavadocReference")
    public void takePicture() throws NullPointerException, IllegalAccessException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        switch (mode) {
            case ACTIVITY:
                if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
                    setUpIntent(takePictureIntent);
                    activity.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                } else {
                    throw new IllegalAccessException("Unable to open camera");
                }
                break;

            case FRAGMENT:
                if (takePictureIntent.resolveActivity(fragment.getActivity().getPackageManager()) != null) {
                    setUpIntent(takePictureIntent);
                    fragment.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                } else {
                    throw new IllegalAccessException("Unable to open camera");
                }
                break;

            case COMPAT_FRAGMENT:
                if (takePictureIntent.resolveActivity(compatFragment.getActivity().getPackageManager()) != null) {
                    setUpIntent(takePictureIntent);
                    compatFragment.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                } else {
                    throw new IllegalAccessException("Unable to open camera");
                }
                break;
                default:
                    if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
                        setUpIntent(takePictureIntent);
                        activity.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                    } else {
                        throw new IllegalAccessException("Unable to open camera");
                    }
                    break;
        }
    }

    /**
     * @return the saved bitmap path but scaling bitmap as per builder
     */
    public String getCameraBitmapPath() {
        Bitmap bitmap = getCameraBitmap();
        bitmap.recycle();
        return cameraBitmapPath;
    }

    /**
     * @return The scaled bitmap as per builder
     */
    public Bitmap getCameraBitmap() {
        return resizeAndGetCameraBitmap(imageHeight);
    }

    /**
     * @param imageHeight
     * @return Bitmap path with approx desired height
     */
    public String resizeAndGetCameraBitmapPath(int imageHeight) {
        Bitmap bitmap = resizeAndGetCameraBitmap(imageHeight);
        bitmap.recycle();
        return cameraBitmapPath;
    }

    /**
     * @param imageHeight
     * @return Bitmap with approx desired height
     */
    public Bitmap resizeAndGetCameraBitmap(int imageHeight) {
        try {
            if (cameraBitmap != null) {
                cameraBitmap.recycle();
            }
            cameraBitmap = CameraUtils.decodeFile(new File(cameraBitmapPath), imageHeight);
            if (cameraBitmap != null) {
                if (isCorrectOrientationRequired) {
                    cameraBitmap = CameraUtils.rotateBitmap(cameraBitmap, CameraUtils.getImageRotation(cameraBitmapPath));
                }
                CameraUtils.saveBitmap(cameraBitmap, cameraBitmapPath, imageType, compression);
            }
            return cameraBitmap;
        } catch (Exception e) {
            return null;
        }
    }

    public String compressImage(String imageUri) {

        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        //float maxHeight = 816.0f;
        //float maxWidth = 612.0f;

        //float maxHeight = 1920.0f;
        //float maxWidth = 1080.0f;

        float maxHeight = 2160.0f;
        float maxWidth = 3840.0f;


        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image
        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = cameraBitmapPath;
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;
    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = activity.getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    /**
     * Deletes the saved camera image
     */
    public void deleteImage() {
        if (cameraBitmapPath != null) {
            File image = new File(cameraBitmapPath);
            if (image.exists()) {
                image.delete();
            }
        }
    }

    private enum MODE {ACTIVITY, FRAGMENT, COMPAT_FRAGMENT}

    /**
     * Camera builder declaration
     */
    public static class Builder {
        private Context context;
        private Activity activity;
        private Fragment fragment;
        private androidx.fragment.app.Fragment compatFragment;
        private String dirName;
        private String imageName;
        private String imageType;
        private int imageHeight;
        private int compression;
        private boolean isCorrectOrientationRequired;
        private MODE mode;
        private int REQUEST_TAKE_PHOTO;

        public Builder(AppCompatActivity activity) {
            dirName = Camera.IMAGE_DEFAULT_DIR;
            imageName = Camera.IMAGE_DEFAULT_NAME + System.currentTimeMillis();
            imageHeight = Camera.IMAGE_HEIGHT;
            compression = Camera.IMAGE_COMPRESSION;
            imageType = Camera.IMAGE_FORMAT_JPG;
            REQUEST_TAKE_PHOTO = Camera.REQUEST_TAKE_PHOTO;
            this.activity = activity;
        }

        public Builder setDirectory(String dirName) {
            if (dirName != null)
                this.dirName = dirName;
            return this;
        }

        public Builder setTakePhotoRequestCode(int requestCode) {
            this.REQUEST_TAKE_PHOTO = requestCode;
            return this;
        }

        public Builder setName(String imageName) {
            if (imageName != null)
                this.imageName = imageName;
            return this;
        }

        public Builder resetToCorrectOrientation(boolean reset) {
            this.isCorrectOrientationRequired = reset;
            return this;
        }

        public Builder setImageFormat(String imageFormat) {

            if (TextUtils.isEmpty(imageFormat)) {
                return this;
            }

            switch (imageFormat) {
                case "png":
                case "PNG":
                case ".png":
                    this.imageType = IMAGE_FORMAT_PNG;
                    break;
                case "jpg":
                case "JPG":
                case ".jpg":
                    this.imageType = IMAGE_FORMAT_JPG;
                    break;
                case "jpeg":
                case "JPEG":
                case ".jpeg":
                    this.imageType = IMAGE_FORMAT_JPEG;
                    break;
                default:
                    this.imageType = IMAGE_FORMAT_JPG;
            }
            return this;
        }

        public Builder setImageHeight(int imageHeight) {
            this.imageHeight = imageHeight;
            return this;
        }

        public Builder setCompression(int compression) {
            if (compression > 100) {
                compression = 100;
            } else if (compression < 0) {
                compression = 0;
            }
            this.compression = compression;
            return this;
        }

        public Camera build(Activity activity) {
            this.activity = activity;
            context = activity.getApplicationContext();
            mode = MODE.ACTIVITY;
            return new Camera(this);
        }

        public Camera build(Fragment fragment) {
            this.fragment = fragment;
            context = fragment.getActivity().getApplicationContext();
            mode = MODE.FRAGMENT;
            return new Camera(this);
        }

        public Camera build(androidx.fragment.app.Fragment fragment) {
            compatFragment = fragment;
            context = fragment.getActivity().getApplicationContext();
            mode = MODE.COMPAT_FRAGMENT;
            return new Camera(this);
        }
    }
}
