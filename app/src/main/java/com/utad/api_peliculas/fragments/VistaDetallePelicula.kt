package com.utad.api_peliculas.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.squareup.picasso.Picasso
import com.utad.api_peliculas.R
import com.utad.api_peliculas.adapters.GenreAdapter
import com.utad.api_peliculas.api.ApiRest
import com.utad.api_peliculas.databinding.FragmentVistaDetallePeliculaBinding
import com.utad.api_peliculas.model.Generos
import com.utad.api_peliculas.model.Genre
import com.utad.api_peliculas.model.PeliculasResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class VistaDetallePelicula : Fragment() {

    private var _binding: FragmentVistaDetallePeliculaBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVistaDetallePeliculaBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    private var adapter: GenreAdapter? = null
    var data: ArrayList<Genre> = ArrayList()
    var loader: ProgressBar? = null
    var generosPeli: List<Int>? = null
    var data2: ArrayList<Genre> = arrayListOf()
    private lateinit var rvGenerosXPelicula: RecyclerView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pelicula: PeliculasResponse.Pelicula = arguments?.getSerializable("peli") as PeliculasResponse.Pelicula
        loader = binding.progreso
        generosPeli = pelicula.genre_ids
        getGenres()

        binding.anoPeli.text = pelicula.title + " - " + pelicula.release_date.substring(0,4)
        binding.argumento.text = pelicula.overview
        Picasso.get().load(ApiRest.URL_IMAGES + pelicula.backdrop_path).into(binding.imgPelicula)
        binding.valoracion.text = "Valoracion: " + pelicula.vote_average + " / 10"

        rvGenerosXPelicula = binding.rvGenerosPeli

        //val mLayoutManager = GridLayoutManager(this.context, 2)
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(3, LinearLayoutManager.HORIZONTAL)
        rvGenerosXPelicula.layoutManager = staggeredGridLayoutManager

        adapter = GenreAdapter(data2, R.layout.item_generos_xpelicula) {

            val fragment = VistaPeliculas()
            val bundle = Bundle()
            bundle.putSerializable("miGenero",it)
            fragment.arguments = bundle
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.mainContainer, fragment)?.addToBackStack("VistaGeneros")?.commit()
        }
        rvGenerosXPelicula.adapter = adapter


        // Toolbar
        val toolbar = binding.toolbar
        toolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    private fun getGenres() {
        val call = ApiRest.service.getGenres()
        call.enqueue(object : Callback<Generos> {
            override fun onResponse(call: Call<Generos>, response: Response<Generos>) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    data.clear()
                    data.addAll(body.genres)
                    adapter?.notifyItemRangeChanged(0,data.size)
                } else {
                    Log.e("VistaDetallePeliculas", response.errorBody()?.string() ?: "error")
                }
                data2.clear()
                for (genero: Genre in data){
                    if (genero.id in generosPeli!!){
                        data2.add(genero)
                    }
                }


                loader?.isVisible = false

            }

            override fun onFailure(call: Call<Generos>, t: Throwable) {
                Log.e("VistaDetallePeliculas", t.message.toString())
                loader?.isVisible = false
            }
        })
    }

    private fun asignarAdapter(data2: ArrayList<Genre>) {

    }
}