package com.nathit.kotlin_retrofit_github

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nathit.kotlin_retrofit_github.Adapter.UserAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeFragment : Fragment() {

    private lateinit var userAdapter: UserAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var rv: RecyclerView
    lateinit var loadingDialog : ProgressDialog

    var mSwipeRefreshLayout: SwipeRefreshLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        rv = view.findViewById(R.id.rv)
        linearLayoutManager = LinearLayoutManager(context)
        rv.layoutManager = linearLayoutManager

        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)
        mSwipeRefreshLayout!!.setOnRefreshListener {
            getData()
            mSwipeRefreshLayout!!.isRefreshing = false
        }
        getData()

        return view
    }

    private fun getData() {
        loadingDialog = ProgressDialog.show(context,"กำลังโหลดข้อมูล","รอสักครู่...",true,false)
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getUserData()

        retrofitData.enqueue(object : Callback<List<UserModel>?> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<List<UserModel>?>,
                response: Response<List<UserModel>?>
            ) {
                loadingDialog.dismiss()
                val responseBody = response.body()
                userAdapter = UserAdapter(activity!!.applicationContext, responseBody!!)
                userAdapter.notifyDataSetChanged()
                rv.adapter = userAdapter

            }

            override fun onFailure(call: Call<List<UserModel>?>, t: Throwable) {
                loadingDialog.dismiss()
                Toast.makeText(context, "Error"+t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

}