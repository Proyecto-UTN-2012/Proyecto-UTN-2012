package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.conociendoacali;




import org.utn.proyecto.helpful.integrart.integrar_t_android.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class PictureAdapter extends BaseAdapter {

    //use the default gallery background image
    int defaultItemBackground;
    //gallery context
    private Context galleryContext;
    //array to store bitmaps to display
    private Bitmap[] imageBitmaps;
    //placeholder bitmap for empty spaces in gallery
    Bitmap placeholder;

    public PictureAdapter (Context contexto)
    {
        galleryContext = contexto;
        imageBitmaps = new Bitmap[10];
        placeholder = BitmapFactory.decodeResource(galleryContext.getResources(), R.drawable.icon_trans);
        
        InitializeGallerySmallImages();
        SetBackgroundImage();
    }
    
    private void SetBackgroundImage() {
      //get the styling attributes - use default Android system resources
        TypedArray styleAttrs = galleryContext.obtainStyledAttributes(R.styleable.PicGallery);
        //get the background resource
        defaultItemBackground = styleAttrs.getResourceId(
            R.styleable.PicGallery_android_galleryItemBackground, 0);
        //recycle attributes
        styleAttrs.recycle();
    }

    private void InitializeGallerySmallImages() {
        
        imageBitmaps[0] = BitmapFactory.decodeResource(galleryContext.getResources(), R.drawable.icon_trans);
        imageBitmaps[1] = BitmapFactory.decodeResource(galleryContext.getResources(), R.drawable.cachorros);
        imageBitmaps[2] = BitmapFactory.decodeResource(galleryContext.getResources(), R.drawable.rubber);
        imageBitmaps[3] = BitmapFactory.decodeResource(galleryContext.getResources(), R.drawable.avion);
        imageBitmaps[4] = BitmapFactory.decodeResource(galleryContext.getResources(), R.drawable.cuentos_camara);
        imageBitmaps[5] = BitmapFactory.decodeResource(galleryContext.getResources(), R.drawable.green_led);
        imageBitmaps[6] = BitmapFactory.decodeResource(galleryContext.getResources(), R.drawable.trash_can_medium);
        imageBitmaps[7] = BitmapFactory.decodeResource(galleryContext.getResources(), R.drawable.ricitos_pg1);
        imageBitmaps[8] = BitmapFactory.decodeResource(galleryContext.getResources(), R.drawable.notebook_background);
        imageBitmaps[9] = BitmapFactory.decodeResource(galleryContext.getResources(), R.drawable.dedo);
        
    }

    @Override
    public int getCount() {
        return imageBitmaps.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //create the view
        ImageView imageView = new ImageView(galleryContext);
        //specify the bitmap at this position in the array
        imageView.setImageBitmap(imageBitmaps[position]);
        //set layout options
        imageView.setLayoutParams(new Gallery.LayoutParams(300, 200));
        //scale type within view area
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        //set default gallery item background
        imageView.setBackgroundResource(defaultItemBackground);
        //return the view
        return imageView;
    }

    public Bitmap getPic(int position) {
      //return bitmap at position index
        return imageBitmaps[position];
    }

}
