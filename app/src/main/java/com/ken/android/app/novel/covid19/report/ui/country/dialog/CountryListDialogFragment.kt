package com.ken.android.app.novel.covid19.report.ui.country.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.ken.android.app.novel.covid19.report.R
import com.ken.android.app.novel.covid19.report.repository.bean.Country
import com.ken.android.app.novel.covid19.report.databinding.DialogFragmentCountryListBinding


class CountryListDialogFragment : DialogFragment() {

    private var title = ""
    private var countrylist = ArrayList<String>()
    private lateinit var binding : DialogFragmentCountryListBinding


    private var onCountryClickListener: OnCountryClickListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = arguments?.getString(ARGUMENT_TITLE, "")?:""
        countrylist = arguments?.getStringArrayList(ARGUMENT_COUNTRY_LIST)?:ArrayList<String>()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<DialogFragmentCountryListBinding>(inflater, R.layout.dialog_fragment_country_list, container, true)



        val arrayAdapter = ArrayAdapter<String>(requireActivity(), android.R.layout.simple_dropdown_item_1line, countrylist)
        binding.autoCompleteSearch.setAdapter(arrayAdapter)
        binding.autoCompleteSearch.threshold = 1

        val dialogHeight = resources.getDimensionPixelSize(R.dimen.search_country_dialog_height)
        val fieldHeight = resources.getDimensionPixelSize(R.dimen.search_country_field_height)
        binding.autoCompleteSearch.dropDownHeight = dialogHeight - fieldHeight * 3
        binding.autoCompleteSearch.onItemClickListener = object : AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val token = view?.windowToken
                if(token != null){
                    val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(token, 0)
                }



                onCountryClickListener?.onCountryClick(binding.autoCompleteSearch.adapter.getItem(position).toString())

                dismiss()
            }

        }

        binding.search.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                v?:return

                val text = binding.autoCompleteSearch.text.toString()
                dismiss()
                onCountryClickListener?.onCountryClick(text)
            }

        })
        return binding.root
    }

    interface OnCountryClickListener{
        fun onCountryClick(country : String)
    }

    fun setOnCountryClickListener( onCountryClickListener: OnCountryClickListener){
        this.onCountryClickListener = onCountryClickListener
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun dismiss() {
        val token = binding.autoCompleteSearch.windowToken
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if(token != null){
            inputMethodManager.hideSoftInputFromWindow(token, 0)

        }else{
            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

        }


        super.dismiss()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }

    companion object{
        const val TAG = "CountryListDialog"
        const val ARGUMENT_TITLE = "argument_title"
        const val ARGUMENT_COUNTRY_LIST = "argument_country_list"


        fun newInstance(title : String, countryList: List<Country>) : CountryListDialogFragment {
            val fragment = CountryListDialogFragment()
            val bundle = Bundle()
            bundle.putString(ARGUMENT_TITLE, title)

            val countryStrList = ArrayList<String>()
            for(country in countryList){
                countryStrList.add(country.country)
            }
            bundle.putStringArrayList(ARGUMENT_COUNTRY_LIST, countryStrList)
            fragment.arguments = bundle
            return fragment
        }
    }
}