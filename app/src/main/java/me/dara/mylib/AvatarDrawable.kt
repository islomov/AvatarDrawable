package me.dara.mylib

import android.graphics.*
import android.graphics.drawable.Drawable
import android.text.Layout
import android.text.TextPaint


/**
 * @author sardor
 */
class AvatarDrawable(builder: Builder) : Drawable() {

  //TODO if textsize is bigger decrease its size until it fits width and height of view
  //TODO functionality for changing gradient shadow

  var textX = 0f
  var textY = 0f
  var width = 0
  var height = 0
  var textLayout: Layout? = null
  val textPaint: TextPaint by lazy {
    TextPaint(Paint.ANTI_ALIAS_FLAG)
  }
  val paint: Paint by lazy {
    Paint(Paint.ANTI_ALIAS_FLAG)
  }

  init {
    val startColor = builder.startColor!!
    val endColor = builder.endColor!!

    width = builder.width!!.toInt()
    height = builder.height!!.toInt()
    val firstName = builder.firstName!!
    val lastName = builder.lastName!!

    width = Math.max(width,height)
    height = width

    textPaint.color = builder.textColor
    textPaint.typeface = Typeface.defaultFromStyle(builder.fontWeight)
    textPaint.textSize = builder.textSize
    setBounds(0, 0, width, height)

    val gradient = LinearGradient(width / 2.toFloat(), height.toFloat(),
        width / 2.toFloat(), 0f, endColor,
        startColor, Shader.TileMode.CLAMP)
    var finalText = ""
    if (firstName.isNotEmpty()) {
      finalText += firstName[0]
    }
    if (lastName.isNotEmpty())
      finalText += lastName[0]
    finalText = finalText.toUpperCase()
    paint.shader = gradient
    textLayout = AvatarUtils.calculateTextLayout(textPaint,finalText,width.toFloat())
    val textW = textLayout?.width!!
    val textH = textLayout?.height!!
    textX = (width.div(2) - textW.div(2)).toFloat()
    textY = (height.div(2) - textH.div(2)).toFloat()
  }


  override fun draw(canvas: Canvas?) {
    canvas?.drawCircle(width/2.toFloat(),height/2.toFloat(),
        width/2.toFloat(),paint)
    canvas?.translate(textX,textY)
    textLayout?.draw(canvas)
  }

  override fun setAlpha(alpha: Int) {
  }

  override fun getOpacity() = PixelFormat.TRANSPARENT


  override fun setColorFilter(colorFilter: ColorFilter?) {

  }


  class Builder {
    var textSize = AvatarUtils.sp(12f)
    var startColor :Int? = null
    var endColor :Int? = null
    var width: Float? = null
    var height: Float? = null
    var fontWeight = Typeface.NORMAL
    var firstName: String? = null
    var lastName: String? = null
    var textColor = Color.WHITE

    fun textColor(color: Int): Builder {
      this.textColor = color
      return this
    }

    fun textColor(color: String): Builder {
      this.textColor = Color.parseColor(color)
      return this
    }

    fun firstName(firstName: String): Builder {
      this.firstName = firstName
      return this
    }

    fun fontWeight(fontWeight: Int): Builder{
      this.fontWeight = fontWeight
      return this
    }

    fun lastName(lastName: String): Builder {
      this.lastName = lastName
      return this
    }

    fun textSize(textSize: Int): Builder {
      this.textSize = AvatarUtils.sp(textSize.toFloat())
      return this
    }

    fun startColor(startColor: String): Builder {
      this.startColor = Color.parseColor(startColor)
      return this
    }

    fun startColor(startColor: Int): Builder {
      this.startColor = startColor
      return this
    }

    fun endColor(endColor: String): Builder {
      this.endColor = Color.parseColor(endColor)
      return this
    }

    fun endColor(endColor: Int): Builder {
      this.endColor = endColor
      return this
    }

    fun width(width: Int): Builder {
      this.width = AvatarUtils.dp(width).toFloat()
      return this
    }

    fun height(height: Int): Builder {
      this.height = AvatarUtils.dp(height).toFloat()
      return this
    }

    fun create(): AvatarDrawable {
      if (startColor == null)
        throw NullPointerException("Start color is null")
      if (endColor == null)
        throw NullPointerException("End color is null")
      if (width == null)
        throw NullPointerException("Width is null")
      if (height == null)
        throw NullPointerException("Height is null")
      if (firstName == null)
        throw NullPointerException("First name is null")
      if (lastName == null)
        throw NullPointerException("Last name is null")
      return AvatarDrawable(this)
    }
  }


}