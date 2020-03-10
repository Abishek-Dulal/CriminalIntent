package com.bignerdranch.android.criminalintent

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import layout.CrimeListModel
import java.util.*
import javax.security.auth.callback.Callback

class CrimeListFragment:Fragment(){

    private lateinit var crimeRecyclerView:RecyclerView
    private var adapter: CrimeAdapter? = CrimeAdapter(emptyList())

    private val crimeListModel:CrimeListModel by activityViewModels();

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_crime_list,container,false)
        crimeRecyclerView =view.findViewById(R.id.crime_recyler_view) as RecyclerView
        crimeRecyclerView.layoutManager   = LinearLayoutManager(context)
        crimeRecyclerView.adapter=adapter
        return view;

    }

    private fun updateUi(crimes:List<Crime>) {
        adapter = CrimeAdapter(crimes)
        crimeRecyclerView.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        crimeListModel.crimelistLiveData.observe(viewLifecycleOwner,Observer{
             crimes-> crimes?.let {
            Log.d("bongo","This is done")
              updateUi(crimes)
            }
        })
    }

    companion object{
        fun newInstance():CrimeListFragment{
            return CrimeListFragment()
        }
    }

     private inner class  CrimeHolder(view:View):RecyclerView.ViewHolder(view),View.OnClickListener{

         val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
         val dateTextView: TextView = itemView.findViewById(R.id.crime_date)
         val solvedImageView:ImageView=itemView.findViewById(R.id.crime_solved)

         init {
             itemView.setOnClickListener(this)
         }

         private lateinit var crime: Crime

         fun bind(crime: Crime) {
             this.crime = crime
             titleTextView.text = this.crime.title
             dateTextView.text = this.crime.date.toString()
             solvedImageView.visibility= if(crime.isSolved)View.VISIBLE else View.GONE
         }

         override fun onClick(p0: View?) {
             callbacks?.onCrimeSelected(crime.id)
         }


     }

    private  inner class CrimeAdapter(var crimes:List<Crime>):RecyclerView.Adapter<CrimeHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
         val view = layoutInflater.inflate(R.layout.list_item_crime,parent,false)
        return CrimeHolder(view);
        }

        override fun getItemCount()= crimes.size

        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime=crimes[position]
            holder.bind(crime)
        }

    }


    interface  Callbacks{
        fun onCrimeSelected(crimeId:UUID)
    }

    private var  callbacks:Callbacks?=null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks=context as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks=null
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_crime_list,menu);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_crime -> {
                val crime = Crime()
                crimeListModel.addCrime(crime)
                callbacks?.onCrimeSelected(crime.id)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
}