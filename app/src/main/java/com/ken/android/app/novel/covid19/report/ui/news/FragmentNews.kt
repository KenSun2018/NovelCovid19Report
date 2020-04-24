package com.ken.android.app.novel.covid19.report.ui.news

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ken.android.app.novel.covid19.report.R
import com.ken.android.app.novel.covid19.report.databinding.FragmentNewsBinding
import com.ken.android.app.novel.covid19.report.repository.bean.NewsArticle
import com.ken.android.app.novel.covid19.report.ui.recyclerview.RecyclerViewItemDecoration

class FragmentNews : Fragment() {
    companion object{
        const val TAG = "FragmentNews"
    }

    private val viewModel : NewsViewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application).create(NewsViewModelRxImpl::class.java)
    }

    private lateinit var binding : FragmentNewsBinding

    private var adapter = NewsAdapter()
    private var itemDecoration =
        RecyclerViewItemDecoration()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentNewsBinding>(inflater, R.layout.fragment_news, container, true)
        binding.viewModel = viewModel
        val linearLayoutManager = LinearLayoutManager(requireActivity())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.globalRecyclerView.layoutManager = linearLayoutManager
        binding.globalRecyclerView.adapter = adapter
        binding.globalRecyclerView.removeItemDecoration(itemDecoration)
        binding.globalRecyclerView.addItemDecoration(itemDecoration)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.refresh.setOnRefreshListener {
            viewModel.loadNews(resources.getString(R.string.news_search_key))
        }
        viewModel.getNewsLiveData().observe(requireActivity(), Observer {
            binding.refresh.isRefreshing = false
            adapter.setNewsArticle(it)
            adapter.notifyDataSetChanged()
        })

        viewModel.getErrorLiveData().observe(requireActivity(), Observer {
            binding.refresh.isRefreshing = false

            adapter.notifyDataSetChanged()
        })

        adapter.setOnItemViewClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

                val tag = v?.tag?:return

                if(tag is NewsArticle){
                    try {
                        val intentToView = Intent(Intent.ACTION_VIEW)
                        intentToView.data = Uri.parse(tag.url)
                        startActivity(intentToView)
                    } catch (e: Exception) {
                    }
                }
            }
        })
        viewModel.loadNews(resources.getString(R.string.news_search_key))
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.destroy()
    }
}