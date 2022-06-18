package com.nathit.kotlin_retrofit_github

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nathit.kotlin_retrofit_github.Adapter.EventsAdapter
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


class EventFragment : Fragment() {

    private lateinit var eventsAdapter: EventsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var rvEvn: RecyclerView
    lateinit var loadingDialog: ProgressDialog

    var mSwipeRefreshLayout: SwipeRefreshLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_event, container, false)

        rvEvn = view.findViewById(R.id.rv)
        linearLayoutManager = LinearLayoutManager(context)
        rvEvn.layoutManager = linearLayoutManager

        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)
        mSwipeRefreshLayout!!.setOnRefreshListener {
            getEventData()
            mSwipeRefreshLayout!!.isRefreshing = false
        }
        getEventData()

        return view
    }

    private fun getEventData() {
        loadingDialog = ProgressDialog.show(context, "กำลังโหลด", "รอสักครู่...", true, false)
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.github.com")
            .build()
            .create(ApiEventInterface::class.java)

        val retrofitData = retrofitBuilder.loadEvent()

        retrofitData.enqueue(object : Callback<List<EventModel>?> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<List<EventModel>?>,
                response: Response<List<EventModel>?>
            ) {
                loadingDialog.dismiss()
                val responsBody = response.body()
                eventsAdapter = EventsAdapter(activity!!.applicationContext, responsBody!!)
                eventsAdapter.notifyDataSetChanged()
                rvEvn.adapter = eventsAdapter
            }

            override fun onFailure(call: Call<List<EventModel>?>, t: Throwable) {
                loadingDialog.dismiss()
                Log.d(TAG, "onFailure: "+t.message)
            }

        })
    }
}