package com.nathit.kotlin_retrofit_github.Activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nathit.kotlin_retrofit_github.Adapter.FollowersAdapter
import com.nathit.kotlin_retrofit_github.ApiFollowersInterface
import com.nathit.kotlin_retrofit_github.FollowersModel
import com.nathit.kotlin_retrofit_github.MainActivity
import com.nathit.kotlin_retrofit_github.R
import kotlinx.android.synthetic.main.activity_repos.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersActivity : AppCompatActivity() {

    private lateinit var followersAdapter: FollowersAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var rvfow: RecyclerView
    lateinit var loadingDialog: ProgressDialog

    var mSwipeRefreshLayout: SwipeRefreshLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_followers)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        rvfow = findViewById(R.id.rvfow)
        linearLayoutManager = LinearLayoutManager(this)
        rvfow.layoutManager = linearLayoutManager

        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)
        mSwipeRefreshLayout!!.setOnRefreshListener {
            getDataFollowers()
            mSwipeRefreshLayout!!.isRefreshing = false
        }

        getDataFollowers()
    }

    private fun getDataFollowers() {
        loadingDialog = ProgressDialog.show(this, "กำลังโหลดข้อมูล", "รอสักครู่...", true, false)
        val apiFollowersInterface: ApiFollowersInterface =
            ApiFollowersInterface.retrofit.create(ApiFollowersInterface::class.java)
        val url = intent.getStringExtra("follower_url")
        val call: Call<List<FollowersModel>> = apiFollowersInterface.loadFollowers(url)

        call.enqueue(object : Callback<List<FollowersModel>> {
            override fun onResponse(
                call: Call<List<FollowersModel>>,
                response: Response<List<FollowersModel>>
            ) {
                loadingDialog.dismiss()
                val responseBody = response.body()!!
                followersAdapter = FollowersAdapter(applicationContext, responseBody)
                followersAdapter.notifyDataSetChanged()
                rvfow.adapter = followersAdapter
            }

            override fun onFailure(call: Call<List<FollowersModel>>, t: Throwable) {
                loadingDialog.dismiss()
                Log.d("RequestCall", "Request failed")
            }

        })

    }
}