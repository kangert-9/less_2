package com.example.less_2.ui.main.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.less_2.R

class FilmAdapter : RecyclerView.Adapter<FilmAdapter.ViewHolder>(){
    private var filmList: List<Film> = ArrayList()

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

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(film: Film){
            itemView.findViewById<TextView>(R.id.textFilm).text = {film.name+" "+film.rating}.toString()
        }
    }
}