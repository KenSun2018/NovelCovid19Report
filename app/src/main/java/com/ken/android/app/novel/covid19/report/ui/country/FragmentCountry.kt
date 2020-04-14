package com.ken.android.app.novel.covid19.report.ui.country

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ken.android.app.novel.covid19.report.R
import com.ken.android.app.novel.covid19.report.databinding.FragmentCountryBinding
import com.ken.android.app.novel.covid19.report.ui.country.dialog.CountryListDialogFragment
import com.ken.android.app.novel.covid19.report.ui.recyclerview.RecyclerViewItemDecoration

class FragmentCountry : Fragment() {

    companion object{
        const val TAG = "FragmentCountry"
    }

    private val viewModel : COVID19ViewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application).create(
            COVID19ViewModelRxImpl::class.java)
    }

    private lateinit var binding : FragmentCountryBinding
    private var adapter = CountryListAdapter()

    private var itemDecoration =
        RecyclerViewItemDecoration()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentCountryBinding>(inflater, R.layout.fragment_country, container, true)
        binding.viewModel = viewModel
        binding.countryRecyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.countryRecyclerView.layoutManager = layoutManager
        binding.countryRecyclerView.removeItemDecoration(itemDecoration)
        binding.countryRecyclerView.addItemDecoration(itemDecoration)
        binding.refresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener{
            override fun onRefresh() {
                viewModel.loadGlobalTotalCase()
            }
        })
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getGlobalCaseLiveData().observe(requireActivity(), Observer {
            adapter.setGlobalCase(it)
            viewModel.loadCountries()
        })

        viewModel.getGlobalCaseErrorLiveData().observe(requireActivity(), Observer {
            viewModel.loadCountries()
        })

        viewModel.getCountriesLiveData().observe(requireActivity(), Observer {
            binding.refresh.isRefreshing = false
            adapter.setCountryList(it)
            adapter.notifyDataSetChanged()


        })
        viewModel.getCountriesErrorLiveData().observe(requireActivity(), Observer {
            Toast.makeText(requireActivity(), "error $it", Toast.LENGTH_LONG).show()
            adapter.notifyDataSetChanged()
        })

        viewModel.searchErrorLiveData().observe(requireActivity(), Observer {
            val searchNotFoundKeyWord = resources.getString(R.string.search_not_found)
            Toast.makeText(requireActivity(),String.format(searchNotFoundKeyWord, it) , Toast.LENGTH_SHORT).show()
        })

        binding.searchButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val countries = viewModel.getCountriesLiveData().value
                if(countries == null){
                    val searchNotFoundKeyWord = resources.getString(R.string.search_not_found)
                    Toast.makeText(requireActivity(),String.format(searchNotFoundKeyWord, "") , Toast.LENGTH_SHORT).show()
                    return
                }
                val searchTitle = resources.getString(R.string.search_dialog_text)
                val fragment = CountryListDialogFragment.newInstance(searchTitle, countries)
                fragment.setOnCountryClickListener(object : CountryListDialogFragment.OnCountryClickListener{
                    override fun onCountryClick(country: String) {
                        viewModel.search(country)
                    }
                })

                fragment.show(requireActivity().supportFragmentManager, TAG)
            }
        })


        binding.countryRecyclerView.adapter = adapter
        viewModel.loadGlobalTotalCase()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.destroy()
    }
}