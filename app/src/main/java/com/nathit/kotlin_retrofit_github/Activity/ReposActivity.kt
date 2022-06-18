package com.nathit.kotlin_retrofit_github.Activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nathit.kotlin_retrofit_github.Adapter.ReposAdapter
import com.nathit.kotlin_retrofit_github.ApiReposInterface
import com.nathit.kotlin_retrofit_github.MainActivity
import com.nathit.kotlin_retrofit_github.R
import com.nathit.kotlin_retrofit_github.ReposModel
import kotlinx.android.synthetic.main.activity_repos.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ReposActivity : AppCompatActivity() {

    private lateinit var reposAdapter: ReposAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var rvpos: RecyclerView
    lateinit var loadingDialog: ProgressDialog

    var mSwipeRefreshLayout: SwipeRefreshLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repos)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        rvpos = findViewById(R.id.rvpos)
        linearLayoutManager = LinearLayoutManager(this)
        rvpos.layoutManager = linearLayoutManager

        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)
        mSwipeRefreshLayout!!.setOnRefreshListener {
            getDataRepos()
            mSwipeRefreshLayout!!.isRefreshing = false
        }
        getDataRepos()

    }

    private fun getDataRepos() {
        loadingDialog = ProgressDialog.show(this, "กำลังโหลดข้อมูล", "รอสักครู่...", true, false)
        val apiReposInterface: ApiReposInterface =
            ApiReposInterface.retrofit.create(ApiReposInterface::class.java)
        val url = intent.getStringExtra("repos_url")
        val call: Call<List<ReposModel>> = apiReposInterface.loadRepos(url)

        call.enqueue(object : Callback<List<ReposModel>> {
            override fun onResponse(
                call: Call<List<ReposModel>>,
                response: Response<List<ReposModel>>
            ) {
                loadingDialog.dismiss()
                val responseBody = response.body()!!
                reposAdapter = ReposAdapter(applicationContext, responseBody)
                reposAdapter.notifyDataSetChanged()
                rvpos.adapter = reposAdapter

            }

            override fun onFailure(call: Call<List<ReposModel>>, t: Throwable) {
                loadingDialog.dismiss()
                Log.d("RequestCall", "Request failed"+t.message)
            }
        })
    }
}