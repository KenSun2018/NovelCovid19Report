package com.ken.android.app.novel.covid19.report.ui.news

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ken.android.app.novel.covid19.report.MyApplication
import com.ken.android.app.novel.covid19.report.R
import com.ken.android.app.novel.covid19.report.databinding.FragmentNewsBinding
import com.ken.android.app.novel.covid19.report.repository.bean.NewsArticle
import com.ken.android.app.novel.covid19.report.repository.remote.OKHttpBaseInterceptor
import com.ken.android.app.novel.covid19.report.repository.remote.rx.NewsApiOrgRxApiRepository
import com.ken.android.app.novel.covid19.report.ui.recyclerview.RecyclerViewItemDecoration
import com.ken.android.app.novel.covid19.report.utils.Log
import javax.inject.Inject

class FragmentNews : Fragment() {
    companion object{
        const val TAG = "FragmentNews"
    }

    @Inject
    lateinit var newsApiOrgRxApiRepository: NewsApiOrgRxApiRepository

    private val viewModel : NewsViewModel by viewModels<NewsViewModelRxImpl> {
        NewsViewModel.RxFactory(newsApiOrgRxApiRepository)
    }

    private lateinit var binding : FragmentNewsBinding

    private var adapter = NewsAdapter()
    private var itemDecoration =
        RecyclerViewItemDecoration()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.i(TAG, "onCreateView")
        binding = DataBindingUtil.inflate<FragmentNewsBinding>(inflater, R.layout.fragment_news, container, true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated")

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        val linearLayoutManager = LinearLayoutManager(requireActivity())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.globalRecyclerView.layoutManager = linearLayoutManager
        binding.globalRecyclerView.adapter = adapter
        binding.globalRecyclerView.removeItemDecoration(itemDecoration)
        binding.globalRecyclerView.addItemDecoration(itemDecoration)
        binding.lifecycleOwner = this

        binding.refresh.setOnRefreshListener {
            viewModel.loadNews(resources.getString(R.string.news_search_key))
        }
        viewModel.getNewsLiveData().observe(viewLifecycleOwner, Observer {
            binding.refresh.isRefreshing = false
            adapter.setNewsArticle(it)
            adapter.notifyDataSetChanged()
        })

        viewModel.getErrorLiveData().observe(viewLifecycleOwner, Observer {
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


}