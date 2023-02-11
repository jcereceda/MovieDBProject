package com.utad.api_peliculas.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.appbar.MaterialToolbar
import com.utad.api_peliculas.R
import com.utad.api_peliculas.adapters.GenreAdapter
import com.utad.api_peliculas.api.ApiRest
import com.utad.api_peliculas.model.Generos
import com.utad.api_peliculas.model.Genre
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class VistaGeneros : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vista_generos, container, false)
    }


    private lateinit var rvGeneros: RecyclerView
    val TAG = "VistaGeneros"
    var data: ArrayList<Genre> = ArrayList()
    private var adapter: GenreAdapter? = null
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.title = "Todos los generos"
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)
        rvGeneros = view.findViewById<RecyclerView>(R.id.rvGeneros)

        val mLayoutManager = GridLayoutManager(this.context, 2)
        rvGeneros.layoutManager = mLayoutManager
        //Creamos el adapter y lo vinculamos con el recycle

        adapter = GenreAdapter(data, R.layout.simple_item) {

            val fragment = VistaPeliculas()
            val bundle = Bundle()
            bundle.putSerializable("miGenero",it)
            fragment.arguments = bundle
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.mainContainer, fragment)?.addToBackStack("VistaGeneros")?.commit()
        }
        rvGeneros.adapter = adapter
        ApiRest.initService()
        getGenres()

        swipeRefreshLayout.setOnRefreshListener {
            getGenres()
        }


    }
    private fun getGenres() {
        val call = ApiRest.service.getGenres()
        call.enqueue(object : Callback<Generos> {
            override fun onResponse(call: Call<Generos>, response: Response<Generos>) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.i(TAG, body.toString())
                    data.clear()
                    data.addAll(body.genres)
                    Log.i(TAG,data.toString())
                    adapter?.notifyItemRangeChanged(0,data.size)
                } else {
                    Log.e(TAG, response.errorBody()?.string() ?: "error")
                }

                //loader?.isVisible = false
                swipeRefreshLayout.isRefreshing = false
            }

            override fun onFailure(call: Call<Generos>, t: Throwable) {
                Log.e(TAG, t.message.toString())
                // loader?.isVisible = false
                swipeRefreshLayout.isRefreshing= false
            }
        })
    }
}