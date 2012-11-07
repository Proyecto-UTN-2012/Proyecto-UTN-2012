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
    
    private int[] drawableResource;
    private int currentSelection;

    public PictureAdapter (Context contexto)
    {
        galleryContext = contexto;
        imageBitmaps = new Bitmap[6];
        drawableResource = new int[6];
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
        
        imageBitmaps[0] = BitmapFactory.decodeResource(galleryContext.getResources(), R.drawable.cali1);
        imageBitmaps[1] = BitmapFactory.decodeResource(galleryContext.getResources(), R.drawable.cali2);
        imageBitmaps[2] = BitmapFactory.decodeResource(galleryContext.getResources(), R.drawable.cali3);
        imageBitmaps[3] = BitmapFactory.decodeResource(galleryContext.getResources(), R.drawable.cali4);
        imageBitmaps[4] = BitmapFactory.decodeResource(galleryContext.getResources(), R.drawable.cali5);
        imageBitmaps[5] = BitmapFactory.decodeResource(galleryContext.getResources(), R.drawable.cali6);
        
        
        drawableResource[0] = R.drawable.icon_trans;
        drawableResource[1] = R.drawable.cachorros;
        drawableResource[2] = R.drawable.rubber;
        drawableResource[3] = R.drawable.avion;
        drawableResource[4] = R.drawable.cuentos_camara;
        drawableResource[5] = R.drawable.green_led;
        
        currentSelection = 0;
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
        //specify the current selection.
        currentSelection = drawableResource[position];
        
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
    
    public int getDrawbleResourceSelected(){
        return currentSelection;
    }
    
    public void moveCarruselPosition(int id)
    {
        for (int i=0; i< drawableResource.length;i++){
            if (drawableResource[i]==id)
            {
                getView(i, null, null);
            }
        }
    }

    public Bitmap getPic(int position) {
      //return bitmap at position index
        return imageBitmaps[position];
    }

}
