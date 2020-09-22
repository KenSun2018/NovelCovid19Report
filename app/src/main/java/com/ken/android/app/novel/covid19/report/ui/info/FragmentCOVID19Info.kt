package com.ken.android.app.novel.covid19.report.ui.info

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ken.android.app.novel.covid19.report.MyApplication
import com.ken.android.app.novel.covid19.report.R
import com.ken.android.app.novel.covid19.report.databinding.FragmentCountryBinding
import com.ken.android.app.novel.covid19.report.repository.remote.OKHttpBaseInterceptor
import com.ken.android.app.novel.covid19.report.repository.remote.rx.COVID19RxApiRepository
import com.ken.android.app.novel.covid19.report.repository.remote.rx.NewsApiOrgRxApiRepository
import com.ken.android.app.novel.covid19.report.ui.info.dialog.CountryListDialogFragment
import com.ken.android.app.novel.covid19.report.ui.recyclerview.RecyclerViewItemDecoration
import javax.inject.Inject

class FragmentCOVID19Info : Fragment() {

    companion object{
        const val TAG = "FragmentCOVID19Info"
    }

    @Inject
    lateinit var covid19ApiRepository: COVID19RxApiRepository


    private val infoViewModel : COVID19InfoViewModel by viewModels<COVID19InfoViewModelRxImpl> {
        COVID19InfoViewModel.RxFactory(covid19ApiRepository)
    }


    private lateinit var binding : FragmentCountryBinding
    private var adapter = COVID19InfoListAdapter()

    private var itemDecoration =
        RecyclerViewItemDecoration()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.add(R.string.search_btn_text)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val title = item.title
        if(title == getString(R.string.search_btn_text)){
            val countries = infoViewModel.getCountriesLiveData().value
            if(countries == null){
                val searchNotFoundKeyWord = resources.getString(R.string.search_not_found)
                Toast.makeText(requireActivity(),String.format(searchNotFoundKeyWord, "") , Toast.LENGTH_SHORT).show()
                return super.onOptionsItemSelected(item)
            }
            val searchTitle = resources.getString(R.string.search_dialog_text)
            val fragment = CountryListDialogFragment.newInstance(searchTitle, countries)
            fragment.setOnCountryClickListener(object : CountryListDialogFragment.OnCountryClickListener{
                override fun onCountryClick(country: String) {
                    infoViewModel.search(country)
                }
            })

            fragment.show(requireActivity().supportFragmentManager, TAG)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentCountryBinding>(inflater, R.layout.fragment_country, container, true)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        binding.viewModel = infoViewModel
        binding.lifecycleOwner = this
        binding.countryRecyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.countryRecyclerView.layoutManager = layoutManager
        binding.countryRecyclerView.removeItemDecoration(itemDecoration)
        binding.countryRecyclerView.addItemDecoration(itemDecoration)
        binding.refresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener{
            override fun onRefresh() {
                infoViewModel.loadGlobalTotalCase()
            }
        })

        infoViewModel.getGlobalCaseLiveData().observe(viewLifecycleOwner, Observer {
            adapter.setGlobalCase(it)
            infoViewModel.loadCountries()
        })

        infoViewModel.getGlobalCaseErrorLiveData().observe(viewLifecycleOwner, Observer {
            infoViewModel.loadCountries()
        })

        infoViewModel.getCountriesLiveData().observe(viewLifecycleOwner, Observer {
            binding.refresh.isRefreshing = false
            adapter.setCountryList(it)
            adapter.notifyDataSetChanged()
        })

        infoViewModel.getCOVID19ChartLiveData().observe(viewLifecycleOwner, Observer {
            adapter.setCOVID19ChartData(it)
            adapter.notifyDataSetChanged()
        })
        infoViewModel.getCountriesErrorLiveData().observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireActivity(), "error $it", Toast.LENGTH_LONG).show()
            adapter.notifyDataSetChanged()
        })

        infoViewModel.searchErrorLiveData().observe(viewLifecycleOwner, Observer {
            val searchNotFoundKeyWord = resources.getString(R.string.search_not_found)
            Toast.makeText(requireActivity(),String.format(searchNotFoundKeyWord, it) , Toast.LENGTH_SHORT).show()
        })




        binding.countryRecyclerView.adapter = adapter
        infoViewModel.loadGlobalTotalCase()
    }
}