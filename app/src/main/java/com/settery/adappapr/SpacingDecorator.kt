import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
class SpacingDecorator(
    private val dividerHeight: Int,     // высота линии-разделителя (например, 1dp)
    private val marginTop: Int,        // отступ сверху до разделителя (например, 8dp)
    private val marginBottom: Int,     // отступ снизу после разделителя (например, 8dp)
    private val paddingStart: Int = 0, // отступ слева (например, 16dp)
    private val paddingEnd: Int = 0,   // отступ справа
    private val color: Int = Color.LTGRAY
) : RecyclerView.ItemDecoration() {

    private val paint = Paint().apply {
        this.color = color
        this.style = Paint.Style.FILL
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        // Рассчитываем границы разделителя
        val left = parent.paddingLeft + paddingStart
        val right = parent.width - parent.paddingRight - paddingEnd

        // Рисуем разделитель для всех элементов, кроме последнего
        for (i in 0 until parent.childCount - 1) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams

            // Позиция разделителя = нижний край элемента + marginTop
            val top = child.bottom + params.bottomMargin + marginTop
            val bottom = top + dividerHeight

            c.drawRect(
                left.toFloat(),
                top.toFloat(),
                right.toFloat(),
                bottom.toFloat(),
                paint
            )
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        // Общий отступ снизу = marginTop + dividerHeight + marginBottom
        outRect.bottom = marginTop + dividerHeight + marginBottom
    }
}