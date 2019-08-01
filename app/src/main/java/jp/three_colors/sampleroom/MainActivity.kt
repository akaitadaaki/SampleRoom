package jp.three_colors.sampleroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import jp.three_colors.sampleroom.adapter.AddressListAdapter
import jp.three_colors.sampleroom.databinding.ActivityMainBinding
import jp.three_colors.sampleroom.viewmodel.AddressViewModel

class MainActivity : AppCompatActivity() {
    private val adapter by lazy { AddressListAdapter()}
    private lateinit var addressViewModel: AddressViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addressViewModel = ViewModelProviders.of(this).get(AddressViewModel::class.java)

        addressViewModel.allAddress.observe(this, Observer { address ->
            address?.let {
                adapter.setAddressList(it)
            }
        })

        addressViewModel.targetAddress.observe(this, Observer { address ->
            address?.let {
                if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                    Toast.makeText(applicationContext, it.apiAddress.stateName+it.apiAddress.city+it.apiAddress.street, Toast.LENGTH_SHORT).show()
                }
            }
        })

        addressViewModel.errorMessage.observe(this, Observer { message ->
            message?.let {
                if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                    Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
                }
            }
        })

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.viewModel = addressViewModel
        binding.listAddress.adapter = adapter
        binding.lifecycleOwner = this
        binding.clickCallback = this
    }

    fun onClickSearch() {
        addressViewModel.getAddress()
    }

    fun onClickClear() {
        addressViewModel.deleteAll()
    }

}
