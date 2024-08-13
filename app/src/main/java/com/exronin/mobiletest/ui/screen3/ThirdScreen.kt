package com.exronin.mobiletest.ui.screen3


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exronin.mobiletest.data.model.User
import com.exronin.mobiletest.data.model.UserResponse
import com.exronin.mobiletest.data.repository.UserAdapter
import com.exronin.mobiletest.data.source.network.ApiClient
import com.exronin.mobiletest.databinding.ActivityThirdBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThirdScreen : AppCompatActivity() {

    private lateinit var binding: ActivityThirdBinding
    private lateinit var adapter: UserAdapter
    private var users = mutableListOf<User>()
    private var currentPage = 1
    private var totalPages = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)


        adapter = UserAdapter(users) { user ->
            val intent = Intent()
            intent.putExtra("SELECTED_USER", "${user.first_name} ${user.last_name}")
            setResult(RESULT_OK, intent)
            finish()
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter


        binding.swipeRefreshLayout.setOnRefreshListener {
            currentPage = 1
            users.clear()
            loadUsers()
        }

        binding.backButton.setOnClickListener {
            onBackPressed()
        }


        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                if (!binding.recyclerView.canScrollVertically(1) && lastVisibleItemPosition == users.size - 1) {
                    if (currentPage < totalPages) {
                        currentPage++
                        loadUsers()
                    }
                }
            }
        })

        loadUsers()
    }

    private fun loadUsers() {
        binding.swipeRefreshLayout.isRefreshing = true

        ApiClient.instance.getUsers(currentPage, 10).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        totalPages = it.total_pages
                        adapter.addUsers(it.data)
                    }
                } else {
                    Log.d("API_RESPONSE", "Response not successful")
                }
                binding.swipeRefreshLayout.isRefreshing = false
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("API_ERROR", "Failed to load users", t)
                binding.swipeRefreshLayout.isRefreshing = false
            }
        })
    }

}