import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.farm2fabric.AcceptOrder
import com.example.farm2fabric.ConsignmentDetail
import com.example.farm2fabric.ConsignmentItem
import com.example.farm2fabric.PaymentDetail
import com.example.farm2fabric.R
import java.text.SimpleDateFormat
import java.util.*

class ConsignmentAdapter(
    private val context: Context,
    private val consignments: List<ConsignmentItem>
) : RecyclerView.Adapter<ConsignmentAdapter.ConsignmentViewHolder>() {

    inner class ConsignmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val consignmentIdTextView: TextView = itemView.findViewById(R.id.consignmentIdTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val amountTextView: TextView = itemView.findViewById(R.id.amountTextView)
        val quantityTextView: TextView = itemView.findViewById(R.id.quantityTextView)
        val statusTextView: TextView = itemView.findViewById(R.id.statusTextView)
        val viewInfoButton: LinearLayout = itemView.findViewById(R.id.viewInfoButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConsignmentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_consignment, parent, false)
        return ConsignmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: ConsignmentViewHolder, position: Int) {
        val item = consignments[position]

        holder.consignmentIdTextView.text = item.consignmentId

        val formatter = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        val formattedDate = formatter.format(item.date)
        holder.dateTextView.text = formattedDate

        holder.amountTextView.text = "₹${item.price}"
        holder.quantityTextView.text = "Qty: ${item.quantity} Kg"

        if(item.status == "step0"){
            holder.statusTextView.text = "Created"
        }else if(item.status == "step1") {
            holder.statusTextView.text = "Accepted"
        }else if(item.status == "step2"){
            holder.statusTextView.text = "In Transit"
        }


        val sharedPreferences = context.getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
        val role = sharedPreferences.getString("role", "") ?: ""

        if(role == "Customer"){
            holder.viewInfoButton.setOnClickListener {
                val intent = Intent(context, ConsignmentDetail::class.java).apply {
                    putExtra("consignmentId", item.consignmentId)
                    putExtra("amount", item.price.toString())
                    putExtra("status", item.status)
                    putExtra("date", formattedDate)
                    putExtra("payment_id", item.paymentid)
                    putExtra("quantity", item.quantity.toString())
                }
                context.startActivity(intent)
            }
        }else if(role == "Farmer"){
            holder.viewInfoButton.setOnClickListener {
                val intent = Intent(context, AcceptOrder::class.java).apply {
                    putExtra("consignmentId", item.consignmentId)
                    putExtra("amount", item.price.toString())
                    putExtra("status", item.status)
                    putExtra("date", formattedDate)
                    putExtra("payment_id", item.paymentid)
                    putExtra("quantity", item.quantity.toString())
                }
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = consignments.size
}
