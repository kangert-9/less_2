package com.example.less_2.ui.main.model

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.less_2.R
import com.example.less_2.ui.main.view.MainFragment

class FilmAdapter (private var onItemViewClickListener: MainFragment.OnItemViewClickListener?): RecyclerView.Adapter<FilmAdapter.ViewHolder>(){
    private var filmList: List<Film> = ArrayList()

    fun removeListener() {
        onItemViewClickListener = null
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFilmList (filmList: List<Film>){
        this.filmList = filmList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filmList[position])
    }

    override fun getItemCount(): Int = filmList.size

    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(film: Film){
            val s: String = film.name+ " "+ film.rating.toString()
            itemView.findViewById<TextView>(R.id.textFilm).text = s
            itemView.setOnClickListener {
                onItemViewClickListener?.onItemViewClick(film)
            }
        }
    }
}