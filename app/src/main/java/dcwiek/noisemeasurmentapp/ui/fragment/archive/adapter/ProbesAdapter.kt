package dcwiek.noisemeasurmentapp.ui.fragment.archive.adapter

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import dcwiek.noisemeasurmentapp.R
import dcwiek.noisemeasurmentapp.domain.model.Probe

class ProbesAdapter(private val appContext: Context, private val list: List<Probe>):
    RecyclerView.Adapter<ProbeViewHolder>() {

    private lateinit var popupWindow : PopupWindow

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProbeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ProbeViewHolder(parent.context, inflater, parent)
    }

    override fun onBindViewHolder(holder: ProbeViewHolder, position: Int) {
        val newsFeed: Probe = list[position]
        holder.bind(newsFeed)
        holder.setButtonClickAction(View.OnClickListener { switchToDetailsFragment(appContext, holder.view) })
    }

    override fun getItemCount(): Int = list.size

    private fun switchToDetailsFragment(context: Context, view: View) {
        val popupView: View = LayoutInflater.from(context).inflate(R.layout.popup_chooseprobeinfo, null)
        val width = ConstraintLayout.LayoutParams.WRAP_CONTENT
        val height = ConstraintLayout.LayoutParams.WRAP_CONTENT
        val focusable = true
        popupWindow = PopupWindow(popupView, width, height, focusable)
        popupWindow.animationStyle = R.style.Animation
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
        (popupView.findViewById(R.id.popup_chooseprobeinfo_closebutton) as Button).setOnClickListener {
            //Return to previous fragment
            popupWindow.dismiss()
        }
    }

}