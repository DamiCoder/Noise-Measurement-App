package dcwiek.noisemeasurmentapp.ui.fragment.common.popup

import android.content.Context
import android.content.res.Resources
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import dcwiek.noisemeasurmentapp.R
import kotlinx.android.synthetic.main.popup_info.view.*

class PopupUtil {
    companion object {
        fun createInfoPopup(context: Context, parentView: View, header: String, content: String) {
            val popupView: View = LayoutInflater.from(context).inflate(R.layout.popup_info, null)
            popupView.popup_header.text = header
            popupView.popup_comment.text = content

            val focusable = true
            val popupWindow = PopupWindow(popupView, 350.toPx(), 250.toPx(), focusable)
            popupWindow.animationStyle = R.style.Animation
            popupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0)
            popupView.popup_okbutton.setOnClickListener {
                popupWindow.dismiss()
            }
        }

        private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
    }
}