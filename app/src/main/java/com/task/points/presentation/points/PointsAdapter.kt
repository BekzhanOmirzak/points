package com.task.points.presentation.points

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.task.points.R
import com.task.points.domain.model.Point

class PointsAdapter : RecyclerView.Adapter<PointsAdapter.PointViewHolder>() {

    private val items = mutableListOf<Point>()

    fun submitList(newPoints: List<Point>) {
        items.clear()
        items.addAll(newPoints)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.point_item, parent, false)
        return PointViewHolder(view)
    }

    override fun onBindViewHolder(holder: PointViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class PointViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvX: TextView = itemView.findViewById(R.id.txtX)
        private val tvY: TextView = itemView.findViewById(R.id.txtY)

        fun bind(point: Point) {
            tvX.text = "%.2f".format(point.x)
            tvY.text = "%.2f".format(point.y)
        }
    }

}