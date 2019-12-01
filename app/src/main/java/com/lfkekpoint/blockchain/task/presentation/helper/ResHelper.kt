package com.lfkekpoint.blockchain.task.presentation.helper

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.raketa.im.lfkekpoint.utaircashbox.presentation.modules.application.appLifeClasses.AppState.appContext

object ResHelper {

    fun getDrawable(drawableResId: Int, ctx: Context = appContext): Drawable?{

        if (drawableResId != -1) return try {
            ContextCompat.getDrawable(ctx, drawableResId)

        } catch (e: Resources.NotFoundException) {
            VectorDrawableCompat.create(ctx.resources, drawableResId, null)
        }

        return null
    }

    fun getString(stringResId: Int, context: Context = appContext): String {
        return context.getString(stringResId)
    }

    fun getColor(colorResId: Int, appContext: Context): Int {
        return ContextCompat.getColor(appContext, colorResId)
    }

    fun getDimenPixels(dimenResId: Int, appContext: Context): Float {
        return appContext.resources.getDimension(dimenResId)
    }
}