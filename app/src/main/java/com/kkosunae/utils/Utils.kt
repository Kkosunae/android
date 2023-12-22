package com.kkosunae.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import java.util.Objects

object Utils {

    fun replaceBackgroundInLayerList(context : Context, drawable : Drawable?, layerListId : Int, layerIdToReplace : Int) : Drawable {
        var layerDrawable: LayerDrawable = context.getDrawable(layerListId) as LayerDrawable
        layerDrawable.setDrawableByLayerId(layerIdToReplace, drawable)
        return layerDrawable
    }
}