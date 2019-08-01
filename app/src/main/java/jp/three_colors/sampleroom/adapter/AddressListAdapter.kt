package jp.three_colors.sampleroom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import jp.three_colors.sampleroom.R
import jp.three_colors.sampleroom.data.entity.Address
import jp.three_colors.sampleroom.databinding.ItemAddressBinding
import java.util.*

class AddressListAdapter: RecyclerView.Adapter<AddressListAdapter.AddressViewHolder>() {
    private var addressList = emptyList<Address>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val binding = DataBindingUtil.inflate<ItemAddressBinding>(LayoutInflater.from(parent.context), R.layout.item_address, parent, false)
        return AddressViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.bind(addressList[position])
    }

    override fun getItemCount(): Int {
        return addressList.size
    }

    inner class AddressViewHolder(private val binding: ItemAddressBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Address) {
            binding.address = data
            binding.executePendingBindings()
        }
    }

    fun setAddressList(addressList: List<Address>) {
        if( this.addressList.isEmpty() ) {
            this.addressList = addressList
            notifyItemRangeChanged(0, addressList.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return this@AddressListAdapter.addressList.size
                }

                override fun getNewListSize(): Int {
                    return addressList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return this@AddressListAdapter.addressList[oldItemPosition].id == addressList[newItemPosition].id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val address = addressList[newItemPosition]
                    val old = addressList[oldItemPosition]

                    return address.id == old.id
                            && Objects.equals(address.apiAddress.state, old.apiAddress.state)
                            && Objects.equals(address.apiAddress.stateName, old.apiAddress.stateName)
                            && Objects.equals(address.apiAddress.city, old.apiAddress.city)
                            && Objects.equals(address.apiAddress.street, old.apiAddress.street)
                }
            })

            this.addressList = addressList
            result.dispatchUpdatesTo(this)
        }
    }
}