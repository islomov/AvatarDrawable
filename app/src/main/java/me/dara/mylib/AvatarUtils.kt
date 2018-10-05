package me.dara.mylib

import android.content.res.Resources
import android.graphics.Color
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint


/**
 * @author sardor
 */
class AvatarUtils {

  companion object {

    fun dp(dp: Int): Int {
      val metrics = Resources.getSystem().displayMetrics
      val px = dp * (metrics.densityDpi / 160f)
      return Math.round(px)
    }

    fun sp(sp: Float): Float {
      return sp * Resources.getSystem().displayMetrics.density
    }

    fun darker(color: Int): Int {
      val hsv = FloatArray(3)
      Color.colorToHSV(color,hsv)
      hsv[2] *=0.8f
      return Color.HSVToColor(hsv)
    }

    private fun lightenColor(color: Int, fraction: Double): Int {
      return Math.min(color + color * fraction, 255.0).toInt()
    }

    fun calculateTextLayout(textPaint: TextPaint,src:String,maxWidth: Float): StaticLayout{
      var width = textPaint.measureText(src)
      width = Math.min(width,maxWidth)
      return StaticLayout(src,textPaint,width.toInt(),Layout.Alignment.ALIGN_NORMAL,
          1f,0f,true)
    }

  }





}

